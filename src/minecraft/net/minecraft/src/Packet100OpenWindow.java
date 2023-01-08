package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet100OpenWindow extends Packet {
	public int windowId;
	public int inventoryType;
	public String windowTitle;
	public int slotsCount;

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_20087_a(this);
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.windowId = dataInputStream1.readByte();
		this.inventoryType = dataInputStream1.readByte();
		this.windowTitle = dataInputStream1.readUTF();
		this.slotsCount = dataInputStream1.readByte();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeByte(this.windowId);
		dataOutputStream1.writeByte(this.inventoryType);
		dataOutputStream1.writeUTF(this.windowTitle);
		dataOutputStream1.writeByte(this.slotsCount);
	}

	public int getPacketSize() {
		return 3 + this.windowTitle.length();
	}
}
