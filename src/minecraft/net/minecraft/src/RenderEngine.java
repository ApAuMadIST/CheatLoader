package net.minecraft.src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

public class RenderEngine {
	public static boolean useMipmaps = false;
	private HashMap textureMap = new HashMap();
	private HashMap field_28151_c = new HashMap();
	private HashMap textureNameToImageMap = new HashMap();
	private IntBuffer singleIntBuffer = GLAllocation.createDirectIntBuffer(1);
	private ByteBuffer imageData = GLAllocation.createDirectByteBuffer(1048576);
	private List textureList = new ArrayList();
	private Map urlToImageDataMap = new HashMap();
	private GameSettings options;
	private boolean clampTexture = false;
	private boolean blurTexture = false;
	private TexturePackList texturePack;
	private BufferedImage missingTextureImage = new BufferedImage(64, 64, 2);

	public RenderEngine(TexturePackList texturePackList1, GameSettings gameSettings2) {
		this.texturePack = texturePackList1;
		this.options = gameSettings2;
		Graphics graphics3 = this.missingTextureImage.getGraphics();
		graphics3.setColor(Color.WHITE);
		graphics3.fillRect(0, 0, 64, 64);
		graphics3.setColor(Color.BLACK);
		graphics3.drawString("missingtex", 1, 10);
		graphics3.dispose();
	}

	public int[] func_28149_a(String string1) {
		TexturePackBase texturePackBase2 = this.texturePack.selectedTexturePack;
		int[] i3 = (int[])this.field_28151_c.get(string1);
		if(i3 != null) {
			return i3;
		} else {
			try {
				Object object6 = null;
				if(string1.startsWith("##")) {
					i3 = this.func_28148_b(this.unwrapImageByColumns(this.readTextureImage(texturePackBase2.getResourceAsStream(string1.substring(2)))));
				} else if(string1.startsWith("%clamp%")) {
					this.clampTexture = true;
					i3 = this.func_28148_b(this.readTextureImage(texturePackBase2.getResourceAsStream(string1.substring(7))));
					this.clampTexture = false;
				} else if(string1.startsWith("%blur%")) {
					this.blurTexture = true;
					i3 = this.func_28148_b(this.readTextureImage(texturePackBase2.getResourceAsStream(string1.substring(6))));
					this.blurTexture = false;
				} else {
					InputStream inputStream7 = texturePackBase2.getResourceAsStream(string1);
					if(inputStream7 == null) {
						i3 = this.func_28148_b(this.missingTextureImage);
					} else {
						i3 = this.func_28148_b(this.readTextureImage(inputStream7));
					}
				}

				this.field_28151_c.put(string1, i3);
				return i3;
			} catch (IOException iOException5) {
				iOException5.printStackTrace();
				int[] i4 = this.func_28148_b(this.missingTextureImage);
				this.field_28151_c.put(string1, i4);
				return i4;
			}
		}
	}

	private int[] func_28148_b(BufferedImage bufferedImage1) {
		int i2 = bufferedImage1.getWidth();
		int i3 = bufferedImage1.getHeight();
		int[] i4 = new int[i2 * i3];
		bufferedImage1.getRGB(0, 0, i2, i3, i4, 0, i2);
		return i4;
	}

	private int[] func_28147_a(BufferedImage bufferedImage1, int[] i2) {
		int i3 = bufferedImage1.getWidth();
		int i4 = bufferedImage1.getHeight();
		bufferedImage1.getRGB(0, 0, i3, i4, i2, 0, i3);
		return i2;
	}

	public int getTexture(String string1) {
		TexturePackBase texturePackBase2 = this.texturePack.selectedTexturePack;
		Integer integer3 = (Integer)this.textureMap.get(string1);
		if(integer3 != null) {
			return integer3.intValue();
		} else {
			try {
				this.singleIntBuffer.clear();
				GLAllocation.generateTextureNames(this.singleIntBuffer);
				int i6 = this.singleIntBuffer.get(0);
				if(string1.startsWith("##")) {
					this.setupTexture(this.unwrapImageByColumns(this.readTextureImage(texturePackBase2.getResourceAsStream(string1.substring(2)))), i6);
				} else if(string1.startsWith("%clamp%")) {
					this.clampTexture = true;
					this.setupTexture(this.readTextureImage(texturePackBase2.getResourceAsStream(string1.substring(7))), i6);
					this.clampTexture = false;
				} else if(string1.startsWith("%blur%")) {
					this.blurTexture = true;
					this.setupTexture(this.readTextureImage(texturePackBase2.getResourceAsStream(string1.substring(6))), i6);
					this.blurTexture = false;
				} else {
					InputStream inputStream7 = texturePackBase2.getResourceAsStream(string1);
					if(inputStream7 == null) {
						this.setupTexture(this.missingTextureImage, i6);
					} else {
						this.setupTexture(this.readTextureImage(inputStream7), i6);
					}
				}

				this.textureMap.put(string1, i6);
				return i6;
			} catch (IOException iOException5) {
				iOException5.printStackTrace();
				GLAllocation.generateTextureNames(this.singleIntBuffer);
				int i4 = this.singleIntBuffer.get(0);
				this.setupTexture(this.missingTextureImage, i4);
				this.textureMap.put(string1, i4);
				return i4;
			}
		}
	}

	private BufferedImage unwrapImageByColumns(BufferedImage bufferedImage1) {
		int i2 = bufferedImage1.getWidth() / 16;
		BufferedImage bufferedImage3 = new BufferedImage(16, bufferedImage1.getHeight() * i2, 2);
		Graphics graphics4 = bufferedImage3.getGraphics();

		for(int i5 = 0; i5 < i2; ++i5) {
			graphics4.drawImage(bufferedImage1, -i5 * 16, i5 * bufferedImage1.getHeight(), (ImageObserver)null);
		}

		graphics4.dispose();
		return bufferedImage3;
	}

	public int allocateAndSetupTexture(BufferedImage bufferedImage1) {
		this.singleIntBuffer.clear();
		GLAllocation.generateTextureNames(this.singleIntBuffer);
		int i2 = this.singleIntBuffer.get(0);
		this.setupTexture(bufferedImage1, i2);
		this.textureNameToImageMap.put(i2, bufferedImage1);
		return i2;
	}

	public void setupTexture(BufferedImage bufferedImage1, int i2) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, i2);
		if(useMipmaps) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		}

		if(this.blurTexture) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		}

		if(this.clampTexture) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		}

		int i3 = bufferedImage1.getWidth();
		int i4 = bufferedImage1.getHeight();
		int[] i5 = new int[i3 * i4];
		byte[] b6 = new byte[i3 * i4 * 4];
		bufferedImage1.getRGB(0, 0, i3, i4, i5, 0, i3);

		int i7;
		int i8;
		int i9;
		int i10;
		int i11;
		int i12;
		int i13;
		int i14;
		for(i7 = 0; i7 < i5.length; ++i7) {
			i8 = i5[i7] >> 24 & 255;
			i9 = i5[i7] >> 16 & 255;
			i10 = i5[i7] >> 8 & 255;
			i11 = i5[i7] & 255;
			if(this.options != null && this.options.anaglyph) {
				i12 = (i9 * 30 + i10 * 59 + i11 * 11) / 100;
				i13 = (i9 * 30 + i10 * 70) / 100;
				i14 = (i9 * 30 + i11 * 70) / 100;
				i9 = i12;
				i10 = i13;
				i11 = i14;
			}

			b6[i7 * 4 + 0] = (byte)i9;
			b6[i7 * 4 + 1] = (byte)i10;
			b6[i7 * 4 + 2] = (byte)i11;
			b6[i7 * 4 + 3] = (byte)i8;
		}

		this.imageData.clear();
		this.imageData.put(b6);
		this.imageData.position(0).limit(b6.length);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, i3, i4, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
		if(useMipmaps) {
			for(i7 = 1; i7 <= 4; ++i7) {
				i8 = i3 >> i7 - 1;
				i9 = i3 >> i7;
				i10 = i4 >> i7;

				for(i11 = 0; i11 < i9; ++i11) {
					for(i12 = 0; i12 < i10; ++i12) {
						i13 = this.imageData.getInt((i11 * 2 + 0 + (i12 * 2 + 0) * i8) * 4);
						i14 = this.imageData.getInt((i11 * 2 + 1 + (i12 * 2 + 0) * i8) * 4);
						int i15 = this.imageData.getInt((i11 * 2 + 1 + (i12 * 2 + 1) * i8) * 4);
						int i16 = this.imageData.getInt((i11 * 2 + 0 + (i12 * 2 + 1) * i8) * 4);
						int i17 = this.weightedAverageColor(this.weightedAverageColor(i13, i14), this.weightedAverageColor(i15, i16));
						this.imageData.putInt((i11 + i12 * i9) * 4, i17);
					}
				}

				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, i7, GL11.GL_RGBA, i9, i10, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
			}
		}

	}

	public void func_28150_a(int[] i1, int i2, int i3, int i4) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, i4);
		if(useMipmaps) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		}

		if(this.blurTexture) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		}

		if(this.clampTexture) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		}

		byte[] b5 = new byte[i2 * i3 * 4];

		for(int i6 = 0; i6 < i1.length; ++i6) {
			int i7 = i1[i6] >> 24 & 255;
			int i8 = i1[i6] >> 16 & 255;
			int i9 = i1[i6] >> 8 & 255;
			int i10 = i1[i6] & 255;
			if(this.options != null && this.options.anaglyph) {
				int i11 = (i8 * 30 + i9 * 59 + i10 * 11) / 100;
				int i12 = (i8 * 30 + i9 * 70) / 100;
				int i13 = (i8 * 30 + i10 * 70) / 100;
				i8 = i11;
				i9 = i12;
				i10 = i13;
			}

			b5[i6 * 4 + 0] = (byte)i8;
			b5[i6 * 4 + 1] = (byte)i9;
			b5[i6 * 4 + 2] = (byte)i10;
			b5[i6 * 4 + 3] = (byte)i7;
		}

		this.imageData.clear();
		this.imageData.put(b5);
		this.imageData.position(0).limit(b5.length);
		GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, i2, i3, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
	}

	public void deleteTexture(int i1) {
		this.textureNameToImageMap.remove(i1);
		this.singleIntBuffer.clear();
		this.singleIntBuffer.put(i1);
		this.singleIntBuffer.flip();
		GL11.glDeleteTextures(this.singleIntBuffer);
	}

	public int getTextureForDownloadableImage(String string1, String string2) {
		ThreadDownloadImageData threadDownloadImageData3 = (ThreadDownloadImageData)this.urlToImageDataMap.get(string1);
		if(threadDownloadImageData3 != null && threadDownloadImageData3.image != null && !threadDownloadImageData3.textureSetupComplete) {
			if(threadDownloadImageData3.textureName < 0) {
				threadDownloadImageData3.textureName = this.allocateAndSetupTexture(threadDownloadImageData3.image);
			} else {
				this.setupTexture(threadDownloadImageData3.image, threadDownloadImageData3.textureName);
			}

			threadDownloadImageData3.textureSetupComplete = true;
		}

		return threadDownloadImageData3 != null && threadDownloadImageData3.textureName >= 0 ? threadDownloadImageData3.textureName : (string2 == null ? -1 : this.getTexture(string2));
	}

	public ThreadDownloadImageData obtainImageData(String string1, ImageBuffer imageBuffer2) {
		ThreadDownloadImageData threadDownloadImageData3 = (ThreadDownloadImageData)this.urlToImageDataMap.get(string1);
		if(threadDownloadImageData3 == null) {
			this.urlToImageDataMap.put(string1, new ThreadDownloadImageData(string1, imageBuffer2));
		} else {
			++threadDownloadImageData3.referenceCount;
		}

		return threadDownloadImageData3;
	}

	public void releaseImageData(String string1) {
		ThreadDownloadImageData threadDownloadImageData2 = (ThreadDownloadImageData)this.urlToImageDataMap.get(string1);
		if(threadDownloadImageData2 != null) {
			--threadDownloadImageData2.referenceCount;
			if(threadDownloadImageData2.referenceCount == 0) {
				if(threadDownloadImageData2.textureName >= 0) {
					this.deleteTexture(threadDownloadImageData2.textureName);
				}

				this.urlToImageDataMap.remove(string1);
			}
		}

	}

	public void registerTextureFX(TextureFX textureFX1) {
		this.textureList.add(textureFX1);
		textureFX1.onTick();
	}

	public void updateDynamicTextures() {
		int i1;
		TextureFX textureFX2;
		int i3;
		int i4;
		int i5;
		int i6;
		int i7;
		int i8;
		int i9;
		int i10;
		int i11;
		int i12;
		for(i1 = 0; i1 < this.textureList.size(); ++i1) {
			textureFX2 = (TextureFX)this.textureList.get(i1);
			textureFX2.anaglyphEnabled = this.options.anaglyph;
			textureFX2.onTick();
			this.imageData.clear();
			this.imageData.put(textureFX2.imageData);
			this.imageData.position(0).limit(textureFX2.imageData.length);
			textureFX2.bindImage(this);

			for(i3 = 0; i3 < textureFX2.tileSize; ++i3) {
				for(i4 = 0; i4 < textureFX2.tileSize; ++i4) {
					GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, textureFX2.iconIndex % 16 * 16 + i3 * 16, textureFX2.iconIndex / 16 * 16 + i4 * 16, 16, 16, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
					if(useMipmaps) {
						for(i5 = 1; i5 <= 4; ++i5) {
							i6 = 16 >> i5 - 1;
							i7 = 16 >> i5;

							for(i8 = 0; i8 < i7; ++i8) {
								for(i9 = 0; i9 < i7; ++i9) {
									i10 = this.imageData.getInt((i8 * 2 + 0 + (i9 * 2 + 0) * i6) * 4);
									i11 = this.imageData.getInt((i8 * 2 + 1 + (i9 * 2 + 0) * i6) * 4);
									i12 = this.imageData.getInt((i8 * 2 + 1 + (i9 * 2 + 1) * i6) * 4);
									int i13 = this.imageData.getInt((i8 * 2 + 0 + (i9 * 2 + 1) * i6) * 4);
									int i14 = this.averageColor(this.averageColor(i10, i11), this.averageColor(i12, i13));
									this.imageData.putInt((i8 + i9 * i7) * 4, i14);
								}
							}

							GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, i5, textureFX2.iconIndex % 16 * i7, textureFX2.iconIndex / 16 * i7, i7, i7, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
						}
					}
				}
			}
		}

		for(i1 = 0; i1 < this.textureList.size(); ++i1) {
			textureFX2 = (TextureFX)this.textureList.get(i1);
			if(textureFX2.textureId > 0) {
				this.imageData.clear();
				this.imageData.put(textureFX2.imageData);
				this.imageData.position(0).limit(textureFX2.imageData.length);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureFX2.textureId);
				GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 16, 16, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
				if(useMipmaps) {
					for(i3 = 1; i3 <= 4; ++i3) {
						i4 = 16 >> i3 - 1;
						i5 = 16 >> i3;

						for(i6 = 0; i6 < i5; ++i6) {
							for(i7 = 0; i7 < i5; ++i7) {
								i8 = this.imageData.getInt((i6 * 2 + 0 + (i7 * 2 + 0) * i4) * 4);
								i9 = this.imageData.getInt((i6 * 2 + 1 + (i7 * 2 + 0) * i4) * 4);
								i10 = this.imageData.getInt((i6 * 2 + 1 + (i7 * 2 + 1) * i4) * 4);
								i11 = this.imageData.getInt((i6 * 2 + 0 + (i7 * 2 + 1) * i4) * 4);
								i12 = this.averageColor(this.averageColor(i8, i9), this.averageColor(i10, i11));
								this.imageData.putInt((i6 + i7 * i5) * 4, i12);
							}
						}

						GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, i3, 0, 0, i5, i5, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageData);
					}
				}
			}
		}

	}

	private int averageColor(int i1, int i2) {
		int i3 = (i1 & 0xFF000000) >> 24 & 255;
		int i4 = (i2 & 0xFF000000) >> 24 & 255;
		return (i3 + i4 >> 1 << 24) + ((i1 & 16711422) + (i2 & 16711422) >> 1);
	}

	private int weightedAverageColor(int i1, int i2) {
		int i3 = (i1 & 0xFF000000) >> 24 & 255;
		int i4 = (i2 & 0xFF000000) >> 24 & 255;
		short s5 = 255;
		if(i3 + i4 == 0) {
			i3 = 1;
			i4 = 1;
			s5 = 0;
		}

		int i6 = (i1 >> 16 & 255) * i3;
		int i7 = (i1 >> 8 & 255) * i3;
		int i8 = (i1 & 255) * i3;
		int i9 = (i2 >> 16 & 255) * i4;
		int i10 = (i2 >> 8 & 255) * i4;
		int i11 = (i2 & 255) * i4;
		int i12 = (i6 + i9) / (i3 + i4);
		int i13 = (i7 + i10) / (i3 + i4);
		int i14 = (i8 + i11) / (i3 + i4);
		return s5 << 24 | i12 << 16 | i13 << 8 | i14;
	}

	public void refreshTextures() {
		TexturePackBase texturePackBase1 = this.texturePack.selectedTexturePack;
		Iterator iterator2 = this.textureNameToImageMap.keySet().iterator();

		BufferedImage bufferedImage4;
		while(iterator2.hasNext()) {
			int i3 = ((Integer)iterator2.next()).intValue();
			bufferedImage4 = (BufferedImage)this.textureNameToImageMap.get(i3);
			this.setupTexture(bufferedImage4, i3);
		}

		ThreadDownloadImageData threadDownloadImageData8;
		for(iterator2 = this.urlToImageDataMap.values().iterator(); iterator2.hasNext(); threadDownloadImageData8.textureSetupComplete = false) {
			threadDownloadImageData8 = (ThreadDownloadImageData)iterator2.next();
		}

		iterator2 = this.textureMap.keySet().iterator();

		String string9;
		while(iterator2.hasNext()) {
			string9 = (String)iterator2.next();

			try {
				if(string9.startsWith("##")) {
					bufferedImage4 = this.unwrapImageByColumns(this.readTextureImage(texturePackBase1.getResourceAsStream(string9.substring(2))));
				} else if(string9.startsWith("%clamp%")) {
					this.clampTexture = true;
					bufferedImage4 = this.readTextureImage(texturePackBase1.getResourceAsStream(string9.substring(7)));
				} else if(string9.startsWith("%blur%")) {
					this.blurTexture = true;
					bufferedImage4 = this.readTextureImage(texturePackBase1.getResourceAsStream(string9.substring(6)));
				} else {
					bufferedImage4 = this.readTextureImage(texturePackBase1.getResourceAsStream(string9));
				}

				int i5 = ((Integer)this.textureMap.get(string9)).intValue();
				this.setupTexture(bufferedImage4, i5);
				this.blurTexture = false;
				this.clampTexture = false;
			} catch (IOException iOException7) {
				iOException7.printStackTrace();
			}
		}

		iterator2 = this.field_28151_c.keySet().iterator();

		while(iterator2.hasNext()) {
			string9 = (String)iterator2.next();

			try {
				if(string9.startsWith("##")) {
					bufferedImage4 = this.unwrapImageByColumns(this.readTextureImage(texturePackBase1.getResourceAsStream(string9.substring(2))));
				} else if(string9.startsWith("%clamp%")) {
					this.clampTexture = true;
					bufferedImage4 = this.readTextureImage(texturePackBase1.getResourceAsStream(string9.substring(7)));
				} else if(string9.startsWith("%blur%")) {
					this.blurTexture = true;
					bufferedImage4 = this.readTextureImage(texturePackBase1.getResourceAsStream(string9.substring(6)));
				} else {
					bufferedImage4 = this.readTextureImage(texturePackBase1.getResourceAsStream(string9));
				}

				this.func_28147_a(bufferedImage4, (int[])this.field_28151_c.get(string9));
				this.blurTexture = false;
				this.clampTexture = false;
			} catch (IOException iOException6) {
				iOException6.printStackTrace();
			}
		}

	}

	private BufferedImage readTextureImage(InputStream inputStream1) throws IOException {
		BufferedImage bufferedImage2 = ImageIO.read(inputStream1);
		inputStream1.close();
		return bufferedImage2;
	}

	public void bindTexture(int i1) {
		if(i1 >= 0) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, i1);
		}
	}
}
