package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class EntityList {
	private static Map stringToClassMapping = new HashMap();
	private static Map classToStringMapping = new HashMap();
	private static Map IDtoClassMapping = new HashMap();
	private static Map classToIDMapping = new HashMap();

	private static void addMapping(Class class0, String string1, int i2) {
		stringToClassMapping.put(string1, class0);
		classToStringMapping.put(class0, string1);
		IDtoClassMapping.put(i2, class0);
		classToIDMapping.put(class0, i2);
	}

	public static Entity createEntityInWorld(String string0, World world1) {
		Entity entity2 = null;

		try {
			Class class3 = (Class)stringToClassMapping.get(string0);
			if(class3 != null) {
				entity2 = (Entity)class3.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world1});
			}
		} catch (Exception exception4) {
			exception4.printStackTrace();
		}

		return entity2;
	}

	public static Entity createEntityFromNBT(NBTTagCompound nBTTagCompound0, World world1) {
		Entity entity2 = null;

		try {
			Class class3 = (Class)stringToClassMapping.get(nBTTagCompound0.getString("id"));
			if(class3 != null) {
				entity2 = (Entity)class3.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world1});
			}
		} catch (Exception exception4) {
			exception4.printStackTrace();
		}

		if(entity2 != null) {
			entity2.readFromNBT(nBTTagCompound0);
		} else {
			System.out.println("Skipping Entity with id " + nBTTagCompound0.getString("id"));
		}

		return entity2;
	}

	public static Entity createEntity(int i0, World world1) {
		Entity entity2 = null;

		try {
			Class class3 = (Class)IDtoClassMapping.get(i0);
			if(class3 != null) {
				entity2 = (Entity)class3.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world1});
			}
		} catch (Exception exception4) {
			exception4.printStackTrace();
		}

		if(entity2 == null) {
			System.out.println("Skipping Entity with id " + i0);
		}

		return entity2;
	}

	public static int getEntityID(Entity entity0) {
		return ((Integer)classToIDMapping.get(entity0.getClass())).intValue();
	}

	public static String getEntityString(Entity entity0) {
		return (String)classToStringMapping.get(entity0.getClass());
	}

	static {
		addMapping(EntityArrow.class, "Arrow", 10);
		addMapping(EntitySnowball.class, "Snowball", 11);
		addMapping(EntityItem.class, "Item", 1);
		addMapping(EntityPainting.class, "Painting", 9);
		addMapping(EntityLiving.class, "Mob", 48);
		addMapping(EntityMob.class, "Monster", 49);
		addMapping(EntityCreeper.class, "Creeper", 50);
		addMapping(EntitySkeleton.class, "Skeleton", 51);
		addMapping(EntitySpider.class, "Spider", 52);
		addMapping(EntityGiantZombie.class, "Giant", 53);
		addMapping(EntityZombie.class, "Zombie", 54);
		addMapping(EntitySlime.class, "Slime", 55);
		addMapping(EntityGhast.class, "Ghast", 56);
		addMapping(EntityPigZombie.class, "PigZombie", 57);
		addMapping(EntityPig.class, "Pig", 90);
		addMapping(EntitySheep.class, "Sheep", 91);
		addMapping(EntityCow.class, "Cow", 92);
		addMapping(EntityChicken.class, "Chicken", 93);
		addMapping(EntitySquid.class, "Squid", 94);
		addMapping(EntityWolf.class, "Wolf", 95);
		addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
		addMapping(EntityFallingSand.class, "FallingSand", 21);
		addMapping(EntityMinecart.class, "Minecart", 40);
		addMapping(EntityBoat.class, "Boat", 41);
	}
}
