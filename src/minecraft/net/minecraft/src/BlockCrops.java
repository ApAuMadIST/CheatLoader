package net.minecraft.src;

import java.util.Random;

public class BlockCrops extends BlockFlower {
	protected BlockCrops(int i1, int i2) {
		super(i1, i2);
		this.blockIndexInTexture = i2;
		this.setTickOnLoad(true);
		float f3 = 0.5F;
		this.setBlockBounds(0.5F - f3, 0.0F, 0.5F - f3, 0.5F + f3, 0.25F, 0.5F + f3);
	}

	protected boolean canThisPlantGrowOnThisBlockID(int i1) {
		return i1 == Block.tilledField.blockID;
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		super.updateTick(world1, i2, i3, i4, random5);
		if(world1.getBlockLightValue(i2, i3 + 1, i4) >= 9) {
			int i6 = world1.getBlockMetadata(i2, i3, i4);
			if(i6 < 7) {
				float f7 = this.getGrowthRate(world1, i2, i3, i4);
				if(random5.nextInt((int)(100.0F / f7)) == 0) {
					++i6;
					world1.setBlockMetadataWithNotify(i2, i3, i4, i6);
				}
			}
		}

	}

	public void fertilize(World world1, int i2, int i3, int i4) {
		world1.setBlockMetadataWithNotify(i2, i3, i4, 7);
	}

	private float getGrowthRate(World world1, int i2, int i3, int i4) {
		float f5 = 1.0F;
		int i6 = world1.getBlockId(i2, i3, i4 - 1);
		int i7 = world1.getBlockId(i2, i3, i4 + 1);
		int i8 = world1.getBlockId(i2 - 1, i3, i4);
		int i9 = world1.getBlockId(i2 + 1, i3, i4);
		int i10 = world1.getBlockId(i2 - 1, i3, i4 - 1);
		int i11 = world1.getBlockId(i2 + 1, i3, i4 - 1);
		int i12 = world1.getBlockId(i2 + 1, i3, i4 + 1);
		int i13 = world1.getBlockId(i2 - 1, i3, i4 + 1);
		boolean z14 = i8 == this.blockID || i9 == this.blockID;
		boolean z15 = i6 == this.blockID || i7 == this.blockID;
		boolean z16 = i10 == this.blockID || i11 == this.blockID || i12 == this.blockID || i13 == this.blockID;

		for(int i17 = i2 - 1; i17 <= i2 + 1; ++i17) {
			for(int i18 = i4 - 1; i18 <= i4 + 1; ++i18) {
				int i19 = world1.getBlockId(i17, i3 - 1, i18);
				float f20 = 0.0F;
				if(i19 == Block.tilledField.blockID) {
					f20 = 1.0F;
					if(world1.getBlockMetadata(i17, i3 - 1, i18) > 0) {
						f20 = 3.0F;
					}
				}

				if(i17 != i2 || i18 != i4) {
					f20 /= 4.0F;
				}

				f5 += f20;
			}
		}

		if(z16 || z14 && z15) {
			f5 /= 2.0F;
		}

		return f5;
	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		if(i2 < 0) {
			i2 = 7;
		}

		return this.blockIndexInTexture + i2;
	}

	public int getRenderType() {
		return 6;
	}

	public void dropBlockAsItemWithChance(World world1, int i2, int i3, int i4, int i5, float f6) {
		super.dropBlockAsItemWithChance(world1, i2, i3, i4, i5, f6);
		if(!world1.multiplayerWorld) {
			for(int i7 = 0; i7 < 3; ++i7) {
				if(world1.rand.nextInt(15) <= i5) {
					float f8 = 0.7F;
					float f9 = world1.rand.nextFloat() * f8 + (1.0F - f8) * 0.5F;
					float f10 = world1.rand.nextFloat() * f8 + (1.0F - f8) * 0.5F;
					float f11 = world1.rand.nextFloat() * f8 + (1.0F - f8) * 0.5F;
					EntityItem entityItem12 = new EntityItem(world1, (double)((float)i2 + f9), (double)((float)i3 + f10), (double)((float)i4 + f11), new ItemStack(Item.seeds));
					entityItem12.delayBeforeCanPickup = 10;
					world1.entityJoinedWorld(entityItem12);
				}
			}

		}
	}

	public int idDropped(int i1, Random random2) {
		return i1 == 7 ? Item.wheat.shiftedIndex : -1;
	}

	public int quantityDropped(Random random1) {
		return 1;
	}
}
