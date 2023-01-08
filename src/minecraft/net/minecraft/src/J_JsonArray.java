package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final class J_JsonArray extends J_JsonRootNode {
	private final List field_27221_a;

	J_JsonArray(Iterable iterable1) {
		this.field_27221_a = func_27220_a(iterable1);
	}

	public EnumJsonNodeType func_27218_a() {
		return EnumJsonNodeType.ARRAY;
	}

	public List func_27215_d() {
		return new ArrayList(this.field_27221_a);
	}

	public String func_27216_b() {
		throw new IllegalStateException("Attempt to get text on a JsonNode without text.");
	}

	public Map func_27214_c() {
		throw new IllegalStateException("Attempt to get fields on a JsonNode without fields.");
	}

	public boolean equals(Object object1) {
		if(this == object1) {
			return true;
		} else if(object1 != null && this.getClass() == object1.getClass()) {
			J_JsonArray j_JsonArray2 = (J_JsonArray)object1;
			return this.field_27221_a.equals(j_JsonArray2.field_27221_a);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.field_27221_a.hashCode();
	}

	public String toString() {
		return "JsonArray elements:[" + this.field_27221_a + "]";
	}

	private static List func_27220_a(Iterable iterable0) {
		return new J_JsonNodeList(iterable0);
	}
}
