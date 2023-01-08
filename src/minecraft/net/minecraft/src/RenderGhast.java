package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderGhast extends RenderLiving {
	public RenderGhast() {
		super(new ModelGhast(), 0.5F);
	}

	protected void func_4014_a(EntityGhast entityGhast1, float f2) {
		float f4 = ((float)entityGhast1.prevAttackCounter + (float)(entityGhast1.attackCounter - entityGhast1.prevAttackCounter) * f2) / 20.0F;
		if(f4 < 0.0F) {
			f4 = 0.0F;
		}

		f4 = 1.0F / (f4 * f4 * f4 * f4 * f4 * 2.0F + 1.0F);
		float f5 = (8.0F + f4) / 2.0F;
		float f6 = (8.0F + 1.0F / f4) / 2.0F;
		GL11.glScalef(f6, f5, f6);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
		this.func_4014_a((EntityGhast)entityLiving1, f2);
	}
}
