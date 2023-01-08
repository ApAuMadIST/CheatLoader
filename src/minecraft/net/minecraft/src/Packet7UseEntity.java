package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet7UseEntity extends Packet {
	public int playerEntityId;
	public int targetEntity;
	public int isLeftClick;

	public Packet7UseEntity() {
	}

	public Packet7UseEntity(int i1, int i2, int i3) {
		this.playerEntityId = i1;
		this.targetEntity = i2;
		this.isLeftClick = i3;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.playerEntityId = dataInputStream1.readInt();
		this.targetEntity = dataInputStream1.readInt();
		this.isLeftClick = dataInputStream1.readByte();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.playerEntityId);
		dataOutputStream1.writeInt(this.targetEntity);
		dataOutputStream1.writeByte(this.isLeftClick);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleUseEntity(this);
	}

	public int getPacketSize() {
		return 9;
	}
}
