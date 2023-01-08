package net.minecraft.src;

public class InventoryCrafting implements IInventory {
	private ItemStack[] stackList;
	private int field_21104_b;
	private Container eventHandler;

	public InventoryCrafting(Container container1, int i2, int i3) {
		int i4 = i2 * i3;
		this.stackList = new ItemStack[i4];
		this.eventHandler = container1;
		this.field_21104_b = i2;
	}

	public int getSizeInventory() {
		return this.stackList.length;
	}

	public ItemStack getStackInSlot(int i1) {
		return i1 >= this.getSizeInventory() ? null : this.stackList[i1];
	}

	public ItemStack func_21103_b(int i1, int i2) {
		if(i1 >= 0 && i1 < this.field_21104_b) {
			int i3 = i1 + i2 * this.field_21104_b;
			return this.getStackInSlot(i3);
		} else {
			return null;
		}
	}

	public String getInvName() {
		return "Crafting";
	}

	public ItemStack decrStackSize(int i1, int i2) {
		if(this.stackList[i1] != null) {
			ItemStack itemStack3;
			if(this.stackList[i1].stackSize <= i2) {
				itemStack3 = this.stackList[i1];
				this.stackList[i1] = null;
				this.eventHandler.onCraftMatrixChanged(this);
				return itemStack3;
			} else {
				itemStack3 = this.stackList[i1].splitStack(i2);
				if(this.stackList[i1].stackSize == 0) {
					this.stackList[i1] = null;
				}

				this.eventHandler.onCraftMatrixChanged(this);
				return itemStack3;
			}
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i1, ItemStack itemStack2) {
		this.stackList[i1] = itemStack2;
		this.eventHandler.onCraftMatrixChanged(this);
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void onInventoryChanged() {
	}

	public boolean canInteractWith(EntityPlayer entityPlayer1) {
		return true;
	}
}
