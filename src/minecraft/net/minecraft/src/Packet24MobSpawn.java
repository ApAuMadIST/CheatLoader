package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet24MobSpawn extends Packet {
	public int entityId;
	public byte type;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte yaw;
	public byte pitch;
	private DataWatcher metaData;
	private List receivedMetadata;

	public Packet24MobSpawn() {
	}

	public Packet24MobSpawn(EntityLiving entityLiving1) {
		this.entityId = entityLiving1.entityId;
		this.type = (byte)EntityList.getEntityID(entityLiving1);
		this.xPosition = MathHelper.floor_double(entityLiving1.posX * 32.0D);
		this.yPosition = MathHelper.floor_double(entityLiving1.posY * 32.0D);
		this.zPosition = MathHelper.floor_double(entityLiving1.posZ * 32.0D);
		this.yaw = (byte)((int)(entityLiving1.rotationYaw * 256.0F / 360.0F));
		this.pitch = (byte)((int)(entityLiving1.rotationPitch * 256.0F / 360.0F));
		this.metaData = entityLiving1.getDataWatcher();
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.entityId = dataInputStream1.readInt();
		this.type = dataInputStream1.readByte();
		this.xPosition = dataInputStream1.readInt();
		this.yPosition = dataInputStream1.readInt();
		this.zPosition = dataInputStream1.readInt();
		this.yaw = dataInputStream1.readByte();
		this.pitch = dataInputStream1.readByte();
		this.receivedMetadata = DataWatcher.readWatchableObjects(dataInputStream1);
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.entityId);
		dataOutputStream1.writeByte(this.type);
		dataOutputStream1.writeInt(this.xPosition);
		dataOutputStream1.writeInt(this.yPosition);
		dataOutputStream1.writeInt(this.zPosition);
		dataOutputStream1.writeByte(this.yaw);
		dataOutputStream1.writeByte(this.pitch);
		this.metaData.writeWatchableObjects(dataOutputStream1);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleMobSpawn(this);
	}

	public int getPacketSize() {
		return 20;
	}

	public List getMetadata() {
		return this.receivedMetadata;
	}
}
