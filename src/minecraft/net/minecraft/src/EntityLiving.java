package net.minecraft.src;

import java.util.List;

public abstract class EntityLiving extends Entity {
	public int heartsHalvesLife = 20;
	public float field_9365_p;
	public float field_9363_r;
	public float renderYawOffset = 0.0F;
	public float prevRenderYawOffset = 0.0F;
	protected float field_9362_u;
	protected float field_9361_v;
	protected float field_9360_w;
	protected float field_9359_x;
	protected boolean field_9358_y = true;
	protected String texture = "/mob/char.png";
	protected boolean field_9355_A = true;
	protected float field_9353_B = 0.0F;
	protected String field_9351_C = null;
	protected float field_9349_D = 1.0F;
	protected int scoreValue = 0;
	protected float field_9345_F = 0.0F;
	public boolean isMultiplayerEntity = false;
	public float prevSwingProgress;
	public float swingProgress;
	public int health = 10;
	public int prevHealth;
	private int livingSoundTime;
	public int hurtTime;
	public int maxHurtTime;
	public float attackedAtYaw = 0.0F;
	public int deathTime = 0;
	public int attackTime = 0;
	public float cameraPitch;
	public float field_9328_R;
	protected boolean unused_flag = false;
	public int field_9326_T = -1;
	public float field_9325_U = (float)(Math.random() * (double)0.9F + (double)0.1F);
	public float field_705_Q;
	public float field_704_R;
	public float field_703_S;
	protected int newPosRotationIncrements;
	protected double newPosX;
	protected double newPosY;
	protected double newPosZ;
	protected double newRotationYaw;
	protected double newRotationPitch;
	float field_9348_ae = 0.0F;
	protected int field_9346_af = 0;
	protected int entityAge = 0;
	protected float moveStrafing;
	protected float moveForward;
	protected float randomYawVelocity;
	protected boolean isJumping = false;
	protected float defaultPitch = 0.0F;
	protected float moveSpeed = 0.7F;
	private Entity currentTarget;
	protected int numTicksToChaseTarget = 0;

	public EntityLiving(World world1) {
		super(world1);
		this.preventEntitySpawning = true;
		this.field_9363_r = (float)(Math.random() + 1.0D) * 0.01F;
		this.setPosition(this.posX, this.posY, this.posZ);
		this.field_9365_p = (float)Math.random() * 12398.0F;
		this.rotationYaw = (float)(Math.random() * (double)(float)Math.PI * 2.0D);
		this.stepHeight = 0.5F;
	}

	protected void entityInit() {
	}

	public boolean canEntityBeSeen(Entity entity1) {
		return this.worldObj.rayTraceBlocks(Vec3D.createVector(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), Vec3D.createVector(entity1.posX, entity1.posY + (double)entity1.getEyeHeight(), entity1.posZ)) == null;
	}

	public String getEntityTexture() {
		return this.texture;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public boolean canBePushed() {
		return !this.isDead;
	}

	public float getEyeHeight() {
		return this.height * 0.85F;
	}

	public int getTalkInterval() {
		return 80;
	}

	public void playLivingSound() {
		String string1 = this.getLivingSound();
		if(string1 != null) {
			this.worldObj.playSoundAtEntity(this, string1, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
		}

	}

	public void onEntityUpdate() {
		this.prevSwingProgress = this.swingProgress;
		super.onEntityUpdate();
		if(this.rand.nextInt(1000) < this.livingSoundTime++) {
			this.livingSoundTime = -this.getTalkInterval();
			this.playLivingSound();
		}

		if(this.isEntityAlive() && this.isEntityInsideOpaqueBlock()) {
			this.attackEntityFrom((Entity)null, 1);
		}

		if(this.isImmuneToFire || this.worldObj.multiplayerWorld) {
			this.fire = 0;
		}

		int i1;
		if(this.isEntityAlive() && this.isInsideOfMaterial(Material.water) && !this.canBreatheUnderwater()) {
			--this.air;
			if(this.air == -20) {
				this.air = 0;

				for(i1 = 0; i1 < 8; ++i1) {
					float f2 = this.rand.nextFloat() - this.rand.nextFloat();
					float f3 = this.rand.nextFloat() - this.rand.nextFloat();
					float f4 = this.rand.nextFloat() - this.rand.nextFloat();
					this.worldObj.spawnParticle("bubble", this.posX + (double)f2, this.posY + (double)f3, this.posZ + (double)f4, this.motionX, this.motionY, this.motionZ);
				}

				this.attackEntityFrom((Entity)null, 2);
			}

			this.fire = 0;
		} else {
			this.air = this.maxAir;
		}

		this.cameraPitch = this.field_9328_R;
		if(this.attackTime > 0) {
			--this.attackTime;
		}

		if(this.hurtTime > 0) {
			--this.hurtTime;
		}

		if(this.heartsLife > 0) {
			--this.heartsLife;
		}

		if(this.health <= 0) {
			++this.deathTime;
			if(this.deathTime > 20) {
				this.onEntityDeath();
				this.setEntityDead();

				for(i1 = 0; i1 < 20; ++i1) {
					double d8 = this.rand.nextGaussian() * 0.02D;
					double d9 = this.rand.nextGaussian() * 0.02D;
					double d6 = this.rand.nextGaussian() * 0.02D;
					this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d8, d9, d6);
				}
			}
		}

		this.field_9359_x = this.field_9360_w;
		this.prevRenderYawOffset = this.renderYawOffset;
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
	}

	public void spawnExplosionParticle() {
		for(int i1 = 0; i1 < 20; ++i1) {
			double d2 = this.rand.nextGaussian() * 0.02D;
			double d4 = this.rand.nextGaussian() * 0.02D;
			double d6 = this.rand.nextGaussian() * 0.02D;
			double d8 = 10.0D;
			this.worldObj.spawnParticle("explode", this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d2 * d8, this.posY + (double)(this.rand.nextFloat() * this.height) - d4 * d8, this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width - d6 * d8, d2, d4, d6);
		}

	}

	public void updateRidden() {
		super.updateRidden();
		this.field_9362_u = this.field_9361_v;
		this.field_9361_v = 0.0F;
	}

	public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
		this.yOffset = 0.0F;
		this.newPosX = d1;
		this.newPosY = d3;
		this.newPosZ = d5;
		this.newRotationYaw = (double)f7;
		this.newRotationPitch = (double)f8;
		this.newPosRotationIncrements = i9;
	}

	public void onUpdate() {
		super.onUpdate();
		this.onLivingUpdate();
		double d1 = this.posX - this.prevPosX;
		double d3 = this.posZ - this.prevPosZ;
		float f5 = MathHelper.sqrt_double(d1 * d1 + d3 * d3);
		float f6 = this.renderYawOffset;
		float f7 = 0.0F;
		this.field_9362_u = this.field_9361_v;
		float f8 = 0.0F;
		if(f5 > 0.05F) {
			f8 = 1.0F;
			f7 = f5 * 3.0F;
			f6 = (float)Math.atan2(d3, d1) * 180.0F / (float)Math.PI - 90.0F;
		}

		if(this.swingProgress > 0.0F) {
			f6 = this.rotationYaw;
		}

		if(!this.onGround) {
			f8 = 0.0F;
		}

		this.field_9361_v += (f8 - this.field_9361_v) * 0.3F;

		float f9;
		for(f9 = f6 - this.renderYawOffset; f9 < -180.0F; f9 += 360.0F) {
		}

		while(f9 >= 180.0F) {
			f9 -= 360.0F;
		}

		this.renderYawOffset += f9 * 0.3F;

		float f10;
		for(f10 = this.rotationYaw - this.renderYawOffset; f10 < -180.0F; f10 += 360.0F) {
		}

		while(f10 >= 180.0F) {
			f10 -= 360.0F;
		}

		boolean z11 = f10 < -90.0F || f10 >= 90.0F;
		if(f10 < -75.0F) {
			f10 = -75.0F;
		}

		if(f10 >= 75.0F) {
			f10 = 75.0F;
		}

		this.renderYawOffset = this.rotationYaw - f10;
		if(f10 * f10 > 2500.0F) {
			this.renderYawOffset += f10 * 0.2F;
		}

		if(z11) {
			f7 *= -1.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		while(this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
			this.prevRenderYawOffset -= 360.0F;
		}

		while(this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
			this.prevRenderYawOffset += 360.0F;
		}

		while(this.rotationPitch - this.prevRotationPitch < -180.0F) {
			this.prevRotationPitch -= 360.0F;
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		this.field_9360_w += f7;
	}

	protected void setSize(float f1, float f2) {
		super.setSize(f1, f2);
	}

	public void heal(int i1) {
		if(this.health > 0) {
			this.health += i1;
			if(this.health > 20) {
				this.health = 20;
			}

			this.heartsLife = this.heartsHalvesLife / 2;
		}
	}

	public boolean attackEntityFrom(Entity entity1, int i2) {
		if(this.worldObj.multiplayerWorld) {
			return false;
		} else {
			this.entityAge = 0;
			if(this.health <= 0) {
				return false;
			} else {
				this.field_704_R = 1.5F;
				boolean z3 = true;
				if((float)this.heartsLife > (float)this.heartsHalvesLife / 2.0F) {
					if(i2 <= this.field_9346_af) {
						return false;
					}

					this.damageEntity(i2 - this.field_9346_af);
					this.field_9346_af = i2;
					z3 = false;
				} else {
					this.field_9346_af = i2;
					this.prevHealth = this.health;
					this.heartsLife = this.heartsHalvesLife;
					this.damageEntity(i2);
					this.hurtTime = this.maxHurtTime = 10;
				}

				this.attackedAtYaw = 0.0F;
				if(z3) {
					this.worldObj.func_9425_a(this, (byte)2);
					this.setBeenAttacked();
					if(entity1 != null) {
						double d4 = entity1.posX - this.posX;

						double d6;
						for(d6 = entity1.posZ - this.posZ; d4 * d4 + d6 * d6 < 1.0E-4D; d6 = (Math.random() - Math.random()) * 0.01D) {
							d4 = (Math.random() - Math.random()) * 0.01D;
						}

						this.attackedAtYaw = (float)(Math.atan2(d6, d4) * 180.0D / (double)(float)Math.PI) - this.rotationYaw;
						this.knockBack(entity1, i2, d4, d6);
					} else {
						this.attackedAtYaw = (float)((int)(Math.random() * 2.0D) * 180);
					}
				}

				if(this.health <= 0) {
					if(z3) {
						this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
					}

					this.onDeath(entity1);
				} else if(z3) {
					this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				}

				return true;
			}
		}
	}

	public void performHurtAnimation() {
		this.hurtTime = this.maxHurtTime = 10;
		this.attackedAtYaw = 0.0F;
	}

	protected void damageEntity(int i1) {
		this.health -= i1;
	}

	protected float getSoundVolume() {
		return 1.0F;
	}

	protected String getLivingSound() {
		return null;
	}

	protected String getHurtSound() {
		return "random.hurt";
	}

	protected String getDeathSound() {
		return "random.hurt";
	}

	public void knockBack(Entity entity1, int i2, double d3, double d5) {
		float f7 = MathHelper.sqrt_double(d3 * d3 + d5 * d5);
		float f8 = 0.4F;
		this.motionX /= 2.0D;
		this.motionY /= 2.0D;
		this.motionZ /= 2.0D;
		this.motionX -= d3 / (double)f7 * (double)f8;
		this.motionY += (double)0.4F;
		this.motionZ -= d5 / (double)f7 * (double)f8;
		if(this.motionY > (double)0.4F) {
			this.motionY = (double)0.4F;
		}

	}

	public void onDeath(Entity entity1) {
		if(this.scoreValue >= 0 && entity1 != null) {
			entity1.addToPlayerScore(this, this.scoreValue);
		}

		if(entity1 != null) {
			entity1.onKillEntity(this);
		}

		this.unused_flag = true;
		if(!this.worldObj.multiplayerWorld) {
			this.dropFewItems();
		}

		this.worldObj.func_9425_a(this, (byte)3);
	}

	protected void dropFewItems() {
		int i1 = this.getDropItemId();
		if(i1 > 0) {
			int i2 = this.rand.nextInt(3);

			for(int i3 = 0; i3 < i2; ++i3) {
				this.dropItem(i1, 1);
			}
		}

	}

	protected int getDropItemId() {
		return 0;
	}

	protected void fall(float f1) {
		super.fall(f1);
		int i2 = (int)Math.ceil((double)(f1 - 3.0F));
		if(i2 > 0) {
			this.attackEntityFrom((Entity)null, i2);
			int i3 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - (double)0.2F - (double)this.yOffset), MathHelper.floor_double(this.posZ));
			if(i3 > 0) {
				StepSound stepSound4 = Block.blocksList[i3].stepSound;
				this.worldObj.playSoundAtEntity(this, stepSound4.func_1145_d(), stepSound4.getVolume() * 0.5F, stepSound4.getPitch() * 0.75F);
			}
		}

	}

	public void moveEntityWithHeading(float f1, float f2) {
		double d3;
		if(this.isInWater()) {
			d3 = this.posY;
			this.moveFlying(f1, f2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)0.8F;
			this.motionY *= (double)0.8F;
			this.motionZ *= (double)0.8F;
			this.motionY -= 0.02D;
			if(this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6F - this.posY + d3, this.motionZ)) {
				this.motionY = (double)0.3F;
			}
		} else if(this.handleLavaMovement()) {
			d3 = this.posY;
			this.moveFlying(f1, f2, 0.02F);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
			this.motionY -= 0.02D;
			if(this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + (double)0.6F - this.posY + d3, this.motionZ)) {
				this.motionY = (double)0.3F;
			}
		} else {
			float f8 = 0.91F;
			if(this.onGround) {
				f8 = 0.54600006F;
				int i4 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(i4 > 0) {
					f8 = Block.blocksList[i4].slipperiness * 0.91F;
				}
			}

			float f9 = 0.16277136F / (f8 * f8 * f8);
			this.moveFlying(f1, f2, this.onGround ? 0.1F * f9 : 0.02F);
			f8 = 0.91F;
			if(this.onGround) {
				f8 = 0.54600006F;
				int i5 = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
				if(i5 > 0) {
					f8 = Block.blocksList[i5].slipperiness * 0.91F;
				}
			}

			if(this.isOnLadder()) {
				float f10 = 0.15F;
				if(this.motionX < (double)(-f10)) {
					this.motionX = (double)(-f10);
				}

				if(this.motionX > (double)f10) {
					this.motionX = (double)f10;
				}

				if(this.motionZ < (double)(-f10)) {
					this.motionZ = (double)(-f10);
				}

				if(this.motionZ > (double)f10) {
					this.motionZ = (double)f10;
				}

				this.fallDistance = 0.0F;
				if(this.motionY < -0.15D) {
					this.motionY = -0.15D;
				}

				if(this.isSneaking() && this.motionY < 0.0D) {
					this.motionY = 0.0D;
				}
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			if(this.isCollidedHorizontally && this.isOnLadder()) {
				this.motionY = 0.2D;
			}

			this.motionY -= 0.08D;
			this.motionY *= (double)0.98F;
			this.motionX *= (double)f8;
			this.motionZ *= (double)f8;
		}

		this.field_705_Q = this.field_704_R;
		d3 = this.posX - this.prevPosX;
		double d11 = this.posZ - this.prevPosZ;
		float f7 = MathHelper.sqrt_double(d3 * d3 + d11 * d11) * 4.0F;
		if(f7 > 1.0F) {
			f7 = 1.0F;
		}

		this.field_704_R += (f7 - this.field_704_R) * 0.4F;
		this.field_703_S += this.field_704_R;
	}

	public boolean isOnLadder() {
		int i1 = MathHelper.floor_double(this.posX);
		int i2 = MathHelper.floor_double(this.boundingBox.minY);
		int i3 = MathHelper.floor_double(this.posZ);
		return this.worldObj.getBlockId(i1, i2, i3) == Block.ladder.blockID;
	}

	public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		nBTTagCompound1.setShort("Health", (short)this.health);
		nBTTagCompound1.setShort("HurtTime", (short)this.hurtTime);
		nBTTagCompound1.setShort("DeathTime", (short)this.deathTime);
		nBTTagCompound1.setShort("AttackTime", (short)this.attackTime);
	}

	public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		this.health = nBTTagCompound1.getShort("Health");
		if(!nBTTagCompound1.hasKey("Health")) {
			this.health = 10;
		}

		this.hurtTime = nBTTagCompound1.getShort("HurtTime");
		this.deathTime = nBTTagCompound1.getShort("DeathTime");
		this.attackTime = nBTTagCompound1.getShort("AttackTime");
	}

	public boolean isEntityAlive() {
		return !this.isDead && this.health > 0;
	}

	public boolean canBreatheUnderwater() {
		return false;
	}

	public void onLivingUpdate() {
		if(this.newPosRotationIncrements > 0) {
			double d1 = this.posX + (this.newPosX - this.posX) / (double)this.newPosRotationIncrements;
			double d3 = this.posY + (this.newPosY - this.posY) / (double)this.newPosRotationIncrements;
			double d5 = this.posZ + (this.newPosZ - this.posZ) / (double)this.newPosRotationIncrements;

			double d7;
			for(d7 = this.newRotationYaw - (double)this.rotationYaw; d7 < -180.0D; d7 += 360.0D) {
			}

			while(d7 >= 180.0D) {
				d7 -= 360.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + d7 / (double)this.newPosRotationIncrements);
			this.rotationPitch = (float)((double)this.rotationPitch + (this.newRotationPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
			--this.newPosRotationIncrements;
			this.setPosition(d1, d3, d5);
			this.setRotation(this.rotationYaw, this.rotationPitch);
			List list9 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.func_28195_e(8.0D / 256D, 0.0D, 8.0D / 256D));
			if(list9.size() > 0) {
				double d10 = 0.0D;

				for(int i12 = 0; i12 < list9.size(); ++i12) {
					AxisAlignedBB axisAlignedBB13 = (AxisAlignedBB)list9.get(i12);
					if(axisAlignedBB13.maxY > d10) {
						d10 = axisAlignedBB13.maxY;
					}
				}

				d3 += d10 - this.boundingBox.minY;
				this.setPosition(d1, d3, d5);
			}
		}

		if(this.isMovementBlocked()) {
			this.isJumping = false;
			this.moveStrafing = 0.0F;
			this.moveForward = 0.0F;
			this.randomYawVelocity = 0.0F;
		} else if(!this.isMultiplayerEntity) {
			this.updatePlayerActionState();
		}

		boolean z14 = this.isInWater();
		boolean z2 = this.handleLavaMovement();
		if(this.isJumping) {
			if(z14) {
				this.motionY += (double)0.04F;
			} else if(z2) {
				this.motionY += (double)0.04F;
			} else if(this.onGround) {
				this.jump();
			}
		}

		this.moveStrafing *= 0.98F;
		this.moveForward *= 0.98F;
		this.randomYawVelocity *= 0.9F;
		this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
		List list15 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand((double)0.2F, 0.0D, (double)0.2F));
		if(list15 != null && list15.size() > 0) {
			for(int i4 = 0; i4 < list15.size(); ++i4) {
				Entity entity16 = (Entity)list15.get(i4);
				if(entity16.canBePushed()) {
					entity16.applyEntityCollision(this);
				}
			}
		}

	}

	protected boolean isMovementBlocked() {
		return this.health <= 0;
	}

	protected void jump() {
		this.motionY = (double)0.42F;
	}

	protected boolean canDespawn() {
		return true;
	}

	protected void func_27021_X() {
		EntityPlayer entityPlayer1 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
		if(this.canDespawn() && entityPlayer1 != null) {
			double d2 = entityPlayer1.posX - this.posX;
			double d4 = entityPlayer1.posY - this.posY;
			double d6 = entityPlayer1.posZ - this.posZ;
			double d8 = d2 * d2 + d4 * d4 + d6 * d6;
			if(d8 > 16384.0D) {
				this.setEntityDead();
			}

			if(this.entityAge > 600 && this.rand.nextInt(800) == 0) {
				if(d8 < 1024.0D) {
					this.entityAge = 0;
				} else {
					this.setEntityDead();
				}
			}
		}

	}

	protected void updatePlayerActionState() {
		++this.entityAge;
		EntityPlayer entityPlayer1 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
		this.func_27021_X();
		this.moveStrafing = 0.0F;
		this.moveForward = 0.0F;
		float f2 = 8.0F;
		if(this.rand.nextFloat() < 0.02F) {
			entityPlayer1 = this.worldObj.getClosestPlayerToEntity(this, (double)f2);
			if(entityPlayer1 != null) {
				this.currentTarget = entityPlayer1;
				this.numTicksToChaseTarget = 10 + this.rand.nextInt(20);
			} else {
				this.randomYawVelocity = (this.rand.nextFloat() - 0.5F) * 20.0F;
			}
		}

		if(this.currentTarget != null) {
			this.faceEntity(this.currentTarget, 10.0F, (float)this.func_25026_x());
			if(this.numTicksToChaseTarget-- <= 0 || this.currentTarget.isDead || this.currentTarget.getDistanceSqToEntity(this) > (double)(f2 * f2)) {
				this.currentTarget = null;
			}
		} else {
			if(this.rand.nextFloat() < 0.05F) {
				this.randomYawVelocity = (this.rand.nextFloat() - 0.5F) * 20.0F;
			}

			this.rotationYaw += this.randomYawVelocity;
			this.rotationPitch = this.defaultPitch;
		}

		boolean z3 = this.isInWater();
		boolean z4 = this.handleLavaMovement();
		if(z3 || z4) {
			this.isJumping = this.rand.nextFloat() < 0.8F;
		}

	}

	protected int func_25026_x() {
		return 40;
	}

	public void faceEntity(Entity entity1, float f2, float f3) {
		double d4 = entity1.posX - this.posX;
		double d8 = entity1.posZ - this.posZ;
		double d6;
		if(entity1 instanceof EntityLiving) {
			EntityLiving entityLiving10 = (EntityLiving)entity1;
			d6 = this.posY + (double)this.getEyeHeight() - (entityLiving10.posY + (double)entityLiving10.getEyeHeight());
		} else {
			d6 = (entity1.boundingBox.minY + entity1.boundingBox.maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
		}

		double d14 = (double)MathHelper.sqrt_double(d4 * d4 + d8 * d8);
		float f12 = (float)(Math.atan2(d8, d4) * 180.0D / (double)(float)Math.PI) - 90.0F;
		float f13 = (float)(-(Math.atan2(d6, d14) * 180.0D / (double)(float)Math.PI));
		this.rotationPitch = -this.updateRotation(this.rotationPitch, f13, f3);
		this.rotationYaw = this.updateRotation(this.rotationYaw, f12, f2);
	}

	public boolean hasCurrentTarget() {
		return this.currentTarget != null;
	}

	public Entity getCurrentTarget() {
		return this.currentTarget;
	}

	private float updateRotation(float f1, float f2, float f3) {
		float f4;
		for(f4 = f2 - f1; f4 < -180.0F; f4 += 360.0F) {
		}

		while(f4 >= 180.0F) {
			f4 -= 360.0F;
		}

		if(f4 > f3) {
			f4 = f3;
		}

		if(f4 < -f3) {
			f4 = -f3;
		}

		return f1 + f4;
	}

	public void onEntityDeath() {
	}

	public boolean getCanSpawnHere() {
		return this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
	}

	protected void kill() {
		this.attackEntityFrom((Entity)null, 4);
	}

	public float getSwingProgress(float f1) {
		float f2 = this.swingProgress - this.prevSwingProgress;
		if(f2 < 0.0F) {
			++f2;
		}

		return this.prevSwingProgress + f2 * f1;
	}

	public Vec3D getPosition(float f1) {
		if(f1 == 1.0F) {
			return Vec3D.createVector(this.posX, this.posY, this.posZ);
		} else {
			double d2 = this.prevPosX + (this.posX - this.prevPosX) * (double)f1;
			double d4 = this.prevPosY + (this.posY - this.prevPosY) * (double)f1;
			double d6 = this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f1;
			return Vec3D.createVector(d2, d4, d6);
		}
	}

	public Vec3D getLookVec() {
		return this.getLook(1.0F);
	}

	public Vec3D getLook(float f1) {
		float f2;
		float f3;
		float f4;
		float f5;
		if(f1 == 1.0F) {
			f2 = MathHelper.cos(-this.rotationYaw * 0.017453292F - (float)Math.PI);
			f3 = MathHelper.sin(-this.rotationYaw * 0.017453292F - (float)Math.PI);
			f4 = -MathHelper.cos(-this.rotationPitch * 0.017453292F);
			f5 = MathHelper.sin(-this.rotationPitch * 0.017453292F);
			return Vec3D.createVector((double)(f3 * f4), (double)f5, (double)(f2 * f4));
		} else {
			f2 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * f1;
			f3 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * f1;
			f4 = MathHelper.cos(-f3 * 0.017453292F - (float)Math.PI);
			f5 = MathHelper.sin(-f3 * 0.017453292F - (float)Math.PI);
			float f6 = -MathHelper.cos(-f2 * 0.017453292F);
			float f7 = MathHelper.sin(-f2 * 0.017453292F);
			return Vec3D.createVector((double)(f5 * f6), (double)f7, (double)(f4 * f6));
		}
	}

	public MovingObjectPosition rayTrace(double d1, float f3) {
		Vec3D vec3D4 = this.getPosition(f3);
		Vec3D vec3D5 = this.getLook(f3);
		Vec3D vec3D6 = vec3D4.addVector(vec3D5.xCoord * d1, vec3D5.yCoord * d1, vec3D5.zCoord * d1);
		return this.worldObj.rayTraceBlocks(vec3D4, vec3D6);
	}

	public int getMaxSpawnedInChunk() {
		return 4;
	}

	public ItemStack getHeldItem() {
		return null;
	}

	public void handleHealthUpdate(byte b1) {
		if(b1 == 2) {
			this.field_704_R = 1.5F;
			this.heartsLife = this.heartsHalvesLife;
			this.hurtTime = this.maxHurtTime = 10;
			this.attackedAtYaw = 0.0F;
			this.worldObj.playSoundAtEntity(this, this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.attackEntityFrom((Entity)null, 0);
		} else if(b1 == 3) {
			this.worldObj.playSoundAtEntity(this, this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.health = 0;
			this.onDeath((Entity)null);
		} else {
			super.handleHealthUpdate(b1);
		}

	}

	public boolean isPlayerSleeping() {
		return false;
	}

	public int getItemIcon(ItemStack itemStack1) {
		return itemStack1.getIconIndex();
	}
}
