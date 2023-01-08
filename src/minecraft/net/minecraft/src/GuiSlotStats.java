package net.minecraft.src;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.input.Mouse;

abstract class GuiSlotStats extends GuiSlot {
	protected int field_27268_b;
	protected List field_27273_c;
	protected Comparator field_27272_d;
	protected int field_27271_e;
	protected int field_27270_f;
	final GuiStats field_27269_g;

	protected GuiSlotStats(GuiStats guiStats1) {
		super(GuiStats.func_27143_f(guiStats1), guiStats1.width, guiStats1.height, 32, guiStats1.height - 64, 20);
		this.field_27269_g = guiStats1;
		this.field_27268_b = -1;
		this.field_27271_e = -1;
		this.field_27270_f = 0;
		this.func_27258_a(false);
		this.func_27259_a(true, 20);
	}

	protected void elementClicked(int i1, boolean z2) {
	}

	protected boolean isSelected(int i1) {
		return false;
	}

	protected void drawBackground() {
		this.field_27269_g.drawDefaultBackground();
	}

	protected void func_27260_a(int i1, int i2, Tessellator tessellator3) {
		if(!Mouse.isButtonDown(0)) {
			this.field_27268_b = -1;
		}

		if(this.field_27268_b == 0) {
			GuiStats.func_27128_a(this.field_27269_g, i1 + 115 - 18, i2 + 1, 0, 0);
		} else {
			GuiStats.func_27128_a(this.field_27269_g, i1 + 115 - 18, i2 + 1, 0, 18);
		}

		if(this.field_27268_b == 1) {
			GuiStats.func_27128_a(this.field_27269_g, i1 + 165 - 18, i2 + 1, 0, 0);
		} else {
			GuiStats.func_27128_a(this.field_27269_g, i1 + 165 - 18, i2 + 1, 0, 18);
		}

		if(this.field_27268_b == 2) {
			GuiStats.func_27128_a(this.field_27269_g, i1 + 215 - 18, i2 + 1, 0, 0);
		} else {
			GuiStats.func_27128_a(this.field_27269_g, i1 + 215 - 18, i2 + 1, 0, 18);
		}

		if(this.field_27271_e != -1) {
			short s4 = 79;
			byte b5 = 18;
			if(this.field_27271_e == 1) {
				s4 = 129;
			} else if(this.field_27271_e == 2) {
				s4 = 179;
			}

			if(this.field_27270_f == 1) {
				b5 = 36;
			}

			GuiStats.func_27128_a(this.field_27269_g, i1 + s4, i2 + 1, b5, 0);
		}

	}

	protected void func_27255_a(int i1, int i2) {
		this.field_27268_b = -1;
		if(i1 >= 79 && i1 < 115) {
			this.field_27268_b = 0;
		} else if(i1 >= 129 && i1 < 165) {
			this.field_27268_b = 1;
		} else if(i1 >= 179 && i1 < 215) {
			this.field_27268_b = 2;
		}

		if(this.field_27268_b >= 0) {
			this.func_27266_c(this.field_27268_b);
			GuiStats.func_27149_g(this.field_27269_g).sndManager.playSoundFX("random.click", 1.0F, 1.0F);
		}

	}

	protected final int getSize() {
		return this.field_27273_c.size();
	}

	protected final StatCrafting func_27264_b(int i1) {
		return (StatCrafting)this.field_27273_c.get(i1);
	}

	protected abstract String func_27263_a(int i1);

	protected void func_27265_a(StatCrafting statCrafting1, int i2, int i3, boolean z4) {
		String string5;
		if(statCrafting1 != null) {
			string5 = statCrafting1.func_27084_a(GuiStats.func_27142_c(this.field_27269_g).writeStat(statCrafting1));
			this.field_27269_g.drawString(GuiStats.func_27133_h(this.field_27269_g), string5, i2 - GuiStats.func_27137_i(this.field_27269_g).getStringWidth(string5), i3 + 5, z4 ? 0xFFFFFF : 9474192);
		} else {
			string5 = "-";
			this.field_27269_g.drawString(GuiStats.func_27132_j(this.field_27269_g), string5, i2 - GuiStats.func_27134_k(this.field_27269_g).getStringWidth(string5), i3 + 5, z4 ? 0xFFFFFF : 9474192);
		}

	}

	protected void func_27257_b(int i1, int i2) {
		if(i2 >= this.top && i2 <= this.bottom) {
			int i3 = this.func_27256_c(i1, i2);
			int i4 = this.field_27269_g.width / 2 - 92 - 16;
			if(i3 >= 0) {
				if(i1 < i4 + 40 || i1 > i4 + 40 + 20) {
					return;
				}

				StatCrafting statCrafting9 = this.func_27264_b(i3);
				this.func_27267_a(statCrafting9, i1, i2);
			} else {
				String string5 = "";
				if(i1 >= i4 + 115 - 18 && i1 <= i4 + 115) {
					string5 = this.func_27263_a(0);
				} else if(i1 >= i4 + 165 - 18 && i1 <= i4 + 165) {
					string5 = this.func_27263_a(1);
				} else {
					if(i1 < i4 + 215 - 18 || i1 > i4 + 215) {
						return;
					}

					string5 = this.func_27263_a(2);
				}

				string5 = ("" + StringTranslate.getInstance().translateKey(string5)).trim();
				if(string5.length() > 0) {
					int i6 = i1 + 12;
					int i7 = i2 - 12;
					int i8 = GuiStats.func_27139_l(this.field_27269_g).getStringWidth(string5);
					GuiStats.func_27129_a(this.field_27269_g, i6 - 3, i7 - 3, i6 + i8 + 3, i7 + 8 + 3, -1073741824, -1073741824);
					GuiStats.func_27144_m(this.field_27269_g).drawStringWithShadow(string5, i6, i7, -1);
				}
			}

		}
	}

	protected void func_27267_a(StatCrafting statCrafting1, int i2, int i3) {
		if(statCrafting1 != null) {
			Item item4 = Item.itemsList[statCrafting1.func_25072_b()];
			String string5 = ("" + StringTranslate.getInstance().translateNamedKey(item4.getItemName())).trim();
			if(string5.length() > 0) {
				int i6 = i2 + 12;
				int i7 = i3 - 12;
				int i8 = GuiStats.func_27127_n(this.field_27269_g).getStringWidth(string5);
				GuiStats.func_27135_b(this.field_27269_g, i6 - 3, i7 - 3, i6 + i8 + 3, i7 + 8 + 3, -1073741824, -1073741824);
				GuiStats.func_27131_o(this.field_27269_g).drawStringWithShadow(string5, i6, i7, -1);
			}

		}
	}

	protected void func_27266_c(int i1) {
		if(i1 != this.field_27271_e) {
			this.field_27271_e = i1;
			this.field_27270_f = -1;
		} else if(this.field_27270_f == -1) {
			this.field_27270_f = 1;
		} else {
			this.field_27271_e = -1;
			this.field_27270_f = 0;
		}

		Collections.sort(this.field_27273_c, this.field_27272_d);
	}
}
