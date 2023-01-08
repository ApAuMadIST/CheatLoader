package net.minecraft.src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class J_JsonObject extends J_JsonRootNode {
	private final Map field_27222_a;

	J_JsonObject(Map map1) {
		this.field_27222_a = new HashMap(map1);
	}

	public Map func_27214_c() {
		return new HashMap(this.field_27222_a);
	}

	public EnumJsonNodeType func_27218_a() {
		return EnumJsonNodeType.OBJECT;
	}

	public String func_27216_b() {
		throw new IllegalStateException("Attempt to get text on a JsonNode without text.");
	}

	public List func_27215_d() {
		throw new IllegalStateException("Attempt to get elements on a JsonNode without elements.");
	}

	public boolean equals(Object object1) {
		if(this == object1) {
			return true;
		} else if(object1 != null && this.getClass() == object1.getClass()) {
			J_JsonObject j_JsonObject2 = (J_JsonObject)object1;
			return this.field_27222_a.equals(j_JsonObject2.field_27222_a);
		} else {
			return false;
		}
	}

	public int hashCode() {
		return this.field_27222_a.hashCode();
	}

	public String toString() {
		return "JsonObject fields:[" + this.field_27222_a + "]";
	}
}
