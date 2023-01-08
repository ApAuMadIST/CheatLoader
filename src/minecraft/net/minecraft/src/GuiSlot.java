package net.minecraft.src;

import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiSlot {
	private final Minecraft mc;
	private final int width;
	private final int height;
	protected final int top;
	protected final int bottom;
	private final int right;
	private final int left;
	protected final int posZ;
	private int scrollUpButtonID;
	private int scrollDownButtonID;
	private float initialClickY = -2.0F;
	private float scrollMultiplier;
	private float amountScrolled;
	private int selectedElement = -1;
	private long lastClicked = 0L;
	private boolean field_25123_p = true;
	private boolean field_27262_q;
	private int field_27261_r;

	public GuiSlot(Minecraft minecraft1, int i2, int i3, int i4, int i5, int i6) {
		this.mc = minecraft1;
		this.width = i2;
		this.height = i3;
		this.top = i4;
		this.bottom = i5;
		this.posZ = i6;
		this.left = 0;
		this.right = i2;
	}

	public void func_27258_a(boolean z1) {
		this.field_25123_p = z1;
	}

	protected void func_27259_a(boolean z1, int i2) {
		this.field_27262_q = z1;
		this.field_27261_r = i2;
		if(!z1) {
			this.field_27261_r = 0;
		}

	}

	protected abstract int getSize();

	protected abstract void elementClicked(int i1, boolean z2);

	protected abstract boolean isSelected(int i1);

	protected int getContentHeight() {
		return this.getSize() * this.posZ + this.field_27261_r;
	}

	protected abstract void drawBackground();

	protected abstract void drawSlot(int i1, int i2, int i3, int i4, Tessellator tessellator5);

	protected void func_27260_a(int i1, int i2, Tessellator tessellator3) {
	}

	protected void func_27255_a(int i1, int i2) {
	}

	protected void func_27257_b(int i1, int i2) {
	}

	public int func_27256_c(int i1, int i2) {
		int i3 = this.width / 2 - 110;
		int i4 = this.width / 2 + 110;
		int i5 = i2 - this.top - this.field_27261_r + (int)this.amountScrolled - 4;
		int i6 = i5 / this.posZ;
		return i1 >= i3 && i1 <= i4 && i6 >= 0 && i5 >= 0 && i6 < this.getSize() ? i6 : -1;
	}

	public void registerScrollButtons(List list1, int i2, int i3) {
		this.scrollUpButtonID = i2;
		this.scrollDownButtonID = i3;
	}

	private void bindAmountScrolled() {
		int i1 = this.getContentHeight() - (this.bottom - this.top - 4);
		if(i1 < 0) {
			i1 /= 2;
		}

		if(this.amountScrolled < 0.0F) {
			this.amountScrolled = 0.0F;
		}

		if(this.amountScrolled > (float)i1) {
			this.amountScrolled = (float)i1;
		}

	}

	public void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.enabled) {
			if(guiButton1.id == this.scrollUpButtonID) {
				this.amountScrolled -= (float)(this.posZ * 2 / 3);
				this.initialClickY = -2.0F;
				this.bindAmountScrolled();
			} else if(guiButton1.id == this.scrollDownButtonID) {
				this.amountScrolled += (float)(this.posZ * 2 / 3);
				this.initialClickY = -2.0F;
				this.bindAmountScrolled();
			}

		}
	}

	public void drawScreen(int i1, int i2, float f3) {
		this.drawBackground();
		int i4 = this.getSize();
		int i5 = this.width / 2 + 124;
		int i6 = i5 + 6;
		int i9;
		int i10;
		int i11;
		int i13;
		int i19;
		if(Mouse.isButtonDown(0)) {
			if(this.initialClickY == -1.0F) {
				boolean z7 = true;
				if(i2 >= this.top && i2 <= this.bottom) {
					int i8 = this.width / 2 - 110;
					i9 = this.width / 2 + 110;
					i10 = i2 - this.top - this.field_27261_r + (int)this.amountScrolled - 4;
					i11 = i10 / this.posZ;
					if(i1 >= i8 && i1 <= i9 && i11 >= 0 && i10 >= 0 && i11 < i4) {
						boolean z12 = i11 == this.selectedElement && System.currentTimeMillis() - this.lastClicked < 250L;
						this.elementClicked(i11, z12);
						this.selectedElement = i11;
						this.lastClicked = System.currentTimeMillis();
					} else if(i1 >= i8 && i1 <= i9 && i10 < 0) {
						this.func_27255_a(i1 - i8, i2 - this.top + (int)this.amountScrolled - 4);
						z7 = false;
					}

					if(i1 >= i5 && i1 <= i6) {
						this.scrollMultiplier = -1.0F;
						i19 = this.getContentHeight() - (this.bottom - this.top - 4);
						if(i19 < 1) {
							i19 = 1;
						}

						i13 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getContentHeight());
						if(i13 < 32) {
							i13 = 32;
						}

						if(i13 > this.bottom - this.top - 8) {
							i13 = this.bottom - this.top - 8;
						}

						this.scrollMultiplier /= (float)(this.bottom - this.top - i13) / (float)i19;
					} else {
						this.scrollMultiplier = 1.0F;
					}

					if(z7) {
						this.initialClickY = (float)i2;
					} else {
						this.initialClickY = -2.0F;
					}
				} else {
					this.initialClickY = -2.0F;
				}
			} else if(this.initialClickY >= 0.0F) {
				this.amountScrolled -= ((float)i2 - this.initialClickY) * this.scrollMultiplier;
				this.initialClickY = (float)i2;
			}
		} else {
			this.initialClickY = -1.0F;
		}

		this.bindAmountScrolled();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator tessellator16 = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/background.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f17 = 32.0F;
		tessellator16.startDrawingQuads();
		tessellator16.setColorOpaque_I(2105376);
		tessellator16.addVertexWithUV((double)this.left, (double)this.bottom, 0.0D, (double)((float)this.left / f17), (double)((float)(this.bottom + (int)this.amountScrolled) / f17));
		tessellator16.addVertexWithUV((double)this.right, (double)this.bottom, 0.0D, (double)((float)this.right / f17), (double)((float)(this.bottom + (int)this.amountScrolled) / f17));
		tessellator16.addVertexWithUV((double)this.right, (double)this.top, 0.0D, (double)((float)this.right / f17), (double)((float)(this.top + (int)this.amountScrolled) / f17));
		tessellator16.addVertexWithUV((double)this.left, (double)this.top, 0.0D, (double)((float)this.left / f17), (double)((float)(this.top + (int)this.amountScrolled) / f17));
		tessellator16.draw();
		i9 = this.width / 2 - 92 - 16;
		i10 = this.top + 4 - (int)this.amountScrolled;
		if(this.field_27262_q) {
			this.func_27260_a(i9, i10, tessellator16);
		}

		int i14;
		for(i11 = 0; i11 < i4; ++i11) {
			i19 = i10 + i11 * this.posZ + this.field_27261_r;
			i13 = this.posZ - 4;
			if(i19 <= this.bottom && i19 + i13 >= this.top) {
				if(this.field_25123_p && this.isSelected(i11)) {
					i14 = this.width / 2 - 110;
					int i15 = this.width / 2 + 110;
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					tessellator16.startDrawingQuads();
					tessellator16.setColorOpaque_I(8421504);
					tessellator16.addVertexWithUV((double)i14, (double)(i19 + i13 + 2), 0.0D, 0.0D, 1.0D);
					tessellator16.addVertexWithUV((double)i15, (double)(i19 + i13 + 2), 0.0D, 1.0D, 1.0D);
					tessellator16.addVertexWithUV((double)i15, (double)(i19 - 2), 0.0D, 1.0D, 0.0D);
					tessellator16.addVertexWithUV((double)i14, (double)(i19 - 2), 0.0D, 0.0D, 0.0D);
					tessellator16.setColorOpaque_I(0);
					tessellator16.addVertexWithUV((double)(i14 + 1), (double)(i19 + i13 + 1), 0.0D, 0.0D, 1.0D);
					tessellator16.addVertexWithUV((double)(i15 - 1), (double)(i19 + i13 + 1), 0.0D, 1.0D, 1.0D);
					tessellator16.addVertexWithUV((double)(i15 - 1), (double)(i19 - 1), 0.0D, 1.0D, 0.0D);
					tessellator16.addVertexWithUV((double)(i14 + 1), (double)(i19 - 1), 0.0D, 0.0D, 0.0D);
					tessellator16.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}

				this.drawSlot(i11, i9, i19, i13, tessellator16);
			}
		}

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		byte b18 = 4;
		this.overlayBackground(0, this.top, 255, 255);
		this.overlayBackground(this.bottom, this.height, 255, 255);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		tessellator16.startDrawingQuads();
		tessellator16.setColorRGBA_I(0, 0);
		tessellator16.addVertexWithUV((double)this.left, (double)(this.top + b18), 0.0D, 0.0D, 1.0D);
		tessellator16.addVertexWithUV((double)this.right, (double)(this.top + b18), 0.0D, 1.0D, 1.0D);
		tessellator16.setColorRGBA_I(0, 255);
		tessellator16.addVertexWithUV((double)this.right, (double)this.top, 0.0D, 1.0D, 0.0D);
		tessellator16.addVertexWithUV((double)this.left, (double)this.top, 0.0D, 0.0D, 0.0D);
		tessellator16.draw();
		tessellator16.startDrawingQuads();
		tessellator16.setColorRGBA_I(0, 255);
		tessellator16.addVertexWithUV((double)this.left, (double)this.bottom, 0.0D, 0.0D, 1.0D);
		tessellator16.addVertexWithUV((double)this.right, (double)this.bottom, 0.0D, 1.0D, 1.0D);
		tessellator16.setColorRGBA_I(0, 0);
		tessellator16.addVertexWithUV((double)this.right, (double)(this.bottom - b18), 0.0D, 1.0D, 0.0D);
		tessellator16.addVertexWithUV((double)this.left, (double)(this.bottom - b18), 0.0D, 0.0D, 0.0D);
		tessellator16.draw();
		i19 = this.getContentHeight() - (this.bottom - this.top - 4);
		if(i19 > 0) {
			i13 = (this.bottom - this.top) * (this.bottom - this.top) / this.getContentHeight();
			if(i13 < 32) {
				i13 = 32;
			}

			if(i13 > this.bottom - this.top - 8) {
				i13 = this.bottom - this.top - 8;
			}

			i14 = (int)this.amountScrolled * (this.bottom - this.top - i13) / i19 + this.top;
			if(i14 < this.top) {
				i14 = this.top;
			}

			tessellator16.startDrawingQuads();
			tessellator16.setColorRGBA_I(0, 255);
			tessellator16.addVertexWithUV((double)i5, (double)this.bottom, 0.0D, 0.0D, 1.0D);
			tessellator16.addVertexWithUV((double)i6, (double)this.bottom, 0.0D, 1.0D, 1.0D);
			tessellator16.addVertexWithUV((double)i6, (double)this.top, 0.0D, 1.0D, 0.0D);
			tessellator16.addVertexWithUV((double)i5, (double)this.top, 0.0D, 0.0D, 0.0D);
			tessellator16.draw();
			tessellator16.startDrawingQuads();
			tessellator16.setColorRGBA_I(8421504, 255);
			tessellator16.addVertexWithUV((double)i5, (double)(i14 + i13), 0.0D, 0.0D, 1.0D);
			tessellator16.addVertexWithUV((double)i6, (double)(i14 + i13), 0.0D, 1.0D, 1.0D);
			tessellator16.addVertexWithUV((double)i6, (double)i14, 0.0D, 1.0D, 0.0D);
			tessellator16.addVertexWithUV((double)i5, (double)i14, 0.0D, 0.0D, 0.0D);
			tessellator16.draw();
			tessellator16.startDrawingQuads();
			tessellator16.setColorRGBA_I(12632256, 255);
			tessellator16.addVertexWithUV((double)i5, (double)(i14 + i13 - 1), 0.0D, 0.0D, 1.0D);
			tessellator16.addVertexWithUV((double)(i6 - 1), (double)(i14 + i13 - 1), 0.0D, 1.0D, 1.0D);
			tessellator16.addVertexWithUV((double)(i6 - 1), (double)i14, 0.0D, 1.0D, 0.0D);
			tessellator16.addVertexWithUV((double)i5, (double)i14, 0.0D, 0.0D, 0.0D);
			tessellator16.draw();
		}

		this.func_27257_b(i1, i2);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private void overlayBackground(int i1, int i2, int i3, int i4) {
		Tessellator tessellator5 = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/background.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f6 = 32.0F;
		tessellator5.startDrawingQuads();
		tessellator5.setColorRGBA_I(4210752, i4);
		tessellator5.addVertexWithUV(0.0D, (double)i2, 0.0D, 0.0D, (double)((float)i2 / f6));
		tessellator5.addVertexWithUV((double)this.width, (double)i2, 0.0D, (double)((float)this.width / f6), (double)((float)i2 / f6));
		tessellator5.setColorRGBA_I(4210752, i3);
		tessellator5.addVertexWithUV((double)this.width, (double)i1, 0.0D, (double)((float)this.width / f6), (double)((float)i1 / f6));
		tessellator5.addVertexWithUV(0.0D, (double)i1, 0.0D, 0.0D, (double)((float)i1 / f6));
		tessellator5.draw();
	}
}
