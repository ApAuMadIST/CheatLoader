package net.minecraft.src;

public class ContainerFurnace extends Container {
	private TileEntityFurnace furnace;
	private int cookTime = 0;
	private int burnTime = 0;
	private int itemBurnTime = 0;

	public ContainerFurnace(InventoryPlayer inventoryPlayer1, TileEntityFurnace tileEntityFurnace2) {
		this.furnace = tileEntityFurnace2;
		this.addSlot(new Slot(tileEntityFurnace2, 0, 56, 17));
		this.addSlot(new Slot(tileEntityFurnace2, 1, 56, 53));
		this.addSlot(new SlotFurnace(inventoryPlayer1.player, tileEntityFurnace2, 2, 116, 35));

		int i3;
		for(i3 = 0; i3 < 3; ++i3) {
			for(int i4 = 0; i4 < 9; ++i4) {
				this.addSlot(new Slot(inventoryPlayer1, i4 + i3 * 9 + 9, 8 + i4 * 18, 84 + i3 * 18));
			}
		}

		for(i3 = 0; i3 < 9; ++i3) {
			this.addSlot(new Slot(inventoryPlayer1, i3, 8 + i3 * 18, 142));
		}

	}

	public void updateCraftingResults() {
		super.updateCraftingResults();

		for(int i1 = 0; i1 < this.field_20121_g.size(); ++i1) {
			ICrafting iCrafting2 = (ICrafting)this.field_20121_g.get(i1);
			if(this.cookTime != this.furnace.furnaceCookTime) {
				iCrafting2.func_20158_a(this, 0, this.furnace.furnaceCookTime);
			}

			if(this.burnTime != this.furnace.furnaceBurnTime) {
				iCrafting2.func_20158_a(this, 1, this.furnace.furnaceBurnTime);
			}

			if(this.itemBurnTime != this.furnace.currentItemBurnTime) {
				iCrafting2.func_20158_a(this, 2, this.furnace.currentItemBurnTime);
			}
		}

		this.cookTime = this.furnace.furnaceCookTime;
		this.burnTime = this.furnace.furnaceBurnTime;
		this.itemBurnTime = this.furnace.currentItemBurnTime;
	}

	public void func_20112_a(int i1, int i2) {
		if(i1 == 0) {
			this.furnace.furnaceCookTime = i2;
		}

		if(i1 == 1) {
			this.furnace.furnaceBurnTime = i2;
		}

		if(i1 == 2) {
			this.furnace.currentItemBurnTime = i2;
		}

	}

	public boolean isUsableByPlayer(EntityPlayer entityPlayer1) {
		return this.furnace.canInteractWith(entityPlayer1);
	}

	public ItemStack getStackInSlot(int i1) {
		ItemStack itemStack2 = null;
		Slot slot3 = (Slot)this.slots.get(i1);
		if(slot3 != null && slot3.getHasStack()) {
			ItemStack itemStack4 = slot3.getStack();
			itemStack2 = itemStack4.copy();
			if(i1 == 2) {
				this.func_28125_a(itemStack4, 3, 39, true);
			} else if(i1 >= 3 && i1 < 30) {
				this.func_28125_a(itemStack4, 30, 39, false);
			} else if(i1 >= 30 && i1 < 39) {
				this.func_28125_a(itemStack4, 3, 30, false);
			} else {
				this.func_28125_a(itemStack4, 3, 39, false);
			}

			if(itemStack4.stackSize == 0) {
				slot3.putStack((ItemStack)null);
			} else {
				slot3.onSlotChanged();
			}

			if(itemStack4.stackSize == itemStack2.stackSize) {
				return null;
			}

			slot3.onPickupFromSlot(itemStack4);
		}

		return itemStack2;
	}
}
