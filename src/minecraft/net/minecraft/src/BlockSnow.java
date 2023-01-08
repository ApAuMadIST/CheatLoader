package net.minecraft.src;

import java.util.Random;

public class BlockSnow extends Block {
	protected BlockSnow(int i1, int i2) {
		super(i1, i2, Material.snow);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setTickOnLoad(true);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockMetadata(i2, i3, i4) & 7;
		return i5 >= 3 ? AxisAlignedBB.getBoundingBoxFromPool((double)i2 + this.minX, (double)i3 + this.minY, (double)i4 + this.minZ, (double)i2 + this.maxX, (double)((float)i3 + 0.5F), (double)i4 + this.maxZ) : null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		int i5 = iBlockAccess1.getBlockMetadata(i2, i3, i4) & 7;
		float f6 = (float)(2 * (1 + i5)) / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f6, 1.0F);
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockId(i2, i3 - 1, i4);
		return i5 != 0 && Block.blocksList[i5].isOpaqueCube() ? world1.getBlockMaterial(i2, i3 - 1, i4).getIsSolid() : false;
	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		this.func_314_h(world1, i2, i3, i4);
	}

	private boolean func_314_h(World world1, int i2, int i3, int i4) {
		if(!this.canPlaceBlockAt(world1, i2, i3, i4)) {
			this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
			world1.setBlockWithNotify(i2, i3, i4, 0);
			return false;
		} else {
			return true;
		}
	}

	public void harvestBlock(World world1, EntityPlayer entityPlayer2, int i3, int i4, int i5, int i6) {
		int i7 = Item.snowball.shiftedIndex;
		float f8 = 0.7F;
		double d9 = (double)(world1.rand.nextFloat() * f8) + (double)(1.0F - f8) * 0.5D;
		double d11 = (double)(world1.rand.nextFloat() * f8) + (double)(1.0F - f8) * 0.5D;
		double d13 = (double)(world1.rand.nextFloat() * f8) + (double)(1.0F - f8) * 0.5D;
		EntityItem entityItem15 = new EntityItem(world1, (double)i3 + d9, (double)i4 + d11, (double)i5 + d13, new ItemStack(i7, 1, 0));
		entityItem15.delayBeforeCanPickup = 10;
		world1.entityJoinedWorld(entityItem15);
		world1.setBlockWithNotify(i3, i4, i5, 0);
		entityPlayer2.addStat(StatList.mineBlockStatArray[this.blockID], 1);
	}

	public int idDropped(int i1, Random random2) {
		return Item.snowball.shiftedIndex;
	}

	public int quantityDropped(Random random1) {
		return 0;
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		if(world1.getSavedLightValue(EnumSkyBlock.Block, i2, i3, i4) > 11) {
			this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
			world1.setBlockWithNotify(i2, i3, i4, 0);
		}

	}

	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		return i5 == 1 ? true : super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5);
	}
}
