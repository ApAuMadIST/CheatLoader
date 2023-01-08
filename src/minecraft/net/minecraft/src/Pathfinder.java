package net.minecraft.src;

public class Pathfinder {
	private IBlockAccess worldMap;
	private Path path = new Path();
	private MCHash pointMap = new MCHash();
	private PathPoint[] pathOptions = new PathPoint[32];

	public Pathfinder(IBlockAccess iBlockAccess1) {
		this.worldMap = iBlockAccess1;
	}

	public PathEntity createEntityPathTo(Entity entity1, Entity entity2, float f3) {
		return this.createEntityPathTo(entity1, entity2.posX, entity2.boundingBox.minY, entity2.posZ, f3);
	}

	public PathEntity createEntityPathTo(Entity entity1, int i2, int i3, int i4, float f5) {
		return this.createEntityPathTo(entity1, (double)((float)i2 + 0.5F), (double)((float)i3 + 0.5F), (double)((float)i4 + 0.5F), f5);
	}

	private PathEntity createEntityPathTo(Entity entity1, double d2, double d4, double d6, float f8) {
		this.path.clearPath();
		this.pointMap.clearMap();
		PathPoint pathPoint9 = this.openPoint(MathHelper.floor_double(entity1.boundingBox.minX), MathHelper.floor_double(entity1.boundingBox.minY), MathHelper.floor_double(entity1.boundingBox.minZ));
		PathPoint pathPoint10 = this.openPoint(MathHelper.floor_double(d2 - (double)(entity1.width / 2.0F)), MathHelper.floor_double(d4), MathHelper.floor_double(d6 - (double)(entity1.width / 2.0F)));
		PathPoint pathPoint11 = new PathPoint(MathHelper.floor_float(entity1.width + 1.0F), MathHelper.floor_float(entity1.height + 1.0F), MathHelper.floor_float(entity1.width + 1.0F));
		PathEntity pathEntity12 = this.addToPath(entity1, pathPoint9, pathPoint10, pathPoint11, f8);
		return pathEntity12;
	}

	private PathEntity addToPath(Entity entity1, PathPoint pathPoint2, PathPoint pathPoint3, PathPoint pathPoint4, float f5) {
		pathPoint2.totalPathDistance = 0.0F;
		pathPoint2.distanceToNext = pathPoint2.distanceTo(pathPoint3);
		pathPoint2.distanceToTarget = pathPoint2.distanceToNext;
		this.path.clearPath();
		this.path.addPoint(pathPoint2);
		PathPoint pathPoint6 = pathPoint2;

		while(!this.path.isPathEmpty()) {
			PathPoint pathPoint7 = this.path.dequeue();
			if(pathPoint7.equals(pathPoint3)) {
				return this.createEntityPath(pathPoint2, pathPoint3);
			}

			if(pathPoint7.distanceTo(pathPoint3) < pathPoint6.distanceTo(pathPoint3)) {
				pathPoint6 = pathPoint7;
			}

			pathPoint7.isFirst = true;
			int i8 = this.findPathOptions(entity1, pathPoint7, pathPoint4, pathPoint3, f5);

			for(int i9 = 0; i9 < i8; ++i9) {
				PathPoint pathPoint10 = this.pathOptions[i9];
				float f11 = pathPoint7.totalPathDistance + pathPoint7.distanceTo(pathPoint10);
				if(!pathPoint10.isAssigned() || f11 < pathPoint10.totalPathDistance) {
					pathPoint10.previous = pathPoint7;
					pathPoint10.totalPathDistance = f11;
					pathPoint10.distanceToNext = pathPoint10.distanceTo(pathPoint3);
					if(pathPoint10.isAssigned()) {
						this.path.changeDistance(pathPoint10, pathPoint10.totalPathDistance + pathPoint10.distanceToNext);
					} else {
						pathPoint10.distanceToTarget = pathPoint10.totalPathDistance + pathPoint10.distanceToNext;
						this.path.addPoint(pathPoint10);
					}
				}
			}
		}

		if(pathPoint6 == pathPoint2) {
			return null;
		} else {
			return this.createEntityPath(pathPoint2, pathPoint6);
		}
	}

	private int findPathOptions(Entity entity1, PathPoint pathPoint2, PathPoint pathPoint3, PathPoint pathPoint4, float f5) {
		int i6 = 0;
		byte b7 = 0;
		if(this.getVerticalOffset(entity1, pathPoint2.xCoord, pathPoint2.yCoord + 1, pathPoint2.zCoord, pathPoint3) == 1) {
			b7 = 1;
		}

		PathPoint pathPoint8 = this.getSafePoint(entity1, pathPoint2.xCoord, pathPoint2.yCoord, pathPoint2.zCoord + 1, pathPoint3, b7);
		PathPoint pathPoint9 = this.getSafePoint(entity1, pathPoint2.xCoord - 1, pathPoint2.yCoord, pathPoint2.zCoord, pathPoint3, b7);
		PathPoint pathPoint10 = this.getSafePoint(entity1, pathPoint2.xCoord + 1, pathPoint2.yCoord, pathPoint2.zCoord, pathPoint3, b7);
		PathPoint pathPoint11 = this.getSafePoint(entity1, pathPoint2.xCoord, pathPoint2.yCoord, pathPoint2.zCoord - 1, pathPoint3, b7);
		if(pathPoint8 != null && !pathPoint8.isFirst && pathPoint8.distanceTo(pathPoint4) < f5) {
			this.pathOptions[i6++] = pathPoint8;
		}

		if(pathPoint9 != null && !pathPoint9.isFirst && pathPoint9.distanceTo(pathPoint4) < f5) {
			this.pathOptions[i6++] = pathPoint9;
		}

		if(pathPoint10 != null && !pathPoint10.isFirst && pathPoint10.distanceTo(pathPoint4) < f5) {
			this.pathOptions[i6++] = pathPoint10;
		}

		if(pathPoint11 != null && !pathPoint11.isFirst && pathPoint11.distanceTo(pathPoint4) < f5) {
			this.pathOptions[i6++] = pathPoint11;
		}

		return i6;
	}

	private PathPoint getSafePoint(Entity entity1, int i2, int i3, int i4, PathPoint pathPoint5, int i6) {
		PathPoint pathPoint7 = null;
		if(this.getVerticalOffset(entity1, i2, i3, i4, pathPoint5) == 1) {
			pathPoint7 = this.openPoint(i2, i3, i4);
		}

		if(pathPoint7 == null && i6 > 0 && this.getVerticalOffset(entity1, i2, i3 + i6, i4, pathPoint5) == 1) {
			pathPoint7 = this.openPoint(i2, i3 + i6, i4);
			i3 += i6;
		}

		if(pathPoint7 != null) {
			int i8 = 0;
			int i9 = 0;

			while(i3 > 0 && (i9 = this.getVerticalOffset(entity1, i2, i3 - 1, i4, pathPoint5)) == 1) {
				++i8;
				if(i8 >= 4) {
					return null;
				}

				--i3;
				if(i3 > 0) {
					pathPoint7 = this.openPoint(i2, i3, i4);
				}
			}

			if(i9 == -2) {
				return null;
			}
		}

		return pathPoint7;
	}

	private final PathPoint openPoint(int i1, int i2, int i3) {
		int i4 = PathPoint.func_22329_a(i1, i2, i3);
		PathPoint pathPoint5 = (PathPoint)this.pointMap.lookup(i4);
		if(pathPoint5 == null) {
			pathPoint5 = new PathPoint(i1, i2, i3);
			this.pointMap.addKey(i4, pathPoint5);
		}

		return pathPoint5;
	}

	private int getVerticalOffset(Entity entity1, int i2, int i3, int i4, PathPoint pathPoint5) {
		for(int i6 = i2; i6 < i2 + pathPoint5.xCoord; ++i6) {
			for(int i7 = i3; i7 < i3 + pathPoint5.yCoord; ++i7) {
				for(int i8 = i4; i8 < i4 + pathPoint5.zCoord; ++i8) {
					int i9 = this.worldMap.getBlockId(i6, i7, i8);
					if(i9 > 0) {
						if(i9 != Block.doorSteel.blockID && i9 != Block.doorWood.blockID) {
							Material material11 = Block.blocksList[i9].blockMaterial;
							if(material11.getIsSolid()) {
								return 0;
							}

							if(material11 == Material.water) {
								return -1;
							}

							if(material11 == Material.lava) {
								return -2;
							}
						} else {
							int i10 = this.worldMap.getBlockMetadata(i6, i7, i8);
							if(!BlockDoor.isOpen(i10)) {
								return 0;
							}
						}
					}
				}
			}
		}

		return 1;
	}

	private PathEntity createEntityPath(PathPoint pathPoint1, PathPoint pathPoint2) {
		int i3 = 1;

		PathPoint pathPoint4;
		for(pathPoint4 = pathPoint2; pathPoint4.previous != null; pathPoint4 = pathPoint4.previous) {
			++i3;
		}

		PathPoint[] pathPoint5 = new PathPoint[i3];
		pathPoint4 = pathPoint2;
		--i3;

		for(pathPoint5[i3] = pathPoint2; pathPoint4.previous != null; pathPoint5[i3] = pathPoint4) {
			pathPoint4 = pathPoint4.previous;
			--i3;
		}

		return new PathEntity(pathPoint5);
	}
}
