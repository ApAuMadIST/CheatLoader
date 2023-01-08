package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {
	private String key = null;

	abstract void writeTagContents(DataOutput dataOutput1) throws IOException;

	abstract void readTagContents(DataInput dataInput1) throws IOException;

	public abstract byte getType();

	public String getKey() {
		return this.key == null ? "" : this.key;
	}

	public NBTBase setKey(String string1) {
		this.key = string1;
		return this;
	}

	public static NBTBase readTag(DataInput dataInput0) throws IOException {
		byte b1 = dataInput0.readByte();
		if(b1 == 0) {
			return new NBTTagEnd();
		} else {
			NBTBase nBTBase2 = createTagOfType(b1);
			nBTBase2.key = dataInput0.readUTF();
			nBTBase2.readTagContents(dataInput0);
			return nBTBase2;
		}
	}

	public static void writeTag(NBTBase nBTBase0, DataOutput dataOutput1) throws IOException {
		dataOutput1.writeByte(nBTBase0.getType());
		if(nBTBase0.getType() != 0) {
			dataOutput1.writeUTF(nBTBase0.getKey());
			nBTBase0.writeTagContents(dataOutput1);
		}
	}

	public static NBTBase createTagOfType(byte b0) {
		switch(b0) {
		case 0:
			return new NBTTagEnd();
		case 1:
			return new NBTTagByte();
		case 2:
			return new NBTTagShort();
		case 3:
			return new NBTTagInt();
		case 4:
			return new NBTTagLong();
		case 5:
			return new NBTTagFloat();
		case 6:
			return new NBTTagDouble();
		case 7:
			return new NBTTagByteArray();
		case 8:
			return new NBTTagString();
		case 9:
			return new NBTTagList();
		case 10:
			return new NBTTagCompound();
		default:
			return null;
		}
	}

	public static String getTagName(byte b0) {
		switch(b0) {
		case 0:
			return "TAG_End";
		case 1:
			return "TAG_Byte";
		case 2:
			return "TAG_Short";
		case 3:
			return "TAG_Int";
		case 4:
			return "TAG_Long";
		case 5:
			return "TAG_Float";
		case 6:
			return "TAG_Double";
		case 7:
			return "TAG_Byte_Array";
		case 8:
			return "TAG_String";
		case 9:
			return "TAG_List";
		case 10:
			return "TAG_Compound";
		default:
			return "UNKNOWN";
		}
	}
}
