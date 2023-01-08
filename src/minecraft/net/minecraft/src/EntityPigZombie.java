package net.minecraft.src;

import java.util.List;

public class EntityPigZombie extends EntityZombie {
	private int angerLevel = 0;
	private int randomSoundDelay = 0;
	private static final ItemStack defaultHeldItem = new ItemStack(Item.swordGold, 1);

	public EntityPigZombie(World world1) {
		super(world1);
		this.texture = "/mob/pigzombie.png";
		this.moveSpeed = 0.5F;
		this.attackStrength = 5;
		this.isImmuneToFire = true;
	}

	public void onUpdate() {
		this.moveSpeed = this.playerToAttack != null ? 0.95F : 0.5F;
		if(this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
			this.worldObj.playSoundAtEntity(this, "mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
		}

		super.onUpdate();
	}

	public boolean getCanSpawnHere() {
		return this.worldObj.difficultySetting > 0 && this.worldObj.checkIfAABBIsClear(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.getIsAnyLiquid(this.boundingBox);
	}

	public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		super.writeEntityToNBT(nBTTagCompound1);
		nBTTagCompound1.setShort("Anger", (short)this.angerLevel);
	}

	public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		super.readEntityFromNBT(nBTTagCompound1);
		this.angerLevel = nBTTagCompound1.getShort("Anger");
	}

	protected Entity findPlayerToAttack() {
		return this.angerLevel == 0 ? null : super.findPlayerToAttack();
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
	}

	public boolean attackEntityFrom(Entity entity1, int i2) {
		if(entity1 instanceof EntityPlayer) {
			List list3 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0D, 32.0D, 32.0D));

			for(int i4 = 0; i4 < list3.size(); ++i4) {
				Entity entity5 = (Entity)list3.get(i4);
				if(entity5 instanceof EntityPigZombie) {
					EntityPigZombie entityPigZombie6 = (EntityPigZombie)entity5;
					entityPigZombie6.becomeAngryAt(entity1);
				}
			}

			this.becomeAngryAt(entity1);
		}

		return super.attackEntityFrom(entity1, i2);
	}

	private void becomeAngryAt(Entity entity1) {
		this.playerToAttack = entity1;
		this.angerLevel = 400 + this.rand.nextInt(400);
		this.randomSoundDelay = this.rand.nextInt(40);
	}

	protected String getLivingSound() {
		return "mob.zombiepig.zpig";
	}

	protected String getHurtSound() {
		return "mob.zombiepig.zpighurt";
	}

	protected String getDeathSound() {
		return "mob.zombiepig.zpigdeath";
	}

	protected int getDropItemId() {
		return Item.porkCooked.shiftedIndex;
	}

	public ItemStack getHeldItem() {
		return defaultHeldItem;
	}
}
