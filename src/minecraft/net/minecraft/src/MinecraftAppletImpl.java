package net.minecraft.src;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;

public class MinecraftAppletImpl extends Minecraft {
	final MinecraftApplet mainFrame;

	public MinecraftAppletImpl(MinecraftApplet minecraftApplet1, Component component2, Canvas canvas3, MinecraftApplet minecraftApplet4, int i5, int i6, boolean z7) {
		super(component2, canvas3, minecraftApplet4, i5, i6, z7);
		this.mainFrame = minecraftApplet1;
	}

	public void displayUnexpectedThrowable(UnexpectedThrowable unexpectedThrowable1) {
		this.mainFrame.removeAll();
		this.mainFrame.setLayout(new BorderLayout());
		this.mainFrame.add(new PanelCrashReport(unexpectedThrowable1), "Center");
		this.mainFrame.validate();
	}
}
