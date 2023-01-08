package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet131MapData extends Packet {
	public short field_28055_a;
	public short field_28054_b;
	public byte[] field_28056_c;

	public Packet131MapData() {
		this.isChunkDataPacket = true;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.field_28055_a = dataInputStream1.readShort();
		this.field_28054_b = dataInputStream1.readShort();
		this.field_28056_c = new byte[dataInputStream1.readByte() & 255];
		dataInputStream1.readFully(this.field_28056_c);
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeShort(this.field_28055_a);
		dataOutputStream1.writeShort(this.field_28054_b);
		dataOutputStream1.writeByte(this.field_28056_c.length);
		dataOutputStream1.write(this.field_28056_c);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_28116_a(this);
	}

	public int getPacketSize() {
		return 4 + this.field_28056_c.length;
	}
}
