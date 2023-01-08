package net.minecraft.src;

import java.util.Stack;

final class J_JsonListenerToJdomAdapter implements J_JsonListener {
	private final Stack field_27210_a = new Stack();
	private J_JsonNodeBuilder field_27209_b;

	J_JsonRootNode func_27208_a() {
		return (J_JsonRootNode)this.field_27209_b.func_27234_b();
	}

	public void func_27195_b() {
	}

	public void func_27204_c() {
	}

	public void func_27200_d() {
		J_JsonArrayNodeBuilder j_JsonArrayNodeBuilder1 = J_JsonNodeBuilders.func_27249_e();
		this.func_27207_a(j_JsonArrayNodeBuilder1);
		this.field_27210_a.push(new J_ArrayNodeContainer(this, j_JsonArrayNodeBuilder1));
	}

	public void func_27197_e() {
		this.field_27210_a.pop();
	}

	public void func_27194_f() {
		J_JsonObjectNodeBuilder j_JsonObjectNodeBuilder1 = J_JsonNodeBuilders.func_27253_d();
		this.func_27207_a(j_JsonObjectNodeBuilder1);
		this.field_27210_a.push(new J_ObjectNodeContainer(this, j_JsonObjectNodeBuilder1));
	}

	public void func_27203_g() {
		this.field_27210_a.pop();
	}

	public void func_27205_a(String string1) {
		J_JsonFieldBuilder j_JsonFieldBuilder2 = J_JsonFieldBuilder.func_27301_a().func_27304_a(J_JsonNodeBuilders.func_27254_b(string1));
		((J_NodeContainer)this.field_27210_a.peek()).func_27289_a(j_JsonFieldBuilder2);
		this.field_27210_a.push(new J_FieldNodeContainer(this, j_JsonFieldBuilder2));
	}

	public void func_27199_h() {
		this.field_27210_a.pop();
	}

	public void func_27201_b(String string1) {
		this.func_27206_b(J_JsonNodeBuilders.func_27250_a(string1));
	}

	public void func_27196_i() {
		this.func_27206_b(J_JsonNodeBuilders.func_27251_b());
	}

	public void func_27198_c(String string1) {
		this.func_27206_b(J_JsonNodeBuilders.func_27254_b(string1));
	}

	public void func_27193_j() {
		this.func_27206_b(J_JsonNodeBuilders.func_27252_c());
	}

	public void func_27202_k() {
		this.func_27206_b(J_JsonNodeBuilders.func_27248_a());
	}

	private void func_27207_a(J_JsonNodeBuilder j_JsonNodeBuilder1) {
		if(this.field_27209_b == null) {
			this.field_27209_b = j_JsonNodeBuilder1;
		} else {
			this.func_27206_b(j_JsonNodeBuilder1);
		}

	}

	private void func_27206_b(J_JsonNodeBuilder j_JsonNodeBuilder1) {
		((J_NodeContainer)this.field_27210_a.peek()).func_27290_a(j_JsonNodeBuilder1);
	}
}
