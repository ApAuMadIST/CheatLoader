package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class GuiMultiplayer extends GuiScreen {
	private GuiScreen parentScreen;
	private GuiTextField field_22111_h;

	public GuiMultiplayer(GuiScreen guiScreen1) {
		this.parentScreen = guiScreen1;
	}

	public void updateScreen() {
		this.field_22111_h.updateCursorCounter();
	}

	public void initGui() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringTranslate1.translateKey("multiplayer.connect")));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, stringTranslate1.translateKey("gui.cancel")));
		String string2 = this.mc.gameSettings.lastServer.replaceAll("_", ":");
		((GuiButton)this.controlList.get(0)).enabled = string2.length() > 0;
		this.field_22111_h = new GuiTextField(this, this.fontRenderer, this.width / 2 - 100, this.height / 4 - 10 + 50 + 18, 200, 20, string2);
		this.field_22111_h.isFocused = true;
		this.field_22111_h.setMaxStringLength(128);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.enabled) {
			if(guiButton1.id == 1) {
				this.mc.displayGuiScreen(this.parentScreen);
			} else if(guiButton1.id == 0) {
				String string2 = this.field_22111_h.getText().trim();
				this.mc.gameSettings.lastServer = string2.replaceAll(":", "_");
				this.mc.gameSettings.saveOptions();
				String[] string3 = string2.split(":");
				if(string2.startsWith("[")) {
					int i4 = string2.indexOf("]");
					if(i4 > 0) {
						String string5 = string2.substring(1, i4);
						String string6 = string2.substring(i4 + 1).trim();
						if(string6.startsWith(":") && string6.length() > 0) {
							string6 = string6.substring(1);
							string3 = new String[]{string5, string6};
						} else {
							string3 = new String[]{string5};
						}
					}
				}

				if(string3.length > 2) {
					string3 = new String[]{string2};
				}

				this.mc.displayGuiScreen(new GuiConnecting(this.mc, string3[0], string3.length > 1 ? this.parseIntWithDefault(string3[1], 25565) : 25565));
			}

		}
	}

	private int parseIntWithDefault(String string1, int i2) {
		try {
			return Integer.parseInt(string1.trim());
		} catch (Exception exception4) {
			return i2;
		}
	}

	protected void keyTyped(char c1, int i2) {
		this.field_22111_h.textboxKeyTyped(c1, i2);
		if(c1 == 13) {
			this.actionPerformed((GuiButton)this.controlList.get(0));
		}

		((GuiButton)this.controlList.get(0)).enabled = this.field_22111_h.getText().length() > 0;
	}

	protected void mouseClicked(int i1, int i2, int i3) {
		super.mouseClicked(i1, i2, i3);
		this.field_22111_h.mouseClicked(i1, i2, i3);
	}

	public void drawScreen(int i1, int i2, float f3) {
		StringTranslate stringTranslate4 = StringTranslate.getInstance();
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, stringTranslate4.translateKey("multiplayer.title"), this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
		this.drawString(this.fontRenderer, stringTranslate4.translateKey("multiplayer.info1"), this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 10526880);
		this.drawString(this.fontRenderer, stringTranslate4.translateKey("multiplayer.info2"), this.width / 2 - 140, this.height / 4 - 60 + 60 + 9, 10526880);
		this.drawString(this.fontRenderer, stringTranslate4.translateKey("multiplayer.ipinfo"), this.width / 2 - 140, this.height / 4 - 60 + 60 + 36, 10526880);
		this.field_22111_h.drawTextBox();
		super.drawScreen(i1, i2, f3);
	}
}
