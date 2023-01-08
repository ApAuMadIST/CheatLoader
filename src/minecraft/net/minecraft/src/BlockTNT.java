package net.minecraft.src;

import java.util.Random;

public class BlockTNT extends Block {
	public BlockTNT(int i1, int i2) {
		super(i1, i2, Material.tnt);
	}

	public int getBlockTextureFromSide(int i1) {
		return i1 == 0 ? this.blockIndexInTexture + 2 : (i1 == 1 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture);
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
		super.onBlockAdded(world1, i2, i3, i4);
		if(world1.isBlockIndirectlyGettingPowered(i2, i3, i4)) {
			this.onBlockDestroyedByPlayer(world1, i2, i3, i4, 1);
			world1.setBlockWithNotify(i2, i3, i4, 0);
		}

	}

	public void onNeighborBlockChange(World world1, int i2, int i3, int i4, int i5) {
		if(i5 > 0 && Block.blocksList[i5].canProvidePower() && world1.isBlockIndirectlyGettingPowered(i2, i3, i4)) {
			this.onBlockDestroyedByPlayer(world1, i2, i3, i4, 1);
			world1.setBlockWithNotify(i2, i3, i4, 0);
		}

	}

	public int quantityDropped(Random random1) {
		return 0;
	}

	public void onBlockDestroyedByExplosion(World world1, int i2, int i3, int i4) {
		EntityTNTPrimed entityTNTPrimed5 = new EntityTNTPrimed(world1, (double)((float)i2 + 0.5F), (double)((float)i3 + 0.5F), (double)((float)i4 + 0.5F));
		entityTNTPrimed5.fuse = world1.rand.nextInt(entityTNTPrimed5.fuse / 4) + entityTNTPrimed5.fuse / 8;
		world1.entityJoinedWorld(entityTNTPrimed5);
	}

	public void onBlockDestroyedByPlayer(World world1, int i2, int i3, int i4, int i5) {
		if(!world1.multiplayerWorld) {
			if((i5 & 1) == 0) {
				this.dropBlockAsItem_do(world1, i2, i3, i4, new ItemStack(Block.tnt.blockID, 1, 0));
			} else {
				EntityTNTPrimed entityTNTPrimed6 = new EntityTNTPrimed(world1, (double)((float)i2 + 0.5F), (double)((float)i3 + 0.5F), (double)((float)i4 + 0.5F));
				world1.entityJoinedWorld(entityTNTPrimed6);
				world1.playSoundAtEntity(entityTNTPrimed6, "random.fuse", 1.0F, 1.0F);
			}

		}
	}

	public void onBlockClicked(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		if(entityPlayer5.getCurrentEquippedItem() != null && entityPlayer5.getCurrentEquippedItem().itemID == Item.flintAndSteel.shiftedIndex) {
			world1.setBlockMetadata(i2, i3, i4, 1);
		}

		super.onBlockClicked(world1, i2, i3, i4, entityPlayer5);
	}

	public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		return super.blockActivated(world1, i2, i3, i4, entityPlayer5);
	}
}
