package net.minecraft.src;

import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiAchievements extends GuiScreen {
	private static final int field_27126_s = AchievementList.minDisplayColumn * 24 - 112;
	private static final int field_27125_t = AchievementList.minDisplayRow * 24 - 112;
	private static final int field_27124_u = AchievementList.maxDisplayColumn * 24 - 77;
	private static final int field_27123_v = AchievementList.maxDisplayRow * 24 - 77;
	protected int field_27121_a = 256;
	protected int field_27119_i = 202;
	protected int field_27118_j = 0;
	protected int field_27117_l = 0;
	protected double field_27116_m;
	protected double field_27115_n;
	protected double field_27114_o;
	protected double field_27113_p;
	protected double field_27112_q;
	protected double field_27111_r;
	private int field_27122_w = 0;
	private StatFileWriter field_27120_x;

	public GuiAchievements(StatFileWriter statFileWriter1) {
		this.field_27120_x = statFileWriter1;
		short s2 = 141;
		short s3 = 141;
		this.field_27116_m = this.field_27114_o = this.field_27112_q = (double)(AchievementList.openInventory.displayColumn * 24 - s2 / 2 - 12);
		this.field_27115_n = this.field_27113_p = this.field_27111_r = (double)(AchievementList.openInventory.displayRow * 24 - s3 / 2);
	}

	public void initGui() {
		this.controlList.clear();
		this.controlList.add(new GuiSmallButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, StatCollector.translateToLocal("gui.done")));
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.id == 1) {
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
		}

		super.actionPerformed(guiButton1);
	}

	protected void keyTyped(char c1, int i2) {
		if(i2 == this.mc.gameSettings.keyBindInventory.keyCode) {
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
		} else {
			super.keyTyped(c1, i2);
		}

	}

	public void drawScreen(int i1, int i2, float f3) {
		if(Mouse.isButtonDown(0)) {
			int i4 = (this.width - this.field_27121_a) / 2;
			int i5 = (this.height - this.field_27119_i) / 2;
			int i6 = i4 + 8;
			int i7 = i5 + 17;
			if((this.field_27122_w == 0 || this.field_27122_w == 1) && i1 >= i6 && i1 < i6 + 224 && i2 >= i7 && i2 < i7 + 155) {
				if(this.field_27122_w == 0) {
					this.field_27122_w = 1;
				} else {
					this.field_27114_o -= (double)(i1 - this.field_27118_j);
					this.field_27113_p -= (double)(i2 - this.field_27117_l);
					this.field_27112_q = this.field_27116_m = this.field_27114_o;
					this.field_27111_r = this.field_27115_n = this.field_27113_p;
				}

				this.field_27118_j = i1;
				this.field_27117_l = i2;
			}

			if(this.field_27112_q < (double)field_27126_s) {
				this.field_27112_q = (double)field_27126_s;
			}

			if(this.field_27111_r < (double)field_27125_t) {
				this.field_27111_r = (double)field_27125_t;
			}

			if(this.field_27112_q >= (double)field_27124_u) {
				this.field_27112_q = (double)(field_27124_u - 1);
			}

			if(this.field_27111_r >= (double)field_27123_v) {
				this.field_27111_r = (double)(field_27123_v - 1);
			}
		} else {
			this.field_27122_w = 0;
		}

		this.drawDefaultBackground();
		this.func_27109_b(i1, i2, f3);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		this.func_27110_k();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}

	public void updateScreen() {
		this.field_27116_m = this.field_27114_o;
		this.field_27115_n = this.field_27113_p;
		double d1 = this.field_27112_q - this.field_27114_o;
		double d3 = this.field_27111_r - this.field_27113_p;
		if(d1 * d1 + d3 * d3 < 4.0D) {
			this.field_27114_o += d1;
			this.field_27113_p += d3;
		} else {
			this.field_27114_o += d1 * 0.85D;
			this.field_27113_p += d3 * 0.85D;
		}

	}

	protected void func_27110_k() {
		int i1 = (this.width - this.field_27121_a) / 2;
		int i2 = (this.height - this.field_27119_i) / 2;
		this.fontRenderer.drawString("Achievements", i1 + 15, i2 + 5, 4210752);
	}

	protected void func_27109_b(int i1, int i2, float f3) {
		int i4 = MathHelper.floor_double(this.field_27116_m + (this.field_27114_o - this.field_27116_m) * (double)f3);
		int i5 = MathHelper.floor_double(this.field_27115_n + (this.field_27113_p - this.field_27115_n) * (double)f3);
		if(i4 < field_27126_s) {
			i4 = field_27126_s;
		}

		if(i5 < field_27125_t) {
			i5 = field_27125_t;
		}

		if(i4 >= field_27124_u) {
			i4 = field_27124_u - 1;
		}

		if(i5 >= field_27123_v) {
			i5 = field_27123_v - 1;
		}

		int i6 = this.mc.renderEngine.getTexture("/terrain.png");
		int i7 = this.mc.renderEngine.getTexture("/achievement/bg.png");
		int i8 = (this.width - this.field_27121_a) / 2;
		int i9 = (this.height - this.field_27119_i) / 2;
		int i10 = i8 + 16;
		int i11 = i9 + 17;
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_GEQUAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.0F, -200.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		this.mc.renderEngine.bindTexture(i6);
		int i12 = i4 + 288 >> 4;
		int i13 = i5 + 288 >> 4;
		int i14 = (i4 + 288) % 16;
		int i15 = (i5 + 288) % 16;
		Random random21 = new Random();

		for(int i22 = 0; i22 * 16 - i15 < 155; ++i22) {
			float f23 = 0.6F - (float)(i13 + i22) / 25.0F * 0.3F;
			GL11.glColor4f(f23, f23, f23, 1.0F);

			for(int i24 = 0; i24 * 16 - i14 < 224; ++i24) {
				random21.setSeed((long)(1234 + i12 + i24));
				random21.nextInt();
				int i25 = random21.nextInt(1 + i13 + i22) + (i13 + i22) / 2;
				int i26 = Block.sand.blockIndexInTexture;
				if(i25 <= 37 && i13 + i22 != 35) {
					if(i25 == 22) {
						if(random21.nextInt(2) == 0) {
							i26 = Block.oreDiamond.blockIndexInTexture;
						} else {
							i26 = Block.oreRedstone.blockIndexInTexture;
						}
					} else if(i25 == 10) {
						i26 = Block.oreIron.blockIndexInTexture;
					} else if(i25 == 8) {
						i26 = Block.oreCoal.blockIndexInTexture;
					} else if(i25 > 4) {
						i26 = Block.stone.blockIndexInTexture;
					} else if(i25 > 0) {
						i26 = Block.dirt.blockIndexInTexture;
					}
				} else {
					i26 = Block.bedrock.blockIndexInTexture;
				}

				this.drawTexturedModalRect(i10 + i24 * 16 - i14, i11 + i22 * 16 - i15, i26 % 16 << 4, i26 >> 4 << 4, 16, 16);
			}
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_TEXTURE_2D);

		int i16;
		int i17;
		int i33;
		int i38;
		for(i12 = 0; i12 < AchievementList.achievementList.size(); ++i12) {
			Achievement achievement28 = (Achievement)AchievementList.achievementList.get(i12);
			if(achievement28.parentAchievement != null) {
				i14 = achievement28.displayColumn * 24 - i4 + 11 + i10;
				i15 = achievement28.displayRow * 24 - i5 + 11 + i11;
				i16 = achievement28.parentAchievement.displayColumn * 24 - i4 + 11 + i10;
				i17 = achievement28.parentAchievement.displayRow * 24 - i5 + 11 + i11;
				boolean z18 = false;
				boolean z19 = this.field_27120_x.hasAchievementUnlocked(achievement28);
				boolean z20 = this.field_27120_x.func_27181_b(achievement28);
				i38 = Math.sin((double)(System.currentTimeMillis() % 600L) / 600.0D * Math.PI * 2.0D) > 0.6D ? 255 : 130;
				if(z19) {
					i33 = -9408400;
				} else if(z20) {
					i33 = 65280 + (i38 << 24);
				} else {
					i33 = 0xFF000000;
				}

				this.func_27100_a(i14, i16, i15, i33);
				this.func_27099_b(i16, i15, i17, i33);
			}
		}

		Achievement achievement27 = null;
		RenderItem renderItem29 = new RenderItem();
		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);

		int i34;
		for(i14 = 0; i14 < AchievementList.achievementList.size(); ++i14) {
			Achievement achievement30 = (Achievement)AchievementList.achievementList.get(i14);
			i16 = achievement30.displayColumn * 24 - i4;
			i17 = achievement30.displayRow * 24 - i5;
			if(i16 >= -24 && i17 >= -24 && i16 <= 224 && i17 <= 155) {
				float f35;
				if(this.field_27120_x.hasAchievementUnlocked(achievement30)) {
					f35 = 1.0F;
					GL11.glColor4f(f35, f35, f35, 1.0F);
				} else if(this.field_27120_x.func_27181_b(achievement30)) {
					f35 = Math.sin((double)(System.currentTimeMillis() % 600L) / 600.0D * Math.PI * 2.0D) < 0.6D ? 0.6F : 0.8F;
					GL11.glColor4f(f35, f35, f35, 1.0F);
				} else {
					f35 = 0.3F;
					GL11.glColor4f(f35, f35, f35, 1.0F);
				}

				this.mc.renderEngine.bindTexture(i7);
				i33 = i10 + i16;
				i34 = i11 + i17;
				if(achievement30.getSpecial()) {
					this.drawTexturedModalRect(i33 - 2, i34 - 2, 26, 202, 26, 26);
				} else {
					this.drawTexturedModalRect(i33 - 2, i34 - 2, 0, 202, 26, 26);
				}

				if(!this.field_27120_x.func_27181_b(achievement30)) {
					float f36 = 0.1F;
					GL11.glColor4f(f36, f36, f36, 1.0F);
					renderItem29.field_27004_a = false;
				}

				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_CULL_FACE);
				renderItem29.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, achievement30.theItemStack, i33 + 3, i34 + 3);
				GL11.glDisable(GL11.GL_LIGHTING);
				if(!this.field_27120_x.func_27181_b(achievement30)) {
					renderItem29.field_27004_a = true;
				}

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				if(i1 >= i10 && i2 >= i11 && i1 < i10 + 224 && i2 < i11 + 155 && i1 >= i33 && i1 <= i33 + 22 && i2 >= i34 && i2 <= i34 + 22) {
					achievement27 = achievement30;
				}
			}
		}

		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(i7);
		this.drawTexturedModalRect(i8, i9, 0, 0, this.field_27121_a, this.field_27119_i);
		GL11.glPopMatrix();
		this.zLevel = 0.0F;
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		super.drawScreen(i1, i2, f3);
		if(achievement27 != null) {
			String string31 = achievement27.statName;
			String string32 = achievement27.getDescription();
			i17 = i1 + 12;
			i33 = i2 - 4;
			if(this.field_27120_x.func_27181_b(achievement27)) {
				i34 = Math.max(this.fontRenderer.getStringWidth(string31), 120);
				int i37 = this.fontRenderer.func_27277_a(string32, i34);
				if(this.field_27120_x.hasAchievementUnlocked(achievement27)) {
					i37 += 12;
				}

				this.drawGradientRect(i17 - 3, i33 - 3, i17 + i34 + 3, i33 + i37 + 3 + 12, -1073741824, -1073741824);
				this.fontRenderer.func_27278_a(string32, i17, i33 + 12, i34, -6250336);
				if(this.field_27120_x.hasAchievementUnlocked(achievement27)) {
					this.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("achievement.taken"), i17, i33 + i37 + 4, -7302913);
				}
			} else {
				i34 = Math.max(this.fontRenderer.getStringWidth(string31), 120);
				String string39 = StatCollector.translateToLocalFormatted("achievement.requires", new Object[]{achievement27.parentAchievement.statName});
				i38 = this.fontRenderer.func_27277_a(string39, i34);
				this.drawGradientRect(i17 - 3, i33 - 3, i17 + i34 + 3, i33 + i38 + 12 + 3, -1073741824, -1073741824);
				this.fontRenderer.func_27278_a(string39, i17, i33 + 12, i34, -9416624);
			}

			this.fontRenderer.drawStringWithShadow(string31, i17, i33, this.field_27120_x.func_27181_b(achievement27) ? (achievement27.getSpecial() ? -128 : -1) : (achievement27.getSpecial() ? -8355776 : -8355712));
		}

		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.disableStandardItemLighting();
	}

	public boolean doesGuiPauseGame() {
		return true;
	}
}
