package net.minecraft.src;

import java.util.Random;

public class ModelGhast extends ModelBase {
	ModelRenderer body;
	ModelRenderer[] tentacles = new ModelRenderer[9];

	public ModelGhast() {
		byte b1 = -16;
		this.body = new ModelRenderer(0, 0);
		this.body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
		this.body.rotationPointY += (float)(24 + b1);
		Random random2 = new Random(1660L);

		for(int i3 = 0; i3 < this.tentacles.length; ++i3) {
			this.tentacles[i3] = new ModelRenderer(0, 0);
			float f4 = (((float)(i3 % 3) - (float)(i3 / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
			float f5 = ((float)(i3 / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
			int i6 = random2.nextInt(7) + 8;
			this.tentacles[i3].addBox(-1.0F, 0.0F, -1.0F, 2, i6, 2);
			this.tentacles[i3].rotationPointX = f4;
			this.tentacles[i3].rotationPointZ = f5;
			this.tentacles[i3].rotationPointY = (float)(31 + b1);
		}

	}

	public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6) {
		for(int i7 = 0; i7 < this.tentacles.length; ++i7) {
			this.tentacles[i7].rotateAngleX = 0.2F * MathHelper.sin(f3 * 0.3F + (float)i7) + 0.4F;
		}

	}

	public void render(float f1, float f2, float f3, float f4, float f5, float f6) {
		this.setRotationAngles(f1, f2, f3, f4, f5, f6);
		this.body.render(f6);

		for(int i7 = 0; i7 < this.tentacles.length; ++i7) {
			this.tentacles[i7].render(f6);
		}

	}
}
