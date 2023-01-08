package net.minecraft.src;

class J_ArrayNodeContainer implements J_NodeContainer {
	final J_JsonArrayNodeBuilder field_27294_a;
	final J_JsonListenerToJdomAdapter field_27293_b;

	J_ArrayNodeContainer(J_JsonListenerToJdomAdapter j_JsonListenerToJdomAdapter1, J_JsonArrayNodeBuilder j_JsonArrayNodeBuilder2) {
		this.field_27293_b = j_JsonListenerToJdomAdapter1;
		this.field_27294_a = j_JsonArrayNodeBuilder2;
	}

	public void func_27290_a(J_JsonNodeBuilder j_JsonNodeBuilder1) {
		this.field_27294_a.func_27240_a(j_JsonNodeBuilder1);
	}

	public void func_27289_a(J_JsonFieldBuilder j_JsonFieldBuilder1) {
		throw new RuntimeException("Coding failure in Argo:  Attempt to add a field to an array.");
	}
}
