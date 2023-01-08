package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

public class StatStringFormatKeyInv implements IStatStringFormat {
	final Minecraft mc;

	public StatStringFormatKeyInv(Minecraft minecraft1) {
		this.mc = minecraft1;
	}

	public String formatString(String string1) {
		return String.format(string1, new Object[]{Keyboard.getKeyName(this.mc.gameSettings.keyBindInventory.keyCode)});
	}
}
