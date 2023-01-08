package net.minecraft.src;

public class TextureLavaFlowFX extends TextureFX {
	protected float[] field_1143_g = new float[256];
	protected float[] field_1142_h = new float[256];
	protected float[] field_1141_i = new float[256];
	protected float[] field_1140_j = new float[256];
	int field_1139_k = 0;

	public TextureLavaFlowFX() {
		super(Block.lavaMoving.blockIndexInTexture + 1);
		this.tileSize = 2;
	}

	public void onTick() {
		++this.field_1139_k;

		int i2;
		float f3;
		int i5;
		int i6;
		int i7;
		int i8;
		int i9;
		for(int i1 = 0; i1 < 16; ++i1) {
			for(i2 = 0; i2 < 16; ++i2) {
				f3 = 0.0F;
				int i4 = (int)(MathHelper.sin((float)i2 * (float)Math.PI * 2.0F / 16.0F) * 1.2F);
				i5 = (int)(MathHelper.sin((float)i1 * (float)Math.PI * 2.0F / 16.0F) * 1.2F);

				for(i6 = i1 - 1; i6 <= i1 + 1; ++i6) {
					for(i7 = i2 - 1; i7 <= i2 + 1; ++i7) {
						i8 = i6 + i4 & 15;
						i9 = i7 + i5 & 15;
						f3 += this.field_1143_g[i8 + i9 * 16];
					}
				}

				this.field_1142_h[i1 + i2 * 16] = f3 / 10.0F + (this.field_1141_i[(i1 + 0 & 15) + (i2 + 0 & 15) * 16] + this.field_1141_i[(i1 + 1 & 15) + (i2 + 0 & 15) * 16] + this.field_1141_i[(i1 + 1 & 15) + (i2 + 1 & 15) * 16] + this.field_1141_i[(i1 + 0 & 15) + (i2 + 1 & 15) * 16]) / 4.0F * 0.8F;
				this.field_1141_i[i1 + i2 * 16] += this.field_1140_j[i1 + i2 * 16] * 0.01F;
				if(this.field_1141_i[i1 + i2 * 16] < 0.0F) {
					this.field_1141_i[i1 + i2 * 16] = 0.0F;
				}

				this.field_1140_j[i1 + i2 * 16] -= 0.06F;
				if(Math.random() < 0.005D) {
					this.field_1140_j[i1 + i2 * 16] = 1.5F;
				}
			}
		}

		float[] f11 = this.field_1142_h;
		this.field_1142_h = this.field_1143_g;
		this.field_1143_g = f11;

		for(i2 = 0; i2 < 256; ++i2) {
			f3 = this.field_1143_g[i2 - this.field_1139_k / 3 * 16 & 255] * 2.0F;
			if(f3 > 1.0F) {
				f3 = 1.0F;
			}

			if(f3 < 0.0F) {
				f3 = 0.0F;
			}

			i5 = (int)(f3 * 100.0F + 155.0F);
			i6 = (int)(f3 * f3 * 255.0F);
			i7 = (int)(f3 * f3 * f3 * f3 * 128.0F);
			if(this.anaglyphEnabled) {
				i8 = (i5 * 30 + i6 * 59 + i7 * 11) / 100;
				i9 = (i5 * 30 + i6 * 70) / 100;
				int i10 = (i5 * 30 + i7 * 70) / 100;
				i5 = i8;
				i6 = i9;
				i7 = i10;
			}

			this.imageData[i2 * 4 + 0] = (byte)i5;
			this.imageData[i2 * 4 + 1] = (byte)i6;
			this.imageData[i2 * 4 + 2] = (byte)i7;
			this.imageData[i2 * 4 + 3] = -1;
		}

	}
}
