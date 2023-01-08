package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class ThreadSleepForever extends Thread {
	final Minecraft mc;

	public ThreadSleepForever(Minecraft minecraft1, String string2) {
		super(string2);
		this.mc = minecraft1;
		this.setDaemon(true);
		this.start();
	}

	public void run() {
		while(this.mc.running) {
			try {
				Thread.sleep(2147483647L);
			} catch (InterruptedException interruptedException2) {
			}
		}

	}
}
