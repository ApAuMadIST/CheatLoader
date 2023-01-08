package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet71Weather extends Packet {
	public int field_27054_a;
	public int field_27053_b;
	public int field_27057_c;
	public int field_27056_d;
	public int field_27055_e;

	public Packet71Weather() {
	}

	public Packet71Weather(Entity entity1) {
		this.field_27054_a = entity1.entityId;
		this.field_27053_b = MathHelper.floor_double(entity1.posX * 32.0D);
		this.field_27057_c = MathHelper.floor_double(entity1.posY * 32.0D);
		this.field_27056_d = MathHelper.floor_double(entity1.posZ * 32.0D);
		if(entity1 instanceof EntityLightningBolt) {
			this.field_27055_e = 1;
		}

	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.field_27054_a = dataInputStream1.readInt();
		this.field_27055_e = dataInputStream1.readByte();
		this.field_27053_b = dataInputStream1.readInt();
		this.field_27057_c = dataInputStream1.readInt();
		this.field_27056_d = dataInputStream1.readInt();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.field_27054_a);
		dataOutputStream1.writeByte(this.field_27055_e);
		dataOutputStream1.writeInt(this.field_27053_b);
		dataOutputStream1.writeInt(this.field_27057_c);
		dataOutputStream1.writeInt(this.field_27056_d);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleWeather(this);
	}

	public int getPacketSize() {
		return 17;
	}
}
