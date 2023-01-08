package net.minecraft.src;

public class ChatLine {
	public String message;
	public int updateCounter;

	public ChatLine(String string1) {
		this.message = string1;
		this.updateCounter = 0;
	}
}
