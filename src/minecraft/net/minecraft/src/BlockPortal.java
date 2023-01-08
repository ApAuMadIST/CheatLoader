package net.minecraft.src;

import java.util.Random;

public class BlockPortal extends BlockBreakable {
	public BlockPortal(int i1, int i2) {
		super(i1, i2, Material.portal, false);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		return null;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		float f5;
		float f6;
		if(iBlockAccess1.getBlockId(i2 - 1, i3, i4) != this.blockID && iBlockAccess1.getBlockId(i2 + 1, i3, i4) != this.blockID) {
			f5 = 0.125F;
			f6 = 0.5F;
			this.setBlockBounds(0.5F - f5, 0.0F, 0.5F - f6, 0.5F + f5, 1.0F, 0.5F + f6);
		} else {
			f5 = 0.5F;
			f6 = 0.125F;
			this.setBlockBounds(0.5F - f5, 0.0F, 0.5F - f6, 0.5F + f5, 1.0F, 0.5F + f6);
		}

	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean tryToCreatePortal(World world1, int i2, int i3, int i4) {
		byte b5 = 0;
		byte b6 = 0;
		if(world1.getBlockId(i2 - 1, i3, i4) == Block.obsidian.blockID || world1.getBlockId(i2 + 1, i3, i4) == Block.obsidian.blockID) {
			b5 = 1;
		}

		if(world1.getBlockId(i2, i3, i4 - 1) == Block.obsidian.blockID || world1.getBlockId(i2, i3, i4 + 1) == Block.obsidian.blockID) {
			b6 = 1;
		}

		if(b5 == b6) {
			return false;
		} else {
			if(world1.getBlockId(i2 - b5, i3, i4 - b6) == 0) {
				i2 -= b5;
				i4 -= b6;
			}

			int i7;
			int i8;
			for(i7 = -1; i7 <= 2; ++i7) {
				for(i8 = -1; i8 <= 3; ++i8) {
					boolean z9 = i7 == -1 || i7 == 2 || i8 == -1 || i8 == 3;
					if(i7 != -1 && i7 != 2 || i8 != -1 && i8 != 3) {
						int i10 = world1.getBlockId(i2 + b5 * i7, i3 + i8, i4 + b6 * i7);
						if(z9) {
							if(i10 != Block.obsidian.blockID) {
								return false;
							}
						} else if(i10 != 0 && i10 != Block.fire.blockID) {
							return false;
						}
					}
				}
			}

			world1.editingBlocks = true;

			for(i7 = 0; i7 < 2; ++i7) {
				for(i8 = 0; i8 < 3; ++i8) {
					world1.setBlockWithNotify(i2 + b5 * i7, i3 + i8, i4 + b6 * i7, Block.portal.blockID);
				}
			}

			world1.editingBlocks = false;
			return true;
		}
	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		byte b6 = 0;
		byte b7 = 1;
		if(world1.getBlockId(i2 - 1, i3, i4) == this.blockID || world1.getBlockId(i2 + 1, i3, i4) == this.blockID) {
			b6 = 1;
			b7 = 0;
		}

		int i8;
		for(i8 = i3; world1.getBlockId(i2, i8 - 1, i4) == this.blockID; --i8) {
		}

		if(world1.getBlockId(i2, i8 - 1, i4) != Block.obsidian.blockID) {
			world1.setBlockWithNotify(i2, i3, i4, 0);
		} else {
			int i9;
			for(i9 = 1; i9 < 4 && world1.getBlockId(i2, i8 + i9, i4) == this.blockID; ++i9) {
			}

			if(i9 == 3 && world1.getBlockId(i2, i8 + i9, i4) == Block.obsidian.blockID) {
				boolean z10 = world1.getBlockId(i2 - 1, i3, i4) == this.blockID || world1.getBlockId(i2 + 1, i3, i4) == this.blockID;
				boolean z11 = world1.getBlockId(i2, i3, i4 - 1) == this.blockID || world1.getBlockId(i2, i3, i4 + 1) == this.blockID;
				if(z10 && z11) {
					world1.setBlockWithNotify(i2, i3, i4, 0);
				} else if((world1.getBlockId(i2 + b6, i3, i4 + b7) != Block.obsidian.blockID || world1.getBlockId(i2 - b6, i3, i4 - b7) != this.blockID) && (world1.getBlockId(i2 - b6, i3, i4 - b7) != Block.obsidian.blockID || world1.getBlockId(i2 + b6, i3, i4 + b7) != this.blockID)) {
					world1.setBlockWithNotify(i2, i3, i4, 0);
				}
			} else {
				world1.setBlockWithNotify(i2, i3, i4, 0);
			}
		}
	}

	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		if(iBlockAccess1.getBlockId(i2, i3, i4) == this.blockID) {
			return false;
		} else {
			boolean z6 = iBlockAccess1.getBlockId(i2 - 1, i3, i4) == this.blockID && iBlockAccess1.getBlockId(i2 - 2, i3, i4) != this.blockID;
			boolean z7 = iBlockAccess1.getBlockId(i2 + 1, i3, i4) == this.blockID && iBlockAccess1.getBlockId(i2 + 2, i3, i4) != this.blockID;
			boolean z8 = iBlockAccess1.getBlockId(i2, i3, i4 - 1) == this.blockID && iBlockAccess1.getBlockId(i2, i3, i4 - 2) != this.blockID;
			boolean z9 = iBlockAccess1.getBlockId(i2, i3, i4 + 1) == this.blockID && iBlockAccess1.getBlockId(i2, i3, i4 + 2) != this.blockID;
			boolean z10 = z6 || z7;
			boolean z11 = z8 || z9;
			return z10 && i5 == 4 ? true : (z10 && i5 == 5 ? true : (z11 && i5 == 2 ? true : z11 && i5 == 3));
		}
	}

	public int quantityDropped(Random random1) {
		return 0;
	}

	public int getRenderBlockPass() {
		return 1;
	}

	public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
		if(entity5.ridingEntity == null && entity5.riddenByEntity == null) {
			entity5.setInPortal();
		}

	}

	public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
		if(random5.nextInt(100) == 0) {
			world1.playSoundEffect((double)i2 + 0.5D, (double)i3 + 0.5D, (double)i4 + 0.5D, "portal.portal", 1.0F, random5.nextFloat() * 0.4F + 0.8F);
		}

		for(int i6 = 0; i6 < 4; ++i6) {
			double d7 = (double)((float)i2 + random5.nextFloat());
			double d9 = (double)((float)i3 + random5.nextFloat());
			double d11 = (double)((float)i4 + random5.nextFloat());
			double d13 = 0.0D;
			double d15 = 0.0D;
			double d17 = 0.0D;
			int i19 = random5.nextInt(2) * 2 - 1;
			d13 = ((double)random5.nextFloat() - 0.5D) * 0.5D;
			d15 = ((double)random5.nextFloat() - 0.5D) * 0.5D;
			d17 = ((double)random5.nextFloat() - 0.5D) * 0.5D;
			if(world1.getBlockId(i2 - 1, i3, i4) != this.blockID && world1.getBlockId(i2 + 1, i3, i4) != this.blockID) {
				d7 = (double)i2 + 0.5D + 0.25D * (double)i19;
				d13 = (double)(random5.nextFloat() * 2.0F * (float)i19);
			} else {
				d11 = (double)i4 + 0.5D + 0.25D * (double)i19;
				d17 = (double)(random5.nextFloat() * 2.0F * (float)i19);
			}

			world1.spawnParticle("portal", d7, d9, d11, d13, d15, d17);
		}

	}
}
