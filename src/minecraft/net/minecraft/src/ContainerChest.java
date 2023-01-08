package net.minecraft.src;

public class ContainerChest extends Container {
	private IInventory field_20125_a;
	private int field_27282_b;

	public ContainerChest(IInventory iInventory1, IInventory iInventory2) {
		this.field_20125_a = iInventory2;
		this.field_27282_b = iInventory2.getSizeInventory() / 9;
		int i3 = (this.field_27282_b - 4) * 18;

		int i4;
		int i5;
		for(i4 = 0; i4 < this.field_27282_b; ++i4) {
			for(i5 = 0; i5 < 9; ++i5) {
				this.addSlot(new Slot(iInventory2, i5 + i4 * 9, 8 + i5 * 18, 18 + i4 * 18));
			}
		}

		for(i4 = 0; i4 < 3; ++i4) {
			for(i5 = 0; i5 < 9; ++i5) {
				this.addSlot(new Slot(iInventory1, i5 + i4 * 9 + 9, 8 + i5 * 18, 103 + i4 * 18 + i3));
			}
		}

		for(i4 = 0; i4 < 9; ++i4) {
			this.addSlot(new Slot(iInventory1, i4, 8 + i4 * 18, 161 + i3));
		}

	}

	public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
		return this.field_20125_a.canInteractWith(entityPlayer1);
	}

	public ItemStack getStackInSlot(int i1) {
		ItemStack itemStack2 = null;
		Slot slot3 = (Slot)this.slots.get(i1);
		if(slot3 != null && slot3.getHasStack()) {
			ItemStack itemStack4 = slot3.getStack();
			itemStack2 = itemStack4.copy();
			if(i1 < this.field_27282_b * 9) {
				this.func_28125_a(itemStack4, this.field_27282_b * 9, this.slots.size(), true);
			} else {
				this.func_28125_a(itemStack4, 0, this.field_27282_b * 9, false);
			}

			if(itemStack4.stackSize == 0) {
				slot3.putStack((ItemStack)null);
			} else {
				slot3.onSlotChanged();
			}
		}

		return itemStack2;
	}
}
