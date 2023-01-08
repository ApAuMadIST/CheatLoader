package net.minecraft.src;

public class GuiOptions extends GuiScreen {
	private GuiScreen parentScreen;
	protected String screenTitle = "Options";
	private GameSettings options;
	private static EnumOptions[] field_22135_k = new EnumOptions[]{EnumOptions.MUSIC, EnumOptions.SOUND, EnumOptions.INVERT_MOUSE, EnumOptions.SENSITIVITY, EnumOptions.DIFFICULTY};

	public GuiOptions(GuiScreen guiScreen1, GameSettings gameSettings2) {
		this.parentScreen = guiScreen1;
		this.options = gameSettings2;
	}

	public void initGui() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		this.screenTitle = stringTranslate1.translateKey("options.title");
		int i2 = 0;
		EnumOptions[] enumOptions3 = field_22135_k;
		int i4 = enumOptions3.length;

		for(int i5 = 0; i5 < i4; ++i5) {
			EnumOptions enumOptions6 = enumOptions3[i5];
			if(!enumOptions6.getEnumFloat()) {
				this.controlList.add(new GuiSmallButton(enumOptions6.returnEnumOrdinal(), this.width / 2 - 155 + i2 % 2 * 160, this.height / 6 + 24 * (i2 >> 1), enumOptions6, this.options.getKeyBinding(enumOptions6)));
			} else {
				this.controlList.add(new GuiSlider(enumOptions6.returnEnumOrdinal(), this.width / 2 - 155 + i2 % 2 * 160, this.height / 6 + 24 * (i2 >> 1), enumOptions6, this.options.getKeyBinding(enumOptions6), this.options.getOptionFloatValue(enumOptions6)));
			}

			++i2;
		}

		this.controlList.add(new GuiButton(101, this.width / 2 - 100, this.height / 6 + 96 + 12, stringTranslate1.translateKey("options.video")));
		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 120 + 12, stringTranslate1.translateKey("options.controls")));
		this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, stringTranslate1.translateKey("gui.done")));
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.enabled) {
			if(guiButton1.id < 100 && guiButton1 instanceof GuiSmallButton) {
				this.options.setOptionValue(((GuiSmallButton)guiButton1).returnEnumOptions(), 1);
				guiButton1.displayString = this.options.getKeyBinding(EnumOptions.getEnumOptions(guiButton1.id));
			}

			if(guiButton1.id == 101) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiVideoSettings(this, this.options));
			}

			if(guiButton1.id == 100) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiControls(this, this.options));
			}

			if(guiButton1.id == 200) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(this.parentScreen);
			}

		}
	}

	public void drawScreen(int i1, int i2, float f3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
		super.drawScreen(i1, i2, f3);
	}
}
