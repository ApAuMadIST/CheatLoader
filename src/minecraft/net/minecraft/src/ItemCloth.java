package net.minecraft.src;

public class ItemCloth extends ItemBlock {
	public ItemCloth(int i1) {
		super(i1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	public int getIconFromDamage(int i1) {
		return Block.cloth.getBlockTextureFromSideAndMetadata(2, BlockCloth.func_21034_c(i1));
	}

	public int getPlacedBlockMetadata(int i1) {
		return i1;
	}

	public String getItemNameIS(ItemStack itemStack1) {
		return super.getItemName() + "." + ItemDye.dyeColors[BlockCloth.func_21034_c(itemStack1.getItemDamage())];
	}
}
