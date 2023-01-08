package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiInventory extends GuiContainer {
	private float xSize_lo;
	private float ySize_lo;

	public GuiInventory(EntityPlayer entityPlayer1) {
		super(entityPlayer1.inventorySlots);
		this.field_948_f = true;
		entityPlayer1.addStat(AchievementList.openInventory, 1);
	}

	public void initGui() {
		this.controlList.clear();
	}

	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString("Crafting", 86, 16, 4210752);
	}

	public void drawScreen(int i1, int i2, float f3) {
		super.drawScreen(i1, i2, f3);
		this.xSize_lo = (float)i1;
		this.ySize_lo = (float)i2;
	}

	protected void drawGuiContainerBackgroundLayer(float f1) {
		int i2 = this.mc.renderEngine.getTexture("/gui/inventory.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(i2);
		int i3 = (this.width - this.xSize) / 2;
		int i4 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i3, i4, 0, 0, this.xSize, this.ySize);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(i3 + 51), (float)(i4 + 75), 50.0F);
		float f5 = 30.0F;
		GL11.glScalef(-f5, f5, f5);
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		float f6 = this.mc.thePlayer.renderYawOffset;
		float f7 = this.mc.thePlayer.rotationYaw;
		float f8 = this.mc.thePlayer.rotationPitch;
		float f9 = (float)(i3 + 51) - this.xSize_lo;
		float f10 = (float)(i4 + 75 - 50) - this.ySize_lo;
		GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-((float)Math.atan((double)(f10 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		this.mc.thePlayer.renderYawOffset = (float)Math.atan((double)(f9 / 40.0F)) * 20.0F;
		this.mc.thePlayer.rotationYaw = (float)Math.atan((double)(f9 / 40.0F)) * 40.0F;
		this.mc.thePlayer.rotationPitch = -((float)Math.atan((double)(f10 / 40.0F))) * 20.0F;
		this.mc.thePlayer.entityBrightness = 1.0F;
		GL11.glTranslatef(0.0F, this.mc.thePlayer.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180.0F;
		RenderManager.instance.renderEntityWithPosYaw(this.mc.thePlayer, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		this.mc.thePlayer.entityBrightness = 0.0F;
		this.mc.thePlayer.renderYawOffset = f6;
		this.mc.thePlayer.rotationYaw = f7;
		this.mc.thePlayer.rotationPitch = f8;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.id == 0) {
			this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
		}

		if(guiButton1.id == 1) {
			this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
		}

	}
}
