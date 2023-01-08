package net.minecraft.src;

import java.util.List;

import org.lwjgl.opengl.GL11;

class GuiTexturePackSlot extends GuiSlot {
	final GuiTexturePacks parentTexturePackGui;

	public GuiTexturePackSlot(GuiTexturePacks guiTexturePacks1) {
		super(GuiTexturePacks.func_22124_a(guiTexturePacks1), guiTexturePacks1.width, guiTexturePacks1.height, 32, guiTexturePacks1.height - 55 + 4, 36);
		this.parentTexturePackGui = guiTexturePacks1;
	}

	protected int getSize() {
		List list1 = GuiTexturePacks.func_22126_b(this.parentTexturePackGui).texturePackList.availableTexturePacks();
		return list1.size();
	}

	protected void elementClicked(int i1, boolean z2) {
		List list3 = GuiTexturePacks.func_22119_c(this.parentTexturePackGui).texturePackList.availableTexturePacks();
		GuiTexturePacks.func_22122_d(this.parentTexturePackGui).texturePackList.setTexturePack((TexturePackBase)list3.get(i1));
		GuiTexturePacks.func_22117_e(this.parentTexturePackGui).renderEngine.refreshTextures();
	}

	protected boolean isSelected(int i1) {
		List list2 = GuiTexturePacks.func_22118_f(this.parentTexturePackGui).texturePackList.availableTexturePacks();
		return GuiTexturePacks.func_22116_g(this.parentTexturePackGui).texturePackList.selectedTexturePack == list2.get(i1);
	}

	protected int getContentHeight() {
		return this.getSize() * 36;
	}

	protected void drawBackground() {
		this.parentTexturePackGui.drawDefaultBackground();
	}

	protected void drawSlot(int i1, int i2, int i3, int i4, Tessellator tessellator5) {
		TexturePackBase texturePackBase6 = (TexturePackBase)GuiTexturePacks.func_22121_h(this.parentTexturePackGui).texturePackList.availableTexturePacks().get(i1);
		texturePackBase6.bindThumbnailTexture(GuiTexturePacks.func_22123_i(this.parentTexturePackGui));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator5.startDrawingQuads();
		tessellator5.setColorOpaque_I(0xFFFFFF);
		tessellator5.addVertexWithUV((double)i2, (double)(i3 + i4), 0.0D, 0.0D, 1.0D);
		tessellator5.addVertexWithUV((double)(i2 + 32), (double)(i3 + i4), 0.0D, 1.0D, 1.0D);
		tessellator5.addVertexWithUV((double)(i2 + 32), (double)i3, 0.0D, 1.0D, 0.0D);
		tessellator5.addVertexWithUV((double)i2, (double)i3, 0.0D, 0.0D, 0.0D);
		tessellator5.draw();
		this.parentTexturePackGui.drawString(GuiTexturePacks.func_22127_j(this.parentTexturePackGui), texturePackBase6.texturePackFileName, i2 + 32 + 2, i3 + 1, 0xFFFFFF);
		this.parentTexturePackGui.drawString(GuiTexturePacks.func_22120_k(this.parentTexturePackGui), texturePackBase6.firstDescriptionLine, i2 + 32 + 2, i3 + 12, 8421504);
		this.parentTexturePackGui.drawString(GuiTexturePacks.func_22125_l(this.parentTexturePackGui), texturePackBase6.secondDescriptionLine, i2 + 32 + 2, i3 + 12 + 10, 8421504);
	}
}
