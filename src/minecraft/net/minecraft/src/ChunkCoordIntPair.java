package net.minecraft.src;

public class ChunkCoordIntPair {
	public final int chunkXPos;
	public final int chunkZPos;

	public ChunkCoordIntPair(int i1, int i2) {
		this.chunkXPos = i1;
		this.chunkZPos = i2;
	}

	public static int chunkXZ2Int(int i0, int i1) {
		return (i0 < 0 ? Integer.MIN_VALUE : 0) | (i0 & 32767) << 16 | (i1 < 0 ? 32768 : 0) | i1 & 32767;
	}

	public int hashCode() {
		return chunkXZ2Int(this.chunkXPos, this.chunkZPos);
	}

	public boolean equals(Object object1) {
		ChunkCoordIntPair chunkCoordIntPair2 = (ChunkCoordIntPair)object1;
		return chunkCoordIntPair2.chunkXPos == this.chunkXPos && chunkCoordIntPair2.chunkZPos == this.chunkZPos;
	}
}
