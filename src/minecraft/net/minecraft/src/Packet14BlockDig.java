package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet14BlockDig extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int face;
	public int status;

	public Packet14BlockDig() {
	}

	public Packet14BlockDig(int i1, int i2, int i3, int i4, int i5) {
		this.status = i1;
		this.xPosition = i2;
		this.yPosition = i3;
		this.zPosition = i4;
		this.face = i5;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.status = dataInputStream1.read();
		this.xPosition = dataInputStream1.readInt();
		this.yPosition = dataInputStream1.read();
		this.zPosition = dataInputStream1.readInt();
		this.face = dataInputStream1.read();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.write(this.status);
		dataOutputStream1.writeInt(this.xPosition);
		dataOutputStream1.write(this.yPosition);
		dataOutputStream1.writeInt(this.zPosition);
		dataOutputStream1.write(this.face);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleBlockDig(this);
	}

	public int getPacketSize() {
		return 11;
	}
}
