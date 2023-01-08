package net.minecraft.src;

public class EntityOtherPlayerMP extends EntityPlayer {
	private int field_785_bg;
	private double field_784_bh;
	private double field_783_bi;
	private double field_782_bj;
	private double field_780_bk;
	private double field_786_bl;
	float field_20924_a = 0.0F;

	public EntityOtherPlayerMP(World world1, String string2) {
		super(world1);
		this.username = string2;
		this.yOffset = 0.0F;
		this.stepHeight = 0.0F;
		if(string2 != null && string2.length() > 0) {
			this.skinUrl = "http://s3.amazonaws.com/MinecraftSkins/" + string2 + ".png";
		}

		this.noClip = true;
		this.field_22062_y = 0.25F;
		this.renderDistanceWeight = 10.0D;
	}

	protected void resetHeight() {
		this.yOffset = 0.0F;
	}

	public boolean attackEntityFrom(Entity entity1, int i2) {
		return true;
	}

	public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
		this.field_784_bh = d1;
		this.field_783_bi = d3;
		this.field_782_bj = d5;
		this.field_780_bk = (double)f7;
		this.field_786_bl = (double)f8;
		this.field_785_bg = i9;
	}

	public void onUpdate() {
		this.field_22062_y = 0.0F;
		super.onUpdate();
		this.field_705_Q = this.field_704_R;
		double d1 = this.posX - this.prevPosX;
		double d3 = this.posZ - this.prevPosZ;
		float f5 = MathHelper.sqrt_double(d1 * d1 + d3 * d3) * 4.0F;
		if(f5 > 1.0F) {
			f5 = 1.0F;
		}

		this.field_704_R += (f5 - this.field_704_R) * 0.4F;
		this.field_703_S += this.field_704_R;
	}

	public float getShadowSize() {
		return 0.0F;
	}

	public void onLivingUpdate() {
		super.updatePlayerActionState();
		if(this.field_785_bg > 0) {
			double d1 = this.posX + (this.field_784_bh - this.posX) / (double)this.field_785_bg;
			double d3 = this.posY + (this.field_783_bi - this.posY) / (double)this.field_785_bg;
			double d5 = this.posZ + (this.field_782_bj - this.posZ) / (double)this.field_785_bg;

			double d7;
			for(d7 = this.field_780_bk - (double)this.rotationYaw; d7 < -180.0D; d7 += 360.0D) {
			}

			while(d7 >= 180.0D) {
				d7 -= 360.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + d7 / (double)this.field_785_bg);
			this.rotationPitch = (float)((double)this.rotationPitch + (this.field_786_bl - (double)this.rotationPitch) / (double)this.field_785_bg);
			--this.field_785_bg;
			this.setPosition(d1, d3, d5);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}

		this.field_775_e = this.field_774_f;
		float f9 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		float f2 = (float)Math.atan(-this.motionY * (double)0.2F) * 15.0F;
		if(f9 > 0.1F) {
			f9 = 0.1F;
		}

		if(!this.onGround || this.health <= 0) {
			f9 = 0.0F;
		}

		if(this.onGround || this.health <= 0) {
			f2 = 0.0F;
		}

		this.field_774_f += (f9 - this.field_774_f) * 0.4F;
		this.field_9328_R += (f2 - this.field_9328_R) * 0.8F;
	}

	public void outfitWithItem(int i1, int i2, int i3) {
		ItemStack itemStack4 = null;
		if(i2 >= 0) {
			itemStack4 = new ItemStack(i2, 1, i3);
		}

		if(i1 == 0) {
			this.inventory.mainInventory[this.inventory.currentItem] = itemStack4;
		} else {
			this.inventory.armorInventory[i1 - 1] = itemStack4;
		}

	}

	public void func_6420_o() {
	}
}
