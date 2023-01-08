package net.minecraft.src;

public class ItemSlab extends ItemBlock {
	public ItemSlab(int i1) {
		super(i1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	public int getIconFromDamage(int i1) {
		return Block.stairSingle.getBlockTextureFromSideAndMetadata(2, i1);
	}

	public int getPlacedBlockMetadata(int i1) {
		return i1;
	}

	public String getItemNameIS(ItemStack itemStack1) {
		return super.getItemName() + "." + BlockStep.field_22037_a[itemStack1.getItemDamage()];
	}
}
