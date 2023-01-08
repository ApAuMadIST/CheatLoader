package net.minecraft.src;

import java.util.LinkedList;
import java.util.List;

public final class J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException extends J_JsonNodeDoesNotMatchJsonNodeSelectorException {
	final J_Functor field_27326_a;
	final List field_27325_b;

	static J_JsonNodeDoesNotMatchJsonNodeSelectorException func_27322_a(J_Functor j_Functor0) {
		return new J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException(j_Functor0, new LinkedList());
	}

	static J_JsonNodeDoesNotMatchJsonNodeSelectorException func_27323_a(J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0, J_JsonNodeSelector j_JsonNodeSelector1) {
		LinkedList linkedList2 = new LinkedList(j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0.field_27325_b);
		linkedList2.add(j_JsonNodeSelector1);
		return new J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException(j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0.field_27326_a, linkedList2);
	}

	static J_JsonNodeDoesNotMatchJsonNodeSelectorException func_27321_b(J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0, J_JsonNodeSelector j_JsonNodeSelector1) {
		LinkedList linkedList2 = new LinkedList();
		linkedList2.add(j_JsonNodeSelector1);
		return new J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException(j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0.field_27326_a, linkedList2);
	}

	private J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException(J_Functor j_Functor1, List list2) {
		super("Failed to match any JSON node at [" + func_27324_a(list2) + "]");
		this.field_27326_a = j_Functor1;
		this.field_27325_b = list2;
	}

	static String func_27324_a(List list0) {
		StringBuilder stringBuilder1 = new StringBuilder();

		for(int i2 = list0.size() - 1; i2 >= 0; --i2) {
			stringBuilder1.append(((J_JsonNodeSelector)list0.get(i2)).func_27358_a());
			if(i2 != 0) {
				stringBuilder1.append(".");
			}
		}

		return stringBuilder1.toString();
	}

	public String toString() {
		return "JsonNodeDoesNotMatchJsonNodeSelectorException{failedNode=" + this.field_27326_a + ", failPath=" + this.field_27325_b + '}';
	}
}
