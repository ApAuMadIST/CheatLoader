package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EmptyChunk extends Chunk {
	public EmptyChunk(World world1, int i2, int i3) {
		super(world1, i2, i3);
		this.neverSave = true;
	}

	public EmptyChunk(World world1, byte[] b2, int i3, int i4) {
		super(world1, b2, i3, i4);
		this.neverSave = true;
	}

	public boolean isAtLocation(int i1, int i2) {
		return i1 == this.xPosition && i2 == this.zPosition;
	}

	public int getHeightValue(int i1, int i2) {
		return 0;
	}

	public void func_1014_a() {
	}

	public void generateHeightMap() {
	}

	public void func_1024_c() {
	}

	public void func_4143_d() {
	}

	public int getBlockID(int i1, int i2, int i3) {
		return 0;
	}

	public boolean setBlockIDWithMetadata(int i1, int i2, int i3, int i4, int i5) {
		return true;
	}

	public boolean setBlockID(int i1, int i2, int i3, int i4) {
		return true;
	}

	public int getBlockMetadata(int i1, int i2, int i3) {
		return 0;
	}

	public void setBlockMetadata(int i1, int i2, int i3, int i4) {
	}

	public int getSavedLightValue(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4) {
		return 0;
	}

	public void setLightValue(EnumSkyBlock enumSkyBlock1, int i2, int i3, int i4, int i5) {
	}

	public int getBlockLightValue(int i1, int i2, int i3, int i4) {
		return 0;
	}

	public void addEntity(Entity entity1) {
	}

	public void removeEntity(Entity entity1) {
	}

	public void removeEntityAtIndex(Entity entity1, int i2) {
	}

	public boolean canBlockSeeTheSky(int i1, int i2, int i3) {
		return false;
	}

	public TileEntity getChunkBlockTileEntity(int i1, int i2, int i3) {
		return null;
	}

	public void addTileEntity(TileEntity tileEntity1) {
	}

	public void setChunkBlockTileEntity(int i1, int i2, int i3, TileEntity tileEntity4) {
	}

	public void removeChunkBlockTileEntity(int i1, int i2, int i3) {
	}

	public void onChunkLoad() {
	}

	public void onChunkUnload() {
	}

	public void setChunkModified() {
	}

	public void getEntitiesWithinAABBForEntity(Entity entity1, AxisAlignedBB axisAlignedBB2, List list3) {
	}

	public void getEntitiesOfTypeWithinAAAB(Class class1, AxisAlignedBB axisAlignedBB2, List list3) {
	}

	public boolean needsSaving(boolean z1) {
		return false;
	}

	public int setChunkData(byte[] b1, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
		int i9 = i5 - i2;
		int i10 = i6 - i3;
		int i11 = i7 - i4;
		int i12 = i9 * i10 * i11;
		return i12 + i12 / 2 * 3;
	}

	public Random func_997_a(long j1) {
		return new Random(this.worldObj.getRandomSeed() + (long)(this.xPosition * this.xPosition * 4987142) + (long)(this.xPosition * 5947611) + (long)(this.zPosition * this.zPosition) * 4392871L + (long)(this.zPosition * 389711) ^ j1);
	}

	public boolean func_21167_h() {
		return true;
	}
}
