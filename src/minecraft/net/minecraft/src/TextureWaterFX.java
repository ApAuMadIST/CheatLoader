package net.minecraft.src;

public class TextureWaterFX extends TextureFX {
	protected float[] field_1158_g = new float[256];
	protected float[] field_1157_h = new float[256];
	protected float[] field_1156_i = new float[256];
	protected float[] field_1155_j = new float[256];
	private int tickCounter = 0;

	public TextureWaterFX() {
		super(Block.waterMoving.blockIndexInTexture);
	}

	public void onTick() {
		++this.tickCounter;

		int i1;
		int i2;
		float f3;
		int i5;
		int i6;
		for(i1 = 0; i1 < 16; ++i1) {
			for(i2 = 0; i2 < 16; ++i2) {
				f3 = 0.0F;

				for(int i4 = i1 - 1; i4 <= i1 + 1; ++i4) {
					i5 = i4 & 15;
					i6 = i2 & 15;
					f3 += this.field_1158_g[i5 + i6 * 16];
				}

				this.field_1157_h[i1 + i2 * 16] = f3 / 3.3F + this.field_1156_i[i1 + i2 * 16] * 0.8F;
			}
		}

		for(i1 = 0; i1 < 16; ++i1) {
			for(i2 = 0; i2 < 16; ++i2) {
				this.field_1156_i[i1 + i2 * 16] += this.field_1155_j[i1 + i2 * 16] * 0.05F;
				if(this.field_1156_i[i1 + i2 * 16] < 0.0F) {
					this.field_1156_i[i1 + i2 * 16] = 0.0F;
				}

				this.field_1155_j[i1 + i2 * 16] -= 0.1F;
				if(Math.random() < 0.05D) {
					this.field_1155_j[i1 + i2 * 16] = 0.5F;
				}
			}
		}

		float[] f12 = this.field_1157_h;
		this.field_1157_h = this.field_1158_g;
		this.field_1158_g = f12;

		for(i2 = 0; i2 < 256; ++i2) {
			f3 = this.field_1158_g[i2];
			if(f3 > 1.0F) {
				f3 = 1.0F;
			}

			if(f3 < 0.0F) {
				f3 = 0.0F;
			}

			float f13 = f3 * f3;
			i5 = (int)(32.0F + f13 * 32.0F);
			i6 = (int)(50.0F + f13 * 64.0F);
			int i7 = 255;
			int i8 = (int)(146.0F + f13 * 50.0F);
			if(this.anaglyphEnabled) {
				int i9 = (i5 * 30 + i6 * 59 + i7 * 11) / 100;
				int i10 = (i5 * 30 + i6 * 70) / 100;
				int i11 = (i5 * 30 + i7 * 70) / 100;
				i5 = i9;
				i6 = i10;
				i7 = i11;
			}

			this.imageData[i2 * 4 + 0] = (byte)i5;
			this.imageData[i2 * 4 + 1] = (byte)i6;
			this.imageData[i2 * 4 + 2] = (byte)i7;
			this.imageData[i2 * 4 + 3] = (byte)i8;
		}

	}
}
