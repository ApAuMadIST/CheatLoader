package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet38EntityStatus extends Packet {
	public int entityId;
	public byte entityStatus;

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.entityId = dataInputStream1.readInt();
		this.entityStatus = dataInputStream1.readByte();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.entityId);
		dataOutputStream1.writeByte(this.entityStatus);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_9447_a(this);
	}

	public int getPacketSize() {
		return 5;
	}
}
