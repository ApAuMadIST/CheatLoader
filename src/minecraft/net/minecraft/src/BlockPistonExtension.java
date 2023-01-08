package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockPistonExtension extends Block {
	private int field_31053_a = -1;

	public BlockPistonExtension(int i1, int i2) {
		super(i1, i2, Material.field_31067_B);
		this.setStepSound(soundStoneFootstep);
		this.setHardness(0.5F);
	}

	public void func_31052_a_(int i1) {
		this.field_31053_a = i1;
	}

	public void func_31051_a() {
		this.field_31053_a = -1;
	}

	public void onBlockRemoval(World world1, int i2, int i3, int i4) {
		super.onBlockRemoval(world1, i2, i3, i4);
		int i5 = world1.getBlockMetadata(i2, i3, i4);
		int i6 = PistonBlockTextures.field_31057_a[func_31050_c(i5)];
		i2 += PistonBlockTextures.field_31056_b[i6];
		i3 += PistonBlockTextures.field_31059_c[i6];
		i4 += PistonBlockTextures.field_31058_d[i6];
		int i7 = world1.getBlockId(i2, i3, i4);
		if(i7 == Block.pistonBase.blockID || i7 == Block.pistonStickyBase.blockID) {
			i5 = world1.getBlockMetadata(i2, i3, i4);
			if(BlockPistonBase.isPowered(i5)) {
				Block.blocksList[i7].dropBlockAsItem(world1, i2, i3, i4, i5);
				world1.setBlockWithNotify(i2, i3, i4, 0);
			}
		}

	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		int i3 = func_31050_c(i2);
		return i1 == i3 ? (this.field_31053_a >= 0 ? this.field_31053_a : ((i2 & 8) != 0 ? this.blockIndexInTexture - 1 : this.blockIndexInTexture)) : (i1 == PistonBlockTextures.field_31057_a[i3] ? 107 : 108);
	}

	public int getRenderType() {
		return 17;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		return false;
	}

	public boolean canPlaceBlockOnSide(World world1, int i2, int i3, int i4, int i5) {
		return false;
	}

	public int quantityDropped(Random random1) {
		return 0;
	}

	public void getCollidingBoundingBoxes(World world1, int i2, int i3, int i4, AxisAlignedBB axisAlignedBB5, ArrayList arrayList6) {
		int i7 = world1.getBlockMetadata(i2, i3, i4);
		switch(func_31050_c(i7)) {
		case 0:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			this.setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			break;
		case 1:
			this.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			break;
		case 2:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			this.setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			break;
		case 3:
			this.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			this.setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			break;
		case 4:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			this.setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			break;
		case 5:
			this.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
			this.setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
			super.getCollidingBoundingBoxes(world1, i2, i3, i4, axisAlignedBB5, arrayList6);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4);
		switch(func_31050_c(i5)) {
		case 0:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
			break;
		case 1:
			this.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
			break;
		case 2:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
			break;
		case 3:
			this.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
			break;
		case 4:
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
			break;
		case 5:
			this.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		int i6 = func_31050_c(world1.getBlockMetadata(i2, i3, i4));
		int i7 = world1.getBlockId(i2 - PistonBlockTextures.field_31056_b[i6], i3 - PistonBlockTextures.field_31059_c[i6], i4 - PistonBlockTextures.field_31058_d[i6]);
		if(i7 != Block.pistonBase.blockID && i7 != Block.pistonStickyBase.blockID) {
			world1.setBlockWithNotify(i2, i3, i4, 0);
		} else {
			Block.blocksList[i7].onNeighborBlockChange(world1, i2 - PistonBlockTextures.field_31056_b[i6], i3 - PistonBlockTextures.field_31059_c[i6], i4 - PistonBlockTextures.field_31058_d[i6], i5);
		}

	}

	public static int func_31050_c(int i0) {
		return i0 & 7;
	}
}
