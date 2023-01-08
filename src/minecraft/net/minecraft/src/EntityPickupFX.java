package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class EntityPickupFX extends EntityFX {
	private Entity field_675_a;
	private Entity field_679_o;
	private int field_678_p = 0;
	private int field_677_q = 0;
	private float field_676_r;

	public EntityPickupFX(World world1, Entity entity2, Entity entity3, float f4) {
		super(world1, entity2.posX, entity2.posY, entity2.posZ, entity2.motionX, entity2.motionY, entity2.motionZ);
		this.field_675_a = entity2;
		this.field_679_o = entity3;
		this.field_677_q = 3;
		this.field_676_r = f4;
	}

	public void renderParticle(Tessellator tessellator1, float f2, float f3, float f4, float f5, float f6, float f7) {
		float f8 = ((float)this.field_678_p + f2) / (float)this.field_677_q;
		f8 *= f8;
		double d9 = this.field_675_a.posX;
		double d11 = this.field_675_a.posY;
		double d13 = this.field_675_a.posZ;
		double d15 = this.field_679_o.lastTickPosX + (this.field_679_o.posX - this.field_679_o.lastTickPosX) * (double)f2;
		double d17 = this.field_679_o.lastTickPosY + (this.field_679_o.posY - this.field_679_o.lastTickPosY) * (double)f2 + (double)this.field_676_r;
		double d19 = this.field_679_o.lastTickPosZ + (this.field_679_o.posZ - this.field_679_o.lastTickPosZ) * (double)f2;
		double d21 = d9 + (d15 - d9) * (double)f8;
		double d23 = d11 + (d17 - d11) * (double)f8;
		double d25 = d13 + (d19 - d13) * (double)f8;
		int i27 = MathHelper.floor_double(d21);
		int i28 = MathHelper.floor_double(d23 + (double)(this.yOffset / 2.0F));
		int i29 = MathHelper.floor_double(d25);
		float f30 = this.worldObj.getLightBrightness(i27, i28, i29);
		d21 -= interpPosX;
		d23 -= interpPosY;
		d25 -= interpPosZ;
		GL11.glColor4f(f30, f30, f30, 1.0F);
		RenderManager.instance.renderEntityWithPosYaw(this.field_675_a, (double)((float)d21), (double)((float)d23), (double)((float)d25), this.field_675_a.rotationYaw, f2);
	}

	public void onUpdate() {
		++this.field_678_p;
		if(this.field_678_p == this.field_677_q) {
			this.setEntityDead();
		}

	}

	public int getFXLayer() {
		return 3;
	}
}
