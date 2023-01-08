package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet3Chat extends Packet {
	public String message;

	public Packet3Chat() {
	}

	public Packet3Chat(String string1) {
		if(string1.length() > 119) {
			string1 = string1.substring(0, 119);
		}

		this.message = string1;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.message = readString(dataInputStream1, 119);
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		writeString(this.message, dataOutputStream1);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleChat(this);
	}

	public int getPacketSize() {
		return this.message.length();
	}
}
