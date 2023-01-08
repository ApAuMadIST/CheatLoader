package net.minecraft.src;

import java.net.HttpURLConnection;
import java.net.URL;

import net.minecraft.client.Minecraft;

public class ThreadCheckHasPaid extends Thread {
	final Minecraft field_28146_a;

	public ThreadCheckHasPaid(Minecraft minecraft1) {
		this.field_28146_a = minecraft1;
	}

	public void run() {
		try {
			HttpURLConnection httpURLConnection1 = (HttpURLConnection)(new URL("https://login.minecraft.net/session?name=" + this.field_28146_a.session.username + "&session=" + this.field_28146_a.session.sessionId)).openConnection();
			httpURLConnection1.connect();
			if(httpURLConnection1.getResponseCode() == 400) {
				Minecraft.hasPaidCheckTime = System.currentTimeMillis();
			}

			httpURLConnection1.disconnect();
		} catch (Exception exception2) {
			exception2.printStackTrace();
		}

	}
}
