package net.minecraft.src;

public class TexturedQuad {
	public PositionTextureVertex[] vertexPositions;
	public int nVertices;
	private boolean invertNormal;

	public TexturedQuad(PositionTextureVertex[] positionTextureVertex1) {
		this.nVertices = 0;
		this.invertNormal = false;
		this.vertexPositions = positionTextureVertex1;
		this.nVertices = positionTextureVertex1.length;
	}

	public TexturedQuad(PositionTextureVertex[] positionTextureVertex1, int i2, int i3, int i4, int i5) {
		this(positionTextureVertex1);
		float f6 = 0.0015625F;
		float f7 = 0.003125F;
		positionTextureVertex1[0] = positionTextureVertex1[0].setTexturePosition((float)i4 / 64.0F - f6, (float)i3 / 32.0F + f7);
		positionTextureVertex1[1] = positionTextureVertex1[1].setTexturePosition((float)i2 / 64.0F + f6, (float)i3 / 32.0F + f7);
		positionTextureVertex1[2] = positionTextureVertex1[2].setTexturePosition((float)i2 / 64.0F + f6, (float)i5 / 32.0F - f7);
		positionTextureVertex1[3] = positionTextureVertex1[3].setTexturePosition((float)i4 / 64.0F - f6, (float)i5 / 32.0F - f7);
	}

	public void flipFace() {
		PositionTextureVertex[] positionTextureVertex1 = new PositionTextureVertex[this.vertexPositions.length];

		for(int i2 = 0; i2 < this.vertexPositions.length; ++i2) {
			positionTextureVertex1[i2] = this.vertexPositions[this.vertexPositions.length - i2 - 1];
		}

		this.vertexPositions = positionTextureVertex1;
	}

	public void draw(Tessellator tessellator1, float f2) {
		Vec3D vec3D3 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
		Vec3D vec3D4 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
		Vec3D vec3D5 = vec3D4.crossProduct(vec3D3).normalize();
		tessellator1.startDrawingQuads();
		if(this.invertNormal) {
			tessellator1.setNormal(-((float)vec3D5.xCoord), -((float)vec3D5.yCoord), -((float)vec3D5.zCoord));
		} else {
			tessellator1.setNormal((float)vec3D5.xCoord, (float)vec3D5.yCoord, (float)vec3D5.zCoord);
		}

		for(int i6 = 0; i6 < 4; ++i6) {
			PositionTextureVertex positionTextureVertex7 = this.vertexPositions[i6];
			tessellator1.addVertexWithUV((double)((float)positionTextureVertex7.vector3D.xCoord * f2), (double)((float)positionTextureVertex7.vector3D.yCoord * f2), (double)((float)positionTextureVertex7.vector3D.zCoord * f2), (double)positionTextureVertex7.texturePositionX, (double)positionTextureVertex7.texturePositionY);
		}

		tessellator1.draw();
	}
}
