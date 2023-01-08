package net.minecraft.src;

import java.util.List;

public class InventoryBasic implements IInventory {
	private String inventoryTitle;
	private int slotsCount;
	private ItemStack[] inventoryContents;
	private List field_20073_d;

	public InventoryBasic(String string1, int i2) {
		this.inventoryTitle = string1;
		this.slotsCount = i2;
		this.inventoryContents = new ItemStack[i2];
	}

	public ItemStack getStackInSlot(int i1) {
		return this.inventoryContents[i1];
	}

	public ItemStack decrStackSize(int i1, int i2) {
		if(this.inventoryContents[i1] != null) {
			ItemStack itemStack3;
			if(this.inventoryContents[i1].stackSize <= i2) {
				itemStack3 = this.inventoryContents[i1];
				this.inventoryContents[i1] = null;
				this.onInventoryChanged();
				return itemStack3;
			} else {
				itemStack3 = this.inventoryContents[i1].splitStack(i2);
				if(this.inventoryContents[i1].stackSize == 0) {
					this.inventoryContents[i1] = null;
				}

				this.onInventoryChanged();
				return itemStack3;
			}
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i1, ItemStack itemStack2) {
		this.inventoryContents[i1] = itemStack2;
		if(itemStack2 != null && itemStack2.stackSize > this.getInventoryStackLimit()) {
			itemStack2.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	public int getSizeInventory() {
		return this.slotsCount;
	}

	public String getInvName() {
		return this.inventoryTitle;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void onInventoryChanged() {
		if(this.field_20073_d != null) {
			for(int i1 = 0; i1 < this.field_20073_d.size(); ++i1) {
				((IInvBasic)this.field_20073_d.get(i1)).func_20134_a(this);
			}
		}

	}

	public boolean canInteractWith(EntityPlayer entityPlayer1) {
		return true;
	}
}
