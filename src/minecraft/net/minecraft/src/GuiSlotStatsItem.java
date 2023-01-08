package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;

class GuiSlotStatsItem extends GuiSlotStats {
	final GuiStats field_27275_a;

	public GuiSlotStatsItem(GuiStats guiStats1) {
		super(guiStats1);
		this.field_27275_a = guiStats1;
		this.field_27273_c = new ArrayList();
		Iterator iterator2 = StatList.field_25186_c.iterator();

		while(iterator2.hasNext()) {
			StatCrafting statCrafting3 = (StatCrafting)iterator2.next();
			boolean z4 = false;
			int i5 = statCrafting3.func_25072_b();
			if(GuiStats.func_27142_c(guiStats1).writeStat(statCrafting3) > 0) {
				z4 = true;
			} else if(StatList.field_25170_B[i5] != null && GuiStats.func_27142_c(guiStats1).writeStat(StatList.field_25170_B[i5]) > 0) {
				z4 = true;
			} else if(StatList.field_25158_z[i5] != null && GuiStats.func_27142_c(guiStats1).writeStat(StatList.field_25158_z[i5]) > 0) {
				z4 = true;
			}

			if(z4) {
				this.field_27273_c.add(statCrafting3);
			}
		}

		this.field_27272_d = new SorterStatsItem(this, guiStats1);
	}

	protected void func_27260_a(int i1, int i2, Tessellator tessellator3) {
		super.func_27260_a(i1, i2, tessellator3);
		if(this.field_27268_b == 0) {
			GuiStats.func_27128_a(this.field_27275_a, i1 + 115 - 18 + 1, i2 + 1 + 1, 72, 18);
		} else {
			GuiStats.func_27128_a(this.field_27275_a, i1 + 115 - 18, i2 + 1, 72, 18);
		}

		if(this.field_27268_b == 1) {
			GuiStats.func_27128_a(this.field_27275_a, i1 + 165 - 18 + 1, i2 + 1 + 1, 18, 18);
		} else {
			GuiStats.func_27128_a(this.field_27275_a, i1 + 165 - 18, i2 + 1, 18, 18);
		}

		if(this.field_27268_b == 2) {
			GuiStats.func_27128_a(this.field_27275_a, i1 + 215 - 18 + 1, i2 + 1 + 1, 36, 18);
		} else {
			GuiStats.func_27128_a(this.field_27275_a, i1 + 215 - 18, i2 + 1, 36, 18);
		}

	}

	protected void drawSlot(int i1, int i2, int i3, int i4, Tessellator tessellator5) {
		StatCrafting statCrafting6 = this.func_27264_b(i1);
		int i7 = statCrafting6.func_25072_b();
		GuiStats.func_27148_a(this.field_27275_a, i2 + 40, i3, i7);
		this.func_27265_a((StatCrafting)StatList.field_25170_B[i7], i2 + 115, i3, i1 % 2 == 0);
		this.func_27265_a((StatCrafting)StatList.field_25158_z[i7], i2 + 165, i3, i1 % 2 == 0);
		this.func_27265_a(statCrafting6, i2 + 215, i3, i1 % 2 == 0);
	}

	protected String func_27263_a(int i1) {
		return i1 == 1 ? "stat.crafted" : (i1 == 2 ? "stat.used" : "stat.depleted");
	}
}
