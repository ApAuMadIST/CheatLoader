package net.minecraft.src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class J_JsonArrayNodeBuilder implements J_JsonNodeBuilder {
	private final List field_27242_a = new LinkedList();

	public J_JsonArrayNodeBuilder func_27240_a(J_JsonNodeBuilder j_JsonNodeBuilder1) {
		this.field_27242_a.add(j_JsonNodeBuilder1);
		return this;
	}

	public J_JsonRootNode func_27241_a() {
		LinkedList linkedList1 = new LinkedList();
		Iterator iterator2 = this.field_27242_a.iterator();

		while(iterator2.hasNext()) {
			J_JsonNodeBuilder j_JsonNodeBuilder3 = (J_JsonNodeBuilder)iterator2.next();
			linkedList1.add(j_JsonNodeBuilder3.func_27234_b());
		}

		return J_JsonNodeFactories.func_27309_a(linkedList1);
	}

	public J_JsonNode func_27234_b() {
		return this.func_27241_a();
	}
}
