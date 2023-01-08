package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet200Statistic extends Packet {
	public int field_27052_a;
	public int field_27051_b;

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_27245_a(this);
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.field_27052_a = dataInputStream1.readInt();
		this.field_27051_b = dataInputStream1.readByte();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.field_27052_a);
		dataOutputStream1.writeByte(this.field_27051_b);
	}

	public int getPacketSize() {
		return 6;
	}
}
