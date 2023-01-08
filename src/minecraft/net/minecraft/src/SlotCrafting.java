package net.minecraft.src;

public class SlotCrafting extends Slot {
	private final IInventory craftMatrix;
	private EntityPlayer thePlayer;

	public SlotCrafting(EntityPlayer paramgs, IInventory paramlw1, IInventory paramlw2, int paramInt1, int paramInt2, int paramInt3) {
		super(paramlw2, paramInt1, paramInt2, paramInt3);
		this.thePlayer = paramgs;
		this.craftMatrix = paramlw1;
	}

	public boolean isItemValid(ItemStack paramiz) {
		return false;
	}

	public void onPickupFromSlot(ItemStack paramiz) {
		paramiz.onCrafting(this.thePlayer.worldObj, this.thePlayer);
		if(paramiz.itemID == Block.workbench.blockID) {
			this.thePlayer.addStat(AchievementList.buildWorkBench, 1);
		} else if(paramiz.itemID == Item.pickaxeWood.shiftedIndex) {
			this.thePlayer.addStat(AchievementList.buildPickaxe, 1);
		} else if(paramiz.itemID == Block.stoneOvenIdle.blockID) {
			this.thePlayer.addStat(AchievementList.buildFurnace, 1);
		} else if(paramiz.itemID == Item.hoeWood.shiftedIndex) {
			this.thePlayer.addStat(AchievementList.buildHoe, 1);
		} else if(paramiz.itemID == Item.bread.shiftedIndex) {
			this.thePlayer.addStat(AchievementList.makeBread, 1);
		} else if(paramiz.itemID == Item.cake.shiftedIndex) {
			this.thePlayer.addStat(AchievementList.bakeCake, 1);
		} else if(paramiz.itemID == Item.pickaxeStone.shiftedIndex) {
			this.thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
		} else if(paramiz.itemID == Item.swordWood.shiftedIndex) {
			this.thePlayer.addStat(AchievementList.buildSword, 1);
		}

		ModLoader.TakenFromCrafting(this.thePlayer, paramiz);

		for(int i = 0; i < this.craftMatrix.getSizeInventory(); ++i) {
			ItemStack localiz = this.craftMatrix.getStackInSlot(i);
			if(localiz != null) {
				this.craftMatrix.decrStackSize(i, 1);
				if(localiz.getItem().hasContainerItem()) {
					this.craftMatrix.setInventorySlotContents(i, new ItemStack(localiz.getItem().getContainerItem()));
				}
			}
		}

	}
}
