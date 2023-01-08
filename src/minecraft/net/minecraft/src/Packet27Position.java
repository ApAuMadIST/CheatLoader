package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet27Position extends Packet {
	private float field_22039_a;
	private float field_22038_b;
	private boolean field_22043_c;
	private boolean field_22042_d;
	private float field_22041_e;
	private float field_22040_f;

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.field_22039_a = dataInputStream1.readFloat();
		this.field_22038_b = dataInputStream1.readFloat();
		this.field_22041_e = dataInputStream1.readFloat();
		this.field_22040_f = dataInputStream1.readFloat();
		this.field_22043_c = dataInputStream1.readBoolean();
		this.field_22042_d = dataInputStream1.readBoolean();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeFloat(this.field_22039_a);
		dataOutputStream1.writeFloat(this.field_22038_b);
		dataOutputStream1.writeFloat(this.field_22041_e);
		dataOutputStream1.writeFloat(this.field_22040_f);
		dataOutputStream1.writeBoolean(this.field_22043_c);
		dataOutputStream1.writeBoolean(this.field_22042_d);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_22185_a(this);
	}

	public int getPacketSize() {
		return 18;
	}
}
