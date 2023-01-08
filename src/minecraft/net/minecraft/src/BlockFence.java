package net.minecraft.src;

public class BlockFence extends Block {
	public BlockFence(int i1, int i2) {
		super(i1, i2, Material.wood);
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		return world1.getBlockId(i2, i3 - 1, i4) == this.blockID ? true : (!world1.getBlockMaterial(i2, i3 - 1, i4).isSolid() ? false : super.canPlaceBlockAt(world1, i2, i3, i4));
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		return AxisAlignedBB.getBoundingBoxFromPool((double)i2, (double)i3, (double)i4, (double)(i2 + 1), (double)((float)i3 + 1.5F), (double)(i4 + 1));
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 11;
	}
}
