package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class TerrainTextureManager {
	private float[] field_1181_a = new float[768];
	private int[] field_1180_b = new int[5120];
	private int[] field_1186_c = new int[5120];
	private int[] field_1185_d = new int[5120];
	private int[] field_1184_e = new int[5120];
	private int[] field_1183_f = new int[34];
	private int[] field_1182_g = new int[768];

	public TerrainTextureManager() {
		try {
			BufferedImage bufferedImage1 = ImageIO.read(TerrainTextureManager.class.getResource("/terrain.png"));
			int[] i2 = new int[65536];
			bufferedImage1.getRGB(0, 0, 256, 256, i2, 0, 256);

			for(int i3 = 0; i3 < 256; ++i3) {
				int i4 = 0;
				int i5 = 0;
				int i6 = 0;
				int i7 = i3 % 16 * 16;
				int i8 = i3 / 16 * 16;
				int i9 = 0;

				for(int i10 = 0; i10 < 16; ++i10) {
					for(int i11 = 0; i11 < 16; ++i11) {
						int i12 = i2[i11 + i7 + (i10 + i8) * 256];
						int i13 = i12 >> 24 & 255;
						if(i13 > 128) {
							i4 += i12 >> 16 & 255;
							i5 += i12 >> 8 & 255;
							i6 += i12 & 255;
							++i9;
						}
					}

					if(i9 == 0) {
						++i9;
					}

					this.field_1181_a[i3 * 3 + 0] = (float)(i4 / i9);
					this.field_1181_a[i3 * 3 + 1] = (float)(i5 / i9);
					this.field_1181_a[i3 * 3 + 2] = (float)(i6 / i9);
				}
			}
		} catch (IOException iOException14) {
			iOException14.printStackTrace();
		}

		for(int i15 = 0; i15 < 256; ++i15) {
			if(Block.blocksList[i15] != null) {
				this.field_1182_g[i15 * 3 + 0] = Block.blocksList[i15].getBlockTextureFromSide(1);
				this.field_1182_g[i15 * 3 + 1] = Block.blocksList[i15].getBlockTextureFromSide(2);
				this.field_1182_g[i15 * 3 + 2] = Block.blocksList[i15].getBlockTextureFromSide(3);
			}
		}

	}

	public void func_799_a(IsoImageBuffer isoImageBuffer1) {
		World world2 = isoImageBuffer1.worldObj;
		if(world2 == null) {
			isoImageBuffer1.field_1351_f = true;
			isoImageBuffer1.field_1352_e = true;
		} else {
			int i3 = isoImageBuffer1.field_1354_c * 16;
			int i4 = isoImageBuffer1.field_1353_d * 16;
			int i5 = i3 + 16;
			int i6 = i4 + 16;
			Chunk chunk7 = world2.getChunkFromChunkCoords(isoImageBuffer1.field_1354_c, isoImageBuffer1.field_1353_d);
			if(chunk7.func_21167_h()) {
				isoImageBuffer1.field_1351_f = true;
				isoImageBuffer1.field_1352_e = true;
			} else {
				isoImageBuffer1.field_1351_f = false;
				Arrays.fill(this.field_1186_c, 0);
				Arrays.fill(this.field_1185_d, 0);
				Arrays.fill(this.field_1183_f, 160);

				for(int i8 = i6 - 1; i8 >= i4; --i8) {
					for(int i9 = i5 - 1; i9 >= i3; --i9) {
						int i10 = i9 - i3;
						int i11 = i8 - i4;
						int i12 = i10 + i11;
						boolean z13 = true;

						for(int i14 = 0; i14 < 128; ++i14) {
							int i15 = i11 - i10 - i14 + 160 - 16;
							if(i15 < this.field_1183_f[i12] || i15 < this.field_1183_f[i12 + 1]) {
								Block block16 = Block.blocksList[world2.getBlockId(i9, i14, i8)];
								if(block16 == null) {
									z13 = false;
								} else if(block16.blockMaterial == Material.water) {
									int i24 = world2.getBlockId(i9, i14 + 1, i8);
									if(i24 == 0 || Block.blocksList[i24].blockMaterial != Material.water) {
										float f25 = (float)i14 / 127.0F * 0.6F + 0.4F;
										float f26 = world2.getLightBrightness(i9, i14 + 1, i8) * f25;
										if(i15 >= 0 && i15 < 160) {
											int i27 = i12 + i15 * 32;
											if(i12 >= 0 && i12 <= 32 && this.field_1185_d[i27] <= i14) {
												this.field_1185_d[i27] = i14;
												this.field_1184_e[i27] = (int)(f26 * 127.0F);
											}

											if(i12 >= -1 && i12 <= 31 && this.field_1185_d[i27 + 1] <= i14) {
												this.field_1185_d[i27 + 1] = i14;
												this.field_1184_e[i27 + 1] = (int)(f26 * 127.0F);
											}

											z13 = false;
										}
									}
								} else {
									if(z13) {
										if(i15 < this.field_1183_f[i12]) {
											this.field_1183_f[i12] = i15;
										}

										if(i15 < this.field_1183_f[i12 + 1]) {
											this.field_1183_f[i12 + 1] = i15;
										}
									}

									float f17 = (float)i14 / 127.0F * 0.6F + 0.4F;
									int i18;
									int i19;
									float f20;
									float f22;
									if(i15 >= 0 && i15 < 160) {
										i18 = i12 + i15 * 32;
										i19 = this.field_1182_g[block16.blockID * 3 + 0];
										f20 = (world2.getLightBrightness(i9, i14 + 1, i8) * 0.8F + 0.2F) * f17;
										if(i12 >= 0 && this.field_1186_c[i18] <= i14) {
											this.field_1186_c[i18] = i14;
											this.field_1180_b[i18] = 0xFF000000 | (int)(this.field_1181_a[i19 * 3 + 0] * f20) << 16 | (int)(this.field_1181_a[i19 * 3 + 1] * f20) << 8 | (int)(this.field_1181_a[i19 * 3 + 2] * f20);
										}

										if(i12 < 31) {
											f22 = f20 * 0.9F;
											if(this.field_1186_c[i18 + 1] <= i14) {
												this.field_1186_c[i18 + 1] = i14;
												this.field_1180_b[i18 + 1] = 0xFF000000 | (int)(this.field_1181_a[i19 * 3 + 0] * f22) << 16 | (int)(this.field_1181_a[i19 * 3 + 1] * f22) << 8 | (int)(this.field_1181_a[i19 * 3 + 2] * f22);
											}
										}
									}

									if(i15 >= -1 && i15 < 159) {
										i18 = i12 + (i15 + 1) * 32;
										i19 = this.field_1182_g[block16.blockID * 3 + 1];
										f20 = world2.getLightBrightness(i9 - 1, i14, i8) * 0.8F + 0.2F;
										int i21 = this.field_1182_g[block16.blockID * 3 + 2];
										f22 = world2.getLightBrightness(i9, i14, i8 + 1) * 0.8F + 0.2F;
										float f23;
										if(i12 >= 0) {
											f23 = f20 * f17 * 0.6F;
											if(this.field_1186_c[i18] <= i14 - 1) {
												this.field_1186_c[i18] = i14 - 1;
												this.field_1180_b[i18] = 0xFF000000 | (int)(this.field_1181_a[i19 * 3 + 0] * f23) << 16 | (int)(this.field_1181_a[i19 * 3 + 1] * f23) << 8 | (int)(this.field_1181_a[i19 * 3 + 2] * f23);
											}
										}

										if(i12 < 31) {
											f23 = f22 * 0.9F * f17 * 0.4F;
											if(this.field_1186_c[i18 + 1] <= i14 - 1) {
												this.field_1186_c[i18 + 1] = i14 - 1;
												this.field_1180_b[i18 + 1] = 0xFF000000 | (int)(this.field_1181_a[i21 * 3 + 0] * f23) << 16 | (int)(this.field_1181_a[i21 * 3 + 1] * f23) << 8 | (int)(this.field_1181_a[i21 * 3 + 2] * f23);
											}
										}
									}
								}
							}
						}
					}
				}

				this.func_800_a();
				if(isoImageBuffer1.field_1348_a == null) {
					isoImageBuffer1.field_1348_a = new BufferedImage(32, 160, 2);
				}

				isoImageBuffer1.field_1348_a.setRGB(0, 0, 32, 160, this.field_1180_b, 0, 32);
				isoImageBuffer1.field_1352_e = true;
			}
		}
	}

	private void func_800_a() {
		for(int i1 = 0; i1 < 32; ++i1) {
			for(int i2 = 0; i2 < 160; ++i2) {
				int i3 = i1 + i2 * 32;
				if(this.field_1186_c[i3] == 0) {
					this.field_1180_b[i3] = 0;
				}

				if(this.field_1185_d[i3] > this.field_1186_c[i3]) {
					int i4 = this.field_1180_b[i3] >> 24 & 255;
					this.field_1180_b[i3] = ((this.field_1180_b[i3] & 16711422) >> 1) + this.field_1184_e[i3];
					if(i4 < 128) {
						this.field_1180_b[i3] = Integer.MIN_VALUE + this.field_1184_e[i3] * 2;
					} else {
						this.field_1180_b[i3] |= 0xFF000000;
					}
				}
			}
		}

	}
}
