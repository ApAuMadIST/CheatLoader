package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderBiped extends RenderLiving {
	protected ModelBiped modelBipedMain;

	public RenderBiped(ModelBiped modelBiped1, float f2) {
		super(modelBiped1, f2);
		this.modelBipedMain = modelBiped1;
	}

	protected void renderEquippedItems(EntityLiving entityLiving1, float f2) {
		ItemStack itemStack3 = entityLiving1.getHeldItem();
		if(itemStack3 != null) {
			GL11.glPushMatrix();
			this.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			float f4;
			if(itemStack3.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemStack3.itemID].getRenderType())) {
				f4 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				f4 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f4, -f4, f4);
			} else if(Item.itemsList[itemStack3.itemID].isFull3D()) {
				f4 = 0.625F;
				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glScalef(f4, -f4, f4);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else {
				f4 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(f4, f4, f4);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			this.renderManager.itemRenderer.renderItem(entityLiving1, itemStack3);
			GL11.glPopMatrix();
		}

	}
}
