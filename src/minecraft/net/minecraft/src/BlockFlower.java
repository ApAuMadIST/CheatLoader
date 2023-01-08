package net.minecraft.src;

import java.util.Random;

public class BlockFlower extends Block {
	protected BlockFlower(int i1, int i2) {
		super(i1, Material.plants);
		this.blockIndexInTexture = i2;
		this.setTickOnLoad(true);
		float f3 = 0.2F;
		this.setBlockBounds(0.5F - f3, 0.0F, 0.5F - f3, 0.5F + f3, f3 * 3.0F, 0.5F + f3);
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		return super.canPlaceBlockAt(world1, i2, i3, i4) && this.canThisPlantGrowOnThisBlockID(world1.getBlockId(i2, i3 - 1, i4));
	}

	protected boolean canThisPlantGrowOnThisBlockID(int i1) {
		return i1 == Block.grass.blockID || i1 == Block.dirt.blockID || i1 == Block.tilledField.blockID;
	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		super.onNeighborBlockChange(world1, i2, i3, i4, i5);
		this.func_268_h(world1, i2, i3, i4);
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		this.func_268_h(world1, i2, i3, i4);
	}

	protected final void func_268_h(World world1, int i2, int i3, int i4) {
		if(!this.canBlockStay(world1, i2, i3, i4)) {
			this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
			world1.setBlockWithNotify(i2, i3, i4, 0);
		}

	}

	public boolean canBlockStay(World world1, int i2, int i3, int i4) {
		return (world1.getFullBlockLightValue(i2, i3, i4) >= 8 || world1.canBlockSeeTheSky(i2, i3, i4)) && this.canThisPlantGrowOnThisBlockID(world1.getBlockId(i2, i3 - 1, i4));
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
		return 1;
	}
}
