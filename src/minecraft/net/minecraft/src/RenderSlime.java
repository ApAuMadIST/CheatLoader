package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderSlime extends RenderLiving {
	private ModelBase scaleAmount;

	public RenderSlime(ModelBase modelBase1, ModelBase modelBase2, float f3) {
		super(modelBase1, f3);
		this.scaleAmount = modelBase2;
	}

	protected boolean renderSlimePassModel(EntitySlime entitySlime1, int i2, float f3) {
		if(i2 == 0) {
			this.setRenderPassModel(this.scaleAmount);
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			return true;
		} else {
			if(i2 == 1) {
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			return false;
		}
	}

	protected void scaleSlime(EntitySlime entitySlime1, float f2) {
		int i3 = entitySlime1.getSlimeSize();
		float f4 = (entitySlime1.field_767_b + (entitySlime1.field_768_a - entitySlime1.field_767_b) * f2) / ((float)i3 * 0.5F + 1.0F);
		float f5 = 1.0F / (f4 + 1.0F);
		float f6 = (float)i3;
		GL11.glScalef(f5 * f6, 1.0F / f5 * f6, f5 * f6);
	}

	protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
		this.scaleSlime((EntitySlime)entityLiving1, f2);
	}

	protected boolean shouldRenderPass(EntityLiving entityLiving1, int i2, float f3) {
		return this.renderSlimePassModel((EntitySlime)entityLiving1, i2, f3);
	}
}
