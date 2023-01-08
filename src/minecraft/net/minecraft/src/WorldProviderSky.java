package net.minecraft.src;

public class WorldProviderSky extends WorldProvider {
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.5D, 0.0D);
		this.worldType = 1;
	}

	public IChunkProvider getChunkProvider() {
		return new ChunkProviderSky(this.worldObj, this.worldObj.getRandomSeed());
	}

	public float calculateCelestialAngle(long j1, float f3) {
		return 0.0F;
	}

	public float[] calcSunriseSunsetColors(float f1, float f2) {
		return null;
	}

	public Vec3D func_4096_a(float f1, float f2) {
		int i3 = 8421536;
		float f4 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
		if(f4 < 0.0F) {
			f4 = 0.0F;
		}

		if(f4 > 1.0F) {
			f4 = 1.0F;
		}

		float f5 = (float)(i3 >> 16 & 255) / 255.0F;
		float f6 = (float)(i3 >> 8 & 255) / 255.0F;
		float f7 = (float)(i3 & 255) / 255.0F;
		f5 *= f4 * 0.94F + 0.06F;
		f6 *= f4 * 0.94F + 0.06F;
		f7 *= f4 * 0.91F + 0.09F;
		return Vec3D.createVector((double)f5, (double)f6, (double)f7);
	}

	public boolean func_28112_c() {
		return false;
	}

	public float getCloudHeight() {
		return 8.0F;
	}

	public boolean canCoordinateBeSpawn(int i1, int i2) {
		int i3 = this.worldObj.getFirstUncoveredBlock(i1, i2);
		return i3 == 0 ? false : Block.blocksList[i3].blockMaterial.getIsSolid();
	}
}
