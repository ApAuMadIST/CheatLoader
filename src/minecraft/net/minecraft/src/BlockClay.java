package net.minecraft.src;

import java.util.Random;

public class BlockClay extends Block {
	public BlockClay(int i1, int i2) {
		super(i1, i2, Material.clay);
	}

	public int idDropped(int i1, Random random2) {
		return Item.clay.shiftedIndex;
	}

	public int quantityDropped(Random random1) {
		return 4;
	}
}
