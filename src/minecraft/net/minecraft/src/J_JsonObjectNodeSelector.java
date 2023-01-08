package net.minecraft.src;

import java.util.Map;

final class J_JsonObjectNodeSelector extends J_LeafFunctor {
	public boolean func_27070_a(J_JsonNode j_JsonNode1) {
		return EnumJsonNodeType.OBJECT == j_JsonNode1.func_27218_a();
	}

	public String func_27060_a() {
		return "A short form object";
	}

	public Map func_27071_b(J_JsonNode j_JsonNode1) {
		return j_JsonNode1.func_27214_c();
	}

	public String toString() {
		return "an object";
	}

	public Object func_27063_c(Object object1) {
		return this.func_27071_b((J_JsonNode)object1);
	}

	public boolean func_27058_a(Object object1) {
		return this.func_27070_a((J_JsonNode)object1);
	}
}
