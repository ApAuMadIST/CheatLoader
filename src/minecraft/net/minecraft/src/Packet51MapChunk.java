package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class Packet51MapChunk extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int xSize;
	public int ySize;
	public int zSize;
	public byte[] chunk;
	private int chunkSize;

	public Packet51MapChunk() {
		this.isChunkDataPacket = true;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.xPosition = dataInputStream1.readInt();
		this.yPosition = dataInputStream1.readShort();
		this.zPosition = dataInputStream1.readInt();
		this.xSize = dataInputStream1.read() + 1;
		this.ySize = dataInputStream1.read() + 1;
		this.zSize = dataInputStream1.read() + 1;
		this.chunkSize = dataInputStream1.readInt();
		byte[] b2 = new byte[this.chunkSize];
		dataInputStream1.readFully(b2);
		this.chunk = new byte[this.xSize * this.ySize * this.zSize * 5 / 2];
		Inflater inflater3 = new Inflater();
		inflater3.setInput(b2);

		try {
			inflater3.inflate(this.chunk);
		} catch (DataFormatException dataFormatException8) {
			throw new IOException("Bad compressed data format");
		} finally {
			inflater3.end();
		}

	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.xPosition);
		dataOutputStream1.writeShort(this.yPosition);
		dataOutputStream1.writeInt(this.zPosition);
		dataOutputStream1.write(this.xSize - 1);
		dataOutputStream1.write(this.ySize - 1);
		dataOutputStream1.write(this.zSize - 1);
		dataOutputStream1.writeInt(this.chunkSize);
		dataOutputStream1.write(this.chunk, 0, this.chunkSize);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleMapChunk(this);
	}

	public int getPacketSize() {
		return 17 + this.chunkSize;
	}
}
