package net.minecraft.src;

class J_FieldNodeContainer implements J_NodeContainer {
	final J_JsonFieldBuilder field_27292_a;
	final J_JsonListenerToJdomAdapter field_27291_b;

	J_FieldNodeContainer(J_JsonListenerToJdomAdapter j_JsonListenerToJdomAdapter1, J_JsonFieldBuilder j_JsonFieldBuilder2) {
		this.field_27291_b = j_JsonListenerToJdomAdapter1;
		this.field_27292_a = j_JsonFieldBuilder2;
	}

	public void func_27290_a(J_JsonNodeBuilder j_JsonNodeBuilder1) {
		this.field_27292_a.func_27300_b(j_JsonNodeBuilder1);
	}

	public void func_27289_a(J_JsonFieldBuilder j_JsonFieldBuilder1) {
		throw new RuntimeException("Coding failure in Argo:  Attempt to add a field to a field.");
	}
}
