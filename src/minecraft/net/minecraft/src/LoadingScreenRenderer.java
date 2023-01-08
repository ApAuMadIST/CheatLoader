package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class LoadingScreenRenderer implements IProgressUpdate {
	private String field_1004_a = "";
	private Minecraft mc;
	private String field_1007_c = "";
	private long field_1006_d = System.currentTimeMillis();
	private boolean field_1005_e = false;

	public LoadingScreenRenderer(Minecraft minecraft1) {
		this.mc = minecraft1;
	}

	public void printText(String string1) {
		this.field_1005_e = false;
		this.func_597_c(string1);
	}

	public void func_594_b(String string1) {
		this.field_1005_e = true;
		this.func_597_c(this.field_1007_c);
	}

	public void func_597_c(String string1) {
		if(!this.mc.running) {
			if(!this.field_1005_e) {
				throw new MinecraftError();
			}
		} else {
			this.field_1007_c = string1;
			ScaledResolution scaledResolution2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0.0D, scaledResolution2.field_25121_a, scaledResolution2.field_25120_b, 0.0D, 100.0D, 300.0D);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		}
	}

	public void displayLoadingString(String string1) {
		if(!this.mc.running) {
			if(!this.field_1005_e) {
				throw new MinecraftError();
			}
		} else {
			this.field_1006_d = 0L;
			this.field_1004_a = string1;
			this.setLoadingProgress(-1);
			this.field_1006_d = 0L;
		}
	}

	public void setLoadingProgress(int i1) {
		if(!this.mc.running) {
			if(!this.field_1005_e) {
				throw new MinecraftError();
			}
		} else {
			long j2 = System.currentTimeMillis();
			if(j2 - this.field_1006_d >= 20L) {
				this.field_1006_d = j2;
				ScaledResolution scaledResolution4 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
				int i5 = scaledResolution4.getScaledWidth();
				int i6 = scaledResolution4.getScaledHeight();
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glOrtho(0.0D, scaledResolution4.field_25121_a, scaledResolution4.field_25120_b, 0.0D, 100.0D, 300.0D);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				GL11.glTranslatef(0.0F, 0.0F, -200.0F);
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				Tessellator tessellator7 = Tessellator.instance;
				int i8 = this.mc.renderEngine.getTexture("/gui/background.png");
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, i8);
				float f9 = 32.0F;
				tessellator7.startDrawingQuads();
				tessellator7.setColorOpaque_I(4210752);
				tessellator7.addVertexWithUV(0.0D, (double)i6, 0.0D, 0.0D, (double)((float)i6 / f9));
				tessellator7.addVertexWithUV((double)i5, (double)i6, 0.0D, (double)((float)i5 / f9), (double)((float)i6 / f9));
				tessellator7.addVertexWithUV((double)i5, 0.0D, 0.0D, (double)((float)i5 / f9), 0.0D);
				tessellator7.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
				tessellator7.draw();
				if(i1 >= 0) {
					byte b10 = 100;
					byte b11 = 2;
					int i12 = i5 / 2 - b10 / 2;
					int i13 = i6 / 2 + 16;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tessellator7.startDrawingQuads();
					tessellator7.setColorOpaque_I(8421504);
					tessellator7.addVertex((double)i12, (double)i13, 0.0D);
					tessellator7.addVertex((double)i12, (double)(i13 + b11), 0.0D);
					tessellator7.addVertex((double)(i12 + b10), (double)(i13 + b11), 0.0D);
					tessellator7.addVertex((double)(i12 + b10), (double)i13, 0.0D);
					tessellator7.setColorOpaque_I(8454016);
					tessellator7.addVertex((double)i12, (double)i13, 0.0D);
					tessellator7.addVertex((double)i12, (double)(i13 + b11), 0.0D);
					tessellator7.addVertex((double)(i12 + i1), (double)(i13 + b11), 0.0D);
					tessellator7.addVertex((double)(i12 + i1), (double)i13, 0.0D);
					tessellator7.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}

				this.mc.fontRenderer.drawStringWithShadow(this.field_1007_c, (i5 - this.mc.fontRenderer.getStringWidth(this.field_1007_c)) / 2, i6 / 2 - 4 - 16, 0xFFFFFF);
				this.mc.fontRenderer.drawStringWithShadow(this.field_1004_a, (i5 - this.mc.fontRenderer.getStringWidth(this.field_1004_a)) / 2, i6 / 2 - 4 + 8, 0xFFFFFF);
				Display.update();

				try {
					Thread.yield();
				} catch (Exception exception14) {
				}

			}
		}
	}
}
