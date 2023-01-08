package net.minecraft.src;

public class GuiConnectFailed extends GuiScreen {
	private String errorMessage;
	private String errorDetail;

	public GuiConnectFailed(String string1, String string2, Object... object3) {
		StringTranslate stringTranslate4 = StringTranslate.getInstance();
		this.errorMessage = stringTranslate4.translateKey(string1);
		if(object3 != null) {
			this.errorDetail = stringTranslate4.translateKeyFormat(string2, object3);
		} else {
			this.errorDetail = stringTranslate4.translateKey(string2);
		}

	}

	public void updateScreen() {
	}

	protected void keyTyped(char c1, int i2) {
	}

	public void initGui() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, stringTranslate1.translateKey("gui.toMenu")));
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.id == 0) {
			this.mc.displayGuiScreen(new GuiMainMenu());
		}

	}

	public void drawScreen(int i1, int i2, float f3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.errorMessage, this.width / 2, this.height / 2 - 50, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, this.errorDetail, this.width / 2, this.height / 2 - 10, 0xFFFFFF);
		super.drawScreen(i1, i2, f3);
	}
}
