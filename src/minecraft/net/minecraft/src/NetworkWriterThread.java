package net.minecraft.src;

import java.io.IOException;

class NetworkWriterThread extends Thread {
	final NetworkManager netManager;

	NetworkWriterThread(NetworkManager networkManager1, String string2) {
		super(string2);
		this.netManager = networkManager1;
	}

	public void run() {
		Object object1 = NetworkManager.threadSyncObject;
		synchronized(NetworkManager.threadSyncObject) {
			++NetworkManager.numWriteThreads;
		}

		while(true) {
			boolean z13 = false;

			try {
				z13 = true;
				if(!NetworkManager.isRunning(this.netManager)) {
					z13 = false;
					break;
				}

				while(NetworkManager.sendNetworkPacket(this.netManager)) {
				}

				try {
					sleep(100L);
				} catch (InterruptedException interruptedException16) {
				}

				try {
					if(NetworkManager.func_28140_f(this.netManager) != null) {
						NetworkManager.func_28140_f(this.netManager).flush();
					}
				} catch (IOException iOException18) {
					if(!NetworkManager.func_28138_e(this.netManager)) {
						NetworkManager.func_30005_a(this.netManager, iOException18);
					}

					iOException18.printStackTrace();
				}
			} finally {
				if(z13) {
					Object object5 = NetworkManager.threadSyncObject;
					synchronized(NetworkManager.threadSyncObject) {
						--NetworkManager.numWriteThreads;
					}
				}
			}
		}

		object1 = NetworkManager.threadSyncObject;
		synchronized(NetworkManager.threadSyncObject) {
			--NetworkManager.numWriteThreads;
		}
	}
}
