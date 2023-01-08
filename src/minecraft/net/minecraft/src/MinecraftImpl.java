package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Frame;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;

public final class MinecraftImpl extends Minecraft {
	final Frame mcFrame;

	public MinecraftImpl(Component component1, Canvas canvas2, MinecraftApplet minecraftApplet3, int i4, int i5, boolean z6, Frame frame7) {
		super(component1, canvas2, minecraftApplet3, i4, i5, z6);
		this.mcFrame = frame7;
	}

	public void displayUnexpectedThrowable(UnexpectedThrowable unexpectedThrowable1) {
		this.mcFrame.removeAll();
		this.mcFrame.add(new PanelCrashReport(unexpectedThrowable1), "Center");
		this.mcFrame.validate();
	}
}
