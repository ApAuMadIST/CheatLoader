package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class ModelRenderer {
	private PositionTextureVertex[] corners;
	private TexturedQuad[] faces;
	private int textureOffsetX;
	private int textureOffsetY;
	public float rotationPointX;
	public float rotationPointY;
	public float rotationPointZ;
	public float rotateAngleX;
	public float rotateAngleY;
	public float rotateAngleZ;
	private boolean compiled = false;
	private int displayList = 0;
	public boolean mirror = false;
	public boolean showModel = true;
	public boolean field_1402_i = false;

	public ModelRenderer(int i1, int i2) {
		this.textureOffsetX = i1;
		this.textureOffsetY = i2;
	}

	public void addBox(float f1, float f2, float f3, int i4, int i5, int i6) {
		this.addBox(f1, f2, f3, i4, i5, i6, 0.0F);
	}

	public void addBox(float f1, float f2, float f3, int i4, int i5, int i6, float f7) {
		this.corners = new PositionTextureVertex[8];
		this.faces = new TexturedQuad[6];
		float f8 = f1 + (float)i4;
		float f9 = f2 + (float)i5;
		float f10 = f3 + (float)i6;
		f1 -= f7;
		f2 -= f7;
		f3 -= f7;
		f8 += f7;
		f9 += f7;
		f10 += f7;
		if(this.mirror) {
			float f11 = f8;
			f8 = f1;
			f1 = f11;
		}

		PositionTextureVertex positionTextureVertex20 = new PositionTextureVertex(f1, f2, f3, 0.0F, 0.0F);
		PositionTextureVertex positionTextureVertex12 = new PositionTextureVertex(f8, f2, f3, 0.0F, 8.0F);
		PositionTextureVertex positionTextureVertex13 = new PositionTextureVertex(f8, f9, f3, 8.0F, 8.0F);
		PositionTextureVertex positionTextureVertex14 = new PositionTextureVertex(f1, f9, f3, 8.0F, 0.0F);
		PositionTextureVertex positionTextureVertex15 = new PositionTextureVertex(f1, f2, f10, 0.0F, 0.0F);
		PositionTextureVertex positionTextureVertex16 = new PositionTextureVertex(f8, f2, f10, 0.0F, 8.0F);
		PositionTextureVertex positionTextureVertex17 = new PositionTextureVertex(f8, f9, f10, 8.0F, 8.0F);
		PositionTextureVertex positionTextureVertex18 = new PositionTextureVertex(f1, f9, f10, 8.0F, 0.0F);
		this.corners[0] = positionTextureVertex20;
		this.corners[1] = positionTextureVertex12;
		this.corners[2] = positionTextureVertex13;
		this.corners[3] = positionTextureVertex14;
		this.corners[4] = positionTextureVertex15;
		this.corners[5] = positionTextureVertex16;
		this.corners[6] = positionTextureVertex17;
		this.corners[7] = positionTextureVertex18;
		this.faces[0] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex16, positionTextureVertex12, positionTextureVertex13, positionTextureVertex17}, this.textureOffsetX + i6 + i4, this.textureOffsetY + i6, this.textureOffsetX + i6 + i4 + i6, this.textureOffsetY + i6 + i5);
		this.faces[1] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex20, positionTextureVertex15, positionTextureVertex18, positionTextureVertex14}, this.textureOffsetX + 0, this.textureOffsetY + i6, this.textureOffsetX + i6, this.textureOffsetY + i6 + i5);
		this.faces[2] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex16, positionTextureVertex15, positionTextureVertex20, positionTextureVertex12}, this.textureOffsetX + i6, this.textureOffsetY + 0, this.textureOffsetX + i6 + i4, this.textureOffsetY + i6);
		this.faces[3] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex13, positionTextureVertex14, positionTextureVertex18, positionTextureVertex17}, this.textureOffsetX + i6 + i4, this.textureOffsetY + 0, this.textureOffsetX + i6 + i4 + i4, this.textureOffsetY + i6);
		this.faces[4] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex12, positionTextureVertex20, positionTextureVertex14, positionTextureVertex13}, this.textureOffsetX + i6, this.textureOffsetY + i6, this.textureOffsetX + i6 + i4, this.textureOffsetY + i6 + i5);
		this.faces[5] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex15, positionTextureVertex16, positionTextureVertex17, positionTextureVertex18}, this.textureOffsetX + i6 + i4 + i6, this.textureOffsetY + i6, this.textureOffsetX + i6 + i4 + i6 + i4, this.textureOffsetY + i6 + i5);
		if(this.mirror) {
			for(int i19 = 0; i19 < this.faces.length; ++i19) {
				this.faces[i19].flipFace();
			}
		}

	}

	public void setRotationPoint(float f1, float f2, float f3) {
		this.rotationPointX = f1;
		this.rotationPointY = f2;
		this.rotationPointZ = f3;
	}

	public void render(float f1) {
		if(!this.field_1402_i) {
			if(this.showModel) {
				if(!this.compiled) {
					this.compileDisplayList(f1);
				}

				if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if(this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
						GL11.glCallList(this.displayList);
					} else {
						GL11.glTranslatef(this.rotationPointX * f1, this.rotationPointY * f1, this.rotationPointZ * f1);
						GL11.glCallList(this.displayList);
						GL11.glTranslatef(-this.rotationPointX * f1, -this.rotationPointY * f1, -this.rotationPointZ * f1);
					}
				} else {
					GL11.glPushMatrix();
					GL11.glTranslatef(this.rotationPointX * f1, this.rotationPointY * f1, this.rotationPointZ * f1);
					if(this.rotateAngleZ != 0.0F) {
						GL11.glRotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
					}

					if(this.rotateAngleY != 0.0F) {
						GL11.glRotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
					}

					if(this.rotateAngleX != 0.0F) {
						GL11.glRotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
					}

					GL11.glCallList(this.displayList);
					GL11.glPopMatrix();
				}

			}
		}
	}

	public void renderWithRotation(float f1) {
		if(!this.field_1402_i) {
			if(this.showModel) {
				if(!this.compiled) {
					this.compileDisplayList(f1);
				}

				GL11.glPushMatrix();
				GL11.glTranslatef(this.rotationPointX * f1, this.rotationPointY * f1, this.rotationPointZ * f1);
				if(this.rotateAngleY != 0.0F) {
					GL11.glRotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
				}

				if(this.rotateAngleX != 0.0F) {
					GL11.glRotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
				}

				if(this.rotateAngleZ != 0.0F) {
					GL11.glRotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
				}

				GL11.glCallList(this.displayList);
				GL11.glPopMatrix();
			}
		}
	}

	public void postRender(float f1) {
		if(!this.field_1402_i) {
			if(this.showModel) {
				if(!this.compiled) {
					this.compileDisplayList(f1);
				}

				if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
					if(this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
						GL11.glTranslatef(this.rotationPointX * f1, this.rotationPointY * f1, this.rotationPointZ * f1);
					}
				} else {
					GL11.glTranslatef(this.rotationPointX * f1, this.rotationPointY * f1, this.rotationPointZ * f1);
					if(this.rotateAngleZ != 0.0F) {
						GL11.glRotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
					}

					if(this.rotateAngleY != 0.0F) {
						GL11.glRotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
					}

					if(this.rotateAngleX != 0.0F) {
						GL11.glRotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
					}
				}

			}
		}
	}

	private void compileDisplayList(float f1) {
		this.displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(this.displayList, GL11.GL_COMPILE);
		Tessellator tessellator2 = Tessellator.instance;

		for(int i3 = 0; i3 < this.faces.length; ++i3) {
			this.faces[i3].draw(tessellator2, f1);
		}

		GL11.glEndList();
		this.compiled = true;
	}
}
