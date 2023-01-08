package net.minecraft.src;

import java.util.Random;

public class WorldGenTaiga1 extends WorldGenerator {
	public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
		int i6 = random2.nextInt(5) + 7;
		int i7 = i6 - random2.nextInt(2) - 3;
		int i8 = i6 - i7;
		int i9 = 1 + random2.nextInt(i8 + 1);
		boolean z10 = true;
		if(i4 >= 1 && i4 + i6 + 1 <= 128) {
			int i11;
			int i13;
			int i14;
			int i15;
			int i18;
			for(i11 = i4; i11 <= i4 + 1 + i6 && z10; ++i11) {
				boolean z12 = true;
				if(i11 - i4 < i7) {
					i18 = 0;
				} else {
					i18 = i9;
				}

				for(i13 = i3 - i18; i13 <= i3 + i18 && z10; ++i13) {
					for(i14 = i5 - i18; i14 <= i5 + i18 && z10; ++i14) {
						if(i11 >= 0 && i11 < 128) {
							i15 = world1.getBlockId(i13, i11, i14);
							if(i15 != 0 && i15 != Block.leaves.blockID) {
								z10 = false;
							}
						} else {
							z10 = false;
						}
					}
				}
			}

			if(!z10) {
				return false;
			} else {
				i11 = world1.getBlockId(i3, i4 - 1, i5);
				if((i11 == Block.grass.blockID || i11 == Block.dirt.blockID) && i4 < 128 - i6 - 1) {
					world1.setBlock(i3, i4 - 1, i5, Block.dirt.blockID);
					i18 = 0;

					for(i13 = i4 + i6; i13 >= i4 + i7; --i13) {
						for(i14 = i3 - i18; i14 <= i3 + i18; ++i14) {
							i15 = i14 - i3;

							for(int i16 = i5 - i18; i16 <= i5 + i18; ++i16) {
								int i17 = i16 - i5;
								if((Math.abs(i15) != i18 || Math.abs(i17) != i18 || i18 <= 0) && !Block.opaqueCubeLookup[world1.getBlockId(i14, i13, i16)]) {
									world1.setBlockAndMetadata(i14, i13, i16, Block.leaves.blockID, 1);
								}
							}
						}

						if(i18 >= 1 && i13 == i4 + i7 + 1) {
							--i18;
						} else if(i18 < i9) {
							++i18;
						}
					}

					for(i13 = 0; i13 < i6 - 1; ++i13) {
						i14 = world1.getBlockId(i3, i4 + i13, i5);
						if(i14 == 0 || i14 == Block.leaves.blockID) {
							world1.setBlockAndMetadata(i3, i4 + i13, i5, Block.wood.blockID, 1);
						}
					}

					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
}
