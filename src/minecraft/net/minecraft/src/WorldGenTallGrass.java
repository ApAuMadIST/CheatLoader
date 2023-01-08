package net.minecraft.src;

import java.util.Random;

public class WorldGenTallGrass extends WorldGenerator {
	private int field_28060_a;
	private int field_28059_b;

	public WorldGenTallGrass(int i1, int i2) {
		this.field_28060_a = i1;
		this.field_28059_b = i2;
	}

	public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
		int i11;
		for(boolean z6 = false; ((i11 = world1.getBlockId(i3, i4, i5)) == 0 || i11 == Block.leaves.blockID) && i4 > 0; --i4) {
		}

		for(int i7 = 0; i7 < 128; ++i7) {
			int i8 = i3 + random2.nextInt(8) - random2.nextInt(8);
			int i9 = i4 + random2.nextInt(4) - random2.nextInt(4);
			int i10 = i5 + random2.nextInt(8) - random2.nextInt(8);
			if(world1.isAirBlock(i8, i9, i10) && ((BlockFlower)Block.blocksList[this.field_28060_a]).canBlockStay(world1, i8, i9, i10)) {
				world1.setBlockAndMetadata(i8, i9, i10, this.field_28060_a, this.field_28059_b);
			}
		}

		return true;
	}
}
