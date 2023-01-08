package net.minecraft.src;

public class EntityGhast extends EntityFlying implements IMob {
	public int courseChangeCooldown = 0;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private Entity targetedEntity = null;
	private int aggroCooldown = 0;
	public int prevAttackCounter = 0;
	public int attackCounter = 0;

	public EntityGhast(World world1) {
		super(world1);
		this.texture = "/mob/ghast.png";
		this.setSize(4.0F, 4.0F);
		this.isImmuneToFire = true;
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, (byte)0);
	}

	public void onUpdate() {
		super.onUpdate();
		byte b1 = this.dataWatcher.getWatchableObjectByte(16);
		this.texture = b1 == 1 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
	}

	protected void updatePlayerActionState() {
		if(!this.worldObj.multiplayerWorld && this.worldObj.difficultySetting == 0) {
			this.setEntityDead();
		}

		this.func_27021_X();
		this.prevAttackCounter = this.attackCounter;
		double d1 = this.waypointX - this.posX;
		double d3 = this.waypointY - this.posY;
		double d5 = this.waypointZ - this.posZ;
		double d7 = (double)MathHelper.sqrt_double(d1 * d1 + d3 * d3 + d5 * d5);
		if(d7 < 1.0D || d7 > 60.0D) {
			this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
		}

		if(this.courseChangeCooldown-- <= 0) {
			this.courseChangeCooldown += this.rand.nextInt(5) + 2;
			if(this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d7)) {
				this.motionX += d1 / d7 * 0.1D;
				this.motionY += d3 / d7 * 0.1D;
				this.motionZ += d5 / d7 * 0.1D;
			} else {
				this.waypointX = this.posX;
				this.waypointY = this.posY;
				this.waypointZ = this.posZ;
			}
		}

		if(this.targetedEntity != null && this.targetedEntity.isDead) {
			this.targetedEntity = null;
		}

		if(this.targetedEntity == null || this.aggroCooldown-- <= 0) {
			this.targetedEntity = this.worldObj.getClosestPlayerToEntity(this, 100.0D);
			if(this.targetedEntity != null) {
				this.aggroCooldown = 20;
			}
		}

		double d9 = 64.0D;
		if(this.targetedEntity != null && this.targetedEntity.getDistanceSqToEntity(this) < d9 * d9) {
			double d11 = this.targetedEntity.posX - this.posX;
			double d13 = this.targetedEntity.boundingBox.minY + (double)(this.targetedEntity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
			double d15 = this.targetedEntity.posZ - this.posZ;
			this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(d11, d15)) * 180.0F / (float)Math.PI;
			if(this.canEntityBeSeen(this.targetedEntity)) {
				if(this.attackCounter == 10) {
					this.worldObj.playSoundAtEntity(this, "mob.ghast.charge", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				}

				++this.attackCounter;
				if(this.attackCounter == 20) {
					this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
					EntityFireball entityFireball17 = new EntityFireball(this.worldObj, this, d11, d13, d15);
					double d18 = 4.0D;
					Vec3D vec3D20 = this.getLook(1.0F);
					entityFireball17.posX = this.posX + vec3D20.xCoord * d18;
					entityFireball17.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
					entityFireball17.posZ = this.posZ + vec3D20.zCoord * d18;
					this.worldObj.entityJoinedWorld(entityFireball17);
					this.attackCounter = -40;
				}
			} else if(this.attackCounter > 0) {
				--this.attackCounter;
			}
		} else {
			this.renderYawOffset = this.rotationYaw = -((float)Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float)Math.PI;
			if(this.attackCounter > 0) {
				--this.attackCounter;
			}
		}

		if(!this.worldObj.multiplayerWorld) {
			byte b21 = this.dataWatcher.getWatchableObjectByte(16);
			byte b12 = (byte)(this.attackCounter > 10 ? 1 : 0);
			if(b21 != b12) {
				this.dataWatcher.updateObject(16, b12);
			}
		}

	}

	private boolean isCourseTraversable(double d1, double d3, double d5, double d7) {
		double d9 = (this.waypointX - this.posX) / d7;
		double d11 = (this.waypointY - this.posY) / d7;
		double d13 = (this.waypointZ - this.posZ) / d7;
		AxisAlignedBB axisAlignedBB15 = this.boundingBox.copy();

		for(int i16 = 1; (double)i16 < d7; ++i16) {
			axisAlignedBB15.offset(d9, d11, d13);
			if(this.worldObj.getCollidingBoundingBoxes(this, axisAlignedBB15).size() > 0) {
				return false;
			}
		}

		return true;
	}

	protected String getLivingSound() {
		return "mob.ghast.moan";
	}

	protected String getHurtSound() {
		return "mob.ghast.scream";
	}

	protected String getDeathSound() {
		return "mob.ghast.death";
	}

	protected int getDropItemId() {
		return Item.gunpowder.shiftedIndex;
	}

	protected float getSoundVolume() {
		return 10.0F;
	}

	public boolean getCanSpawnHere() {
		return this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.worldObj.difficultySetting > 0;
	}

	public int getMaxSpawnedInChunk() {
		return 1;
	}
}
