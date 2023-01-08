package net.minecraft.src;

public class EntityTNTPrimed extends Entity {
	public int fuse;

	public EntityTNTPrimed(World world1) {
		super(world1);
		this.fuse = 0;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
	}

	public EntityTNTPrimed(World world1, double d2, double d4, double d6) {
		this(world1);
		this.setPosition(d2, d4, d6);
		float f8 = (float)(Math.random() * (double)(float)Math.PI * 2.0D);
		this.motionX = (double)(-MathHelper.sin(f8 * (float)Math.PI / 180.0F) * 0.02F);
		this.motionY = (double)0.2F;
		this.motionZ = (double)(-MathHelper.cos(f8 * (float)Math.PI / 180.0F) * 0.02F);
		this.fuse = 80;
		this.prevPosX = d2;
		this.prevPosY = d4;
		this.prevPosZ = d6;
	}

	protected void entityInit() {
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= (double)0.04F;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= (double)0.98F;
		this.motionY *= (double)0.98F;
		this.motionZ *= (double)0.98F;
		if(this.onGround) {
			this.motionX *= (double)0.7F;
			this.motionZ *= (double)0.7F;
			this.motionY *= -0.5D;
		}

		if(this.fuse-- <= 0) {
			if(!this.worldObj.multiplayerWorld) {
				this.setEntityDead();
				this.explode();
			} else {
				this.setEntityDead();
			}
		} else {
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}

	}

	private void explode() {
		float f1 = 4.0F;
		this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, f1);
	}

	protected void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		nBTTagCompound1.setByte("Fuse", (byte)this.fuse);
	}

	protected void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		this.fuse = nBTTagCompound1.getByte("Fuse");
	}

	public float getShadowSize() {
		return 0.0F;
	}
}
