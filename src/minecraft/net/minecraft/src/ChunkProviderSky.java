package net.minecraft.src;

import java.util.Random;

public class ChunkProviderSky implements IChunkProvider {
	private Random field_28087_j;
	private NoiseGeneratorOctaves field_28086_k;
	private NoiseGeneratorOctaves field_28085_l;
	private NoiseGeneratorOctaves field_28084_m;
	private NoiseGeneratorOctaves field_28083_n;
	private NoiseGeneratorOctaves field_28082_o;
	public NoiseGeneratorOctaves field_28096_a;
	public NoiseGeneratorOctaves field_28095_b;
	public NoiseGeneratorOctaves field_28094_c;
	private World field_28081_p;
	private double[] field_28080_q;
	private double[] field_28079_r = new double[256];
	private double[] field_28078_s = new double[256];
	private double[] field_28077_t = new double[256];
	private MapGenBase field_28076_u = new MapGenCaves();
	private BiomeGenBase[] field_28075_v;
	double[] field_28093_d;
	double[] field_28092_e;
	double[] field_28091_f;
	double[] field_28090_g;
	double[] field_28089_h;
	int[][] field_28088_i = new int[32][32];
	private double[] field_28074_w;

	public ChunkProviderSky(World world1, long j2) {
		this.field_28081_p = world1;
		this.field_28087_j = new Random(j2);
		this.field_28086_k = new NoiseGeneratorOctaves(this.field_28087_j, 16);
		this.field_28085_l = new NoiseGeneratorOctaves(this.field_28087_j, 16);
		this.field_28084_m = new NoiseGeneratorOctaves(this.field_28087_j, 8);
		this.field_28083_n = new NoiseGeneratorOctaves(this.field_28087_j, 4);
		this.field_28082_o = new NoiseGeneratorOctaves(this.field_28087_j, 4);
		this.field_28096_a = new NoiseGeneratorOctaves(this.field_28087_j, 10);
		this.field_28095_b = new NoiseGeneratorOctaves(this.field_28087_j, 16);
		this.field_28094_c = new NoiseGeneratorOctaves(this.field_28087_j, 8);
	}

	public void func_28071_a(int i1, int i2, byte[] b3, BiomeGenBase[] biomeGenBase4, double[] d5) {
		byte b6 = 2;
		int i7 = b6 + 1;
		byte b8 = 33;
		int i9 = b6 + 1;
		this.field_28080_q = this.func_28073_a(this.field_28080_q, i1 * b6, 0, i2 * b6, i7, b8, i9);

		for(int i10 = 0; i10 < b6; ++i10) {
			for(int i11 = 0; i11 < b6; ++i11) {
				for(int i12 = 0; i12 < 32; ++i12) {
					double d13 = 0.25D;
					double d15 = this.field_28080_q[((i10 + 0) * i9 + i11 + 0) * b8 + i12 + 0];
					double d17 = this.field_28080_q[((i10 + 0) * i9 + i11 + 1) * b8 + i12 + 0];
					double d19 = this.field_28080_q[((i10 + 1) * i9 + i11 + 0) * b8 + i12 + 0];
					double d21 = this.field_28080_q[((i10 + 1) * i9 + i11 + 1) * b8 + i12 + 0];
					double d23 = (this.field_28080_q[((i10 + 0) * i9 + i11 + 0) * b8 + i12 + 1] - d15) * d13;
					double d25 = (this.field_28080_q[((i10 + 0) * i9 + i11 + 1) * b8 + i12 + 1] - d17) * d13;
					double d27 = (this.field_28080_q[((i10 + 1) * i9 + i11 + 0) * b8 + i12 + 1] - d19) * d13;
					double d29 = (this.field_28080_q[((i10 + 1) * i9 + i11 + 1) * b8 + i12 + 1] - d21) * d13;

					for(int i31 = 0; i31 < 4; ++i31) {
						double d32 = 0.125D;
						double d34 = d15;
						double d36 = d17;
						double d38 = (d19 - d15) * d32;
						double d40 = (d21 - d17) * d32;

						for(int i42 = 0; i42 < 8; ++i42) {
							int i43 = i42 + i10 * 8 << 11 | 0 + i11 * 8 << 7 | i12 * 4 + i31;
							short s44 = 128;
							double d45 = 0.125D;
							double d47 = d34;
							double d49 = (d36 - d34) * d45;

							for(int i51 = 0; i51 < 8; ++i51) {
								int i52 = 0;
								if(d47 > 0.0D) {
									i52 = Block.stone.blockID;
								}

								b3[i43] = (byte)i52;
								i43 += s44;
								d47 += d49;
							}

							d34 += d38;
							d36 += d40;
						}

						d15 += d23;
						d17 += d25;
						d19 += d27;
						d21 += d29;
					}
				}
			}
		}

	}

	public void func_28072_a(int i1, int i2, byte[] b3, BiomeGenBase[] biomeGenBase4) {
		double d5 = 8.0D / 256D;
		this.field_28079_r = this.field_28083_n.generateNoiseOctaves(this.field_28079_r, (double)(i1 * 16), (double)(i2 * 16), 0.0D, 16, 16, 1, d5, d5, 1.0D);
		this.field_28078_s = this.field_28083_n.generateNoiseOctaves(this.field_28078_s, (double)(i1 * 16), 109.0134D, (double)(i2 * 16), 16, 1, 16, d5, 1.0D, d5);
		this.field_28077_t = this.field_28082_o.generateNoiseOctaves(this.field_28077_t, (double)(i1 * 16), (double)(i2 * 16), 0.0D, 16, 16, 1, d5 * 2.0D, d5 * 2.0D, d5 * 2.0D);

		for(int i7 = 0; i7 < 16; ++i7) {
			for(int i8 = 0; i8 < 16; ++i8) {
				BiomeGenBase biomeGenBase9 = biomeGenBase4[i7 + i8 * 16];
				int i10 = (int)(this.field_28077_t[i7 + i8 * 16] / 3.0D + 3.0D + this.field_28087_j.nextDouble() * 0.25D);
				int i11 = -1;
				byte b12 = biomeGenBase9.topBlock;
				byte b13 = biomeGenBase9.fillerBlock;

				for(int i14 = 127; i14 >= 0; --i14) {
					int i15 = (i8 * 16 + i7) * 128 + i14;
					byte b16 = b3[i15];
					if(b16 == 0) {
						i11 = -1;
					} else if(b16 == Block.stone.blockID) {
						if(i11 == -1) {
							if(i10 <= 0) {
								b12 = 0;
								b13 = (byte)Block.stone.blockID;
							}

							i11 = i10;
							if(i14 >= 0) {
								b3[i15] = b12;
							} else {
								b3[i15] = b13;
							}
						} else if(i11 > 0) {
							--i11;
							b3[i15] = b13;
							if(i11 == 0 && b13 == Block.sand.blockID) {
								i11 = this.field_28087_j.nextInt(4);
								b13 = (byte)Block.sandStone.blockID;
							}
						}
					}
				}
			}
		}

	}

	public Chunk prepareChunk(int i1, int i2) {
		return this.provideChunk(i1, i2);
	}

	public Chunk provideChunk(int i1, int i2) {
		this.field_28087_j.setSeed((long)i1 * 341873128712L + (long)i2 * 132897987541L);
		byte[] b3 = new byte[32768];
		Chunk chunk4 = new Chunk(this.field_28081_p, b3, i1, i2);
		this.field_28075_v = this.field_28081_p.getWorldChunkManager().loadBlockGeneratorData(this.field_28075_v, i1 * 16, i2 * 16, 16, 16);
		double[] d5 = this.field_28081_p.getWorldChunkManager().temperature;
		this.func_28071_a(i1, i2, b3, this.field_28075_v, d5);
		this.func_28072_a(i1, i2, b3, this.field_28075_v);
		this.field_28076_u.func_867_a(this, this.field_28081_p, i1, i2, b3);
		chunk4.func_1024_c();
		return chunk4;
	}

	private double[] func_28073_a(double[] d1, int i2, int i3, int i4, int i5, int i6, int i7) {
		if(d1 == null) {
			d1 = new double[i5 * i6 * i7];
		}

		double d8 = 684.412D;
		double d10 = 684.412D;
		double[] d12 = this.field_28081_p.getWorldChunkManager().temperature;
		double[] d13 = this.field_28081_p.getWorldChunkManager().humidity;
		this.field_28090_g = this.field_28096_a.func_4109_a(this.field_28090_g, i2, i4, i5, i7, 1.121D, 1.121D, 0.5D);
		this.field_28089_h = this.field_28095_b.func_4109_a(this.field_28089_h, i2, i4, i5, i7, 200.0D, 200.0D, 0.5D);
		d8 *= 2.0D;
		this.field_28093_d = this.field_28084_m.generateNoiseOctaves(this.field_28093_d, (double)i2, (double)i3, (double)i4, i5, i6, i7, d8 / 80.0D, d10 / 160.0D, d8 / 80.0D);
		this.field_28092_e = this.field_28086_k.generateNoiseOctaves(this.field_28092_e, (double)i2, (double)i3, (double)i4, i5, i6, i7, d8, d10, d8);
		this.field_28091_f = this.field_28085_l.generateNoiseOctaves(this.field_28091_f, (double)i2, (double)i3, (double)i4, i5, i6, i7, d8, d10, d8);
		int i14 = 0;
		int i15 = 0;
		int i16 = 16 / i5;

		for(int i17 = 0; i17 < i5; ++i17) {
			int i18 = i17 * i16 + i16 / 2;

			for(int i19 = 0; i19 < i7; ++i19) {
				int i20 = i19 * i16 + i16 / 2;
				double d21 = d12[i18 * 16 + i20];
				double d23 = d13[i18 * 16 + i20] * d21;
				double d25 = 1.0D - d23;
				d25 *= d25;
				d25 *= d25;
				d25 = 1.0D - d25;
				double d27 = (this.field_28090_g[i15] + 256.0D) / 512.0D;
				d27 *= d25;
				if(d27 > 1.0D) {
					d27 = 1.0D;
				}

				double d29 = this.field_28089_h[i15] / 8000.0D;
				if(d29 < 0.0D) {
					d29 = -d29 * 0.3D;
				}

				d29 = d29 * 3.0D - 2.0D;
				if(d29 > 1.0D) {
					d29 = 1.0D;
				}

				d29 /= 8.0D;
				d29 = 0.0D;
				if(d27 < 0.0D) {
					d27 = 0.0D;
				}

				d27 += 0.5D;
				d29 = d29 * (double)i6 / 16.0D;
				++i15;
				double d31 = (double)i6 / 2.0D;

				for(int i33 = 0; i33 < i6; ++i33) {
					double d34 = 0.0D;
					double d36 = ((double)i33 - d31) * 8.0D / d27;
					if(d36 < 0.0D) {
						d36 *= -1.0D;
					}

					double d38 = this.field_28092_e[i14] / 512.0D;
					double d40 = this.field_28091_f[i14] / 512.0D;
					double d42 = (this.field_28093_d[i14] / 10.0D + 1.0D) / 2.0D;
					if(d42 < 0.0D) {
						d34 = d38;
					} else if(d42 > 1.0D) {
						d34 = d40;
					} else {
						d34 = d38 + (d40 - d38) * d42;
					}

					d34 -= 8.0D;
					byte b44 = 32;
					double d45;
					if(i33 > i6 - b44) {
						d45 = (double)((float)(i33 - (i6 - b44)) / ((float)b44 - 1.0F));
						d34 = d34 * (1.0D - d45) + -30.0D * d45;
					}

					b44 = 8;
					if(i33 < b44) {
						d45 = (double)((float)(b44 - i33) / ((float)b44 - 1.0F));
						d34 = d34 * (1.0D - d45) + -30.0D * d45;
					}

					d1[i14] = d34;
					++i14;
				}
			}
		}

		return d1;
	}

	public boolean chunkExists(int i1, int i2) {
		return true;
	}

	public void populate(IChunkProvider iChunkProvider1, int i2, int i3) {
		BlockSand.fallInstantly = true;
		int i4 = i2 * 16;
		int i5 = i3 * 16;
		BiomeGenBase biomeGenBase6 = this.field_28081_p.getWorldChunkManager().getBiomeGenAt(i4 + 16, i5 + 16);
		this.field_28087_j.setSeed(this.field_28081_p.getRandomSeed());
		long j7 = this.field_28087_j.nextLong() / 2L * 2L + 1L;
		long j9 = this.field_28087_j.nextLong() / 2L * 2L + 1L;
		this.field_28087_j.setSeed((long)i2 * j7 + (long)i3 * j9 ^ this.field_28081_p.getRandomSeed());
		double d11 = 0.25D;
		int i13;
		int i14;
		int i15;
		if(this.field_28087_j.nextInt(4) == 0) {
			i13 = i4 + this.field_28087_j.nextInt(16) + 8;
			i14 = this.field_28087_j.nextInt(128);
			i15 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenLakes(Block.waterStill.blockID)).generate(this.field_28081_p, this.field_28087_j, i13, i14, i15);
		}

		if(this.field_28087_j.nextInt(8) == 0) {
			i13 = i4 + this.field_28087_j.nextInt(16) + 8;
			i14 = this.field_28087_j.nextInt(this.field_28087_j.nextInt(120) + 8);
			i15 = i5 + this.field_28087_j.nextInt(16) + 8;
			if(i14 < 64 || this.field_28087_j.nextInt(10) == 0) {
				(new WorldGenLakes(Block.lavaStill.blockID)).generate(this.field_28081_p, this.field_28087_j, i13, i14, i15);
			}
		}

		int i16;
		for(i13 = 0; i13 < 8; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16) + 8;
			i15 = this.field_28087_j.nextInt(128);
			i16 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenDungeons()).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		for(i13 = 0; i13 < 10; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16);
			i15 = this.field_28087_j.nextInt(128);
			i16 = i5 + this.field_28087_j.nextInt(16);
			(new WorldGenClay(32)).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		for(i13 = 0; i13 < 20; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16);
			i15 = this.field_28087_j.nextInt(128);
			i16 = i5 + this.field_28087_j.nextInt(16);
			(new WorldGenMinable(Block.dirt.blockID, 32)).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		for(i13 = 0; i13 < 10; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16);
			i15 = this.field_28087_j.nextInt(128);
			i16 = i5 + this.field_28087_j.nextInt(16);
			(new WorldGenMinable(Block.gravel.blockID, 32)).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		for(i13 = 0; i13 < 20; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16);
			i15 = this.field_28087_j.nextInt(128);
			i16 = i5 + this.field_28087_j.nextInt(16);
			(new WorldGenMinable(Block.oreCoal.blockID, 16)).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		for(i13 = 0; i13 < 20; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16);
			i15 = this.field_28087_j.nextInt(64);
			i16 = i5 + this.field_28087_j.nextInt(16);
			(new WorldGenMinable(Block.oreIron.blockID, 8)).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		for(i13 = 0; i13 < 2; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16);
			i15 = this.field_28087_j.nextInt(32);
			i16 = i5 + this.field_28087_j.nextInt(16);
			(new WorldGenMinable(Block.oreGold.blockID, 8)).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		for(i13 = 0; i13 < 8; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16);
			i15 = this.field_28087_j.nextInt(16);
			i16 = i5 + this.field_28087_j.nextInt(16);
			(new WorldGenMinable(Block.oreRedstone.blockID, 7)).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		for(i13 = 0; i13 < 1; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16);
			i15 = this.field_28087_j.nextInt(16);
			i16 = i5 + this.field_28087_j.nextInt(16);
			(new WorldGenMinable(Block.oreDiamond.blockID, 7)).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		for(i13 = 0; i13 < 1; ++i13) {
			i14 = i4 + this.field_28087_j.nextInt(16);
			i15 = this.field_28087_j.nextInt(16) + this.field_28087_j.nextInt(16);
			i16 = i5 + this.field_28087_j.nextInt(16);
			(new WorldGenMinable(Block.oreLapis.blockID, 6)).generate(this.field_28081_p, this.field_28087_j, i14, i15, i16);
		}

		d11 = 0.5D;
		i13 = (int)((this.field_28094_c.func_806_a((double)i4 * d11, (double)i5 * d11) / 8.0D + this.field_28087_j.nextDouble() * 4.0D + 4.0D) / 3.0D);
		i14 = 0;
		if(this.field_28087_j.nextInt(10) == 0) {
			++i14;
		}

		if(biomeGenBase6 == BiomeGenBase.forest) {
			i14 += i13 + 5;
		}

		if(biomeGenBase6 == BiomeGenBase.rainforest) {
			i14 += i13 + 5;
		}

		if(biomeGenBase6 == BiomeGenBase.seasonalForest) {
			i14 += i13 + 2;
		}

		if(biomeGenBase6 == BiomeGenBase.taiga) {
			i14 += i13 + 5;
		}

		if(biomeGenBase6 == BiomeGenBase.desert) {
			i14 -= 20;
		}

		if(biomeGenBase6 == BiomeGenBase.tundra) {
			i14 -= 20;
		}

		if(biomeGenBase6 == BiomeGenBase.plains) {
			i14 -= 20;
		}

		int i17;
		for(i15 = 0; i15 < i14; ++i15) {
			i16 = i4 + this.field_28087_j.nextInt(16) + 8;
			i17 = i5 + this.field_28087_j.nextInt(16) + 8;
			WorldGenerator worldGenerator18 = biomeGenBase6.getRandomWorldGenForTrees(this.field_28087_j);
			worldGenerator18.func_517_a(1.0D, 1.0D, 1.0D);
			worldGenerator18.generate(this.field_28081_p, this.field_28087_j, i16, this.field_28081_p.getHeightValue(i16, i17), i17);
		}

		int i23;
		for(i15 = 0; i15 < 2; ++i15) {
			i16 = i4 + this.field_28087_j.nextInt(16) + 8;
			i17 = this.field_28087_j.nextInt(128);
			i23 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenFlowers(Block.plantYellow.blockID)).generate(this.field_28081_p, this.field_28087_j, i16, i17, i23);
		}

		if(this.field_28087_j.nextInt(2) == 0) {
			i15 = i4 + this.field_28087_j.nextInt(16) + 8;
			i16 = this.field_28087_j.nextInt(128);
			i17 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenFlowers(Block.plantRed.blockID)).generate(this.field_28081_p, this.field_28087_j, i15, i16, i17);
		}

		if(this.field_28087_j.nextInt(4) == 0) {
			i15 = i4 + this.field_28087_j.nextInt(16) + 8;
			i16 = this.field_28087_j.nextInt(128);
			i17 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.field_28081_p, this.field_28087_j, i15, i16, i17);
		}

		if(this.field_28087_j.nextInt(8) == 0) {
			i15 = i4 + this.field_28087_j.nextInt(16) + 8;
			i16 = this.field_28087_j.nextInt(128);
			i17 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenFlowers(Block.mushroomRed.blockID)).generate(this.field_28081_p, this.field_28087_j, i15, i16, i17);
		}

		for(i15 = 0; i15 < 10; ++i15) {
			i16 = i4 + this.field_28087_j.nextInt(16) + 8;
			i17 = this.field_28087_j.nextInt(128);
			i23 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenReed()).generate(this.field_28081_p, this.field_28087_j, i16, i17, i23);
		}

		if(this.field_28087_j.nextInt(32) == 0) {
			i15 = i4 + this.field_28087_j.nextInt(16) + 8;
			i16 = this.field_28087_j.nextInt(128);
			i17 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenPumpkin()).generate(this.field_28081_p, this.field_28087_j, i15, i16, i17);
		}

		i15 = 0;
		if(biomeGenBase6 == BiomeGenBase.desert) {
			i15 += 10;
		}

		int i19;
		for(i16 = 0; i16 < i15; ++i16) {
			i17 = i4 + this.field_28087_j.nextInt(16) + 8;
			i23 = this.field_28087_j.nextInt(128);
			i19 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenCactus()).generate(this.field_28081_p, this.field_28087_j, i17, i23, i19);
		}

		for(i16 = 0; i16 < 50; ++i16) {
			i17 = i4 + this.field_28087_j.nextInt(16) + 8;
			i23 = this.field_28087_j.nextInt(this.field_28087_j.nextInt(120) + 8);
			i19 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenLiquids(Block.waterMoving.blockID)).generate(this.field_28081_p, this.field_28087_j, i17, i23, i19);
		}

		for(i16 = 0; i16 < 20; ++i16) {
			i17 = i4 + this.field_28087_j.nextInt(16) + 8;
			i23 = this.field_28087_j.nextInt(this.field_28087_j.nextInt(this.field_28087_j.nextInt(112) + 8) + 8);
			i19 = i5 + this.field_28087_j.nextInt(16) + 8;
			(new WorldGenLiquids(Block.lavaMoving.blockID)).generate(this.field_28081_p, this.field_28087_j, i17, i23, i19);
		}

		this.field_28074_w = this.field_28081_p.getWorldChunkManager().getTemperatures(this.field_28074_w, i4 + 8, i5 + 8, 16, 16);

		for(i16 = i4 + 8; i16 < i4 + 8 + 16; ++i16) {
			for(i17 = i5 + 8; i17 < i5 + 8 + 16; ++i17) {
				i23 = i16 - (i4 + 8);
				i19 = i17 - (i5 + 8);
				int i20 = this.field_28081_p.findTopSolidBlock(i16, i17);
				double d21 = this.field_28074_w[i23 * 16 + i19] - (double)(i20 - 64) / 64.0D * 0.3D;
				if(d21 < 0.5D && i20 > 0 && i20 < 128 && this.field_28081_p.isAirBlock(i16, i20, i17) && this.field_28081_p.getBlockMaterial(i16, i20 - 1, i17).getIsSolid() && this.field_28081_p.getBlockMaterial(i16, i20 - 1, i17) != Material.ice) {
					this.field_28081_p.setBlockWithNotify(i16, i20, i17, Block.snow.blockID);
				}
			}
		}

		BlockSand.fallInstantly = false;
	}

	public boolean saveChunks(boolean z1, IProgressUpdate iProgressUpdate2) {
		return true;
	}

	public boolean unload100OldestChunks() {
		return false;
	}

	public boolean canSave() {
		return true;
	}

	public String makeString() {
		return "RandomLevelSource";
	}
}
