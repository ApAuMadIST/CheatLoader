package net.minecraft.src;

import java.util.Arrays;

public final class J_JsonNodeSelectors {
	public static J_JsonNodeSelector func_27349_a(Object... object0) {
		return func_27352_a(object0, new J_JsonNodeSelector(new J_JsonStringNodeSelector()));
	}

	public static J_JsonNodeSelector func_27346_b(Object... object0) {
		return func_27352_a(object0, new J_JsonNodeSelector(new J_JsonArrayNodeSelector()));
	}

	public static J_JsonNodeSelector func_27353_c(Object... object0) {
		return func_27352_a(object0, new J_JsonNodeSelector(new J_JsonObjectNodeSelector()));
	}

	public static J_JsonNodeSelector func_27348_a(String string0) {
		return func_27350_a(J_JsonNodeFactories.func_27316_a(string0));
	}

	public static J_JsonNodeSelector func_27350_a(J_JsonStringNode j_JsonStringNode0) {
		return new J_JsonNodeSelector(new J_JsonFieldNodeSelector(j_JsonStringNode0));
	}

	public static J_JsonNodeSelector func_27351_b(String string0) {
		return func_27353_c(new Object[0]).func_27355_a(func_27348_a(string0));
	}

	public static J_JsonNodeSelector func_27347_a(int i0) {
		return new J_JsonNodeSelector(new J_JsonElementNodeSelector(i0));
	}

	public static J_JsonNodeSelector func_27354_b(int i0) {
		return func_27346_b(new Object[0]).func_27355_a(func_27347_a(i0));
	}

	private static J_JsonNodeSelector func_27352_a(Object[] object0, J_JsonNodeSelector j_JsonNodeSelector1) {
		J_JsonNodeSelector j_JsonNodeSelector2 = j_JsonNodeSelector1;

		for(int i3 = object0.length - 1; i3 >= 0; --i3) {
			if(object0[i3] instanceof Integer) {
				j_JsonNodeSelector2 = func_27345_a(func_27354_b(((Integer)object0[i3]).intValue()), j_JsonNodeSelector2);
			} else {
				if(!(object0[i3] instanceof String)) {
					throw new IllegalArgumentException("Element [" + object0[i3] + "] of path elements" + " [" + Arrays.toString(object0) + "] was of illegal type [" + object0[i3].getClass().getCanonicalName() + "]; only Integer and String are valid.");
				}

				j_JsonNodeSelector2 = func_27345_a(func_27351_b((String)object0[i3]), j_JsonNodeSelector2);
			}
		}

		return j_JsonNodeSelector2;
	}

	private static J_JsonNodeSelector func_27345_a(J_JsonNodeSelector j_JsonNodeSelector0, J_JsonNodeSelector j_JsonNodeSelector1) {
		return new J_JsonNodeSelector(new J_ChainedFunctor(j_JsonNodeSelector0, j_JsonNodeSelector1));
	}
}
