package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet4UpdateTime extends Packet {
	public long time;

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.time = dataInputStream1.readLong();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeLong(this.time);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleUpdateTime(this);
	}

	public int getPacketSize() {
		return 8;
	}
}
