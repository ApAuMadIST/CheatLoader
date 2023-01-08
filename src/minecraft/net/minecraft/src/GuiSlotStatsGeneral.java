package net.minecraft.src;

class GuiSlotStatsGeneral extends GuiSlot {
	final GuiStats field_27276_a;

	public GuiSlotStatsGeneral(GuiStats guiStats1) {
		super(GuiStats.func_27141_a(guiStats1), guiStats1.width, guiStats1.height, 32, guiStats1.height - 64, 10);
		this.field_27276_a = guiStats1;
		this.func_27258_a(false);
	}

	protected int getSize() {
		return StatList.field_25187_b.size();
	}

	protected void elementClicked(int i1, boolean z2) {
	}

	protected boolean isSelected(int i1) {
		return false;
	}

	protected int getContentHeight() {
		return this.getSize() * 10;
	}

	protected void drawBackground() {
		this.field_27276_a.drawDefaultBackground();
	}

	protected void drawSlot(int i1, int i2, int i3, int i4, Tessellator tessellator5) {
		StatBase statBase6 = (StatBase)StatList.field_25187_b.get(i1);
		this.field_27276_a.drawString(GuiStats.func_27145_b(this.field_27276_a), statBase6.statName, i2 + 2, i3 + 1, i1 % 2 == 0 ? 0xFFFFFF : 9474192);
		String string7 = statBase6.func_27084_a(GuiStats.func_27142_c(this.field_27276_a).writeStat(statBase6));
		this.field_27276_a.drawString(GuiStats.func_27140_d(this.field_27276_a), string7, i2 + 2 + 213 - GuiStats.func_27146_e(this.field_27276_a).getStringWidth(string7), i3 + 1, i1 % 2 == 0 ? 0xFFFFFF : 9474192);
	}
}
