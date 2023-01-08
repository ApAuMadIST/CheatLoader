package net.minecraft.src;

import java.util.Arrays;

public class WorldChunkManagerHell extends WorldChunkManager {
	private BiomeGenBase field_4201_e;
	private double field_4200_f;
	private double field_4199_g;

	public WorldChunkManagerHell(BiomeGenBase biomeGenBase1, double d2, double d4) {
		this.field_4201_e = biomeGenBase1;
		this.field_4200_f = d2;
		this.field_4199_g = d4;
	}

	public BiomeGenBase getBiomeGenAtChunkCoord(ChunkCoordIntPair chunkCoordIntPair1) {
		return this.field_4201_e;
	}

	public BiomeGenBase getBiomeGenAt(int i1, int i2) {
		return this.field_4201_e;
	}

	public double getTemperature(int i1, int i2) {
		return this.field_4200_f;
	}

	public BiomeGenBase[] func_4069_a(int i1, int i2, int i3, int i4) {
		this.field_4195_d = this.loadBlockGeneratorData(this.field_4195_d, i1, i2, i3, i4);
		return this.field_4195_d;
	}

	public double[] getTemperatures(double[] d1, int i2, int i3, int i4, int i5) {
		if(d1 == null || d1.length < i4 * i5) {
			d1 = new double[i4 * i5];
		}

		Arrays.fill(d1, 0, i4 * i5, this.field_4200_f);
		return d1;
	}

	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] biomeGenBase1, int i2, int i3, int i4, int i5) {
		if(biomeGenBase1 == null || biomeGenBase1.length < i4 * i5) {
			biomeGenBase1 = new BiomeGenBase[i4 * i5];
		}

		if(this.temperature == null || this.temperature.length < i4 * i5) {
			this.temperature = new double[i4 * i5];
			this.humidity = new double[i4 * i5];
		}

		Arrays.fill(biomeGenBase1, 0, i4 * i5, this.field_4201_e);
		Arrays.fill(this.humidity, 0, i4 * i5, this.field_4199_g);
		Arrays.fill(this.temperature, 0, i4 * i5, this.field_4200_f);
		return biomeGenBase1;
	}
}
