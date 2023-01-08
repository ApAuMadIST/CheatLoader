package net.minecraft.src;

public class MovingObjectPosition {
	public EnumMovingObjectType typeOfHit;
	public int blockX;
	public int blockY;
	public int blockZ;
	public int sideHit;
	public Vec3D hitVec;
	public Entity entityHit;

	public MovingObjectPosition(int i1, int i2, int i3, int i4, Vec3D vec3D5) {
		this.typeOfHit = EnumMovingObjectType.TILE;
		this.blockX = i1;
		this.blockY = i2;
		this.blockZ = i3;
		this.sideHit = i4;
		this.hitVec = Vec3D.createVector(vec3D5.xCoord, vec3D5.yCoord, vec3D5.zCoord);
	}

	public MovingObjectPosition(Entity entity1) {
		this.typeOfHit = EnumMovingObjectType.ENTITY;
		this.entityHit = entity1;
		this.hitVec = Vec3D.createVector(entity1.posX, entity1.posY, entity1.posZ);
	}
}
