package net.minecraft.src;

import java.util.Random;

public class BlockMushroom extends BlockFlower {
	protected BlockMushroom(int i1, int i2) {
		super(i1, i2);
		float f3 = 0.2F;
		this.setBlockBounds(0.5F - f3, 0.0F, 0.5F - f3, 0.5F + f3, f3 * 2.0F, 0.5F + f3);
		this.setTickOnLoad(true);
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		if(random5.nextInt(100) == 0) {
			int i6 = i2 + random5.nextInt(3) - 1;
			int i7 = i3 + random5.nextInt(2) - random5.nextInt(2);
			int i8 = i4 + random5.nextInt(3) - 1;
			if(world1.isAirBlock(i6, i7, i8) && this.canBlockStay(world1, i6, i7, i8)) {
				int i10000 = i2 + (random5.nextInt(3) - 1);
				i10000 = i4 + (random5.nextInt(3) - 1);
				if(world1.isAirBlock(i6, i7, i8) && this.canBlockStay(world1, i6, i7, i8)) {
					world1.setBlockWithNotify(i6, i7, i8, this.blockID);
				}
			}
		}

	}

	protected boolean canThisPlantGrowOnThisBlockID(int i1) {
		return Block.opaqueCubeLookup[i1];
	}

	public boolean canBlockStay(World world1, int i2, int i3, int i4) {
		return i3 >= 0 && i3 < 128 ? world1.getFullBlockLightValue(i2, i3, i4) < 13 && this.canThisPlantGrowOnThisBlockID(world1.getBlockId(i2, i3 - 1, i4)) : false;
	}
}
