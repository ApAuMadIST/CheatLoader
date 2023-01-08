package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet130UpdateSign extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public String[] signLines;

	public Packet130UpdateSign() {
		this.isChunkDataPacket = true;
	}

	public Packet130UpdateSign(int i1, int i2, int i3, String[] string4) {
		this.isChunkDataPacket = true;
		this.xPosition = i1;
		this.yPosition = i2;
		this.zPosition = i3;
		this.signLines = string4;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.xPosition = dataInputStream1.readInt();
		this.yPosition = dataInputStream1.readShort();
		this.zPosition = dataInputStream1.readInt();
		this.signLines = new String[4];

		for(int i2 = 0; i2 < 4; ++i2) {
			this.signLines[i2] = readString(dataInputStream1, 15);
		}

	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.xPosition);
		dataOutputStream1.writeShort(this.yPosition);
		dataOutputStream1.writeInt(this.zPosition);

		for(int i2 = 0; i2 < 4; ++i2) {
			writeString(this.signLines[i2], dataOutputStream1);
		}

	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleSignUpdate(this);
	}

	public int getPacketSize() {
		int i1 = 0;

		for(int i2 = 0; i2 < 4; ++i2) {
			i1 += this.signLines[i2].length();
		}

		return i1;
	}
}
