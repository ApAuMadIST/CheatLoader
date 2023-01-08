package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet106Transaction extends Packet {
	public int windowId;
	public short field_20028_b;
	public boolean field_20030_c;

	public Packet106Transaction() {
	}

	public Packet106Transaction(int i1, short s2, boolean z3) {
		this.windowId = i1;
		this.field_20028_b = s2;
		this.field_20030_c = z3;
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_20089_a(this);
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.windowId = dataInputStream1.readByte();
		this.field_20028_b = dataInputStream1.readShort();
		this.field_20030_c = dataInputStream1.readByte() != 0;
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeByte(this.windowId);
		dataOutputStream1.writeShort(this.field_20028_b);
		dataOutputStream1.writeByte(this.field_20030_c ? 1 : 0);
	}

	public int getPacketSize() {
		return 4;
	}
}
