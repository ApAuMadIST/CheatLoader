package net.minecraft.src;

public final class J_InvalidSyntaxException extends Exception {
	private final int field_27191_a;
	private final int field_27190_b;

	J_InvalidSyntaxException(String string1, J_ThingWithPosition j_ThingWithPosition2) {
		super("At line " + j_ThingWithPosition2.func_27330_b() + ", column " + j_ThingWithPosition2.func_27331_a() + ":  " + string1);
		this.field_27191_a = j_ThingWithPosition2.func_27331_a();
		this.field_27190_b = j_ThingWithPosition2.func_27330_b();
	}

	J_InvalidSyntaxException(String string1, Throwable throwable2, J_ThingWithPosition j_ThingWithPosition3) {
		super("At line " + j_ThingWithPosition3.func_27330_b() + ", column " + j_ThingWithPosition3.func_27331_a() + ":  " + string1, throwable2);
		this.field_27191_a = j_ThingWithPosition3.func_27331_a();
		this.field_27190_b = j_ThingWithPosition3.func_27330_b();
	}
}
