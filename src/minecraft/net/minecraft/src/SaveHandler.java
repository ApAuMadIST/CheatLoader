package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SaveHandler implements ISaveHandler {
	private static final Logger logger = Logger.getLogger("Minecraft");
	private final File saveDirectory;
	private final File playersDirectory;
	private final File field_28114_d;
	private final long now = System.currentTimeMillis();

	public SaveHandler(File file1, String string2, boolean z3) {
		this.saveDirectory = new File(file1, string2);
		this.saveDirectory.mkdirs();
		this.playersDirectory = new File(this.saveDirectory, "players");
		this.field_28114_d = new File(this.saveDirectory, "data");
		this.field_28114_d.mkdirs();
		if(z3) {
			this.playersDirectory.mkdirs();
		}

		this.func_22154_d();
	}

	private void func_22154_d() {
		try {
			File file1 = new File(this.saveDirectory, "session.lock");
			DataOutputStream dataOutputStream2 = new DataOutputStream(new FileOutputStream(file1));

			try {
				dataOutputStream2.writeLong(this.now);
			} finally {
				dataOutputStream2.close();
			}

		} catch (IOException iOException7) {
			iOException7.printStackTrace();
			throw new RuntimeException("Failed to check session lock, aborting");
		}
	}

	protected File getSaveDirectory() {
		return this.saveDirectory;
	}

	public void func_22150_b() {
		try {
			File file1 = new File(this.saveDirectory, "session.lock");
			DataInputStream dataInputStream2 = new DataInputStream(new FileInputStream(file1));

			try {
				if(dataInputStream2.readLong() != this.now) {
					throw new MinecraftException("The save is being accessed from another location, aborting");
				}
			} finally {
				dataInputStream2.close();
			}

		} catch (IOException iOException7) {
			throw new MinecraftException("Failed to check session lock, aborting");
		}
	}

	public IChunkLoader getChunkLoader(WorldProvider worldProvider1) {
		if(worldProvider1 instanceof WorldProviderHell) {
			File file2 = new File(this.saveDirectory, "DIM-1");
			file2.mkdirs();
			return new ChunkLoader(file2, true);
		} else {
			return new ChunkLoader(this.saveDirectory, true);
		}
	}

	public WorldInfo loadWorldInfo() {
		File file1 = new File(this.saveDirectory, "level.dat");
		NBTTagCompound nBTTagCompound2;
		NBTTagCompound nBTTagCompound3;
		if(file1.exists()) {
			try {
				nBTTagCompound2 = CompressedStreamTools.func_1138_a(new FileInputStream(file1));
				nBTTagCompound3 = nBTTagCompound2.getCompoundTag("Data");
				return new WorldInfo(nBTTagCompound3);
			} catch (Exception exception5) {
				exception5.printStackTrace();
			}
		}

		file1 = new File(this.saveDirectory, "level.dat_old");
		if(file1.exists()) {
			try {
				nBTTagCompound2 = CompressedStreamTools.func_1138_a(new FileInputStream(file1));
				nBTTagCompound3 = nBTTagCompound2.getCompoundTag("Data");
				return new WorldInfo(nBTTagCompound3);
			} catch (Exception exception4) {
				exception4.printStackTrace();
			}
		}

		return null;
	}

	public void saveWorldInfoAndPlayer(WorldInfo worldInfo1, List list2) {
		NBTTagCompound nBTTagCompound3 = worldInfo1.getNBTTagCompoundWithPlayer(list2);
		NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
		nBTTagCompound4.setTag("Data", nBTTagCompound3);

		try {
			File file5 = new File(this.saveDirectory, "level.dat_new");
			File file6 = new File(this.saveDirectory, "level.dat_old");
			File file7 = new File(this.saveDirectory, "level.dat");
			CompressedStreamTools.writeGzippedCompoundToOutputStream(nBTTagCompound4, new FileOutputStream(file5));
			if(file6.exists()) {
				file6.delete();
			}

			file7.renameTo(file6);
			if(file7.exists()) {
				file7.delete();
			}

			file5.renameTo(file7);
			if(file5.exists()) {
				file5.delete();
			}
		} catch (Exception exception8) {
			exception8.printStackTrace();
		}

	}

	public void saveWorldInfo(WorldInfo worldInfo1) {
		NBTTagCompound nBTTagCompound2 = worldInfo1.getNBTTagCompound();
		NBTTagCompound nBTTagCompound3 = new NBTTagCompound();
		nBTTagCompound3.setTag("Data", nBTTagCompound2);

		try {
			File file4 = new File(this.saveDirectory, "level.dat_new");
			File file5 = new File(this.saveDirectory, "level.dat_old");
			File file6 = new File(this.saveDirectory, "level.dat");
			CompressedStreamTools.writeGzippedCompoundToOutputStream(nBTTagCompound3, new FileOutputStream(file4));
			if(file5.exists()) {
				file5.delete();
			}

			file6.renameTo(file5);
			if(file6.exists()) {
				file6.delete();
			}

			file4.renameTo(file6);
			if(file4.exists()) {
				file4.delete();
			}
		} catch (Exception exception7) {
			exception7.printStackTrace();
		}

	}

	public File func_28113_a(String string1) {
		return new File(this.field_28114_d, string1 + ".dat");
	}
}
