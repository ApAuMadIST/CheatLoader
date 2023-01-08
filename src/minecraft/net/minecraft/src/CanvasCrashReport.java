package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Dimension;

class CanvasCrashReport extends Canvas {
	public CanvasCrashReport(int i1) {
		this.setPreferredSize(new Dimension(i1, i1));
		this.setMinimumSize(new Dimension(i1, i1));
	}
}
