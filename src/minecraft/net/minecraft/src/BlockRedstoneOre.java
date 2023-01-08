package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneOre extends Block {
	private boolean field_468_a;

	public BlockRedstoneOre(int i1, int i2, boolean z3) {
		super(i1, i2, Material.rock);
		if(z3) {
			this.setTickOnLoad(true);
		}

		this.field_468_a = z3;
	}

	public int tickRate() {
		return 30;
	}

	public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		this.func_320_h(world1, i2, i3, i4);
		super.onBlockClicked(world1, i2, i3, i4, entityPlayer5);
	}

	public void onEntityWalking(World world1, int i2, int i3, int i4, Entity entity5) {
		this.func_320_h(world1, i2, i3, i4);
		super.onEntityWalking(world1, i2, i3, i4, entity5);
	}

	public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		this.func_320_h(world1, i2, i3, i4);
		return super.blockActivated(world1, i2, i3, i4, entityPlayer5);
	}

	private void func_320_h(World world1, int i2, int i3, int i4) {
		this.func_319_i(world1, i2, i3, i4);
		if(this.blockID == Block.oreRedstone.blockID) {
			world1.setBlockWithNotify(i2, i3, i4, Block.oreRedstoneGlowing.blockID);
		}

	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		if(this.blockID == Block.oreRedstoneGlowing.blockID) {
			world1.setBlockWithNotify(i2, i3, i4, Block.oreRedstone.blockID);
		}

	}

	public int idDropped(int i1, Random random2) {
		return Item.redstone.shiftedIndex;
	}

	public int quantityDropped(Random random1) {
		return 4 + random1.nextInt(2);
	}

	public void randomDisplayTick(World world1, int i2, int i3, int i4, Random random5) {
		if(this.field_468_a) {
			this.func_319_i(world1, i2, i3, i4);
		}

	}

	private void func_319_i(World world1, int i2, int i3, int i4) {
		Random random5 = world1.rand;
		double d6 = 0.0625D;

		for(int i8 = 0; i8 < 6; ++i8) {
			double d9 = (double)((float)i2 + random5.nextFloat());
			double d11 = (double)((float)i3 + random5.nextFloat());
			double d13 = (double)((float)i4 + random5.nextFloat());
			if(i8 == 0 && !world1.isBlockOpaqueCube(i2, i3 + 1, i4)) {
				d11 = (double)(i3 + 1) + d6;
			}

			if(i8 == 1 && !world1.isBlockOpaqueCube(i2, i3 - 1, i4)) {
				d11 = (double)(i3 + 0) - d6;
			}

			if(i8 == 2 && !world1.isBlockOpaqueCube(i2, i3, i4 + 1)) {
				d13 = (double)(i4 + 1) + d6;
			}

			if(i8 == 3 && !world1.isBlockOpaqueCube(i2, i3, i4 - 1)) {
				d13 = (double)(i4 + 0) - d6;
			}

			if(i8 == 4 && !world1.isBlockOpaqueCube(i2 + 1, i3, i4)) {
				d9 = (double)(i2 + 1) + d6;
			}

			if(i8 == 5 && !world1.isBlockOpaqueCube(i2 - 1, i3, i4)) {
				d9 = (double)(i2 + 0) - d6;
			}

			if(d9 < (double)i2 || d9 > (double)(i2 + 1) || d11 < 0.0D || d11 > (double)(i3 + 1) || d13 < (double)i4 || d13 > (double)(i4 + 1)) {
				world1.spawnParticle("reddust", d9, d11, d13, 0.0D, 0.0D, 0.0D);
			}
		}

	}
}
