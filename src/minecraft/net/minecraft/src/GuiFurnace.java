package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class GuiFurnace extends GuiContainer {
	private TileEntityFurnace furnaceInventory;

	public GuiFurnace(InventoryPlayer inventoryPlayer1, TileEntityFurnace tileEntityFurnace2) {
		super(new ContainerFurnace(inventoryPlayer1, tileEntityFurnace2));
		this.furnaceInventory = tileEntityFurnace2;
	}

	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString("Furnace", 60, 6, 4210752);
		this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float f1) {
		int i2 = this.mc.renderEngine.getTexture("/gui/furnace.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(i2);
		int i3 = (this.width - this.xSize) / 2;
		int i4 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i3, i4, 0, 0, this.xSize, this.ySize);
		int i5;
		if(this.furnaceInventory.isBurning()) {
			i5 = this.furnaceInventory.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(i3 + 56, i4 + 36 + 12 - i5, 176, 12 - i5, 14, i5 + 2);
		}

		i5 = this.furnaceInventory.getCookProgressScaled(24);
		this.drawTexturedModalRect(i3 + 79, i4 + 34, 176, 14, i5 + 1, 16);
	}
}
