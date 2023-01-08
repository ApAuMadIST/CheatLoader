package net.minecraft.src;

public final class J_JsonNodeDoesNotMatchPathElementsException extends J_JsonNodeDoesNotMatchJsonNodeSelectorException {
	private static final J_JsonFormatter field_27320_a = new J_CompactJsonFormatter();

	static J_JsonNodeDoesNotMatchPathElementsException func_27319_a(J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0, Object[] object1, J_JsonRootNode j_JsonRootNode2) {
		return new J_JsonNodeDoesNotMatchPathElementsException(j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0, object1, j_JsonRootNode2);
	}

	private J_JsonNodeDoesNotMatchPathElementsException(J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException1, Object[] object2, J_JsonRootNode j_JsonRootNode3) {
		super(func_27318_b(j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException1, object2, j_JsonRootNode3));
	}

	private static String func_27318_b(J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0, Object[] object1, J_JsonRootNode j_JsonRootNode2) {
		return "Failed to find " + j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0.field_27326_a.toString() + " at [" + J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException.func_27324_a(j_JsonNodeDoesNotMatchChainedJsonNodeSelectorException0.field_27325_b) + "] while resolving [" + func_27317_a(object1) + "] in " + field_27320_a.func_27327_a(j_JsonRootNode2) + ".";
	}

	private static String func_27317_a(Object[] object0) {
		StringBuilder stringBuilder1 = new StringBuilder();
		boolean z2 = true;
		Object[] object3 = object0;
		int i4 = object0.length;

		for(int i5 = 0; i5 < i4; ++i5) {
			Object object6 = object3[i5];
			if(!z2) {
				stringBuilder1.append(".");
			}

			z2 = false;
			if(object6 instanceof String) {
				stringBuilder1.append("\"").append(object6).append("\"");
			} else {
				stringBuilder1.append(object6);
			}
		}

		return stringBuilder1.toString();
	}
}
