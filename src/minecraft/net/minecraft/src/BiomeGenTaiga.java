package net.minecraft.src;

import java.util.Random;

public class BiomeGenTaiga extends BiomeGenBase {
	public BiomeGenTaiga() {
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 2));
	}

	public WorldGenerator getRandomWorldGenForTrees(Random random1) {
		return (WorldGenerator)(random1.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2());
	}
}
