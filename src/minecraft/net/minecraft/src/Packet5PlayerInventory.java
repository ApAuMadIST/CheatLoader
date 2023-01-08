package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet5PlayerInventory extends Packet {
	public int entityID;
	public int slot;
	public int itemID;
	public int itemDamage;

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.entityID = dataInputStream1.readInt();
		this.slot = dataInputStream1.readShort();
		this.itemID = dataInputStream1.readShort();
		this.itemDamage = dataInputStream1.readShort();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.entityID);
		dataOutputStream1.writeShort(this.slot);
		dataOutputStream1.writeShort(this.itemID);
		dataOutputStream1.writeShort(this.itemDamage);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handlePlayerInventory(this);
	}

	public int getPacketSize() {
		return 8;
	}
}
