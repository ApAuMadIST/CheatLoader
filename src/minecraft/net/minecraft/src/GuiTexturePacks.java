package net.minecraft.src;

import java.io.File;

import net.minecraft.client.Minecraft;

import org.lwjgl.Sys;

public class GuiTexturePacks extends GuiScreen {
	protected GuiScreen guiScreen;
	private int field_6454_o = -1;
	private String fileLocation = "";
	private GuiTexturePackSlot guiTexturePackSlot;

	public GuiTexturePacks(GuiScreen guiScreen1) {
		this.guiScreen = guiScreen1;
	}

	public void initGui() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		this.controlList.add(new GuiSmallButton(5, this.width / 2 - 154, this.height - 48, stringTranslate1.translateKey("texturePack.openFolder")));
		this.controlList.add(new GuiSmallButton(6, this.width / 2 + 4, this.height - 48, stringTranslate1.translateKey("gui.done")));
		this.mc.texturePackList.updateAvaliableTexturePacks();
		this.fileLocation = (new File(Minecraft.getMinecraftDir(), "texturepacks")).getAbsolutePath();
		this.guiTexturePackSlot = new GuiTexturePackSlot(this);
		this.guiTexturePackSlot.registerScrollButtons(this.controlList, 7, 8);
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.enabled) {
			if(guiButton1.id == 5) {
				Sys.openURL("file://" + this.fileLocation);
			} else if(guiButton1.id == 6) {
				this.mc.renderEngine.refreshTextures();
				this.mc.displayGuiScreen(this.guiScreen);
			} else {
				this.guiTexturePackSlot.actionPerformed(guiButton1);
			}

		}
	}

	protected void mouseClicked(int i1, int i2, int i3) {
		super.mouseClicked(i1, i2, i3);
	}

	protected void mouseMovedOrUp(int i1, int i2, int i3) {
		super.mouseMovedOrUp(i1, i2, i3);
	}

	public void drawScreen(int i1, int i2, float f3) {
		this.guiTexturePackSlot.drawScreen(i1, i2, f3);
		if(this.field_6454_o <= 0) {
			this.mc.texturePackList.updateAvaliableTexturePacks();
			this.field_6454_o += 20;
		}

		StringTranslate stringTranslate4 = StringTranslate.getInstance();
		this.drawCenteredString(this.fontRenderer, stringTranslate4.translateKey("texturePack.title"), this.width / 2, 16, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, stringTranslate4.translateKey("texturePack.folderInfo"), this.width / 2 - 77, this.height - 26, 8421504);
		super.drawScreen(i1, i2, f3);
	}

	public void updateScreen() {
		super.updateScreen();
		--this.field_6454_o;
	}

	static Minecraft func_22124_a(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.mc;
	}

	static Minecraft func_22126_b(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.mc;
	}

	static Minecraft func_22119_c(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.mc;
	}

	static Minecraft func_22122_d(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.mc;
	}

	static Minecraft func_22117_e(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.mc;
	}

	static Minecraft func_22118_f(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.mc;
	}

	static Minecraft func_22116_g(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.mc;
	}

	static Minecraft func_22121_h(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.mc;
	}

	static Minecraft func_22123_i(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.mc;
	}

	static FontRenderer func_22127_j(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.fontRenderer;
	}

	static FontRenderer func_22120_k(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.fontRenderer;
	}

	static FontRenderer func_22125_l(GuiTexturePacks guiTexturePacks0) {
		return guiTexturePacks0.fontRenderer;
	}
}
