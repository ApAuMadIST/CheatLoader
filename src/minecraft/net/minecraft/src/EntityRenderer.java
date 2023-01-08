package net.minecraft.src;

import java.nio.FloatBuffer;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;

public class EntityRenderer {
	public static boolean field_28135_a = false;
	public static int anaglyphField;
	private Minecraft mc;
	private float farPlaneDistance = 0.0F;
	public ItemRenderer itemRenderer;
	private int rendererUpdateCount;
	private Entity pointedEntity = null;
	private MouseFilter mouseFilterXAxis = new MouseFilter();
	private MouseFilter mouseFilterYAxis = new MouseFilter();
	private MouseFilter mouseFilterDummy1 = new MouseFilter();
	private MouseFilter mouseFilterDummy2 = new MouseFilter();
	private MouseFilter mouseFilterDummy3 = new MouseFilter();
	private MouseFilter mouseFilterDummy4 = new MouseFilter();
	private float field_22228_r = 4.0F;
	private float field_22227_s = 4.0F;
	private float field_22226_t = 0.0F;
	private float field_22225_u = 0.0F;
	private float field_22224_v = 0.0F;
	private float field_22223_w = 0.0F;
	private float field_22222_x = 0.0F;
	private float field_22221_y = 0.0F;
	private float field_22220_z = 0.0F;
	private float field_22230_A = 0.0F;
	private boolean cloudFog = false;
	private double cameraZoom = 1.0D;
	private double cameraYaw = 0.0D;
	private double cameraPitch = 0.0D;
	private long prevFrameTime = System.currentTimeMillis();
	private long field_28133_I = 0L;
	private Random random = new Random();
	private int rainSoundCounter = 0;
	volatile int field_1394_b = 0;
	volatile int field_1393_c = 0;
	FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
	float fogColorRed;
	float fogColorGreen;
	float fogColorBlue;
	private float fogColor2;
	private float fogColor1;

	public EntityRenderer(Minecraft minecraft1) {
		this.mc = minecraft1;
		this.itemRenderer = new ItemRenderer(minecraft1);
	}

	public void updateRenderer() {
		this.fogColor2 = this.fogColor1;
		this.field_22227_s = this.field_22228_r;
		this.field_22225_u = this.field_22226_t;
		this.field_22223_w = this.field_22224_v;
		this.field_22221_y = this.field_22222_x;
		this.field_22230_A = this.field_22220_z;
		if(this.mc.renderViewEntity == null) {
			this.mc.renderViewEntity = this.mc.thePlayer;
		}

		float f1 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(this.mc.renderViewEntity.posX), MathHelper.floor_double(this.mc.renderViewEntity.posY), MathHelper.floor_double(this.mc.renderViewEntity.posZ));
		float f2 = (float)(3 - this.mc.gameSettings.renderDistance) / 3.0F;
		float f3 = f1 * (1.0F - f2) + f2;
		this.fogColor1 += (f3 - this.fogColor1) * 0.1F;
		++this.rendererUpdateCount;
		this.itemRenderer.updateEquippedItem();
		this.addRainParticles();
	}

	public void getMouseOver(float f1) {
		if(this.mc.renderViewEntity != null) {
			if(this.mc.theWorld != null) {
				double d2 = (double)this.mc.playerController.getBlockReachDistance();
				this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(d2, f1);
				double d4 = d2;
				Vec3D vec3D6 = this.mc.renderViewEntity.getPosition(f1);
				if(this.mc.objectMouseOver != null) {
					d4 = this.mc.objectMouseOver.hitVec.distanceTo(vec3D6);
				}

				if(this.mc.playerController instanceof PlayerControllerTest) {
					d2 = 32.0D;
					d4 = 32.0D;
				} else {
					if(d4 > 3.0D) {
						d4 = 3.0D;
					}

					d2 = d4;
				}

				Vec3D vec3D7 = this.mc.renderViewEntity.getLook(f1);
				Vec3D vec3D8 = vec3D6.addVector(vec3D7.xCoord * d2, vec3D7.yCoord * d2, vec3D7.zCoord * d2);
				this.pointedEntity = null;
				float f9 = 1.0F;
				List list10 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(vec3D7.xCoord * d2, vec3D7.yCoord * d2, vec3D7.zCoord * d2).expand((double)f9, (double)f9, (double)f9));
				double d11 = 0.0D;

				for(int i13 = 0; i13 < list10.size(); ++i13) {
					Entity entity14 = (Entity)list10.get(i13);
					if(entity14.canBeCollidedWith()) {
						float f15 = entity14.getCollisionBorderSize();
						AxisAlignedBB axisAlignedBB16 = entity14.boundingBox.expand((double)f15, (double)f15, (double)f15);
						MovingObjectPosition movingObjectPosition17 = axisAlignedBB16.func_1169_a(vec3D6, vec3D8);
						if(axisAlignedBB16.isVecInside(vec3D6)) {
							if(0.0D < d11 || d11 == 0.0D) {
								this.pointedEntity = entity14;
								d11 = 0.0D;
							}
						} else if(movingObjectPosition17 != null) {
							double d18 = vec3D6.distanceTo(movingObjectPosition17.hitVec);
							if(d18 < d11 || d11 == 0.0D) {
								this.pointedEntity = entity14;
								d11 = d18;
							}
						}
					}
				}

				if(this.pointedEntity != null && !(this.mc.playerController instanceof PlayerControllerTest)) {
					this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity);
				}

			}
		}
	}

	private float getFOVModifier(float f1) {
		EntityLiving entityLiving2 = this.mc.renderViewEntity;
		float f3 = 70.0F;
		if(entityLiving2.isInsideOfMaterial(Material.water)) {
			f3 = 60.0F;
		}

		if(entityLiving2.health <= 0) {
			float f4 = (float)entityLiving2.deathTime + f1;
			f3 /= (1.0F - 500.0F / (f4 + 500.0F)) * 2.0F + 1.0F;
		}

		return f3 + this.field_22221_y + (this.field_22222_x - this.field_22221_y) * f1;
	}

	private void hurtCameraEffect(float f1) {
		EntityLiving entityLiving2 = this.mc.renderViewEntity;
		float f3 = (float)entityLiving2.hurtTime - f1;
		float f4;
		if(entityLiving2.health <= 0) {
			f4 = (float)entityLiving2.deathTime + f1;
			GL11.glRotatef(40.0F - 8000.0F / (f4 + 200.0F), 0.0F, 0.0F, 1.0F);
		}

		if(f3 >= 0.0F) {
			f3 /= (float)entityLiving2.maxHurtTime;
			f3 = MathHelper.sin(f3 * f3 * f3 * f3 * (float)Math.PI);
			f4 = entityLiving2.attackedAtYaw;
			GL11.glRotatef(-f4, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-f3 * 14.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(f4, 0.0F, 1.0F, 0.0F);
		}
	}

	private void setupViewBobbing(float f1) {
		if(this.mc.renderViewEntity instanceof EntityPlayer) {
			EntityPlayer entityPlayer2 = (EntityPlayer)this.mc.renderViewEntity;
			float f3 = entityPlayer2.distanceWalkedModified - entityPlayer2.prevDistanceWalkedModified;
			float f4 = -(entityPlayer2.distanceWalkedModified + f3 * f1);
			float f5 = entityPlayer2.field_775_e + (entityPlayer2.field_774_f - entityPlayer2.field_775_e) * f1;
			float f6 = entityPlayer2.cameraPitch + (entityPlayer2.field_9328_R - entityPlayer2.cameraPitch) * f1;
			GL11.glTranslatef(MathHelper.sin(f4 * (float)Math.PI) * f5 * 0.5F, -Math.abs(MathHelper.cos(f4 * (float)Math.PI) * f5), 0.0F);
			GL11.glRotatef(MathHelper.sin(f4 * (float)Math.PI) * f5 * 3.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(Math.abs(MathHelper.cos(f4 * (float)Math.PI - 0.2F) * f5) * 5.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(f6, 1.0F, 0.0F, 0.0F);
		}
	}

	private void orientCamera(float f1) {
		EntityLiving entityLiving2 = this.mc.renderViewEntity;
		float f3 = entityLiving2.yOffset - 1.62F;
		double d4 = entityLiving2.prevPosX + (entityLiving2.posX - entityLiving2.prevPosX) * (double)f1;
		double d6 = entityLiving2.prevPosY + (entityLiving2.posY - entityLiving2.prevPosY) * (double)f1 - (double)f3;
		double d8 = entityLiving2.prevPosZ + (entityLiving2.posZ - entityLiving2.prevPosZ) * (double)f1;
		GL11.glRotatef(this.field_22230_A + (this.field_22220_z - this.field_22230_A) * f1, 0.0F, 0.0F, 1.0F);
		if(entityLiving2.isPlayerSleeping()) {
			f3 = (float)((double)f3 + 1.0D);
			GL11.glTranslatef(0.0F, 0.3F, 0.0F);
			if(!this.mc.gameSettings.field_22273_E) {
				int i10 = this.mc.theWorld.getBlockId(MathHelper.floor_double(entityLiving2.posX), MathHelper.floor_double(entityLiving2.posY), MathHelper.floor_double(entityLiving2.posZ));
				if(i10 == Block.blockBed.blockID) {
					int i11 = this.mc.theWorld.getBlockMetadata(MathHelper.floor_double(entityLiving2.posX), MathHelper.floor_double(entityLiving2.posY), MathHelper.floor_double(entityLiving2.posZ));
					int i12 = i11 & 3;
					GL11.glRotatef((float)(i12 * 90), 0.0F, 1.0F, 0.0F);
				}

				GL11.glRotatef(entityLiving2.prevRotationYaw + (entityLiving2.rotationYaw - entityLiving2.prevRotationYaw) * f1 + 180.0F, 0.0F, -1.0F, 0.0F);
				GL11.glRotatef(entityLiving2.prevRotationPitch + (entityLiving2.rotationPitch - entityLiving2.prevRotationPitch) * f1, -1.0F, 0.0F, 0.0F);
			}
		} else if(this.mc.gameSettings.thirdPersonView) {
			double d27 = (double)(this.field_22227_s + (this.field_22228_r - this.field_22227_s) * f1);
			float f13;
			float f28;
			if(this.mc.gameSettings.field_22273_E) {
				f28 = this.field_22225_u + (this.field_22226_t - this.field_22225_u) * f1;
				f13 = this.field_22223_w + (this.field_22224_v - this.field_22223_w) * f1;
				GL11.glTranslatef(0.0F, 0.0F, (float)(-d27));
				GL11.glRotatef(f13, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(f28, 0.0F, 1.0F, 0.0F);
			} else {
				f28 = entityLiving2.rotationYaw;
				f13 = entityLiving2.rotationPitch;
				double d14 = (double)(-MathHelper.sin(f28 / 180.0F * (float)Math.PI) * MathHelper.cos(f13 / 180.0F * (float)Math.PI)) * d27;
				double d16 = (double)(MathHelper.cos(f28 / 180.0F * (float)Math.PI) * MathHelper.cos(f13 / 180.0F * (float)Math.PI)) * d27;
				double d18 = (double)(-MathHelper.sin(f13 / 180.0F * (float)Math.PI)) * d27;

				for(int i20 = 0; i20 < 8; ++i20) {
					float f21 = (float)((i20 & 1) * 2 - 1);
					float f22 = (float)((i20 >> 1 & 1) * 2 - 1);
					float f23 = (float)((i20 >> 2 & 1) * 2 - 1);
					f21 *= 0.1F;
					f22 *= 0.1F;
					f23 *= 0.1F;
					MovingObjectPosition movingObjectPosition24 = this.mc.theWorld.rayTraceBlocks(Vec3D.createVector(d4 + (double)f21, d6 + (double)f22, d8 + (double)f23), Vec3D.createVector(d4 - d14 + (double)f21 + (double)f23, d6 - d18 + (double)f22, d8 - d16 + (double)f23));
					if(movingObjectPosition24 != null) {
						double d25 = movingObjectPosition24.hitVec.distanceTo(Vec3D.createVector(d4, d6, d8));
						if(d25 < d27) {
							d27 = d25;
						}
					}
				}

				GL11.glRotatef(entityLiving2.rotationPitch - f13, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(entityLiving2.rotationYaw - f28, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.0F, 0.0F, (float)(-d27));
				GL11.glRotatef(f28 - entityLiving2.rotationYaw, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(f13 - entityLiving2.rotationPitch, 1.0F, 0.0F, 0.0F);
			}
		} else {
			GL11.glTranslatef(0.0F, 0.0F, -0.1F);
		}

		if(!this.mc.gameSettings.field_22273_E) {
			GL11.glRotatef(entityLiving2.prevRotationPitch + (entityLiving2.rotationPitch - entityLiving2.prevRotationPitch) * f1, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(entityLiving2.prevRotationYaw + (entityLiving2.rotationYaw - entityLiving2.prevRotationYaw) * f1 + 180.0F, 0.0F, 1.0F, 0.0F);
		}

		GL11.glTranslatef(0.0F, f3, 0.0F);
		d4 = entityLiving2.prevPosX + (entityLiving2.posX - entityLiving2.prevPosX) * (double)f1;
		d6 = entityLiving2.prevPosY + (entityLiving2.posY - entityLiving2.prevPosY) * (double)f1 - (double)f3;
		d8 = entityLiving2.prevPosZ + (entityLiving2.posZ - entityLiving2.prevPosZ) * (double)f1;
		this.cloudFog = this.mc.renderGlobal.func_27307_a(d4, d6, d8, f1);
	}

	private void setupCameraTransform(float f1, int i2) {
		this.farPlaneDistance = (float)(256 >> this.mc.gameSettings.renderDistance);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		float f3 = 0.07F;
		if(this.mc.gameSettings.anaglyph) {
			GL11.glTranslatef((float)(-(i2 * 2 - 1)) * f3, 0.0F, 0.0F);
		}

		if(this.cameraZoom != 1.0D) {
			GL11.glTranslatef((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
			GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0D);
			GLU.gluPerspective(this.getFOVModifier(f1), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
		} else {
			GLU.gluPerspective(this.getFOVModifier(f1), (float)this.mc.displayWidth / (float)this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
		}

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		if(this.mc.gameSettings.anaglyph) {
			GL11.glTranslatef((float)(i2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
		}

		this.hurtCameraEffect(f1);
		if(this.mc.gameSettings.viewBobbing) {
			this.setupViewBobbing(f1);
		}

		float f4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * f1;
		if(f4 > 0.0F) {
			float f5 = 5.0F / (f4 * f4 + 5.0F) - f4 * 0.04F;
			f5 *= f5;
			GL11.glRotatef(((float)this.rendererUpdateCount + f1) * 20.0F, 0.0F, 1.0F, 1.0F);
			GL11.glScalef(1.0F / f5, 1.0F, 1.0F);
			GL11.glRotatef(-((float)this.rendererUpdateCount + f1) * 20.0F, 0.0F, 1.0F, 1.0F);
		}

		this.orientCamera(f1);
	}

	private void func_4135_b(float f1, int i2) {
		GL11.glLoadIdentity();
		if(this.mc.gameSettings.anaglyph) {
			GL11.glTranslatef((float)(i2 * 2 - 1) * 0.1F, 0.0F, 0.0F);
		}

		GL11.glPushMatrix();
		this.hurtCameraEffect(f1);
		if(this.mc.gameSettings.viewBobbing) {
			this.setupViewBobbing(f1);
		}

		if(!this.mc.gameSettings.thirdPersonView && !this.mc.renderViewEntity.isPlayerSleeping() && !this.mc.gameSettings.hideGUI) {
			this.itemRenderer.renderItemInFirstPerson(f1);
		}

		GL11.glPopMatrix();
		if(!this.mc.gameSettings.thirdPersonView && !this.mc.renderViewEntity.isPlayerSleeping()) {
			this.itemRenderer.renderOverlays(f1);
			this.hurtCameraEffect(f1);
		}

		if(this.mc.gameSettings.viewBobbing) {
			this.setupViewBobbing(f1);
		}

	}

	public void updateCameraAndRender(float f1) {
		if(!Display.isActive()) {
			if(System.currentTimeMillis() - this.prevFrameTime > 500L) {
				this.mc.displayInGameMenu();
			}
		} else {
			this.prevFrameTime = System.currentTimeMillis();
		}

		if(this.mc.inGameHasFocus) {
			this.mc.mouseHelper.mouseXYChange();
			float f2 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
			float f3 = f2 * f2 * f2 * 8.0F;
			float f4 = (float)this.mc.mouseHelper.deltaX * f3;
			float f5 = (float)this.mc.mouseHelper.deltaY * f3;
			byte b6 = 1;
			if(this.mc.gameSettings.invertMouse) {
				b6 = -1;
			}

			if(this.mc.gameSettings.smoothCamera) {
				f4 = this.mouseFilterXAxis.func_22386_a(f4, 0.05F * f3);
				f5 = this.mouseFilterYAxis.func_22386_a(f5, 0.05F * f3);
			}

			this.mc.thePlayer.func_346_d(f4, f5 * (float)b6);
		}

		if(!this.mc.skipRenderWorld) {
			field_28135_a = this.mc.gameSettings.anaglyph;
			ScaledResolution scaledResolution13 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
			int i14 = scaledResolution13.getScaledWidth();
			int i15 = scaledResolution13.getScaledHeight();
			int i16 = Mouse.getX() * i14 / this.mc.displayWidth;
			int i17 = i15 - Mouse.getY() * i15 / this.mc.displayHeight - 1;
			short s7 = 200;
			if(this.mc.gameSettings.limitFramerate == 1) {
				s7 = 120;
			}

			if(this.mc.gameSettings.limitFramerate == 2) {
				s7 = 40;
			}

			long j8;
			if(this.mc.theWorld != null) {
				if(this.mc.gameSettings.limitFramerate == 0) {
					this.renderWorld(f1, 0L);
				} else {
					this.renderWorld(f1, this.field_28133_I + (long)(1000000000 / s7));
				}

				if(this.mc.gameSettings.limitFramerate == 2) {
					j8 = (this.field_28133_I + (long)(1000000000 / s7) - System.nanoTime()) / 1000000L;
					if(j8 > 0L && j8 < 500L) {
						try {
							Thread.sleep(j8);
						} catch (InterruptedException interruptedException12) {
							interruptedException12.printStackTrace();
						}
					}
				}

				this.field_28133_I = System.nanoTime();
				if(!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
					this.mc.ingameGUI.renderGameOverlay(f1, this.mc.currentScreen != null, i16, i17);
				}
			} else {
				GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				this.func_905_b();
				if(this.mc.gameSettings.limitFramerate == 2) {
					j8 = (this.field_28133_I + (long)(1000000000 / s7) - System.nanoTime()) / 1000000L;
					if(j8 < 0L) {
						j8 += 10L;
					}

					if(j8 > 0L && j8 < 500L) {
						try {
							Thread.sleep(j8);
						} catch (InterruptedException interruptedException11) {
							interruptedException11.printStackTrace();
						}
					}
				}

				this.field_28133_I = System.nanoTime();
			}

			if(this.mc.currentScreen != null) {
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				this.mc.currentScreen.drawScreen(i16, i17, f1);
				if(this.mc.currentScreen != null && this.mc.currentScreen.field_25091_h != null) {
					this.mc.currentScreen.field_25091_h.func_25087_a(f1);
				}
			}

		}
	}

	public void renderWorld(float f1, long j2) {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		if(this.mc.renderViewEntity == null) {
			this.mc.renderViewEntity = this.mc.thePlayer;
		}

		this.getMouseOver(f1);
		EntityLiving entityLiving4 = this.mc.renderViewEntity;
		RenderGlobal renderGlobal5 = this.mc.renderGlobal;
		EffectRenderer effectRenderer6 = this.mc.effectRenderer;
		double d7 = entityLiving4.lastTickPosX + (entityLiving4.posX - entityLiving4.lastTickPosX) * (double)f1;
		double d9 = entityLiving4.lastTickPosY + (entityLiving4.posY - entityLiving4.lastTickPosY) * (double)f1;
		double d11 = entityLiving4.lastTickPosZ + (entityLiving4.posZ - entityLiving4.lastTickPosZ) * (double)f1;
		IChunkProvider iChunkProvider13 = this.mc.theWorld.getIChunkProvider();
		int i16;
		if(iChunkProvider13 instanceof ChunkProviderLoadOrGenerate) {
			ChunkProviderLoadOrGenerate chunkProviderLoadOrGenerate14 = (ChunkProviderLoadOrGenerate)iChunkProvider13;
			int i15 = MathHelper.floor_float((float)((int)d7)) >> 4;
			i16 = MathHelper.floor_float((float)((int)d11)) >> 4;
			chunkProviderLoadOrGenerate14.setCurrentChunkOver(i15, i16);
		}

		for(int i18 = 0; i18 < 2; ++i18) {
			if(this.mc.gameSettings.anaglyph) {
				anaglyphField = i18;
				if(anaglyphField == 0) {
					GL11.glColorMask(false, true, true, false);
				} else {
					GL11.glColorMask(true, false, false, false);
				}
			}

			GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
			this.updateFogColor(f1);
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glEnable(GL11.GL_CULL_FACE);
			this.setupCameraTransform(f1, i18);
			ClippingHelperImpl.getInstance();
			if(this.mc.gameSettings.renderDistance < 2) {
				this.setupFog(-1, f1);
				renderGlobal5.renderSky(f1);
			}

			GL11.glEnable(GL11.GL_FOG);
			this.setupFog(1, f1);
			if(this.mc.gameSettings.ambientOcclusion) {
				GL11.glShadeModel(GL11.GL_SMOOTH);
			}

			Frustrum frustrum19 = new Frustrum();
			frustrum19.setPosition(d7, d9, d11);
			this.mc.renderGlobal.clipRenderersByFrustrum(frustrum19, f1);
			if(i18 == 0) {
				while(!this.mc.renderGlobal.updateRenderers(entityLiving4, false) && j2 != 0L) {
					long j20 = j2 - System.nanoTime();
					if(j20 < 0L || j20 > 1000000000L) {
						break;
					}
				}
			}

			this.setupFog(0, f1);
			GL11.glEnable(GL11.GL_FOG);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			RenderHelper.disableStandardItemLighting();
			renderGlobal5.sortAndRender(entityLiving4, 0, (double)f1);
			GL11.glShadeModel(GL11.GL_FLAT);
			RenderHelper.enableStandardItemLighting();
			renderGlobal5.renderEntities(entityLiving4.getPosition(f1), frustrum19, f1);
			effectRenderer6.func_1187_b(entityLiving4, f1);
			RenderHelper.disableStandardItemLighting();
			this.setupFog(0, f1);
			effectRenderer6.renderParticles(entityLiving4, f1);
			EntityPlayer entityPlayer21;
			if(this.mc.objectMouseOver != null && entityLiving4.isInsideOfMaterial(Material.water) && entityLiving4 instanceof EntityPlayer) {
				entityPlayer21 = (EntityPlayer)entityLiving4;
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				renderGlobal5.drawBlockBreaking(entityPlayer21, this.mc.objectMouseOver, 0, entityPlayer21.inventory.getCurrentItem(), f1);
				renderGlobal5.drawSelectionBox(entityPlayer21, this.mc.objectMouseOver, 0, entityPlayer21.inventory.getCurrentItem(), f1);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
			}

			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			this.setupFog(0, f1);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			if(this.mc.gameSettings.fancyGraphics) {
				if(this.mc.gameSettings.ambientOcclusion) {
					GL11.glShadeModel(GL11.GL_SMOOTH);
				}

				GL11.glColorMask(false, false, false, false);
				i16 = renderGlobal5.sortAndRender(entityLiving4, 1, (double)f1);
				if(this.mc.gameSettings.anaglyph) {
					if(anaglyphField == 0) {
						GL11.glColorMask(false, true, true, true);
					} else {
						GL11.glColorMask(true, false, false, true);
					}
				} else {
					GL11.glColorMask(true, true, true, true);
				}

				if(i16 > 0) {
					renderGlobal5.renderAllRenderLists(1, (double)f1);
				}

				GL11.glShadeModel(GL11.GL_FLAT);
			} else {
				renderGlobal5.sortAndRender(entityLiving4, 1, (double)f1);
			}

			GL11.glDepthMask(true);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			if(this.cameraZoom == 1.0D && entityLiving4 instanceof EntityPlayer && this.mc.objectMouseOver != null && !entityLiving4.isInsideOfMaterial(Material.water)) {
				entityPlayer21 = (EntityPlayer)entityLiving4;
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				renderGlobal5.drawBlockBreaking(entityPlayer21, this.mc.objectMouseOver, 0, entityPlayer21.inventory.getCurrentItem(), f1);
				renderGlobal5.drawSelectionBox(entityPlayer21, this.mc.objectMouseOver, 0, entityPlayer21.inventory.getCurrentItem(), f1);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
			}

			this.renderRainSnow(f1);
			GL11.glDisable(GL11.GL_FOG);
			if(this.pointedEntity != null) {
				;
			}

			this.setupFog(0, f1);
			GL11.glEnable(GL11.GL_FOG);
			renderGlobal5.renderClouds(f1);
			GL11.glDisable(GL11.GL_FOG);
			this.setupFog(1, f1);
			if(this.cameraZoom == 1.0D) {
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				this.func_4135_b(f1, i18);
			}

			if(!this.mc.gameSettings.anaglyph) {
				return;
			}
		}

		GL11.glColorMask(true, true, true, false);
	}

	private void addRainParticles() {
		float f1 = this.mc.theWorld.func_27162_g(1.0F);
		if(!this.mc.gameSettings.fancyGraphics) {
			f1 /= 2.0F;
		}

		if(f1 != 0.0F) {
			this.random.setSeed((long)this.rendererUpdateCount * 312987231L);
			EntityLiving entityLiving2 = this.mc.renderViewEntity;
			World world3 = this.mc.theWorld;
			int i4 = MathHelper.floor_double(entityLiving2.posX);
			int i5 = MathHelper.floor_double(entityLiving2.posY);
			int i6 = MathHelper.floor_double(entityLiving2.posZ);
			byte b7 = 10;
			double d8 = 0.0D;
			double d10 = 0.0D;
			double d12 = 0.0D;
			int i14 = 0;

			for(int i15 = 0; i15 < (int)(100.0F * f1 * f1); ++i15) {
				int i16 = i4 + this.random.nextInt(b7) - this.random.nextInt(b7);
				int i17 = i6 + this.random.nextInt(b7) - this.random.nextInt(b7);
				int i18 = world3.findTopSolidBlock(i16, i17);
				int i19 = world3.getBlockId(i16, i18 - 1, i17);
				if(i18 <= i5 + b7 && i18 >= i5 - b7 && world3.getWorldChunkManager().getBiomeGenAt(i16, i17).canSpawnLightningBolt()) {
					float f20 = this.random.nextFloat();
					float f21 = this.random.nextFloat();
					if(i19 > 0) {
						if(Block.blocksList[i19].blockMaterial == Material.lava) {
							this.mc.effectRenderer.addEffect(new EntitySmokeFX(world3, (double)((float)i16 + f20), (double)((float)i18 + 0.1F) - Block.blocksList[i19].minY, (double)((float)i17 + f21), 0.0D, 0.0D, 0.0D));
						} else {
							++i14;
							if(this.random.nextInt(i14) == 0) {
								d8 = (double)((float)i16 + f20);
								d10 = (double)((float)i18 + 0.1F) - Block.blocksList[i19].minY;
								d12 = (double)((float)i17 + f21);
							}

							this.mc.effectRenderer.addEffect(new EntityRainFX(world3, (double)((float)i16 + f20), (double)((float)i18 + 0.1F) - Block.blocksList[i19].minY, (double)((float)i17 + f21)));
						}
					}
				}
			}

			if(i14 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
				this.rainSoundCounter = 0;
				if(d10 > entityLiving2.posY + 1.0D && world3.findTopSolidBlock(MathHelper.floor_double(entityLiving2.posX), MathHelper.floor_double(entityLiving2.posZ)) > MathHelper.floor_double(entityLiving2.posY)) {
					this.mc.theWorld.playSoundEffect(d8, d10, d12, "ambient.weather.rain", 0.1F, 0.5F);
				} else {
					this.mc.theWorld.playSoundEffect(d8, d10, d12, "ambient.weather.rain", 0.2F, 1.0F);
				}
			}

		}
	}

	protected void renderRainSnow(float f1) {
		float f2 = this.mc.theWorld.func_27162_g(f1);
		if(f2 > 0.0F) {
			EntityLiving entityLiving3 = this.mc.renderViewEntity;
			World world4 = this.mc.theWorld;
			int i5 = MathHelper.floor_double(entityLiving3.posX);
			int i6 = MathHelper.floor_double(entityLiving3.posY);
			int i7 = MathHelper.floor_double(entityLiving3.posZ);
			Tessellator tessellator8 = Tessellator.instance;
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.01F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/environment/snow.png"));
			double d9 = entityLiving3.lastTickPosX + (entityLiving3.posX - entityLiving3.lastTickPosX) * (double)f1;
			double d11 = entityLiving3.lastTickPosY + (entityLiving3.posY - entityLiving3.lastTickPosY) * (double)f1;
			double d13 = entityLiving3.lastTickPosZ + (entityLiving3.posZ - entityLiving3.lastTickPosZ) * (double)f1;
			int i15 = MathHelper.floor_double(d11);
			byte b16 = 5;
			if(this.mc.gameSettings.fancyGraphics) {
				b16 = 10;
			}

			BiomeGenBase[] biomeGenBase17 = world4.getWorldChunkManager().func_4069_a(i5 - b16, i7 - b16, b16 * 2 + 1, b16 * 2 + 1);
			int i18 = 0;

			int i19;
			int i20;
			BiomeGenBase biomeGenBase21;
			int i22;
			int i23;
			int i24;
			float f26;
			for(i19 = i5 - b16; i19 <= i5 + b16; ++i19) {
				for(i20 = i7 - b16; i20 <= i7 + b16; ++i20) {
					biomeGenBase21 = biomeGenBase17[i18++];
					if(biomeGenBase21.getEnableSnow()) {
						i22 = world4.findTopSolidBlock(i19, i20);
						if(i22 < 0) {
							i22 = 0;
						}

						i23 = i22;
						if(i22 < i15) {
							i23 = i15;
						}

						i24 = i6 - b16;
						int i25 = i6 + b16;
						if(i24 < i22) {
							i24 = i22;
						}

						if(i25 < i22) {
							i25 = i22;
						}

						f26 = 1.0F;
						if(i24 != i25) {
							this.random.setSeed((long)(i19 * i19 * 3121 + i19 * 45238971 + i20 * i20 * 418711 + i20 * 13761));
							float f27 = (float)this.rendererUpdateCount + f1;
							float f28 = ((float)(this.rendererUpdateCount & 511) + f1) / 512.0F;
							float f29 = this.random.nextFloat() + f27 * 0.01F * (float)this.random.nextGaussian();
							float f30 = this.random.nextFloat() + f27 * (float)this.random.nextGaussian() * 0.001F;
							double d31 = (double)((float)i19 + 0.5F) - entityLiving3.posX;
							double d33 = (double)((float)i20 + 0.5F) - entityLiving3.posZ;
							float f35 = MathHelper.sqrt_double(d31 * d31 + d33 * d33) / (float)b16;
							tessellator8.startDrawingQuads();
							float f36 = world4.getLightBrightness(i19, i23, i20);
							GL11.glColor4f(f36, f36, f36, ((1.0F - f35 * f35) * 0.3F + 0.5F) * f2);
							tessellator8.setTranslationD(-d9 * 1.0D, -d11 * 1.0D, -d13 * 1.0D);
							tessellator8.addVertexWithUV((double)(i19 + 0), (double)i24, (double)i20 + 0.5D, (double)(0.0F * f26 + f29), (double)((float)i24 * f26 / 4.0F + f28 * f26 + f30));
							tessellator8.addVertexWithUV((double)(i19 + 1), (double)i24, (double)i20 + 0.5D, (double)(1.0F * f26 + f29), (double)((float)i24 * f26 / 4.0F + f28 * f26 + f30));
							tessellator8.addVertexWithUV((double)(i19 + 1), (double)i25, (double)i20 + 0.5D, (double)(1.0F * f26 + f29), (double)((float)i25 * f26 / 4.0F + f28 * f26 + f30));
							tessellator8.addVertexWithUV((double)(i19 + 0), (double)i25, (double)i20 + 0.5D, (double)(0.0F * f26 + f29), (double)((float)i25 * f26 / 4.0F + f28 * f26 + f30));
							tessellator8.addVertexWithUV((double)i19 + 0.5D, (double)i24, (double)(i20 + 0), (double)(0.0F * f26 + f29), (double)((float)i24 * f26 / 4.0F + f28 * f26 + f30));
							tessellator8.addVertexWithUV((double)i19 + 0.5D, (double)i24, (double)(i20 + 1), (double)(1.0F * f26 + f29), (double)((float)i24 * f26 / 4.0F + f28 * f26 + f30));
							tessellator8.addVertexWithUV((double)i19 + 0.5D, (double)i25, (double)(i20 + 1), (double)(1.0F * f26 + f29), (double)((float)i25 * f26 / 4.0F + f28 * f26 + f30));
							tessellator8.addVertexWithUV((double)i19 + 0.5D, (double)i25, (double)(i20 + 0), (double)(0.0F * f26 + f29), (double)((float)i25 * f26 / 4.0F + f28 * f26 + f30));
							tessellator8.setTranslationD(0.0D, 0.0D, 0.0D);
							tessellator8.draw();
						}
					}
				}
			}

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/environment/rain.png"));
			if(this.mc.gameSettings.fancyGraphics) {
				b16 = 10;
			}

			i18 = 0;

			for(i19 = i5 - b16; i19 <= i5 + b16; ++i19) {
				for(i20 = i7 - b16; i20 <= i7 + b16; ++i20) {
					biomeGenBase21 = biomeGenBase17[i18++];
					if(biomeGenBase21.canSpawnLightningBolt()) {
						i22 = world4.findTopSolidBlock(i19, i20);
						i23 = i6 - b16;
						i24 = i6 + b16;
						if(i23 < i22) {
							i23 = i22;
						}

						if(i24 < i22) {
							i24 = i22;
						}

						float f37 = 1.0F;
						if(i23 != i24) {
							this.random.setSeed((long)(i19 * i19 * 3121 + i19 * 45238971 + i20 * i20 * 418711 + i20 * 13761));
							f26 = ((float)(this.rendererUpdateCount + i19 * i19 * 3121 + i19 * 45238971 + i20 * i20 * 418711 + i20 * 13761 & 31) + f1) / 32.0F * (3.0F + this.random.nextFloat());
							double d38 = (double)((float)i19 + 0.5F) - entityLiving3.posX;
							double d39 = (double)((float)i20 + 0.5F) - entityLiving3.posZ;
							float f40 = MathHelper.sqrt_double(d38 * d38 + d39 * d39) / (float)b16;
							tessellator8.startDrawingQuads();
							float f32 = world4.getLightBrightness(i19, 128, i20) * 0.85F + 0.15F;
							GL11.glColor4f(f32, f32, f32, ((1.0F - f40 * f40) * 0.5F + 0.5F) * f2);
							tessellator8.setTranslationD(-d9 * 1.0D, -d11 * 1.0D, -d13 * 1.0D);
							tessellator8.addVertexWithUV((double)(i19 + 0), (double)i23, (double)i20 + 0.5D, (double)(0.0F * f37), (double)((float)i23 * f37 / 4.0F + f26 * f37));
							tessellator8.addVertexWithUV((double)(i19 + 1), (double)i23, (double)i20 + 0.5D, (double)(1.0F * f37), (double)((float)i23 * f37 / 4.0F + f26 * f37));
							tessellator8.addVertexWithUV((double)(i19 + 1), (double)i24, (double)i20 + 0.5D, (double)(1.0F * f37), (double)((float)i24 * f37 / 4.0F + f26 * f37));
							tessellator8.addVertexWithUV((double)(i19 + 0), (double)i24, (double)i20 + 0.5D, (double)(0.0F * f37), (double)((float)i24 * f37 / 4.0F + f26 * f37));
							tessellator8.addVertexWithUV((double)i19 + 0.5D, (double)i23, (double)(i20 + 0), (double)(0.0F * f37), (double)((float)i23 * f37 / 4.0F + f26 * f37));
							tessellator8.addVertexWithUV((double)i19 + 0.5D, (double)i23, (double)(i20 + 1), (double)(1.0F * f37), (double)((float)i23 * f37 / 4.0F + f26 * f37));
							tessellator8.addVertexWithUV((double)i19 + 0.5D, (double)i24, (double)(i20 + 1), (double)(1.0F * f37), (double)((float)i24 * f37 / 4.0F + f26 * f37));
							tessellator8.addVertexWithUV((double)i19 + 0.5D, (double)i24, (double)(i20 + 0), (double)(0.0F * f37), (double)((float)i24 * f37 / 4.0F + f26 * f37));
							tessellator8.setTranslationD(0.0D, 0.0D, 0.0D);
							tessellator8.draw();
						}
					}
				}
			}

			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		}
	}

	public void func_905_b() {
		ScaledResolution scaledResolution1 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, scaledResolution1.field_25121_a, scaledResolution1.field_25120_b, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
	}

	private void updateFogColor(float f1) {
		World world2 = this.mc.theWorld;
		EntityLiving entityLiving3 = this.mc.renderViewEntity;
		float f4 = 1.0F / (float)(4 - this.mc.gameSettings.renderDistance);
		f4 = 1.0F - (float)Math.pow((double)f4, 0.25D);
		Vec3D vec3D5 = world2.func_4079_a(this.mc.renderViewEntity, f1);
		float f6 = (float)vec3D5.xCoord;
		float f7 = (float)vec3D5.yCoord;
		float f8 = (float)vec3D5.zCoord;
		Vec3D vec3D9 = world2.getFogColor(f1);
		this.fogColorRed = (float)vec3D9.xCoord;
		this.fogColorGreen = (float)vec3D9.yCoord;
		this.fogColorBlue = (float)vec3D9.zCoord;
		this.fogColorRed += (f6 - this.fogColorRed) * f4;
		this.fogColorGreen += (f7 - this.fogColorGreen) * f4;
		this.fogColorBlue += (f8 - this.fogColorBlue) * f4;
		float f10 = world2.func_27162_g(f1);
		float f11;
		float f12;
		if(f10 > 0.0F) {
			f11 = 1.0F - f10 * 0.5F;
			f12 = 1.0F - f10 * 0.4F;
			this.fogColorRed *= f11;
			this.fogColorGreen *= f11;
			this.fogColorBlue *= f12;
		}

		f11 = world2.func_27166_f(f1);
		if(f11 > 0.0F) {
			f12 = 1.0F - f11 * 0.5F;
			this.fogColorRed *= f12;
			this.fogColorGreen *= f12;
			this.fogColorBlue *= f12;
		}

		if(this.cloudFog) {
			Vec3D vec3D16 = world2.func_628_d(f1);
			this.fogColorRed = (float)vec3D16.xCoord;
			this.fogColorGreen = (float)vec3D16.yCoord;
			this.fogColorBlue = (float)vec3D16.zCoord;
		} else if(entityLiving3.isInsideOfMaterial(Material.water)) {
			this.fogColorRed = 0.02F;
			this.fogColorGreen = 0.02F;
			this.fogColorBlue = 0.2F;
		} else if(entityLiving3.isInsideOfMaterial(Material.lava)) {
			this.fogColorRed = 0.6F;
			this.fogColorGreen = 0.1F;
			this.fogColorBlue = 0.0F;
		}

		f12 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * f1;
		this.fogColorRed *= f12;
		this.fogColorGreen *= f12;
		this.fogColorBlue *= f12;
		if(this.mc.gameSettings.anaglyph) {
			float f13 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
			float f14 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
			float f15 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
			this.fogColorRed = f13;
			this.fogColorGreen = f14;
			this.fogColorBlue = f15;
		}

		GL11.glClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
	}

	private void setupFog(int i1, float f2) {
		EntityLiving entityLiving3 = this.mc.renderViewEntity;
		GL11.glFog(GL11.GL_FOG_COLOR, this.func_908_a(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
		GL11.glNormal3f(0.0F, -1.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f4;
		float f5;
		float f6;
		float f7;
		float f8;
		float f9;
		if(this.cloudFog) {
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
			f4 = 1.0F;
			f5 = 1.0F;
			f6 = 1.0F;
			if(this.mc.gameSettings.anaglyph) {
				f7 = (f4 * 30.0F + f5 * 59.0F + f6 * 11.0F) / 100.0F;
				f8 = (f4 * 30.0F + f5 * 70.0F) / 100.0F;
				f9 = (f4 * 30.0F + f6 * 70.0F) / 100.0F;
			}
		} else if(entityLiving3.isInsideOfMaterial(Material.water)) {
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 0.1F);
			f4 = 0.4F;
			f5 = 0.4F;
			f6 = 0.9F;
			if(this.mc.gameSettings.anaglyph) {
				f7 = (f4 * 30.0F + f5 * 59.0F + f6 * 11.0F) / 100.0F;
				f8 = (f4 * 30.0F + f5 * 70.0F) / 100.0F;
				f9 = (f4 * 30.0F + f6 * 70.0F) / 100.0F;
			}
		} else if(entityLiving3.isInsideOfMaterial(Material.lava)) {
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 2.0F);
			f4 = 0.4F;
			f5 = 0.3F;
			f6 = 0.3F;
			if(this.mc.gameSettings.anaglyph) {
				f7 = (f4 * 30.0F + f5 * 59.0F + f6 * 11.0F) / 100.0F;
				f8 = (f4 * 30.0F + f5 * 70.0F) / 100.0F;
				f9 = (f4 * 30.0F + f6 * 70.0F) / 100.0F;
			}
		} else {
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
			GL11.glFogf(GL11.GL_FOG_START, this.farPlaneDistance * 0.25F);
			GL11.glFogf(GL11.GL_FOG_END, this.farPlaneDistance);
			if(i1 < 0) {
				GL11.glFogf(GL11.GL_FOG_START, 0.0F);
				GL11.glFogf(GL11.GL_FOG_END, this.farPlaneDistance * 0.8F);
			}

			if(GLContext.getCapabilities().GL_NV_fog_distance) {
				GL11.glFogi(34138, 34139);
			}

			if(this.mc.theWorld.worldProvider.isNether) {
				GL11.glFogf(GL11.GL_FOG_START, 0.0F);
			}
		}

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
	}

	private FloatBuffer func_908_a(float f1, float f2, float f3, float f4) {
		this.fogColorBuffer.clear();
		this.fogColorBuffer.put(f1).put(f2).put(f3).put(f4);
		this.fogColorBuffer.flip();
		return this.fogColorBuffer;
	}
}
