package net.minecraft.src;

import java.util.List;

final class J_JsonElementNodeSelector extends J_LeafFunctor {
	final int field_27069_a;

	J_JsonElementNodeSelector(int i1) {
		this.field_27069_a = i1;
	}

	public boolean func_27067_a(List list1) {
		return list1.size() > this.field_27069_a;
	}

	public String func_27060_a() {
		return Integer.toString(this.field_27069_a);
	}

	public J_JsonNode func_27068_b(List list1) {
		return (J_JsonNode)list1.get(this.field_27069_a);
	}

	public String toString() {
		return "an element at index [" + this.field_27069_a + "]";
	}

	public Object func_27063_c(Object object1) {
		return this.func_27068_b((List)object1);
	}

	public boolean func_27058_a(Object object1) {
		return this.func_27067_a((List)object1);
	}
}
