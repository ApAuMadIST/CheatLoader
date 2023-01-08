package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet23VehicleSpawn extends Packet {
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int field_28047_e;
	public int field_28046_f;
	public int field_28045_g;
	public int type;
	public int field_28044_i;

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.entityId = dataInputStream1.readInt();
		this.type = dataInputStream1.readByte();
		this.xPosition = dataInputStream1.readInt();
		this.yPosition = dataInputStream1.readInt();
		this.zPosition = dataInputStream1.readInt();
		this.field_28044_i = dataInputStream1.readInt();
		if(this.field_28044_i > 0) {
			this.field_28047_e = dataInputStream1.readShort();
			this.field_28046_f = dataInputStream1.readShort();
			this.field_28045_g = dataInputStream1.readShort();
		}

	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.entityId);
		dataOutputStream1.writeByte(this.type);
		dataOutputStream1.writeInt(this.xPosition);
		dataOutputStream1.writeInt(this.yPosition);
		dataOutputStream1.writeInt(this.zPosition);
		dataOutputStream1.writeInt(this.field_28044_i);
		if(this.field_28044_i > 0) {
			dataOutputStream1.writeShort(this.field_28047_e);
			dataOutputStream1.writeShort(this.field_28046_f);
			dataOutputStream1.writeShort(this.field_28045_g);
		}

	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleVehicleSpawn(this);
	}

	public int getPacketSize() {
		return 21 + this.field_28044_i > 0 ? 6 : 0;
	}
}
