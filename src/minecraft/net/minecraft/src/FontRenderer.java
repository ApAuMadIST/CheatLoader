package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

public class FontRenderer {
	private int[] charWidth = new int[256];
	public int fontTextureName = 0;
	private int fontDisplayLists;
	private IntBuffer buffer = GLAllocation.createDirectIntBuffer(1024);

	public FontRenderer(GameSettings gameSettings1, String string2, RenderEngine renderEngine3) {
		BufferedImage bufferedImage4;
		try {
			bufferedImage4 = ImageIO.read(RenderEngine.class.getResourceAsStream(string2));
		} catch (IOException iOException18) {
			throw new RuntimeException(iOException18);
		}

		int i5 = bufferedImage4.getWidth();
		int i6 = bufferedImage4.getHeight();
		int[] i7 = new int[i5 * i6];
		bufferedImage4.getRGB(0, 0, i5, i6, i7, 0, i5);

		int i9;
		int i10;
		int i11;
		int i12;
		int i15;
		int i16;
		for(int i8 = 0; i8 < 256; ++i8) {
			i9 = i8 % 16;
			i10 = i8 / 16;

			for(i11 = 7; i11 >= 0; --i11) {
				i12 = i9 * 8 + i11;
				boolean z13 = true;

				for(int i14 = 0; i14 < 8 && z13; ++i14) {
					i15 = (i10 * 8 + i14) * i5;
					i16 = i7[i12 + i15] & 255;
					if(i16 > 0) {
						z13 = false;
					}
				}

				if(!z13) {
					break;
				}
			}

			if(i8 == 32) {
				i11 = 2;
			}

			this.charWidth[i8] = i11 + 2;
		}

		this.fontTextureName = renderEngine3.allocateAndSetupTexture(bufferedImage4);
		this.fontDisplayLists = GLAllocation.generateDisplayLists(288);
		Tessellator tessellator19 = Tessellator.instance;

		for(i9 = 0; i9 < 256; ++i9) {
			GL11.glNewList(this.fontDisplayLists + i9, GL11.GL_COMPILE);
			tessellator19.startDrawingQuads();
			i10 = i9 % 16 * 8;
			i11 = i9 / 16 * 8;
			float f20 = 7.99F;
			float f21 = 0.0F;
			float f23 = 0.0F;
			tessellator19.addVertexWithUV(0.0D, (double)(0.0F + f20), 0.0D, (double)((float)i10 / 128.0F + f21), (double)(((float)i11 + f20) / 128.0F + f23));
			tessellator19.addVertexWithUV((double)(0.0F + f20), (double)(0.0F + f20), 0.0D, (double)(((float)i10 + f20) / 128.0F + f21), (double)(((float)i11 + f20) / 128.0F + f23));
			tessellator19.addVertexWithUV((double)(0.0F + f20), 0.0D, 0.0D, (double)(((float)i10 + f20) / 128.0F + f21), (double)((float)i11 / 128.0F + f23));
			tessellator19.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)((float)i10 / 128.0F + f21), (double)((float)i11 / 128.0F + f23));
			tessellator19.draw();
			GL11.glTranslatef((float)this.charWidth[i9], 0.0F, 0.0F);
			GL11.glEndList();
		}

		for(i9 = 0; i9 < 32; ++i9) {
			i10 = (i9 >> 3 & 1) * 85;
			i11 = (i9 >> 2 & 1) * 170 + i10;
			i12 = (i9 >> 1 & 1) * 170 + i10;
			int i22 = (i9 >> 0 & 1) * 170 + i10;
			if(i9 == 6) {
				i11 += 85;
			}

			boolean z24 = i9 >= 16;
			if(gameSettings1.anaglyph) {
				i15 = (i11 * 30 + i12 * 59 + i22 * 11) / 100;
				i16 = (i11 * 30 + i12 * 70) / 100;
				int i17 = (i11 * 30 + i22 * 70) / 100;
				i11 = i15;
				i12 = i16;
				i22 = i17;
			}

			if(z24) {
				i11 /= 4;
				i12 /= 4;
				i22 /= 4;
			}

			GL11.glNewList(this.fontDisplayLists + 256 + i9, GL11.GL_COMPILE);
			GL11.glColor3f((float)i11 / 255.0F, (float)i12 / 255.0F, (float)i22 / 255.0F);
			GL11.glEndList();
		}

	}

	public void drawStringWithShadow(String string1, int i2, int i3, int i4) {
		this.renderString(string1, i2 + 1, i3 + 1, i4, true);
		this.drawString(string1, i2, i3, i4);
	}

	public void drawString(String string1, int i2, int i3, int i4) {
		this.renderString(string1, i2, i3, i4, false);
	}

	public void renderString(String string1, int i2, int i3, int i4, boolean z5) {
		if(string1 != null) {
			int i6;
			if(z5) {
				i6 = i4 & 0xFF000000;
				i4 = (i4 & 16579836) >> 2;
				i4 += i6;
			}

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.fontTextureName);
			float f10 = (float)(i4 >> 16 & 255) / 255.0F;
			float f7 = (float)(i4 >> 8 & 255) / 255.0F;
			float f8 = (float)(i4 & 255) / 255.0F;
			float f9 = (float)(i4 >> 24 & 255) / 255.0F;
			if(f9 == 0.0F) {
				f9 = 1.0F;
			}

			GL11.glColor4f(f10, f7, f8, f9);
			this.buffer.clear();
			GL11.glPushMatrix();
			GL11.glTranslatef((float)i2, (float)i3, 0.0F);

			for(i6 = 0; i6 < string1.length(); ++i6) {
				int i11;
				for(; string1.length() > i6 + 1 && string1.charAt(i6) == 167; i6 += 2) {
					i11 = "0123456789abcdef".indexOf(string1.toLowerCase().charAt(i6 + 1));
					if(i11 < 0 || i11 > 15) {
						i11 = 15;
					}

					this.buffer.put(this.fontDisplayLists + 256 + i11 + (z5 ? 16 : 0));
					if(this.buffer.remaining() == 0) {
						this.buffer.flip();
						GL11.glCallLists(this.buffer);
						this.buffer.clear();
					}
				}

				if(i6 < string1.length()) {
					i11 = ChatAllowedCharacters.allowedCharacters.indexOf(string1.charAt(i6));
					if(i11 >= 0) {
						this.buffer.put(this.fontDisplayLists + i11 + 32);
					}
				}

				if(this.buffer.remaining() == 0) {
					this.buffer.flip();
					GL11.glCallLists(this.buffer);
					this.buffer.clear();
				}
			}

			this.buffer.flip();
			GL11.glCallLists(this.buffer);
			GL11.glPopMatrix();
		}
	}

	public int getStringWidth(String string1) {
		if(string1 == null) {
			return 0;
		} else {
			int i2 = 0;

			for(int i3 = 0; i3 < string1.length(); ++i3) {
				if(string1.charAt(i3) == 167) {
					++i3;
				} else {
					int i4 = ChatAllowedCharacters.allowedCharacters.indexOf(string1.charAt(i3));
					if(i4 >= 0) {
						i2 += this.charWidth[i4 + 32];
					}
				}
			}

			return i2;
		}
	}

	public void func_27278_a(String string1, int i2, int i3, int i4, int i5) {
		String[] string6 = string1.split("\n");
		if(string6.length > 1) {
			for(int i11 = 0; i11 < string6.length; ++i11) {
				this.func_27278_a(string6[i11], i2, i3, i4, i5);
				i3 += this.func_27277_a(string6[i11], i4);
			}

		} else {
			String[] string7 = string1.split(" ");
			int i8 = 0;

			while(i8 < string7.length) {
				String string9;
				for(string9 = string7[i8++] + " "; i8 < string7.length && this.getStringWidth(string9 + string7[i8]) < i4; string9 = string9 + string7[i8++] + " ") {
				}

				int i10;
				for(; this.getStringWidth(string9) > i4; string9 = string9.substring(i10)) {
					for(i10 = 0; this.getStringWidth(string9.substring(0, i10 + 1)) <= i4; ++i10) {
					}

					if(string9.substring(0, i10).trim().length() > 0) {
						this.drawString(string9.substring(0, i10), i2, i3, i5);
						i3 += 8;
					}
				}

				if(string9.trim().length() > 0) {
					this.drawString(string9, i2, i3, i5);
					i3 += 8;
				}
			}

		}
	}

	public int func_27277_a(String string1, int i2) {
		String[] string3 = string1.split("\n");
		int i5;
		if(string3.length > 1) {
			int i9 = 0;

			for(i5 = 0; i5 < string3.length; ++i5) {
				i9 += this.func_27277_a(string3[i5], i2);
			}

			return i9;
		} else {
			String[] string4 = string1.split(" ");
			i5 = 0;
			int i6 = 0;

			while(i5 < string4.length) {
				String string7;
				for(string7 = string4[i5++] + " "; i5 < string4.length && this.getStringWidth(string7 + string4[i5]) < i2; string7 = string7 + string4[i5++] + " ") {
				}

				int i8;
				for(; this.getStringWidth(string7) > i2; string7 = string7.substring(i8)) {
					for(i8 = 0; this.getStringWidth(string7.substring(0, i8 + 1)) <= i2; ++i8) {
					}

					if(string7.substring(0, i8).trim().length() > 0) {
						i6 += 8;
					}
				}

				if(string7.trim().length() > 0) {
					i6 += 8;
				}
			}

			if(i6 < 8) {
				i6 += 8;
			}

			return i6;
		}
	}
}
