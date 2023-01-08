package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Packet60Explosion extends Packet {
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public float explosionSize;
	public Set destroyedBlockPositions;

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.explosionX = dataInputStream1.readDouble();
		this.explosionY = dataInputStream1.readDouble();
		this.explosionZ = dataInputStream1.readDouble();
		this.explosionSize = dataInputStream1.readFloat();
		int i2 = dataInputStream1.readInt();
		this.destroyedBlockPositions = new HashSet();
		int i3 = (int)this.explosionX;
		int i4 = (int)this.explosionY;
		int i5 = (int)this.explosionZ;

		for(int i6 = 0; i6 < i2; ++i6) {
			int i7 = dataInputStream1.readByte() + i3;
			int i8 = dataInputStream1.readByte() + i4;
			int i9 = dataInputStream1.readByte() + i5;
			this.destroyedBlockPositions.add(new ChunkPosition(i7, i8, i9));
		}

	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeDouble(this.explosionX);
		dataOutputStream1.writeDouble(this.explosionY);
		dataOutputStream1.writeDouble(this.explosionZ);
		dataOutputStream1.writeFloat(this.explosionSize);
		dataOutputStream1.writeInt(this.destroyedBlockPositions.size());
		int i2 = (int)this.explosionX;
		int i3 = (int)this.explosionY;
		int i4 = (int)this.explosionZ;
		Iterator iterator5 = this.destroyedBlockPositions.iterator();

		while(iterator5.hasNext()) {
			ChunkPosition chunkPosition6 = (ChunkPosition)iterator5.next();
			int i7 = chunkPosition6.x - i2;
			int i8 = chunkPosition6.y - i3;
			int i9 = chunkPosition6.z - i4;
			dataOutputStream1.writeByte(i7);
			dataOutputStream1.writeByte(i8);
			dataOutputStream1.writeByte(i9);
		}

	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_12245_a(this);
	}

	public int getPacketSize() {
		return 32 + this.destroyedBlockPositions.size() * 3;
	}
}
