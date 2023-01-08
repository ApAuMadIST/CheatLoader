package net.minecraft.src;

public class GuiSmallButton extends GuiButton {
	private final EnumOptions enumOptions;

	public GuiSmallButton(int i1, int i2, int i3, String string4) {
		this(i1, i2, i3, (EnumOptions)null, string4);
	}

	public GuiSmallButton(int i1, int i2, int i3, int i4, int i5, String string6) {
		super(i1, i2, i3, i4, i5, string6);
		this.enumOptions = null;
	}

	public GuiSmallButton(int i1, int i2, int i3, EnumOptions enumOptions4, String string5) {
		super(i1, i2, i3, 150, 20, string5);
		this.enumOptions = enumOptions4;
	}

	public EnumOptions returnEnumOptions() {
		return this.enumOptions;
	}
}
