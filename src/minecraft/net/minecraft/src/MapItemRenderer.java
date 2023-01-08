package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

public class MapItemRenderer {
	private int[] field_28159_a = new int[16384];
	private int field_28158_b;
	private GameSettings field_28161_c;
	private FontRenderer field_28160_d;

	public MapItemRenderer(FontRenderer fontRenderer1, GameSettings gameSettings2, RenderEngine renderEngine3) {
		this.field_28161_c = gameSettings2;
		this.field_28160_d = fontRenderer1;
		this.field_28158_b = renderEngine3.allocateAndSetupTexture(new BufferedImage(128, 128, 2));

		for(int i4 = 0; i4 < 16384; ++i4) {
			this.field_28159_a[i4] = 0;
		}

	}

	public void func_28157_a(EntityPlayer entityPlayer1, RenderEngine renderEngine2, MapData mapData3) {
		for(int i4 = 0; i4 < 16384; ++i4) {
			byte b5 = mapData3.field_28176_f[i4];
			if(b5 / 4 == 0) {
				this.field_28159_a[i4] = (i4 + i4 / 128 & 1) * 8 + 16 << 24;
			} else {
				int i6 = MapColor.mapColorArray[b5 / 4].colorValue;
				int i7 = b5 & 3;
				short s8 = 220;
				if(i7 == 2) {
					s8 = 255;
				}

				if(i7 == 0) {
					s8 = 180;
				}

				int i9 = (i6 >> 16 & 255) * s8 / 255;
				int i10 = (i6 >> 8 & 255) * s8 / 255;
				int i11 = (i6 & 255) * s8 / 255;
				if(this.field_28161_c.anaglyph) {
					int i12 = (i9 * 30 + i10 * 59 + i11 * 11) / 100;
					int i13 = (i9 * 30 + i10 * 70) / 100;
					int i14 = (i9 * 30 + i11 * 70) / 100;
					i9 = i12;
					i10 = i13;
					i11 = i14;
				}

				this.field_28159_a[i4] = 0xFF000000 | i9 << 16 | i10 << 8 | i11;
			}
		}

		renderEngine2.func_28150_a(this.field_28159_a, 128, 128, this.field_28158_b);
		byte b15 = 0;
		byte b16 = 0;
		Tessellator tessellator17 = Tessellator.instance;
		float f18 = 0.0F;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.field_28158_b);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		tessellator17.startDrawingQuads();
		tessellator17.addVertexWithUV((double)((float)(b15 + 0) + f18), (double)((float)(b16 + 128) - f18), -0.009999999776482582D, 0.0D, 1.0D);
		tessellator17.addVertexWithUV((double)((float)(b15 + 128) - f18), (double)((float)(b16 + 128) - f18), -0.009999999776482582D, 1.0D, 1.0D);
		tessellator17.addVertexWithUV((double)((float)(b15 + 128) - f18), (double)((float)(b16 + 0) + f18), -0.009999999776482582D, 1.0D, 0.0D);
		tessellator17.addVertexWithUV((double)((float)(b15 + 0) + f18), (double)((float)(b16 + 0) + f18), -0.009999999776482582D, 0.0D, 0.0D);
		tessellator17.draw();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		renderEngine2.bindTexture(renderEngine2.getTexture("/misc/mapicons.png"));
		Iterator iterator19 = mapData3.field_28173_i.iterator();

		while(iterator19.hasNext()) {
			MapCoord mapCoord20 = (MapCoord)iterator19.next();
			GL11.glPushMatrix();
			GL11.glTranslatef((float)b15 + (float)mapCoord20.field_28216_b / 2.0F + 64.0F, (float)b16 + (float)mapCoord20.field_28220_c / 2.0F + 64.0F, -0.02F);
			GL11.glRotatef((float)(mapCoord20.field_28219_d * 360) / 16.0F, 0.0F, 0.0F, 1.0F);
			GL11.glScalef(4.0F, 4.0F, 3.0F);
			GL11.glTranslatef(-0.125F, 0.125F, 0.0F);
			float f21 = (float)(mapCoord20.field_28217_a % 4 + 0) / 4.0F;
			float f22 = (float)(mapCoord20.field_28217_a / 4 + 0) / 4.0F;
			float f23 = (float)(mapCoord20.field_28217_a % 4 + 1) / 4.0F;
			float f24 = (float)(mapCoord20.field_28217_a / 4 + 1) / 4.0F;
			tessellator17.startDrawingQuads();
			tessellator17.addVertexWithUV(-1.0D, 1.0D, 0.0D, (double)f21, (double)f22);
			tessellator17.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)f23, (double)f22);
			tessellator17.addVertexWithUV(1.0D, -1.0D, 0.0D, (double)f23, (double)f24);
			tessellator17.addVertexWithUV(-1.0D, -1.0D, 0.0D, (double)f21, (double)f24);
			tessellator17.draw();
			GL11.glPopMatrix();
		}

		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, -0.04F);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		this.field_28160_d.drawString(mapData3.field_28168_a, b15, b16, 0xFF000000);
		GL11.glPopMatrix();
	}
}
