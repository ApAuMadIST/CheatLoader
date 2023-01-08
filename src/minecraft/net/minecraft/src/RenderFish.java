package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderFish extends Render {
	public void func_4011_a(EntityFish entityFish1, double d2, double d4, double d6, float f8, float f9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d2, (float)d4, (float)d6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		byte b10 = 1;
		byte b11 = 2;
		this.loadTexture("/particles.png");
		Tessellator tessellator12 = Tessellator.instance;
		float f13 = (float)(b10 * 8 + 0) / 128.0F;
		float f14 = (float)(b10 * 8 + 8) / 128.0F;
		float f15 = (float)(b11 * 8 + 0) / 128.0F;
		float f16 = (float)(b11 * 8 + 8) / 128.0F;
		float f17 = 1.0F;
		float f18 = 0.5F;
		float f19 = 0.5F;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tessellator12.startDrawingQuads();
		tessellator12.setNormal(0.0F, 1.0F, 0.0F);
		tessellator12.addVertexWithUV((double)(0.0F - f18), (double)(0.0F - f19), 0.0D, (double)f13, (double)f16);
		tessellator12.addVertexWithUV((double)(f17 - f18), (double)(0.0F - f19), 0.0D, (double)f14, (double)f16);
		tessellator12.addVertexWithUV((double)(f17 - f18), (double)(1.0F - f19), 0.0D, (double)f14, (double)f15);
		tessellator12.addVertexWithUV((double)(0.0F - f18), (double)(1.0F - f19), 0.0D, (double)f13, (double)f15);
		tessellator12.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		if(entityFish1.angler != null) {
			float f20 = (entityFish1.angler.prevRotationYaw + (entityFish1.angler.rotationYaw - entityFish1.angler.prevRotationYaw) * f9) * (float)Math.PI / 180.0F;
			double d21 = (double)MathHelper.sin(f20);
			double d23 = (double)MathHelper.cos(f20);
			float f25 = entityFish1.angler.getSwingProgress(f9);
			float f26 = MathHelper.sin(MathHelper.sqrt_float(f25) * (float)Math.PI);
			Vec3D vec3D27 = Vec3D.createVector(-0.5D, 0.03D, 0.8D);
			vec3D27.rotateAroundX(-(entityFish1.angler.prevRotationPitch + (entityFish1.angler.rotationPitch - entityFish1.angler.prevRotationPitch) * f9) * (float)Math.PI / 180.0F);
			vec3D27.rotateAroundY(-(entityFish1.angler.prevRotationYaw + (entityFish1.angler.rotationYaw - entityFish1.angler.prevRotationYaw) * f9) * (float)Math.PI / 180.0F);
			vec3D27.rotateAroundY(f26 * 0.5F);
			vec3D27.rotateAroundX(-f26 * 0.7F);
			double d28 = entityFish1.angler.prevPosX + (entityFish1.angler.posX - entityFish1.angler.prevPosX) * (double)f9 + vec3D27.xCoord;
			double d30 = entityFish1.angler.prevPosY + (entityFish1.angler.posY - entityFish1.angler.prevPosY) * (double)f9 + vec3D27.yCoord;
			double d32 = entityFish1.angler.prevPosZ + (entityFish1.angler.posZ - entityFish1.angler.prevPosZ) * (double)f9 + vec3D27.zCoord;
			if(this.renderManager.options.thirdPersonView) {
				f20 = (entityFish1.angler.prevRenderYawOffset + (entityFish1.angler.renderYawOffset - entityFish1.angler.prevRenderYawOffset) * f9) * (float)Math.PI / 180.0F;
				d21 = (double)MathHelper.sin(f20);
				d23 = (double)MathHelper.cos(f20);
				d28 = entityFish1.angler.prevPosX + (entityFish1.angler.posX - entityFish1.angler.prevPosX) * (double)f9 - d23 * 0.35D - d21 * 0.85D;
				d30 = entityFish1.angler.prevPosY + (entityFish1.angler.posY - entityFish1.angler.prevPosY) * (double)f9 - 0.45D;
				d32 = entityFish1.angler.prevPosZ + (entityFish1.angler.posZ - entityFish1.angler.prevPosZ) * (double)f9 - d21 * 0.35D + d23 * 0.85D;
			}

			double d34 = entityFish1.prevPosX + (entityFish1.posX - entityFish1.prevPosX) * (double)f9;
			double d36 = entityFish1.prevPosY + (entityFish1.posY - entityFish1.prevPosY) * (double)f9 + 0.25D;
			double d38 = entityFish1.prevPosZ + (entityFish1.posZ - entityFish1.prevPosZ) * (double)f9;
			double d40 = (double)((float)(d28 - d34));
			double d42 = (double)((float)(d30 - d36));
			double d44 = (double)((float)(d32 - d38));
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			tessellator12.startDrawing(3);
			tessellator12.setColorOpaque_I(0);
			byte b46 = 16;

			for(int i47 = 0; i47 <= b46; ++i47) {
				float f48 = (float)i47 / (float)b46;
				tessellator12.addVertex(d2 + d40 * (double)f48, d4 + d42 * (double)(f48 * f48 + f48) * 0.5D + 0.25D, d6 + d44 * (double)f48);
			}

			tessellator12.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}

	}

	public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
		this.func_4011_a((EntityFish)entity1, d2, d4, d6, f8, f9);
	}
}
