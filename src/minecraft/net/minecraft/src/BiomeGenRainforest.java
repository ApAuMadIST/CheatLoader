package net.minecraft.src;

import java.util.Random;

public class BiomeGenRainforest extends BiomeGenBase {
	public WorldGenerator getRandomWorldGenForTrees(Random random1) {
		return (WorldGenerator)(random1.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees());
	}
}
