package net.minecraft.src;

public class GuiControls extends GuiScreen {
	private GuiScreen parentScreen;
	protected String screenTitle = "Controls";
	private GameSettings options;
	private int buttonId = -1;

	public GuiControls(GuiScreen guiScreen1, GameSettings gameSettings2) {
		this.parentScreen = guiScreen1;
		this.options = gameSettings2;
	}

	private int func_20080_j() {
		return this.width / 2 - 155;
	}

	public void initGui() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		int i2 = this.func_20080_j();

		for(int i3 = 0; i3 < this.options.keyBindings.length; ++i3) {
			this.controlList.add(new GuiSmallButton(i3, i2 + i3 % 2 * 160, this.height / 6 + 24 * (i3 >> 1), 70, 20, this.options.getOptionDisplayString(i3)));
		}

		this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, stringTranslate1.translateKey("gui.done")));
		this.screenTitle = stringTranslate1.translateKey("controls.title");
	}

	protected void actionPerformed(GuiButton guiButton1) {
		for(int i2 = 0; i2 < this.options.keyBindings.length; ++i2) {
			((GuiButton)this.controlList.get(i2)).displayString = this.options.getOptionDisplayString(i2);
		}

		if(guiButton1.id == 200) {
			this.mc.displayGuiScreen(this.parentScreen);
		} else {
			this.buttonId = guiButton1.id;
			guiButton1.displayString = "> " + this.options.getOptionDisplayString(guiButton1.id) + " <";
		}

	}

	protected void keyTyped(char c1, int i2) {
		if(this.buttonId >= 0) {
			this.options.setKeyBinding(this.buttonId, i2);
			((GuiButton)this.controlList.get(this.buttonId)).displayString = this.options.getOptionDisplayString(this.buttonId);
			this.buttonId = -1;
		} else {
			super.keyTyped(c1, i2);
		}

	}

	public void drawScreen(int i1, int i2, float f3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
		int i4 = this.func_20080_j();

		for(int i5 = 0; i5 < this.options.keyBindings.length; ++i5) {
			this.drawString(this.fontRenderer, this.options.getKeyBindingDescription(i5), i4 + i5 % 2 * 160 + 70 + 6, this.height / 6 + 24 * (i5 >> 1) + 7, -1);
		}

		super.drawScreen(i1, i2, f3);
	}
}
