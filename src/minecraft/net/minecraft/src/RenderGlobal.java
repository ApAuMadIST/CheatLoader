package net.minecraft.src;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.ARBOcclusionQuery;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class RenderGlobal implements IWorldAccess {
	public List tileEntities = new ArrayList();
	private World worldObj;
	private RenderEngine renderEngine;
	private List worldRenderersToUpdate = new ArrayList();
	private WorldRenderer[] sortedWorldRenderers;
	private WorldRenderer[] worldRenderers;
	private int renderChunksWide;
	private int renderChunksTall;
	private int renderChunksDeep;
	private int glRenderListBase;
	private Minecraft mc;
	private RenderBlocks globalRenderBlocks;
	private IntBuffer glOcclusionQueryBase;
	private boolean occlusionEnabled = false;
	private int cloudOffsetX = 0;
	private int starGLCallList;
	private int glSkyList;
	private int glSkyList2;
	private int minBlockX;
	private int minBlockY;
	private int minBlockZ;
	private int maxBlockX;
	private int maxBlockY;
	private int maxBlockZ;
	private int renderDistance = -1;
	private int renderEntitiesStartupCounter = 2;
	private int countEntitiesTotal;
	private int countEntitiesRendered;
	private int countEntitiesHidden;
	int[] dummyBuf50k = new int[50000];
	IntBuffer occlusionResult = GLAllocation.createDirectIntBuffer(64);
	private int renderersLoaded;
	private int renderersBeingClipped;
	private int renderersBeingOccluded;
	private int renderersBeingRendered;
	private int renderersSkippingRenderPass;
	private int worldRenderersCheckIndex;
	private List glRenderLists = new ArrayList();
	private RenderList[] allRenderLists = new RenderList[]{new RenderList(), new RenderList(), new RenderList(), new RenderList()};
	int dummyInt0 = 0;
	int glDummyList = GLAllocation.generateDisplayLists(1);
	double prevSortX = -9999.0D;
	double prevSortY = -9999.0D;
	double prevSortZ = -9999.0D;
	public float damagePartialTime;
	int frustrumCheckOffset = 0;

	public RenderGlobal(Minecraft minecraft1, RenderEngine renderEngine2) {
		this.mc = minecraft1;
		this.renderEngine = renderEngine2;
		byte b3 = 64;
		this.glRenderListBase = GLAllocation.generateDisplayLists(b3 * b3 * b3 * 3);
		this.occlusionEnabled = minecraft1.getOpenGlCapsChecker().checkARBOcclusion();
		if(this.occlusionEnabled) {
			this.occlusionResult.clear();
			this.glOcclusionQueryBase = GLAllocation.createDirectIntBuffer(b3 * b3 * b3);
			this.glOcclusionQueryBase.clear();
			this.glOcclusionQueryBase.position(0);
			this.glOcclusionQueryBase.limit(b3 * b3 * b3);
			ARBOcclusionQuery.glGenQueriesARB(this.glOcclusionQueryBase);
		}

		this.starGLCallList = GLAllocation.generateDisplayLists(3);
		GL11.glPushMatrix();
		GL11.glNewList(this.starGLCallList, GL11.GL_COMPILE);
		this.renderStars();
		GL11.glEndList();
		GL11.glPopMatrix();
		Tessellator tessellator4 = Tessellator.instance;
		this.glSkyList = this.starGLCallList + 1;
		GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
		byte b6 = 64;
		int i7 = 256 / b6 + 2;
		float f5 = 16.0F;

		int i8;
		int i9;
		for(i8 = -b6 * i7; i8 <= b6 * i7; i8 += b6) {
			for(i9 = -b6 * i7; i9 <= b6 * i7; i9 += b6) {
				tessellator4.startDrawingQuads();
				tessellator4.addVertex((double)(i8 + 0), (double)f5, (double)(i9 + 0));
				tessellator4.addVertex((double)(i8 + b6), (double)f5, (double)(i9 + 0));
				tessellator4.addVertex((double)(i8 + b6), (double)f5, (double)(i9 + b6));
				tessellator4.addVertex((double)(i8 + 0), (double)f5, (double)(i9 + b6));
				tessellator4.draw();
			}
		}

		GL11.glEndList();
		this.glSkyList2 = this.starGLCallList + 2;
		GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
		f5 = -16.0F;
		tessellator4.startDrawingQuads();

		for(i8 = -b6 * i7; i8 <= b6 * i7; i8 += b6) {
			for(i9 = -b6 * i7; i9 <= b6 * i7; i9 += b6) {
				tessellator4.addVertex((double)(i8 + b6), (double)f5, (double)(i9 + 0));
				tessellator4.addVertex((double)(i8 + 0), (double)f5, (double)(i9 + 0));
				tessellator4.addVertex((double)(i8 + 0), (double)f5, (double)(i9 + b6));
				tessellator4.addVertex((double)(i8 + b6), (double)f5, (double)(i9 + b6));
			}
		}

		tessellator4.draw();
		GL11.glEndList();
	}

	private void renderStars() {
		Random random1 = new Random(10842L);
		Tessellator tessellator2 = Tessellator.instance;
		tessellator2.startDrawingQuads();

		for(int i3 = 0; i3 < 1500; ++i3) {
			double d4 = (double)(random1.nextFloat() * 2.0F - 1.0F);
			double d6 = (double)(random1.nextFloat() * 2.0F - 1.0F);
			double d8 = (double)(random1.nextFloat() * 2.0F - 1.0F);
			double d10 = (double)(0.25F + random1.nextFloat() * 0.25F);
			double d12 = d4 * d4 + d6 * d6 + d8 * d8;
			if(d12 < 1.0D && d12 > 0.01D) {
				d12 = 1.0D / Math.sqrt(d12);
				d4 *= d12;
				d6 *= d12;
				d8 *= d12;
				double d14 = d4 * 100.0D;
				double d16 = d6 * 100.0D;
				double d18 = d8 * 100.0D;
				double d20 = Math.atan2(d4, d8);
				double d22 = Math.sin(d20);
				double d24 = Math.cos(d20);
				double d26 = Math.atan2(Math.sqrt(d4 * d4 + d8 * d8), d6);
				double d28 = Math.sin(d26);
				double d30 = Math.cos(d26);
				double d32 = random1.nextDouble() * Math.PI * 2.0D;
				double d34 = Math.sin(d32);
				double d36 = Math.cos(d32);

				for(int i38 = 0; i38 < 4; ++i38) {
					double d39 = 0.0D;
					double d41 = (double)((i38 & 2) - 1) * d10;
					double d43 = (double)((i38 + 1 & 2) - 1) * d10;
					double d47 = d41 * d36 - d43 * d34;
					double d49 = d43 * d36 + d41 * d34;
					double d53 = d47 * d28 + d39 * d30;
					double d55 = d39 * d28 - d47 * d30;
					double d57 = d55 * d22 - d49 * d24;
					double d61 = d49 * d22 + d55 * d24;
					tessellator2.addVertex(d14 + d57, d16 + d53, d18 + d61);
				}
			}
		}

		tessellator2.draw();
	}

	public void changeWorld(World world1) {
		if(this.worldObj != null) {
			this.worldObj.removeWorldAccess(this);
		}

		this.prevSortX = -9999.0D;
		this.prevSortY = -9999.0D;
		this.prevSortZ = -9999.0D;
		RenderManager.instance.func_852_a(world1);
		this.worldObj = world1;
		this.globalRenderBlocks = new RenderBlocks(world1);
		if(world1 != null) {
			world1.addWorldAccess(this);
			this.loadRenderers();
		}

	}

	public void loadRenderers() {
		Block.leaves.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
		this.renderDistance = this.mc.gameSettings.renderDistance;
		int i1;
		if(this.worldRenderers != null) {
			for(i1 = 0; i1 < this.worldRenderers.length; ++i1) {
				this.worldRenderers[i1].func_1204_c();
			}
		}

		i1 = 64 << 3 - this.renderDistance;
		if(i1 > 400) {
			i1 = 400;
		}

		this.renderChunksWide = i1 / 16 + 1;
		this.renderChunksTall = 8;
		this.renderChunksDeep = i1 / 16 + 1;
		this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
		this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall * this.renderChunksDeep];
		int i2 = 0;
		int i3 = 0;
		this.minBlockX = 0;
		this.minBlockY = 0;
		this.minBlockZ = 0;
		this.maxBlockX = this.renderChunksWide;
		this.maxBlockY = this.renderChunksTall;
		this.maxBlockZ = this.renderChunksDeep;

		int i4;
		for(i4 = 0; i4 < this.worldRenderersToUpdate.size(); ++i4) {
			((WorldRenderer)this.worldRenderersToUpdate.get(i4)).needsUpdate = false;
		}

		this.worldRenderersToUpdate.clear();
		this.tileEntities.clear();

		for(i4 = 0; i4 < this.renderChunksWide; ++i4) {
			for(int i5 = 0; i5 < this.renderChunksTall; ++i5) {
				for(int i6 = 0; i6 < this.renderChunksDeep; ++i6) {
					this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4] = new WorldRenderer(this.worldObj, this.tileEntities, i4 * 16, i5 * 16, i6 * 16, 16, this.glRenderListBase + i2);
					if(this.occlusionEnabled) {
						this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].glOcclusionQuery = this.glOcclusionQueryBase.get(i3);
					}

					this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].isWaitingOnOcclusionQuery = false;
					this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].isVisible = true;
					this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].isInFrustum = true;
					this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].chunkIndex = i3++;
					this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4].markDirty();
					this.sortedWorldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4] = this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4];
					this.worldRenderersToUpdate.add(this.worldRenderers[(i6 * this.renderChunksTall + i5) * this.renderChunksWide + i4]);
					i2 += 3;
				}
			}
		}

		if(this.worldObj != null) {
			EntityLiving entityLiving7 = this.mc.renderViewEntity;
			if(entityLiving7 != null) {
				this.markRenderersForNewPosition(MathHelper.floor_double(entityLiving7.posX), MathHelper.floor_double(entityLiving7.posY), MathHelper.floor_double(entityLiving7.posZ));
				Arrays.sort(this.sortedWorldRenderers, new EntitySorter(entityLiving7));
			}
		}

		this.renderEntitiesStartupCounter = 2;
	}

	public void renderEntities(Vec3D vec3D1, ICamera iCamera2, float f3) {
		if(this.renderEntitiesStartupCounter > 0) {
			--this.renderEntitiesStartupCounter;
		} else {
			TileEntityRenderer.instance.cacheActiveRenderInfo(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, f3);
			RenderManager.instance.cacheActiveRenderInfo(this.worldObj, this.renderEngine, this.mc.fontRenderer, this.mc.renderViewEntity, this.mc.gameSettings, f3);
			this.countEntitiesTotal = 0;
			this.countEntitiesRendered = 0;
			this.countEntitiesHidden = 0;
			EntityLiving entityLiving4 = this.mc.renderViewEntity;
			RenderManager.renderPosX = entityLiving4.lastTickPosX + (entityLiving4.posX - entityLiving4.lastTickPosX) * (double)f3;
			RenderManager.renderPosY = entityLiving4.lastTickPosY + (entityLiving4.posY - entityLiving4.lastTickPosY) * (double)f3;
			RenderManager.renderPosZ = entityLiving4.lastTickPosZ + (entityLiving4.posZ - entityLiving4.lastTickPosZ) * (double)f3;
			TileEntityRenderer.staticPlayerX = entityLiving4.lastTickPosX + (entityLiving4.posX - entityLiving4.lastTickPosX) * (double)f3;
			TileEntityRenderer.staticPlayerY = entityLiving4.lastTickPosY + (entityLiving4.posY - entityLiving4.lastTickPosY) * (double)f3;
			TileEntityRenderer.staticPlayerZ = entityLiving4.lastTickPosZ + (entityLiving4.posZ - entityLiving4.lastTickPosZ) * (double)f3;
			List list5 = this.worldObj.getLoadedEntityList();
			this.countEntitiesTotal = list5.size();

			int i6;
			Entity entity7;
			for(i6 = 0; i6 < this.worldObj.weatherEffects.size(); ++i6) {
				entity7 = (Entity)this.worldObj.weatherEffects.get(i6);
				++this.countEntitiesRendered;
				if(entity7.isInRangeToRenderVec3D(vec3D1)) {
					RenderManager.instance.renderEntity(entity7, f3);
				}
			}

			for(i6 = 0; i6 < list5.size(); ++i6) {
				entity7 = (Entity)list5.get(i6);
				if(entity7.isInRangeToRenderVec3D(vec3D1) && (entity7.ignoreFrustumCheck || iCamera2.isBoundingBoxInFrustum(entity7.boundingBox)) && (entity7 != this.mc.renderViewEntity || this.mc.gameSettings.thirdPersonView || this.mc.renderViewEntity.isPlayerSleeping())) {
					int i8 = MathHelper.floor_double(entity7.posY);
					if(i8 < 0) {
						i8 = 0;
					}

					if(i8 >= 128) {
						i8 = 127;
					}

					if(this.worldObj.blockExists(MathHelper.floor_double(entity7.posX), i8, MathHelper.floor_double(entity7.posZ))) {
						++this.countEntitiesRendered;
						RenderManager.instance.renderEntity(entity7, f3);
					}
				}
			}

			for(i6 = 0; i6 < this.tileEntities.size(); ++i6) {
				TileEntityRenderer.instance.renderTileEntity((TileEntity)this.tileEntities.get(i6), f3);
			}

		}
	}

	public String getDebugInfoRenders() {
		return "C: " + this.renderersBeingRendered + "/" + this.renderersLoaded + ". F: " + this.renderersBeingClipped + ", O: " + this.renderersBeingOccluded + ", E: " + this.renderersSkippingRenderPass;
	}

	public String getDebugInfoEntities() {
		return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ". B: " + this.countEntitiesHidden + ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered);
	}

	private void markRenderersForNewPosition(int i1, int i2, int i3) {
		i1 -= 8;
		i2 -= 8;
		i3 -= 8;
		this.minBlockX = Integer.MAX_VALUE;
		this.minBlockY = Integer.MAX_VALUE;
		this.minBlockZ = Integer.MAX_VALUE;
		this.maxBlockX = Integer.MIN_VALUE;
		this.maxBlockY = Integer.MIN_VALUE;
		this.maxBlockZ = Integer.MIN_VALUE;
		int i4 = this.renderChunksWide * 16;
		int i5 = i4 / 2;

		for(int i6 = 0; i6 < this.renderChunksWide; ++i6) {
			int i7 = i6 * 16;
			int i8 = i7 + i5 - i1;
			if(i8 < 0) {
				i8 -= i4 - 1;
			}

			i8 /= i4;
			i7 -= i8 * i4;
			if(i7 < this.minBlockX) {
				this.minBlockX = i7;
			}

			if(i7 > this.maxBlockX) {
				this.maxBlockX = i7;
			}

			for(int i9 = 0; i9 < this.renderChunksDeep; ++i9) {
				int i10 = i9 * 16;
				int i11 = i10 + i5 - i3;
				if(i11 < 0) {
					i11 -= i4 - 1;
				}

				i11 /= i4;
				i10 -= i11 * i4;
				if(i10 < this.minBlockZ) {
					this.minBlockZ = i10;
				}

				if(i10 > this.maxBlockZ) {
					this.maxBlockZ = i10;
				}

				for(int i12 = 0; i12 < this.renderChunksTall; ++i12) {
					int i13 = i12 * 16;
					if(i13 < this.minBlockY) {
						this.minBlockY = i13;
					}

					if(i13 > this.maxBlockY) {
						this.maxBlockY = i13;
					}

					WorldRenderer worldRenderer14 = this.worldRenderers[(i9 * this.renderChunksTall + i12) * this.renderChunksWide + i6];
					boolean z15 = worldRenderer14.needsUpdate;
					worldRenderer14.setPosition(i7, i13, i10);
					if(!z15 && worldRenderer14.needsUpdate) {
						this.worldRenderersToUpdate.add(worldRenderer14);
					}
				}
			}
		}

	}

	public int sortAndRender(EntityLiving entityLiving1, int i2, double d3) {
		for(int i5 = 0; i5 < 10; ++i5) {
			this.worldRenderersCheckIndex = (this.worldRenderersCheckIndex + 1) % this.worldRenderers.length;
			WorldRenderer worldRenderer6 = this.worldRenderers[this.worldRenderersCheckIndex];
			if(worldRenderer6.needsUpdate && !this.worldRenderersToUpdate.contains(worldRenderer6)) {
				this.worldRenderersToUpdate.add(worldRenderer6);
			}
		}

		if(this.mc.gameSettings.renderDistance != this.renderDistance) {
			this.loadRenderers();
		}

		if(i2 == 0) {
			this.renderersLoaded = 0;
			this.renderersBeingClipped = 0;
			this.renderersBeingOccluded = 0;
			this.renderersBeingRendered = 0;
			this.renderersSkippingRenderPass = 0;
		}

		double d33 = entityLiving1.lastTickPosX + (entityLiving1.posX - entityLiving1.lastTickPosX) * d3;
		double d7 = entityLiving1.lastTickPosY + (entityLiving1.posY - entityLiving1.lastTickPosY) * d3;
		double d9 = entityLiving1.lastTickPosZ + (entityLiving1.posZ - entityLiving1.lastTickPosZ) * d3;
		double d11 = entityLiving1.posX - this.prevSortX;
		double d13 = entityLiving1.posY - this.prevSortY;
		double d15 = entityLiving1.posZ - this.prevSortZ;
		if(d11 * d11 + d13 * d13 + d15 * d15 > 16.0D) {
			this.prevSortX = entityLiving1.posX;
			this.prevSortY = entityLiving1.posY;
			this.prevSortZ = entityLiving1.posZ;
			this.markRenderersForNewPosition(MathHelper.floor_double(entityLiving1.posX), MathHelper.floor_double(entityLiving1.posY), MathHelper.floor_double(entityLiving1.posZ));
			Arrays.sort(this.sortedWorldRenderers, new EntitySorter(entityLiving1));
		}

		RenderHelper.disableStandardItemLighting();
		byte b17 = 0;
		int i34;
		if(this.occlusionEnabled && this.mc.gameSettings.advancedOpengl && !this.mc.gameSettings.anaglyph && i2 == 0) {
			byte b18 = 0;
			int i19 = 16;
			this.checkOcclusionQueryResult(b18, i19);

			for(int i20 = b18; i20 < i19; ++i20) {
				this.sortedWorldRenderers[i20].isVisible = true;
			}

			i34 = b17 + this.renderSortedRenderers(b18, i19, i2, d3);

			do {
				int i35 = i19;
				i19 *= 2;
				if(i19 > this.sortedWorldRenderers.length) {
					i19 = this.sortedWorldRenderers.length;
				}

				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glDisable(GL11.GL_FOG);
				GL11.glColorMask(false, false, false, false);
				GL11.glDepthMask(false);
				this.checkOcclusionQueryResult(i35, i19);
				GL11.glPushMatrix();
				float f36 = 0.0F;
				float f21 = 0.0F;
				float f22 = 0.0F;

				for(int i23 = i35; i23 < i19; ++i23) {
					if(this.sortedWorldRenderers[i23].skipAllRenderPasses()) {
						this.sortedWorldRenderers[i23].isInFrustum = false;
					} else {
						if(!this.sortedWorldRenderers[i23].isInFrustum) {
							this.sortedWorldRenderers[i23].isVisible = true;
						}

						if(this.sortedWorldRenderers[i23].isInFrustum && !this.sortedWorldRenderers[i23].isWaitingOnOcclusionQuery) {
							float f24 = MathHelper.sqrt_float(this.sortedWorldRenderers[i23].distanceToEntitySquared(entityLiving1));
							int i25 = (int)(1.0F + f24 / 128.0F);
							if(this.cloudOffsetX % i25 == i23 % i25) {
								WorldRenderer worldRenderer26 = this.sortedWorldRenderers[i23];
								float f27 = (float)((double)worldRenderer26.posXMinus - d33);
								float f28 = (float)((double)worldRenderer26.posYMinus - d7);
								float f29 = (float)((double)worldRenderer26.posZMinus - d9);
								float f30 = f27 - f36;
								float f31 = f28 - f21;
								float f32 = f29 - f22;
								if(f30 != 0.0F || f31 != 0.0F || f32 != 0.0F) {
									GL11.glTranslatef(f30, f31, f32);
									f36 += f30;
									f21 += f31;
									f22 += f32;
								}

								ARBOcclusionQuery.glBeginQueryARB(GL15.GL_SAMPLES_PASSED, this.sortedWorldRenderers[i23].glOcclusionQuery);
								this.sortedWorldRenderers[i23].callOcclusionQueryList();
								ARBOcclusionQuery.glEndQueryARB(GL15.GL_SAMPLES_PASSED);
								this.sortedWorldRenderers[i23].isWaitingOnOcclusionQuery = true;
							}
						}
					}
				}

				GL11.glPopMatrix();
				if(this.mc.gameSettings.anaglyph) {
					if(EntityRenderer.anaglyphField == 0) {
						GL11.glColorMask(false, true, true, true);
					} else {
						GL11.glColorMask(true, false, false, true);
					}
				} else {
					GL11.glColorMask(true, true, true, true);
				}

				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_FOG);
				i34 += this.renderSortedRenderers(i35, i19, i2, d3);
			} while(i19 < this.sortedWorldRenderers.length);
		} else {
			i34 = b17 + this.renderSortedRenderers(0, this.sortedWorldRenderers.length, i2, d3);
		}

		return i34;
	}

	private void checkOcclusionQueryResult(int i1, int i2) {
		for(int i3 = i1; i3 < i2; ++i3) {
			if(this.sortedWorldRenderers[i3].isWaitingOnOcclusionQuery) {
				this.occlusionResult.clear();
				ARBOcclusionQuery.glGetQueryObjectuARB(this.sortedWorldRenderers[i3].glOcclusionQuery, GL15.GL_QUERY_RESULT_AVAILABLE, this.occlusionResult);
				if(this.occlusionResult.get(0) != 0) {
					this.sortedWorldRenderers[i3].isWaitingOnOcclusionQuery = false;
					this.occlusionResult.clear();
					ARBOcclusionQuery.glGetQueryObjectuARB(this.sortedWorldRenderers[i3].glOcclusionQuery, GL15.GL_QUERY_RESULT, this.occlusionResult);
					this.sortedWorldRenderers[i3].isVisible = this.occlusionResult.get(0) != 0;
				}
			}
		}

	}

	private int renderSortedRenderers(int i1, int i2, int i3, double d4) {
		this.glRenderLists.clear();
		int i6 = 0;

		for(int i7 = i1; i7 < i2; ++i7) {
			if(i3 == 0) {
				++this.renderersLoaded;
				if(this.sortedWorldRenderers[i7].skipRenderPass[i3]) {
					++this.renderersSkippingRenderPass;
				} else if(!this.sortedWorldRenderers[i7].isInFrustum) {
					++this.renderersBeingClipped;
				} else if(this.occlusionEnabled && !this.sortedWorldRenderers[i7].isVisible) {
					++this.renderersBeingOccluded;
				} else {
					++this.renderersBeingRendered;
				}
			}

			if(!this.sortedWorldRenderers[i7].skipRenderPass[i3] && this.sortedWorldRenderers[i7].isInFrustum && (!this.occlusionEnabled || this.sortedWorldRenderers[i7].isVisible)) {
				int i8 = this.sortedWorldRenderers[i7].getGLCallListForPass(i3);
				if(i8 >= 0) {
					this.glRenderLists.add(this.sortedWorldRenderers[i7]);
					++i6;
				}
			}
		}

		EntityLiving entityLiving19 = this.mc.renderViewEntity;
		double d20 = entityLiving19.lastTickPosX + (entityLiving19.posX - entityLiving19.lastTickPosX) * d4;
		double d10 = entityLiving19.lastTickPosY + (entityLiving19.posY - entityLiving19.lastTickPosY) * d4;
		double d12 = entityLiving19.lastTickPosZ + (entityLiving19.posZ - entityLiving19.lastTickPosZ) * d4;
		int i14 = 0;

		int i15;
		for(i15 = 0; i15 < this.allRenderLists.length; ++i15) {
			this.allRenderLists[i15].func_859_b();
		}

		for(i15 = 0; i15 < this.glRenderLists.size(); ++i15) {
			WorldRenderer worldRenderer16 = (WorldRenderer)this.glRenderLists.get(i15);
			int i17 = -1;

			for(int i18 = 0; i18 < i14; ++i18) {
				if(this.allRenderLists[i18].func_862_a(worldRenderer16.posXMinus, worldRenderer16.posYMinus, worldRenderer16.posZMinus)) {
					i17 = i18;
				}
			}

			if(i17 < 0) {
				i17 = i14++;
				this.allRenderLists[i17].func_861_a(worldRenderer16.posXMinus, worldRenderer16.posYMinus, worldRenderer16.posZMinus, d20, d10, d12);
			}

			this.allRenderLists[i17].func_858_a(worldRenderer16.getGLCallListForPass(i3));
		}

		this.renderAllRenderLists(i3, d4);
		return i6;
	}

	public void renderAllRenderLists(int i1, double d2) {
		for(int i4 = 0; i4 < this.allRenderLists.length; ++i4) {
			this.allRenderLists[i4].func_860_a();
		}

	}

	public void updateClouds() {
		++this.cloudOffsetX;
	}

	public void renderSky(float f1) {
		if(!this.mc.theWorld.worldProvider.isNether) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			Vec3D vec3D2 = this.worldObj.func_4079_a(this.mc.renderViewEntity, f1);
			float f3 = (float)vec3D2.xCoord;
			float f4 = (float)vec3D2.yCoord;
			float f5 = (float)vec3D2.zCoord;
			float f7;
			float f8;
			if(this.mc.gameSettings.anaglyph) {
				float f6 = (f3 * 30.0F + f4 * 59.0F + f5 * 11.0F) / 100.0F;
				f7 = (f3 * 30.0F + f4 * 70.0F) / 100.0F;
				f8 = (f3 * 30.0F + f5 * 70.0F) / 100.0F;
				f3 = f6;
				f4 = f7;
				f5 = f8;
			}

			GL11.glColor3f(f3, f4, f5);
			Tessellator tessellator17 = Tessellator.instance;
			GL11.glDepthMask(false);
			GL11.glEnable(GL11.GL_FOG);
			GL11.glColor3f(f3, f4, f5);
			GL11.glCallList(this.glSkyList);
			GL11.glDisable(GL11.GL_FOG);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderHelper.disableStandardItemLighting();
			float[] f18 = this.worldObj.worldProvider.calcSunriseSunsetColors(this.worldObj.getCelestialAngle(f1), f1);
			float f9;
			float f10;
			float f11;
			float f12;
			if(f18 != null) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				GL11.glPushMatrix();
				GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
				f8 = this.worldObj.getCelestialAngle(f1);
				GL11.glRotatef(f8 > 0.5F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
				f9 = f18[0];
				f10 = f18[1];
				f11 = f18[2];
				float f14;
				if(this.mc.gameSettings.anaglyph) {
					f12 = (f9 * 30.0F + f10 * 59.0F + f11 * 11.0F) / 100.0F;
					float f13 = (f9 * 30.0F + f10 * 70.0F) / 100.0F;
					f14 = (f9 * 30.0F + f11 * 70.0F) / 100.0F;
					f9 = f12;
					f10 = f13;
					f11 = f14;
				}

				tessellator17.startDrawing(6);
				tessellator17.setColorRGBA_F(f9, f10, f11, f18[3]);
				tessellator17.addVertex(0.0D, 100.0D, 0.0D);
				byte b19 = 16;
				tessellator17.setColorRGBA_F(f18[0], f18[1], f18[2], 0.0F);

				for(int i20 = 0; i20 <= b19; ++i20) {
					f14 = (float)i20 * (float)Math.PI * 2.0F / (float)b19;
					float f15 = MathHelper.sin(f14);
					float f16 = MathHelper.cos(f14);
					tessellator17.addVertex((double)(f15 * 120.0F), (double)(f16 * 120.0F), (double)(-f16 * 40.0F * f18[3]));
				}

				tessellator17.draw();
				GL11.glPopMatrix();
				GL11.glShadeModel(GL11.GL_FLAT);
			}

			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glPushMatrix();
			f7 = 1.0F - this.worldObj.func_27162_g(f1);
			f8 = 0.0F;
			f9 = 0.0F;
			f10 = 0.0F;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, f7);
			GL11.glTranslatef(f8, f9, f10);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(this.worldObj.getCelestialAngle(f1) * 360.0F, 1.0F, 0.0F, 0.0F);
			f11 = 30.0F;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/sun.png"));
			tessellator17.startDrawingQuads();
			tessellator17.addVertexWithUV((double)(-f11), 100.0D, (double)(-f11), 0.0D, 0.0D);
			tessellator17.addVertexWithUV((double)f11, 100.0D, (double)(-f11), 1.0D, 0.0D);
			tessellator17.addVertexWithUV((double)f11, 100.0D, (double)f11, 1.0D, 1.0D);
			tessellator17.addVertexWithUV((double)(-f11), 100.0D, (double)f11, 0.0D, 1.0D);
			tessellator17.draw();
			f11 = 20.0F;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/moon.png"));
			tessellator17.startDrawingQuads();
			tessellator17.addVertexWithUV((double)(-f11), -100.0D, (double)f11, 1.0D, 1.0D);
			tessellator17.addVertexWithUV((double)f11, -100.0D, (double)f11, 0.0D, 1.0D);
			tessellator17.addVertexWithUV((double)f11, -100.0D, (double)(-f11), 0.0D, 0.0D);
			tessellator17.addVertexWithUV((double)(-f11), -100.0D, (double)(-f11), 1.0D, 0.0D);
			tessellator17.draw();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			f12 = this.worldObj.getStarBrightness(f1) * f7;
			if(f12 > 0.0F) {
				GL11.glColor4f(f12, f12, f12, f12);
				GL11.glCallList(this.starGLCallList);
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_FOG);
			GL11.glPopMatrix();
			if(this.worldObj.worldProvider.func_28112_c()) {
				GL11.glColor3f(f3 * 0.2F + 0.04F, f4 * 0.2F + 0.04F, f5 * 0.6F + 0.1F);
			} else {
				GL11.glColor3f(f3, f4, f5);
			}

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glCallList(this.glSkyList2);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(true);
		}
	}

	public void renderClouds(float f1) {
		if(!this.mc.theWorld.worldProvider.isNether) {
			if(this.mc.gameSettings.fancyGraphics) {
				this.renderCloudsFancy(f1);
			} else {
				GL11.glDisable(GL11.GL_CULL_FACE);
				float f2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)f1);
				byte b3 = 32;
				int i4 = 256 / b3;
				Tessellator tessellator5 = Tessellator.instance;
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/environment/clouds.png"));
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				Vec3D vec3D6 = this.worldObj.func_628_d(f1);
				float f7 = (float)vec3D6.xCoord;
				float f8 = (float)vec3D6.yCoord;
				float f9 = (float)vec3D6.zCoord;
				float f10;
				if(this.mc.gameSettings.anaglyph) {
					f10 = (f7 * 30.0F + f8 * 59.0F + f9 * 11.0F) / 100.0F;
					float f11 = (f7 * 30.0F + f8 * 70.0F) / 100.0F;
					float f12 = (f7 * 30.0F + f9 * 70.0F) / 100.0F;
					f7 = f10;
					f8 = f11;
					f9 = f12;
				}

				f10 = 4.8828125E-4F;
				double d22 = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)f1 + (double)(((float)this.cloudOffsetX + f1) * 0.03F);
				double d13 = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)f1;
				int i15 = MathHelper.floor_double(d22 / 2048.0D);
				int i16 = MathHelper.floor_double(d13 / 2048.0D);
				d22 -= (double)(i15 * 2048);
				d13 -= (double)(i16 * 2048);
				float f17 = this.worldObj.worldProvider.getCloudHeight() - f2 + 0.33F;
				float f18 = (float)(d22 * (double)f10);
				float f19 = (float)(d13 * (double)f10);
				tessellator5.startDrawingQuads();
				tessellator5.setColorRGBA_F(f7, f8, f9, 0.8F);

				for(int i20 = -b3 * i4; i20 < b3 * i4; i20 += b3) {
					for(int i21 = -b3 * i4; i21 < b3 * i4; i21 += b3) {
						tessellator5.addVertexWithUV((double)(i20 + 0), (double)f17, (double)(i21 + b3), (double)((float)(i20 + 0) * f10 + f18), (double)((float)(i21 + b3) * f10 + f19));
						tessellator5.addVertexWithUV((double)(i20 + b3), (double)f17, (double)(i21 + b3), (double)((float)(i20 + b3) * f10 + f18), (double)((float)(i21 + b3) * f10 + f19));
						tessellator5.addVertexWithUV((double)(i20 + b3), (double)f17, (double)(i21 + 0), (double)((float)(i20 + b3) * f10 + f18), (double)((float)(i21 + 0) * f10 + f19));
						tessellator5.addVertexWithUV((double)(i20 + 0), (double)f17, (double)(i21 + 0), (double)((float)(i20 + 0) * f10 + f18), (double)((float)(i21 + 0) * f10 + f19));
					}
				}

				tessellator5.draw();
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		}
	}

	public boolean func_27307_a(double d1, double d3, double d5, float f7) {
		return false;
	}

	public void renderCloudsFancy(float f1) {
		GL11.glDisable(GL11.GL_CULL_FACE);
		float f2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)f1);
		Tessellator tessellator3 = Tessellator.instance;
		float f4 = 12.0F;
		float f5 = 4.0F;
		double d6 = (this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)f1 + (double)(((float)this.cloudOffsetX + f1) * 0.03F)) / (double)f4;
		double d8 = (this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)f1) / (double)f4 + (double)0.33F;
		float f10 = this.worldObj.worldProvider.getCloudHeight() - f2 + 0.33F;
		int i11 = MathHelper.floor_double(d6 / 2048.0D);
		int i12 = MathHelper.floor_double(d8 / 2048.0D);
		d6 -= (double)(i11 * 2048);
		d8 -= (double)(i12 * 2048);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/environment/clouds.png"));
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Vec3D vec3D13 = this.worldObj.func_628_d(f1);
		float f14 = (float)vec3D13.xCoord;
		float f15 = (float)vec3D13.yCoord;
		float f16 = (float)vec3D13.zCoord;
		float f17;
		float f18;
		float f19;
		if(this.mc.gameSettings.anaglyph) {
			f17 = (f14 * 30.0F + f15 * 59.0F + f16 * 11.0F) / 100.0F;
			f18 = (f14 * 30.0F + f15 * 70.0F) / 100.0F;
			f19 = (f14 * 30.0F + f16 * 70.0F) / 100.0F;
			f14 = f17;
			f15 = f18;
			f16 = f19;
		}

		f17 = (float)(d6 * 0.0D);
		f18 = (float)(d8 * 0.0D);
		f19 = 0.00390625F;
		f17 = (float)MathHelper.floor_double(d6) * f19;
		f18 = (float)MathHelper.floor_double(d8) * f19;
		float f20 = (float)(d6 - (double)MathHelper.floor_double(d6));
		float f21 = (float)(d8 - (double)MathHelper.floor_double(d8));
		byte b22 = 8;
		byte b23 = 3;
		float f24 = 9.765625E-4F;
		GL11.glScalef(f4, 1.0F, f4);

		for(int i25 = 0; i25 < 2; ++i25) {
			if(i25 == 0) {
				GL11.glColorMask(false, false, false, false);
			} else if(this.mc.gameSettings.anaglyph) {
				if(EntityRenderer.anaglyphField == 0) {
					GL11.glColorMask(false, true, true, true);
				} else {
					GL11.glColorMask(true, false, false, true);
				}
			} else {
				GL11.glColorMask(true, true, true, true);
			}

			for(int i26 = -b23 + 1; i26 <= b23; ++i26) {
				for(int i27 = -b23 + 1; i27 <= b23; ++i27) {
					tessellator3.startDrawingQuads();
					float f28 = (float)(i26 * b22);
					float f29 = (float)(i27 * b22);
					float f30 = f28 - f20;
					float f31 = f29 - f21;
					if(f10 > -f5 - 1.0F) {
						tessellator3.setColorRGBA_F(f14 * 0.7F, f15 * 0.7F, f16 * 0.7F, 0.8F);
						tessellator3.setNormal(0.0F, -1.0F, 0.0F);
						tessellator3.addVertexWithUV((double)(f30 + 0.0F), (double)(f10 + 0.0F), (double)(f31 + (float)b22), (double)((f28 + 0.0F) * f19 + f17), (double)((f29 + (float)b22) * f19 + f18));
						tessellator3.addVertexWithUV((double)(f30 + (float)b22), (double)(f10 + 0.0F), (double)(f31 + (float)b22), (double)((f28 + (float)b22) * f19 + f17), (double)((f29 + (float)b22) * f19 + f18));
						tessellator3.addVertexWithUV((double)(f30 + (float)b22), (double)(f10 + 0.0F), (double)(f31 + 0.0F), (double)((f28 + (float)b22) * f19 + f17), (double)((f29 + 0.0F) * f19 + f18));
						tessellator3.addVertexWithUV((double)(f30 + 0.0F), (double)(f10 + 0.0F), (double)(f31 + 0.0F), (double)((f28 + 0.0F) * f19 + f17), (double)((f29 + 0.0F) * f19 + f18));
					}

					if(f10 <= f5 + 1.0F) {
						tessellator3.setColorRGBA_F(f14, f15, f16, 0.8F);
						tessellator3.setNormal(0.0F, 1.0F, 0.0F);
						tessellator3.addVertexWithUV((double)(f30 + 0.0F), (double)(f10 + f5 - f24), (double)(f31 + (float)b22), (double)((f28 + 0.0F) * f19 + f17), (double)((f29 + (float)b22) * f19 + f18));
						tessellator3.addVertexWithUV((double)(f30 + (float)b22), (double)(f10 + f5 - f24), (double)(f31 + (float)b22), (double)((f28 + (float)b22) * f19 + f17), (double)((f29 + (float)b22) * f19 + f18));
						tessellator3.addVertexWithUV((double)(f30 + (float)b22), (double)(f10 + f5 - f24), (double)(f31 + 0.0F), (double)((f28 + (float)b22) * f19 + f17), (double)((f29 + 0.0F) * f19 + f18));
						tessellator3.addVertexWithUV((double)(f30 + 0.0F), (double)(f10 + f5 - f24), (double)(f31 + 0.0F), (double)((f28 + 0.0F) * f19 + f17), (double)((f29 + 0.0F) * f19 + f18));
					}

					tessellator3.setColorRGBA_F(f14 * 0.9F, f15 * 0.9F, f16 * 0.9F, 0.8F);
					int i32;
					if(i26 > -1) {
						tessellator3.setNormal(-1.0F, 0.0F, 0.0F);

						for(i32 = 0; i32 < b22; ++i32) {
							tessellator3.addVertexWithUV((double)(f30 + (float)i32 + 0.0F), (double)(f10 + 0.0F), (double)(f31 + (float)b22), (double)((f28 + (float)i32 + 0.5F) * f19 + f17), (double)((f29 + (float)b22) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)i32 + 0.0F), (double)(f10 + f5), (double)(f31 + (float)b22), (double)((f28 + (float)i32 + 0.5F) * f19 + f17), (double)((f29 + (float)b22) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)i32 + 0.0F), (double)(f10 + f5), (double)(f31 + 0.0F), (double)((f28 + (float)i32 + 0.5F) * f19 + f17), (double)((f29 + 0.0F) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)i32 + 0.0F), (double)(f10 + 0.0F), (double)(f31 + 0.0F), (double)((f28 + (float)i32 + 0.5F) * f19 + f17), (double)((f29 + 0.0F) * f19 + f18));
						}
					}

					if(i26 <= 1) {
						tessellator3.setNormal(1.0F, 0.0F, 0.0F);

						for(i32 = 0; i32 < b22; ++i32) {
							tessellator3.addVertexWithUV((double)(f30 + (float)i32 + 1.0F - f24), (double)(f10 + 0.0F), (double)(f31 + (float)b22), (double)((f28 + (float)i32 + 0.5F) * f19 + f17), (double)((f29 + (float)b22) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)i32 + 1.0F - f24), (double)(f10 + f5), (double)(f31 + (float)b22), (double)((f28 + (float)i32 + 0.5F) * f19 + f17), (double)((f29 + (float)b22) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)i32 + 1.0F - f24), (double)(f10 + f5), (double)(f31 + 0.0F), (double)((f28 + (float)i32 + 0.5F) * f19 + f17), (double)((f29 + 0.0F) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)i32 + 1.0F - f24), (double)(f10 + 0.0F), (double)(f31 + 0.0F), (double)((f28 + (float)i32 + 0.5F) * f19 + f17), (double)((f29 + 0.0F) * f19 + f18));
						}
					}

					tessellator3.setColorRGBA_F(f14 * 0.8F, f15 * 0.8F, f16 * 0.8F, 0.8F);
					if(i27 > -1) {
						tessellator3.setNormal(0.0F, 0.0F, -1.0F);

						for(i32 = 0; i32 < b22; ++i32) {
							tessellator3.addVertexWithUV((double)(f30 + 0.0F), (double)(f10 + f5), (double)(f31 + (float)i32 + 0.0F), (double)((f28 + 0.0F) * f19 + f17), (double)((f29 + (float)i32 + 0.5F) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)b22), (double)(f10 + f5), (double)(f31 + (float)i32 + 0.0F), (double)((f28 + (float)b22) * f19 + f17), (double)((f29 + (float)i32 + 0.5F) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)b22), (double)(f10 + 0.0F), (double)(f31 + (float)i32 + 0.0F), (double)((f28 + (float)b22) * f19 + f17), (double)((f29 + (float)i32 + 0.5F) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + 0.0F), (double)(f10 + 0.0F), (double)(f31 + (float)i32 + 0.0F), (double)((f28 + 0.0F) * f19 + f17), (double)((f29 + (float)i32 + 0.5F) * f19 + f18));
						}
					}

					if(i27 <= 1) {
						tessellator3.setNormal(0.0F, 0.0F, 1.0F);

						for(i32 = 0; i32 < b22; ++i32) {
							tessellator3.addVertexWithUV((double)(f30 + 0.0F), (double)(f10 + f5), (double)(f31 + (float)i32 + 1.0F - f24), (double)((f28 + 0.0F) * f19 + f17), (double)((f29 + (float)i32 + 0.5F) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)b22), (double)(f10 + f5), (double)(f31 + (float)i32 + 1.0F - f24), (double)((f28 + (float)b22) * f19 + f17), (double)((f29 + (float)i32 + 0.5F) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + (float)b22), (double)(f10 + 0.0F), (double)(f31 + (float)i32 + 1.0F - f24), (double)((f28 + (float)b22) * f19 + f17), (double)((f29 + (float)i32 + 0.5F) * f19 + f18));
							tessellator3.addVertexWithUV((double)(f30 + 0.0F), (double)(f10 + 0.0F), (double)(f31 + (float)i32 + 1.0F - f24), (double)((f28 + 0.0F) * f19 + f17), (double)((f29 + (float)i32 + 0.5F) * f19 + f18));
						}
					}

					tessellator3.draw();
				}
			}
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	public boolean updateRenderers(EntityLiving entityLiving1, boolean z2) {
		boolean z3 = false;
		if(z3) {
			Collections.sort(this.worldRenderersToUpdate, new RenderSorter(entityLiving1));
			int i17 = this.worldRenderersToUpdate.size() - 1;
			int i18 = this.worldRenderersToUpdate.size();

			for(int i19 = 0; i19 < i18; ++i19) {
				WorldRenderer worldRenderer20 = (WorldRenderer)this.worldRenderersToUpdate.get(i17 - i19);
				if(!z2) {
					if(worldRenderer20.distanceToEntitySquared(entityLiving1) > 256.0F) {
						if(worldRenderer20.isInFrustum) {
							if(i19 >= 3) {
								return false;
							}
						} else if(i19 >= 1) {
							return false;
						}
					}
				} else if(!worldRenderer20.isInFrustum) {
					continue;
				}

				worldRenderer20.updateRenderer();
				this.worldRenderersToUpdate.remove(worldRenderer20);
				worldRenderer20.needsUpdate = false;
			}

			return this.worldRenderersToUpdate.size() == 0;
		} else {
			byte b4 = 2;
			RenderSorter renderSorter5 = new RenderSorter(entityLiving1);
			WorldRenderer[] worldRenderer6 = new WorldRenderer[b4];
			ArrayList arrayList7 = null;
			int i8 = this.worldRenderersToUpdate.size();
			int i9 = 0;

			int i10;
			WorldRenderer worldRenderer11;
			int i12;
			int i13;
			label169:
			for(i10 = 0; i10 < i8; ++i10) {
				worldRenderer11 = (WorldRenderer)this.worldRenderersToUpdate.get(i10);
				if(!z2) {
					if(worldRenderer11.distanceToEntitySquared(entityLiving1) > 256.0F) {
						for(i12 = 0; i12 < b4 && (worldRenderer6[i12] == null || renderSorter5.doCompare(worldRenderer6[i12], worldRenderer11) <= 0); ++i12) {
						}

						--i12;
						if(i12 <= 0) {
							continue;
						}

						i13 = i12;

						while(true) {
							--i13;
							if(i13 == 0) {
								worldRenderer6[i12] = worldRenderer11;
								continue label169;
							}

							worldRenderer6[i13 - 1] = worldRenderer6[i13];
						}
					}
				} else if(!worldRenderer11.isInFrustum) {
					continue;
				}

				if(arrayList7 == null) {
					arrayList7 = new ArrayList();
				}

				++i9;
				arrayList7.add(worldRenderer11);
				this.worldRenderersToUpdate.set(i10, (Object)null);
			}

			if(arrayList7 != null) {
				if(arrayList7.size() > 1) {
					Collections.sort(arrayList7, renderSorter5);
				}

				for(i10 = arrayList7.size() - 1; i10 >= 0; --i10) {
					worldRenderer11 = (WorldRenderer)arrayList7.get(i10);
					worldRenderer11.updateRenderer();
					worldRenderer11.needsUpdate = false;
				}
			}

			i10 = 0;

			int i21;
			for(i21 = b4 - 1; i21 >= 0; --i21) {
				WorldRenderer worldRenderer22 = worldRenderer6[i21];
				if(worldRenderer22 != null) {
					if(!worldRenderer22.isInFrustum && i21 != b4 - 1) {
						worldRenderer6[i21] = null;
						worldRenderer6[0] = null;
						break;
					}

					worldRenderer6[i21].updateRenderer();
					worldRenderer6[i21].needsUpdate = false;
					++i10;
				}
			}

			i21 = 0;
			i12 = 0;

			for(i13 = this.worldRenderersToUpdate.size(); i21 != i13; ++i21) {
				WorldRenderer worldRenderer14 = (WorldRenderer)this.worldRenderersToUpdate.get(i21);
				if(worldRenderer14 != null) {
					boolean z15 = false;

					for(int i16 = 0; i16 < b4 && !z15; ++i16) {
						if(worldRenderer14 == worldRenderer6[i16]) {
							z15 = true;
						}
					}

					if(!z15) {
						if(i12 != i21) {
							this.worldRenderersToUpdate.set(i12, worldRenderer14);
						}

						++i12;
					}
				}
			}

			while(true) {
				--i21;
				if(i21 < i12) {
					return i8 == i9 + i10;
				}

				this.worldRenderersToUpdate.remove(i21);
			}
		}
	}

	public void drawBlockBreaking(EntityPlayer entityPlayer1, MovingObjectPosition movingObjectPosition2, int i3, ItemStack itemStack4, float f5) {
		Tessellator tessellator6 = Tessellator.instance;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathHelper.sin((float)System.currentTimeMillis() / 100.0F) * 0.2F + 0.4F) * 0.5F);
		int i8;
		if(i3 == 0) {
			if(this.damagePartialTime > 0.0F) {
				GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR);
				int i7 = this.renderEngine.getTexture("/terrain.png");
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, i7);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
				GL11.glPushMatrix();
				i8 = this.worldObj.getBlockId(movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ);
				Block block9 = i8 > 0 ? Block.blocksList[i8] : null;
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glPolygonOffset(-3.0F, -3.0F);
				GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
				double d10 = entityPlayer1.lastTickPosX + (entityPlayer1.posX - entityPlayer1.lastTickPosX) * (double)f5;
				double d12 = entityPlayer1.lastTickPosY + (entityPlayer1.posY - entityPlayer1.lastTickPosY) * (double)f5;
				double d14 = entityPlayer1.lastTickPosZ + (entityPlayer1.posZ - entityPlayer1.lastTickPosZ) * (double)f5;
				if(block9 == null) {
					block9 = Block.stone;
				}

				GL11.glEnable(GL11.GL_ALPHA_TEST);
				tessellator6.startDrawingQuads();
				tessellator6.setTranslationD(-d10, -d12, -d14);
				tessellator6.disableColor();
				this.globalRenderBlocks.renderBlockUsingTexture(block9, movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ, 240 + (int)(this.damagePartialTime * 10.0F));
				tessellator6.draw();
				tessellator6.setTranslationD(0.0D, 0.0D, 0.0D);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glPolygonOffset(0.0F, 0.0F);
				GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glDepthMask(true);
				GL11.glPopMatrix();
			}
		} else if(itemStack4 != null) {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			float f16 = MathHelper.sin((float)System.currentTimeMillis() / 100.0F) * 0.2F + 0.8F;
			GL11.glColor4f(f16, f16, f16, MathHelper.sin((float)System.currentTimeMillis() / 200.0F) * 0.2F + 0.5F);
			i8 = this.renderEngine.getTexture("/terrain.png");
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, i8);
			int i17 = movingObjectPosition2.blockX;
			int i18 = movingObjectPosition2.blockY;
			int i11 = movingObjectPosition2.blockZ;
			if(movingObjectPosition2.sideHit == 0) {
				--i18;
			}

			if(movingObjectPosition2.sideHit == 1) {
				++i18;
			}

			if(movingObjectPosition2.sideHit == 2) {
				--i11;
			}

			if(movingObjectPosition2.sideHit == 3) {
				++i11;
			}

			if(movingObjectPosition2.sideHit == 4) {
				--i17;
			}

			if(movingObjectPosition2.sideHit == 5) {
				++i17;
			}
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
	}

	public void drawSelectionBox(EntityPlayer entityPlayer1, MovingObjectPosition movingObjectPosition2, int i3, ItemStack itemStack4, float f5) {
		if(i3 == 0 && movingObjectPosition2.typeOfHit == EnumMovingObjectType.TILE) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
			GL11.glLineWidth(2.0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDepthMask(false);
			float f6 = 0.002F;
			int i7 = this.worldObj.getBlockId(movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ);
			if(i7 > 0) {
				Block.blocksList[i7].setBlockBoundsBasedOnState(this.worldObj, movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ);
				double d8 = entityPlayer1.lastTickPosX + (entityPlayer1.posX - entityPlayer1.lastTickPosX) * (double)f5;
				double d10 = entityPlayer1.lastTickPosY + (entityPlayer1.posY - entityPlayer1.lastTickPosY) * (double)f5;
				double d12 = entityPlayer1.lastTickPosZ + (entityPlayer1.posZ - entityPlayer1.lastTickPosZ) * (double)f5;
				this.drawOutlinedBoundingBox(Block.blocksList[i7].getSelectedBoundingBoxFromPool(this.worldObj, movingObjectPosition2.blockX, movingObjectPosition2.blockY, movingObjectPosition2.blockZ).expand((double)f6, (double)f6, (double)f6).getOffsetBoundingBox(-d8, -d10, -d12));
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}

	}

	private void drawOutlinedBoundingBox(AxisAlignedBB axisAlignedBB1) {
		Tessellator tessellator2 = Tessellator.instance;
		tessellator2.startDrawing(3);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
		tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
		tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.minY, axisAlignedBB1.maxZ);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.maxZ);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
		tessellator2.draw();
		tessellator2.startDrawing(3);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
		tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
		tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.maxY, axisAlignedBB1.maxZ);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.maxZ);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
		tessellator2.draw();
		tessellator2.startDrawing(1);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
		tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.minY, axisAlignedBB1.minZ);
		tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.maxY, axisAlignedBB1.minZ);
		tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.minY, axisAlignedBB1.maxZ);
		tessellator2.addVertex(axisAlignedBB1.maxX, axisAlignedBB1.maxY, axisAlignedBB1.maxZ);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.minY, axisAlignedBB1.maxZ);
		tessellator2.addVertex(axisAlignedBB1.minX, axisAlignedBB1.maxY, axisAlignedBB1.maxZ);
		tessellator2.draw();
	}

	public void func_949_a(int i1, int i2, int i3, int i4, int i5, int i6) {
		int i7 = MathHelper.bucketInt(i1, 16);
		int i8 = MathHelper.bucketInt(i2, 16);
		int i9 = MathHelper.bucketInt(i3, 16);
		int i10 = MathHelper.bucketInt(i4, 16);
		int i11 = MathHelper.bucketInt(i5, 16);
		int i12 = MathHelper.bucketInt(i6, 16);

		for(int i13 = i7; i13 <= i10; ++i13) {
			int i14 = i13 % this.renderChunksWide;
			if(i14 < 0) {
				i14 += this.renderChunksWide;
			}

			for(int i15 = i8; i15 <= i11; ++i15) {
				int i16 = i15 % this.renderChunksTall;
				if(i16 < 0) {
					i16 += this.renderChunksTall;
				}

				for(int i17 = i9; i17 <= i12; ++i17) {
					int i18 = i17 % this.renderChunksDeep;
					if(i18 < 0) {
						i18 += this.renderChunksDeep;
					}

					int i19 = (i18 * this.renderChunksTall + i16) * this.renderChunksWide + i14;
					WorldRenderer worldRenderer20 = this.worldRenderers[i19];
					if(!worldRenderer20.needsUpdate) {
						this.worldRenderersToUpdate.add(worldRenderer20);
						worldRenderer20.markDirty();
					}
				}
			}
		}

	}

	public void markBlockAndNeighborsNeedsUpdate(int i1, int i2, int i3) {
		this.func_949_a(i1 - 1, i2 - 1, i3 - 1, i1 + 1, i2 + 1, i3 + 1);
	}

	public void markBlockRangeNeedsUpdate(int i1, int i2, int i3, int i4, int i5, int i6) {
		this.func_949_a(i1 - 1, i2 - 1, i3 - 1, i4 + 1, i5 + 1, i6 + 1);
	}

	public void clipRenderersByFrustrum(ICamera iCamera1, float f2) {
		for(int i3 = 0; i3 < this.worldRenderers.length; ++i3) {
			if(!this.worldRenderers[i3].skipAllRenderPasses() && (!this.worldRenderers[i3].isInFrustum || (i3 + this.frustrumCheckOffset & 15) == 0)) {
				this.worldRenderers[i3].updateInFrustrum(iCamera1);
			}
		}

		++this.frustrumCheckOffset;
	}

	public void playRecord(String string1, int i2, int i3, int i4) {
		if(string1 != null) {
			this.mc.ingameGUI.setRecordPlayingMessage("C418 - " + string1);
		}

		this.mc.sndManager.playStreaming(string1, (float)i2, (float)i3, (float)i4, 1.0F, 1.0F);
	}

	public void playSound(String string1, double d2, double d4, double d6, float f8, float f9) {
		float f10 = 16.0F;
		if(f8 > 1.0F) {
			f10 *= f8;
		}

		if(this.mc.renderViewEntity.getDistanceSq(d2, d4, d6) < (double)(f10 * f10)) {
			this.mc.sndManager.playSound(string1, (float)d2, (float)d4, (float)d6, f8, f9);
		}

	}

	public void spawnParticle(String string1, double d2, double d4, double d6, double d8, double d10, double d12) {
		if(this.mc != null && this.mc.renderViewEntity != null && this.mc.effectRenderer != null) {
			double d14 = this.mc.renderViewEntity.posX - d2;
			double d16 = this.mc.renderViewEntity.posY - d4;
			double d18 = this.mc.renderViewEntity.posZ - d6;
			double d20 = 16.0D;
			if(d14 * d14 + d16 * d16 + d18 * d18 <= d20 * d20) {
				if(string1.equals("bubble")) {
					this.mc.effectRenderer.addEffect(new EntityBubbleFX(this.worldObj, d2, d4, d6, d8, d10, d12));
				} else if(string1.equals("smoke")) {
					this.mc.effectRenderer.addEffect(new EntitySmokeFX(this.worldObj, d2, d4, d6, d8, d10, d12));
				} else if(string1.equals("note")) {
					this.mc.effectRenderer.addEffect(new EntityNoteFX(this.worldObj, d2, d4, d6, d8, d10, d12));
				} else if(string1.equals("portal")) {
					this.mc.effectRenderer.addEffect(new EntityPortalFX(this.worldObj, d2, d4, d6, d8, d10, d12));
				} else if(string1.equals("explode")) {
					this.mc.effectRenderer.addEffect(new EntityExplodeFX(this.worldObj, d2, d4, d6, d8, d10, d12));
				} else if(string1.equals("flame")) {
					this.mc.effectRenderer.addEffect(new EntityFlameFX(this.worldObj, d2, d4, d6, d8, d10, d12));
				} else if(string1.equals("lava")) {
					this.mc.effectRenderer.addEffect(new EntityLavaFX(this.worldObj, d2, d4, d6));
				} else if(string1.equals("footstep")) {
					this.mc.effectRenderer.addEffect(new EntityFootStepFX(this.renderEngine, this.worldObj, d2, d4, d6));
				} else if(string1.equals("splash")) {
					this.mc.effectRenderer.addEffect(new EntitySplashFX(this.worldObj, d2, d4, d6, d8, d10, d12));
				} else if(string1.equals("largesmoke")) {
					this.mc.effectRenderer.addEffect(new EntitySmokeFX(this.worldObj, d2, d4, d6, d8, d10, d12, 2.5F));
				} else if(string1.equals("reddust")) {
					this.mc.effectRenderer.addEffect(new EntityReddustFX(this.worldObj, d2, d4, d6, (float)d8, (float)d10, (float)d12));
				} else if(string1.equals("snowballpoof")) {
					this.mc.effectRenderer.addEffect(new EntitySlimeFX(this.worldObj, d2, d4, d6, Item.snowball));
				} else if(string1.equals("snowshovel")) {
					this.mc.effectRenderer.addEffect(new EntitySnowShovelFX(this.worldObj, d2, d4, d6, d8, d10, d12));
				} else if(string1.equals("slime")) {
					this.mc.effectRenderer.addEffect(new EntitySlimeFX(this.worldObj, d2, d4, d6, Item.slimeBall));
				} else if(string1.equals("heart")) {
					this.mc.effectRenderer.addEffect(new EntityHeartFX(this.worldObj, d2, d4, d6, d8, d10, d12));
				}

			}
		}
	}

	public void obtainEntitySkin(Entity entity1) {
		entity1.updateCloak();
		if(entity1.skinUrl != null) {
			this.renderEngine.obtainImageData(entity1.skinUrl, new ImageBufferDownload());
		}

		if(entity1.cloakUrl != null) {
			this.renderEngine.obtainImageData(entity1.cloakUrl, new ImageBufferDownload());
		}

	}

	public void releaseEntitySkin(Entity entity1) {
		if(entity1.skinUrl != null) {
			this.renderEngine.releaseImageData(entity1.skinUrl);
		}

		if(entity1.cloakUrl != null) {
			this.renderEngine.releaseImageData(entity1.cloakUrl);
		}

	}

	public void updateAllRenderers() {
		for(int i1 = 0; i1 < this.worldRenderers.length; ++i1) {
			if(this.worldRenderers[i1].isChunkLit && !this.worldRenderers[i1].needsUpdate) {
				this.worldRenderersToUpdate.add(this.worldRenderers[i1]);
				this.worldRenderers[i1].markDirty();
			}
		}

	}

	public void doNothingWithTileEntity(int i1, int i2, int i3, TileEntity tileEntity4) {
	}

	public void func_28137_f() {
		GLAllocation.func_28194_b(this.glRenderListBase);
	}

	public void func_28136_a(EntityPlayer entityPlayer1, int i2, int i3, int i4, int i5, int i6) {
		Random random7 = this.worldObj.rand;
		int i16;
		switch(i2) {
		case 1000:
			this.worldObj.playSoundEffect((double)i3, (double)i4, (double)i5, "random.click", 1.0F, 1.0F);
			break;
		case 1001:
			this.worldObj.playSoundEffect((double)i3, (double)i4, (double)i5, "random.click", 1.0F, 1.2F);
			break;
		case 1002:
			this.worldObj.playSoundEffect((double)i3, (double)i4, (double)i5, "random.bow", 1.0F, 1.2F);
			break;
		case 1003:
			if(Math.random() < 0.5D) {
				this.worldObj.playSoundEffect((double)i3 + 0.5D, (double)i4 + 0.5D, (double)i5 + 0.5D, "random.door_open", 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			} else {
				this.worldObj.playSoundEffect((double)i3 + 0.5D, (double)i4 + 0.5D, (double)i5 + 0.5D, "random.door_close", 1.0F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			break;
		case 1004:
			this.worldObj.playSoundEffect((double)((float)i3 + 0.5F), (double)((float)i4 + 0.5F), (double)((float)i5 + 0.5F), "random.fizz", 0.5F, 2.6F + (random7.nextFloat() - random7.nextFloat()) * 0.8F);
			break;
		case 1005:
			if(Item.itemsList[i6] instanceof ItemRecord) {
				this.worldObj.playRecord(((ItemRecord)Item.itemsList[i6]).recordName, i3, i4, i5);
			} else {
				this.worldObj.playRecord((String)null, i3, i4, i5);
			}
			break;
		case 2000:
			int i8 = i6 % 3 - 1;
			int i9 = i6 / 3 % 3 - 1;
			double d10 = (double)i3 + (double)i8 * 0.6D + 0.5D;
			double d12 = (double)i4 + 0.5D;
			double d14 = (double)i5 + (double)i9 * 0.6D + 0.5D;

			for(i16 = 0; i16 < 10; ++i16) {
				double d31 = random7.nextDouble() * 0.2D + 0.01D;
				double d19 = d10 + (double)i8 * 0.01D + (random7.nextDouble() - 0.5D) * (double)i9 * 0.5D;
				double d21 = d12 + (random7.nextDouble() - 0.5D) * 0.5D;
				double d23 = d14 + (double)i9 * 0.01D + (random7.nextDouble() - 0.5D) * (double)i8 * 0.5D;
				double d25 = (double)i8 * d31 + random7.nextGaussian() * 0.01D;
				double d27 = -0.03D + random7.nextGaussian() * 0.01D;
				double d29 = (double)i9 * d31 + random7.nextGaussian() * 0.01D;
				this.spawnParticle("smoke", d19, d21, d23, d25, d27, d29);
			}

			return;
		case 2001:
			i16 = i6 & 255;
			if(i16 > 0) {
				Block block17 = Block.blocksList[i16];
				this.mc.sndManager.playSound(block17.stepSound.stepSoundDir(), (float)i3 + 0.5F, (float)i4 + 0.5F, (float)i5 + 0.5F, (block17.stepSound.getVolume() + 1.0F) / 2.0F, block17.stepSound.getPitch() * 0.8F);
			}

			this.mc.effectRenderer.addBlockDestroyEffects(i3, i4, i5, i6 & 255, i6 >> 8 & 255);
		}

	}
}
