package net.minecraft.src;

public class EntityDiggingFX extends EntityFX {
	private Block field_4082_a;
	private int field_32001_o = 0;

	public EntityDiggingFX(World world1, double d2, double d4, double d6, double d8, double d10, double d12, Block block14, int i15, int i16) {
		super(world1, d2, d4, d6, d8, d10, d12);
		this.field_4082_a = block14;
		this.particleTextureIndex = block14.getBlockTextureFromSideAndMetadata(0, i16);
		this.particleGravity = block14.blockParticleGravity;
		this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
		this.particleScale /= 2.0F;
		this.field_32001_o = i15;
	}

	public EntityDiggingFX func_4041_a(int i1, int i2, int i3) {
		if(this.field_4082_a == Block.grass) {
			return this;
		} else {
			int i4 = this.field_4082_a.colorMultiplier(this.worldObj, i1, i2, i3);
			this.particleRed *= (float)(i4 >> 16 & 255) / 255.0F;
			this.particleGreen *= (float)(i4 >> 8 & 255) / 255.0F;
			this.particleBlue *= (float)(i4 & 255) / 255.0F;
			return this;
		}
	}

	public int getFXLayer() {
		return 1;
	}

	public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
		float f8 = ((float)(this.particleTextureIndex % 16) + this.particleTextureJitterX / 4.0F) / 16.0F;
		float f9 = f8 + 0.015609375F;
		float f10 = ((float)(this.particleTextureIndex / 16) + this.particleTextureJitterY / 4.0F) / 16.0F;
		float f11 = f10 + 0.015609375F;
		float f12 = 0.1F * this.particleScale;
		float f13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f2 - interpPosX);
		float f14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f2 - interpPosY);
		float f15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f2 - interpPosZ);
		float f16 = this.getEntityBrightness(f2);
		tessellator1.setColorOpaque_F(f16 * this.particleRed, f16 * this.particleGreen, f16 * this.particleBlue);
		tessellator1.addVertexWithUV((double)(f13 - f3 * f12 - f6 * f12), (double)(f14 - f4 * f12), (double)(f15 - f5 * f12 - f7 * f12), (double)f8, (double)f11);
		tessellator1.addVertexWithUV((double)(f13 - f3 * f12 + f6 * f12), (double)(f14 + f4 * f12), (double)(f15 - f5 * f12 + f7 * f12), (double)f8, (double)f10);
		tessellator1.addVertexWithUV((double)(f13 + f3 * f12 + f6 * f12), (double)(f14 + f4 * f12), (double)(f15 + f5 * f12 + f7 * f12), (double)f9, (double)f10);
		tessellator1.addVertexWithUV((double)(f13 + f3 * f12 - f6 * f12), (double)(f14 - f4 * f12), (double)(f15 + f5 * f12 - f7 * f12), (double)f9, (double)f11);
	}
}
