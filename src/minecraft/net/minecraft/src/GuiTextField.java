package net.minecraft.src;

public class GuiTextField extends Gui {
	private final FontRenderer fontRenderer;
	private final int xPos;
	private final int yPos;
	private final int width;
	private final int height;
	private String text;
	private int maxStringLength;
	private int cursorCounter;
	public boolean isFocused = false;
	public boolean isEnabled = true;
	private GuiScreen parentGuiScreen;

	public GuiTextField(GuiScreen guiScreen1, FontRenderer fontRenderer2, int i3, int i4, int i5, int i6, String string7) {
		this.parentGuiScreen = guiScreen1;
		this.fontRenderer = fontRenderer2;
		this.xPos = i3;
		this.yPos = i4;
		this.width = i5;
		this.height = i6;
		this.setText(string7);
	}

	public void setText(String string1) {
		this.text = string1;
	}

	public String getText() {
		return this.text;
	}

	public void updateCursorCounter() {
		++this.cursorCounter;
	}

	public void textboxKeyTyped(char c1, int i2) {
		if(this.isEnabled && this.isFocused) {
			if(c1 == 9) {
				this.parentGuiScreen.selectNextField();
			}

			if(c1 == 22) {
				String string3 = GuiScreen.getClipboardString();
				if(string3 == null) {
					string3 = "";
				}

				int i4 = 32 - this.text.length();
				if(i4 > string3.length()) {
					i4 = string3.length();
				}

				if(i4 > 0) {
					this.text = this.text + string3.substring(0, i4);
				}
			}

			if(i2 == 14 && this.text.length() > 0) {
				this.text = this.text.substring(0, this.text.length() - 1);
			}

			if(ChatAllowedCharacters.allowedCharacters.indexOf(c1) >= 0 && (this.text.length() < this.maxStringLength || this.maxStringLength == 0)) {
				this.text = this.text + c1;
			}

		}
	}

	public void mouseClicked(int i1, int i2, int i3) {
		boolean z4 = this.isEnabled && i1 >= this.xPos && i1 < this.xPos + this.width && i2 >= this.yPos && i2 < this.yPos + this.height;
		this.setFocused(z4);
	}

	public void setFocused(boolean z1) {
		if(z1 && !this.isFocused) {
			this.cursorCounter = 0;
		}

		this.isFocused = z1;
	}

	public void drawTextBox() {
		this.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
		this.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, 0xFF000000);
		if(this.isEnabled) {
			boolean z1 = this.isFocused && this.cursorCounter / 6 % 2 == 0;
			this.drawString(this.fontRenderer, this.text + (z1 ? "_" : ""), this.xPos + 4, this.yPos + (this.height - 8) / 2, 14737632);
		} else {
			this.drawString(this.fontRenderer, this.text, this.xPos + 4, this.yPos + (this.height - 8) / 2, 7368816);
		}

	}

	public void setMaxStringLength(int i1) {
		this.maxStringLength = i1;
	}
}
