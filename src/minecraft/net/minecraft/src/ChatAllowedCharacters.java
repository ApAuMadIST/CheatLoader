package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChatAllowedCharacters {
	public static final String allowedCharacters = getAllowedCharacters();
	public static final char[] allowedCharactersArray = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};

	private static String getAllowedCharacters() {
		String string0 = "";

		try {
			BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(ChatAllowedCharacters.class.getResourceAsStream("/font.txt"), "UTF-8"));
			String string2 = "";

			while((string2 = bufferedReader1.readLine()) != null) {
				if(!string2.startsWith("#")) {
					string0 = string0 + string2;
				}
			}

			bufferedReader1.close();
		} catch (Exception exception3) {
		}

		return string0;
	}
}
