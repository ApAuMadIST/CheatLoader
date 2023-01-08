package net.minecraft.src;

public final class J_JsonNodeSelector {
	final J_Functor field_27359_a;

	J_JsonNodeSelector(J_Functor j_Functor1) {
		this.field_27359_a = j_Functor1;
	}

	public boolean func_27356_a(Object object1) {
		return this.field_27359_a.func_27058_a(object1);
	}

	public Object func_27357_b(Object object1) {
		return this.field_27359_a.func_27059_b(object1);
	}

	public J_JsonNodeSelector func_27355_a(J_JsonNodeSelector j_JsonNodeSelector1) {
		return new J_JsonNodeSelector(new J_ChainedFunctor(this, j_JsonNodeSelector1));
	}

	String func_27358_a() {
		return this.field_27359_a.func_27060_a();
	}

	public String toString() {
		return this.field_27359_a.toString();
	}
}
