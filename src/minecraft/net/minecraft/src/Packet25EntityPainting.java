package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet25EntityPainting extends Packet {
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int direction;
	public String title;

	public Packet25EntityPainting() {
	}

	public Packet25EntityPainting(EntityPainting entityPainting1) {
		this.entityId = entityPainting1.entityId;
		this.xPosition = entityPainting1.xPosition;
		this.yPosition = entityPainting1.yPosition;
		this.zPosition = entityPainting1.zPosition;
		this.direction = entityPainting1.direction;
		this.title = entityPainting1.art.title;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.entityId = dataInputStream1.readInt();
		this.title = readString(dataInputStream1, EnumArt.maxArtTitleLength);
		this.xPosition = dataInputStream1.readInt();
		this.yPosition = dataInputStream1.readInt();
		this.zPosition = dataInputStream1.readInt();
		this.direction = dataInputStream1.readInt();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.entityId);
		writeString(this.title, dataOutputStream1);
		dataOutputStream1.writeInt(this.xPosition);
		dataOutputStream1.writeInt(this.yPosition);
		dataOutputStream1.writeInt(this.zPosition);
		dataOutputStream1.writeInt(this.direction);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_21146_a(this);
	}

	public int getPacketSize() {
		return 24;
	}
}
