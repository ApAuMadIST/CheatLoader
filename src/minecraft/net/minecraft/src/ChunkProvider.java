package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChunkProvider implements IChunkProvider {
	private Set droppedChunksSet = new HashSet();
	private Chunk field_28064_b;
	private IChunkProvider chunkProvider;
	private IChunkLoader chunkLoader;
	private Map chunkMap = new HashMap();
	private List chunkList = new ArrayList();
	private World field_28066_g;

	public ChunkProvider(World paramfd, IChunkLoader parambf, IChunkProvider paramcl) {
		this.field_28064_b = new EmptyChunk(paramfd, new byte[32768], 0, 0);
		this.field_28066_g = paramfd;
		this.chunkLoader = parambf;
		this.chunkProvider = paramcl;
	}

	public boolean chunkExists(int paramInt1, int paramInt2) {
		return this.chunkMap.containsKey(ChunkCoordIntPair.chunkXZ2Int(paramInt1, paramInt2));
	}

	public Chunk prepareChunk(int paramInt1, int paramInt2) {
		int i = ChunkCoordIntPair.chunkXZ2Int(paramInt1, paramInt2);
		this.droppedChunksSet.remove(i);
		Chunk locallm = (Chunk)this.chunkMap.get(i);
		if(locallm == null) {
			locallm = this.loadChunkFromFile(paramInt1, paramInt2);
			if(locallm == null) {
				if(this.chunkProvider == null) {
					locallm = this.field_28064_b;
				} else {
					locallm = this.chunkProvider.provideChunk(paramInt1, paramInt2);
				}
			}

			this.chunkMap.put(i, locallm);
			this.chunkList.add(locallm);
			if(locallm != null) {
				locallm.func_4143_d();
				locallm.onChunkLoad();
			}

			if(!locallm.isTerrainPopulated && this.chunkExists(paramInt1 + 1, paramInt2 + 1) && this.chunkExists(paramInt1, paramInt2 + 1) && this.chunkExists(paramInt1 + 1, paramInt2)) {
				this.populate(this, paramInt1, paramInt2);
			}

			if(this.chunkExists(paramInt1 - 1, paramInt2) && !this.provideChunk(paramInt1 - 1, paramInt2).isTerrainPopulated && this.chunkExists(paramInt1 - 1, paramInt2 + 1) && this.chunkExists(paramInt1, paramInt2 + 1) && this.chunkExists(paramInt1 - 1, paramInt2)) {
				this.populate(this, paramInt1 - 1, paramInt2);
			}

			if(this.chunkExists(paramInt1, paramInt2 - 1) && !this.provideChunk(paramInt1, paramInt2 - 1).isTerrainPopulated && this.chunkExists(paramInt1 + 1, paramInt2 - 1) && this.chunkExists(paramInt1, paramInt2 - 1) && this.chunkExists(paramInt1 + 1, paramInt2)) {
				this.populate(this, paramInt1, paramInt2 - 1);
			}

			if(this.chunkExists(paramInt1 - 1, paramInt2 - 1) && !this.provideChunk(paramInt1 - 1, paramInt2 - 1).isTerrainPopulated && this.chunkExists(paramInt1 - 1, paramInt2 - 1) && this.chunkExists(paramInt1, paramInt2 - 1) && this.chunkExists(paramInt1 - 1, paramInt2)) {
				this.populate(this, paramInt1 - 1, paramInt2 - 1);
			}
		}

		return locallm;
	}

	public Chunk provideChunk(int paramInt1, int paramInt2) {
		Chunk locallm = (Chunk)this.chunkMap.get(ChunkCoordIntPair.chunkXZ2Int(paramInt1, paramInt2));
		return locallm == null ? this.prepareChunk(paramInt1, paramInt2) : locallm;
	}

	private Chunk loadChunkFromFile(int paramInt1, int paramInt2) {
		if(this.chunkLoader == null) {
			return null;
		} else {
			try {
				Chunk localException = this.chunkLoader.loadChunk(this.field_28066_g, paramInt1, paramInt2);
				if(localException != null) {
					localException.lastSaveTime = this.field_28066_g.getWorldTime();
				}

				return localException;
			} catch (Exception exception4) {
				exception4.printStackTrace();
				return null;
			}
		}
	}

	private void func_28063_a(Chunk paramlm) {
		if(this.chunkLoader != null) {
			try {
				this.chunkLoader.saveExtraChunkData(this.field_28066_g, paramlm);
			} catch (Exception exception3) {
				exception3.printStackTrace();
			}

		}
	}

	private void func_28062_b(Chunk paramlm) {
		if(this.chunkLoader != null) {
			try {
				paramlm.lastSaveTime = this.field_28066_g.getWorldTime();
				this.chunkLoader.saveChunk(this.field_28066_g, paramlm);
			} catch (IOException iOException3) {
				iOException3.printStackTrace();
			}

		}
	}

	public void populate(IChunkProvider paramcl, int paramInt1, int paramInt2) {
		Chunk locallm = this.provideChunk(paramInt1, paramInt2);
		if(!locallm.isTerrainPopulated) {
			locallm.isTerrainPopulated = true;
			if(this.chunkProvider != null) {
				this.chunkProvider.populate(paramcl, paramInt1, paramInt2);
				ModLoader.PopulateChunk(this.chunkProvider, paramInt1, paramInt2, this.field_28066_g);
				locallm.setChunkModified();
			}
		}

	}

	public boolean saveChunks(boolean paramBoolean, IProgressUpdate paramyb) {
		int i = 0;

		for(int j = 0; j < this.chunkList.size(); ++j) {
			Chunk locallm = (Chunk)this.chunkList.get(j);
			if(paramBoolean && !locallm.neverSave) {
				this.func_28063_a(locallm);
			}

			if(locallm.needsSaving(paramBoolean)) {
				this.func_28062_b(locallm);
				locallm.isModified = false;
				++i;
				if(i == 24 && !paramBoolean) {
					return false;
				}
			}
		}

		if(paramBoolean) {
			if(this.chunkLoader == null) {
				return true;
			}

			this.chunkLoader.saveExtraData();
		}

		return true;
	}

	public boolean unload100OldestChunks() {
		for(int i = 0; i < 100; ++i) {
			if(!this.droppedChunksSet.isEmpty()) {
				Integer localInteger = (Integer)this.droppedChunksSet.iterator().next();
				Chunk locallm = (Chunk)this.chunkMap.get(localInteger);
				locallm.onChunkUnload();
				this.func_28062_b(locallm);
				this.func_28063_a(locallm);
				this.droppedChunksSet.remove(localInteger);
				this.chunkMap.remove(localInteger);
				this.chunkList.remove(locallm);
			}
		}

		if(this.chunkLoader != null) {
			this.chunkLoader.func_814_a();
		}

		return this.chunkProvider.unload100OldestChunks();
	}

	public boolean canSave() {
		return true;
	}

	public String makeString() {
		return "ServerChunkCache: " + this.chunkMap.size() + " Drop: " + this.droppedChunksSet.size();
	}
}
