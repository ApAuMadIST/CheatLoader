package net.minecraft.src;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkManager {
	public static final Object threadSyncObject = new Object();
	public static int numReadThreads;
	public static int numWriteThreads;
	private Object sendQueueLock = new Object();
	private Socket networkSocket;
	private final SocketAddress remoteSocketAddress;
	private DataInputStream socketInputStream;
	private DataOutputStream socketOutputStream;
	private boolean isRunning = true;
	private List readPackets = Collections.synchronizedList(new ArrayList());
	private List dataPackets = Collections.synchronizedList(new ArrayList());
	private List chunkDataPackets = Collections.synchronizedList(new ArrayList());
	private NetHandler netHandler;
	private boolean isServerTerminating = false;
	private Thread writeThread;
	private Thread readThread;
	private boolean isTerminating = false;
	private String terminationReason = "";
	private Object[] field_20101_t;
	private int timeSinceLastRead = 0;
	private int sendQueueByteLength = 0;
	public static int[] field_28145_d = new int[256];
	public static int[] field_28144_e = new int[256];
	public int chunkDataSendCounter = 0;
	private int field_20100_w = 50;

	public NetworkManager(Socket socket1, String string2, NetHandler netHandler3) throws IOException {
		this.networkSocket = socket1;
		this.remoteSocketAddress = socket1.getRemoteSocketAddress();
		this.netHandler = netHandler3;

		try {
			socket1.setSoTimeout(30000);
			socket1.setTrafficClass(24);
		} catch (SocketException socketException5) {
			System.err.println(socketException5.getMessage());
		}

		this.socketInputStream = new DataInputStream(socket1.getInputStream());
		this.socketOutputStream = new DataOutputStream(new BufferedOutputStream(socket1.getOutputStream(), 5120));
		this.readThread = new NetworkReaderThread(this, string2 + " read thread");
		this.writeThread = new NetworkWriterThread(this, string2 + " write thread");
		this.readThread.start();
		this.writeThread.start();
	}

	public void addToSendQueue(Packet packet1) {
		if(!this.isServerTerminating) {
			Object object2 = this.sendQueueLock;
			synchronized(this.sendQueueLock) {
				this.sendQueueByteLength += packet1.getPacketSize() + 1;
				if(packet1.isChunkDataPacket) {
					this.chunkDataPackets.add(packet1);
				} else {
					this.dataPackets.add(packet1);
				}

			}
		}
	}

	private boolean sendPacket() {
		boolean z1 = false;

		try {
			int[] i10000;
			int i10001;
			Packet packet2;
			Object object3;
			if(!this.dataPackets.isEmpty() && (this.chunkDataSendCounter == 0 || System.currentTimeMillis() - ((Packet)this.dataPackets.get(0)).creationTimeMillis >= (long)this.chunkDataSendCounter)) {
				object3 = this.sendQueueLock;
				synchronized(this.sendQueueLock) {
					packet2 = (Packet)this.dataPackets.remove(0);
					this.sendQueueByteLength -= packet2.getPacketSize() + 1;
				}

				Packet.writePacket(packet2, this.socketOutputStream);
				i10000 = field_28144_e;
				i10001 = packet2.getPacketId();
				i10000[i10001] += packet2.getPacketSize() + 1;
				z1 = true;
			}

			if(this.field_20100_w-- <= 0 && !this.chunkDataPackets.isEmpty() && (this.chunkDataSendCounter == 0 || System.currentTimeMillis() - ((Packet)this.chunkDataPackets.get(0)).creationTimeMillis >= (long)this.chunkDataSendCounter)) {
				object3 = this.sendQueueLock;
				synchronized(this.sendQueueLock) {
					packet2 = (Packet)this.chunkDataPackets.remove(0);
					this.sendQueueByteLength -= packet2.getPacketSize() + 1;
				}

				Packet.writePacket(packet2, this.socketOutputStream);
				i10000 = field_28144_e;
				i10001 = packet2.getPacketId();
				i10000[i10001] += packet2.getPacketSize() + 1;
				this.field_20100_w = 0;
				z1 = true;
			}

			return z1;
		} catch (Exception exception8) {
			if(!this.isTerminating) {
				this.onNetworkError(exception8);
			}

			return false;
		}
	}

	public void wakeThreads() {
		this.readThread.interrupt();
		this.writeThread.interrupt();
	}

	private boolean readPacket() {
		boolean z1 = false;

		try {
			Packet packet2 = Packet.readPacket(this.socketInputStream, this.netHandler.isServerHandler());
			if(packet2 != null) {
				int[] i10000 = field_28145_d;
				int i10001 = packet2.getPacketId();
				i10000[i10001] += packet2.getPacketSize() + 1;
				this.readPackets.add(packet2);
				z1 = true;
			} else {
				this.networkShutdown("disconnect.endOfStream", new Object[0]);
			}

			return z1;
		} catch (Exception exception3) {
			if(!this.isTerminating) {
				this.onNetworkError(exception3);
			}

			return false;
		}
	}

	private void onNetworkError(Exception exception1) {
		exception1.printStackTrace();
		this.networkShutdown("disconnect.genericReason", new Object[]{"Internal exception: " + exception1.toString()});
	}

	public void networkShutdown(String string1, Object... object2) {
		if(this.isRunning) {
			this.isTerminating = true;
			this.terminationReason = string1;
			this.field_20101_t = object2;
			(new NetworkMasterThread(this)).start();
			this.isRunning = false;

			try {
				this.socketInputStream.close();
				this.socketInputStream = null;
			} catch (Throwable throwable6) {
			}

			try {
				this.socketOutputStream.close();
				this.socketOutputStream = null;
			} catch (Throwable throwable5) {
			}

			try {
				this.networkSocket.close();
				this.networkSocket = null;
			} catch (Throwable throwable4) {
			}

		}
	}

	public void processReadPackets() {
		if(this.sendQueueByteLength > 1048576) {
			this.networkShutdown("disconnect.overflow", new Object[0]);
		}

		if(this.readPackets.isEmpty()) {
			if(this.timeSinceLastRead++ == 1200) {
				this.networkShutdown("disconnect.timeout", new Object[0]);
			}
		} else {
			this.timeSinceLastRead = 0;
		}

		int i1 = 100;

		while(!this.readPackets.isEmpty() && i1-- >= 0) {
			Packet packet2 = (Packet)this.readPackets.remove(0);
			packet2.processPacket(this.netHandler);
		}

		this.wakeThreads();
		if(this.isTerminating && this.readPackets.isEmpty()) {
			this.netHandler.handleErrorMessage(this.terminationReason, this.field_20101_t);
		}

	}

	public void func_28142_c() {
		this.wakeThreads();
		this.isServerTerminating = true;
		this.readThread.interrupt();
		(new ThreadCloseConnection(this)).start();
	}

	static boolean isRunning(NetworkManager networkManager0) {
		return networkManager0.isRunning;
	}

	static boolean isServerTerminating(NetworkManager networkManager0) {
		return networkManager0.isServerTerminating;
	}

	static boolean readNetworkPacket(NetworkManager networkManager0) {
		return networkManager0.readPacket();
	}

	static boolean sendNetworkPacket(NetworkManager networkManager0) {
		return networkManager0.sendPacket();
	}

	static DataOutputStream func_28140_f(NetworkManager networkManager0) {
		return networkManager0.socketOutputStream;
	}

	static boolean func_28138_e(NetworkManager networkManager0) {
		return networkManager0.isTerminating;
	}

	static void func_30005_a(NetworkManager networkManager0, Exception exception1) {
		networkManager0.onNetworkError(exception1);
	}

	static Thread getReadThread(NetworkManager networkManager0) {
		return networkManager0.readThread;
	}

	static Thread getWriteThread(NetworkManager networkManager0) {
		return networkManager0.writeThread;
	}
}
