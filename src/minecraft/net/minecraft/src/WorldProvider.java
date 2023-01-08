package net.minecraft.src;

public abstract class WorldProvider {
	public World worldObj;
	public WorldChunkManager worldChunkMgr;
	public boolean isNether = false;
	public boolean isHellWorld = false;
	public boolean hasNoSky = false;
	public float[] lightBrightnessTable = new float[16];
	public int worldType = 0;
	private float[] colorsSunriseSunset = new float[4];

	public final void registerWorld(World world1) {
		this.worldObj = world1;
		this.registerWorldChunkManager();
		this.generateLightBrightnessTable();
	}

	protected void generateLightBrightnessTable() {
		float f1 = 0.05F;

		for(int i2 = 0; i2 <= 15; ++i2) {
			float f3 = 1.0F - (float)i2 / 15.0F;
			this.lightBrightnessTable[i2] = (1.0F - f3) / (f3 * 3.0F + 1.0F) * (1.0F - f1) + f1;
		}

	}

	protected void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManager(this.worldObj);
	}

	public IChunkProvider getChunkProvider() {
		return new ChunkProviderGenerate(this.worldObj, this.worldObj.getRandomSeed());
	}

	public boolean canCoordinateBeSpawn(int i1, int i2) {
		int i3 = this.worldObj.getFirstUncoveredBlock(i1, i2);
		return i3 == Block.sand.blockID;
	}

	public float calculateCelestialAngle(long j1, float f3) {
		int i4 = (int)(j1 % 24000L);
		float f5 = ((float)i4 + f3) / 24000.0F - 0.25F;
		if(f5 < 0.0F) {
			++f5;
		}

		if(f5 > 1.0F) {
			--f5;
		}

		float f6 = f5;
		f5 = 1.0F - (float)((Math.cos((double)f5 * Math.PI) + 1.0D) / 2.0D);
		f5 = f6 + (f5 - f6) / 3.0F;
		return f5;
	}

	public float[] calcSunriseSunsetColors(float f1, float f2) {
		float f3 = 0.4F;
		float f4 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) - 0.0F;
		float f5 = -0.0F;
		if(f4 >= f5 - f3 && f4 <= f5 + f3) {
			float f6 = (f4 - f5) / f3 * 0.5F + 0.5F;
			float f7 = 1.0F - (1.0F - MathHelper.sin(f6 * (float)Math.PI)) * 0.99F;
			f7 *= f7;
			this.colorsSunriseSunset[0] = f6 * 0.3F + 0.7F;
			this.colorsSunriseSunset[1] = f6 * f6 * 0.7F + 0.2F;
			this.colorsSunriseSunset[2] = f6 * f6 * 0.0F + 0.2F;
			this.colorsSunriseSunset[3] = f7;
			return this.colorsSunriseSunset;
		} else {
			return null;
		}
	}

	public Vec3D func_4096_a(float f1, float f2) {
		float f3 = MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;
		if(f3 < 0.0F) {
			f3 = 0.0F;
		}

		if(f3 > 1.0F) {
			f3 = 1.0F;
		}

		float f4 = 0.7529412F;
		float f5 = 0.84705883F;
		float f6 = 1.0F;
		f4 *= f3 * 0.94F + 0.06F;
		f5 *= f3 * 0.94F + 0.06F;
		f6 *= f3 * 0.91F + 0.09F;
		return Vec3D.createVector((double)f4, (double)f5, (double)f6);
	}

	public boolean canRespawnHere() {
		return true;
	}

	public static WorldProvider getProviderForDimension(int i0) {
		return (WorldProvider)(i0 == -1 ? new WorldProviderHell() : (i0 == 0 ? new WorldProviderSurface() : (i0 == 1 ? new WorldProviderSky() : null)));
	}

	public float getCloudHeight() {
		return 108.0F;
	}

	public boolean func_28112_c() {
		return true;
	}
}
