package net.minecraft.src;

import java.util.Arrays;
import java.util.Map;

public final class J_JsonNodeFactories {
	public static J_JsonNode func_27310_a() {
		return J_JsonConstants.field_27228_a;
	}

	public static J_JsonNode func_27313_b() {
		return J_JsonConstants.field_27227_b;
	}

	public static J_JsonNode func_27314_c() {
		return J_JsonConstants.field_27230_c;
	}

	public static J_JsonStringNode func_27316_a(String string0) {
		return new J_JsonStringNode(string0);
	}

	public static J_JsonNode func_27311_b(String string0) {
		return new J_JsonNumberNode(string0);
	}

	public static J_JsonRootNode func_27309_a(Iterable iterable0) {
		return new J_JsonArray(iterable0);
	}

	public static J_JsonRootNode func_27315_a(J_JsonNode... j_JsonNode0) {
		return func_27309_a(Arrays.asList(j_JsonNode0));
	}

	public static J_JsonRootNode func_27312_a(Map map0) {
		return new J_JsonObject(map0);
	}
}
