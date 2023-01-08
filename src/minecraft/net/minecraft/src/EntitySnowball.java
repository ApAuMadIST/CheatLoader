package net.minecraft.src;

import java.util.List;

public class EntitySnowball extends Entity {
	private int xTileSnowball = -1;
	private int yTileSnowball = -1;
	private int zTileSnowball = -1;
	private int inTileSnowball = 0;
	private boolean inGroundSnowball = false;
	public int shakeSnowball = 0;
	private EntityLiving thrower;
	private int ticksInGroundSnowball;
	private int ticksInAirSnowball = 0;

	public EntitySnowball(World world1) {
		super(world1);
		this.setSize(0.25F, 0.25F);
	}

	protected void entityInit() {
	}

	public boolean isInRangeToRenderDist(double d1) {
		double d3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		d3 *= 64.0D;
		return d1 < d3 * d3;
	}

	public EntitySnowball(World world1, EntityLiving entityLiving2) {
		super(world1);
		this.thrower = entityLiving2;
		this.setSize(0.25F, 0.25F);
		this.setLocationAndAngles(entityLiving2.posX, entityLiving2.posY + (double)entityLiving2.getEyeHeight(), entityLiving2.posZ, entityLiving2.rotationYaw, entityLiving2.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= (double)0.1F;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		float f3 = 0.4F;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f3);
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f3);
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * f3);
		this.setSnowballHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
	}

	public EntitySnowball(World world1, double d2, double d4, double d6) {
		super(world1);
		this.ticksInGroundSnowball = 0;
		this.setSize(0.25F, 0.25F);
		this.setPosition(d2, d4, d6);
		this.yOffset = 0.0F;
	}

	public void setSnowballHeading(double d1, double d3, double d5, float f7, float f8) {
		float f9 = MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
		d1 /= (double)f9;
		d3 /= (double)f9;
		d5 /= (double)f9;
		d1 += this.rand.nextGaussian() * (double)0.0075F * (double)f8;
		d3 += this.rand.nextGaussian() * (double)0.0075F * (double)f8;
		d5 += this.rand.nextGaussian() * (double)0.0075F * (double)f8;
		d1 *= (double)f7;
		d3 *= (double)f7;
		d5 *= (double)f7;
		this.motionX = d1;
		this.motionY = d3;
		this.motionZ = d5;
		float f10 = MathHelper.sqrt_double(d1 * d1 + d5 * d5);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(d1, d5) * 180.0D / (double)(float)Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(d3, (double)f10) * 180.0D / (double)(float)Math.PI);
		this.ticksInGroundSnowball = 0;
	}

	public void setVelocity(double d1, double d3, double d5) {
		this.motionX = d1;
		this.motionY = d3;
		this.motionZ = d5;
		if(this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f7 = MathHelper.sqrt_double(d1 * d1 + d5 * d5);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(d1, d5) * 180.0D / (double)(float)Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(d3, (double)f7) * 180.0D / (double)(float)Math.PI);
		}

	}

	public void onUpdate() {
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();
		if(this.shakeSnowball > 0) {
			--this.shakeSnowball;
		}

		if(this.inGroundSnowball) {
			int i1 = this.worldObj.getBlockId(this.xTileSnowball, this.yTileSnowball, this.zTileSnowball);
			if(i1 == this.inTileSnowball) {
				++this.ticksInGroundSnowball;
				if(this.ticksInGroundSnowball == 1200) {
					this.setEntityDead();
				}

				return;
			}

			this.inGroundSnowball = false;
			this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
			this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
			this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
			this.ticksInGroundSnowball = 0;
			this.ticksInAirSnowball = 0;
		} else {
			++this.ticksInAirSnowball;
		}

		Vec3D vec3D15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
		Vec3D vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition movingObjectPosition3 = this.worldObj.rayTraceBlocks(vec3D15, vec3D2);
		vec3D15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
		vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		if(movingObjectPosition3 != null) {
			vec3D2 = Vec3D.createVector(movingObjectPosition3.hitVec.xCoord, movingObjectPosition3.hitVec.yCoord, movingObjectPosition3.hitVec.zCoord);
		}

		if(!this.worldObj.multiplayerWorld) {
			Entity entity4 = null;
			List list5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d6 = 0.0D;

			for(int i8 = 0; i8 < list5.size(); ++i8) {
				Entity entity9 = (Entity)list5.get(i8);
				if(entity9.canBeCollidedWith() && (entity9 != this.thrower || this.ticksInAirSnowball >= 5)) {
					float f10 = 0.3F;
					AxisAlignedBB axisAlignedBB11 = entity9.boundingBox.expand((double)f10, (double)f10, (double)f10);
					MovingObjectPosition movingObjectPosition12 = axisAlignedBB11.func_1169_a(vec3D15, vec3D2);
					if(movingObjectPosition12 != null) {
						double d13 = vec3D15.distanceTo(movingObjectPosition12.hitVec);
						if(d13 < d6 || d6 == 0.0D) {
							entity4 = entity9;
							d6 = d13;
						}
					}
				}
			}

			if(entity4 != null) {
				movingObjectPosition3 = new MovingObjectPosition(entity4);
			}
		}

		if(movingObjectPosition3 != null) {
			if(movingObjectPosition3.entityHit != null && movingObjectPosition3.entityHit.attackEntityFrom(this.thrower, 0)) {
				;
			}

			for(int i16 = 0; i16 < 8; ++i16) {
				this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}

			this.setEntityDead();
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f17 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / (double)(float)Math.PI);

		for(this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f17) * 180.0D / (double)(float)Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float f18 = 0.99F;
		float f19 = 0.03F;
		if(this.isInWater()) {
			for(int i7 = 0; i7 < 4; ++i7) {
				float f20 = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f20, this.posY - this.motionY * (double)f20, this.posZ - this.motionZ * (double)f20, this.motionX, this.motionY, this.motionZ);
			}

			f18 = 0.8F;
		}

		this.motionX *= (double)f18;
		this.motionY *= (double)f18;
		this.motionZ *= (double)f18;
		this.motionY -= (double)f19;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		nBTTagCompound1.setShort("xTile", (short)this.xTileSnowball);
		nBTTagCompound1.setShort("yTile", (short)this.yTileSnowball);
		nBTTagCompound1.setShort("zTile", (short)this.zTileSnowball);
		nBTTagCompound1.setByte("inTile", (byte)this.inTileSnowball);
		nBTTagCompound1.setByte("shake", (byte)this.shakeSnowball);
		nBTTagCompound1.setByte("inGround", (byte)(this.inGroundSnowball ? 1 : 0));
	}

	public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		this.xTileSnowball = nBTTagCompound1.getShort("xTile");
		this.yTileSnowball = nBTTagCompound1.getShort("yTile");
		this.zTileSnowball = nBTTagCompound1.getShort("zTile");
		this.inTileSnowball = nBTTagCompound1.getByte("inTile") & 255;
		this.shakeSnowball = nBTTagCompound1.getByte("shake") & 255;
		this.inGroundSnowball = nBTTagCompound1.getByte("inGround") == 1;
	}

	public void onCollideWithPlayer(EntityPlayer entityPlayer1) {
		if(this.inGroundSnowball && this.thrower == entityPlayer1 && this.shakeSnowball <= 0 && entityPlayer1.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1))) {
			this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			entityPlayer1.onItemPickup(this, 1);
			this.setEntityDead();
		}

	}

	public float getShadowSize() {
		return 0.0F;
	}
}
