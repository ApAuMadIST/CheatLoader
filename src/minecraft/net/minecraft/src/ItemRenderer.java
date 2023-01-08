package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ItemRenderer {
	private Minecraft mc;
	private ItemStack itemToRender = null;
	private float equippedProgress = 0.0F;
	private float prevEquippedProgress = 0.0F;
	private RenderBlocks renderBlocksInstance = new RenderBlocks();
	private MapItemRenderer field_28131_f;
	private int field_20099_f = -1;

	public ItemRenderer(Minecraft minecraft1) {
		this.mc = minecraft1;
		this.field_28131_f = new MapItemRenderer(minecraft1.fontRenderer, minecraft1.gameSettings, minecraft1.renderEngine);
	}

	public void renderItem(EntityLiving entityLiving1, ItemStack itemStack2) {
		GL11.glPushMatrix();
		if(itemStack2.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemStack2.itemID].getRenderType())) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			this.renderBlocksInstance.renderBlockOnInventory(Block.blocksList[itemStack2.itemID], itemStack2.getItemDamage(), entityLiving1.getEntityBrightness(1.0F));
		} else {
			if(itemStack2.itemID < 256) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			} else {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/items.png"));
			}

			Tessellator tessellator3 = Tessellator.instance;
			int i4 = entityLiving1.getItemIcon(itemStack2);
			float f5 = ((float)(i4 % 16 * 16) + 0.0F) / 256.0F;
			float f6 = ((float)(i4 % 16 * 16) + 15.99F) / 256.0F;
			float f7 = ((float)(i4 / 16 * 16) + 0.0F) / 256.0F;
			float f8 = ((float)(i4 / 16 * 16) + 15.99F) / 256.0F;
			float f9 = 1.0F;
			float f10 = 0.0F;
			float f11 = 0.3F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslatef(-f10, -f11, 0.0F);
			float f12 = 1.5F;
			GL11.glScalef(f12, f12, f12);
			GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
			float f13 = 0.0625F;
			tessellator3.startDrawingQuads();
			tessellator3.setNormal(0.0F, 0.0F, 1.0F);
			tessellator3.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)f6, (double)f8);
			tessellator3.addVertexWithUV((double)f9, 0.0D, 0.0D, (double)f5, (double)f8);
			tessellator3.addVertexWithUV((double)f9, 1.0D, 0.0D, (double)f5, (double)f7);
			tessellator3.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)f6, (double)f7);
			tessellator3.draw();
			tessellator3.startDrawingQuads();
			tessellator3.setNormal(0.0F, 0.0F, -1.0F);
			tessellator3.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - f13), (double)f6, (double)f7);
			tessellator3.addVertexWithUV((double)f9, 1.0D, (double)(0.0F - f13), (double)f5, (double)f7);
			tessellator3.addVertexWithUV((double)f9, 0.0D, (double)(0.0F - f13), (double)f5, (double)f8);
			tessellator3.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - f13), (double)f6, (double)f8);
			tessellator3.draw();
			tessellator3.startDrawingQuads();
			tessellator3.setNormal(-1.0F, 0.0F, 0.0F);

			int i14;
			float f15;
			float f16;
			float f17;
			for(i14 = 0; i14 < 16; ++i14) {
				f15 = (float)i14 / 16.0F;
				f16 = f6 + (f5 - f6) * f15 - 0.001953125F;
				f17 = f9 * f15;
				tessellator3.addVertexWithUV((double)f17, 0.0D, (double)(0.0F - f13), (double)f16, (double)f8);
				tessellator3.addVertexWithUV((double)f17, 0.0D, 0.0D, (double)f16, (double)f8);
				tessellator3.addVertexWithUV((double)f17, 1.0D, 0.0D, (double)f16, (double)f7);
				tessellator3.addVertexWithUV((double)f17, 1.0D, (double)(0.0F - f13), (double)f16, (double)f7);
			}

			tessellator3.draw();
			tessellator3.startDrawingQuads();
			tessellator3.setNormal(1.0F, 0.0F, 0.0F);

			for(i14 = 0; i14 < 16; ++i14) {
				f15 = (float)i14 / 16.0F;
				f16 = f6 + (f5 - f6) * f15 - 0.001953125F;
				f17 = f9 * f15 + 0.0625F;
				tessellator3.addVertexWithUV((double)f17, 1.0D, (double)(0.0F - f13), (double)f16, (double)f7);
				tessellator3.addVertexWithUV((double)f17, 1.0D, 0.0D, (double)f16, (double)f7);
				tessellator3.addVertexWithUV((double)f17, 0.0D, 0.0D, (double)f16, (double)f8);
				tessellator3.addVertexWithUV((double)f17, 0.0D, (double)(0.0F - f13), (double)f16, (double)f8);
			}

			tessellator3.draw();
			tessellator3.startDrawingQuads();
			tessellator3.setNormal(0.0F, 1.0F, 0.0F);

			for(i14 = 0; i14 < 16; ++i14) {
				f15 = (float)i14 / 16.0F;
				f16 = f8 + (f7 - f8) * f15 - 0.001953125F;
				f17 = f9 * f15 + 0.0625F;
				tessellator3.addVertexWithUV(0.0D, (double)f17, 0.0D, (double)f6, (double)f16);
				tessellator3.addVertexWithUV((double)f9, (double)f17, 0.0D, (double)f5, (double)f16);
				tessellator3.addVertexWithUV((double)f9, (double)f17, (double)(0.0F - f13), (double)f5, (double)f16);
				tessellator3.addVertexWithUV(0.0D, (double)f17, (double)(0.0F - f13), (double)f6, (double)f16);
			}

			tessellator3.draw();
			tessellator3.startDrawingQuads();
			tessellator3.setNormal(0.0F, -1.0F, 0.0F);

			for(i14 = 0; i14 < 16; ++i14) {
				f15 = (float)i14 / 16.0F;
				f16 = f8 + (f7 - f8) * f15 - 0.001953125F;
				f17 = f9 * f15;
				tessellator3.addVertexWithUV((double)f9, (double)f17, 0.0D, (double)f5, (double)f16);
				tessellator3.addVertexWithUV(0.0D, (double)f17, 0.0D, (double)f6, (double)f16);
				tessellator3.addVertexWithUV(0.0D, (double)f17, (double)(0.0F - f13), (double)f6, (double)f16);
				tessellator3.addVertexWithUV((double)f9, (double)f17, (double)(0.0F - f13), (double)f5, (double)f16);
			}

			tessellator3.draw();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		}

		GL11.glPopMatrix();
	}

	public void renderItemInFirstPerson(float f1) {
		float f2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * f1;
		EntityPlayerSP entityPlayerSP3 = this.mc.thePlayer;
		float f4 = entityPlayerSP3.prevRotationPitch + (entityPlayerSP3.rotationPitch - entityPlayerSP3.prevRotationPitch) * f1;
		GL11.glPushMatrix();
		GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(entityPlayerSP3.prevRotationYaw + (entityPlayerSP3.rotationYaw - entityPlayerSP3.prevRotationYaw) * f1, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		ItemStack itemStack5 = this.itemToRender;
		float f6 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(entityPlayerSP3.posX), MathHelper.floor_double(entityPlayerSP3.posY), MathHelper.floor_double(entityPlayerSP3.posZ));
		float f8;
		float f9;
		float f10;
		if(itemStack5 != null) {
			int i7 = Item.itemsList[itemStack5.itemID].getColorFromDamage(itemStack5.getItemDamage());
			f8 = (float)(i7 >> 16 & 255) / 255.0F;
			f9 = (float)(i7 >> 8 & 255) / 255.0F;
			f10 = (float)(i7 & 255) / 255.0F;
			GL11.glColor4f(f6 * f8, f6 * f9, f6 * f10, 1.0F);
		} else {
			GL11.glColor4f(f6, f6, f6, 1.0F);
		}

		float f14;
		if(itemStack5 != null && itemStack5.itemID == Item.mapItem.shiftedIndex) {
			GL11.glPushMatrix();
			f14 = 0.8F;
			f8 = entityPlayerSP3.getSwingProgress(f1);
			f9 = MathHelper.sin(f8 * (float)Math.PI);
			f10 = MathHelper.sin(MathHelper.sqrt_float(f8) * (float)Math.PI);
			GL11.glTranslatef(-f10 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(f8) * (float)Math.PI * 2.0F) * 0.2F, -f9 * 0.2F);
			f8 = 1.0F - f4 / 45.0F + 0.1F;
			if(f8 < 0.0F) {
				f8 = 0.0F;
			}

			if(f8 > 1.0F) {
				f8 = 1.0F;
			}

			f8 = -MathHelper.cos(f8 * (float)Math.PI) * 0.5F + 0.5F;
			GL11.glTranslatef(0.0F, 0.0F * f14 - (1.0F - f2) * 1.2F - f8 * 0.5F + 0.04F, -0.9F * f14);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(f8 * -85.0F, 0.0F, 0.0F, 1.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTextureForDownloadableImage(this.mc.thePlayer.skinUrl, this.mc.thePlayer.getEntityTexture()));

			for(int i17 = 0; i17 < 2; ++i17) {
				int i21 = i17 * 2 - 1;
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.0F, -0.6F, 1.1F * (float)i21);
				GL11.glRotatef((float)(-45 * i21), 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef((float)(-65 * i21), 0.0F, 1.0F, 0.0F);
				Render render11 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
				RenderPlayer renderPlayer12 = (RenderPlayer)render11;
				float f13 = 1.0F;
				GL11.glScalef(f13, f13, f13);
				renderPlayer12.drawFirstPersonHand();
				GL11.glPopMatrix();
			}

			f9 = entityPlayerSP3.getSwingProgress(f1);
			f10 = MathHelper.sin(f9 * f9 * (float)Math.PI);
			float f18 = MathHelper.sin(MathHelper.sqrt_float(f9) * (float)Math.PI);
			GL11.glRotatef(-f10 * 20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-f18 * 20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-f18 * 80.0F, 1.0F, 0.0F, 0.0F);
			f9 = 0.38F;
			GL11.glScalef(f9, f9, f9);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
			f10 = 0.015625F;
			GL11.glScalef(f10, f10, f10);
			this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/misc/mapbg.png"));
			Tessellator tessellator19 = Tessellator.instance;
			GL11.glNormal3f(0.0F, 0.0F, -1.0F);
			tessellator19.startDrawingQuads();
			byte b20 = 7;
			tessellator19.addVertexWithUV((double)(0 - b20), (double)(128 + b20), 0.0D, 0.0D, 1.0D);
			tessellator19.addVertexWithUV((double)(128 + b20), (double)(128 + b20), 0.0D, 1.0D, 1.0D);
			tessellator19.addVertexWithUV((double)(128 + b20), (double)(0 - b20), 0.0D, 1.0D, 0.0D);
			tessellator19.addVertexWithUV((double)(0 - b20), (double)(0 - b20), 0.0D, 0.0D, 0.0D);
			tessellator19.draw();
			MapData mapData22 = Item.mapItem.func_28012_a(itemStack5, this.mc.theWorld);
			this.field_28131_f.func_28157_a(this.mc.thePlayer, this.mc.renderEngine, mapData22);
			GL11.glPopMatrix();
		} else if(itemStack5 != null) {
			GL11.glPushMatrix();
			f14 = 0.8F;
			f8 = entityPlayerSP3.getSwingProgress(f1);
			f9 = MathHelper.sin(f8 * (float)Math.PI);
			f10 = MathHelper.sin(MathHelper.sqrt_float(f8) * (float)Math.PI);
			GL11.glTranslatef(-f10 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(f8) * (float)Math.PI * 2.0F) * 0.2F, -f9 * 0.2F);
			GL11.glTranslatef(0.7F * f14, -0.65F * f14 - (1.0F - f2) * 0.6F, -0.9F * f14);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			f8 = entityPlayerSP3.getSwingProgress(f1);
			f9 = MathHelper.sin(f8 * f8 * (float)Math.PI);
			f10 = MathHelper.sin(MathHelper.sqrt_float(f8) * (float)Math.PI);
			GL11.glRotatef(-f9 * 20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-f10 * 20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-f10 * 80.0F, 1.0F, 0.0F, 0.0F);
			f8 = 0.4F;
			GL11.glScalef(f8, f8, f8);
			if(itemStack5.getItem().shouldRotateAroundWhenRendering()) {
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			}

			this.renderItem(entityPlayerSP3, itemStack5);
			GL11.glPopMatrix();
		} else {
			GL11.glPushMatrix();
			f14 = 0.8F;
			f8 = entityPlayerSP3.getSwingProgress(f1);
			f9 = MathHelper.sin(f8 * (float)Math.PI);
			f10 = MathHelper.sin(MathHelper.sqrt_float(f8) * (float)Math.PI);
			GL11.glTranslatef(-f10 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(f8) * (float)Math.PI * 2.0F) * 0.4F, -f9 * 0.4F);
			GL11.glTranslatef(0.8F * f14, -0.75F * f14 - (1.0F - f2) * 0.6F, -0.9F * f14);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			f8 = entityPlayerSP3.getSwingProgress(f1);
			f9 = MathHelper.sin(f8 * f8 * (float)Math.PI);
			f10 = MathHelper.sin(MathHelper.sqrt_float(f8) * (float)Math.PI);
			GL11.glRotatef(f10 * 70.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-f9 * 20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTextureForDownloadableImage(this.mc.thePlayer.skinUrl, this.mc.thePlayer.getEntityTexture()));
			GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
			GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(5.6F, 0.0F, 0.0F);
			Render render15 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
			RenderPlayer renderPlayer16 = (RenderPlayer)render15;
			f10 = 1.0F;
			GL11.glScalef(f10, f10, f10);
			renderPlayer16.drawFirstPersonHand();
			GL11.glPopMatrix();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
	}

	public void renderOverlays(float f1) {
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		int i2;
		if(this.mc.thePlayer.isBurning()) {
			i2 = this.mc.renderEngine.getTexture("/terrain.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, i2);
			this.renderFireInFirstPerson(f1);
		}

		if(this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
			i2 = MathHelper.floor_double(this.mc.thePlayer.posX);
			int i3 = MathHelper.floor_double(this.mc.thePlayer.posY);
			int i4 = MathHelper.floor_double(this.mc.thePlayer.posZ);
			int i5 = this.mc.renderEngine.getTexture("/terrain.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, i5);
			int i6 = this.mc.theWorld.getBlockId(i2, i3, i4);
			if(this.mc.theWorld.isBlockNormalCube(i2, i3, i4)) {
				this.renderInsideOfBlock(f1, Block.blocksList[i6].getBlockTextureFromSide(2));
			} else {
				for(int i7 = 0; i7 < 8; ++i7) {
					float f8 = ((float)((i7 >> 0) % 2) - 0.5F) * this.mc.thePlayer.width * 0.9F;
					float f9 = ((float)((i7 >> 1) % 2) - 0.5F) * this.mc.thePlayer.height * 0.2F;
					float f10 = ((float)((i7 >> 2) % 2) - 0.5F) * this.mc.thePlayer.width * 0.9F;
					int i11 = MathHelper.floor_float((float)i2 + f8);
					int i12 = MathHelper.floor_float((float)i3 + f9);
					int i13 = MathHelper.floor_float((float)i4 + f10);
					if(this.mc.theWorld.isBlockNormalCube(i11, i12, i13)) {
						i6 = this.mc.theWorld.getBlockId(i11, i12, i13);
					}
				}
			}

			if(Block.blocksList[i6] != null) {
				this.renderInsideOfBlock(f1, Block.blocksList[i6].getBlockTextureFromSide(2));
			}
		}

		if(this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
			i2 = this.mc.renderEngine.getTexture("/misc/water.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, i2);
			this.renderWarpedTextureOverlay(f1);
		}

		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}

	private void renderInsideOfBlock(float f1, int i2) {
		Tessellator tessellator3 = Tessellator.instance;
		this.mc.thePlayer.getEntityBrightness(f1);
		float f4 = 0.1F;
		GL11.glColor4f(f4, f4, f4, 0.5F);
		GL11.glPushMatrix();
		float f5 = -1.0F;
		float f6 = 1.0F;
		float f7 = -1.0F;
		float f8 = 1.0F;
		float f9 = -0.5F;
		float f10 = 0.0078125F;
		float f11 = (float)(i2 % 16) / 256.0F - f10;
		float f12 = ((float)(i2 % 16) + 15.99F) / 256.0F + f10;
		float f13 = (float)(i2 / 16) / 256.0F - f10;
		float f14 = ((float)(i2 / 16) + 15.99F) / 256.0F + f10;
		tessellator3.startDrawingQuads();
		tessellator3.addVertexWithUV((double)f5, (double)f7, (double)f9, (double)f12, (double)f14);
		tessellator3.addVertexWithUV((double)f6, (double)f7, (double)f9, (double)f11, (double)f14);
		tessellator3.addVertexWithUV((double)f6, (double)f8, (double)f9, (double)f11, (double)f13);
		tessellator3.addVertexWithUV((double)f5, (double)f8, (double)f9, (double)f12, (double)f13);
		tessellator3.draw();
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void renderWarpedTextureOverlay(float f1) {
		Tessellator tessellator2 = Tessellator.instance;
		float f3 = this.mc.thePlayer.getEntityBrightness(f1);
		GL11.glColor4f(f3, f3, f3, 0.5F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glPushMatrix();
		float f4 = 4.0F;
		float f5 = -1.0F;
		float f6 = 1.0F;
		float f7 = -1.0F;
		float f8 = 1.0F;
		float f9 = -0.5F;
		float f10 = -this.mc.thePlayer.rotationYaw / 64.0F;
		float f11 = this.mc.thePlayer.rotationPitch / 64.0F;
		tessellator2.startDrawingQuads();
		tessellator2.addVertexWithUV((double)f5, (double)f7, (double)f9, (double)(f4 + f10), (double)(f4 + f11));
		tessellator2.addVertexWithUV((double)f6, (double)f7, (double)f9, (double)(0.0F + f10), (double)(f4 + f11));
		tessellator2.addVertexWithUV((double)f6, (double)f8, (double)f9, (double)(0.0F + f10), (double)(0.0F + f11));
		tessellator2.addVertexWithUV((double)f5, (double)f8, (double)f9, (double)(f4 + f10), (double)(0.0F + f11));
		tessellator2.draw();
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private void renderFireInFirstPerson(float f1) {
		Tessellator tessellator2 = Tessellator.instance;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		float f3 = 1.0F;

		for(int i4 = 0; i4 < 2; ++i4) {
			GL11.glPushMatrix();
			int i5 = Block.fire.blockIndexInTexture + i4 * 16;
			int i6 = (i5 & 15) << 4;
			int i7 = i5 & 240;
			float f8 = (float)i6 / 256.0F;
			float f9 = ((float)i6 + 15.99F) / 256.0F;
			float f10 = (float)i7 / 256.0F;
			float f11 = ((float)i7 + 15.99F) / 256.0F;
			float f12 = (0.0F - f3) / 2.0F;
			float f13 = f12 + f3;
			float f14 = 0.0F - f3 / 2.0F;
			float f15 = f14 + f3;
			float f16 = -0.5F;
			GL11.glTranslatef((float)(-(i4 * 2 - 1)) * 0.24F, -0.3F, 0.0F);
			GL11.glRotatef((float)(i4 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
			tessellator2.startDrawingQuads();
			tessellator2.addVertexWithUV((double)f12, (double)f14, (double)f16, (double)f9, (double)f11);
			tessellator2.addVertexWithUV((double)f13, (double)f14, (double)f16, (double)f8, (double)f11);
			tessellator2.addVertexWithUV((double)f13, (double)f15, (double)f16, (double)f8, (double)f10);
			tessellator2.addVertexWithUV((double)f12, (double)f15, (double)f16, (double)f9, (double)f10);
			tessellator2.draw();
			GL11.glPopMatrix();
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void updateEquippedItem() {
		this.prevEquippedProgress = this.equippedProgress;
		EntityPlayerSP entityPlayerSP1 = this.mc.thePlayer;
		ItemStack itemStack2 = entityPlayerSP1.inventory.getCurrentItem();
		boolean z4 = this.field_20099_f == entityPlayerSP1.inventory.currentItem && itemStack2 == this.itemToRender;
		if(this.itemToRender == null && itemStack2 == null) {
			z4 = true;
		}

		if(itemStack2 != null && this.itemToRender != null && itemStack2 != this.itemToRender && itemStack2.itemID == this.itemToRender.itemID && itemStack2.getItemDamage() == this.itemToRender.getItemDamage()) {
			this.itemToRender = itemStack2;
			z4 = true;
		}

		float f5 = 0.4F;
		float f6 = z4 ? 1.0F : 0.0F;
		float f7 = f6 - this.equippedProgress;
		if(f7 < -f5) {
			f7 = -f5;
		}

		if(f7 > f5) {
			f7 = f5;
		}

		this.equippedProgress += f7;
		if(this.equippedProgress < 0.1F) {
			this.itemToRender = itemStack2;
			this.field_20099_f = entityPlayerSP1.inventory.currentItem;
		}

	}

	public void func_9449_b() {
		this.equippedProgress = 0.0F;
	}

	public void func_9450_c() {
		this.equippedProgress = 0.0F;
	}
}
