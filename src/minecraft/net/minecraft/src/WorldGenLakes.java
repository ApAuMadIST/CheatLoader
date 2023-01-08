package net.minecraft.src;

import java.util.Random;

public class WorldGenLakes extends WorldGenerator {
	private int field_15235_a;

	public WorldGenLakes(int i1) {
		this.field_15235_a = i1;
	}

	public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
		i3 -= 8;

		for(i5 -= 8; i4 > 0 && world1.isAirBlock(i3, i4, i5); --i4) {
		}

		i4 -= 4;
		boolean[] z6 = new boolean[2048];
		int i7 = random2.nextInt(4) + 4;

		int i8;
		for(i8 = 0; i8 < i7; ++i8) {
			double d9 = random2.nextDouble() * 6.0D + 3.0D;
			double d11 = random2.nextDouble() * 4.0D + 2.0D;
			double d13 = random2.nextDouble() * 6.0D + 3.0D;
			double d15 = random2.nextDouble() * (16.0D - d9 - 2.0D) + 1.0D + d9 / 2.0D;
			double d17 = random2.nextDouble() * (8.0D - d11 - 4.0D) + 2.0D + d11 / 2.0D;
			double d19 = random2.nextDouble() * (16.0D - d13 - 2.0D) + 1.0D + d13 / 2.0D;

			for(int i21 = 1; i21 < 15; ++i21) {
				for(int i22 = 1; i22 < 15; ++i22) {
					for(int i23 = 1; i23 < 7; ++i23) {
						double d24 = ((double)i21 - d15) / (d9 / 2.0D);
						double d26 = ((double)i23 - d17) / (d11 / 2.0D);
						double d28 = ((double)i22 - d19) / (d13 / 2.0D);
						double d30 = d24 * d24 + d26 * d26 + d28 * d28;
						if(d30 < 1.0D) {
							z6[(i21 * 16 + i22) * 8 + i23] = true;
						}
					}
				}
			}
		}

		int i10;
		int i32;
		boolean z33;
		for(i8 = 0; i8 < 16; ++i8) {
			for(i32 = 0; i32 < 16; ++i32) {
				for(i10 = 0; i10 < 8; ++i10) {
					z33 = !z6[(i8 * 16 + i32) * 8 + i10] && (i8 < 15 && z6[((i8 + 1) * 16 + i32) * 8 + i10] || i8 > 0 && z6[((i8 - 1) * 16 + i32) * 8 + i10] || i32 < 15 && z6[(i8 * 16 + i32 + 1) * 8 + i10] || i32 > 0 && z6[(i8 * 16 + (i32 - 1)) * 8 + i10] || i10 < 7 && z6[(i8 * 16 + i32) * 8 + i10 + 1] || i10 > 0 && z6[(i8 * 16 + i32) * 8 + (i10 - 1)]);
					if(z33) {
						Material material12 = world1.getBlockMaterial(i3 + i8, i4 + i10, i5 + i32);
						if(i10 >= 4 && material12.getIsLiquid()) {
							return false;
						}

						if(i10 < 4 && !material12.isSolid() && world1.getBlockId(i3 + i8, i4 + i10, i5 + i32) != this.field_15235_a) {
							return false;
						}
					}
				}
			}
		}

		for(i8 = 0; i8 < 16; ++i8) {
			for(i32 = 0; i32 < 16; ++i32) {
				for(i10 = 0; i10 < 8; ++i10) {
					if(z6[(i8 * 16 + i32) * 8 + i10]) {
						world1.setBlock(i3 + i8, i4 + i10, i5 + i32, i10 >= 4 ? 0 : this.field_15235_a);
					}
				}
			}
		}

		for(i8 = 0; i8 < 16; ++i8) {
			for(i32 = 0; i32 < 16; ++i32) {
				for(i10 = 4; i10 < 8; ++i10) {
					if(z6[(i8 * 16 + i32) * 8 + i10] && world1.getBlockId(i3 + i8, i4 + i10 - 1, i5 + i32) == Block.dirt.blockID && world1.getSavedLightValue(EnumSkyBlock.Sky, i3 + i8, i4 + i10, i5 + i32) > 0) {
						world1.setBlock(i3 + i8, i4 + i10 - 1, i5 + i32, Block.grass.blockID);
					}
				}
			}
		}

		if(Block.blocksList[this.field_15235_a].blockMaterial == Material.lava) {
			for(i8 = 0; i8 < 16; ++i8) {
				for(i32 = 0; i32 < 16; ++i32) {
					for(i10 = 0; i10 < 8; ++i10) {
						z33 = !z6[(i8 * 16 + i32) * 8 + i10] && (i8 < 15 && z6[((i8 + 1) * 16 + i32) * 8 + i10] || i8 > 0 && z6[((i8 - 1) * 16 + i32) * 8 + i10] || i32 < 15 && z6[(i8 * 16 + i32 + 1) * 8 + i10] || i32 > 0 && z6[(i8 * 16 + (i32 - 1)) * 8 + i10] || i10 < 7 && z6[(i8 * 16 + i32) * 8 + i10 + 1] || i10 > 0 && z6[(i8 * 16 + i32) * 8 + (i10 - 1)]);
						if(z33 && (i10 < 4 || random2.nextInt(2) != 0) && world1.getBlockMaterial(i3 + i8, i4 + i10, i5 + i32).isSolid()) {
							world1.setBlock(i3 + i8, i4 + i10, i5 + i32, Block.stone.blockID);
						}
					}
				}
			}
		}

		return true;
	}
}
