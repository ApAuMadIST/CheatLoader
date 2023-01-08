package net.minecraft.src;

import java.util.Comparator;

class SorterStatsBlock implements Comparator {
	final GuiStats field_27299_a;
	final GuiSlotStatsBlock field_27298_b;

	SorterStatsBlock(GuiSlotStatsBlock guiSlotStatsBlock1, GuiStats guiStats2) {
		this.field_27298_b = guiSlotStatsBlock1;
		this.field_27299_a = guiStats2;
	}

	public int func_27297_a(StatCrafting statCrafting1, StatCrafting statCrafting2) {
		int i3 = statCrafting1.func_25072_b();
		int i4 = statCrafting2.func_25072_b();
		StatBase statBase5 = null;
		StatBase statBase6 = null;
		if(this.field_27298_b.field_27271_e == 2) {
			statBase5 = StatList.mineBlockStatArray[i3];
			statBase6 = StatList.mineBlockStatArray[i4];
		} else if(this.field_27298_b.field_27271_e == 0) {
			statBase5 = StatList.field_25158_z[i3];
			statBase6 = StatList.field_25158_z[i4];
		} else if(this.field_27298_b.field_27271_e == 1) {
			statBase5 = StatList.field_25172_A[i3];
			statBase6 = StatList.field_25172_A[i4];
		}

		if(statBase5 != null || statBase6 != null) {
			if(statBase5 == null) {
				return 1;
			}

			if(statBase6 == null) {
				return -1;
			}

			int i7 = GuiStats.func_27142_c(this.field_27298_b.field_27274_a).writeStat(statBase5);
			int i8 = GuiStats.func_27142_c(this.field_27298_b.field_27274_a).writeStat(statBase6);
			if(i7 != i8) {
				return (i7 - i8) * this.field_27298_b.field_27270_f;
			}
		}

		return i3 - i4;
	}

	public int compare(Object object1, Object object2) {
		return this.func_27297_a((StatCrafting)object1, (StatCrafting)object2);
	}
}
