package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderSnowball extends Render {
	private int itemIconIndex;

	public RenderSnowball(int i1) {
		this.itemIconIndex = i1;
	}

	public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d2, (float)d4, (float)d6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		this.loadTexture("/gui/items.png");
		Tessellator tessellator10 = Tessellator.instance;
		float f11 = (float)(this.itemIconIndex % 16 * 16 + 0) / 256.0F;
		float f12 = (float)(this.itemIconIndex % 16 * 16 + 16) / 256.0F;
		float f13 = (float)(this.itemIconIndex / 16 * 16 + 0) / 256.0F;
		float f14 = (float)(this.itemIconIndex / 16 * 16 + 16) / 256.0F;
		float f15 = 1.0F;
		float f16 = 0.5F;
		float f17 = 0.25F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator10.startDrawingQuads();
		tessellator10.setNormal(0.0F, 1.0F, 0.0F);
		tessellator10.addVertexWithUV((double)(0.0F - f16), (double)(0.0F - f17), 0.0D, (double)f11, (double)f14);
		tessellator10.addVertexWithUV((double)(f15 - f16), (double)(0.0F - f17), 0.0D, (double)f12, (double)f14);
		tessellator10.addVertexWithUV((double)(f15 - f16), (double)(1.0F - f17), 0.0D, (double)f12, (double)f13);
		tessellator10.addVertexWithUV((double)(0.0F - f16), (double)(1.0F - f17), 0.0D, (double)f11, (double)f13);
		tessellator10.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
