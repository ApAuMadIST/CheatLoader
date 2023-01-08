package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet21PickupSpawn extends Packet {
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte rotation;
	public byte pitch;
	public byte roll;
	public int itemID;
	public int count;
	public int itemDamage;

	public Packet21PickupSpawn() {
	}

	public Packet21PickupSpawn(EntityItem entityItem1) {
		this.entityId = entityItem1.entityId;
		this.itemID = entityItem1.item.itemID;
		this.count = entityItem1.item.stackSize;
		this.itemDamage = entityItem1.item.getItemDamage();
		this.xPosition = MathHelper.floor_double(entityItem1.posX * 32.0D);
		this.yPosition = MathHelper.floor_double(entityItem1.posY * 32.0D);
		this.zPosition = MathHelper.floor_double(entityItem1.posZ * 32.0D);
		this.rotation = (byte)((int)(entityItem1.motionX * 128.0D));
		this.pitch = (byte)((int)(entityItem1.motionY * 128.0D));
		this.roll = (byte)((int)(entityItem1.motionZ * 128.0D));
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.entityId = dataInputStream1.readInt();
		this.itemID = dataInputStream1.readShort();
		this.count = dataInputStream1.readByte();
		this.itemDamage = dataInputStream1.readShort();
		this.xPosition = dataInputStream1.readInt();
		this.yPosition = dataInputStream1.readInt();
		this.zPosition = dataInputStream1.readInt();
		this.rotation = dataInputStream1.readByte();
		this.pitch = dataInputStream1.readByte();
		this.roll = dataInputStream1.readByte();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.entityId);
		dataOutputStream1.writeShort(this.itemID);
		dataOutputStream1.writeByte(this.count);
		dataOutputStream1.writeShort(this.itemDamage);
		dataOutputStream1.writeInt(this.xPosition);
		dataOutputStream1.writeInt(this.yPosition);
		dataOutputStream1.writeInt(this.zPosition);
		dataOutputStream1.writeByte(this.rotation);
		dataOutputStream1.writeByte(this.pitch);
		dataOutputStream1.writeByte(this.roll);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handlePickupSpawn(this);
	}

	public int getPacketSize() {
		return 24;
	}
}
