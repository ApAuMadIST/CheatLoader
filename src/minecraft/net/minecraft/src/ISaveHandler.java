package net.minecraft.src;

import java.io.File;
import java.util.List;

public interface ISaveHandler {
	WorldInfo loadWorldInfo();

	void func_22150_b();

	IChunkLoader getChunkLoader(WorldProvider worldProvider1);

	void saveWorldInfoAndPlayer(WorldInfo worldInfo1, List list2);

	void saveWorldInfo(WorldInfo worldInfo1);

	File func_28113_a(String string1);
}
