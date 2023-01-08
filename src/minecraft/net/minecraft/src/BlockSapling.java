package net.minecraft.src;

import java.util.Random;

public class BlockSapling extends BlockFlower {
	protected BlockSapling(int i1, int i2) {
		super(i1, i2);
		float f3 = 0.4F;
		this.setBlockBounds(0.5F - f3, 0.0F, 0.5F - f3, 0.5F + f3, f3 * 2.0F, 0.5F + f3);
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		if(!world1.multiplayerWorld) {
			super.updateTick(world1, i2, i3, i4, random5);
			if(world1.getBlockLightValue(i2, i3 + 1, i4) >= 9 && random5.nextInt(30) == 0) {
				int i6 = world1.getBlockMetadata(i2, i3, i4);
				if((i6 & 8) == 0) {
					world1.setBlockMetadataWithNotify(i2, i3, i4, i6 | 8);
				} else {
					this.growTree(world1, i2, i3, i4, random5);
				}
			}

		}
	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		i2 &= 3;
		return i2 == 1 ? 63 : (i2 == 2 ? 79 : super.getBlockTextureFromSideAndMetadata(i1, i2));
	}

	public void growTree(World world1, int i2, int i3, int i4, Random random5) {
		int i6 = world1.getBlockMetadata(i2, i3, i4) & 3;
		world1.setBlock(i2, i3, i4, 0);
		Object object7 = null;
		if(i6 == 1) {
			object7 = new WorldGenTaiga2();
		} else if(i6 == 2) {
			object7 = new WorldGenForest();
		} else {
			object7 = new WorldGenTrees();
			if(random5.nextInt(10) == 0) {
				object7 = new WorldGenBigTree();
			}
		}

		if(!((WorldGenerator)object7).generate(world1, random5, i2, i3, i4)) {
			world1.setBlockAndMetadata(i2, i3, i4, this.blockID, i6);
		}

	}

	protected int damageDropped(int i1) {
		return i1 & 3;
	}
}
