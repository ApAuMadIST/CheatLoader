package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.lwjgl.opengl.GL11;

public class RenderManager {
	private Map entityRenderMap = new HashMap();
	public static RenderManager instance = new RenderManager();
	private FontRenderer fontRenderer;
	public static double renderPosX;
	public static double renderPosY;
	public static double renderPosZ;
	public RenderEngine renderEngine;
	public ItemRenderer itemRenderer;
	public World worldObj;
	public EntityLiving livingPlayer;
	public float playerViewY;
	public float playerViewX;
	public GameSettings options;
	public double field_1222_l;
	public double field_1221_m;
	public double field_1220_n;

	private RenderManager() {
		this.entityRenderMap.put(EntitySpider.class, new RenderSpider());
		this.entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
		this.entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7F));
		this.entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7F));
		this.entityRenderMap.put(EntityWolf.class, new RenderWolf(new ModelWolf(), 0.5F));
		this.entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3F));
		this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
		this.entityRenderMap.put(EntitySkeleton.class, new RenderBiped(new ModelSkeleton(), 0.5F));
		this.entityRenderMap.put(EntityZombie.class, new RenderBiped(new ModelZombie(), 0.5F));
		this.entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
		this.entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
		this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(new ModelZombie(), 0.5F, 6.0F));
		this.entityRenderMap.put(EntityGhast.class, new RenderGhast());
		this.entityRenderMap.put(EntitySquid.class, new RenderSquid(new ModelSquid(), 0.7F));
		this.entityRenderMap.put(EntityLiving.class, new RenderLiving(new ModelBiped(), 0.5F));
		this.entityRenderMap.put(Entity.class, new RenderEntity());
		this.entityRenderMap.put(EntityPainting.class, new RenderPainting());
		this.entityRenderMap.put(EntityArrow.class, new RenderArrow());
		this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(Item.snowball.getIconFromDamage(0)));
		this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(Item.egg.getIconFromDamage(0)));
		this.entityRenderMap.put(EntityFireball.class, new RenderFireball());
		this.entityRenderMap.put(EntityItem.class, new RenderItem());
		this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
		this.entityRenderMap.put(EntityFallingSand.class, new RenderFallingSand());
		this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
		this.entityRenderMap.put(EntityBoat.class, new RenderBoat());
		this.entityRenderMap.put(EntityFish.class, new RenderFish());
		this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt());
		ModLoader.AddAllRenderers(this.entityRenderMap);
		Iterator iterator2 = this.entityRenderMap.values().iterator();

		while(iterator2.hasNext()) {
			Render localbw = (Render)iterator2.next();
			localbw.setRenderManager(this);
		}

	}

	public Render getEntityClassRenderObject(Class paramClass) {
		Render localbw = (Render)this.entityRenderMap.get(paramClass);
		if(localbw == null && paramClass != Entity.class) {
			localbw = this.getEntityClassRenderObject(paramClass.getSuperclass());
			this.entityRenderMap.put(paramClass, localbw);
		}

		return localbw;
	}

	public Render getEntityRenderObject(Entity paramsn) {
		return this.getEntityClassRenderObject(paramsn.getClass());
	}

	public void cacheActiveRenderInfo(World paramfd, RenderEngine paramji, FontRenderer paramsj, EntityLiving paramls, GameSettings paramkv, float paramFloat) {
		this.worldObj = paramfd;
		this.renderEngine = paramji;
		this.options = paramkv;
		this.livingPlayer = paramls;
		this.fontRenderer = paramsj;
		if(paramls.isPlayerSleeping()) {
			int i1 = paramfd.getBlockId(MathHelper.floor_double(paramls.posX), MathHelper.floor_double(paramls.posY), MathHelper.floor_double(paramls.posZ));
			if(i1 == Block.blockBed.blockID) {
				int i2 = paramfd.getBlockMetadata(MathHelper.floor_double(paramls.posX), MathHelper.floor_double(paramls.posY), MathHelper.floor_double(paramls.posZ));
				int i3 = i2 & 3;
				this.playerViewY = (float)(i3 * 90 + 180);
				this.playerViewX = 0.0F;
			}
		} else {
			this.playerViewY = paramls.prevRotationYaw + (paramls.rotationYaw - paramls.prevRotationYaw) * paramFloat;
			this.playerViewX = paramls.prevRotationPitch + (paramls.rotationPitch - paramls.prevRotationPitch) * paramFloat;
		}

		this.field_1222_l = paramls.lastTickPosX + (paramls.posX - paramls.lastTickPosX) * (double)paramFloat;
		this.field_1221_m = paramls.lastTickPosY + (paramls.posY - paramls.lastTickPosY) * (double)paramFloat;
		this.field_1220_n = paramls.lastTickPosZ + (paramls.posZ - paramls.lastTickPosZ) * (double)paramFloat;
	}

	public void renderEntity(Entity paramsn, float paramFloat) {
		double d1 = paramsn.lastTickPosX + (paramsn.posX - paramsn.lastTickPosX) * (double)paramFloat;
		double d2 = paramsn.lastTickPosY + (paramsn.posY - paramsn.lastTickPosY) * (double)paramFloat;
		double d3 = paramsn.lastTickPosZ + (paramsn.posZ - paramsn.lastTickPosZ) * (double)paramFloat;
		float f1 = paramsn.prevRotationYaw + (paramsn.rotationYaw - paramsn.prevRotationYaw) * paramFloat;
		float f2 = paramsn.getEntityBrightness(paramFloat);
		GL11.glColor3f(f2, f2, f2);
		this.renderEntityWithPosYaw(paramsn, d1 - renderPosX, d2 - renderPosY, d3 - renderPosZ, f1, paramFloat);
	}

	public void renderEntityWithPosYaw(Entity paramsn, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2) {
		Render localbw = this.getEntityRenderObject(paramsn);
		if(localbw != null) {
			localbw.doRender(paramsn, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
			localbw.doRenderShadowAndFire(paramsn, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
		}

	}

	public void func_852_a(World paramfd) {
		this.worldObj = paramfd;
	}

	public double func_851_a(double paramDouble1, double paramDouble2, double paramDouble3) {
		double d1 = paramDouble1 - this.field_1222_l;
		double d2 = paramDouble2 - this.field_1221_m;
		double d3 = paramDouble3 - this.field_1220_n;
		return d1 * d1 + d2 * d2 + d3 * d3;
	}

	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
}
