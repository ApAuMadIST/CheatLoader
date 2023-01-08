package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet8UpdateHealth extends Packet {
	public int healthMP;

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.healthMP = dataInputStream1.readShort();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeShort(this.healthMP);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleHealth(this);
	}

	public int getPacketSize() {
		return 2;
	}
}
