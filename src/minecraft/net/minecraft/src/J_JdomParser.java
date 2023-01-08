package net.minecraft.src;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class J_JdomParser {
	public J_JsonRootNode func_27366_a(Reader reader1) throws J_InvalidSyntaxException, IOException {
		J_JsonListenerToJdomAdapter j_JsonListenerToJdomAdapter2 = new J_JsonListenerToJdomAdapter();
		(new J_SajParser()).func_27463_a(reader1, j_JsonListenerToJdomAdapter2);
		return j_JsonListenerToJdomAdapter2.func_27208_a();
	}

	public J_JsonRootNode func_27367_a(String string1) throws J_InvalidSyntaxException {
		try {
			J_JsonRootNode j_JsonRootNode2 = this.func_27366_a(new StringReader(string1));
			return j_JsonRootNode2;
		} catch (IOException iOException4) {
			throw new RuntimeException("Coding failure in Argo:  StringWriter gave an IOException", iOException4);
		}
	}
}
