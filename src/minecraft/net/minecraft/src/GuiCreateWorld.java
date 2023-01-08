package net.minecraft.src;

import java.util.Random;

import org.lwjgl.input.Keyboard;

public class GuiCreateWorld extends GuiScreen {
	private GuiScreen field_22131_a;
	private GuiTextField textboxWorldName;
	private GuiTextField textboxSeed;
	private String folderName;
	private boolean createClicked;

	public GuiCreateWorld(GuiScreen guiScreen1) {
		this.field_22131_a = guiScreen1;
	}

	public void updateScreen() {
		this.textboxWorldName.updateCursorCounter();
		this.textboxSeed.updateCursorCounter();
	}

	public void initGui() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.controlList.clear();
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, stringTranslate1.translateKey("selectWorld.create")));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, stringTranslate1.translateKey("gui.cancel")));
		this.textboxWorldName = new GuiTextField(this, this.fontRenderer, this.width / 2 - 100, 60, 200, 20, stringTranslate1.translateKey("selectWorld.newWorld"));
		this.textboxWorldName.isFocused = true;
		this.textboxWorldName.setMaxStringLength(32);
		this.textboxSeed = new GuiTextField(this, this.fontRenderer, this.width / 2 - 100, 116, 200, 20, "");
		this.func_22129_j();
	}

	private void func_22129_j() {
		this.folderName = this.textboxWorldName.getText().trim();
		char[] c1 = ChatAllowedCharacters.allowedCharactersArray;
		int i2 = c1.length;

		for(int i3 = 0; i3 < i2; ++i3) {
			char c4 = c1[i3];
			this.folderName = this.folderName.replace(c4, '_');
		}

		if(MathHelper.stringNullOrLengthZero(this.folderName)) {
			this.folderName = "World";
		}

		this.folderName = generateUnusedFolderName(this.mc.getSaveLoader(), this.folderName);
	}

	public static String generateUnusedFolderName(ISaveFormat iSaveFormat0, String string1) {
		while(iSaveFormat0.func_22173_b(string1) != null) {
			string1 = string1 + "-";
		}

		return string1;
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.enabled) {
			if(guiButton1.id == 1) {
				this.mc.displayGuiScreen(this.field_22131_a);
			} else if(guiButton1.id == 0) {
				this.mc.displayGuiScreen((GuiScreen)null);
				if(this.createClicked) {
					return;
				}

				this.createClicked = true;
				long j2 = (new Random()).nextLong();
				String string4 = this.textboxSeed.getText();
				if(!MathHelper.stringNullOrLengthZero(string4)) {
					try {
						long j5 = Long.parseLong(string4);
						if(j5 != 0L) {
							j2 = j5;
						}
					} catch (NumberFormatException numberFormatException7) {
						j2 = (long)string4.hashCode();
					}
				}

				this.mc.playerController = new PlayerControllerSP(this.mc);
				this.mc.startWorld(this.folderName, this.textboxWorldName.getText(), j2);
				this.mc.displayGuiScreen((GuiScreen)null);
			}

		}
	}

	protected void keyTyped(char c1, int i2) {
		if(this.textboxWorldName.isFocused) {
			this.textboxWorldName.textboxKeyTyped(c1, i2);
		} else {
			this.textboxSeed.textboxKeyTyped(c1, i2);
		}

		if(c1 == 13) {
			this.actionPerformed((GuiButton)this.controlList.get(0));
		}

		((GuiButton)this.controlList.get(0)).enabled = this.textboxWorldName.getText().length() > 0;
		this.func_22129_j();
	}

	protected void mouseClicked(int i1, int i2, int i3) {
		super.mouseClicked(i1, i2, i3);
		this.textboxWorldName.mouseClicked(i1, i2, i3);
		this.textboxSeed.mouseClicked(i1, i2, i3);
	}

	public void drawScreen(int i1, int i2, float f3) {
		StringTranslate stringTranslate4 = StringTranslate.getInstance();
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.create"), this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF);
		this.drawString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.enterName"), this.width / 2 - 100, 47, 10526880);
		this.drawString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.resultFolder") + " " + this.folderName, this.width / 2 - 100, 85, 10526880);
		this.drawString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.enterSeed"), this.width / 2 - 100, 104, 10526880);
		this.drawString(this.fontRenderer, stringTranslate4.translateKey("selectWorld.seedInfo"), this.width / 2 - 100, 140, 10526880);
		this.textboxWorldName.drawTextBox();
		this.textboxSeed.drawTextBox();
		super.drawScreen(i1, i2, f3);
	}

	public void selectNextField() {
		if(this.textboxWorldName.isFocused) {
			this.textboxWorldName.setFocused(false);
			this.textboxSeed.setFocused(true);
		} else {
			this.textboxWorldName.setFocused(true);
			this.textboxSeed.setFocused(false);
		}

	}
}
