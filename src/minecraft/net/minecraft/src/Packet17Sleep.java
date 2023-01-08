package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet17Sleep extends Packet {
	public int field_22045_a;
	public int field_22044_b;
	public int field_22048_c;
	public int field_22047_d;
	public int field_22046_e;

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.field_22045_a = dataInputStream1.readInt();
		this.field_22046_e = dataInputStream1.readByte();
		this.field_22044_b = dataInputStream1.readInt();
		this.field_22048_c = dataInputStream1.readByte();
		this.field_22047_d = dataInputStream1.readInt();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.field_22045_a);
		dataOutputStream1.writeByte(this.field_22046_e);
		dataOutputStream1.writeInt(this.field_22044_b);
		dataOutputStream1.writeByte(this.field_22048_c);
		dataOutputStream1.writeInt(this.field_22047_d);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_22186_a(this);
	}

	public int getPacketSize() {
		return 14;
	}
}
