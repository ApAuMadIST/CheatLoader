package net.minecraft.src;

public class SpawnListEntry {
	public Class entityClass;
	public int spawnRarityRate;

	public SpawnListEntry(Class class1, int i2) {
		this.entityClass = class1;
		this.spawnRarityRate = i2;
	}
}
