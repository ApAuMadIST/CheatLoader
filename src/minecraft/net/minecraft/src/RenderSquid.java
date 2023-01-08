package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderSquid extends RenderLiving {
	public RenderSquid(ModelBase modelBase1, float f2) {
		super(modelBase1, f2);
	}

	public void func_21008_a(EntitySquid entitySquid1, double d2, double d4, double d6, float f8, float f9) {
		super.doRenderLiving(entitySquid1, d2, d4, d6, f8, f9);
	}

	protected void func_21007_a(EntitySquid entitySquid1, float f2, float f3, float f4) {
		float f5 = entitySquid1.field_21088_b + (entitySquid1.field_21089_a - entitySquid1.field_21088_b) * f4;
		float f6 = entitySquid1.field_21086_f + (entitySquid1.field_21087_c - entitySquid1.field_21086_f) * f4;
		GL11.glTranslatef(0.0F, 0.5F, 0.0F);
		GL11.glRotatef(180.0F - f3, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(f5, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(f6, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.2F, 0.0F);
	}

	protected void func_21005_a(EntitySquid entitySquid1, float f2) {
	}

	protected float func_21006_b(EntitySquid entitySquid1, float f2) {
		float f3 = entitySquid1.field_21082_j + (entitySquid1.field_21083_i - entitySquid1.field_21082_j) * f2;
		return f3;
	}

	protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
		this.func_21005_a((EntitySquid)entityLiving1, f2);
	}

	protected float func_170_d(EntityLiving entityLiving1, float f2) {
		return this.func_21006_b((EntitySquid)entityLiving1, f2);
	}

	protected void rotateCorpse(EntityLiving entityLiving1, float f2, float f3, float f4) {
		this.func_21007_a((EntitySquid)entityLiving1, f2, f3, f4);
	}

	public void doRenderLiving(EntityLiving entityLiving1, double d2, double d4, double d6, float f8, float f9) {
		this.func_21008_a((EntitySquid)entityLiving1, d2, d4, d6, f8, f9);
	}

	public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
		this.func_21008_a((EntitySquid)entity1, d2, d4, d6, f8, f9);
	}
}
