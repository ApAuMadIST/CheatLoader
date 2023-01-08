package net.minecraft.src;

import java.util.Random;

public class BlockIce extends BlockBreakable {
	public BlockIce(int i1, int i2) {
		super(i1, i2, Material.ice, false);
		this.slipperiness = 0.98F;
		this.setTickOnLoad(true);
	}

	public int getRenderBlockPass() {
		return 1;
	}

	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		return super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, 1 - i5);
	}

	public void harvestBlock(World world1, EntityPlayer entityPlayer2, int i3, int i4, int i5, int i6) {
		super.harvestBlock(world1, entityPlayer2, i3, i4, i5, i6);
		Material material7 = world1.getBlockMaterial(i3, i4 - 1, i5);
		if(material7.getIsSolid() || material7.getIsLiquid()) {
			world1.setBlockWithNotify(i3, i4, i5, Block.waterMoving.blockID);
		}

	}

	public int quantityDropped(Random random1) {
		return 0;
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		if(world1.getSavedLightValue(EnumSkyBlock.Block, i2, i3, i4) > 11 - Block.lightOpacity[this.blockID]) {
			this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
			world1.setBlockWithNotify(i2, i3, i4, Block.waterStill.blockID);
		}

	}

	public int getMobilityFlag() {
		return 0;
	}
}
