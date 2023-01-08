package net.minecraft.src;

import java.util.Random;

public class TexturePortalFX extends TextureFX {
	private int portalTickCounter = 0;
	private byte[][] portalTextureData = new byte[32][1024];

	public TexturePortalFX() {
		super(Block.portal.blockIndexInTexture);
		Random random1 = new Random(100L);

		for(int i2 = 0; i2 < 32; ++i2) {
			for(int i3 = 0; i3 < 16; ++i3) {
				for(int i4 = 0; i4 < 16; ++i4) {
					float f5 = 0.0F;

					int i6;
					for(i6 = 0; i6 < 2; ++i6) {
						float f7 = (float)(i6 * 8);
						float f8 = (float)(i6 * 8);
						float f9 = ((float)i3 - f7) / 16.0F * 2.0F;
						float f10 = ((float)i4 - f8) / 16.0F * 2.0F;
						if(f9 < -1.0F) {
							f9 += 2.0F;
						}

						if(f9 >= 1.0F) {
							f9 -= 2.0F;
						}

						if(f10 < -1.0F) {
							f10 += 2.0F;
						}

						if(f10 >= 1.0F) {
							f10 -= 2.0F;
						}

						float f11 = f9 * f9 + f10 * f10;
						float f12 = (float)Math.atan2((double)f10, (double)f9) + ((float)i2 / 32.0F * (float)Math.PI * 2.0F - f11 * 10.0F + (float)(i6 * 2)) * (float)(i6 * 2 - 1);
						f12 = (MathHelper.sin(f12) + 1.0F) / 2.0F;
						f12 /= f11 + 1.0F;
						f5 += f12 * 0.5F;
					}

					f5 += random1.nextFloat() * 0.1F;
					i6 = (int)(f5 * 100.0F + 155.0F);
					int i13 = (int)(f5 * f5 * 200.0F + 55.0F);
					int i14 = (int)(f5 * f5 * f5 * f5 * 255.0F);
					int i15 = (int)(f5 * 100.0F + 155.0F);
					int i16 = i4 * 16 + i3;
					this.portalTextureData[i2][i16 * 4 + 0] = (byte)i13;
					this.portalTextureData[i2][i16 * 4 + 1] = (byte)i14;
					this.portalTextureData[i2][i16 * 4 + 2] = (byte)i6;
					this.portalTextureData[i2][i16 * 4 + 3] = (byte)i15;
				}
			}
		}

	}

	public void onTick() {
		++this.portalTickCounter;
		byte[] b1 = this.portalTextureData[this.portalTickCounter & 31];

		for(int i2 = 0; i2 < 256; ++i2) {
			int i3 = b1[i2 * 4 + 0] & 255;
			int i4 = b1[i2 * 4 + 1] & 255;
			int i5 = b1[i2 * 4 + 2] & 255;
			int i6 = b1[i2 * 4 + 3] & 255;
			if(this.anaglyphEnabled) {
				int i7 = (i3 * 30 + i4 * 59 + i5 * 11) / 100;
				int i8 = (i3 * 30 + i4 * 70) / 100;
				int i9 = (i3 * 30 + i5 * 70) / 100;
				i3 = i7;
				i4 = i8;
				i5 = i9;
			}

			this.imageData[i2 * 4 + 0] = (byte)i3;
			this.imageData[i2 * 4 + 1] = (byte)i4;
			this.imageData[i2 * 4 + 2] = (byte)i5;
			this.imageData[i2 * 4 + 3] = (byte)i6;
		}

	}
}
