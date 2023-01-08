package net.minecraft.src;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderPainting extends Render {
	private Random rand = new Random();

	public void func_158_a(EntityPainting entityPainting1, double d2, double d4, double d6, float f8, float f9) {
		this.rand.setSeed(187L);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d2, (float)d4, (float)d6);
		GL11.glRotatef(f8, 0.0F, 1.0F, 0.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		this.loadTexture("/art/kz.png");
		EnumArt enumArt10 = entityPainting1.art;
		float f11 = 0.0625F;
		GL11.glScalef(f11, f11, f11);
		this.func_159_a(entityPainting1, enumArt10.sizeX, enumArt10.sizeY, enumArt10.offsetX, enumArt10.offsetY);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	private void func_159_a(EntityPainting entityPainting1, int i2, int i3, int i4, int i5) {
		float f6 = (float)(-i2) / 2.0F;
		float f7 = (float)(-i3) / 2.0F;
		float f8 = -0.5F;
		float f9 = 0.5F;

		for(int i10 = 0; i10 < i2 / 16; ++i10) {
			for(int i11 = 0; i11 < i3 / 16; ++i11) {
				float f12 = f6 + (float)((i10 + 1) * 16);
				float f13 = f6 + (float)(i10 * 16);
				float f14 = f7 + (float)((i11 + 1) * 16);
				float f15 = f7 + (float)(i11 * 16);
				this.func_160_a(entityPainting1, (f12 + f13) / 2.0F, (f14 + f15) / 2.0F);
				float f16 = (float)(i4 + i2 - i10 * 16) / 256.0F;
				float f17 = (float)(i4 + i2 - (i10 + 1) * 16) / 256.0F;
				float f18 = (float)(i5 + i3 - i11 * 16) / 256.0F;
				float f19 = (float)(i5 + i3 - (i11 + 1) * 16) / 256.0F;
				float f20 = 0.75F;
				float f21 = 0.8125F;
				float f22 = 0.0F;
				float f23 = 0.0625F;
				float f24 = 0.75F;
				float f25 = 0.8125F;
				float f26 = 0.001953125F;
				float f27 = 0.001953125F;
				float f28 = 0.7519531F;
				float f29 = 0.7519531F;
				float f30 = 0.0F;
				float f31 = 0.0625F;
				Tessellator tessellator32 = Tessellator.instance;
				tessellator32.startDrawingQuads();
				tessellator32.setNormal(0.0F, 0.0F, -1.0F);
				tessellator32.addVertexWithUV((double)f12, (double)f15, (double)f8, (double)f17, (double)f18);
				tessellator32.addVertexWithUV((double)f13, (double)f15, (double)f8, (double)f16, (double)f18);
				tessellator32.addVertexWithUV((double)f13, (double)f14, (double)f8, (double)f16, (double)f19);
				tessellator32.addVertexWithUV((double)f12, (double)f14, (double)f8, (double)f17, (double)f19);
				tessellator32.setNormal(0.0F, 0.0F, 1.0F);
				tessellator32.addVertexWithUV((double)f12, (double)f14, (double)f9, (double)f20, (double)f22);
				tessellator32.addVertexWithUV((double)f13, (double)f14, (double)f9, (double)f21, (double)f22);
				tessellator32.addVertexWithUV((double)f13, (double)f15, (double)f9, (double)f21, (double)f23);
				tessellator32.addVertexWithUV((double)f12, (double)f15, (double)f9, (double)f20, (double)f23);
				tessellator32.setNormal(0.0F, -1.0F, 0.0F);
				tessellator32.addVertexWithUV((double)f12, (double)f14, (double)f8, (double)f24, (double)f26);
				tessellator32.addVertexWithUV((double)f13, (double)f14, (double)f8, (double)f25, (double)f26);
				tessellator32.addVertexWithUV((double)f13, (double)f14, (double)f9, (double)f25, (double)f27);
				tessellator32.addVertexWithUV((double)f12, (double)f14, (double)f9, (double)f24, (double)f27);
				tessellator32.setNormal(0.0F, 1.0F, 0.0F);
				tessellator32.addVertexWithUV((double)f12, (double)f15, (double)f9, (double)f24, (double)f26);
				tessellator32.addVertexWithUV((double)f13, (double)f15, (double)f9, (double)f25, (double)f26);
				tessellator32.addVertexWithUV((double)f13, (double)f15, (double)f8, (double)f25, (double)f27);
				tessellator32.addVertexWithUV((double)f12, (double)f15, (double)f8, (double)f24, (double)f27);
				tessellator32.setNormal(-1.0F, 0.0F, 0.0F);
				tessellator32.addVertexWithUV((double)f12, (double)f14, (double)f9, (double)f29, (double)f30);
				tessellator32.addVertexWithUV((double)f12, (double)f15, (double)f9, (double)f29, (double)f31);
				tessellator32.addVertexWithUV((double)f12, (double)f15, (double)f8, (double)f28, (double)f31);
				tessellator32.addVertexWithUV((double)f12, (double)f14, (double)f8, (double)f28, (double)f30);
				tessellator32.setNormal(1.0F, 0.0F, 0.0F);
				tessellator32.addVertexWithUV((double)f13, (double)f14, (double)f8, (double)f29, (double)f30);
				tessellator32.addVertexWithUV((double)f13, (double)f15, (double)f8, (double)f29, (double)f31);
				tessellator32.addVertexWithUV((double)f13, (double)f15, (double)f9, (double)f28, (double)f31);
				tessellator32.addVertexWithUV((double)f13, (double)f14, (double)f9, (double)f28, (double)f30);
				tessellator32.draw();
			}
		}

	}

	private void func_160_a(EntityPainting entityPainting1, float f2, float f3) {
		int i4 = MathHelper.floor_double(entityPainting1.posX);
		int i5 = MathHelper.floor_double(entityPainting1.posY + (double)(f3 / 16.0F));
		int i6 = MathHelper.floor_double(entityPainting1.posZ);
		if(entityPainting1.direction == 0) {
			i4 = MathHelper.floor_double(entityPainting1.posX + (double)(f2 / 16.0F));
		}

		if(entityPainting1.direction == 1) {
			i6 = MathHelper.floor_double(entityPainting1.posZ - (double)(f2 / 16.0F));
		}

		if(entityPainting1.direction == 2) {
			i4 = MathHelper.floor_double(entityPainting1.posX - (double)(f2 / 16.0F));
		}

		if(entityPainting1.direction == 3) {
			i6 = MathHelper.floor_double(entityPainting1.posZ + (double)(f2 / 16.0F));
		}

		float f7 = this.renderManager.worldObj.getLightBrightness(i4, i5, i6);
		GL11.glColor3f(f7, f7, f7);
	}

	public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
		this.func_158_a((EntityPainting)entity1, d2, d4, d6, f8, f9);
	}
}
