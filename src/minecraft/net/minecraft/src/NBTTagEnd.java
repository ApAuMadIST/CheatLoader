package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {
	void readTagContents(DataInput dataInput1) throws IOException {
	}

	void writeTagContents(DataOutput dataOutput1) throws IOException {
	}

	public byte getType() {
		return (byte)0;
	}

	public String toString() {
		return "END";
	}
}
