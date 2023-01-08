package net.minecraft.src;

final class J_JsonStringNodeSelector extends J_LeafFunctor {
	public boolean func_27072_a(J_JsonNode j_JsonNode1) {
		return EnumJsonNodeType.STRING == j_JsonNode1.func_27218_a();
	}

	public String func_27060_a() {
		return "A short form string";
	}

	public String func_27073_b(J_JsonNode j_JsonNode1) {
		return j_JsonNode1.func_27216_b();
	}

	public String toString() {
		return "a value that is a string";
	}

	public Object func_27063_c(Object object1) {
		return this.func_27073_b((J_JsonNode)object1);
	}

	public boolean func_27058_a(Object object1) {
		return this.func_27072_a((J_JsonNode)object1);
	}
}
