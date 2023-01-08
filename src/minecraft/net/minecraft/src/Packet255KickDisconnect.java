package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet255KickDisconnect extends Packet {
	public String reason;

	public Packet255KickDisconnect() {
	}

	public Packet255KickDisconnect(String string1) {
		this.reason = string1;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.reason = readString(dataInputStream1, 100);
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		writeString(this.reason, dataOutputStream1);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleKickDisconnect(this);
	}

	public int getPacketSize() {
		return this.reason.length();
	}
}
