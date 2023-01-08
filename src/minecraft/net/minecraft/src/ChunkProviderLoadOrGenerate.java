package net.minecraft.src;

import java.io.IOException;

public class ChunkProviderLoadOrGenerate implements IChunkProvider {
	private Chunk blankChunk;
	private IChunkProvider chunkProvider;
	private IChunkLoader chunkLoader;
	private Chunk[] chunks;
	private World worldObj;
	int lastQueriedChunkXPos;
	int lastQueriedChunkZPos;
	private Chunk lastQueriedChunk;
	private int curChunkX;
	private int curChunkY;

	public void setCurrentChunkOver(int i1, int i2) {
		this.curChunkX = i1;
		this.curChunkY = i2;
	}

	public boolean canChunkExist(int i1, int i2) {
		byte b3 = 15;
		return i1 >= this.curChunkX - b3 && i2 >= this.curChunkY - b3 && i1 <= this.curChunkX + b3 && i2 <= this.curChunkY + b3;
	}

	public boolean chunkExists(int i1, int i2) {
		if(!this.canChunkExist(i1, i2)) {
			return false;
		} else if(i1 == this.lastQueriedChunkXPos && i2 == this.lastQueriedChunkZPos && this.lastQueriedChunk != null) {
			return true;
		} else {
			int i3 = i1 & 31;
			int i4 = i2 & 31;
			int i5 = i3 + i4 * 32;
			return this.chunks[i5] != null && (this.chunks[i5] == this.blankChunk || this.chunks[i5].isAtLocation(i1, i2));
		}
	}

	public Chunk prepareChunk(int i1, int i2) {
		return this.provideChunk(i1, i2);
	}

	public Chunk provideChunk(int i1, int i2) {
		if(i1 == this.lastQueriedChunkXPos && i2 == this.lastQueriedChunkZPos && this.lastQueriedChunk != null) {
			return this.lastQueriedChunk;
		} else if(!this.worldObj.findingSpawnPoint && !this.canChunkExist(i1, i2)) {
			return this.blankChunk;
		} else {
			int i3 = i1 & 31;
			int i4 = i2 & 31;
			int i5 = i3 + i4 * 32;
			if(!this.chunkExists(i1, i2)) {
				if(this.chunks[i5] != null) {
					this.chunks[i5].onChunkUnload();
					this.saveChunk(this.chunks[i5]);
					this.saveExtraChunkData(this.chunks[i5]);
				}

				Chunk chunk6 = this.func_542_c(i1, i2);
				if(chunk6 == null) {
					if(this.chunkProvider == null) {
						chunk6 = this.blankChunk;
					} else {
						chunk6 = this.chunkProvider.provideChunk(i1, i2);
						chunk6.func_25124_i();
					}
				}

				this.chunks[i5] = chunk6;
				chunk6.func_4143_d();
				if(this.chunks[i5] != null) {
					this.chunks[i5].onChunkLoad();
				}

				if(!this.chunks[i5].isTerrainPopulated && this.chunkExists(i1 + 1, i2 + 1) && this.chunkExists(i1, i2 + 1) && this.chunkExists(i1 + 1, i2)) {
					this.populate(this, i1, i2);
				}

				if(this.chunkExists(i1 - 1, i2) && !this.provideChunk(i1 - 1, i2).isTerrainPopulated && this.chunkExists(i1 - 1, i2 + 1) && this.chunkExists(i1, i2 + 1) && this.chunkExists(i1 - 1, i2)) {
					this.populate(this, i1 - 1, i2);
				}

				if(this.chunkExists(i1, i2 - 1) && !this.provideChunk(i1, i2 - 1).isTerrainPopulated && this.chunkExists(i1 + 1, i2 - 1) && this.chunkExists(i1, i2 - 1) && this.chunkExists(i1 + 1, i2)) {
					this.populate(this, i1, i2 - 1);
				}

				if(this.chunkExists(i1 - 1, i2 - 1) && !this.provideChunk(i1 - 1, i2 - 1).isTerrainPopulated && this.chunkExists(i1 - 1, i2 - 1) && this.chunkExists(i1, i2 - 1) && this.chunkExists(i1 - 1, i2)) {
					this.populate(this, i1 - 1, i2 - 1);
				}
			}

			this.lastQueriedChunkXPos = i1;
			this.lastQueriedChunkZPos = i2;
			this.lastQueriedChunk = this.chunks[i5];
			return this.chunks[i5];
		}
	}

	private Chunk func_542_c(int i1, int i2) {
		if(this.chunkLoader == null) {
			return this.blankChunk;
		} else {
			try {
				Chunk chunk3 = this.chunkLoader.loadChunk(this.worldObj, i1, i2);
				if(chunk3 != null) {
					chunk3.lastSaveTime = this.worldObj.getWorldTime();
				}

				return chunk3;
			} catch (Exception exception4) {
				exception4.printStackTrace();
				return this.blankChunk;
			}
		}
	}

	private void saveExtraChunkData(Chunk chunk1) {
		if(this.chunkLoader != null) {
			try {
				this.chunkLoader.saveExtraChunkData(this.worldObj, chunk1);
			} catch (Exception exception3) {
				exception3.printStackTrace();
			}

		}
	}

	private void saveChunk(Chunk chunk1) {
		if(this.chunkLoader != null) {
			try {
				chunk1.lastSaveTime = this.worldObj.getWorldTime();
				this.chunkLoader.saveChunk(this.worldObj, chunk1);
			} catch (IOException iOException3) {
				iOException3.printStackTrace();
			}

		}
	}

	public void populate(IChunkProvider iChunkProvider1, int i2, int i3) {
		Chunk chunk4 = this.provideChunk(i2, i3);
		if(!chunk4.isTerrainPopulated) {
			chunk4.isTerrainPopulated = true;
			if(this.chunkProvider != null) {
				this.chunkProvider.populate(iChunkProvider1, i2, i3);
				chunk4.setChunkModified();
			}
		}

	}

	public boolean saveChunks(boolean z1, IProgressUpdate iProgressUpdate2) {
		int i3 = 0;
		int i4 = 0;
		int i5;
		if(iProgressUpdate2 != null) {
			for(i5 = 0; i5 < this.chunks.length; ++i5) {
				if(this.chunks[i5] != null && this.chunks[i5].needsSaving(z1)) {
					++i4;
				}
			}
		}

		i5 = 0;

		for(int i6 = 0; i6 < this.chunks.length; ++i6) {
			if(this.chunks[i6] != null) {
				if(z1 && !this.chunks[i6].neverSave) {
					this.saveExtraChunkData(this.chunks[i6]);
				}

				if(this.chunks[i6].needsSaving(z1)) {
					this.saveChunk(this.chunks[i6]);
					this.chunks[i6].isModified = false;
					++i3;
					if(i3 == 2 && !z1) {
						return false;
					}

					if(iProgressUpdate2 != null) {
						++i5;
						if(i5 % 10 == 0) {
							iProgressUpdate2.setLoadingProgress(i5 * 100 / i4);
						}
					}
				}
			}
		}

		if(z1) {
			if(this.chunkLoader == null) {
				return true;
			}

			this.chunkLoader.saveExtraData();
		}

		return true;
	}

	public boolean unload100OldestChunks() {
		if(this.chunkLoader != null) {
			this.chunkLoader.func_814_a();
		}

		return this.chunkProvider.unload100OldestChunks();
	}

	public boolean canSave() {
		return true;
	}

	public String makeString() {
		return "ChunkCache: " + this.chunks.length;
	}
}
