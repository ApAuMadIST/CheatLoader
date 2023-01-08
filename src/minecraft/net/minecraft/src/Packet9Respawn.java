package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet9Respawn extends Packet {
	public byte field_28048_a;

	public Packet9Respawn() {
	}

	public Packet9Respawn(byte b1) {
		this.field_28048_a = b1;
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_9448_a(this);
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.field_28048_a = dataInputStream1.readByte();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeByte(this.field_28048_a);
	}

	public int getPacketSize() {
		return 1;
	}
}
