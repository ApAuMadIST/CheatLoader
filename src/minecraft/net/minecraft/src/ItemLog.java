package net.minecraft.src;

public class ItemLog extends ItemBlock {
	public ItemLog(int i1) {
		super(i1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	public int getIconFromDamage(int i1) {
		return Block.wood.getBlockTextureFromSideAndMetadata(2, i1);
	}

	public int getPlacedBlockMetadata(int i1) {
		return i1;
	}
}
