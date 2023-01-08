package net.minecraft.src;

import java.util.Random;

public class BlockStone extends Block {
	public BlockStone(int i1, int i2) {
		super(i1, i2, Material.rock);
	}

	public int idDropped(int i1, Random random2) {
		return Block.cobblestone.blockID;
	}
}
