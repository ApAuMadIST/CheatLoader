package net.minecraft.src;

import java.util.List;

public class EntityFish extends Entity {
	private int xTile;
	private int yTile;
	private int zTile;
	private int inTile;
	private boolean inGround;
	public int shake;
	public EntityPlayer angler;
	private int ticksInGround;
	private int ticksInAir;
	private int ticksCatchable;
	public Entity bobber;
	private int field_6388_l;
	private double field_6387_m;
	private double field_6386_n;
	private double field_6385_o;
	private double field_6384_p;
	private double field_6383_q;
	private double velocityX;
	private double velocityY;
	private double velocityZ;

	public EntityFish(World world1) {
		super(world1);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.inTile = 0;
		this.inGround = false;
		this.shake = 0;
		this.ticksInAir = 0;
		this.ticksCatchable = 0;
		this.bobber = null;
		this.setSize(0.25F, 0.25F);
		this.ignoreFrustumCheck = true;
	}

	public EntityFish(World world1, double d2, double d4, double d6) {
		this(world1);
		this.setPosition(d2, d4, d6);
		this.ignoreFrustumCheck = true;
	}

	public EntityFish(World world1, EntityPlayer entityPlayer2) {
		super(world1);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.inTile = 0;
		this.inGround = false;
		this.shake = 0;
		this.ticksInAir = 0;
		this.ticksCatchable = 0;
		this.bobber = null;
		this.ignoreFrustumCheck = true;
		this.angler = entityPlayer2;
		this.angler.fishEntity = this;
		this.setSize(0.25F, 0.25F);
		this.setLocationAndAngles(entityPlayer2.posX, entityPlayer2.posY + 1.62D - (double)entityPlayer2.yOffset, entityPlayer2.posZ, entityPlayer2.rotationYaw, entityPlayer2.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= (double)0.1F;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		float f3 = 0.4F;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f3);
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f3);
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * f3);
		this.func_4042_a(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
	}

	protected void entityInit() {
	}

	public boolean isInRangeToRenderDist(double d1) {
		double d3 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		d3 *= 64.0D;
		return d1 < d3 * d3;
	}

	public void func_4042_a(double d1, double d3, double d5, float f7, float f8) {
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
		this.ticksInGround = 0;
	}

	public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
		this.field_6387_m = d1;
		this.field_6386_n = d3;
		this.field_6385_o = d5;
		this.field_6384_p = (double)f7;
		this.field_6383_q = (double)f8;
		this.field_6388_l = i9;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	public void setVelocity(double d1, double d3, double d5) {
		this.velocityX = this.motionX = d1;
		this.velocityY = this.motionY = d3;
		this.velocityZ = this.motionZ = d5;
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.field_6388_l > 0) {
			double d21 = this.posX + (this.field_6387_m - this.posX) / (double)this.field_6388_l;
			double d22 = this.posY + (this.field_6386_n - this.posY) / (double)this.field_6388_l;
			double d23 = this.posZ + (this.field_6385_o - this.posZ) / (double)this.field_6388_l;

			double d7;
			for(d7 = this.field_6384_p - (double)this.rotationYaw; d7 < -180.0D; d7 += 360.0D) {
			}

			while(d7 >= 180.0D) {
				d7 -= 360.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + d7 / (double)this.field_6388_l);
			this.rotationPitch = (float)((double)this.rotationPitch + (this.field_6383_q - (double)this.rotationPitch) / (double)this.field_6388_l);
			--this.field_6388_l;
			this.setPosition(d21, d22, d23);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		} else {
			if(!this.worldObj.multiplayerWorld) {
				ItemStack itemStack1 = this.angler.getCurrentEquippedItem();
				if(this.angler.isDead || !this.angler.isEntityAlive() || itemStack1 == null || itemStack1.getItem() != Item.fishingRod || this.getDistanceSqToEntity(this.angler) > 1024.0D) {
					this.setEntityDead();
					this.angler.fishEntity = null;
					return;
				}

				if(this.bobber != null) {
					if(!this.bobber.isDead) {
						this.posX = this.bobber.posX;
						this.posY = this.bobber.boundingBox.minY + (double)this.bobber.height * 0.8D;
						this.posZ = this.bobber.posZ;
						return;
					}

					this.bobber = null;
				}
			}

			if(this.shake > 0) {
				--this.shake;
			}

			if(this.inGround) {
				int i19 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
				if(i19 == this.inTile) {
					++this.ticksInGround;
					if(this.ticksInGround == 1200) {
						this.setEntityDead();
					}

					return;
				}

				this.inGround = false;
				this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			} else {
				++this.ticksInAir;
			}

			Vec3D vec3D20 = Vec3D.createVector(this.posX, this.posY, this.posZ);
			Vec3D vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingObjectPosition3 = this.worldObj.rayTraceBlocks(vec3D20, vec3D2);
			vec3D20 = Vec3D.createVector(this.posX, this.posY, this.posZ);
			vec3D2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			if(movingObjectPosition3 != null) {
				vec3D2 = Vec3D.createVector(movingObjectPosition3.hitVec.xCoord, movingObjectPosition3.hitVec.yCoord, movingObjectPosition3.hitVec.zCoord);
			}

			Entity entity4 = null;
			List list5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d6 = 0.0D;

			double d13;
			for(int i8 = 0; i8 < list5.size(); ++i8) {
				Entity entity9 = (Entity)list5.get(i8);
				if(entity9.canBeCollidedWith() && (entity9 != this.angler || this.ticksInAir >= 5)) {
					float f10 = 0.3F;
					AxisAlignedBB axisAlignedBB11 = entity9.boundingBox.expand((double)f10, (double)f10, (double)f10);
					MovingObjectPosition movingObjectPosition12 = axisAlignedBB11.func_1169_a(vec3D20, vec3D2);
					if(movingObjectPosition12 != null) {
						d13 = vec3D20.distanceTo(movingObjectPosition12.hitVec);
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

			if(movingObjectPosition3 != null) {
				if(movingObjectPosition3.entityHit != null) {
					if(movingObjectPosition3.entityHit.attackEntityFrom(this.angler, 0)) {
						this.bobber = movingObjectPosition3.entityHit;
					}
				} else {
					this.inGround = true;
				}
			}

			if(!this.inGround) {
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				float f24 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / (double)(float)Math.PI);

				for(this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f24) * 180.0D / (double)(float)Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
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
				float f25 = 0.92F;
				if(this.onGround || this.isCollidedHorizontally) {
					f25 = 0.5F;
				}

				byte b26 = 5;
				double d27 = 0.0D;

				for(int i28 = 0; i28 < b26; ++i28) {
					double d14 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i28 + 0) / (double)b26 - 0.125D + 0.125D;
					double d16 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i28 + 1) / (double)b26 - 0.125D + 0.125D;
					AxisAlignedBB axisAlignedBB18 = AxisAlignedBB.getBoundingBoxFromPool(this.boundingBox.minX, d14, this.boundingBox.minZ, this.boundingBox.maxX, d16, this.boundingBox.maxZ);
					if(this.worldObj.isAABBInMaterial(axisAlignedBB18, Material.water)) {
						d27 += 1.0D / (double)b26;
					}
				}

				if(d27 > 0.0D) {
					if(this.ticksCatchable > 0) {
						--this.ticksCatchable;
					} else {
						short s29 = 500;
						if(this.worldObj.canBlockBeRainedOn(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ))) {
							s29 = 300;
						}

						if(this.rand.nextInt(s29) == 0) {
							this.ticksCatchable = this.rand.nextInt(30) + 10;
							this.motionY -= (double)0.2F;
							this.worldObj.playSoundAtEntity(this, "random.splash", 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
							float f30 = (float)MathHelper.floor_double(this.boundingBox.minY);

							int i15;
							float f17;
							float f31;
							for(i15 = 0; (float)i15 < 1.0F + this.width * 20.0F; ++i15) {
								f31 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
								f17 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
								this.worldObj.spawnParticle("bubble", this.posX + (double)f31, (double)(f30 + 1.0F), this.posZ + (double)f17, this.motionX, this.motionY - (double)(this.rand.nextFloat() * 0.2F), this.motionZ);
							}

							for(i15 = 0; (float)i15 < 1.0F + this.width * 20.0F; ++i15) {
								f31 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
								f17 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
								this.worldObj.spawnParticle("splash", this.posX + (double)f31, (double)(f30 + 1.0F), this.posZ + (double)f17, this.motionX, this.motionY, this.motionZ);
							}
						}
					}
				}

				if(this.ticksCatchable > 0) {
					this.motionY -= (double)(this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2D;
				}

				d13 = d27 * 2.0D - 1.0D;
				this.motionY += (double)0.04F * d13;
				if(d27 > 0.0D) {
					f25 = (float)((double)f25 * 0.9D);
					this.motionY *= 0.8D;
				}

				this.motionX *= (double)f25;
				this.motionY *= (double)f25;
				this.motionZ *= (double)f25;
				this.setPosition(this.posX, this.posY, this.posZ);
			}
		}
	}

	public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		nBTTagCompound1.setShort("xTile", (short)this.xTile);
		nBTTagCompound1.setShort("yTile", (short)this.yTile);
		nBTTagCompound1.setShort("zTile", (short)this.zTile);
		nBTTagCompound1.setByte("inTile", (byte)this.inTile);
		nBTTagCompound1.setByte("shake", (byte)this.shake);
		nBTTagCompound1.setByte("inGround", (byte)(this.inGround ? 1 : 0));
	}

	public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		this.xTile = nBTTagCompound1.getShort("xTile");
		this.yTile = nBTTagCompound1.getShort("yTile");
		this.zTile = nBTTagCompound1.getShort("zTile");
		this.inTile = nBTTagCompound1.getByte("inTile") & 255;
		this.shake = nBTTagCompound1.getByte("shake") & 255;
		this.inGround = nBTTagCompound1.getByte("inGround") == 1;
	}

	public float getShadowSize() {
		return 0.0F;
	}

	public int catchFish() {
		byte b1 = 0;
		if(this.bobber != null) {
			double d2 = this.angler.posX - this.posX;
			double d4 = this.angler.posY - this.posY;
			double d6 = this.angler.posZ - this.posZ;
			double d8 = (double)MathHelper.sqrt_double(d2 * d2 + d4 * d4 + d6 * d6);
			double d10 = 0.1D;
			this.bobber.motionX += d2 * d10;
			this.bobber.motionY += d4 * d10 + (double)MathHelper.sqrt_double(d8) * 0.08D;
			this.bobber.motionZ += d6 * d10;
			b1 = 3;
		} else if(this.ticksCatchable > 0) {
			EntityItem entityItem13 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Item.fishRaw));
			double d3 = this.angler.posX - this.posX;
			double d5 = this.angler.posY - this.posY;
			double d7 = this.angler.posZ - this.posZ;
			double d9 = (double)MathHelper.sqrt_double(d3 * d3 + d5 * d5 + d7 * d7);
			double d11 = 0.1D;
			entityItem13.motionX = d3 * d11;
			entityItem13.motionY = d5 * d11 + (double)MathHelper.sqrt_double(d9) * 0.08D;
			entityItem13.motionZ = d7 * d11;
			this.worldObj.entityJoinedWorld(entityItem13);
			this.angler.addStat(StatList.fishCaughtStat, 1);
			b1 = 1;
		}

		if(this.inGround) {
			b1 = 2;
		}

		this.setEntityDead();
		this.angler.fishEntity = null;
		return b1;
	}
}
