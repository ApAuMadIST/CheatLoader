package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class RenderPlayer extends RenderLiving {
	private ModelBiped modelBipedMain = (ModelBiped)this.mainModel;
	private ModelBiped modelArmorChestplate = new ModelBiped(1.0F);
	private ModelBiped modelArmor = new ModelBiped(0.5F);
	private static final String[] armorFilenamePrefix = new String[]{"cloth", "chain", "iron", "diamond", "gold"};

	public RenderPlayer() {
		super(new ModelBiped(0.0F), 0.5F);
	}

	protected boolean setArmorModel(EntityPlayer entityPlayer1, int i2, float f3) {
		ItemStack itemStack4 = entityPlayer1.inventory.armorItemInSlot(3 - i2);
		if(itemStack4 != null) {
			Item item5 = itemStack4.getItem();
			if(item5 instanceof ItemArmor) {
				ItemArmor itemArmor6 = (ItemArmor)item5;
				this.loadTexture("/armor/" + armorFilenamePrefix[itemArmor6.renderIndex] + "_" + (i2 == 2 ? 2 : 1) + ".png");
				ModelBiped modelBiped7 = i2 == 2 ? this.modelArmor : this.modelArmorChestplate;
				modelBiped7.bipedHead.showModel = i2 == 0;
				modelBiped7.bipedHeadwear.showModel = i2 == 0;
				modelBiped7.bipedBody.showModel = i2 == 1 || i2 == 2;
				modelBiped7.bipedRightArm.showModel = i2 == 1;
				modelBiped7.bipedLeftArm.showModel = i2 == 1;
				modelBiped7.bipedRightLeg.showModel = i2 == 2 || i2 == 3;
				modelBiped7.bipedLeftLeg.showModel = i2 == 2 || i2 == 3;
				this.setRenderPassModel(modelBiped7);
				return true;
			}
		}

		return false;
	}

	public void renderPlayer(EntityPlayer entityPlayer1, double d2, double d4, double d6, float f8, float f9) {
		ItemStack itemStack10 = entityPlayer1.inventory.getCurrentItem();
		this.modelArmorChestplate.field_1278_i = this.modelArmor.field_1278_i = this.modelBipedMain.field_1278_i = itemStack10 != null;
		this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = entityPlayer1.isSneaking();
		double d11 = d4 - (double)entityPlayer1.yOffset;
		if(entityPlayer1.isSneaking() && !(entityPlayer1 instanceof EntityPlayerSP)) {
			d11 -= 0.125D;
		}

		super.doRenderLiving(entityPlayer1, d2, d11, d6, f8, f9);
		this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
		this.modelArmorChestplate.field_1278_i = this.modelArmor.field_1278_i = this.modelBipedMain.field_1278_i = false;
	}

	protected void renderName(EntityPlayer entityPlayer1, double d2, double d4, double d6) {
		if(Minecraft.isGuiEnabled() && entityPlayer1 != this.renderManager.livingPlayer) {
			float f8 = 1.6F;
			float f9 = 0.016666668F * f8;
			float f10 = entityPlayer1.getDistanceToEntity(this.renderManager.livingPlayer);
			float f11 = entityPlayer1.isSneaking() ? 32.0F : 64.0F;
			if(f10 < f11) {
				String string12 = entityPlayer1.username;
				if(!entityPlayer1.isSneaking()) {
					if(entityPlayer1.isPlayerSleeping()) {
						this.renderLivingLabel(entityPlayer1, string12, d2, d4 - 1.5D, d6, 64);
					} else {
						this.renderLivingLabel(entityPlayer1, string12, d2, d4, d6, 64);
					}
				} else {
					FontRenderer fontRenderer13 = this.getFontRendererFromRenderManager();
					GL11.glPushMatrix();
					GL11.glTranslatef((float)d2 + 0.0F, (float)d4 + 2.3F, (float)d6);
					GL11.glNormal3f(0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
					GL11.glScalef(-f9, -f9, f9);
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glTranslatef(0.0F, 0.25F / f9, 0.0F);
					GL11.glDepthMask(false);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					Tessellator tessellator14 = Tessellator.instance;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tessellator14.startDrawingQuads();
					int i15 = fontRenderer13.getStringWidth(string12) / 2;
					tessellator14.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
					tessellator14.addVertex((double)(-i15 - 1), -1.0D, 0.0D);
					tessellator14.addVertex((double)(-i15 - 1), 8.0D, 0.0D);
					tessellator14.addVertex((double)(i15 + 1), 8.0D, 0.0D);
					tessellator14.addVertex((double)(i15 + 1), -1.0D, 0.0D);
					tessellator14.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDepthMask(true);
					fontRenderer13.drawString(string12, -fontRenderer13.getStringWidth(string12) / 2, 0, 553648127);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glPopMatrix();
				}
			}
		}

	}

	protected void renderSpecials(EntityPlayer entityPlayer1, float f2) {
		ItemStack itemStack3 = entityPlayer1.inventory.armorItemInSlot(3);
		if(itemStack3 != null && itemStack3.getItem().shiftedIndex < 256) {
			GL11.glPushMatrix();
			this.modelBipedMain.bipedHead.postRender(0.0625F);
			if(RenderBlocks.renderItemIn3d(Block.blocksList[itemStack3.itemID].getRenderType())) {
				float f4 = 0.625F;
				GL11.glTranslatef(0.0F, -0.25F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f4, -f4, f4);
			}

			this.renderManager.itemRenderer.renderItem(entityPlayer1, itemStack3);
			GL11.glPopMatrix();
		}

		float f5;
		if(entityPlayer1.username.equals("deadmau5") && this.loadDownloadableImageTexture(entityPlayer1.skinUrl, (String)null)) {
			for(int i19 = 0; i19 < 2; ++i19) {
				f5 = entityPlayer1.prevRotationYaw + (entityPlayer1.rotationYaw - entityPlayer1.prevRotationYaw) * f2 - (entityPlayer1.prevRenderYawOffset + (entityPlayer1.renderYawOffset - entityPlayer1.prevRenderYawOffset) * f2);
				float f6 = entityPlayer1.prevRotationPitch + (entityPlayer1.rotationPitch - entityPlayer1.prevRotationPitch) * f2;
				GL11.glPushMatrix();
				GL11.glRotatef(f5, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(f6, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.375F * (float)(i19 * 2 - 1), 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.375F, 0.0F);
				GL11.glRotatef(-f6, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-f5, 0.0F, 1.0F, 0.0F);
				float f7 = 1.3333334F;
				GL11.glScalef(f7, f7, f7);
				this.modelBipedMain.renderEars(0.0625F);
				GL11.glPopMatrix();
			}
		}

		if(this.loadDownloadableImageTexture(entityPlayer1.playerCloakUrl, (String)null)) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.0F, 0.125F);
			double d20 = entityPlayer1.field_20066_r + (entityPlayer1.field_20063_u - entityPlayer1.field_20066_r) * (double)f2 - (entityPlayer1.prevPosX + (entityPlayer1.posX - entityPlayer1.prevPosX) * (double)f2);
			double d22 = entityPlayer1.field_20065_s + (entityPlayer1.field_20062_v - entityPlayer1.field_20065_s) * (double)f2 - (entityPlayer1.prevPosY + (entityPlayer1.posY - entityPlayer1.prevPosY) * (double)f2);
			double d8 = entityPlayer1.field_20064_t + (entityPlayer1.field_20061_w - entityPlayer1.field_20064_t) * (double)f2 - (entityPlayer1.prevPosZ + (entityPlayer1.posZ - entityPlayer1.prevPosZ) * (double)f2);
			float f10 = entityPlayer1.prevRenderYawOffset + (entityPlayer1.renderYawOffset - entityPlayer1.prevRenderYawOffset) * f2;
			double d11 = (double)MathHelper.sin(f10 * (float)Math.PI / 180.0F);
			double d13 = (double)(-MathHelper.cos(f10 * (float)Math.PI / 180.0F));
			float f15 = (float)d22 * 10.0F;
			if(f15 < -6.0F) {
				f15 = -6.0F;
			}

			if(f15 > 32.0F) {
				f15 = 32.0F;
			}

			float f16 = (float)(d20 * d11 + d8 * d13) * 100.0F;
			float f17 = (float)(d20 * d13 - d8 * d11) * 100.0F;
			if(f16 < 0.0F) {
				f16 = 0.0F;
			}

			float f18 = entityPlayer1.field_775_e + (entityPlayer1.field_774_f - entityPlayer1.field_775_e) * f2;
			f15 += MathHelper.sin((entityPlayer1.prevDistanceWalkedModified + (entityPlayer1.distanceWalkedModified - entityPlayer1.prevDistanceWalkedModified) * f2) * 6.0F) * 32.0F * f18;
			if(entityPlayer1.isSneaking()) {
				f15 += 25.0F;
			}

			GL11.glRotatef(6.0F + f16 / 2.0F + f15, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(f17 / 2.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-f17 / 2.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			this.modelBipedMain.renderCloak(0.0625F);
			GL11.glPopMatrix();
		}

		ItemStack itemStack21 = entityPlayer1.inventory.getCurrentItem();
		if(itemStack21 != null) {
			GL11.glPushMatrix();
			this.modelBipedMain.bipedRightArm.postRender(0.0625F);
			GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
			if(entityPlayer1.fishEntity != null) {
				itemStack21 = new ItemStack(Item.stick);
			}

			if(itemStack21.itemID < 256 && RenderBlocks.renderItemIn3d(Block.blocksList[itemStack21.itemID].getRenderType())) {
				f5 = 0.5F;
				GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
				f5 *= 0.75F;
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(f5, -f5, f5);
			} else if(Item.itemsList[itemStack21.itemID].isFull3D()) {
				f5 = 0.625F;
				if(Item.itemsList[itemStack21.itemID].shouldRotateAroundWhenRendering()) {
					GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, -0.125F, 0.0F);
				}

				GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
				GL11.glScalef(f5, -f5, f5);
				GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			} else {
				f5 = 0.375F;
				GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
				GL11.glScalef(f5, f5, f5);
				GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
			}

			this.renderManager.itemRenderer.renderItem(entityPlayer1, itemStack21);
			GL11.glPopMatrix();
		}

	}

	protected void func_186_b(EntityPlayer entityPlayer1, float f2) {
		float f3 = 0.9375F;
		GL11.glScalef(f3, f3, f3);
	}

	public void drawFirstPersonHand() {
		this.modelBipedMain.onGround = 0.0F;
		this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		this.modelBipedMain.bipedRightArm.render(0.0625F);
	}

	protected void func_22016_b(EntityPlayer entityPlayer1, double d2, double d4, double d6) {
		if(entityPlayer1.isEntityAlive() && entityPlayer1.isPlayerSleeping()) {
			super.func_22012_b(entityPlayer1, d2 + (double)entityPlayer1.field_22063_x, d4 + (double)entityPlayer1.field_22062_y, d6 + (double)entityPlayer1.field_22061_z);
		} else {
			super.func_22012_b(entityPlayer1, d2, d4, d6);
		}

	}

	protected void func_22017_a(EntityPlayer entityPlayer1, float f2, float f3, float f4) {
		if(entityPlayer1.isEntityAlive() && entityPlayer1.isPlayerSleeping()) {
			GL11.glRotatef(entityPlayer1.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(this.getDeathMaxRotation(entityPlayer1), 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
		} else {
			super.rotateCorpse(entityPlayer1, f2, f3, f4);
		}

	}

	protected void passSpecialRender(EntityLiving entityLiving1, double d2, double d4, double d6) {
		this.renderName((EntityPlayer)entityLiving1, d2, d4, d6);
	}

	protected void preRenderCallback(EntityLiving entityLiving1, float f2) {
		this.func_186_b((EntityPlayer)entityLiving1, f2);
	}

	protected boolean shouldRenderPass(EntityLiving entityLiving1, int i2, float f3) {
		return this.setArmorModel((EntityPlayer)entityLiving1, i2, f3);
	}

	protected void renderEquippedItems(EntityLiving entityLiving1, float f2) {
		this.renderSpecials((EntityPlayer)entityLiving1, f2);
	}

	protected void rotateCorpse(EntityLiving entityLiving1, float f2, float f3, float f4) {
		this.func_22017_a((EntityPlayer)entityLiving1, f2, f3, f4);
	}

	protected void func_22012_b(EntityLiving entityLiving1, double d2, double d4, double d6) {
		this.func_22016_b((EntityPlayer)entityLiving1, d2, d4, d6);
	}

	public void doRenderLiving(EntityLiving entityLiving1, double d2, double d4, double d6, float f8, float f9) {
		this.renderPlayer((EntityPlayer)entityLiving1, d2, d4, d6, f8, f9);
	}

	public void doRender(Entity entity1, double d2, double d4, double d6, float f8, float f9) {
		this.renderPlayer((EntityPlayer)entity1, d2, d4, d6, f8, f9);
	}
}
