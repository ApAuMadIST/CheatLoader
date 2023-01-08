package net.minecraft.src;

import java.util.Random;

public class ChunkProviderGenerate implements IChunkProvider {
	private Random rand;
	private NoiseGeneratorOctaves field_912_k;
	private NoiseGeneratorOctaves field_911_l;
	private NoiseGeneratorOctaves field_910_m;
	private NoiseGeneratorOctaves field_909_n;
	private NoiseGeneratorOctaves field_908_o;
	public NoiseGeneratorOctaves field_922_a;
	public NoiseGeneratorOctaves field_921_b;
	public NoiseGeneratorOctaves mobSpawnerNoise;
	private World worldObj;
	private double[] field_4180_q;
	private double[] sandNoise = new double[256];
	private double[] gravelNoise = new double[256];
	private double[] stoneNoise = new double[256];
	private MapGenBase field_902_u = new MapGenCaves();
	private BiomeGenBase[] biomesForGeneration;
	double[] field_4185_d;
	double[] field_4184_e;
	double[] field_4183_f;
	double[] field_4182_g;
	double[] field_4181_h;
	int[][] field_914_i = new int[32][32];
	private double[] generatedTemperatures;

	public ChunkProviderGenerate(World world1, long j2) {
		this.worldObj = world1;
		this.rand = new Random(j2);
		this.field_912_k = new NoiseGeneratorOctaves(this.rand, 16);
		this.field_911_l = new NoiseGeneratorOctaves(this.rand, 16);
		this.field_910_m = new NoiseGeneratorOctaves(this.rand, 8);
		this.field_909_n = new NoiseGeneratorOctaves(this.rand, 4);
		this.field_908_o = new NoiseGeneratorOctaves(this.rand, 4);
		this.field_922_a = new NoiseGeneratorOctaves(this.rand, 10);
		this.field_921_b = new NoiseGeneratorOctaves(this.rand, 16);
		this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
	}

	public void generateTerrain(int i1, int i2, byte[] b3, BiomeGenBase[] biomeGenBase4, double[] d5) {
		byte b6 = 4;
		byte b7 = 64;
		int i8 = b6 + 1;
		byte b9 = 17;
		int i10 = b6 + 1;
		this.field_4180_q = this.func_4061_a(this.field_4180_q, i1 * b6, 0, i2 * b6, i8, b9, i10);

		for(int i11 = 0; i11 < b6; ++i11) {
			for(int i12 = 0; i12 < b6; ++i12) {
				for(int i13 = 0; i13 < 16; ++i13) {
					double d14 = 0.125D;
					double d16 = this.field_4180_q[((i11 + 0) * i10 + i12 + 0) * b9 + i13 + 0];
					double d18 = this.field_4180_q[((i11 + 0) * i10 + i12 + 1) * b9 + i13 + 0];
					double d20 = this.field_4180_q[((i11 + 1) * i10 + i12 + 0) * b9 + i13 + 0];
					double d22 = this.field_4180_q[((i11 + 1) * i10 + i12 + 1) * b9 + i13 + 0];
					double d24 = (this.field_4180_q[((i11 + 0) * i10 + i12 + 0) * b9 + i13 + 1] - d16) * d14;
					double d26 = (this.field_4180_q[((i11 + 0) * i10 + i12 + 1) * b9 + i13 + 1] - d18) * d14;
					double d28 = (this.field_4180_q[((i11 + 1) * i10 + i12 + 0) * b9 + i13 + 1] - d20) * d14;
					double d30 = (this.field_4180_q[((i11 + 1) * i10 + i12 + 1) * b9 + i13 + 1] - d22) * d14;

					for(int i32 = 0; i32 < 8; ++i32) {
						double d33 = 0.25D;
						double d35 = d16;
						double d37 = d18;
						double d39 = (d20 - d16) * d33;
						double d41 = (d22 - d18) * d33;

						for(int i43 = 0; i43 < 4; ++i43) {
							int i44 = i43 + i11 * 4 << 11 | 0 + i12 * 4 << 7 | i13 * 8 + i32;
							short s45 = 128;
							double d46 = 0.25D;
							double d48 = d35;
							double d50 = (d37 - d35) * d46;

							for(int i52 = 0; i52 < 4; ++i52) {
								double d53 = d5[(i11 * 4 + i43) * 16 + i12 * 4 + i52];
								int i55 = 0;
								if(i13 * 8 + i32 < b7) {
									if(d53 < 0.5D && i13 * 8 + i32 >= b7 - 1) {
										i55 = Block.ice.blockID;
									} else {
										i55 = Block.waterStill.blockID;
									}
								}

								if(d48 > 0.0D) {
									i55 = Block.stone.blockID;
								}

								b3[i44] = (byte)i55;
								i44 += s45;
								d48 += d50;
							}

							d35 += d39;
							d37 += d41;
						}

						d16 += d24;
						d18 += d26;
						d20 += d28;
						d22 += d30;
					}
				}
			}
		}

	}

	public void replaceBlocksForBiome(int i1, int i2, byte[] b3, BiomeGenBase[] biomeGenBase4) {
		byte b5 = 64;
		double d6 = 8.0D / 256D;
		this.sandNoise = this.field_909_n.generateNoiseOctaves(this.sandNoise, (double)(i1 * 16), (double)(i2 * 16), 0.0D, 16, 16, 1, d6, d6, 1.0D);
		this.gravelNoise = this.field_909_n.generateNoiseOctaves(this.gravelNoise, (double)(i1 * 16), 109.0134D, (double)(i2 * 16), 16, 1, 16, d6, 1.0D, d6);
		this.stoneNoise = this.field_908_o.generateNoiseOctaves(this.stoneNoise, (double)(i1 * 16), (double)(i2 * 16), 0.0D, 16, 16, 1, d6 * 2.0D, d6 * 2.0D, d6 * 2.0D);

		for(int i8 = 0; i8 < 16; ++i8) {
			for(int i9 = 0; i9 < 16; ++i9) {
				BiomeGenBase biomeGenBase10 = biomeGenBase4[i8 + i9 * 16];
				boolean z11 = this.sandNoise[i8 + i9 * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
				boolean z12 = this.gravelNoise[i8 + i9 * 16] + this.rand.nextDouble() * 0.2D > 3.0D;
				int i13 = (int)(this.stoneNoise[i8 + i9 * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
				int i14 = -1;
				byte b15 = biomeGenBase10.topBlock;
				byte b16 = biomeGenBase10.fillerBlock;

				for(int i17 = 127; i17 >= 0; --i17) {
					int i18 = (i9 * 16 + i8) * 128 + i17;
					if(i17 <= 0 + this.rand.nextInt(5)) {
						b3[i18] = (byte)Block.bedrock.blockID;
					} else {
						byte b19 = b3[i18];
						if(b19 == 0) {
							i14 = -1;
						} else if(b19 == Block.stone.blockID) {
							if(i14 == -1) {
								if(i13 <= 0) {
									b15 = 0;
									b16 = (byte)Block.stone.blockID;
								} else if(i17 >= b5 - 4 && i17 <= b5 + 1) {
									b15 = biomeGenBase10.topBlock;
									b16 = biomeGenBase10.fillerBlock;
									if(z12) {
										b15 = 0;
									}

									if(z12) {
										b16 = (byte)Block.gravel.blockID;
									}

									if(z11) {
										b15 = (byte)Block.sand.blockID;
									}

									if(z11) {
										b16 = (byte)Block.sand.blockID;
									}
								}

								if(i17 < b5 && b15 == 0) {
									b15 = (byte)Block.waterStill.blockID;
								}

								i14 = i13;
								if(i17 >= b5 - 1) {
									b3[i18] = b15;
								} else {
									b3[i18] = b16;
								}
							} else if(i14 > 0) {
								--i14;
								b3[i18] = b16;
								if(i14 == 0 && b16 == Block.sand.blockID) {
									i14 = this.rand.nextInt(4);
									b16 = (byte)Block.sandStone.blockID;
								}
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
		this.rand.setSeed((long)i1 * 341873128712L + (long)i2 * 132897987541L);
		byte[] b3 = new byte[32768];
		Chunk chunk4 = new Chunk(this.worldObj, b3, i1, i2);
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, i1 * 16, i2 * 16, 16, 16);
		double[] d5 = this.worldObj.getWorldChunkManager().temperature;
		this.generateTerrain(i1, i2, b3, this.biomesForGeneration, d5);
		this.replaceBlocksForBiome(i1, i2, b3, this.biomesForGeneration);
		this.field_902_u.func_867_a(this, this.worldObj, i1, i2, b3);
		chunk4.func_1024_c();
		return chunk4;
	}

	private double[] func_4061_a(double[] d1, int i2, int i3, int i4, int i5, int i6, int i7) {
		if(d1 == null) {
			d1 = new double[i5 * i6 * i7];
		}

		double d8 = 684.412D;
		double d10 = 684.412D;
		double[] d12 = this.worldObj.getWorldChunkManager().temperature;
		double[] d13 = this.worldObj.getWorldChunkManager().humidity;
		this.field_4182_g = this.field_922_a.func_4109_a(this.field_4182_g, i2, i4, i5, i7, 1.121D, 1.121D, 0.5D);
		this.field_4181_h = this.field_921_b.func_4109_a(this.field_4181_h, i2, i4, i5, i7, 200.0D, 200.0D, 0.5D);
		this.field_4185_d = this.field_910_m.generateNoiseOctaves(this.field_4185_d, (double)i2, (double)i3, (double)i4, i5, i6, i7, d8 / 80.0D, d10 / 160.0D, d8 / 80.0D);
		this.field_4184_e = this.field_912_k.generateNoiseOctaves(this.field_4184_e, (double)i2, (double)i3, (double)i4, i5, i6, i7, d8, d10, d8);
		this.field_4183_f = this.field_911_l.generateNoiseOctaves(this.field_4183_f, (double)i2, (double)i3, (double)i4, i5, i6, i7, d8, d10, d8);
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
				double d27 = (this.field_4182_g[i15] + 256.0D) / 512.0D;
				d27 *= d25;
				if(d27 > 1.0D) {
					d27 = 1.0D;
				}

				double d29 = this.field_4181_h[i15] / 8000.0D;
				if(d29 < 0.0D) {
					d29 = -d29 * 0.3D;
				}

				d29 = d29 * 3.0D - 2.0D;
				if(d29 < 0.0D) {
					d29 /= 2.0D;
					if(d29 < -1.0D) {
						d29 = -1.0D;
					}

					d29 /= 1.4D;
					d29 /= 2.0D;
					d27 = 0.0D;
				} else {
					if(d29 > 1.0D) {
						d29 = 1.0D;
					}

					d29 /= 8.0D;
				}

				if(d27 < 0.0D) {
					d27 = 0.0D;
				}

				d27 += 0.5D;
				d29 = d29 * (double)i6 / 16.0D;
				double d31 = (double)i6 / 2.0D + d29 * 4.0D;
				++i15;

				for(int i33 = 0; i33 < i6; ++i33) {
					double d34 = 0.0D;
					double d36 = ((double)i33 - d31) * 12.0D / d27;
					if(d36 < 0.0D) {
						d36 *= 4.0D;
					}

					double d38 = this.field_4184_e[i14] / 512.0D;
					double d40 = this.field_4183_f[i14] / 512.0D;
					double d42 = (this.field_4185_d[i14] / 10.0D + 1.0D) / 2.0D;
					if(d42 < 0.0D) {
						d34 = d38;
					} else if(d42 > 1.0D) {
						d34 = d40;
					} else {
						d34 = d38 + (d40 - d38) * d42;
					}

					d34 -= d36;
					if(i33 > i6 - 4) {
						double d44 = (double)((float)(i33 - (i6 - 4)) / 3.0F);
						d34 = d34 * (1.0D - d44) + -10.0D * d44;
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
		BiomeGenBase biomeGenBase6 = this.worldObj.getWorldChunkManager().getBiomeGenAt(i4 + 16, i5 + 16);
		this.rand.setSeed(this.worldObj.getRandomSeed());
		long j7 = this.rand.nextLong() / 2L * 2L + 1L;
		long j9 = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long)i2 * j7 + (long)i3 * j9 ^ this.worldObj.getRandomSeed());
		double d11 = 0.25D;
		int i13;
		int i14;
		int i15;
		if(this.rand.nextInt(4) == 0) {
			i13 = i4 + this.rand.nextInt(16) + 8;
			i14 = this.rand.nextInt(128);
			i15 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenLakes(Block.waterStill.blockID)).generate(this.worldObj, this.rand, i13, i14, i15);
		}

		if(this.rand.nextInt(8) == 0) {
			i13 = i4 + this.rand.nextInt(16) + 8;
			i14 = this.rand.nextInt(this.rand.nextInt(120) + 8);
			i15 = i5 + this.rand.nextInt(16) + 8;
			if(i14 < 64 || this.rand.nextInt(10) == 0) {
				(new WorldGenLakes(Block.lavaStill.blockID)).generate(this.worldObj, this.rand, i13, i14, i15);
			}
		}

		int i16;
		for(i13 = 0; i13 < 8; ++i13) {
			i14 = i4 + this.rand.nextInt(16) + 8;
			i15 = this.rand.nextInt(128);
			i16 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenDungeons()).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		for(i13 = 0; i13 < 10; ++i13) {
			i14 = i4 + this.rand.nextInt(16);
			i15 = this.rand.nextInt(128);
			i16 = i5 + this.rand.nextInt(16);
			(new WorldGenClay(32)).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		for(i13 = 0; i13 < 20; ++i13) {
			i14 = i4 + this.rand.nextInt(16);
			i15 = this.rand.nextInt(128);
			i16 = i5 + this.rand.nextInt(16);
			(new WorldGenMinable(Block.dirt.blockID, 32)).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		for(i13 = 0; i13 < 10; ++i13) {
			i14 = i4 + this.rand.nextInt(16);
			i15 = this.rand.nextInt(128);
			i16 = i5 + this.rand.nextInt(16);
			(new WorldGenMinable(Block.gravel.blockID, 32)).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		for(i13 = 0; i13 < 20; ++i13) {
			i14 = i4 + this.rand.nextInt(16);
			i15 = this.rand.nextInt(128);
			i16 = i5 + this.rand.nextInt(16);
			(new WorldGenMinable(Block.oreCoal.blockID, 16)).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		for(i13 = 0; i13 < 20; ++i13) {
			i14 = i4 + this.rand.nextInt(16);
			i15 = this.rand.nextInt(64);
			i16 = i5 + this.rand.nextInt(16);
			(new WorldGenMinable(Block.oreIron.blockID, 8)).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		for(i13 = 0; i13 < 2; ++i13) {
			i14 = i4 + this.rand.nextInt(16);
			i15 = this.rand.nextInt(32);
			i16 = i5 + this.rand.nextInt(16);
			(new WorldGenMinable(Block.oreGold.blockID, 8)).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		for(i13 = 0; i13 < 8; ++i13) {
			i14 = i4 + this.rand.nextInt(16);
			i15 = this.rand.nextInt(16);
			i16 = i5 + this.rand.nextInt(16);
			(new WorldGenMinable(Block.oreRedstone.blockID, 7)).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		for(i13 = 0; i13 < 1; ++i13) {
			i14 = i4 + this.rand.nextInt(16);
			i15 = this.rand.nextInt(16);
			i16 = i5 + this.rand.nextInt(16);
			(new WorldGenMinable(Block.oreDiamond.blockID, 7)).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		for(i13 = 0; i13 < 1; ++i13) {
			i14 = i4 + this.rand.nextInt(16);
			i15 = this.rand.nextInt(16) + this.rand.nextInt(16);
			i16 = i5 + this.rand.nextInt(16);
			(new WorldGenMinable(Block.oreLapis.blockID, 6)).generate(this.worldObj, this.rand, i14, i15, i16);
		}

		d11 = 0.5D;
		i13 = (int)((this.mobSpawnerNoise.func_806_a((double)i4 * d11, (double)i5 * d11) / 8.0D + this.rand.nextDouble() * 4.0D + 4.0D) / 3.0D);
		i14 = 0;
		if(this.rand.nextInt(10) == 0) {
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
			i16 = i4 + this.rand.nextInt(16) + 8;
			i17 = i5 + this.rand.nextInt(16) + 8;
			WorldGenerator worldGenerator18 = biomeGenBase6.getRandomWorldGenForTrees(this.rand);
			worldGenerator18.func_517_a(1.0D, 1.0D, 1.0D);
			worldGenerator18.generate(this.worldObj, this.rand, i16, this.worldObj.getHeightValue(i16, i17), i17);
		}

		byte b27 = 0;
		if(biomeGenBase6 == BiomeGenBase.forest) {
			b27 = 2;
		}

		if(biomeGenBase6 == BiomeGenBase.seasonalForest) {
			b27 = 4;
		}

		if(biomeGenBase6 == BiomeGenBase.taiga) {
			b27 = 2;
		}

		if(biomeGenBase6 == BiomeGenBase.plains) {
			b27 = 3;
		}

		int i19;
		int i25;
		for(i16 = 0; i16 < b27; ++i16) {
			i17 = i4 + this.rand.nextInt(16) + 8;
			i25 = this.rand.nextInt(128);
			i19 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.plantYellow.blockID)).generate(this.worldObj, this.rand, i17, i25, i19);
		}

		byte b28 = 0;
		if(biomeGenBase6 == BiomeGenBase.forest) {
			b28 = 2;
		}

		if(biomeGenBase6 == BiomeGenBase.rainforest) {
			b28 = 10;
		}

		if(biomeGenBase6 == BiomeGenBase.seasonalForest) {
			b28 = 2;
		}

		if(biomeGenBase6 == BiomeGenBase.taiga) {
			b28 = 1;
		}

		if(biomeGenBase6 == BiomeGenBase.plains) {
			b28 = 10;
		}

		int i20;
		int i21;
		for(i17 = 0; i17 < b28; ++i17) {
			byte b26 = 1;
			if(biomeGenBase6 == BiomeGenBase.rainforest && this.rand.nextInt(3) != 0) {
				b26 = 2;
			}

			i19 = i4 + this.rand.nextInt(16) + 8;
			i20 = this.rand.nextInt(128);
			i21 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenTallGrass(Block.tallGrass.blockID, b26)).generate(this.worldObj, this.rand, i19, i20, i21);
		}

		b28 = 0;
		if(biomeGenBase6 == BiomeGenBase.desert) {
			b28 = 2;
		}

		for(i17 = 0; i17 < b28; ++i17) {
			i25 = i4 + this.rand.nextInt(16) + 8;
			i19 = this.rand.nextInt(128);
			i20 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenDeadBush(Block.deadBush.blockID)).generate(this.worldObj, this.rand, i25, i19, i20);
		}

		if(this.rand.nextInt(2) == 0) {
			i17 = i4 + this.rand.nextInt(16) + 8;
			i25 = this.rand.nextInt(128);
			i19 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.plantRed.blockID)).generate(this.worldObj, this.rand, i17, i25, i19);
		}

		if(this.rand.nextInt(4) == 0) {
			i17 = i4 + this.rand.nextInt(16) + 8;
			i25 = this.rand.nextInt(128);
			i19 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.mushroomBrown.blockID)).generate(this.worldObj, this.rand, i17, i25, i19);
		}

		if(this.rand.nextInt(8) == 0) {
			i17 = i4 + this.rand.nextInt(16) + 8;
			i25 = this.rand.nextInt(128);
			i19 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenFlowers(Block.mushroomRed.blockID)).generate(this.worldObj, this.rand, i17, i25, i19);
		}

		for(i17 = 0; i17 < 10; ++i17) {
			i25 = i4 + this.rand.nextInt(16) + 8;
			i19 = this.rand.nextInt(128);
			i20 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenReed()).generate(this.worldObj, this.rand, i25, i19, i20);
		}

		if(this.rand.nextInt(32) == 0) {
			i17 = i4 + this.rand.nextInt(16) + 8;
			i25 = this.rand.nextInt(128);
			i19 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenPumpkin()).generate(this.worldObj, this.rand, i17, i25, i19);
		}

		i17 = 0;
		if(biomeGenBase6 == BiomeGenBase.desert) {
			i17 += 10;
		}

		for(i25 = 0; i25 < i17; ++i25) {
			i19 = i4 + this.rand.nextInt(16) + 8;
			i20 = this.rand.nextInt(128);
			i21 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenCactus()).generate(this.worldObj, this.rand, i19, i20, i21);
		}

		for(i25 = 0; i25 < 50; ++i25) {
			i19 = i4 + this.rand.nextInt(16) + 8;
			i20 = this.rand.nextInt(this.rand.nextInt(120) + 8);
			i21 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenLiquids(Block.waterMoving.blockID)).generate(this.worldObj, this.rand, i19, i20, i21);
		}

		for(i25 = 0; i25 < 20; ++i25) {
			i19 = i4 + this.rand.nextInt(16) + 8;
			i20 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(112) + 8) + 8);
			i21 = i5 + this.rand.nextInt(16) + 8;
			(new WorldGenLiquids(Block.lavaMoving.blockID)).generate(this.worldObj, this.rand, i19, i20, i21);
		}

		this.generatedTemperatures = this.worldObj.getWorldChunkManager().getTemperatures(this.generatedTemperatures, i4 + 8, i5 + 8, 16, 16);

		for(i25 = i4 + 8; i25 < i4 + 8 + 16; ++i25) {
			for(i19 = i5 + 8; i19 < i5 + 8 + 16; ++i19) {
				i20 = i25 - (i4 + 8);
				i21 = i19 - (i5 + 8);
				int i22 = this.worldObj.findTopSolidBlock(i25, i19);
				double d23 = this.generatedTemperatures[i20 * 16 + i21] - (double)(i22 - 64) / 64.0D * 0.3D;
				if(d23 < 0.5D && i22 > 0 && i22 < 128 && this.worldObj.isAirBlock(i25, i22, i19) && this.worldObj.getBlockMaterial(i25, i22 - 1, i19).getIsSolid() && this.worldObj.getBlockMaterial(i25, i22 - 1, i19) != Material.ice) {
					this.worldObj.setBlockWithNotify(i25, i22, i19, Block.snow.blockID);
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
