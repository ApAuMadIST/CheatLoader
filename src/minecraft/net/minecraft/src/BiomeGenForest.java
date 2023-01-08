package net.minecraft.src;

import java.util.Random;

public class BiomeGenForest extends BiomeGenBase {
	public BiomeGenForest() {
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 2));
	}

	public WorldGenerator getRandomWorldGenForTrees(Random random1) {
		return (WorldGenerator)(random1.nextInt(5) == 0 ? new WorldGenForest() : (random1.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees()));
	}
}
