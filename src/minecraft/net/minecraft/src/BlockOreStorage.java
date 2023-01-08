package net.minecraft.src;

public class BlockOreStorage extends Block {
	public BlockOreStorage(int i1, int i2) {
		super(i1, Material.iron);
		this.blockIndexInTexture = i2;
	}

	public int getBlockTextureFromSide(int i1) {
		return this.blockIndexInTexture;
	}
}
