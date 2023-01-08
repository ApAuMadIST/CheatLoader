package net.minecraft.src;

import java.util.List;

public interface ISaveFormat {
	String func_22178_a();

	ISaveHandler getSaveLoader(String string1, boolean z2);

	List func_22176_b();

	void flushCache();

	WorldInfo func_22173_b(String string1);

	void func_22172_c(String string1);

	void func_22170_a(String string1, String string2);

	boolean isOldMapFormat(String string1);

	boolean convertMapFormat(String string1, IProgressUpdate iProgressUpdate2);
}
