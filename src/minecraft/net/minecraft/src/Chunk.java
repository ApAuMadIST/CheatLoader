package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Chunk {
	public static boolean isLit;
	public byte[] blocks;
	public boolean isChunkLoaded;
	public World worldObj;
	public NibbleArray data;
	public NibbleArray skylightMap;
	public NibbleArray blocklightMap;
	public byte[] heightMap;
	public int lowestBlockHeight;
	public final int xPosition;
	public final int zPosition;
	public Map chunkTileEntityMap;
	public List[] entities;
	public boolean isTerrainPopulated;
	public boolean isModified;
	public boolean neverSave;
	public boolean hasEntities;
	public long lastSaveTime;

	public Chunk(World world1, int i2, int i3) {
		this.chunkTileEntityMap = new HashMap();
		this.entities = new List[8];
		this.isTerrainPopulated = false;
		this.isModified = false;
		this.hasEntities = false;
		this.lastSaveTime = 0L;
		this.worldObj = world1;
		this.xPosition = i2;
		this.zPosition = i3;
		this.heightMap = new byte[256];

		for(int i4 = 0; i4 < this.entities.length; ++i4) {
			this.entities[i4] = new ArrayList();
		}

	}

	public Chunk(World world1, byte[] b2, int i3, int i4) {
		this(world1, i3, i4);
		this.blocks = b2;
		this.data = new NibbleArray(b2.length);
		this.skylightMap = new NibbleArray(b2.length);
		this.blocklightMap = new NibbleArray(b2.length);
	}

	public boolean isAtLocation(int i1, int i2) {
		return i1 == this.xPosition && i2 == this.zPosition;
	}

	public int getHeightValue(int i1, int i2) {
		return this.heightMap[i2 << 4 | i1] & 255;
	}

	public void func_1014_a() {
	}

	public void generateHeightMap() {
		int i1 = 127;

		for(int i2 = 0; i2 < 16; ++i2) {
			for(int i3 = 0; i3 < 16; ++i3) {
				int i4 = 127;

				for(int i5 = i2 << 11 | i3 << 7; i4 > 0 && Block.lightOpacity[this.blocks[i5 + i4 - 1] & 255] == 0; --i4) {
				}

				this.heightMap[i3 << 4 | i2] = (byte)i4;
				if(i4 < i1) {
					i1 = i4;
				}
			}
		}

		this.lowestBlockHeight = i1;
		this.isModified = true;
	}

	public void func_1024_c() {
		int i1 = 127;

		int i2;
		int i3;
		for(i2 = 0; i2 < 16; ++i2) {
			for(i3 = 0; i3 < 16; ++i3) {
				int i4 = 127;

				int i5;
				for(i5 = i2 << 11 | i3 << 7; i4 > 0 && Block.lightOpacity[this.blocks[i5 + i4 - 1] & 255] == 0; --i4) {
				}

				this.heightMap[i3 << 4 | i2] = (byte)i4;
				if(i4 < i1) {
					i1 = i4;
				}

				if(!this.worldObj.worldProvider.hasNoSky) {
					int i6 = 15;
					int i7 = 127;

					do {
						i6 -= Block.lightOpacity[this.blocks[i5 + i7] & 255];
						if(i6 > 0) {
							this.skylightMap.setNibble(i2, i7, i3, i6);
						}

						--i7;
					} while(i7 > 0 && i6 > 0);
				}
			}
		}

		this.lowestBlockHeight = i1;

		for(i2 = 0; i2 < 16; ++i2) {
			for(i3 = 0; i3 < 16; ++i3) {
				this.func_996_c(i2, i3);
			}
		}

		this.isModified = true;
	}

	public void func_4143_d() {
	}

	private void func_996_c(int i1, int i2) {
		int i3 = this.getHeightValue(i1, i2);
		int i4 = this.xPosition * 16 + i1;
		int i5 = this.zPosition * 16 + i2;
		this.func_1020_f(i4 - 1, i5, i3);
		this.func_1020_f(i4 + 1, i5, i3);
		this.func_1020_f(i4, i5 - 1, i3);
		this.func_1020_f(i4, i5 + 1, i3);
	}

	private void func_1020_f(int i1, int i2, int i3) {
		int i4 = this.worldObj.getHeightValue(i1, i2);
		if(i4 > i3) {
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, i1, i3, i2, i1, i4, i2);
			this.isModified = true;
		} else if(i4 < i3) {
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, i1, i4, i2, i1, i3, i2);
			this.isModified = true;
		}

	}

	private void func_1003_g(int i1, int i2, int i3) {
		int i4 = this.heightMap[i3 << 4 | i1] & 255;
		int i5 = i4;
		if(i2 > i4) {
			i5 = i2;
		}

		for(int i6 = i1 << 11 | i3 << 7; i5 > 0 && Block.lightOpacity[this.blocks[i6 + i5 - 1] & 255] == 0; --i5) {
		}

		if(i5 != i4) {
			this.worldObj.markBlocksDirtyVertical(i1, i3, i5, i4);
			this.heightMap[i3 << 4 | i1] = (byte)i5;
			int i7;
			int i8;
			int i9;
			if(i5 < this.lowestBlockHeight) {
				this.lowestBlockHeight = i5;
			} else {
				i7 = 127;

				for(i8 = 0; i8 < 16; ++i8) {
					for(i9 = 0; i9 < 16; ++i9) {
						if((this.heightMap[i9 << 4 | i8] & 255) < i7) {
							i7 = this.heightMap[i9 << 4 | i8] & 255;
						}
					}
				}

				this.lowestBlockHeight = i7;
			}

			i7 = this.xPosition * 16 + i1;
			i8 = this.zPosition * 16 + i3;
			if(i5 < i4) {
				for(i9 = i5; i9 < i4; ++i9) {
					this.skylightMap.setNibble(i1, i9, i3, 15);
				}
			} else {
				this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, i7, i4, i8, i7, i5, i8);

				for(i9 = i4; i9 < i5; ++i9) {
					this.skylightMap.setNibble(i1, i9, i3, 0);
				}
			}

			i9 = 15;

			int i10;
			for(i10 = i5; i5 > 0 && i9 > 0; this.skylightMap.setNibble(i1, i5, i3, i9)) {
				--i5;
				int i11 = Block.lightOpacity[this.getBlockID(i1, i5, i3)];
				if(i11 == 0) {
					i11 = 1;
				}

				i9 -= i11;
				if(i9 < 0) {
					i9 = 0;
				}
			}

			while(i5 > 0 && Block.lightOpacity[this.getBlockID(i1, i5 - 1, i3)] == 0) {
				--i5;
			}

			if(i5 != i10) {
				this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, i7 - 1, i5, i8 - 1, i7 + 1, i10, i8 + 1);
			}

			this.isModified = true;
		}
	}

	public int getBlockID(int i1, int i2, int i3) {
		return this.blocks[i1 << 11 | i3 << 7 | i2] & 255;
	}

	public boolean setBlockIDWithMetadata(int i1, int i2, int i3, int i4, int i5) {
		byte b6 = (byte)i4;
		int i7 = this.heightMap[i3 << 4 | i1] & 255;
		int i8 = this.blocks[i1 << 11 | i3 << 7 | i2] & 255;
		if(i8 == i4 && this.data.getNibble(i1, i2, i3) == i5) {
			return false;
		} else {
			int i9 = this.xPosition * 16 + i1;
			int i10 = this.zPosition * 16 + i3;
			this.blocks[i1 << 11 | i3 << 7 | i2] = (byte)(b6 & 255);
			if(i8 != 0 && !this.worldObj.multiplayerWorld) {
				Block.blocksList[i8].onBlockRemoval(this.worldObj, i9, i2, i10);
			}

			this.data.setNibble(i1, i2, i3, i5);
			if(!this.worldObj.worldProvider.hasNoSky) {
				if(Block.lightOpacity[b6 & 255] != 0) {
					if(i2 >= i7) {
						this.func_1003_g(i1, i2 + 1, i3);
					}
				} else if(i2 == i7 - 1) {
					this.func_1003_g(i1, i2, i3);
				}

				this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, i9, i2, i10, i9, i2, i10);
			}

			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Block, i9, i2, i10, i9, i2, i10);
			this.func_996_c(i1, i3);
			this.data.setNibble(i1, i2, i3, i5);
			if(i4 != 0) {
				Block.blocksList[i4].onBlockAdded(this.worldObj, i9, i2, i10);
			}

			this.isModified = true;
			return true;
		}
	}

	public boolean setBlockID(int i1, int i2, int i3, int i4) {
		byte b5 = (byte)i4;
		int i6 = this.heightMap[i3 << 4 | i1] & 255;
		int i7 = this.blocks[i1 << 11 | i3 << 7 | i2] & 255;
		if(i7 == i4) {
			return false;
		} else {
			int i8 = this.xPosition * 16 + i1;
			int i9 = this.zPosition * 16 + i3;
			this.blocks[i1 << 11 | i3 << 7 | i2] = (byte)(b5 & 255);
			if(i7 != 0) {
				Block.blocksList[i7].onBlockRemoval(this.worldObj, i8, i2, i9);
			}

			this.data.setNibble(i1, i2, i3, 0);
			if(Block.lightOpacity[b5 & 255] != 0) {
				if(i2 >= i6) {
					this.func_1003_g(i1, i2 + 1, i3);
				}
			} else if(i2 == i6 - 1) {
				this.func_1003_g(i1, i2, i3);
			}

			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, i8, i2, i9, i8, i2, i9);
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Block, i8, i2, i9, i8, i2, i9);
			this.func_996_c(i1, i3);
			if(i4 != 0 && !this.worldObj.multiplayerWorld) {
				Block.blocksList[i4].onBlockAdded(this.worldObj, i8, i2, i9);
			}

			this.isModified = true;
			return true;
		}
	}

	public int getBlockMetadata(int i1, int i2, int i3) {
		return this.data.getNibble(i1, i2, i3);
	}

	public void setBlockMetadata(int i1, int i2, int i3, int i4) {
		this.isModified = true;
		this.data.setNibble(i1, i2, i3, i4);
	}

	public int getSavedLightValue(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4) {
		return enumSkyBlock1 == EnumSkyBlock.Sky ? this.skylightMap.getNibble(i2, i3, i4) : (enumSkyBlock1 == EnumSkyBlock.Block ? this.blocklightMap.getNibble(i2, i3, i4) : 0);
	}

	public void setLightValue(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4, int i5) {
		this.isModified = true;
		if(enumSkyBlock1 == EnumSkyBlock.Sky) {
			this.skylightMap.setNibble(i2, i3, i4, i5);
		} else {
			if(enumSkyBlock1 != EnumSkyBlock.Block) {
				return;
			}

			this.blocklightMap.setNibble(i2, i3, i4, i5);
		}

	}

	public int getBlockLightValue(int i1, int i2, int i3, int i4) {
		int i5 = this.skylightMap.getNibble(i1, i2, i3);
		if(i5 > 0) {
			isLit = true;
		}

		i5 -= i4;
		int i6 = this.blocklightMap.getNibble(i1, i2, i3);
		if(i6 > i5) {
			i5 = i6;
		}

		return i5;
	}

	public void addEntity(Entity entity1) {
		this.hasEntities = true;
		int i2 = MathHelper.floor_double(entity1.posX / 16.0D);
		int i3 = MathHelper.floor_double(entity1.posZ / 16.0D);
		if(i2 != this.xPosition || i3 != this.zPosition) {
			System.out.println("Wrong location! " + entity1);
			Thread.dumpStack();
		}

		int i4 = MathHelper.floor_double(entity1.posY / 16.0D);
		if(i4 < 0) {
			i4 = 0;
		}

		if(i4 >= this.entities.length) {
			i4 = this.entities.length - 1;
		}

		entity1.addedToChunk = true;
		entity1.chunkCoordX = this.xPosition;
		entity1.chunkCoordY = i4;
		entity1.chunkCoordZ = this.zPosition;
		this.entities[i4].add(entity1);
	}

	public void removeEntity(Entity entity1) {
		this.removeEntityAtIndex(entity1, entity1.chunkCoordY);
	}

	public void removeEntityAtIndex(Entity entity1, int i2) {
		if(i2 < 0) {
			i2 = 0;
		}

		if(i2 >= this.entities.length) {
			i2 = this.entities.length - 1;
		}

		this.entities[i2].remove(entity1);
	}

	public boolean canBlockSeeTheSky(int i1, int i2, int i3) {
		return i2 >= (this.heightMap[i3 << 4 | i1] & 255);
	}

	public TileEntity getChunkBlockTileEntity(int i1, int i2, int i3) {
		ChunkPosition chunkPosition4 = new ChunkPosition(i1, i2, i3);
		TileEntity tileEntity5 = (TileEntity)this.chunkTileEntityMap.get(chunkPosition4);
		if(tileEntity5 == null) {
			int i6 = this.getBlockID(i1, i2, i3);
			if(!Block.isBlockContainer[i6]) {
				return null;
			}

			BlockContainer blockContainer7 = (BlockContainer)Block.blocksList[i6];
			blockContainer7.onBlockAdded(this.worldObj, this.xPosition * 16 + i1, i2, this.zPosition * 16 + i3);
			tileEntity5 = (TileEntity)this.chunkTileEntityMap.get(chunkPosition4);
		}

		if(tileEntity5 != null && tileEntity5.func_31006_g()) {
			this.chunkTileEntityMap.remove(chunkPosition4);
			return null;
		} else {
			return tileEntity5;
		}
	}

	public void addTileEntity(TileEntity tileEntity1) {
		int i2 = tileEntity1.xCoord - this.xPosition * 16;
		int i3 = tileEntity1.yCoord;
		int i4 = tileEntity1.zCoord - this.zPosition * 16;
		this.setChunkBlockTileEntity(i2, i3, i4, tileEntity1);
		if(this.isChunkLoaded) {
			this.worldObj.loadedTileEntityList.add(tileEntity1);
		}

	}

	public void setChunkBlockTileEntity(int i1, int i2, int i3, TileEntity tileEntity4) {
		ChunkPosition chunkPosition5 = new ChunkPosition(i1, i2, i3);
		tileEntity4.worldObj = this.worldObj;
		tileEntity4.xCoord = this.xPosition * 16 + i1;
		tileEntity4.yCoord = i2;
		tileEntity4.zCoord = this.zPosition * 16 + i3;
		if(this.getBlockID(i1, i2, i3) != 0 && Block.blocksList[this.getBlockID(i1, i2, i3)] instanceof BlockContainer) {
			tileEntity4.func_31004_j();
			this.chunkTileEntityMap.put(chunkPosition5, tileEntity4);
		} else {
			System.out.println("Attempted to place a tile entity where there was no entity tile!");
		}
	}

	public void removeChunkBlockTileEntity(int i1, int i2, int i3) {
		ChunkPosition chunkPosition4 = new ChunkPosition(i1, i2, i3);
		if(this.isChunkLoaded) {
			TileEntity tileEntity5 = (TileEntity)this.chunkTileEntityMap.remove(chunkPosition4);
			if(tileEntity5 != null) {
				tileEntity5.func_31005_i();
			}
		}

	}

	public void onChunkLoad() {
		this.isChunkLoaded = true;
		this.worldObj.func_31054_a(this.chunkTileEntityMap.values());

		for(int i1 = 0; i1 < this.entities.length; ++i1) {
			this.worldObj.func_636_a(this.entities[i1]);
		}

	}

	public void onChunkUnload() {
		this.isChunkLoaded = false;
		Iterator iterator1 = this.chunkTileEntityMap.values().iterator();

		while(iterator1.hasNext()) {
			TileEntity tileEntity2 = (TileEntity)iterator1.next();
			tileEntity2.func_31005_i();
		}

		for(int i3 = 0; i3 < this.entities.length; ++i3) {
			this.worldObj.func_632_b(this.entities[i3]);
		}

	}

	public void setChunkModified() {
		this.isModified = true;
	}

	public void getEntitiesWithinAABBForEntity(Entity entity1, AxisAlignedBB axisAlignedBB2, List list3) {
		int i4 = MathHelper.floor_double((axisAlignedBB2.minY - 2.0D) / 16.0D);
		int i5 = MathHelper.floor_double((axisAlignedBB2.maxY + 2.0D) / 16.0D);
		if(i4 < 0) {
			i4 = 0;
		}

		if(i5 >= this.entities.length) {
			i5 = this.entities.length - 1;
		}

		for(int i6 = i4; i6 <= i5; ++i6) {
			List list7 = this.entities[i6];

			for(int i8 = 0; i8 < list7.size(); ++i8) {
				Entity entity9 = (Entity)list7.get(i8);
				if(entity9 != entity1 && entity9.boundingBox.intersectsWith(axisAlignedBB2)) {
					list3.add(entity9);
				}
			}
		}

	}

	public void getEntitiesOfTypeWithinAAAB(Class class1, AxisAlignedBB axisAlignedBB2, List list3) {
		int i4 = MathHelper.floor_double((axisAlignedBB2.minY - 2.0D) / 16.0D);
		int i5 = MathHelper.floor_double((axisAlignedBB2.maxY + 2.0D) / 16.0D);
		if(i4 < 0) {
			i4 = 0;
		}

		if(i5 >= this.entities.length) {
			i5 = this.entities.length - 1;
		}

		for(int i6 = i4; i6 <= i5; ++i6) {
			List list7 = this.entities[i6];

			for(int i8 = 0; i8 < list7.size(); ++i8) {
				Entity entity9 = (Entity)list7.get(i8);
				if(class1.isAssignableFrom(entity9.getClass()) && entity9.boundingBox.intersectsWith(axisAlignedBB2)) {
					list3.add(entity9);
				}
			}
		}

	}

	public boolean needsSaving(boolean z1) {
		if(this.neverSave) {
			return false;
		} else {
			if(z1) {
				if(this.hasEntities && this.worldObj.getWorldTime() != this.lastSaveTime) {
					return true;
				}
			} else if(this.hasEntities && this.worldObj.getWorldTime() >= this.lastSaveTime + 600L) {
				return true;
			}

			return this.isModified;
		}
	}

	public int setChunkData(byte[] b1, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
		int i9;
		int i10;
		int i11;
		int i12;
		for(i9 = i2; i9 < i5; ++i9) {
			for(i10 = i4; i10 < i7; ++i10) {
				i11 = i9 << 11 | i10 << 7 | i3;
				i12 = i6 - i3;
				System.arraycopy(b1, i8, this.blocks, i11, i12);
				i8 += i12;
			}
		}

		this.generateHeightMap();

		for(i9 = i2; i9 < i5; ++i9) {
			for(i10 = i4; i10 < i7; ++i10) {
				i11 = (i9 << 11 | i10 << 7 | i3) >> 1;
				i12 = (i6 - i3) / 2;
				System.arraycopy(b1, i8, this.data.data, i11, i12);
				i8 += i12;
			}
		}

		for(i9 = i2; i9 < i5; ++i9) {
			for(i10 = i4; i10 < i7; ++i10) {
				i11 = (i9 << 11 | i10 << 7 | i3) >> 1;
				i12 = (i6 - i3) / 2;
				System.arraycopy(b1, i8, this.blocklightMap.data, i11, i12);
				i8 += i12;
			}
		}

		for(i9 = i2; i9 < i5; ++i9) {
			for(i10 = i4; i10 < i7; ++i10) {
				i11 = (i9 << 11 | i10 << 7 | i3) >> 1;
				i12 = (i6 - i3) / 2;
				System.arraycopy(b1, i8, this.skylightMap.data, i11, i12);
				i8 += i12;
			}
		}

		return i8;
	}

	public Random func_997_a(long j1) {
		return new Random(this.worldObj.getRandomSeed() + (long)(this.xPosition * this.xPosition * 4987142) + (long)(this.xPosition * 5947611) + (long)(this.zPosition * this.zPosition) * 4392871L + (long)(this.zPosition * 389711) ^ j1);
	}

	public boolean func_21167_h() {
		return false;
	}

	public void func_25124_i() {
		ChunkBlockMap.func_26002_a(this.blocks);
	}
}
