package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet2Handshake extends Packet {
	public String username;

	public Packet2Handshake() {
	}

	public Packet2Handshake(String string1) {
		this.username = string1;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.username = readString(dataInputStream1, 32);
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		writeString(this.username, dataOutputStream1);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleHandshake(this);
	}

	public int getPacketSize() {
		return 4 + this.username.length() + 4;
	}
}
