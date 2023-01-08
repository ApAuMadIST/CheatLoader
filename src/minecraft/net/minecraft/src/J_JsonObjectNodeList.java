package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;

class J_JsonObjectNodeList extends HashMap {
	final J_JsonObjectNodeBuilder field_27308_a;

	J_JsonObjectNodeList(J_JsonObjectNodeBuilder j_JsonObjectNodeBuilder1) {
		this.field_27308_a = j_JsonObjectNodeBuilder1;
		Iterator iterator2 = J_JsonObjectNodeBuilder.func_27236_a(this.field_27308_a).iterator();

		while(iterator2.hasNext()) {
			J_JsonFieldBuilder j_JsonFieldBuilder3 = (J_JsonFieldBuilder)iterator2.next();
			this.put(j_JsonFieldBuilder3.func_27303_b(), j_JsonFieldBuilder3.func_27302_c());
		}

	}
}
