package net.minecraft.src;

import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

class ThreadDownloadImage extends Thread {
	final String location;
	final ImageBuffer buffer;
	final ThreadDownloadImageData imageData;

	ThreadDownloadImage(ThreadDownloadImageData threadDownloadImageData1, String string2, ImageBuffer imageBuffer3) {
		this.imageData = threadDownloadImageData1;
		this.location = string2;
		this.buffer = imageBuffer3;
	}

	public void run() {
		HttpURLConnection httpURLConnection1 = null;

		try {
			URL uRL2 = new URL(this.location);
			httpURLConnection1 = (HttpURLConnection)uRL2.openConnection();
			httpURLConnection1.setDoInput(true);
			httpURLConnection1.setDoOutput(false);
			httpURLConnection1.connect();
			if(httpURLConnection1.getResponseCode() / 100 == 4) {
				return;
			}

			if(this.buffer == null) {
				this.imageData.image = ImageIO.read(httpURLConnection1.getInputStream());
			} else {
				this.imageData.image = this.buffer.parseUserSkin(ImageIO.read(httpURLConnection1.getInputStream()));
			}
		} catch (Exception exception6) {
			exception6.printStackTrace();
		} finally {
			httpURLConnection1.disconnect();
		}

	}
}
