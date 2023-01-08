package net.minecraft.src;

import java.util.Random;

public class BlockLog extends Block {
	protected BlockLog(int i1) {
		super(i1, Material.wood);
		this.blockIndexInTexture = 20;
	}

	public int quantityDropped(Random random1) {
		return 1;
	}

	public int idDropped(int i1, Random random2) {
		return Block.wood.blockID;
	}

	public void harvestBlock(World world1, EntityPlayer entityPlayer2, int i3, int i4, int i5, int i6) {
		super.harvestBlock(world1, entityPlayer2, i3, i4, i5, i6);
	}

	public void onBlockRemoval(World world1, int i2, int i3, int i4) {
		byte b5 = 4;
		int i6 = b5 + 1;
		if(world1.checkChunksExist(i2 - i6, i3 - i6, i4 - i6, i2 + i6, i3 + i6, i4 + i6)) {
			for(int i7 = -b5; i7 <= b5; ++i7) {
				for(int i8 = -b5; i8 <= b5; ++i8) {
					for(int i9 = -b5; i9 <= b5; ++i9) {
						int i10 = world1.getBlockId(i2 + i7, i3 + i8, i4 + i9);
						if(i10 == Block.leaves.blockID) {
							int i11 = world1.getBlockMetadata(i2 + i7, i3 + i8, i4 + i9);
							if((i11 & 8) == 0) {
								world1.setBlockMetadata(i2 + i7, i3 + i8, i4 + i9, i11 | 8);
							}
						}
					}
				}
			}
		}

	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		return i1 == 1 ? 21 : (i1 == 0 ? 21 : (i2 == 1 ? 116 : (i2 == 2 ? 117 : 20)));
	}

	protected int damageDropped(int i1) {
		return i1;
	}
}
