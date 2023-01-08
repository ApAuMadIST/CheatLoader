package net.minecraft.src;

public class EntityWaterMob extends EntityCreature {
	public EntityWaterMob(World world1) {
		super(world1);
	}

	public boolean canBreatheUnderwater() {
		return true;
	}

	public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		super.writeEntityToNBT(nBTTagCompound1);
	}

	public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		super.readEntityFromNBT(nBTTagCompound1);
	}

	public boolean getCanSpawnHere() {
		return this.worldObj.checkIfAABBIsClear(this.boundingBox);
	}

	public int getTalkInterval() {
		return 120;
	}
}
