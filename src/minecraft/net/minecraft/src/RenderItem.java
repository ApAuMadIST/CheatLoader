package net.minecraft.src;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderItem extends Render {
	private RenderBlocks renderBlocks = new RenderBlocks();
	private Random random = new Random();
	public boolean field_27004_a = true;

	public RenderItem() {
		this.shadowSize = 0.15F;
		this.field_194_c = 0.75F;
	}

	public void doRenderItem(EntityItem entityItem1, double d2, double d4, double d6, float f8, float f9) {
		this.random.setSeed(187L);
		ItemStack itemStack10 = entityItem1.item;
		GL11.glPushMatrix();
		float f11 = MathHelper.sin(((float)entityItem1.age + f9) / 10.0F + entityItem1.field_804_d) * 0.1F + 0.1F;
		float f12 = (((float)entityItem1.age + f9) / 20.0F + entityItem1.field_804_d) * 57.295776F;
		byte b13 = 1;
		if(entityItem1.item.stackSize > 1) {
			b13 = 2;
		}

		if(entityItem1.item.stackSize > 5) {
			b13 = 3;
		}

		if(entityItem1.item.stackSize > 20) {
			b13 = 4;
		}

		GL11.glTranslatef((float)d2, (float)d4 + f11, (float)d6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float f16;
		float f17;
		float f18;
		if(itemStack10.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemStack10.itemID].getRenderType())) {
			GL11.glRotatef(f12, 0.0F, 1.0F, 0.0F);
			this.loadTexture("/terrain.png");
			float f28 = 0.25F;
			if(!Block.blocksList[itemStack10.itemID].renderAsNormalBlock() && itemStack10.itemID != Block.stairSingle.blockID && Block.blocksList[itemStack10.itemID].getRenderType() != 16) {
				f28 = 0.5F;
			}

			GL11.glScalef(f28, f28, f28);

			for(int i29 = 0; i29 < b13; ++i29) {
				GL11.glPushMatrix();
				if(i29 > 0) {
					f16 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f28;
					f17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f28;
					f18 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f28;
					GL11.glTranslatef(f16, f17, f18);
				}

				this.renderBlocks.renderBlockOnInventory(Block.blocksList[itemStack10.itemID], itemStack10.getItemDamage(), entityItem1.getEntityBrightness(f9));
				GL11.glPopMatrix();
			}
		} else {
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			int i14 = itemStack10.getIconIndex();
			if(itemStack10.itemID < 256) {
				this.loadTexture("/terrain.png");
			} else {
				this.loadTexture("/gui/items.png");
			}

			Tessellator tessellator15 = Tessellator.instance;
			f16 = (float)(i14 % 16 * 16 + 0) / 256.0F;
			f17 = (float)(i14 % 16 * 16 + 16) / 256.0F;
			f18 = (float)(i14 / 16 * 16 + 0) / 256.0F;
			float f19 = (float)(i14 / 16 * 16 + 16) / 256.0F;
			float f20 = 1.0F;
			float f21 = 0.5F;
			float f22 = 0.25F;
			int i23;
			float f24;
			float f25;
			float f26;
			if(this.field_27004_a) {
				i23 = Item.itemsList[itemStack10.itemID].getColorFromDamage(itemStack10.getItemDamage());
				f24 = (float)(i23 >> 16 & 255) / 255.0F;
				f25 = (float)(i23 >> 8 & 255) / 255.0F;
				f26 = (float)(i23 & 255) / 255.0F;
				float f27 = entityItem1.getEntityBrightness(f9);
				GL11.glColor4f(f24 * f27, f25 * f27, f26 * f27, 1.0F);
			}

			for(i23 = 0; i23 < b13; ++i23) {
				GL11.glPushMatrix();
				if(i23 > 0) {
					f24 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					f25 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					f26 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					GL11.glTranslatef(f24, f25, f26);
				}

				GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				tessellator15.startDrawingQuads();
				tessellator15.setNormal(0.0F, 1.0F, 0.0F);
				tessellator15.addVertexWithUV((double)(0.0F - f21), (double)(0.0F - f22), 0.0D, (double)f16, (double)f19);
				tessellator15.addVertexWithUV((double)(f20 - f21), (double)(0.0F - f22), 0.0D, (double)f17, (double)f19);
				tessellator15.addVertexWithUV((double)(f20 - f21), (double)(1.0F - f22), 0.0D, (double)f17, (double)f18);
				tessellator15.addVertexWithUV((double)(0.0F - f21), (double)(1.0F - f22), 0.0D, (double)f16, (double)f18);
				tessellator15.draw();
				GL11.glPopMatrix();
			}
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	public void drawItemIntoGui(FontRenderer fontRenderer1, RenderEngine renderEngine2, int i3, int i4, int i5, int i6, int i7) {
		float f11;
		if(i3 < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[i3].getRenderType())) {
			renderEngine2.bindTexture(renderEngine2.getTexture("/terrain.png"));
			Block block14 = Block.blocksList[i3];
			GL11.glPushMatrix();
			GL11.glTranslatef((float)(i6 - 2), (float)(i7 + 3), -3.0F);
			GL11.glScalef(10.0F, 10.0F, 10.0F);
			GL11.glTranslatef(1.0F, 0.5F, 1.0F);
			GL11.glScalef(1.0F, 1.0F, -1.0F);
			GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			int i15 = Item.itemsList[i3].getColorFromDamage(i4);
			f11 = (float)(i15 >> 16 & 255) / 255.0F;
			float f12 = (float)(i15 >> 8 & 255) / 255.0F;
			float f13 = (float)(i15 & 255) / 255.0F;
			if(this.field_27004_a) {
				GL11.glColor4f(f11, f12, f13, 1.0F);
			}

			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			this.renderBlocks.field_31088_b = this.field_27004_a;
			this.renderBlocks.renderBlockOnInventory(block14, i4, 1.0F);
			this.renderBlocks.field_31088_b = true;
			GL11.glPopMatrix();
		} else if(i5 >= 0) {
			GL11.glDisable(GL11.GL_LIGHTING);
			if(i3 < 256) {
				renderEngine2.bindTexture(renderEngine2.getTexture("/terrain.png"));
			} else {
				renderEngine2.bindTexture(renderEngine2.getTexture("/gui/items.png"));
			}

			int i8 = Item.itemsList[i3].getColorFromDamage(i4);
			float f9 = (float)(i8 >> 16 & 255) / 255.0F;
			float f10 = (float)(i8 >> 8 & 255) / 255.0F;
			f11 = (float)(i8 & 255) / 255.0F;
			if(this.field_27004_a) {
				GL11.glColor4f(f9, f10, f11, 1.0F);
			}

			this.renderTexturedQuad(i6, i7, i5 % 16 * 16, i5 / 16 * 16, 16, 16);
			GL11.glEnable(GL11.GL_LIGHTING);
		}

		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	public void renderItemIntoGUI(FontRenderer fontRenderer1, RenderEngine renderEngine2, ItemStack itemStack3, int i4, int i5) {
		if(itemStack3 != null) {
			this.drawItemIntoGui(fontRenderer1, renderEngine2, itemStack3.itemID, itemStack3.getItemDamage(), itemStack3.getIconIndex(), i4, i5);
		}
	}

	public void renderItemOverlayIntoGUI(FontRenderer fontRenderer1, RenderEngine renderEngine2, ItemStack itemStack3, int i4, int i5) {
		if(itemStack3 != null) {
			if(itemStack3.stackSize > 1) {
				String string6 = "" + itemStack3.stackSize;
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				fontRenderer1.drawStringWithShadow(string6, i4 + 19 - 2 - fontRenderer1.getStringWidth(string6), i5 + 6 + 3, 0xFFFFFF);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}

			if(itemStack3.isItemDamaged()) {
				int i11 = (int)Math.round(13.0D - (double)itemStack3.getItemDamageForDisplay() * 13.0D / (double)itemStack3.getMaxDamage());
				int i7 = (int)Math.round(255.0D - (double)itemStack3.getItemDamageForDisplay() * 255.0D / (double)itemStack3.getMaxDamage());
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				Tessellator tessellator8 = Tessellator.instance;
				int i9 = 255 - i7 << 16 | i7 << 8;
				int i10 = (255 - i7) / 4 << 16 | 16128;
				this.renderQuad(tessellator8, i4 + 2, i5 + 13, 13, 2, 0);
				this.renderQuad(tessellator8, i4 + 2, i5 + 13, 12, 1, i10);
				this.renderQuad(tessellator8, i4 + 2, i5 + 13, i11, 1, i9);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

		}
	}

	private void renderQuad(Tessellator tessellator1, int i2, int i3, int i4, int i5, int i6) {
		tessellator1.startDrawingQuads();
		tessellator1.setColorOpaque_I(i6);
		tessellator1.addVertex((double)(i2 + 0), (double)(i3 + 0), 0.0D);
		tessellator1.addVertex((double)(i2 + 0), (double)(i3 + i5), 0.0D);
		tessellator1.addVertex((double)(i2 + i4), (double)(i3 + i5), 0.0D);
		tessellator1.addVertex((double)(i2 + i4), (double)(i3 + 0), 0.0D);
		tessellator1.draw();
	}

	public void renderTexturedQuad(int i1, int i2, int i3, int i4, int i5, int i6) {
		float f7 = 0.0F;
		float f8 = 0.00390625F;
		float f9 = 0.00390625F;
		Tessellator tessellator10 = Tessellator.instance;
		tessellator10.startDrawingQuads();
		tessellator10.addVertexWithUV((double)(i1 + 0), (double)(i2 + i6), (double)f7, (double)((float)(i3 + 0) * f8), (double)((float)(i4 + i6) * f9));
		tessellator10.addVertexWithUV((double)(i1 + i5), (double)(i2 + i6), (double)f7, (double)((float)(i3 + i5) * f8), (double)((float)(i4 + i6) * f9));
		tessellator10.addVertexWithUV((double)(i1 + i5), (double)(i2 + 0), (double)f7, (double)((float)(i3 + i5) * f8), (double)((float)(i4 + 0) * f9));
		tessellator10.addVertexWithUV((double)(i1 + 0), (double)(i2 + 0), (double)f7, (double)((float)(i3 + 0) * f8), (double)((float)(i4 + 0) * f9));
		tessellator10.draw();
	}

	public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
		this.doRenderItem((EntityItem)entity1, d2, d4, d6, f8, f9);
	}
}
