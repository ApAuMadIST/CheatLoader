package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class GuiChest extends GuiContainer {
	private IInventory upperChestInventory;
	private IInventory lowerChestInventory;
	private int inventoryRows = 0;

	public GuiChest(IInventory iInventory1, IInventory iInventory2) {
		super(new ContainerChest(iInventory1, iInventory2));
		this.upperChestInventory = iInventory1;
		this.lowerChestInventory = iInventory2;
		this.field_948_f = false;
		short s3 = 222;
		int i4 = s3 - 108;
		this.inventoryRows = iInventory2.getSizeInventory() / 9;
		this.ySize = i4 + this.inventoryRows * 18;
	}

	protected void drawGuiContainerForegroundLayer() {
		this.fontRenderer.drawString(this.lowerChestInventory.getInvName(), 8, 6, 4210752);
		this.fontRenderer.drawString(this.upperChestInventory.getInvName(), 8, this.ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float f1) {
		int i2 = this.mc.renderEngine.getTexture("/gui/container.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(i2);
		int i3 = (this.width - this.xSize) / 2;
		int i4 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i3, i4, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
		this.drawTexturedModalRect(i3, i4 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
	}
}
