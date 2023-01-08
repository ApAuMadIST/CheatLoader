package net.minecraft.src;

public class ItemShears extends Item {
	public ItemShears(int i1) {
		super(i1);
		this.setMaxStackSize(1);
		this.setMaxDamage(238);
	}

	public boolean onBlockDestroyed(ItemStack itemStack1, int i2, int i3, int i4, int i5, EntityLiving entityLiving6) {
		if(i2 == Block.leaves.blockID || i2 == Block.web.blockID) {
			itemStack1.damageItem(1, entityLiving6);
		}

		return super.onBlockDestroyed(itemStack1, i2, i3, i4, i5, entityLiving6);
	}

	public boolean canHarvestBlock(Block block1) {
		return block1.blockID == Block.web.blockID;
	}

	public float getStrVsBlock(ItemStack itemStack1, Block block2) {
		return block2.blockID != Block.web.blockID && block2.blockID != Block.leaves.blockID ? (block2.blockID == Block.cloth.blockID ? 5.0F : super.getStrVsBlock(itemStack1, block2)) : 15.0F;
	}
}
