package net.minecraft.src;

import java.util.Random;

public class BlockStationary extends BlockFluid {
	protected BlockStationary(int i1, Material material2) {
		super(i1, material2);
		this.setTickOnLoad(false);
		if(material2 == Material.lava) {
			this.setTickOnLoad(true);
		}

	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		super.onNeighborBlockChange(world1, i2, i3, i4, i5);
		if(world1.getBlockId(i2, i3, i4) == this.blockID) {
			this.func_30004_j(world1, i2, i3, i4);
		}

	}

	private void func_30004_j(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockMetadata(i2, i3, i4);
		world1.editingBlocks = true;
		world1.setBlockAndMetadata(i2, i3, i4, this.blockID - 1, i5);
		world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
		world1.scheduleBlockUpdate(i2, i3, i4, this.blockID - 1, this.tickRate());
		world1.editingBlocks = false;
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		if(this.blockMaterial == Material.lava) {
			int i6 = random5.nextInt(3);

			for(int i7 = 0; i7 < i6; ++i7) {
				i2 += random5.nextInt(3) - 1;
				++i3;
				i4 += random5.nextInt(3) - 1;
				int i8 = world1.getBlockId(i2, i3, i4);
				if(i8 == 0) {
					if(this.func_301_k(world1, i2 - 1, i3, i4) || this.func_301_k(world1, i2 + 1, i3, i4) || this.func_301_k(world1, i2, i3, i4 - 1) || this.func_301_k(world1, i2, i3, i4 + 1) || this.func_301_k(world1, i2, i3 - 1, i4) || this.func_301_k(world1, i2, i3 + 1, i4)) {
						world1.setBlockWithNotify(i2, i3, i4, Block.fire.blockID);
						return;
					}
				} else if(Block.blocksList[i8].blockMaterial.getIsSolid()) {
					return;
				}
			}
		}

	}

	private boolean func_301_k(World world1, int i2, int i3, int i4) {
		return world1.getBlockMaterial(i2, i3, i4).getBurning();
	}
}
