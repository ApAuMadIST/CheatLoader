package net.minecraft.src;

public class SlotFurnace extends Slot {
	private EntityPlayer thePlayer;

	public SlotFurnace(EntityPlayer paramgs, IInventory paramlw, int paramInt1, int paramInt2, int paramInt3) {
		super(paramlw, paramInt1, paramInt2, paramInt3);
		this.thePlayer = paramgs;
	}

	public boolean isItemValid(ItemStack paramiz) {
		return false;
	}

	public void onPickupFromSlot(ItemStack paramiz) {
		paramiz.onCrafting(this.thePlayer.worldObj, this.thePlayer);
		if(paramiz.itemID == Item.ingotIron.shiftedIndex) {
			this.thePlayer.addStat(AchievementList.acquireIron, 1);
		}

		if(paramiz.itemID == Item.fishCooked.shiftedIndex) {
			this.thePlayer.addStat(AchievementList.cookFish, 1);
		}

		ModLoader.TakenFromFurnace(this.thePlayer, paramiz);
		super.onPickupFromSlot(paramiz);
	}
}
