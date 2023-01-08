package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public class EffectRenderer {
	protected World worldObj;
	private List[] fxLayers = new List[4];
	private RenderEngine renderer;
	private Random rand = new Random();

	public EffectRenderer(World world1, RenderEngine renderEngine2) {
		if(world1 != null) {
			this.worldObj = world1;
		}

		this.renderer = renderEngine2;

		for(int i3 = 0; i3 < 4; ++i3) {
			this.fxLayers[i3] = new ArrayList();
		}

	}

	public void addEffect(EntityFX entityFX1) {
		int i2 = entityFX1.getFXLayer();
		if(this.fxLayers[i2].size() >= 4000) {
			this.fxLayers[i2].remove(0);
		}

		this.fxLayers[i2].add(entityFX1);
	}

	public void updateEffects() {
		for(int i1 = 0; i1 < 4; ++i1) {
			for(int i2 = 0; i2 < this.fxLayers[i1].size(); ++i2) {
				EntityFX entityFX3 = (EntityFX)this.fxLayers[i1].get(i2);
				entityFX3.onUpdate();
				if(entityFX3.isDead) {
					this.fxLayers[i1].remove(i2--);
				}
			}
		}

	}

	public void renderParticles(Entity entity1, float f2) {
		float f3 = MathHelper.cos(entity1.rotationYaw * (float)Math.PI / 180.0F);
		float f4 = MathHelper.sin(entity1.rotationYaw * (float)Math.PI / 180.0F);
		float f5 = -f4 * MathHelper.sin(entity1.rotationPitch * (float)Math.PI / 180.0F);
		float f6 = f3 * MathHelper.sin(entity1.rotationPitch * (float)Math.PI / 180.0F);
		float f7 = MathHelper.cos(entity1.rotationPitch * (float)Math.PI / 180.0F);
		EntityFX.interpPosX = entity1.lastTickPosX + (entity1.posX - entity1.lastTickPosX) * (double)f2;
		EntityFX.interpPosY = entity1.lastTickPosY + (entity1.posY - entity1.lastTickPosY) * (double)f2;
		EntityFX.interpPosZ = entity1.lastTickPosZ + (entity1.posZ - entity1.lastTickPosZ) * (double)f2;

		for(int i8 = 0; i8 < 3; ++i8) {
			if(this.fxLayers[i8].size() != 0) {
				int i9 = 0;
				if(i8 == 0) {
					i9 = this.renderer.getTexture("/particles.png");
				}

				if(i8 == 1) {
					i9 = this.renderer.getTexture("/terrain.png");
				}

				if(i8 == 2) {
					i9 = this.renderer.getTexture("/gui/items.png");
				}

				GL11.glBindTexture(GL11.GL_TEXTURE_2D, i9);
				Tessellator tessellator10 = Tessellator.instance;
				tessellator10.startDrawingQuads();

				for(int i11 = 0; i11 < this.fxLayers[i8].size(); ++i11) {
					EntityFX entityFX12 = (EntityFX)this.fxLayers[i8].get(i11);
					entityFX12.renderParticle(tessellator10, f2, f3, f7, f4, f5, f6);
				}

				tessellator10.draw();
			}
		}

	}

	public void func_1187_b(Entity entity1, float f2) {
		byte b3 = 3;
		if(this.fxLayers[b3].size() != 0) {
			Tessellator tessellator4 = Tessellator.instance;

			for(int i5 = 0; i5 < this.fxLayers[b3].size(); ++i5) {
				EntityFX entityFX6 = (EntityFX)this.fxLayers[b3].get(i5);
				entityFX6.renderParticle(tessellator4, f2, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
			}

		}
	}

	public void clearEffects(World world1) {
		this.worldObj = world1;

		for(int i2 = 0; i2 < 4; ++i2) {
			this.fxLayers[i2].clear();
		}

	}

	public void addBlockDestroyEffects(int i1, int i2, int i3, int i4, int i5) {
		if(i4 != 0) {
			Block block6 = Block.blocksList[i4];
			byte b7 = 4;

			for(int i8 = 0; i8 < b7; ++i8) {
				for(int i9 = 0; i9 < b7; ++i9) {
					for(int i10 = 0; i10 < b7; ++i10) {
						double d11 = (double)i1 + ((double)i8 + 0.5D) / (double)b7;
						double d13 = (double)i2 + ((double)i9 + 0.5D) / (double)b7;
						double d15 = (double)i3 + ((double)i10 + 0.5D) / (double)b7;
						int i17 = this.rand.nextInt(6);
						this.addEffect((new EntityDiggingFX(this.worldObj, d11, d13, d15, d11 - (double)i1 - 0.5D, d13 - (double)i2 - 0.5D, d15 - (double)i3 - 0.5D, block6, i17, i5)).func_4041_a(i1, i2, i3));
					}
				}
			}

		}
	}

	public void addBlockHitEffects(int i1, int i2, int i3, int i4) {
		int i5 = this.worldObj.getBlockId(i1, i2, i3);
		if(i5 != 0) {
			Block block6 = Block.blocksList[i5];
			float f7 = 0.1F;
			double d8 = (double)i1 + this.rand.nextDouble() * (block6.maxX - block6.minX - (double)(f7 * 2.0F)) + (double)f7 + block6.minX;
			double d10 = (double)i2 + this.rand.nextDouble() * (block6.maxY - block6.minY - (double)(f7 * 2.0F)) + (double)f7 + block6.minY;
			double d12 = (double)i3 + this.rand.nextDouble() * (block6.maxZ - block6.minZ - (double)(f7 * 2.0F)) + (double)f7 + block6.minZ;
			if(i4 == 0) {
				d10 = (double)i2 + block6.minY - (double)f7;
			}

			if(i4 == 1) {
				d10 = (double)i2 + block6.maxY + (double)f7;
			}

			if(i4 == 2) {
				d12 = (double)i3 + block6.minZ - (double)f7;
			}

			if(i4 == 3) {
				d12 = (double)i3 + block6.maxZ + (double)f7;
			}

			if(i4 == 4) {
				d8 = (double)i1 + block6.minX - (double)f7;
			}

			if(i4 == 5) {
				d8 = (double)i1 + block6.maxX + (double)f7;
			}

			this.addEffect((new EntityDiggingFX(this.worldObj, d8, d10, d12, 0.0D, 0.0D, 0.0D, block6, i4, this.worldObj.getBlockMetadata(i1, i2, i3))).func_4041_a(i1, i2, i3).func_407_b(0.2F).func_405_d(0.6F));
		}
	}

	public String getStatistics() {
		return "" + (this.fxLayers[0].size() + this.fxLayers[1].size() + this.fxLayers[2].size());
	}
}
