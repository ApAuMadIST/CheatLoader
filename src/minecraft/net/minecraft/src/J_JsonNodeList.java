package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;

final class J_JsonNodeList extends ArrayList {
	final Iterable field_27405_a;

	J_JsonNodeList(Iterable iterable1) {
		this.field_27405_a = iterable1;
		Iterator iterator2 = this.field_27405_a.iterator();

		while(iterator2.hasNext()) {
			J_JsonNode j_JsonNode3 = (J_JsonNode)iterator2.next();
			this.add(j_JsonNode3);
		}

	}
}
