package net.minecraft.src;

import java.util.Random;

public class BlockFlowing extends BlockFluid {
	int numAdjacentSources = 0;
	boolean[] isOptimalFlowDirection = new boolean[4];
	int[] flowCost = new int[4];

	protected BlockFlowing(int i1, Material material2) {
		super(i1, material2);
	}

	private void func_30003_j(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockMetadata(i2, i3, i4);
		world1.setBlockAndMetadata(i2, i3, i4, this.blockID + 1, i5);
		world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
		world1.markBlockNeedsUpdate(i2, i3, i4);
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		int i6 = this.getFlowDecay(world1, i2, i3, i4);
		byte b7 = 1;
		if(this.blockMaterial == Material.lava && !world1.worldProvider.isHellWorld) {
			b7 = 2;
		}

		boolean z8 = true;
		int i10;
		if(i6 > 0) {
			byte b9 = -100;
			this.numAdjacentSources = 0;
			int i12 = this.getSmallestFlowDecay(world1, i2 - 1, i3, i4, b9);
			i12 = this.getSmallestFlowDecay(world1, i2 + 1, i3, i4, i12);
			i12 = this.getSmallestFlowDecay(world1, i2, i3, i4 - 1, i12);
			i12 = this.getSmallestFlowDecay(world1, i2, i3, i4 + 1, i12);
			i10 = i12 + b7;
			if(i10 >= 8 || i12 < 0) {
				i10 = -1;
			}

			if(this.getFlowDecay(world1, i2, i3 + 1, i4) >= 0) {
				int i11 = this.getFlowDecay(world1, i2, i3 + 1, i4);
				if(i11 >= 8) {
					i10 = i11;
				} else {
					i10 = i11 + 8;
				}
			}

			if(this.numAdjacentSources >= 2 && this.blockMaterial == Material.water) {
				if(world1.getBlockMaterial(i2, i3 - 1, i4).isSolid()) {
					i10 = 0;
				} else if(world1.getBlockMaterial(i2, i3 - 1, i4) == this.blockMaterial && world1.getBlockMetadata(i2, i3, i4) == 0) {
					i10 = 0;
				}
			}

			if(this.blockMaterial == Material.lava && i6 < 8 && i10 < 8 && i10 > i6 && random5.nextInt(4) != 0) {
				i10 = i6;
				z8 = false;
			}

			if(i10 != i6) {
				i6 = i10;
				if(i10 < 0) {
					world1.setBlockWithNotify(i2, i3, i4, 0);
				} else {
					world1.setBlockMetadataWithNotify(i2, i3, i4, i10);
					world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
					world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
				}
			} else if(z8) {
				this.func_30003_j(world1, i2, i3, i4);
			}
		} else {
			this.func_30003_j(world1, i2, i3, i4);
		}

		if(this.liquidCanDisplaceBlock(world1, i2, i3 - 1, i4)) {
			if(i6 >= 8) {
				world1.setBlockAndMetadataWithNotify(i2, i3 - 1, i4, this.blockID, i6);
			} else {
				world1.setBlockAndMetadataWithNotify(i2, i3 - 1, i4, this.blockID, i6 + 8);
			}
		} else if(i6 >= 0 && (i6 == 0 || this.blockBlocksFlow(world1, i2, i3 - 1, i4))) {
			boolean[] z13 = this.getOptimalFlowDirections(world1, i2, i3, i4);
			i10 = i6 + b7;
			if(i6 >= 8) {
				i10 = 1;
			}

			if(i10 >= 8) {
				return;
			}

			if(z13[0]) {
				this.flowIntoBlock(world1, i2 - 1, i3, i4, i10);
			}

			if(z13[1]) {
				this.flowIntoBlock(world1, i2 + 1, i3, i4, i10);
			}

			if(z13[2]) {
				this.flowIntoBlock(world1, i2, i3, i4 - 1, i10);
			}

			if(z13[3]) {
				this.flowIntoBlock(world1, i2, i3, i4 + 1, i10);
			}
		}

	}

	private void flowIntoBlock(World world1, int i2, int i3, int i4, int i5) {
		if(this.liquidCanDisplaceBlock(world1, i2, i3, i4)) {
			int i6 = world1.getBlockId(i2, i3, i4);
			if(i6 > 0) {
				if(this.blockMaterial == Material.lava) {
					this.triggerLavaMixEffects(world1, i2, i3, i4);
				} else {
					Block.blocksList[i6].dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
				}
			}

			world1.setBlockAndMetadataWithNotify(i2, i3, i4, this.blockID, i5);
		}

	}

	private int calculateFlowCost(World world1, int i2, int i3, int i4, int i5, int i6) {
		int i7 = 1000;

		for(int i8 = 0; i8 < 4; ++i8) {
			if((i8 != 0 || i6 != 1) && (i8 != 1 || i6 != 0) && (i8 != 2 || i6 != 3) && (i8 != 3 || i6 != 2)) {
				int i9 = i2;
				int i11 = i4;
				if(i8 == 0) {
					i9 = i2 - 1;
				}

				if(i8 == 1) {
					++i9;
				}

				if(i8 == 2) {
					i11 = i4 - 1;
				}

				if(i8 == 3) {
					++i11;
				}

				if(!this.blockBlocksFlow(world1, i9, i3, i11) && (world1.getBlockMaterial(i9, i3, i11) != this.blockMaterial || world1.getBlockMetadata(i9, i3, i11) != 0)) {
					if(!this.blockBlocksFlow(world1, i9, i3 - 1, i11)) {
						return i5;
					}

					if(i5 < 4) {
						int i12 = this.calculateFlowCost(world1, i9, i3, i11, i5 + 1, i8);
						if(i12 < i7) {
							i7 = i12;
						}
					}
				}
			}
		}

		return i7;
	}

	private boolean[] getOptimalFlowDirections(World world1, int i2, int i3, int i4) {
		int i5;
		int i6;
		for(i5 = 0; i5 < 4; ++i5) {
			this.flowCost[i5] = 1000;
			i6 = i2;
			int i8 = i4;
			if(i5 == 0) {
				i6 = i2 - 1;
			}

			if(i5 == 1) {
				++i6;
			}

			if(i5 == 2) {
				i8 = i4 - 1;
			}

			if(i5 == 3) {
				++i8;
			}

			if(!this.blockBlocksFlow(world1, i6, i3, i8) && (world1.getBlockMaterial(i6, i3, i8) != this.blockMaterial || world1.getBlockMetadata(i6, i3, i8) != 0)) {
				if(!this.blockBlocksFlow(world1, i6, i3 - 1, i8)) {
					this.flowCost[i5] = 0;
				} else {
					this.flowCost[i5] = this.calculateFlowCost(world1, i6, i3, i8, 1, i5);
				}
			}
		}

		i5 = this.flowCost[0];

		for(i6 = 1; i6 < 4; ++i6) {
			if(this.flowCost[i6] < i5) {
				i5 = this.flowCost[i6];
			}
		}

		for(i6 = 0; i6 < 4; ++i6) {
			this.isOptimalFlowDirection[i6] = this.flowCost[i6] == i5;
		}

		return this.isOptimalFlowDirection;
	}

	private boolean blockBlocksFlow(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockId(i2, i3, i4);
		if(i5 != Block.doorWood.blockID && i5 != Block.doorSteel.blockID && i5 != Block.signPost.blockID && i5 != Block.ladder.blockID && i5 != Block.reed.blockID) {
			if(i5 == 0) {
				return false;
			} else {
				Material material6 = Block.blocksList[i5].blockMaterial;
				return material6.getIsSolid();
			}
		} else {
			return true;
		}
	}

	protected int getSmallestFlowDecay(World world1, int i2, int i3, int i4, int i5) {
		int i6 = this.getFlowDecay(world1, i2, i3, i4);
		if(i6 < 0) {
			return i5;
		} else {
			if(i6 == 0) {
				++this.numAdjacentSources;
			}

			if(i6 >= 8) {
				i6 = 0;
			}

			return i5 >= 0 && i6 >= i5 ? i5 : i6;
		}
	}

	private boolean liquidCanDisplaceBlock(World world1, int i2, int i3, int i4) {
		Material material5 = world1.getBlockMaterial(i2, i3, i4);
		return material5 == this.blockMaterial ? false : (material5 == Material.lava ? false : !this.blockBlocksFlow(world1, i2, i3, i4));
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
		super.onBlockAdded(world1, i2, i3, i4);
		if(world1.getBlockId(i2, i3, i4) == this.blockID) {
			world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
		}

	}
}
