package net.minecraft.src;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import org.lwjgl.opengl.GL11;

public class ModTextureAnimation extends TextureFX {
	private final int tickRate;
	private final byte[][] images;
	private int index;
	private int ticks;

	public ModTextureAnimation(int slot, int dst, BufferedImage source, int rate) {
		this(slot, 1, dst, source, rate);
	}

	public ModTextureAnimation(int slot, int size, int dst, BufferedImage source, int rate) {
		super(slot);
		this.index = 0;
		this.ticks = 0;
		this.tileSize = size;
		this.tileImage = dst;
		this.tickRate = rate;
		this.ticks = rate;
		this.bindImage(ModLoader.getMinecraftInstance().renderEngine);
		int targetWidth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH) / 16;
		int targetHeight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT) / 16;
		int width = source.getWidth();
		int height = source.getHeight();
		int images = (int)Math.floor((double)(height / width));
		if(images <= 0) {
			throw new IllegalArgumentException("source has no complete images");
		} else {
			this.images = new byte[images][];
			if(width != targetWidth) {
				BufferedImage i = new BufferedImage(targetWidth, targetHeight * images, 6);
				Graphics2D temp = i.createGraphics();
				temp.drawImage(source, 0, 0, targetWidth, targetHeight * images, 0, 0, width, height, (ImageObserver)null);
				temp.dispose();
				source = i;
			}

			for(int i18 = 0; i18 < images; ++i18) {
				int[] i19 = new int[targetWidth * targetHeight];
				source.getRGB(0, targetHeight * i18, targetWidth, targetHeight, i19, 0, targetWidth);
				this.images[i18] = new byte[targetWidth * targetHeight * 4];

				for(int j = 0; j < i19.length; ++j) {
					int a = i19[j] >> 24 & 255;
					int r = i19[j] >> 16 & 255;
					int g = i19[j] >> 8 & 255;
					int b = i19[j] >> 0 & 255;
					this.images[i18][j * 4 + 0] = (byte)r;
					this.images[i18][j * 4 + 1] = (byte)g;
					this.images[i18][j * 4 + 2] = (byte)b;
					this.images[i18][j * 4 + 3] = (byte)a;
				}
			}

		}
	}

	public void onTick() {
		if(this.ticks >= this.tickRate) {
			++this.index;
			if(this.index >= this.images.length) {
				this.index = 0;
			}

			this.imageData = this.images[this.index];
			this.ticks = 0;
		}

		++this.ticks;
	}
}
