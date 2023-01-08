package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockPressurePlate extends Block {
	private EnumMobType triggerMobType;

	protected BlockPressurePlate(int i1, int i2, EnumMobType enumMobType3, Material material4) {
		super(i1, i2, material4);
		this.triggerMobType = enumMobType3;
		this.setTickOnLoad(true);
		float f5 = 0.0625F;
		this.setBlockBounds(f5, 0.0F, f5, 1.0F - f5, 0.03125F, 1.0F - f5);
	}

	public int tickRate() {
		return 20;
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

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		return world1.isBlockNormalCube(i2, i3 - 1, i4);
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		boolean z6 = false;
		if(!world1.isBlockNormalCube(i2, i3 - 1, i4)) {
			z6 = true;
		}

		if(z6) {
			this.dropBlockAsItem(world1, i2, i3, i4, world1.getBlockMetadata(i2, i3, i4));
			world1.setBlockWithNotify(i2, i3, i4, 0);
		}

	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		if(!world1.multiplayerWorld) {
			if(world1.getBlockMetadata(i2, i3, i4) != 0) {
				this.setStateIfMobInteractsWithPlate(world1, i2, i3, i4);
			}
		}
	}

	public void onEntityCollidedWithBlock(World world1, int i2, int i3, int i4, Entity entity5) {
		if(!world1.multiplayerWorld) {
			if(world1.getBlockMetadata(i2, i3, i4) != 1) {
				this.setStateIfMobInteractsWithPlate(world1, i2, i3, i4);
			}
		}
	}

	private void setStateIfMobInteractsWithPlate(World world1, int i2, int i3, int i4) {
		boolean z5 = world1.getBlockMetadata(i2, i3, i4) == 1;
		boolean z6 = false;
		float f7 = 0.125F;
		List list8 = null;
		if(this.triggerMobType == EnumMobType.everything) {
			list8 = world1.getEntitiesWithinAABBExcludingEntity((Entity)null, AxisAlignedBB.getBoundingBoxFromPool((double)((float)i2 + f7), (double)i3, (double)((float)i4 + f7), (double)((float)(i2 + 1) - f7), (double)i3 + 0.25D, (double)((float)(i4 + 1) - f7)));
		}

		if(this.triggerMobType == EnumMobType.mobs) {
			list8 = world1.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBoxFromPool((double)((float)i2 + f7), (double)i3, (double)((float)i4 + f7), (double)((float)(i2 + 1) - f7), (double)i3 + 0.25D, (double)((float)(i4 + 1) - f7)));
		}

		if(this.triggerMobType == EnumMobType.players) {
			list8 = world1.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBoxFromPool((double)((float)i2 + f7), (double)i3, (double)((float)i4 + f7), (double)((float)(i2 + 1) - f7), (double)i3 + 0.25D, (double)((float)(i4 + 1) - f7)));
		}

		if(list8.size() > 0) {
			z6 = true;
		}

		if(z6 && !z5) {
			world1.setBlockMetadataWithNotify(i2, i3, i4, 1);
			world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
			world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
			world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
			world1.playSoundEffect((double)i2 + 0.5D, (double)i3 + 0.1D, (double)i4 + 0.5D, "random.click", 0.3F, 0.6F);
		}

		if(!z6 && z5) {
			world1.setBlockMetadataWithNotify(i2, i3, i4, 0);
			world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
			world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
			world1.markBlocksDirty(i2, i3, i4, i2, i3, i4);
			world1.playSoundEffect((double)i2 + 0.5D, (double)i3 + 0.1D, (double)i4 + 0.5D, "random.click", 0.3F, 0.5F);
		}

		if(z6) {
			world1.scheduleBlockUpdate(i2, i3, i4, this.blockID, this.tickRate());
		}

	}

	public void onBlockRemoval(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockMetadata(i2, i3, i4);
		if(i5 > 0) {
			world1.notifyBlocksOfNeighborChange(i2, i3, i4, this.blockID);
			world1.notifyBlocksOfNeighborChange(i2, i3 - 1, i4, this.blockID);
		}

		super.onBlockRemoval(world1, i2, i3, i4);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess1, int i2, int i3, int i4) {
		boolean z5 = iBlockAccess1.getBlockMetadata(i2, i3, i4) == 1;
		float f6 = 0.0625F;
		if(z5) {
			this.setBlockBounds(f6, 0.0F, f6, 1.0F - f6, 0.03125F, 1.0F - f6);
		} else {
			this.setBlockBounds(f6, 0.0F, f6, 1.0F - f6, 0.0625F, 1.0F - f6);
		}

	}

	public boolean isPoweringTo(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		return iBlockAccess1.getBlockMetadata(i2, i3, i4) > 0;
	}

	public boolean isIndirectlyPoweringTo(World world1, int i2, int i3, int i4, int i5) {
		return world1.getBlockMetadata(i2, i3, i4) == 0 ? false : i5 == 1;
	}

	public boolean canProvidePower() {
		return true;
	}

	public void setBlockBoundsForItemRender() {
		float f1 = 0.5F;
		float f2 = 0.125F;
		float f3 = 0.5F;
		this.setBlockBounds(0.5F - f1, 0.5F - f2, 0.5F - f3, 0.5F + f1, 0.5F + f2, 0.5F + f3);
	}

	public int getMobilityFlag() {
		return 1;
	}
}
