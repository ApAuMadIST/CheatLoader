package net.minecraft.src;

public class TileEntityChest extends TileEntity implements IInventory {
	private ItemStack[] chestContents = new ItemStack[36];

	public int getSizeInventory() {
		return 27;
	}

	public ItemStack getStackInSlot(int i1) {
		return this.chestContents[i1];
	}

	public ItemStack decrStackSize(int i1, int i2) {
		if(this.chestContents[i1] != null) {
			ItemStack itemStack3;
			if(this.chestContents[i1].stackSize <= i2) {
				itemStack3 = this.chestContents[i1];
				this.chestContents[i1] = null;
				this.onInventoryChanged();
				return itemStack3;
			} else {
				itemStack3 = this.chestContents[i1].splitStack(i2);
				if(this.chestContents[i1].stackSize == 0) {
					this.chestContents[i1] = null;
				}

				this.onInventoryChanged();
				return itemStack3;
			}
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i1, ItemStack itemStack2) {
		this.chestContents[i1] = itemStack2;
		if(itemStack2 != null && itemStack2.stackSize > this.getInventoryStackLimit()) {
			itemStack2.stackSize = this.getInventoryStackLimit();
		}

		this.onInventoryChanged();
	}

	public String getInvName() {
		return "Chest";
	}

	public void readFromNBT(NBTTagCompound nBTTagCompound1) {
		super.readFromNBT(nBTTagCompound1);
		NBTTagList nBTTagList2 = nBTTagCompound1.getTagList("Items");
		this.chestContents = new ItemStack[this.getSizeInventory()];

		for(int i3 = 0; i3 < nBTTagList2.tagCount(); ++i3) {
			NBTTagCompound nBTTagCompound4 = (NBTTagCompound)nBTTagList2.tagAt(i3);
			int i5 = nBTTagCompound4.getByte("Slot") & 255;
			if(i5 >= 0 && i5 < this.chestContents.length) {
				this.chestContents[i5] = new ItemStack(nBTTagCompound4);
			}
		}

	}

	public void writeToNBT(NBTTagCompound nBTTagCompound1) {
		super.writeToNBT(nBTTagCompound1);
		NBTTagList nBTTagList2 = new NBTTagList();

		for(int i3 = 0; i3 < this.chestContents.length; ++i3) {
			if(this.chestContents[i3] != null) {
				NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
				nBTTagCompound4.setByte("Slot", (byte)i3);
				this.chestContents[i3].writeToNBT(nBTTagCompound4);
				nBTTagList2.setTag(nBTTagCompound4);
			}
		}

		nBTTagCompound1.setTag("Items", nBTTagList2);
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean canInteractWith(EntityPlayer entityPlayer1) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPlayer1.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
}
