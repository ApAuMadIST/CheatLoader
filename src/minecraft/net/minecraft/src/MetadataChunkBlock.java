package net.minecraft.src;

public class MetadataChunkBlock {
	public final EnumSkyBlock field_1299_a;
	public int field_1298_b;
	public int field_1304_c;
	public int field_1303_d;
	public int field_1302_e;
	public int field_1301_f;
	public int field_1300_g;

	public MetadataChunkBlock(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4, int i5, int i6, int i7) {
		this.field_1299_a = enumSkyBlock1;
		this.field_1298_b = i2;
		this.field_1304_c = i3;
		this.field_1303_d = i4;
		this.field_1302_e = i5;
		this.field_1301_f = i6;
		this.field_1300_g = i7;
	}

	public void func_4127_a(World world1) {
		int i2 = this.field_1302_e - this.field_1298_b + 1;
		int i3 = this.field_1301_f - this.field_1304_c + 1;
		int i4 = this.field_1300_g - this.field_1303_d + 1;
		int i5 = i2 * i3 * i4;
		if(i5 > 32768) {
			System.out.println("Light too large, skipping!");
		} else {
			int i6 = 0;
			int i7 = 0;
			boolean z8 = false;
			boolean z9 = false;

			for(int i10 = this.field_1298_b; i10 <= this.field_1302_e; ++i10) {
				for(int i11 = this.field_1303_d; i11 <= this.field_1300_g; ++i11) {
					int i12 = i10 >> 4;
					int i13 = i11 >> 4;
					boolean z14 = false;
					if(z8 && i12 == i6 && i13 == i7) {
						z14 = z9;
					} else {
						z14 = world1.doChunksNearChunkExist(i10, 0, i11, 1);
						if(z14) {
							Chunk chunk15 = world1.getChunkFromChunkCoords(i10 >> 4, i11 >> 4);
							if(chunk15.func_21167_h()) {
								z14 = false;
							}
						}

						z9 = z14;
						i6 = i12;
						i7 = i13;
					}

					if(z14) {
						if(this.field_1304_c < 0) {
							this.field_1304_c = 0;
						}

						if(this.field_1301_f >= 128) {
							this.field_1301_f = 127;
						}

						for(int i27 = this.field_1304_c; i27 <= this.field_1301_f; ++i27) {
							int i16 = world1.getSavedLightValue(this.field_1299_a, i10, i27, i11);
							boolean z17 = false;
							int i18 = world1.getBlockId(i10, i27, i11);
							int i19 = Block.lightOpacity[i18];
							if(i19 == 0) {
								i19 = 1;
							}

							int i20 = 0;
							if(this.field_1299_a == EnumSkyBlock.Sky) {
								if(world1.canExistingBlockSeeTheSky(i10, i27, i11)) {
									i20 = 15;
								}
							} else if(this.field_1299_a == EnumSkyBlock.Block) {
								i20 = Block.lightValue[i18];
							}

							int i21;
							int i28;
							if(i19 >= 15 && i20 == 0) {
								i28 = 0;
							} else {
								i21 = world1.getSavedLightValue(this.field_1299_a, i10 - 1, i27, i11);
								int i22 = world1.getSavedLightValue(this.field_1299_a, i10 + 1, i27, i11);
								int i23 = world1.getSavedLightValue(this.field_1299_a, i10, i27 - 1, i11);
								int i24 = world1.getSavedLightValue(this.field_1299_a, i10, i27 + 1, i11);
								int i25 = world1.getSavedLightValue(this.field_1299_a, i10, i27, i11 - 1);
								int i26 = world1.getSavedLightValue(this.field_1299_a, i10, i27, i11 + 1);
								i28 = i21;
								if(i22 > i21) {
									i28 = i22;
								}

								if(i23 > i28) {
									i28 = i23;
								}

								if(i24 > i28) {
									i28 = i24;
								}

								if(i25 > i28) {
									i28 = i25;
								}

								if(i26 > i28) {
									i28 = i26;
								}

								i28 -= i19;
								if(i28 < 0) {
									i28 = 0;
								}

								if(i20 > i28) {
									i28 = i20;
								}
							}

							if(i16 != i28) {
								world1.setLightValue(this.field_1299_a, i10, i27, i11, i28);
								i21 = i28 - 1;
								if(i21 < 0) {
									i21 = 0;
								}

								world1.neighborLightPropagationChanged(this.field_1299_a, i10 - 1, i27, i11, i21);
								world1.neighborLightPropagationChanged(this.field_1299_a, i10, i27 - 1, i11, i21);
								world1.neighborLightPropagationChanged(this.field_1299_a, i10, i27, i11 - 1, i21);
								if(i10 + 1 >= this.field_1302_e) {
									world1.neighborLightPropagationChanged(this.field_1299_a, i10 + 1, i27, i11, i21);
								}

								if(i27 + 1 >= this.field_1301_f) {
									world1.neighborLightPropagationChanged(this.field_1299_a, i10, i27 + 1, i11, i21);
								}

								if(i11 + 1 >= this.field_1300_g) {
									world1.neighborLightPropagationChanged(this.field_1299_a, i10, i27, i11 + 1, i21);
								}
							}
						}
					}
				}
			}

		}
	}

	public boolean func_866_a(int i1, int i2, int i3, int i4, int i5, int i6) {
		if(i1 >= this.field_1298_b && i2 >= this.field_1304_c && i3 >= this.field_1303_d && i4 <= this.field_1302_e && i5 <= this.field_1301_f && i6 <= this.field_1300_g) {
			return true;
		} else {
			byte b7 = 1;
			if(i1 >= this.field_1298_b - b7 && i2 >= this.field_1304_c - b7 && i3 >= this.field_1303_d - b7 && i4 <= this.field_1302_e + b7 && i5 <= this.field_1301_f + b7 && i6 <= this.field_1300_g + b7) {
				int i8 = this.field_1302_e - this.field_1298_b;
				int i9 = this.field_1301_f - this.field_1304_c;
				int i10 = this.field_1300_g - this.field_1303_d;
				if(i1 > this.field_1298_b) {
					i1 = this.field_1298_b;
				}

				if(i2 > this.field_1304_c) {
					i2 = this.field_1304_c;
				}

				if(i3 > this.field_1303_d) {
					i3 = this.field_1303_d;
				}

				if(i4 < this.field_1302_e) {
					i4 = this.field_1302_e;
				}

				if(i5 < this.field_1301_f) {
					i5 = this.field_1301_f;
				}

				if(i6 < this.field_1300_g) {
					i6 = this.field_1300_g;
				}

				int i11 = i4 - i1;
				int i12 = i5 - i2;
				int i13 = i6 - i3;
				int i14 = i8 * i9 * i10;
				int i15 = i11 * i12 * i13;
				if(i15 - i14 <= 2) {
					this.field_1298_b = i1;
					this.field_1304_c = i2;
					this.field_1303_d = i3;
					this.field_1302_e = i4;
					this.field_1301_f = i5;
					this.field_1300_g = i6;
					return true;
				}
			}

			return false;
		}
	}
}
