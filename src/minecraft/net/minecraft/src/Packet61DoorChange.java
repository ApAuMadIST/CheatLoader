package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet61DoorChange extends Packet {
	public int field_28050_a;
	public int field_28049_b;
	public int field_28053_c;
	public int field_28052_d;
	public int field_28051_e;

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.field_28050_a = dataInputStream1.readInt();
		this.field_28053_c = dataInputStream1.readInt();
		this.field_28052_d = dataInputStream1.readByte();
		this.field_28051_e = dataInputStream1.readInt();
		this.field_28049_b = dataInputStream1.readInt();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.field_28050_a);
		dataOutputStream1.writeInt(this.field_28053_c);
		dataOutputStream1.writeByte(this.field_28052_d);
		dataOutputStream1.writeInt(this.field_28051_e);
		dataOutputStream1.writeInt(this.field_28049_b);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_28115_a(this);
	}

	public int getPacketSize() {
		return 20;
	}
}
