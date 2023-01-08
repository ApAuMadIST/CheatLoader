package net.minecraft.src;

final class J_ChainedFunctor implements J_Functor {
	private final J_JsonNodeSelector field_27062_a;
	private final J_JsonNodeSelector field_27061_b;

	J_ChainedFunctor(J_JsonNodeSelector j_JsonNodeSelector1, J_JsonNodeSelector j_JsonNodeSelector2) {
		this.field_27062_a = j_JsonNodeSelector1;
		this.field_27061_b = j_JsonNodeSelector2;
	}

	public boolean func_27058_a(Object object1) {
		return this.field_27062_a.func_27356_a(object1) && this.field_27061_b.func_27356_a(this.field_27062_a.func_27357_b(object1));
	}

	public Object func_27059_b(Object object1) {
		Object object2;
		try {
			object2 = this.field_27062_a.func_27357_b(object1);
		} catch (J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException6) {
			throw J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27321_b(j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException6, this.field_27062_a);
		}

		try {
			Object object3 = this.field_27061_b.func_27357_b(object2);
			return object3;
		} catch (J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException5) {
			throw J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27323_a(j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException5, this.field_27062_a);
		}
	}

	public String func_27060_a() {
		return this.field_27061_b.func_27358_a();
	}

	public String toString() {
		return this.field_27062_a.toString() + ", with " + this.field_27061_b.toString();
	}
}
