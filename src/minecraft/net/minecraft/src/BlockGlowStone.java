package net.minecraft.src;

import java.util.Random;

public class BlockGlowStone extends Block {
	public BlockGlowStone(int i1, int i2, Material material3) {
		super(i1, i2, material3);
	}

	public int quantityDropped(Random random1) {
		return 2 + random1.nextInt(3);
	}

	public int idDropped(int i1, Random random2) {
		return Item.lightStoneDust.shiftedIndex;
	}
}
