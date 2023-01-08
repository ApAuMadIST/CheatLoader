package net.minecraft.src;

public class EntityItem extends Entity {
	public ItemStack item;
	private int field_803_e;
	public int age = 0;
	public int delayBeforeCanPickup;
	private int health = 5;
	public float field_804_d = (float)(Math.random() * Math.PI * 2.0D);

	public EntityItem(World paramfd, double paramDouble1, double paramDouble2, double paramDouble3, ItemStack paramiz) {
		super(paramfd);
		this.setSize(0.25F, 0.25F);
		this.yOffset = this.height / 2.0F;
		this.setPosition(paramDouble1, paramDouble2, paramDouble3);
		this.item = paramiz;
		this.rotationYaw = (float)(Math.random() * 360.0D);
		this.motionX = (double)((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
		this.motionY = 0.2000000029802322D;
		this.motionZ = (double)((float)(Math.random() * 0.2000000029802322D - 0.1000000014901161D));
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	public EntityItem(World paramfd) {
		super(paramfd);
		this.setSize(0.25F, 0.25F);
		this.yOffset = this.height / 2.0F;
	}

	protected void entityInit() {
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.delayBeforeCanPickup > 0) {
			--this.delayBeforeCanPickup;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= (double)0.04F;
		if(this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) == Material.lava) {
			this.motionY = 0.2000000029802322D;
			this.motionX = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
			this.motionZ = (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
			this.worldObj.playSoundAtEntity(this, "random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
		}

		this.pushOutOfBlocks(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0D, this.posZ);
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		float f1 = 0.98F;
		if(this.onGround) {
			f1 = 0.5880001F;
			int i = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
			if(i > 0) {
				f1 = Block.blocksList[i].slipperiness * 0.98F;
			}
		}

		this.motionX *= (double)f1;
		this.motionY *= (double)0.98F;
		this.motionZ *= (double)f1;
		if(this.onGround) {
			this.motionY *= -0.5D;
		}

		++this.field_803_e;
		++this.age;
		if(this.age >= 6000) {
			this.setEntityDead();
		}

	}

	public boolean handleWaterMovement() {
		return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
	}

	protected void dealFireDamage(int paramInt) {
		this.attackEntityFrom((Entity)null, paramInt);
	}

	public boolean attackEntityFrom(Entity paramsn, int paramInt) {
		this.setBeenAttacked();
		this.health -= paramInt;
		if(this.health <= 0) {
			this.setEntityDead();
		}

		return false;
	}

	public void writeEntityToNBT(NBTTagCompound paramnu) {
		paramnu.setShort("Health", (short)((byte)this.health));
		paramnu.setShort("Age", (short)this.age);
		paramnu.setCompoundTag("Item", this.item.writeToNBT(new NBTTagCompound()));
	}

	public void readEntityFromNBT(NBTTagCompound paramnu) {
		this.health = paramnu.getShort("Health") & 255;
		this.age = paramnu.getShort("Age");
		NBTTagCompound localnu = paramnu.getCompoundTag("Item");
		this.item = new ItemStack(localnu);
	}

	public void onCollideWithPlayer(EntityPlayer paramgs) {
		if(!this.worldObj.multiplayerWorld) {
			int i = this.item.stackSize;
			if(this.delayBeforeCanPickup == 0 && paramgs.inventory.addItemStackToInventory(this.item)) {
				if(this.item.itemID == Block.wood.blockID) {
					paramgs.triggerAchievement(AchievementList.mineWood);
				}

				if(this.item.itemID == Item.leather.shiftedIndex) {
					paramgs.triggerAchievement(AchievementList.killCow);
				}

				ModLoader.OnItemPickup(paramgs, this.item);
				this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				paramgs.onItemPickup(this, i);
				if(this.item.stackSize <= 0) {
					this.setEntityDead();
				}
			}

		}
	}
}
