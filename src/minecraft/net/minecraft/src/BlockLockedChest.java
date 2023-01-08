package net.minecraft.src;

import java.util.Random;

public class BlockLockedChest extends Block {
	protected BlockLockedChest(int i1) {
		super(i1, Material.wood);
		this.blockIndexInTexture = 26;
	}

	public int getBlockTexture(IBlockAccess iBlockAccess1, int i2, int i3, int i4, int i5) {
		if(i5 == 1) {
			return this.blockIndexInTexture - 1;
		} else if(i5 == 0) {
			return this.blockIndexInTexture - 1;
		} else {
			int i6 = iBlockAccess1.getBlockId(i2, i3, i4 - 1);
			int i7 = iBlockAccess1.getBlockId(i2, i3, i4 + 1);
			int i8 = iBlockAccess1.getBlockId(i2 - 1, i3, i4);
			int i9 = iBlockAccess1.getBlockId(i2 + 1, i3, i4);
			byte b10 = 3;
			if(Block.opaqueCubeLookup[i6] && !Block.opaqueCubeLookup[i7]) {
				b10 = 3;
			}

			if(Block.opaqueCubeLookup[i7] && !Block.opaqueCubeLookup[i6]) {
				b10 = 2;
			}

			if(Block.opaqueCubeLookup[i8] && !Block.opaqueCubeLookup[i9]) {
				b10 = 5;
			}

			if(Block.opaqueCubeLookup[i9] && !Block.opaqueCubeLookup[i8]) {
				b10 = 4;
			}

			return i5 == b10 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture;
		}
	}

	public int getBlockTextureFromSide(int i1) {
		return i1 == 1 ? this.blockIndexInTexture - 1 : (i1 == 0 ? this.blockIndexInTexture - 1 : (i1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture));
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		return true;
	}

	public void updateTick(World world1, int i2, int i3, int i4, Random random5) {
		world1.setBlockWithNotify(i2, i3, i4, 0);
	}
}
