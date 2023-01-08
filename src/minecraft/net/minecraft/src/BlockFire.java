package net.minecraft.src;

import java.util.Random;

public class BlockFire extends Block {
	private int[] chanceToEncourageFire = new int[256];
	private int[] abilityToCatchFire = new int[256];

	protected BlockFire(int i1, int i2) {
		super(i1, i2, Material.fire);
		this.setTickOnLoad(true);
	}

	public void initializeBlock() {
		this.setBurnRate(Block.planks.blockID, 5, 20);
		this.setBurnRate(Block.fence.blockID, 5, 20);
		this.setBurnRate(Block.stairCompactPlanks.blockID, 5, 20);
		this.setBurnRate(Block.wood.blockID, 5, 5);
		this.setBurnRate(Block.leaves.blockID, 30, 60);
		this.setBurnRate(Block.bookShelf.blockID, 30, 20);
		this.setBurnRate(Block.tnt.blockID, 15, 100);
		this.setBurnRate(Block.tallGrass.blockID, 60, 100);
		this.setBurnRate(Block.cloth.blockID, 30, 60);
	}

	private void setBurnRate(int i1, int i2, int i3) {
		this.chanceToEncourageFire[i1] = i2;
		this.abilityToCatchFire[i1] = i3;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		return null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 3;
	}

	public int quantityDropped(Random random1) {
		return 0;
	}

	public int tickRate() {
		return 40;
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		boolean z6 = world1.getBlockId(i2, i3 - 1, i4) == Block.netherrack.blockID;
		if(!this.canPlaceBlockAt(world1, i2, i3, i4)) {
			world1.setBlockWithNotify(i2, i3, i4, 0);
		}

		if(!z6 && world1.func_27161_C() && (world1.canBlockBeRainedOn(i2, i3, i4) || world1.canBlockBeRainedOn(i2 - 1, i3, i4) || world1.canBlockBeRainedOn(i2 + 1, i3, i4) || world1.canBlockBeRainedOn(i2, i3, i4 - 1) || world1.canBlockBeRainedOn(i2, i3, i4 + 1))) {
			world1.setBlockWithNotify(i2, i3, i4, 0);
		} else {
			int i7 = world1.getBlockMetadata(i2, i3, i4);
			if(i7 < 15) {
				world1.setBlockMetadata(i2, i3, i4, i7 + random5.nextInt(3) / 2);
			}

			world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
			if(!z6 && !this.func_263_h(world1, i2, i3, i4)) {
				if(!world1.isBlockNormalCube(i2, i3 - 1, i4) || i7 > 3) {
					world1.setBlockWithNotify(i2, i3, i4, 0);
				}

			} else if(!z6 && !this.canBlockCatchFire(world1, i2, i3 - 1, i4) && i7 == 15 && random5.nextInt(4) == 0) {
				world1.setBlockWithNotify(i2, i3, i4, 0);
			} else {
				this.tryToCatchBlockOnFire(world1, i2 + 1, i3, i4, 300, random5, i7);
				this.tryToCatchBlockOnFire(world1, i2 - 1, i3, i4, 300, random5, i7);
				this.tryToCatchBlockOnFire(world1, i2, i3 - 1, i4, 250, random5, i7);
				this.tryToCatchBlockOnFire(world1, i2, i3 + 1, i4, 250, random5, i7);
				this.tryToCatchBlockOnFire(world1, i2, i3, i4 - 1, 300, random5, i7);
				this.tryToCatchBlockOnFire(world1, i2, i3, i4 + 1, 300, random5, i7);

				for(int i8 = i2 - 1; i8 <= i2 + 1; ++i8) {
					for(int i9 = i4 - 1; i9 <= i4 + 1; ++i9) {
						for(int i10 = i3 - 1; i10 <= i3 + 4; ++i10) {
							if(i8 != i2 || i10 != i3 || i9 != i4) {
								int i11 = 100;
								if(i10 > i3 + 1) {
									i11 += (i10 - (i3 + 1)) * 100;
								}

								int i12 = this.getChanceOfNeighborsEncouragingFire(world1, i8, i10, i9);
								if(i12 > 0) {
									int i13 = (i12 + 40) / (i7 + 30);
									if(i13 > 0 && random5.nextInt(i11) <= i13 && (!world1.func_27161_C() || !world1.canBlockBeRainedOn(i8, i10, i9)) && !world1.canBlockBeRainedOn(i8 - 1, i10, i4) && !world1.canBlockBeRainedOn(i8 + 1, i10, i9) && !world1.canBlockBeRainedOn(i8, i10, i9 - 1) && !world1.canBlockBeRainedOn(i8, i10, i9 + 1)) {
										int i14 = i7 + random5.nextInt(5) / 4;
										if(i14 > 15) {
											i14 = 15;
										}

										world1.setBlockAndMetadataWithNotify(i8, i10, i9, this.blockID, i14);
									}
								}
							}
						}
					}
				}

			}
		}
	}

	private void tryToCatchBlockOnFire(World world1, int i2, int i3, int i4, int i5, Random random6, int i7) {
		int i8 = this.abilityToCatchFire[world1.getBlockId(i2, i3, i4)];
		if(random6.nextInt(i5) < i8) {
			boolean z9 = world1.getBlockId(i2, i3, i4) == Block.tnt.blockID;
			if(random6.nextInt(i7 + 10) < 5 && !world1.canBlockBeRainedOn(i2, i3, i4)) {
				int i10 = i7 + random6.nextInt(5) / 4;
				if(i10 > 15) {
					i10 = 15;
				}

				world1.setBlockAndMetadataWithNotify(i2, i3, i4, this.blockID, i10);
			} else {
				world1.setBlockWithNotify(i2, i3, i4, 0);
			}

			if(z9) {
				Block.tnt.onBlockDestroyedByPlayer(world1, i2, i3, i4, 1);
			}
		}

	}

	private boolean func_263_h(World world1, int i2, int i3, int i4) {
		return this.canBlockCatchFire(world1, i2 + 1, i3, i4) ? true : (this.canBlockCatchFire(world1, i2 - 1, i3, i4) ? true : (this.canBlockCatchFire(world1, i2, i3 - 1, i4) ? true : (this.canBlockCatchFire(world1, i2, i3 + 1, i4) ? true : (this.canBlockCatchFire(world1, i2, i3, i4 - 1) ? true : this.canBlockCatchFire(world1, i2, i3, i4 + 1)))));
	}

	private int getChanceOfNeighborsEncouragingFire(World world1, int i2, int i3, int i4) {
		byte b5 = 0;
		if(!world1.isAirBlock(i2, i3, i4)) {
			return 0;
		} else {
			int i6 = this.getChanceToEncourageFire(world1, i2 + 1, i3, i4, b5);
			i6 = this.getChanceToEncourageFire(world1, i2 - 1, i3, i4, i6);
			i6 = this.getChanceToEncourageFire(world1, i2, i3 - 1, i4, i6);
			i6 = this.getChanceToEncourageFire(world1, i2, i3 + 1, i4, i6);
			i6 = this.getChanceToEncourageFire(world1, i2, i3, i4 - 1, i6);
			i6 = this.getChanceToEncourageFire(world1, i2, i3, i4 + 1, i6);
			return i6;
		}
	}

	public boolean isCollidable() {
		return false;
	}

	public boolean canBlockCatchFire(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		return this.chanceToEncourageFire[iBlockAccess1.getBlockId(i2, i3, i4)] > 0;
	}

	public int getChanceToEncourageFire(World world1, int i2, int i3, int i4, int i5) {
		int i6 = this.chanceToEncourageFire[world1.getBlockId(i2, i3, i4)];
		return i6 > i5 ? i6 : i5;
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		return world1.isBlockNormalCube(i2, i3 - 1, i4) || this.func_263_h(world1, i2, i3, i4);
	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		if(!world1.isBlockNormalCube(i2, i3 - 1, i4) && !this.func_263_h(world1, i2, i3, i4)) {
			world1.setBlockWithNotify(i2, i3, i4, 0);
		}
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
		if(world1.getBlockId(i2, i3 - 1, i4) != Block.obsidian.blockID || !Block.portal.tryToCreatePortal(world1, i2, i3, i4)) {
			if(!world1.isBlockNormalCube(i2, i3 - 1, i4) && !this.func_263_h(world1, i2, i3, i4)) {
				world1.setBlockWithNotify(i2, i3, i4, 0);
			} else {
				world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
			}
		}
	}

	public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
		if(random5.nextInt(24) == 0) {
			world1.playSoundEffect((double)((float)i2 + 0.5F), (double)((float)i3 + 0.5F), (double)((float)i4 + 0.5F), "fire.fire", 1.0F + random5.nextFloat(), random5.nextFloat() * 0.7F + 0.3F);
		}

		int i6;
		float f7;
		float f8;
		float f9;
		if(!world1.isBlockNormalCube(i2, i3 - 1, i4) && !Block.fire.canBlockCatchFire(world1, i2, i3 - 1, i4)) {
			if(Block.fire.canBlockCatchFire(world1, i2 - 1, i3, i4)) {
				for(i6 = 0; i6 < 2; ++i6) {
					f7 = (float)i2 + random5.nextFloat() * 0.1F;
					f8 = (float)i3 + random5.nextFloat();
					f9 = (float)i4 + random5.nextFloat();
					world1.spawnParticle("largesmoke", (double)f7, (double)f8, (double)f9, 0.0D, 0.0D, 0.0D);
				}
			}

			if(Block.fire.canBlockCatchFire(world1, i2 + 1, i3, i4)) {
				for(i6 = 0; i6 < 2; ++i6) {
					f7 = (float)(i2 + 1) - random5.nextFloat() * 0.1F;
					f8 = (float)i3 + random5.nextFloat();
					f9 = (float)i4 + random5.nextFloat();
					world1.spawnParticle("largesmoke", (double)f7, (double)f8, (double)f9, 0.0D, 0.0D, 0.0D);
				}
			}

			if(Block.fire.canBlockCatchFire(world1, i2, i3, i4 - 1)) {
				for(i6 = 0; i6 < 2; ++i6) {
					f7 = (float)i2 + random5.nextFloat();
					f8 = (float)i3 + random5.nextFloat();
					f9 = (float)i4 + random5.nextFloat() * 0.1F;
					world1.spawnParticle("largesmoke", (double)f7, (double)f8, (double)f9, 0.0D, 0.0D, 0.0D);
				}
			}

			if(Block.fire.canBlockCatchFire(world1, i2, i3, i4 + 1)) {
				for(i6 = 0; i6 < 2; ++i6) {
					f7 = (float)i2 + random5.nextFloat();
					f8 = (float)i3 + random5.nextFloat();
					f9 = (float)(i4 + 1) - random5.nextFloat() * 0.1F;
					world1.spawnParticle("largesmoke", (double)f7, (double)f8, (double)f9, 0.0D, 0.0D, 0.0D);
				}
			}

			if(Block.fire.canBlockCatchFire(world1, i2, i3 + 1, i4)) {
				for(i6 = 0; i6 < 2; ++i6) {
					f7 = (float)i2 + random5.nextFloat();
					f8 = (float)(i3 + 1) - random5.nextFloat() * 0.1F;
					f9 = (float)i4 + random5.nextFloat();
					world1.spawnParticle("largesmoke", (double)f7, (double)f8, (double)f9, 0.0D, 0.0D, 0.0D);
				}
			}
		} else {
			for(i6 = 0; i6 < 3; ++i6) {
				f7 = (float)i2 + random5.nextFloat();
				f8 = (float)i3 + random5.nextFloat() * 0.5F + 0.5F;
				f9 = (float)i4 + random5.nextFloat();
				world1.spawnParticle("largesmoke", (double)f7, (double)f8, (double)f9, 0.0D, 0.0D, 0.0D);
			}
		}

	}
}
