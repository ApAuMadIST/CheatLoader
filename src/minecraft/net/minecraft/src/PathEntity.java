package net.minecraft.src;

public class PathEntity {
	private final PathPoint[] points;
	public final int pathLength;
	private int pathIndex;

	public PathEntity(PathPoint[] pathPoint1) {
		this.points = pathPoint1;
		this.pathLength = pathPoint1.length;
	}

	public void incrementPathIndex() {
		++this.pathIndex;
	}

	public boolean isFinished() {
		return this.pathIndex >= this.points.length;
	}

	public PathPoint func_22328_c() {
		return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
	}

	public Vec3D getPosition(Entity entity1) {
		double d2 = (double)this.points[this.pathIndex].xCoord + (double)((int)(entity1.width + 1.0F)) * 0.5D;
		double d4 = (double)this.points[this.pathIndex].yCoord;
		double d6 = (double)this.points[this.pathIndex].zCoord + (double)((int)(entity1.width + 1.0F)) * 0.5D;
		return Vec3D.createVector(d2, d4, d6);
	}
}
