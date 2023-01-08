package net.minecraft.src;

public class StatCollector {
	private static StringTranslate localizedName = StringTranslate.getInstance();

	public static String translateToLocal(String string0) {
		return localizedName.translateKey(string0);
	}

	public static String translateToLocalFormatted(String string0, Object... object1) {
		return localizedName.translateKeyFormat(string0, object1);
	}
}
