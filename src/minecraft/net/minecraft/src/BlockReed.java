package net.minecraft.src;

import java.util.Random;

public class BlockReed extends Block {
	protected BlockReed(int i1, int i2) {
		super(i1, Material.plants);
		this.blockIndexInTexture = i2;
		float f3 = 0.375F;
		this.setBlockBounds(0.5F - f3, 0.0F, 0.5F - f3, 0.5F + f3, 1.0F, 0.5F + f3);
		this.setTickOnLoad(true);
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		if(world1.isAirBlock(i2, i3 + 1, i4)) {
			int i6;
			for(i6 = 1; world1.getBlockId(i2, i3 - i6, i4) == this.blockID; ++i6) {
			}

			if(i6 < 3) {
				int i7 = world1.getBlockMetadata(i2, i3, i4);
				if(i7 == 15) {
					world1.setBlockWithNotify(i2, i3 + 1, i4, this.blockID);
					world1.setBlockMetadataWithNotify(i2, i3, i4, 0);
				} else {
					world1.setBlockMetadataWithNotify(i2, i3, i4, i7 + 1);
				}
			}
		}

	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockId(i2, i3 - 1, i4);
		return i5 == this.blockID ? true : (i5 != Block.grass.blockID && i5 != Block.dirt.blockID ? false : (world1.getBlockMaterial(i2 - 1, i3 - 1, i4) == Material.water ? true : (world1.getBlockMaterial(i2 + 1, i3 - 1, i4) == Material.water ? true : (world1.getBlockMaterial(i2, i3 - 1, i4 - 1) == Material.water ? true : world1.getBlockMaterial(i2, i3 - 1, i4 + 1) == Material.water))));
	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		this.checkBlockCoordValid(world1, i2, i3, i4);
	}

	protected final void checkBlockCoordValid(World world1, int i2, int i3, int i4) {
		if(!this.canBlockStay(world1, i2, i3, i4)) {
			this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
			world1.setBlockWithNotify(i2, i3, i4, 0);
		}

	}

	public boolean canBlockStay(World world1, int i2, int i3, int i4) {
		return this.canPlaceBlockAt(world1, i2, i3, i4);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		return null;
	}

	public int idDropped(int i1, Random random2) {
		return Item.reed.shiftedIndex;
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
