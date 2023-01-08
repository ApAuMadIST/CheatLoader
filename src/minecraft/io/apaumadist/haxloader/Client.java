package io.apaumadist.haxloader;

import io.apaumadist.haxloader.utils.ChatUtils;

public class Client {
	public static String VERSION = "1.0";
	
	public ChatUtils chatutils;
		
	public void init() {
		this.chatutils = new ChatUtils();
		
	}
}
