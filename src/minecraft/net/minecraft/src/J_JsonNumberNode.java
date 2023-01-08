package net.minecraft.src;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

final class J_JsonNumberNode extends J_JsonNode {
	private static final Pattern field_27226_a = Pattern.compile("(-?)(0|([1-9]([0-9]*)))(\\.[0-9]+)?((e|E)(\\+|-)?[0-9]+)?");
	private final String field_27225_b;

	J_JsonNumberNode(String string1) {
		if(string1 == null) {
			throw new NullPointerException("Attempt to construct a JsonNumber with a null value.");
		} else if(!field_27226_a.matcher(string1).matches()) {
			throw new IllegalArgumentException("Attempt to construct a JsonNumber with a String [" + string1 + "] that does not match the JSON number specification.");
		} else {
			this.field_27225_b = string1;
		}
	}

	public EnumJsonNodeType func_27218_a() {
		return EnumJsonNodeType.NUMBER;
	}

	public String func_27216_b() {
		return this.field_27225_b;
	}

	public Map func_27214_c() {
		throw new IllegalStateException("Attempt to get fields on a JsonNode without fields.");
	}

	public List func_27215_d() {
		throw new IllegalStateException("Attempt to get elements on a JsonNode without elements.");
	}

	public boolean equals(Object object1) {
		if(this == object1) {
			return true;
		} else if(object1 != null && this.getClass() == object1.getClass()) {
			J_JsonNumberNode j_JsonNumberNode2 = (J_JsonNumberNode)object1;
			return this.field_27225_b.equals(j_JsonNumberNode2.field_27225_b);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.field_27225_b.hashCode();
	}

	public String toString() {
		return "JsonNumberNode value:[" + this.field_27225_b + "]";
	}
}
