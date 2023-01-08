package net.minecraft.src;

import java.util.Random;

public class BlockDeadBush extends BlockFlower {
	protected BlockDeadBush(int i1, int i2) {
		super(i1, i2);
		float f3 = 0.4F;
		this.setBlockBounds(0.5F - f3, 0.0F, 0.5F - f3, 0.5F + f3, 0.8F, 0.5F + f3);
	}

	protected boolean canThisPlantGrowOnThisBlockID(int i1) {
		return i1 == Block.sand.blockID;
	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		return this.blockIndexInTexture;
	}

	public int idDropped(int i1, Random random2) {
		return -1;
	}
}
