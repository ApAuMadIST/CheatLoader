package net.minecraft.src;

public class EntityLavaFX extends EntityFX {
	private float field_674_a;

	public EntityLavaFX(World world1, double d2, double d4, double d6) {
		super(world1, d2, d4, d6, 0.0D, 0.0D, 0.0D);
		this.motionX *= (double)0.8F;
		this.motionY *= (double)0.8F;
		this.motionZ *= (double)0.8F;
		this.motionY = (double)(this.rand.nextFloat() * 0.4F + 0.05F);
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
		this.particleScale *= this.rand.nextFloat() * 2.0F + 0.2F;
		this.field_674_a = this.particleScale;
		this.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
		this.noClip = false;
		this.particleTextureIndex = 49;
	}

	public float getEntityBrightness(float f1) {
		return 1.0F;
	}

	public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
		float f8 = ((float)this.particleAge + f2) / (float)this.particleMaxAge;
		this.particleScale = this.field_674_a * (1.0F - f8 * f8);
		super.renderParticle(tessellator1, f2, f3, f4, f5, f6, f7);
	}

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if(this.particleAge++ >= this.particleMaxAge) {
			this.setEntityDead();
		}

		float f1 = (float)this.particleAge / (float)this.particleMaxAge;
		if(this.rand.nextFloat() > f1) {
			this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
		}

		this.motionY -= 0.03D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= (double)0.999F;
		this.motionY *= (double)0.999F;
		this.motionZ *= (double)0.999F;
		if(this.onGround) {
			this.motionX *= (double)0.7F;
			this.motionZ *= (double)0.7F;
		}

	}
}
