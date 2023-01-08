package net.minecraft.src;

import java.util.Random;

public class EntitySheep extends EntityAnimal {
	public static final float[][] fleeceColorTable = new float[][]{{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F}, {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F}, {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};

	public EntitySheep(World world1) {
		super(world1);
		this.texture = "/mob/sheep.png";
		this.setSize(0.9F, 1.3F);
	}

	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte)0));
	}

	public boolean attackEntityFrom(Entity entity1, int i2) {
		return super.attackEntityFrom(entity1, i2);
	}

	protected void dropFewItems() {
		if(!this.getSheared()) {
			this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 0.0F);
		}

	}

	protected int getDropItemId() {
		return Block.cloth.blockID;
	}

	public boolean interact(EntityPlayer entityPlayer1) {
		ItemStack itemStack2 = entityPlayer1.inventory.getCurrentItem();
		if(itemStack2 != null && itemStack2.itemID == Item.shears.shiftedIndex && !this.getSheared()) {
			if(!this.worldObj.multiplayerWorld) {
				this.setSheared(true);
				int i3 = 2 + this.rand.nextInt(3);

				for(int i4 = 0; i4 < i3; ++i4) {
					EntityItem entityItem5 = this.entityDropItem(new ItemStack(Block.cloth.blockID, 1, this.getFleeceColor()), 1.0F);
					entityItem5.motionY += (double)(this.rand.nextFloat() * 0.05F);
					entityItem5.motionX += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
					entityItem5.motionZ += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
				}
			}

			itemStack2.damageItem(1, entityPlayer1);
		}

		return false;
	}

	public void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		super.writeEntityToNBT(nBTTagCompound1);
		nBTTagCompound1.setBoolean("Sheared", this.getSheared());
		nBTTagCompound1.setByte("Color", (byte)this.getFleeceColor());
	}

	public void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		super.readEntityFromNBT(nBTTagCompound1);
		this.setSheared(nBTTagCompound1.getBoolean("Sheared"));
		this.setFleeceColor(nBTTagCompound1.getByte("Color"));
	}

	protected String getLivingSound() {
		return "mob.sheep";
	}

	protected String getHurtSound() {
		return "mob.sheep";
	}

	protected String getDeathSound() {
		return "mob.sheep";
	}

	public int getFleeceColor() {
		return this.dataWatcher.getWatchableObjectByte(16) & 15;
	}

	public void setFleeceColor(int i1) {
		byte b2 = this.dataWatcher.getWatchableObjectByte(16);
		this.dataWatcher.updateObject(16, (byte)(b2 & 240 | i1 & 15));
	}

	public boolean getSheared() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
	}

	public void setSheared(boolean z1) {
		byte b2 = this.dataWatcher.getWatchableObjectByte(16);
		if(z1) {
			this.dataWatcher.updateObject(16, (byte)(b2 | 16));
		} else {
			this.dataWatcher.updateObject(16, (byte)(b2 & -17));
		}

	}

	public static int getRandomFleeceColor(Random random0) {
		int i1 = random0.nextInt(100);
		return i1 < 5 ? 15 : (i1 < 10 ? 7 : (i1 < 15 ? 8 : (i1 < 18 ? 12 : (random0.nextInt(500) == 0 ? 6 : 0))));
	}
}
