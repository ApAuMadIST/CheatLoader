package net.minecraft.src;

import java.util.Random;

public class BlockPistonMoving extends BlockContainer {
	public BlockPistonMoving(int i1) {
		super(i1, Material.field_31067_B);
		this.setHardness(-1.0F);
	}

	protected TileEntity getBlockEntity() {
		return null;
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
	}

	public void onBlockRemoval(World world1, int i2, int i3, int i4) {
		TileEntity tileEntity5 = world1.getBlockTileEntity(i2, i3, i4);
		if(tileEntity5 != null && tileEntity5 instanceof TileEntityPiston) {
			((TileEntityPiston)tileEntity5).func_31011_l();
		} else {
			super.onBlockRemoval(world1, i2, i3, i4);
		}

	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		return false;
	}

	public boolean canPlaceBlockOnSide(World world1, int i2, int i3, int i4, int i5) {
		return false;
	}

	public int getRenderType() {
		return -1;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		if(!world1.multiplayerWorld && world1.getBlockTileEntity(i2, i3, i4) == null) {
			world1.setBlockWithNotify(i2, i3, i4, 0);
			return true;
		} else {
			return false;
		}
	}

	public int idDropped(int i1, Random random2) {
		return 0;
	}

	public void dropBlockAsItemWithChance(World world1, int i2, int i3, int i4, int i5, float f6) {
		if(!world1.multiplayerWorld) {
			TileEntityPiston tileEntityPiston7 = this.func_31034_c(world1, i2, i3, i4);
			if(tileEntityPiston7 != null) {
				Block.blocksList[tileEntityPiston7.getStoredBlockID()].dropBlockAsItem(world1, i2, i3, i4, tileEntityPiston7.getBlockMetadata());
			}
		}
	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		if(!world1.multiplayerWorld && world1.getBlockTileEntity(i2, i3, i4) == null) {
			;
		}

	}

	public static TileEntity func_31036_a(int i0, int i1, int i2, boolean z3, boolean z4) {
		return new TileEntityPiston(i0, i1, i2, z3, z4);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		TileEntityPiston tileEntityPiston5 = this.func_31034_c(world1, i2, i3, i4);
		if(tileEntityPiston5 == null) {
			return null;
		} else {
			float f6 = tileEntityPiston5.func_31008_a(0.0F);
			if(tileEntityPiston5.func_31015_b()) {
				f6 = 1.0F - f6;
			}

			return this.func_31035_a(world1, i2, i3, i4, tileEntityPiston5.getStoredBlockID(), f6, tileEntityPiston5.func_31009_d());
		}
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		TileEntityPiston tileEntityPiston5 = this.func_31034_c(iBlockAccess1, i2, i3, i4);
		if(tileEntityPiston5 != null) {
			Block block6 = Block.blocksList[tileEntityPiston5.getStoredBlockID()];
			if(block6 == null || block6 == this) {
				return;
			}

			block6.setBlockBoundsBasedOnState(iBlockAccess1, i2, i3, i4);
			float f7 = tileEntityPiston5.func_31008_a(0.0F);
			if(tileEntityPiston5.func_31015_b()) {
				f7 = 1.0F - f7;
			}

			int i8 = tileEntityPiston5.func_31009_d();
			this.minX = block6.minX - (double)((float)PistonBlockTextures.field_31056_b[i8] * f7);
			this.minY = block6.minY - (double)((float)PistonBlockTextures.field_31059_c[i8] * f7);
			this.minZ = block6.minZ - (double)((float)PistonBlockTextures.field_31058_d[i8] * f7);
			this.maxX = block6.maxX - (double)((float)PistonBlockTextures.field_31056_b[i8] * f7);
			this.maxY = block6.maxY - (double)((float)PistonBlockTextures.field_31059_c[i8] * f7);
			this.maxZ = block6.maxZ - (double)((float)PistonBlockTextures.field_31058_d[i8] * f7);
		}

	}

	public AxisAlignedBB func_31035_a(World world1, int i2, int i3, int i4, int i5, float f6, int i7) {
		if(i5 != 0 && i5 != this.blockID) {
			AxisAlignedBB axisAlignedBB8 = Block.blocksList[i5].getCollisionBoundingBoxFromPool(world1, i2, i3, i4);
			if(axisAlignedBB8 == null) {
				return null;
			} else {
				axisAlignedBB8.minX -= (double)((float)PistonBlockTextures.field_31056_b[i7] * f6);
				axisAlignedBB8.maxX -= (double)((float)PistonBlockTextures.field_31056_b[i7] * f6);
				axisAlignedBB8.minY -= (double)((float)PistonBlockTextures.field_31059_c[i7] * f6);
				axisAlignedBB8.maxY -= (double)((float)PistonBlockTextures.field_31059_c[i7] * f6);
				axisAlignedBB8.minZ -= (double)((float)PistonBlockTextures.field_31058_d[i7] * f6);
				axisAlignedBB8.maxZ -= (double)((float)PistonBlockTextures.field_31058_d[i7] * f6);
				return axisAlignedBB8;
			}
		} else {
			return null;
		}
	}

	private TileEntityPiston func_31034_c(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		TileEntity tileEntity5 = iBlockAccess1.getBlockTileEntity(i2, i3, i4);
		return tileEntity5 != null && tileEntity5 instanceof TileEntityPiston ? (TileEntityPiston)tileEntity5 : null;
	}
}
