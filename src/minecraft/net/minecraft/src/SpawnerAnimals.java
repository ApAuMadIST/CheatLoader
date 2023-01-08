package net.minecraft.src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class SpawnerAnimals {
	private static Set eligibleChunksForSpawning = new HashSet();
	protected static final Class[] nightSpawnEntities = new Class[]{EntitySpider.class, EntityZombie.class, EntitySkeleton.class};

	protected static ChunkPosition getRandomSpawningPointInChunk(World world0, int i1, int i2) {
		int i3 = i1 + world0.rand.nextInt(16);
		int i4 = world0.rand.nextInt(128);
		int i5 = i2 + world0.rand.nextInt(16);
		return new ChunkPosition(i3, i4, i5);
	}

	public static final int performSpawning(World world0, boolean z1, boolean z2) {
		if(!z1 && !z2) {
			return 0;
		} else {
			eligibleChunksForSpawning.clear();

			int i3;
			int i6;
			for(i3 = 0; i3 < world0.playerEntities.size(); ++i3) {
				EntityPlayer entityPlayer4 = (EntityPlayer)world0.playerEntities.get(i3);
				int i5 = MathHelper.floor_double(entityPlayer4.posX / 16.0D);
				i6 = MathHelper.floor_double(entityPlayer4.posZ / 16.0D);
				byte b7 = 8;

				for(int i8 = -b7; i8 <= b7; ++i8) {
					for(int i9 = -b7; i9 <= b7; ++i9) {
						eligibleChunksForSpawning.add(new ChunkCoordIntPair(i8 + i5, i9 + i6));
					}
				}
			}

			i3 = 0;
			ChunkCoordinates chunkCoordinates35 = world0.getSpawnPoint();
			EnumCreatureType[] enumCreatureType36 = EnumCreatureType.values();
			i6 = enumCreatureType36.length;

			label133:
			for(int i37 = 0; i37 < i6; ++i37) {
				EnumCreatureType enumCreatureType38 = enumCreatureType36[i37];
				if((!enumCreatureType38.getPeacefulCreature() || z2) && (enumCreatureType38.getPeacefulCreature() || z1) && world0.countEntities(enumCreatureType38.getCreatureClass()) <= enumCreatureType38.getMaxNumberOfCreature() * eligibleChunksForSpawning.size() / 256) {
					Iterator iterator39 = eligibleChunksForSpawning.iterator();

					label130:
					while(true) {
						SpawnListEntry spawnListEntry15;
						int i18;
						int i19;
						int i42;
						do {
							do {
								ChunkCoordIntPair chunkCoordIntPair10;
								List list12;
								do {
									do {
										if(!iterator39.hasNext()) {
											continue label133;
										}

										chunkCoordIntPair10 = (ChunkCoordIntPair)iterator39.next();
										BiomeGenBase biomeGenBase11 = world0.getWorldChunkManager().getBiomeGenAtChunkCoord(chunkCoordIntPair10);
										list12 = biomeGenBase11.getSpawnableList(enumCreatureType38);
									} while(list12 == null);
								} while(list12.isEmpty());

								int i13 = 0;

								for(Iterator iterator14 = list12.iterator(); iterator14.hasNext(); i13 += spawnListEntry15.spawnRarityRate) {
									spawnListEntry15 = (SpawnListEntry)iterator14.next();
								}

								int i40 = world0.rand.nextInt(i13);
								spawnListEntry15 = (SpawnListEntry)list12.get(0);
								Iterator iterator16 = list12.iterator();

								while(iterator16.hasNext()) {
									SpawnListEntry spawnListEntry17 = (SpawnListEntry)iterator16.next();
									i40 -= spawnListEntry17.spawnRarityRate;
									if(i40 < 0) {
										spawnListEntry15 = spawnListEntry17;
										break;
									}
								}

								ChunkPosition chunkPosition41 = getRandomSpawningPointInChunk(world0, chunkCoordIntPair10.chunkXPos * 16, chunkCoordIntPair10.chunkZPos * 16);
								i42 = chunkPosition41.x;
								i18 = chunkPosition41.y;
								i19 = chunkPosition41.z;
							} while(world0.isBlockNormalCube(i42, i18, i19));
						} while(world0.getBlockMaterial(i42, i18, i19) != enumCreatureType38.getCreatureMaterial());

						int i20 = 0;

						for(int i21 = 0; i21 < 3; ++i21) {
							int i22 = i42;
							int i23 = i18;
							int i24 = i19;
							byte b25 = 6;

							for(int i26 = 0; i26 < 4; ++i26) {
								i22 += world0.rand.nextInt(b25) - world0.rand.nextInt(b25);
								i23 += world0.rand.nextInt(1) - world0.rand.nextInt(1);
								i24 += world0.rand.nextInt(b25) - world0.rand.nextInt(b25);
								if(canCreatureTypeSpawnAtLocation(enumCreatureType38, world0, i22, i23, i24)) {
									float f27 = (float)i22 + 0.5F;
									float f28 = (float)i23;
									float f29 = (float)i24 + 0.5F;
									if(world0.getClosestPlayer((double)f27, (double)f28, (double)f29, 24.0D) == null) {
										float f30 = f27 - (float)chunkCoordinates35.x;
										float f31 = f28 - (float)chunkCoordinates35.y;
										float f32 = f29 - (float)chunkCoordinates35.z;
										float f33 = f30 * f30 + f31 * f31 + f32 * f32;
										if(f33 >= 576.0F) {
											EntityLiving entityLiving43;
											try {
												entityLiving43 = (EntityLiving)spawnListEntry15.entityClass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world0});
											} catch (Exception exception34) {
												exception34.printStackTrace();
												return i3;
											}

											entityLiving43.setLocationAndAngles((double)f27, (double)f28, (double)f29, world0.rand.nextFloat() * 360.0F, 0.0F);
											if(entityLiving43.getCanSpawnHere()) {
												++i20;
												world0.entityJoinedWorld(entityLiving43);
												creatureSpecificInit(entityLiving43, world0, f27, f28, f29);
												if(i20 >= entityLiving43.getMaxSpawnedInChunk()) {
													continue label130;
												}
											}

											i3 += i20;
										}
									}
								}
							}
						}
					}
				}
			}

			return i3;
		}
	}

	private static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType enumCreatureType0, World world1, int i2, int i3, int i4) {
		return enumCreatureType0.getCreatureMaterial() == Material.water ? world1.getBlockMaterial(i2, i3, i4).getIsLiquid() && !world1.isBlockNormalCube(i2, i3 + 1, i4) : world1.isBlockNormalCube(i2, i3 - 1, i4) && !world1.isBlockNormalCube(i2, i3, i4) && !world1.getBlockMaterial(i2, i3, i4).getIsLiquid() && !world1.isBlockNormalCube(i2, i3 + 1, i4);
	}

	private static void creatureSpecificInit(EntityLiving entityLiving0, World world1, float f2, float f3, float f4) {
		if(entityLiving0 instanceof EntitySpider && world1.rand.nextInt(100) == 0) {
			EntitySkeleton entitySkeleton5 = new EntitySkeleton(world1);
			entitySkeleton5.setLocationAndAngles((double)f2, (double)f3, (double)f4, entityLiving0.rotationYaw, 0.0F);
			world1.entityJoinedWorld(entitySkeleton5);
			entitySkeleton5.mountEntity(entityLiving0);
		} else if(entityLiving0 instanceof EntitySheep) {
			((EntitySheep)entityLiving0).setFleeceColor(EntitySheep.getRandomFleeceColor(world1.rand));
		}

	}

	public static boolean performSleepSpawning(World world0, List list1) {
		boolean z2 = false;
		Pathfinder pathfinder3 = new Pathfinder(world0);
		Iterator iterator4 = list1.iterator();

		while(true) {
			EntityPlayer entityPlayer5;
			Class[] class6;
			do {
				do {
					if(!iterator4.hasNext()) {
						return z2;
					}

					entityPlayer5 = (EntityPlayer)iterator4.next();
					class6 = nightSpawnEntities;
				} while(class6 == null);
			} while(class6.length == 0);

			boolean z7 = false;

			for(int i8 = 0; i8 < 20 && !z7; ++i8) {
				int i9 = MathHelper.floor_double(entityPlayer5.posX) + world0.rand.nextInt(32) - world0.rand.nextInt(32);
				int i10 = MathHelper.floor_double(entityPlayer5.posZ) + world0.rand.nextInt(32) - world0.rand.nextInt(32);
				int i11 = MathHelper.floor_double(entityPlayer5.posY) + world0.rand.nextInt(16) - world0.rand.nextInt(16);
				if(i11 < 1) {
					i11 = 1;
				} else if(i11 > 128) {
					i11 = 128;
				}

				int i12 = world0.rand.nextInt(class6.length);

				int i13;
				for(i13 = i11; i13 > 2 && !world0.isBlockNormalCube(i9, i13 - 1, i10); --i13) {
				}

				while(!canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, world0, i9, i13, i10) && i13 < i11 + 16 && i13 < 128) {
					++i13;
				}

				if(i13 < i11 + 16 && i13 < 128) {
					float f14 = (float)i9 + 0.5F;
					float f15 = (float)i13;
					float f16 = (float)i10 + 0.5F;

					EntityLiving entityLiving17;
					try {
						entityLiving17 = (EntityLiving)class6[i12].getConstructor(new Class[]{World.class}).newInstance(new Object[]{world0});
					} catch (Exception exception21) {
						exception21.printStackTrace();
						return z2;
					}

					entityLiving17.setLocationAndAngles((double)f14, (double)f15, (double)f16, world0.rand.nextFloat() * 360.0F, 0.0F);
					if(entityLiving17.getCanSpawnHere()) {
						PathEntity pathEntity18 = pathfinder3.createEntityPathTo(entityLiving17, entityPlayer5, 32.0F);
						if(pathEntity18 != null && pathEntity18.pathLength > 1) {
							PathPoint pathPoint19 = pathEntity18.func_22328_c();
							if(Math.abs((double)pathPoint19.xCoord - entityPlayer5.posX) < 1.5D && Math.abs((double)pathPoint19.zCoord - entityPlayer5.posZ) < 1.5D && Math.abs((double)pathPoint19.yCoord - entityPlayer5.posY) < 1.5D) {
								ChunkCoordinates chunkCoordinates20 = BlockBed.getNearestEmptyChunkCoordinates(world0, MathHelper.floor_double(entityPlayer5.posX), MathHelper.floor_double(entityPlayer5.posY), MathHelper.floor_double(entityPlayer5.posZ), 1);
								if(chunkCoordinates20 == null) {
									chunkCoordinates20 = new ChunkCoordinates(i9, i13 + 1, i10);
								}

								entityLiving17.setLocationAndAngles((double)((float)chunkCoordinates20.x + 0.5F), (double)chunkCoordinates20.y, (double)((float)chunkCoordinates20.z + 0.5F), 0.0F, 0.0F);
								world0.entityJoinedWorld(entityLiving17);
								creatureSpecificInit(entityLiving17, world0, (float)chunkCoordinates20.x + 0.5F, (float)chunkCoordinates20.y, (float)chunkCoordinates20.z + 0.5F);
								entityPlayer5.wakeUpPlayer(true, false, false);
								entityLiving17.playLivingSound();
								z2 = true;
								z7 = true;
							}
						}
					}
				}
			}
		}
	}
}
