package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet105UpdateProgressbar extends Packet {
	public int windowId;
	public int progressBar;
	public int progressBarValue;

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_20090_a(this);
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.windowId = dataInputStream1.readByte();
		this.progressBar = dataInputStream1.readShort();
		this.progressBarValue = dataInputStream1.readShort();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeByte(this.windowId);
		dataOutputStream1.writeShort(this.progressBar);
		dataOutputStream1.writeShort(this.progressBarValue);
	}

	public int getPacketSize() {
		return 5;
	}
}
