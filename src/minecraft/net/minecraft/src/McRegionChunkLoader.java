package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class McRegionChunkLoader implements IChunkLoader {
	private final File worldDir;

	public McRegionChunkLoader(File file1) {
		this.worldDir = file1;
	}

	public Chunk loadChunk(World world1, int i2, int i3) throws IOException {
		DataInputStream dataInputStream4 = RegionFileCache.getChunkInputStream(this.worldDir, i2, i3);
		if(dataInputStream4 != null) {
			NBTTagCompound nBTTagCompound5 = CompressedStreamTools.func_1141_a(dataInputStream4);
			if(!nBTTagCompound5.hasKey("Level")) {
				System.out.println("Chunk file at " + i2 + "," + i3 + " is missing level data, skipping");
				return null;
			} else if(!nBTTagCompound5.getCompoundTag("Level").hasKey("Blocks")) {
				System.out.println("Chunk file at " + i2 + "," + i3 + " is missing block data, skipping");
				return null;
			} else {
				Chunk chunk6 = ChunkLoader.loadChunkIntoWorldFromCompound(world1, nBTTagCompound5.getCompoundTag("Level"));
				if(!chunk6.isAtLocation(i2, i3)) {
					System.out.println("Chunk file at " + i2 + "," + i3 + " is in the wrong location; relocating. (Expected " + i2 + ", " + i3 + ", got " + chunk6.xPosition + ", " + chunk6.zPosition + ")");
					nBTTagCompound5.setInteger("xPos", i2);
					nBTTagCompound5.setInteger("zPos", i3);
					chunk6 = ChunkLoader.loadChunkIntoWorldFromCompound(world1, nBTTagCompound5.getCompoundTag("Level"));
				}

				chunk6.func_25124_i();
				return chunk6;
			}
		} else {
			return null;
		}
	}

	public void saveChunk(World world1, Chunk chunk2) throws IOException {
		world1.checkSessionLock();

		try {
			DataOutputStream dataOutputStream3 = RegionFileCache.getChunkOutputStream(this.worldDir, chunk2.xPosition, chunk2.zPosition);
			NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
			NBTTagCompound nBTTagCompound5 = new NBTTagCompound();
			nBTTagCompound4.setTag("Level", nBTTagCompound5);
			ChunkLoader.storeChunkInCompound(chunk2, world1, nBTTagCompound5);
			CompressedStreamTools.func_1139_a(nBTTagCompound4, dataOutputStream3);
			dataOutputStream3.close();
			WorldInfo worldInfo6 = world1.getWorldInfo();
			worldInfo6.setSizeOnDisk(worldInfo6.getSizeOnDisk() + (long)RegionFileCache.getSizeDelta(this.worldDir, chunk2.xPosition, chunk2.zPosition));
		} catch (Exception exception7) {
			exception7.printStackTrace();
		}

	}

	public void saveExtraChunkData(World world1, Chunk chunk2) throws IOException {
	}

	public void func_814_a() {
	}

	public void saveExtraData() {
	}
}
