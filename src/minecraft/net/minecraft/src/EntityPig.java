package net.minecraft.src;

public class EntityPig extends EntityAnimal {
	public EntityPig(World world1) {
		super(world1);
		this.texture = "/mob/pig.png";
		this.setSize(0.9F, 0.9F);
	}

	protected void entityInit() {
		this.dataWatcher.addObject(16, (byte)0);
	}

	public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		super.writeEntityToNBT(nBTTagCompound1);
		nBTTagCompound1.setBoolean("Saddle", this.getSaddled());
	}

	public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		super.readEntityFromNBT(nBTTagCompound1);
		this.setSaddled(nBTTagCompound1.getBoolean("Saddle"));
	}

	protected String getLivingSound() {
		return "mob.pig";
	}

	protected String getHurtSound() {
		return "mob.pig";
	}

	protected String getDeathSound() {
		return "mob.pigdeath";
	}

	public boolean interact(EntityPlayer entityPlayer1) {
		if(!this.getSaddled() || this.worldObj.multiplayerWorld || this.riddenByEntity != null && this.riddenByEntity != entityPlayer1) {
			return false;
		} else {
			entityPlayer1.mountEntity(this);
			return true;
		}
	}

	protected int getDropItemId() {
		return this.fire > 0 ? Item.porkCooked.shiftedIndex : Item.porkRaw.shiftedIndex;
	}

	public boolean getSaddled() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setSaddled(boolean z1) {
		if(z1) {
			this.dataWatcher.updateObject(16, (byte)1);
		} else {
			this.dataWatcher.updateObject(16, (byte)0);
		}

	}

	public void onStruckByLightning(EntityLightningBolt entityLightningBolt1) {
		if(!this.worldObj.multiplayerWorld) {
			EntityPigZombie entityPigZombie2 = new EntityPigZombie(this.worldObj);
			entityPigZombie2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.worldObj.entityJoinedWorld(entityPigZombie2);
			this.setEntityDead();
		}
	}

	protected void fall(float f1) {
		super.fall(f1);
		if(f1 > 5.0F && this.riddenByEntity instanceof EntityPlayer) {
			((EntityPlayer)this.riddenByEntity).triggerAchievement(AchievementList.flyPig);
		}

	}
}
