package net.minecraft.src;

public class UnexpectedThrowable {
	public final String description;
	public final Throwable exception;

	public UnexpectedThrowable(String string1, Throwable throwable2) {
		this.description = string1;
		this.exception = throwable2;
	}
}
