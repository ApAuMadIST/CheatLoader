package io.apaumadist.haxloader.utils;

import net.minecraft.client.Minecraft;

public class ChatUtils {
	Minecraft mc;

	public void chat(String msg) {
		mc.ingameGUI.addChatMessage(msg);
	}
	
	public void info(String msg) {
		chat("[INFO] "+msg);
	}
}
