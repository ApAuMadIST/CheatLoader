package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class GuiRenameWorld extends GuiScreen {
	private GuiScreen field_22112_a;
	private GuiTextField field_22114_h;
	private final String field_22113_i;

	public GuiRenameWorld(GuiScreen guiScreen1, String string2) {
		this.field_22112_a = guiScreen1;
		this.field_22113_i = string2;
	}

	public void updateScreen() {
		this.field_22114_h.updateCursorCounter();
	}

	public void initGui() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringTranslate1.translateKey("selectWorld.renameButton")));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, stringTranslate1.translateKey("gui.cancel")));
		ISaveFormat iSaveFormat2 = this.mc.getSaveLoader();
		WorldInfo worldInfo3 = iSaveFormat2.func_22173_b(this.field_22113_i);
		String string4 = worldInfo3.getWorldName();
		this.field_22114_h = new GuiTextField(this, this.fontRenderer, this.width / 2 - 100, 60, 200, 20, string4);
		this.field_22114_h.isFocused = true;
		this.field_22114_h.setMaxStringLength(32);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.enabled) {
			if(guiButton1.id == 1) {
				this.mc.displayGuiScreen(this.field_22112_a);
			} else if(guiButton1.id == 0) {
				ISaveFormat iSaveFormat2 = this.mc.getSaveLoader();
				iSaveFormat2.func_22170_a(this.field_22113_i, this.field_22114_h.getText().trim());
				this.mc.displayGuiScreen(this.field_22112_a);
			}

		}
	}

	protected void keyTyped(char c1, int i2) {
		this.field_22114_h.textboxKeyTyped(c1, i2);
		((GuiButton)this.controlList.get(0)).enabled = this.field_22114_h.getText().trim().length() > 0;
		if(c1 == 13) {
			this.actionPerformed((GuiButton)this.controlList.get(0));
		}

	}

	protected void mouseClicked(int i1, int i2, int i3) {
		super.mouseClicked(i1, i2, i3);
		this.field_22114_h.mouseClicked(i1, i2, i3);
	}

	public void drawScreen(int i1, int i2, float f3) {
		StringTranslate stringTranslate4 = StringTranslate.getInstance();
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.renameTitle"), this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
		this.drawString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.enterName"), this.width / 2 - 100, 47, 10526880);
		this.field_22114_h.drawTextBox();
		super.drawScreen(i1, i2, f3);
	}
}
