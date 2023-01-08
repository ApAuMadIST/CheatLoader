package net.minecraft.src;

import java.util.Random;

public class BlockLeaves extends BlockLeavesBase {
	private int baseIndexInPNG;
	int[] adjacentTreeBlocks;

	protected BlockLeaves(int i1, int i2) {
		super(i1, i2, Material.leaves, false);
		this.baseIndexInPNG = i2;
		this.setTickOnLoad(true);
	}

	public int getRenderColor(int i1) {
		return (i1 & 1) == 1 ? ColorizerFoliage.getFoliageColorPine() : ((i1 & 2) == 2 ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.func_31073_c());
	}

	public int colorMultiplier(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
		if((i5 & 1) == 1) {
			return ColorizerFoliage.getFoliageColorPine();
		} else if((i5 & 2) == 2) {
			return ColorizerFoliage.getFoliageColorBirch();
		} else {
			iBlockAccess1.getWorldChunkManager().func_4069_a(i2, i4, 1, 1);
			double d6 = iBlockAccess1.getWorldChunkManager().temperature[0];
			double d8 = iBlockAccess1.getWorldChunkManager().humidity[0];
			return ColorizerFoliage.getFoliageColor(d6, d8);
		}
	}

	public void onBlockRemoval(World world1, int i2, int i3, int i4) {
		byte b5 = 1;
		int i6 = b5 + 1;
		if(world1.checkChunksExist(i2 - i6, i3 - i6, i4 - i6, i2 + i6, i3 + i6, i4 + i6)) {
			for(int i7 = -b5; i7 <= b5; ++i7) {
				for(int i8 = -b5; i8 <= b5; ++i8) {
					for(int i9 = -b5; i9 <= b5; ++i9) {
						int i10 = world1.getBlockId(i2 + i7, i3 + i8, i4 + i9);
						if(i10 == Block.leaves.blockID) {
							int i11 = world1.getBlockMetadata(i2 + i7, i3 + i8, i4 + i9);
							world1.setBlockMetadata(i2 + i7, i3 + i8, i4 + i9, i11 | 8);
						}
					}
				}
			}
		}

	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		if(!world1.multiplayerWorld) {
			int i6 = world1.getBlockMetadata(i2, i3, i4);
			if((i6 & 8) != 0) {
				byte b7 = 4;
				int i8 = b7 + 1;
				byte b9 = 32;
				int i10 = b9 * b9;
				int i11 = b9 / 2;
				if(this.adjacentTreeBlocks == null) {
					this.adjacentTreeBlocks = new int[b9 * b9 * b9];
				}

				int i12;
				if(world1.checkChunksExist(i2 - i8, i3 - i8, i4 - i8, i2 + i8, i3 + i8, i4 + i8)) {
					i12 = -b7;

					label111:
					while(true) {
						int i13;
						int i14;
						int i15;
						if(i12 > b7) {
							i12 = 1;

							while(true) {
								if(i12 > 4) {
									break label111;
								}

								for(i13 = -b7; i13 <= b7; ++i13) {
									for(i14 = -b7; i14 <= b7; ++i14) {
										for(i15 = -b7; i15 <= b7; ++i15) {
											if(this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + i15 + i11] == i12 - 1) {
												if(this.adjacentTreeBlocks[(i13 + i11 - 1) * i10 + (i14 + i11) * b9 + i15 + i11] == -2) {
													this.adjacentTreeBlocks[(i13 + i11 - 1) * i10 + (i14 + i11) * b9 + i15 + i11] = i12;
												}

												if(this.adjacentTreeBlocks[(i13 + i11 + 1) * i10 + (i14 + i11) * b9 + i15 + i11] == -2) {
													this.adjacentTreeBlocks[(i13 + i11 + 1) * i10 + (i14 + i11) * b9 + i15 + i11] = i12;
												}

												if(this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11 - 1) * b9 + i15 + i11] == -2) {
													this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11 - 1) * b9 + i15 + i11] = i12;
												}

												if(this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11 + 1) * b9 + i15 + i11] == -2) {
													this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11 + 1) * b9 + i15 + i11] = i12;
												}

												if(this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + (i15 + i11 - 1)] == -2) {
													this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + (i15 + i11 - 1)] = i12;
												}

												if(this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + i15 + i11 + 1] == -2) {
													this.adjacentTreeBlocks[(i13 + i11) * i10 + (i14 + i11) * b9 + i15 + i11 + 1] = i12;
												}
											}
										}
									}
								}

								++i12;
							}
						}

						for(i13 = -b7; i13 <= b7; ++i13) {
							for(i14 = -b7; i14 <= b7; ++i14) {
								i15 = world1.getBlockId(i2 + i12, i3 + i13, i4 + i14);
								if(i15 == Block.wood.blockID) {
									this.adjacentTreeBlocks[(i12 + i11) * i10 + (i13 + i11) * b9 + i14 + i11] = 0;
								} else if(i15 == Block.leaves.blockID) {
									this.adjacentTreeBlocks[(i12 + i11) * i10 + (i13 + i11) * b9 + i14 + i11] = -2;
								} else {
									this.adjacentTreeBlocks[(i12 + i11) * i10 + (i13 + i11) * b9 + i14 + i11] = -1;
								}
							}
						}

						++i12;
					}
				}

				i12 = this.adjacentTreeBlocks[i11 * i10 + i11 * b9 + i11];
				if(i12 >= 0) {
					world1.setBlockMetadata(i2, i3, i4, i6 & -9);
				} else {
					this.removeLeaves(world1, i2, i3, i4);
				}
			}

		}
	}

	private void removeLeaves(World world1, int i2, int i3, int i4) {
		this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
		world1.setBlockWithNotify(i2, i3, i4, 0);
	}

	public int quantityDropped(Random random1) {
		return random1.nextInt(20) == 0 ? 1 : 0;
	}

	public int idDropped(int i1, Random random2) {
		return Block.sapling.blockID;
	}

	public void harvestBlock(World world1, EntityPlayer entityPlayer2, int i3, int i4, int i5, int i6) {
		if(!world1.multiplayerWorld && entityPlayer2.getCurrentEquippedItem() != null && entityPlayer2.getCurrentEquippedItem().itemID == Item.shears.shiftedIndex) {
			entityPlayer2.addStat(StatList.mineBlockStatArray[this.blockID], 1);
			this.dropBlockAsItem_do(world1, i3, i4, i5, new ItemStack(Block.leaves.blockID, 1, i6 & 3));
		} else {
			super.harvestBlock(world1, entityPlayer2, i3, i4, i5, i6);
		}

	}

	protected int damageDropped(int i1) {
		return i1 & 3;
	}

	public boolean isOpaqueCube() {
		return !this.graphicsLevel;
	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		return (i2 & 3) == 1 ? this.blockIndexInTexture + 80 : this.blockIndexInTexture;
	}

	public void setGraphicsLevel(boolean z1) {
		this.graphicsLevel = z1;
		this.blockIndexInTexture = this.baseIndexInPNG + (z1 ? 0 : 1);
	}

	public void onEntityWalking(World world1, int i2, int i3, int i4, Entity entity5) {
		super.onEntityWalking(world1, i2, i3, i4, entity5);
	}
}
