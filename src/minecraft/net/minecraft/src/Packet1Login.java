package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet1Login extends Packet {
	public int protocolVersion;
	public String username;
	public long mapSeed;
	public byte dimension;

	public Packet1Login() {
	}

	public Packet1Login(String string1, int i2) {
		this.username = string1;
		this.protocolVersion = i2;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.protocolVersion = dataInputStream1.readInt();
		this.username = readString(dataInputStream1, 16);
		this.mapSeed = dataInputStream1.readLong();
		this.dimension = dataInputStream1.readByte();
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeInt(this.protocolVersion);
		writeString(this.username, dataOutputStream1);
		dataOutputStream1.writeLong(this.mapSeed);
		dataOutputStream1.writeByte(this.dimension);
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.handleLogin(this);
	}

	public int getPacketSize() {
		return 4 + this.username.length() + 4 + 5;
	}
}
