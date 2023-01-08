package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenShotHelper {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
	private static ByteBuffer buffer;
	private static byte[] pixelData;
	private static int[] imageData;

	public static String saveScreenshot(File file0, int i1, int i2) {
		try {
			File file3 = new File(file0, "screenshots");
			file3.mkdir();
			if(buffer == null || buffer.capacity() < i1 * i2) {
				buffer = BufferUtils.createByteBuffer(i1 * i2 * 3);
			}

			if(imageData == null || imageData.length < i1 * i2 * 3) {
				pixelData = new byte[i1 * i2 * 3];
				imageData = new int[i1 * i2];
			}

			GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			buffer.clear();
			GL11.glReadPixels(0, 0, i1, i2, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);
			buffer.clear();
			String string4 = "" + dateFormat.format(new Date());

			File file5;
			for(int i6 = 1; (file5 = new File(file3, string4 + (i6 == 1 ? "" : "_" + i6) + ".png")).exists(); ++i6) {
			}

			buffer.get(pixelData);

			for(int i7 = 0; i7 < i1; ++i7) {
				for(int i8 = 0; i8 < i2; ++i8) {
					int i9 = i7 + (i2 - i8 - 1) * i1;
					int i10 = pixelData[i9 * 3 + 0] & 255;
					int i11 = pixelData[i9 * 3 + 1] & 255;
					int i12 = pixelData[i9 * 3 + 2] & 255;
					int i13 = 0xFF000000 | i10 << 16 | i11 << 8 | i12;
					imageData[i7 + i8 * i1] = i13;
				}
			}

			BufferedImage bufferedImage15 = new BufferedImage(i1, i2, 1);
			bufferedImage15.setRGB(0, 0, i1, i2, imageData, 0, i1);
			ImageIO.write(bufferedImage15, "png", file5);
			return "Saved screenshot as " + file5.getName();
		} catch (Exception exception14) {
			exception14.printStackTrace();
			return "Failed to save: " + exception14;
		}
	}
}
