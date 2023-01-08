package net.minecraft.src;

import java.util.Random;

public class BlockBookshelf extends Block {
	public BlockBookshelf(int i1, int i2) {
		super(i1, i2, Material.wood);
	}

	public int getBlockTextureFromSide(int i1) {
		return i1 <= 1 ? 4 : this.blockIndexInTexture;
	}

	public int quantityDropped(Random random1) {
		return 0;
	}
}
