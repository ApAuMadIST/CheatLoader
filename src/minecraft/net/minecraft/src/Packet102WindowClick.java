package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet102WindowClick extends Packet {
	public int window_Id;
	public int inventorySlot;
	public int mouseClick;
	public short action;
	public ItemStack itemStack;
	public boolean field_27050_f;

	public Packet102WindowClick() {
	}

	public Packet102WindowClick(int i1, int i2, int i3, boolean z4, ItemStack itemStack5, short s6) {
		this.window_Id = i1;
		this.inventorySlot = i2;
		this.mouseClick = i3;
		this.itemStack = itemStack5;
		this.action = s6;
		this.field_27050_f = z4;
	}

	public void processPacket(NetHandler netHandler1) {
		netHandler1.func_20091_a(this);
	}

	public void readPacketData(DataInputStream dataInputStream1) throws IOException {
		this.window_Id = dataInputStream1.readByte();
		this.inventorySlot = dataInputStream1.readShort();
		this.mouseClick = dataInputStream1.readByte();
		this.action = dataInputStream1.readShort();
		this.field_27050_f = dataInputStream1.readBoolean();
		short s2 = dataInputStream1.readShort();
		if(s2 >= 0) {
			byte b3 = dataInputStream1.readByte();
			short s4 = dataInputStream1.readShort();
			this.itemStack = new ItemStack(s2, b3, s4);
		} else {
			this.itemStack = null;
		}

	}

	public void writePacketData(DataOutputStream dataOutputStream1) throws IOException {
		dataOutputStream1.writeByte(this.window_Id);
		dataOutputStream1.writeShort(this.inventorySlot);
		dataOutputStream1.writeByte(this.mouseClick);
		dataOutputStream1.writeShort(this.action);
		dataOutputStream1.writeBoolean(this.field_27050_f);
		if(this.itemStack == null) {
			dataOutputStream1.writeShort(-1);
		} else {
			dataOutputStream1.writeShort(this.itemStack.itemID);
			dataOutputStream1.writeByte(this.itemStack.stackSize);
			dataOutputStream1.writeShort(this.itemStack.getItemDamage());
		}

	}

	public int getPacketSize() {
		return 11;
	}
}
