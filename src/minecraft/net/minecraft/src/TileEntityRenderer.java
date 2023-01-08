package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.lwjgl.opengl.GL11;

public class TileEntityRenderer {
	private Map specialRendererMap = new HashMap();
	public static TileEntityRenderer instance = new TileEntityRenderer();
	private FontRenderer fontRenderer;
	public static double staticPlayerX;
	public static double staticPlayerY;
	public static double staticPlayerZ;
	public RenderEngine renderEngine;
	public World worldObj;
	public EntityLiving entityLivingPlayer;
	public float playerYaw;
	public float playerPitch;
	public double playerX;
	public double playerY;
	public double playerZ;

	private TileEntityRenderer() {
		this.specialRendererMap.put(TileEntitySign.class, new TileEntitySignRenderer());
		this.specialRendererMap.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
		this.specialRendererMap.put(TileEntityPiston.class, new TileEntityRendererPiston());
		Iterator iterator1 = this.specialRendererMap.values().iterator();

		while(iterator1.hasNext()) {
			TileEntitySpecialRenderer tileEntitySpecialRenderer2 = (TileEntitySpecialRenderer)iterator1.next();
			tileEntitySpecialRenderer2.setTileEntityRenderer(this);
		}

	}

	public TileEntitySpecialRenderer getSpecialRendererForClass(Class class1) {
		TileEntitySpecialRenderer tileEntitySpecialRenderer2 = (TileEntitySpecialRenderer)this.specialRendererMap.get(class1);
		if(tileEntitySpecialRenderer2 == null && class1 != TileEntity.class) {
			tileEntitySpecialRenderer2 = this.getSpecialRendererForClass(class1.getSuperclass());
			this.specialRendererMap.put(class1, tileEntitySpecialRenderer2);
		}

		return tileEntitySpecialRenderer2;
	}

	public boolean hasSpecialRenderer(TileEntity tileEntity1) {
		return this.getSpecialRendererForEntity(tileEntity1) != null;
	}

	public TileEntitySpecialRenderer getSpecialRendererForEntity(TileEntity tileEntity1) {
		return tileEntity1 == null ? null : this.getSpecialRendererForClass(tileEntity1.getClass());
	}

	public void cacheActiveRenderInfo(World world1, RenderEngine renderEngine2, FontRenderer fontRenderer3, EntityLiving entityLiving4, float f5) {
		if(this.worldObj != world1) {
			this.func_31072_a(world1);
		}

		this.renderEngine = renderEngine2;
		this.entityLivingPlayer = entityLiving4;
		this.fontRenderer = fontRenderer3;
		this.playerYaw = entityLiving4.prevRotationYaw + (entityLiving4.rotationYaw - entityLiving4.prevRotationYaw) * f5;
		this.playerPitch = entityLiving4.prevRotationPitch + (entityLiving4.rotationPitch - entityLiving4.prevRotationPitch) * f5;
		this.playerX = entityLiving4.lastTickPosX + (entityLiving4.posX - entityLiving4.lastTickPosX) * (double)f5;
		this.playerY = entityLiving4.lastTickPosY + (entityLiving4.posY - entityLiving4.lastTickPosY) * (double)f5;
		this.playerZ = entityLiving4.lastTickPosZ + (entityLiving4.posZ - entityLiving4.lastTickPosZ) * (double)f5;
	}

	public void renderTileEntity(TileEntity tileEntity1, float f2) {
		if(tileEntity1.getDistanceFrom(this.playerX, this.playerY, this.playerZ) < 4096.0D) {
			float f3 = this.worldObj.getLightBrightness(tileEntity1.xCoord, tileEntity1.yCoord, tileEntity1.zCoord);
			GL11.glColor3f(f3, f3, f3);
			this.renderTileEntityAt(tileEntity1, (double)tileEntity1.xCoord - staticPlayerX, (double)tileEntity1.yCoord - staticPlayerY, (double)tileEntity1.zCoord - staticPlayerZ, f2);
		}

	}

	public void renderTileEntityAt(TileEntity tileEntity1, double d2, double d4, double d6, float f8) {
		TileEntitySpecialRenderer tileEntitySpecialRenderer9 = this.getSpecialRendererForEntity(tileEntity1);
		if(tileEntitySpecialRenderer9 != null) {
			tileEntitySpecialRenderer9.renderTileEntityAt(tileEntity1, d2, d4, d6, f8);
		}

	}

	public void func_31072_a(World world1) {
		this.worldObj = world1;
		Iterator iterator2 = this.specialRendererMap.values().iterator();

		while(iterator2.hasNext()) {
			TileEntitySpecialRenderer tileEntitySpecialRenderer3 = (TileEntitySpecialRenderer)iterator2.next();
			if(tileEntitySpecialRenderer3 != null) {
				tileEntitySpecialRenderer3.func_31069_a(world1);
			}
		}

	}

	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
}
