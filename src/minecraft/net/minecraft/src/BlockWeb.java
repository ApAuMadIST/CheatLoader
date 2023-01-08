package net.minecraft.src;

import java.util.Random;

public class BlockWeb extends Block {
	public BlockWeb(int i1, int i2) {
		super(i1, i2, Material.field_31068_A);
	}

	public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
		entity5.isInWeb = true;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		return null;
	}

	public int getRenderType() {
		return 1;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int idDropped(int i1, Random random2) {
		return Item.silk.shiftedIndex;
	}
}
