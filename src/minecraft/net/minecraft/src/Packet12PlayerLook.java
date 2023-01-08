package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet12PlayerLook extends Packet10Flying {
	public Packet12PlayerLook() {
		this.rotating = true;
	}

	public Packet12PlayerLook(float f1, float f2, boolean z3) {
		this.yaw = f1;
		this.pitch = f2;
		this.onGround = z3;
		this.rotating = true;
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.yaw = dataInputStream1.readFloat();
		this.pitch = dataInputStream1.readFloat();
		super.readPacketData(dataInputStream1);
	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeFloat(this.yaw);
		dataOutputStream1.writeFloat(this.pitch);
		super.writePacketData(dataOutputStream1);
	}

	public int getPacketSize() {
		return 9;
	}
}
