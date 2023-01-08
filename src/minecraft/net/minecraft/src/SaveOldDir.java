package net.minecraft.src;

import java.io.File;
import java.util.List;

public class SaveOldDir extends SaveHandler {
	public SaveOldDir(File file1, String string2, boolean z3) {
		super(file1, string2, z3);
	}

	public IChunkLoader getChunkLoader(WorldProvider worldProvider1) {
		File file2 = this.getSaveDirectory();
		if(worldProvider1 instanceof WorldProviderHell) {
			File file3 = new File(file2, "DIM-1");
			file3.mkdirs();
			return new McRegionChunkLoader(file3);
		} else {
			return new McRegionChunkLoader(file2);
		}
	}

	public void saveWorldInfoAndPlayer(WorldInfo worldInfo1, List list2) {
		worldInfo1.setSaveVersion(19132);
		super.saveWorldInfoAndPlayer(worldInfo1, list2);
	}
}
