package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ChunkLoader implements IChunkLoader {
	private File saveDir;
	private boolean createIfNecessary;

	public ChunkLoader(File file1, boolean z2) {
		this.saveDir = file1;
		this.createIfNecessary = z2;
	}

	private File chunkFileForXZ(int i1, int i2) {
		String string3 = "c." + Integer.toString(i1, 36) + "." + Integer.toString(i2, 36) + ".dat";
		String string4 = Integer.toString(i1 & 63, 36);
		String string5 = Integer.toString(i2 & 63, 36);
		File file6 = new File(this.saveDir, string4);
		if(!file6.exists()) {
			if(!this.createIfNecessary) {
				return null;
			}

			file6.mkdir();
		}

		file6 = new File(file6, string5);
		if(!file6.exists()) {
			if(!this.createIfNecessary) {
				return null;
			}

			file6.mkdir();
		}

		file6 = new File(file6, string3);
		return !file6.exists() && !this.createIfNecessary ? null : file6;
	}

	public Chunk loadChunk(World world1, int i2, int i3) throws IOException {
		File file4 = this.chunkFileForXZ(i2, i3);
		if(file4 != null && file4.exists()) {
			try {
				FileInputStream fileInputStream5 = new FileInputStream(file4);
				NBTTagCompound nBTTagCompound6 = CompressedStreamTools.func_1138_a(fileInputStream5);
				if(!nBTTagCompound6.hasKey("Level")) {
					System.out.println("Chunk file at " + i2 + "," + i3 + " is missing level data, skipping");
					return null;
				}

				if(!nBTTagCompound6.getCompoundTag("Level").hasKey("Blocks")) {
					System.out.println("Chunk file at " + i2 + "," + i3 + " is missing block data, skipping");
					return null;
				}

				Chunk chunk7 = loadChunkIntoWorldFromCompound(world1, nBTTagCompound6.getCompoundTag("Level"));
				if(!chunk7.isAtLocation(i2, i3)) {
					System.out.println("Chunk file at " + i2 + "," + i3 + " is in the wrong location; relocating. (Expected " + i2 + ", " + i3 + ", got " + chunk7.xPosition + ", " + chunk7.zPosition + ")");
					nBTTagCompound6.setInteger("xPos", i2);
					nBTTagCompound6.setInteger("zPos", i3);
					chunk7 = loadChunkIntoWorldFromCompound(world1, nBTTagCompound6.getCompoundTag("Level"));
				}

				chunk7.func_25124_i();
				return chunk7;
			} catch (Exception exception8) {
				exception8.printStackTrace();
			}
		}

		return null;
	}

	public void saveChunk(World world1, Chunk chunk2) throws IOException {
		world1.checkSessionLock();
		File file3 = this.chunkFileForXZ(chunk2.xPosition, chunk2.zPosition);
		if(file3.exists()) {
			WorldInfo worldInfo4 = world1.getWorldInfo();
			worldInfo4.setSizeOnDisk(worldInfo4.getSizeOnDisk() - file3.length());
		}

		try {
			File file10 = new File(this.saveDir, "tmp_chunk.dat");
			FileOutputStream fileOutputStream5 = new FileOutputStream(file10);
			NBTTagCompound nBTTagCompound6 = new NBTTagCompound();
			NBTTagCompound nBTTagCompound7 = new NBTTagCompound();
			nBTTagCompound6.setTag("Level", nBTTagCompound7);
			storeChunkInCompound(chunk2, world1, nBTTagCompound7);
			CompressedStreamTools.writeGzippedCompoundToOutputStream(nBTTagCompound6, fileOutputStream5);
			fileOutputStream5.close();
			if(file3.exists()) {
				file3.delete();
			}

			file10.renameTo(file3);
			WorldInfo worldInfo8 = world1.getWorldInfo();
			worldInfo8.setSizeOnDisk(worldInfo8.getSizeOnDisk() + file3.length());
		} catch (Exception exception9) {
			exception9.printStackTrace();
		}

	}

	public static void storeChunkInCompound(Chunk chunk0, World world1, NBTTagCompound nBTTagCompound2) {
		world1.checkSessionLock();
		nBTTagCompound2.setInteger("xPos", chunk0.xPosition);
		nBTTagCompound2.setInteger("zPos", chunk0.zPosition);
		nBTTagCompound2.setLong("LastUpdate", world1.getWorldTime());
		nBTTagCompound2.setByteArray("Blocks", chunk0.blocks);
		nBTTagCompound2.setByteArray("Data", chunk0.data.data);
		nBTTagCompound2.setByteArray("SkyLight", chunk0.skylightMap.data);
		nBTTagCompound2.setByteArray("BlockLight", chunk0.blocklightMap.data);
		nBTTagCompound2.setByteArray("HeightMap", chunk0.heightMap);
		nBTTagCompound2.setBoolean("TerrainPopulated", chunk0.isTerrainPopulated);
		chunk0.hasEntities = false;
		NBTTagList nBTTagList3 = new NBTTagList();

		Iterator iterator5;
		NBTTagCompound nBTTagCompound7;
		for(int i4 = 0; i4 < chunk0.entities.length; ++i4) {
			iterator5 = chunk0.entities[i4].iterator();

			while(iterator5.hasNext()) {
				Entity entity6 = (Entity)iterator5.next();
				chunk0.hasEntities = true;
				nBTTagCompound7 = new NBTTagCompound();
				if(entity6.addEntityID(nBTTagCompound7)) {
					nBTTagList3.setTag(nBTTagCompound7);
				}
			}
		}

		nBTTagCompound2.setTag("Entities", nBTTagList3);
		NBTTagList nBTTagList8 = new NBTTagList();
		iterator5 = chunk0.chunkTileEntityMap.values().iterator();

		while(iterator5.hasNext()) {
			TileEntity tileEntity9 = (TileEntity)iterator5.next();
			nBTTagCompound7 = new NBTTagCompound();
			tileEntity9.writeToNBT(nBTTagCompound7);
			nBTTagList8.setTag(nBTTagCompound7);
		}

		nBTTagCompound2.setTag("TileEntities", nBTTagList8);
	}

	public static Chunk loadChunkIntoWorldFromCompound(World world0, NBTTagCompound nBTTagCompound1) {
		int i2 = nBTTagCompound1.getInteger("xPos");
		int i3 = nBTTagCompound1.getInteger("zPos");
		Chunk chunk4 = new Chunk(world0, i2, i3);
		chunk4.blocks = nBTTagCompound1.getByteArray("Blocks");
		chunk4.data = new NibbleArray(nBTTagCompound1.getByteArray("Data"));
		chunk4.skylightMap = new NibbleArray(nBTTagCompound1.getByteArray("SkyLight"));
		chunk4.blocklightMap = new NibbleArray(nBTTagCompound1.getByteArray("BlockLight"));
		chunk4.heightMap = nBTTagCompound1.getByteArray("HeightMap");
		chunk4.isTerrainPopulated = nBTTagCompound1.getBoolean("TerrainPopulated");
		if(!chunk4.data.isValid()) {
			chunk4.data = new NibbleArray(chunk4.blocks.length);
		}

		if(chunk4.heightMap == null || !chunk4.skylightMap.isValid()) {
			chunk4.heightMap = new byte[256];
			chunk4.skylightMap = new NibbleArray(chunk4.blocks.length);
			chunk4.func_1024_c();
		}

		if(!chunk4.blocklightMap.isValid()) {
			chunk4.blocklightMap = new NibbleArray(chunk4.blocks.length);
			chunk4.func_1014_a();
		}

		NBTTagList nBTTagList5 = nBTTagCompound1.getTagList("Entities");
		if(nBTTagList5 != null) {
			for(int i6 = 0; i6 < nBTTagList5.tagCount(); ++i6) {
				NBTTagCompound nBTTagCompound7 = (NBTTagCompound)nBTTagList5.tagAt(i6);
				Entity entity8 = EntityList.createEntityFromNBT(nBTTagCompound7, world0);
				chunk4.hasEntities = true;
				if(entity8 != null) {
					chunk4.addEntity(entity8);
				}
			}
		}

		NBTTagList nBTTagList10 = nBTTagCompound1.getTagList("TileEntities");
		if(nBTTagList10 != null) {
			for(int i11 = 0; i11 < nBTTagList10.tagCount(); ++i11) {
				NBTTagCompound nBTTagCompound12 = (NBTTagCompound)nBTTagList10.tagAt(i11);
				TileEntity tileEntity9 = TileEntity.createAndLoadEntity(nBTTagCompound12);
				if(tileEntity9 != null) {
					chunk4.addTileEntity(tileEntity9);
				}
			}
		}

		return chunk4;
	}

	public void func_814_a() {
	}

	public void saveExtraData() {
	}

	public void saveExtraChunkData(World world1, Chunk chunk2) throws IOException {
	}
}
