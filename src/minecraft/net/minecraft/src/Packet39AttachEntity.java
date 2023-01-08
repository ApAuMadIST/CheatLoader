package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet39AttachEntity extends Packet {
	public int entityId;
	public int vehicleEntityId;

	public int getPacketSize() {
		return 8;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.entityId = dataInputStream1.readInt();
		this.vehicleEntityId = dataInputStream1.readInt();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.entityId);
		dataOutputStream1.writeInt(this.vehicleEntityId);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_6497_a(this);
	}
}
