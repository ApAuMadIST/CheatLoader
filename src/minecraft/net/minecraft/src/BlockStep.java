package net.minecraft.src;

import java.util.Random;

public class BlockStep extends Block {
	public static final String[] field_22037_a = new String[]{"stone", "sand", "wood", "cobble"};
	private boolean blockType;

	public BlockStep(int i1, boolean z2) {
		super(i1, 6, Material.rock);
		this.blockType = z2;
		if(!z2) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}

		this.setLightOpacity(255);
	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		return i2 == 0 ? (i1 <= 1 ? 6 : 5) : (i2 == 1 ? (i1 == 0 ? 208 : (i1 == 1 ? 176 : 192)) : (i2 == 2 ? 4 : (i2 == 3 ? 16 : 6)));
	}

	public int getBlockTextureFromSide(int i1) {
		return this.getBlockTextureFromSideAndMetadata(i1, 0);
	}

	public boolean isOpaqueCube() {
		return this.blockType;
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
		if(this != Block.stairSingle) {
			super.onBlockAdded(world1, i2, i3, i4);
		}

		int i5 = world1.getBlockId(i2, i3 - 1, i4);
		int i6 = world1.getBlockMetadata(i2, i3, i4);
		int i7 = world1.getBlockMetadata(i2, i3 - 1, i4);
		if(i6 == i7) {
			if(i5 == stairSingle.blockID) {
				world1.setBlockWithNotify(i2, i3, i4, 0);
				world1.setBlockAndMetadataWithNotify(i2, i3 - 1, i4, Block.stairDouble.blockID, i6);
			}

		}
	}

	public int idDropped(int i1, Random random2) {
		return Block.stairSingle.blockID;
	}

	public int quantityDropped(Random random1) {
		return this.blockType ? 2 : 1;
	}

	protected int damageDropped(int i1) {
		return i1;
	}

	public boolean renderAsNormalBlock() {
		return this.blockType;
	}

	public boolean shouldSideBeRendered(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		if(this != Block.stairSingle) {
			super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5);
		}

		return i5 == 1 ? true : (!super.shouldSideBeRendered(iBlockAccess1, i2, i3, i4, i5) ? false : (i5 == 0 ? true : iBlockAccess1.getBlockId(i2, i3, i4) != this.blockID));
	}
}
