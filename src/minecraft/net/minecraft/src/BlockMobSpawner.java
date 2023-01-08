package net.minecraft.src;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer {
	protected BlockMobSpawner(int i1, int i2) {
		super(i1, i2, Material.rock);
	}

	protected TileEntity getBlockEntity() {
		return new TileEntityMobSpawner();
	}

	public int idDropped(int i1, Random random2) {
		return 0;
	}

	public int quantityDropped(Random random1) {
		return 0;
	}

	public boolean isOpaqueCube() {
		return false;
	}
}
