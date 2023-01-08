package net.minecraft.src;

public class RenderWolf extends RenderLiving {
	public RenderWolf(ModelBase modelBase1, float f2) {
		super(modelBase1, f2);
	}

	public void renderWolf(EntityWolf entityWolf1, double d2, double d4, double d6, float f8, float f9) {
		super.doRenderLiving(entityWolf1, d2, d4, d6, f8, f9);
	}

	protected float func_25004_a(EntityWolf entityWolf1, float f2) {
		return entityWolf1.setTailRotation();
	}

	protected void func_25006_b(EntityWolf entityWolf1, float f2) {
	}

	protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
		this.func_25006_b((EntityWolf)entityLiving1, f2);
	}

	protected float func_170_d(EntityLiving entityLiving1, float f2) {
		return this.func_25004_a((EntityWolf)entityLiving1, f2);
	}

	public void doRenderLiving(EntityLiving entityLiving1, double d2, double d4, double d6, float f8, float f9) {
		this.renderWolf((EntityWolf)entityLiving1, d2, d4, d6, f8, f9);
	}

	public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
		this.renderWolf((EntityWolf)entity1, d2, d4, d6, f8, f9);
	}
}
