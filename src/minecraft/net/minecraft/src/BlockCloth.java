package net.minecraft.src;

public class BlockCloth extends Block {
	public BlockCloth() {
		super(35, 64, Material.cloth);
	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		if(i2 == 0) {
			return this.blockIndexInTexture;
		} else {
			i2 = ~(i2 & 15);
			return 113 + ((i2 & 8) >> 3) + (i2 & 7) * 16;
		}
	}

	protected int damageDropped(int i1) {
		return i1;
	}

	public static int func_21034_c(int i0) {
		return ~i0 & 15;
	}

	public static int func_21035_d(int i0) {
		return ~i0 & 15;
	}
}
