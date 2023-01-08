package net.minecraft.src;

public class EntityFlying extends EntityLiving {
	public EntityFlying(World world1) {
		super(world1);
	}

	protected void fall(float f1) {
	}

	public void moveEntityWithHeading(float f1, float f2) {
		if(this.isInWater()) {
			this.moveFlying(f1, f2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)0.8F;
			this.motionY *= (double)0.8F;
			this.motionZ *= (double)0.8F;
		} else if(this.handleLavaMovement()) {
			this.moveFlying(f1, f2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		} else {
			float f3 = 0.91F;
			if(this.onGround) {
				f3 = 0.54600006F;
				int i4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(i4 > 0) {
					f3 = Block.blocksList[i4].slipperiness * 0.91F;
				}
			}

			float f8 = 0.16277136F / (f3 * f3 * f3);
			this.moveFlying(f1, f2, this.onGround ? 0.1F * f8 : 0.02F);
			f3 = 0.91F;
			if(this.onGround) {
				f3 = 0.54600006F;
				int i5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(i5 > 0) {
					f3 = Block.blocksList[i5].slipperiness * 0.91F;
				}
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)f3;
			this.motionY *= (double)f3;
			this.motionZ *= (double)f3;
		}

		this.field_705_Q = this.field_704_R;
		double d10 = this.posX - this.prevPosX;
		double d9 = this.posZ - this.prevPosZ;
		float f7 = MathHelper.sqrt_double(d10 * d10 + d9 * d9) * 4.0F;
		if(f7 > 1.0F) {
			f7 = 1.0F;
		}

		this.field_704_R += (f7 - this.field_704_R) * 0.4F;
		this.field_703_S += this.field_704_R;
	}

	public boolean isOnLadder() {
		return false;
	}
}
