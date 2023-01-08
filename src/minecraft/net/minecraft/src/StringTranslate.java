package net.minecraft.src;

import java.io.IOException;
import java.util.Properties;

public class StringTranslate {
	private static StringTranslate instance = new StringTranslate();
	private Properties translateTable = new Properties();

	private StringTranslate() {
		try {
			this.translateTable.load(StringTranslate.class.getResourceAsStream("/lang/en_US.lang"));
			this.translateTable.load(StringTranslate.class.getResourceAsStream("/lang/stats_US.lang"));
		} catch (IOException iOException2) {
			iOException2.printStackTrace();
		}

	}

	public static StringTranslate getInstance() {
		return instance;
	}

	public String translateKey(String string1) {
		return this.translateTable.getProperty(string1, string1);
	}

	public String translateKeyFormat(String string1, Object... object2) {
		String string3 = this.translateTable.getProperty(string1, string1);
		return String.format(string3, object2);
	}

	public String translateNamedKey(String string1) {
		return this.translateTable.getProperty(string1 + ".name", "");
	}
}
