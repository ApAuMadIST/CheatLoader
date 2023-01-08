package net.minecraft.src;

public class BlockTrapDoor extends Block {
	protected BlockTrapDoor(int i1, Material material2) {
		super(i1, material2);
		this.blockIndexInTexture = 84;
		if(material2 == Material.iron) {
			++this.blockIndexInTexture;
		}

		float f3 = 0.5F;
		float f4 = 1.0F;
		this.setBlockBounds(0.5F - f3, 0.0F, 0.5F - f3, 0.5F + f3, f4, 0.5F + f3);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 0;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
		return super.getSelectedBoundingBoxFromPool(world1, i2, i3, i4);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
		return super.getCollisionBoundingBoxFromPool(world1, i2, i3, i4);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		this.setBlockBoundsForBlockRender(iBlockAccess1.getBlockMetadata(i2, i3, i4));
	}

	public void setBlockBoundsForItemRender() {
		float f1 = 0.1875F;
		this.setBlockBounds(0.0F, 0.5F - f1 / 2.0F, 0.0F, 1.0F, 0.5F + f1 / 2.0F, 1.0F);
	}

	public void setBlockBoundsForBlockRender(int i1) {
		float f2 = 0.1875F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f2, 1.0F);
		if(isTrapdoorOpen(i1)) {
			if((i1 & 3) == 0) {
				this.setBlockBounds(0.0F, 0.0F, 1.0F - f2, 1.0F, 1.0F, 1.0F);
			}

			if((i1 & 3) == 1) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f2);
			}

			if((i1 & 3) == 2) {
				this.setBlockBounds(1.0F - f2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}

			if((i1 & 3) == 3) {
				this.setBlockBounds(0.0F, 0.0F, 0.0F, f2, 1.0F, 1.0F);
			}
		}

	}

	public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		this.blockActivated(world1, i2, i3, i4, entityPlayer5);
	}

	public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		if(this.blockMaterial == Material.iron) {
			return true;
		} else {
			int i6 = world1.getBlockMetadata(i2, i3, i4);
			world1.setBlockMetadataWithNotify(i2, i3, i4, i6 ^ 4);
			world1.func_28107_a(entityPlayer5, 1003, i2, i3, i4, 0);
			return true;
		}
	}

	public void onPoweredBlockChange(World world1, int i2, int i3, int i4, boolean z5) {
		int i6 = world1.getBlockMetadata(i2, i3, i4);
		boolean z7 = (i6 & 4) > 0;
		if(z7 != z5) {
			world1.setBlockMetadataWithNotify(i2, i3, i4, i6 ^ 4);
			world1.func_28107_a((EntityPlayer)null, 1003, i2, i3, i4, 0);
		}
	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		if(!world1.multiplayerWorld) {
			int i6 = world1.getBlockMetadata(i2, i3, i4);
			int i7 = i2;
			int i8 = i4;
			if((i6 & 3) == 0) {
				i8 = i4 + 1;
			}

			if((i6 & 3) == 1) {
				--i8;
			}

			if((i6 & 3) == 2) {
				i7 = i2 + 1;
			}

			if((i6 & 3) == 3) {
				--i7;
			}

			if(!world1.isBlockNormalCube(i7, i3, i8)) {
				world1.setBlockWithNotify(i2, i3, i4, 0);
				this.dropBlockAsItem(world1, i2, i3, i4, i6);
			}

			if(i5 > 0 && Block.blocksList[i5].canProvidePower()) {
				boolean z9 = world1.isBlockIndirectlyGettingPowered(i2, i3, i4);
				this.onPoweredBlockChange(world1, i2, i3, i4, z9);
			}

		}
	}

	public MovingObjectPosition collisionRayTrace(World world1, int i2, int i3, int i4, Vec3D vec3D5, Vec3D vec3D6) {
		this.setBlockBoundsBasedOnState(world1, i2, i3, i4);
		return super.collisionRayTrace(world1, i2, i3, i4, vec3D5, vec3D6);
	}

	public void onBlockPlaced(World world1, int i2, int i3, int i4, int i5) {
		byte b6 = 0;
		if(i5 == 2) {
			b6 = 0;
		}

		if(i5 == 3) {
			b6 = 1;
		}

		if(i5 == 4) {
			b6 = 2;
		}

		if(i5 == 5) {
			b6 = 3;
		}

		world1.setBlockMetadataWithNotify(i2, i3, i4, b6);
	}

	public boolean canPlaceBlockOnSide(World world1, int i2, int i3, int i4, int i5) {
		if(i5 == 0) {
			return false;
		} else if(i5 == 1) {
			return false;
		} else {
			if(i5 == 2) {
				++i4;
			}

			if(i5 == 3) {
				--i4;
			}

			if(i5 == 4) {
				++i2;
			}

			if(i5 == 5) {
				--i2;
			}

			return world1.isBlockNormalCube(i2, i3, i4);
		}
	}

	public static boolean isTrapdoorOpen(int i0) {
		return (i0 & 4) != 0;
	}
}
