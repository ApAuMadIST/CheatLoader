package net.minecraft.src;

public class StatBasic extends StatBase {
	public StatBasic(int i1, String string2, IStatType iStatType3) {
		super(i1, string2, iStatType3);
	}

	public StatBasic(int i1, String string2) {
		super(i1, string2);
	}

	public StatBase registerStat() {
		super.registerStat();
		StatList.field_25187_b.add(this);
		return this;
	}
}
