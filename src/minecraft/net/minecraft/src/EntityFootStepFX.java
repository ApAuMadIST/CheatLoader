package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class EntityFootStepFX extends EntityFX {
	private int field_27018_a = 0;
	private int field_27020_o = 0;
	private RenderEngine field_27019_p;

	public EntityFootStepFX(RenderEngine renderEngine1, World world2, double d3, double d5, double d7) {
		super(world2, d3, d5, d7, 0.0D, 0.0D, 0.0D);
		this.field_27019_p = renderEngine1;
		this.motionX = this.motionY = this.motionZ = 0.0D;
		this.field_27020_o = 200;
	}

	public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
		float f8 = ((float)this.field_27018_a + f2) / (float)this.field_27020_o;
		f8 *= f8;
		float f9 = 2.0F - f8 * 2.0F;
		if(f9 > 1.0F) {
			f9 = 1.0F;
		}

		f9 *= 0.2F;
		GL11.glDisable(GL11.GL_LIGHTING);
		float f10 = 0.125F;
		float f11 = (float)(this.posX - interpPosX);
		float f12 = (float)(this.posY - interpPosY);
		float f13 = (float)(this.posZ - interpPosZ);
		float f14 = this.worldObj.getLightBrightness(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
		this.field_27019_p.bindTexture(this.field_27019_p.getTexture("/misc/footprint.png"));
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		tessellator1.startDrawingQuads();
		tessellator1.setColorRGBA_F(f14, f14, f14, f9);
		tessellator1.addVertexWithUV((double)(f11 - f10), (double)f12, (double)(f13 + f10), 0.0D, 1.0D);
		tessellator1.addVertexWithUV((double)(f11 + f10), (double)f12, (double)(f13 + f10), 1.0D, 1.0D);
		tessellator1.addVertexWithUV((double)(f11 + f10), (double)f12, (double)(f13 - f10), 1.0D, 0.0D);
		tessellator1.addVertexWithUV((double)(f11 - f10), (double)f12, (double)(f13 - f10), 0.0D, 0.0D);
		tessellator1.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public void onUpdate() {
		++this.field_27018_a;
		if(this.field_27018_a == this.field_27020_o) {
			this.setEntityDead();
		}

	}

	public int getFXLayer() {
		return 3;
	}
}
