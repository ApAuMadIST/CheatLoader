package net.minecraft.src;

public class EntityCreeper extends EntityMob {
	int timeSinceIgnited;
	int lastActiveTime;

	public EntityCreeper(World world1) {
		super(world1);
		this.texture = "/mob/creeper.png";
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, (byte)-1);
		this.dataWatcher.addObject(17, (byte)0);
	}

	public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		super.writeEntityToNBT(nBTTagCompound1);
		if(this.dataWatcher.getWatchableObjectByte(17) == 1) {
			nBTTagCompound1.setBoolean("powered", true);
		}

	}

	public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		super.readEntityFromNBT(nBTTagCompound1);
		this.dataWatcher.updateObject(17, (byte)(nBTTagCompound1.getBoolean("powered") ? 1 : 0));
	}

	protected void attackBlockedEntity(Entity entity1, float f2) {
		if(!this.worldObj.multiplayerWorld) {
			if(this.timeSinceIgnited > 0) {
				this.setCreeperState(-1);
				--this.timeSinceIgnited;
				if(this.timeSinceIgnited < 0) {
					this.timeSinceIgnited = 0;
				}
			}

		}
	}

	public void onUpdate() {
		this.lastActiveTime = this.timeSinceIgnited;
		if(this.worldObj.multiplayerWorld) {
			int i1 = this.getCreeperState();
			if(i1 > 0 && this.timeSinceIgnited == 0) {
				this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i1;
			if(this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if(this.timeSinceIgnited >= 30) {
				this.timeSinceIgnited = 30;
			}
		}

		super.onUpdate();
		if(this.playerToAttack == null && this.timeSinceIgnited > 0) {
			this.setCreeperState(-1);
			--this.timeSinceIgnited;
			if(this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}
		}

	}

	protected String getHurtSound() {
		return "mob.creeper";
	}

	protected String getDeathSound() {
		return "mob.creeperdeath";
	}

	public void onDeath(Entity entity1) {
		super.onDeath(entity1);
		if(entity1 instanceof EntitySkeleton) {
			this.dropItem(Item.record13.shiftedIndex + this.rand.nextInt(2), 1);
		}

	}

	protected void attackEntity(Entity entity1, float f2) {
		if(!this.worldObj.multiplayerWorld) {
			int i3 = this.getCreeperState();
			if(i3 <= 0 && f2 < 3.0F || i3 > 0 && f2 < 7.0F) {
				if(this.timeSinceIgnited == 0) {
					this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
				}

				this.setCreeperState(1);
				++this.timeSinceIgnited;
				if(this.timeSinceIgnited >= 30) {
					if(this.getPowered()) {
						this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 6.0F);
					} else {
						this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 3.0F);
					}

					this.setEntityDead();
				}

				this.hasAttacked = true;
			} else {
				this.setCreeperState(-1);
				--this.timeSinceIgnited;
				if(this.timeSinceIgnited < 0) {
					this.timeSinceIgnited = 0;
				}
			}

		}
	}

	public boolean getPowered() {
		return this.dataWatcher.getWatchableObjectByte(17) == 1;
	}

	public float setCreeperFlashTime(float f1) {
		return ((float)this.lastActiveTime + (float)(this.timeSinceIgnited - this.lastActiveTime) * f1) / 28.0F;
	}

	protected int getDropItemId() {
		return Item.gunpowder.shiftedIndex;
	}

	private int getCreeperState() {
		return this.dataWatcher.getWatchableObjectByte(16);
	}

	private void setCreeperState(int i1) {
		this.dataWatcher.updateObject(16, (byte)i1);
	}

	public void onStruckByLightning(EntityLightningBolt entityLightningBolt1) {
		super.onStruckByLightning(entityLightningBolt1);
		this.dataWatcher.updateObject(17, (byte)1);
	}
}
