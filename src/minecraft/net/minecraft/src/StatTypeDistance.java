package net.minecraft.src;

final class StatTypeDistance implements IStatType {
	public String func_27192_a(int i1) {
		double d3 = (double)i1 / 100.0D;
		double d5 = d3 / 1000.0D;
		return d5 > 0.5D ? StatBase.func_27081_j().format(d5) + " km" : (d3 > 0.5D ? StatBase.func_27081_j().format(d3) + " m" : i1 + " cm");
	}
}
