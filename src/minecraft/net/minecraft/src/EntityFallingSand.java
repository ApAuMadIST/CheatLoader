package net.minecraft.src;

public class EntityFallingSand extends Entity {
	public int blockID;
	public int fallTime = 0;

	public EntityFallingSand(World world1) {
		super(world1);
	}

	public EntityFallingSand(World world1, double d2, double d4, double d6, int i8) {
		super(world1);
		this.blockID = i8;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
		this.setPosition(d2, d4, d6);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = d2;
		this.prevPosY = d4;
		this.prevPosZ = d6;
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public void onUpdate() {
		if(this.blockID == 0) {
			this.setEntityDead();
		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			++this.fallTime;
			this.motionY -= (double)0.04F;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double)0.98F;
			this.motionY *= (double)0.98F;
			this.motionZ *= (double)0.98F;
			int i1 = MathHelper.floor_double(this.posX);
			int i2 = MathHelper.floor_double(this.posY);
			int i3 = MathHelper.floor_double(this.posZ);
			if(this.worldObj.getBlockId(i1, i2, i3) == this.blockID) {
				this.worldObj.setBlockWithNotify(i1, i2, i3, 0);
			}

			if(this.onGround) {
				this.motionX *= (double)0.7F;
				this.motionZ *= (double)0.7F;
				this.motionY *= -0.5D;
				this.setEntityDead();
				if((!this.worldObj.canBlockBePlacedAt(this.blockID, i1, i2, i3, true, 1) || BlockSand.canFallBelow(this.worldObj, i1, i2 - 1, i3) || !this.worldObj.setBlockWithNotify(i1, i2, i3, this.blockID)) && !this.worldObj.multiplayerWorld) {
					this.dropItem(this.blockID, 1);
				}
			} else if(this.fallTime > 100 && !this.worldObj.multiplayerWorld) {
				this.dropItem(this.blockID, 1);
				this.setEntityDead();
			}

		}
	}

	protected void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		nBTTagCompound1.setByte("Tile", (byte)this.blockID);
	}

	protected void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		this.blockID = nBTTagCompound1.getByte("Tile") & 255;
	}

	public float getShadowSize() {
		return 0.0F;
	}

	public World getWorld() {
		return this.worldObj;
	}
}
