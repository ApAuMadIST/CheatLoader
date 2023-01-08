package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

public class TextureWatchFX extends TextureFX {
	private Minecraft mc;
	private int[] watchIconImageData = new int[256];
	private int[] dialImageData = new int[256];
	private double field_4222_j;
	private double field_4221_k;

	public TextureWatchFX(Minecraft minecraft1) {
		super(Item.pocketSundial.getIconFromDamage(0));
		this.mc = minecraft1;
		this.tileImage = 1;

		try {
			BufferedImage bufferedImage2 = ImageIO.read(Minecraft.class.getResource("/gui/items.png"));
			int i3 = this.iconIndex % 16 * 16;
			int i4 = this.iconIndex / 16 * 16;
			bufferedImage2.getRGB(i3, i4, 16, 16, this.watchIconImageData, 0, 16);
			bufferedImage2 = ImageIO.read(Minecraft.class.getResource("/misc/dial.png"));
			bufferedImage2.getRGB(0, 0, 16, 16, this.dialImageData, 0, 16);
		} catch (IOException iOException5) {
			iOException5.printStackTrace();
		}

	}

	public void onTick() {
		double d1 = 0.0D;
		if(this.mc.theWorld != null && this.mc.thePlayer != null) {
			float f3 = this.mc.theWorld.getCelestialAngle(1.0F);
			d1 = (double)(-f3 * (float)Math.PI * 2.0F);
			if(this.mc.theWorld.worldProvider.isNether) {
				d1 = Math.random() * (double)(float)Math.PI * 2.0D;
			}
		}

		double d22;
		for(d22 = d1 - this.field_4222_j; d22 < -3.141592653589793D; d22 += Math.PI * 2D) {
		}

		while(d22 >= Math.PI) {
			d22 -= Math.PI * 2D;
		}

		if(d22 < -1.0D) {
			d22 = -1.0D;
		}

		if(d22 > 1.0D) {
			d22 = 1.0D;
		}

		this.field_4221_k += d22 * 0.1D;
		this.field_4221_k *= 0.8D;
		this.field_4222_j += this.field_4221_k;
		double d5 = Math.sin(this.field_4222_j);
		double d7 = Math.cos(this.field_4222_j);

		for(int i9 = 0; i9 < 256; ++i9) {
			int i10 = this.watchIconImageData[i9] >> 24 & 255;
			int i11 = this.watchIconImageData[i9] >> 16 & 255;
			int i12 = this.watchIconImageData[i9] >> 8 & 255;
			int i13 = this.watchIconImageData[i9] >> 0 & 255;
			if(i11 == i13 && i12 == 0 && i13 > 0) {
				double d14 = -((double)(i9 % 16) / 15.0D - 0.5D);
				double d16 = (double)(i9 / 16) / 15.0D - 0.5D;
				int i18 = i11;
				int i19 = (int)((d14 * d7 + d16 * d5 + 0.5D) * 16.0D);
				int i20 = (int)((d16 * d7 - d14 * d5 + 0.5D) * 16.0D);
				int i21 = (i19 & 15) + (i20 & 15) * 16;
				i10 = this.dialImageData[i21] >> 24 & 255;
				i11 = (this.dialImageData[i21] >> 16 & 255) * i11 / 255;
				i12 = (this.dialImageData[i21] >> 8 & 255) * i18 / 255;
				i13 = (this.dialImageData[i21] >> 0 & 255) * i18 / 255;
			}

			if(this.anaglyphEnabled) {
				int i23 = (i11 * 30 + i12 * 59 + i13 * 11) / 100;
				int i15 = (i11 * 30 + i12 * 70) / 100;
				int i24 = (i11 * 30 + i13 * 70) / 100;
				i11 = i23;
				i12 = i15;
				i13 = i24;
			}

			this.imageData[i9 * 4 + 0] = (byte)i11;
			this.imageData[i9 * 4 + 1] = (byte)i12;
			this.imageData[i9 * 4 + 2] = (byte)i13;
			this.imageData[i9 * 4 + 3] = (byte)i10;
		}

	}
}
