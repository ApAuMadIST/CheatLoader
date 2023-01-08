package net.minecraft.src;

public class BlockSoulSand extends Block {
	public BlockSoulSand(int i1, int i2) {
		super(i1, i2, Material.sand);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		float f5 = 0.125F;
		return AxisAlignedBB.getBoundingBoxFromPool((double)i2, (double)i3, (double)i4, (double)(i2 + 1), (double)((float)(i3 + 1) - f5), (double)(i4 + 1));
	}

	public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
		entity5.motionX *= 0.4D;
		entity5.motionZ *= 0.4D;
	}
}
