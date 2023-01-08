package net.minecraft.src;

public class PositionTextureVertex {
	public Vec3D vector3D;
	public float texturePositionX;
	public float texturePositionY;

	public PositionTextureVertex(float f1, float f2, float f3, float f4, float f5) {
		this(Vec3D.createVectorHelper((double)f1, (double)f2, (double)f3), f4, f5);
	}

	public PositionTextureVertex setTexturePosition(float f1, float f2) {
		return new PositionTextureVertex(this, f1, f2);
	}

	public PositionTextureVertex(PositionTextureVertex positionTextureVertex1, float f2, float f3) {
		this.vector3D = positionTextureVertex1.vector3D;
		this.texturePositionX = f2;
		this.texturePositionY = f3;
	}

	public PositionTextureVertex(Vec3D vec3D1, float f2, float f3) {
		this.vector3D = vec3D1;
		this.texturePositionX = f2;
		this.texturePositionY = f3;
	}
}
