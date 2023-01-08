package net.minecraft.src;

public class PathPoint {
	public final int xCoord;
	public final int yCoord;
	public final int zCoord;
	private final int hash;
	int index = -1;
	float totalPathDistance;
	float distanceToNext;
	float distanceToTarget;
	PathPoint previous;
	public boolean isFirst = false;

	public PathPoint(int i1, int i2, int i3) {
		this.xCoord = i1;
		this.yCoord = i2;
		this.zCoord = i3;
		this.hash = func_22329_a(i1, i2, i3);
	}

	public static int func_22329_a(int i0, int i1, int i2) {
		return i1 & 255 | (i0 & 32767) << 8 | (i2 & 32767) << 24 | (i0 < 0 ? Integer.MIN_VALUE : 0) | (i2 < 0 ? 32768 : 0);
	}

	public float distanceTo(PathPoint pathPoint1) {
		float f2 = (float)(pathPoint1.xCoord - this.xCoord);
		float f3 = (float)(pathPoint1.yCoord - this.yCoord);
		float f4 = (float)(pathPoint1.zCoord - this.zCoord);
		return MathHelper.sqrt_float(f2 * f2 + f3 * f3 + f4 * f4);
	}

	public boolean equals(Object object1) {
		if(!(object1 instanceof PathPoint)) {
			return false;
		} else {
			PathPoint pathPoint2 = (PathPoint)object1;
			return this.hash == pathPoint2.hash && this.xCoord == pathPoint2.xCoord && this.yCoord == pathPoint2.yCoord && this.zCoord == pathPoint2.zCoord;
		}
	}

	public int hashCode() {
		return this.hash;
	}

	public boolean isAssigned() {
		return this.index >= 0;
	}

	public String toString() {
		return this.xCoord + ", " + this.yCoord + ", " + this.zCoord;
	}
}
