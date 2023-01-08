package net.minecraft.src;

public interface IBlockAccess {
	int getBlockId(int i1, int i2, int i3);

	TileEntity getBlockTileEntity(int i1, int i2, int i3);

	float getBrightness(int i1, int i2, int i3, int i4);

	float getLightBrightness(int i1, int i2, int i3);

	int getBlockMetadata(int i1, int i2, int i3);

	Material getBlockMaterial(int i1, int i2, int i3);

	boolean isBlockOpaqueCube(int i1, int i2, int i3);

	boolean isBlockNormalCube(int i1, int i2, int i3);

	WorldChunkManager getWorldChunkManager();
}
