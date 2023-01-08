package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

public final class ModLoader {
	private static final List animList = new LinkedList();
	private static final Map blockModels = new HashMap();
	private static final Map blockSpecialInv = new HashMap();
	private static final File cfgdir = new File(Minecraft.getMinecraftDir(), "/config/");
	private static final File cfgfile = new File(cfgdir, "ModLoader.cfg");
	public static Level cfgLoggingLevel = Level.FINER;
	private static Map classMap = null;
	private static long clock = 0L;
	public static final boolean DEBUG = false;
	private static Field field_animList = null;
	private static Field field_armorList = null;
	private static Field field_blockList = null;
	private static Field field_modifiers = null;
	private static Field field_TileEntityRenderers = null;
	private static boolean hasInit = false;
	private static int highestEntityId = 3000;
	private static final Map inGameHooks = new HashMap();
	private static final Map inGUIHooks = new HashMap();
	private static Minecraft instance = null;
	private static int itemSpriteIndex = 0;
	private static int itemSpritesLeft = 0;
	private static final Map keyList = new HashMap();
	private static final File logfile = new File(Minecraft.getMinecraftDir(), "ModLoader.txt");
	private static final Logger logger = Logger.getLogger("ModLoader");
	private static FileHandler logHandler = null;
	private static Method method_RegisterEntityID = null;
	private static Method method_RegisterTileEntity = null;
	private static final File modDir = new File(Minecraft.getMinecraftDir(), "/mods/");
	private static final LinkedList modList = new LinkedList();
	private static int nextBlockModelID = 1000;
	private static final Map overrides = new HashMap();
	public static final Properties props = new Properties();
	private static BiomeGenBase[] standardBiomes;
	private static int terrainSpriteIndex = 0;
	private static int terrainSpritesLeft = 0;
	private static String texPack = null;
	private static boolean texturesAdded = false;
	private static final boolean[] usedItemSprites = new boolean[256];
	private static final boolean[] usedTerrainSprites = new boolean[256];
	public static final String VERSION = "ModLoader Beta 1.7.3";

	public static void AddAchievementDesc(Achievement achievement, String name, String description) {
		try {
			if(achievement.statName.contains(".")) {
				String[] e = achievement.statName.split("\\.");
				if(e.length == 2) {
					String key = e[1];
					AddLocalization("achievement." + key, name);
					AddLocalization("achievement." + key + ".desc", description);
					setPrivateValue(StatBase.class, achievement, 1, StringTranslate.getInstance().translateKey("achievement." + key));
					setPrivateValue(Achievement.class, achievement, 3, StringTranslate.getInstance().translateKey("achievement." + key + ".desc"));
				} else {
					setPrivateValue(StatBase.class, achievement, 1, name);
					setPrivateValue(Achievement.class, achievement, 3, description);
				}
			} else {
				setPrivateValue(StatBase.class, achievement, 1, name);
				setPrivateValue(Achievement.class, achievement, 3, description);
			}
		} catch (IllegalArgumentException illegalArgumentException5) {
			logger.throwing("ModLoader", "AddAchievementDesc", illegalArgumentException5);
			ThrowException(illegalArgumentException5);
		} catch (SecurityException securityException6) {
			logger.throwing("ModLoader", "AddAchievementDesc", securityException6);
			ThrowException(securityException6);
		} catch (NoSuchFieldException noSuchFieldException7) {
			logger.throwing("ModLoader", "AddAchievementDesc", noSuchFieldException7);
			ThrowException(noSuchFieldException7);
		}

	}

	public static int AddAllFuel(int id) {
		logger.finest("Finding fuel for " + id);
		int result = 0;

		for(Iterator iter = modList.iterator(); iter.hasNext() && result == 0; result = ((BaseMod)iter.next()).AddFuel(id)) {
		}

		if(result != 0) {
			logger.finest("Returned " + result);
		}

		return result;
	}

	public static void AddAllRenderers(Map o) {
		if(!hasInit) {
			init();
			logger.fine("Initialized");
		}

		Iterator iterator2 = modList.iterator();

		while(iterator2.hasNext()) {
			BaseMod mod = (BaseMod)iterator2.next();
			mod.AddRenderer(o);
		}

	}

	public static void addAnimation(TextureFX anim) {
		logger.finest("Adding animation " + anim.toString());
		Iterator iterator2 = animList.iterator();

		while(iterator2.hasNext()) {
			TextureFX oldAnim = (TextureFX)iterator2.next();
			if(oldAnim.tileImage == anim.tileImage && oldAnim.iconIndex == anim.iconIndex) {
				animList.remove(anim);
				break;
			}
		}

		animList.add(anim);
	}

	public static int AddArmor(String armor) {
		try {
			String[] e = (String[])field_armorList.get((Object)null);
			List existingArmorList = Arrays.asList(e);
			ArrayList combinedList = new ArrayList();
			combinedList.addAll(existingArmorList);
			if(!combinedList.contains(armor)) {
				combinedList.add(armor);
			}

			int index = combinedList.indexOf(armor);
			field_armorList.set((Object)null, combinedList.toArray(new String[0]));
			return index;
		} catch (IllegalArgumentException illegalArgumentException5) {
			logger.throwing("ModLoader", "AddArmor", illegalArgumentException5);
			ThrowException("An impossible error has occured!", illegalArgumentException5);
		} catch (IllegalAccessException illegalAccessException6) {
			logger.throwing("ModLoader", "AddArmor", illegalAccessException6);
			ThrowException("An impossible error has occured!", illegalAccessException6);
		}

		return -1;
	}

	public static void AddLocalization(String key, String value) {
		Properties props = null;

		try {
			props = (Properties)getPrivateValue(StringTranslate.class, StringTranslate.getInstance(), 1);
		} catch (SecurityException securityException4) {
			logger.throwing("ModLoader", "AddLocalization", securityException4);
			ThrowException(securityException4);
		} catch (NoSuchFieldException noSuchFieldException5) {
			logger.throwing("ModLoader", "AddLocalization", noSuchFieldException5);
			ThrowException(noSuchFieldException5);
		}

		if(props != null) {
			props.put(key, value);
		}

	}

	private static void addMod(ClassLoader loader, String filename) {
		try {
			String e = filename.split("\\.")[0];
			if(e.contains("$")) {
				return;
			}

			if(props.containsKey(e) && (props.getProperty(e).equalsIgnoreCase("no") || props.getProperty(e).equalsIgnoreCase("off"))) {
				return;
			}

			Package pack = ModLoader.class.getPackage();
			if(pack != null) {
				e = pack.getName() + "." + e;
			}

			Class instclass = loader.loadClass(e);
			if(!BaseMod.class.isAssignableFrom(instclass)) {
				return;
			}

			setupProperties(instclass);
			BaseMod mod = (BaseMod)instclass.newInstance();
			if(mod != null) {
				modList.add(mod);
				logger.fine("Mod Loaded: \"" + mod.toString() + "\" from " + filename);
				System.out.println("Mod Loaded: " + mod.toString());
			}
		} catch (Throwable throwable6) {
			logger.fine("Failed to load mod from \"" + filename + "\"");
			System.out.println("Failed to load mod from \"" + filename + "\"");
			logger.throwing("ModLoader", "addMod", throwable6);
			ThrowException(throwable6);
		}

	}

	public static void AddName(Object instance, String name) {
		String tag = null;
		Exception e3;
		if(instance instanceof Item) {
			Item e = (Item)instance;
			if(e.getItemName() != null) {
				tag = e.getItemName() + ".name";
			}
		} else if(instance instanceof Block) {
			Block e1 = (Block)instance;
			if(e1.getBlockName() != null) {
				tag = e1.getBlockName() + ".name";
			}
		} else if(instance instanceof ItemStack) {
			ItemStack e2 = (ItemStack)instance;
			if(e2.getItemName() != null) {
				tag = e2.getItemName() + ".name";
			}
		} else {
			e3 = new Exception(instance.getClass().getName() + " cannot have name attached to it!");
			logger.throwing("ModLoader", "AddName", e3);
			ThrowException(e3);
		}

		if(tag != null) {
			AddLocalization(tag, name);
		} else {
			e3 = new Exception(instance + " is missing name tag!");
			logger.throwing("ModLoader", "AddName", e3);
			ThrowException(e3);
		}

	}

	public static int addOverride(String fileToOverride, String fileToAdd) {
		try {
			int e = getUniqueSpriteIndex(fileToOverride);
			addOverride(fileToOverride, fileToAdd, e);
			return e;
		} catch (Throwable throwable3) {
			logger.throwing("ModLoader", "addOverride", throwable3);
			ThrowException(throwable3);
			throw new RuntimeException(throwable3);
		}
	}

	public static void addOverride(String path, String overlayPath, int index) {
		boolean dst = true;
		boolean left = false;
		byte dst1;
		int left1;
		if(path.equals("/terrain.png")) {
			dst1 = 0;
			left1 = terrainSpritesLeft;
		} else {
			if(!path.equals("/gui/items.png")) {
				return;
			}

			dst1 = 1;
			left1 = itemSpritesLeft;
		}

		System.out.println("Overriding " + path + " with " + overlayPath + " @ " + index + ". " + left1 + " left.");
		logger.finer("addOverride(" + path + "," + overlayPath + "," + index + "). " + left1 + " left.");
		Object overlays = (Map)overrides.get(Integer.valueOf(dst1));
		if(overlays == null) {
			overlays = new HashMap();
			overrides.put(Integer.valueOf(dst1), overlays);
		}

		((Map)overlays).put(overlayPath, index);
	}

	public static void AddRecipe(ItemStack output, Object... params) {
		CraftingManager.getInstance().addRecipe(output, params);
	}

	public static void AddShapelessRecipe(ItemStack output, Object... params) {
		CraftingManager.getInstance().addShapelessRecipe(output, params);
	}

	public static void AddSmelting(int input, ItemStack output) {
		FurnaceRecipes.smelting().addSmelting(input, output);
	}

	public static void AddSpawn(Class entityClass, int weightedProb, EnumCreatureType spawnList) {
		AddSpawn((Class)entityClass, weightedProb, spawnList, (BiomeGenBase[])null);
	}

	public static void AddSpawn(Class entityClass, int weightedProb, EnumCreatureType spawnList, BiomeGenBase... biomes) {
		if(entityClass == null) {
			throw new IllegalArgumentException("entityClass cannot be null");
		} else if(spawnList == null) {
			throw new IllegalArgumentException("spawnList cannot be null");
		} else {
			if(biomes == null) {
				biomes = standardBiomes;
			}

			for(int i = 0; i < biomes.length; ++i) {
				List list = biomes[i].getSpawnableList(spawnList);
				if(list != null) {
					boolean exists = false;
					Iterator iterator8 = list.iterator();

					while(iterator8.hasNext()) {
						SpawnListEntry entry = (SpawnListEntry)iterator8.next();
						if(entry.entityClass == entityClass) {
							entry.spawnRarityRate = weightedProb;
							exists = true;
							break;
						}
					}

					if(!exists) {
						list.add(new SpawnListEntry(entityClass, weightedProb));
					}
				}
			}

		}
	}

	public static void AddSpawn(String entityName, int weightedProb, EnumCreatureType spawnList) {
		AddSpawn((String)entityName, weightedProb, spawnList, (BiomeGenBase[])null);
	}

	public static void AddSpawn(String entityName, int weightedProb, EnumCreatureType spawnList, BiomeGenBase... biomes) {
		Class entityClass = (Class)classMap.get(entityName);
		if(entityClass != null && EntityLiving.class.isAssignableFrom(entityClass)) {
			AddSpawn(entityClass, weightedProb, spawnList, biomes);
		}

	}

	public static boolean DispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item) {
		boolean result = false;

		for(Iterator iter = modList.iterator(); iter.hasNext() && !result; result = ((BaseMod)iter.next()).DispenseEntity(world, x, y, z, xVel, zVel, item)) {
		}

		return result;
	}

	public static List getLoadedMods() {
		return Collections.unmodifiableList(modList);
	}

	public static Logger getLogger() {
		return logger;
	}

	public static Minecraft getMinecraftInstance() {
		if(instance == null) {
			try {
				ThreadGroup e = Thread.currentThread().getThreadGroup();
				int count = e.activeCount();
				Thread[] threads = new Thread[count];
				e.enumerate(threads);

				for(int i = 0; i < threads.length; ++i) {
					if(threads[i].getName().equals("Minecraft main thread")) {
						instance = (Minecraft)getPrivateValue(Thread.class, threads[i], "target");
						break;
					}
				}
			} catch (SecurityException securityException4) {
				logger.throwing("ModLoader", "getMinecraftInstance", securityException4);
				throw new RuntimeException(securityException4);
			} catch (NoSuchFieldException noSuchFieldException5) {
				logger.throwing("ModLoader", "getMinecraftInstance", noSuchFieldException5);
				throw new RuntimeException(noSuchFieldException5);
			}
		}

		return instance;
	}

	public static Object getPrivateValue(Class instanceclass, Object instance, int fieldindex) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
		try {
			Field e = instanceclass.getDeclaredFields()[fieldindex];
			e.setAccessible(true);
			return e.get(instance);
		} catch (IllegalAccessException illegalAccessException4) {
			logger.throwing("ModLoader", "getPrivateValue", illegalAccessException4);
			ThrowException("An impossible error has occured!", illegalAccessException4);
			return null;
		}
	}

	public static Object getPrivateValue(Class instanceclass, Object instance, String field) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
		try {
			Field e = instanceclass.getDeclaredField(field);
			e.setAccessible(true);
			return e.get(instance);
		} catch (IllegalAccessException illegalAccessException4) {
			logger.throwing("ModLoader", "getPrivateValue", illegalAccessException4);
			ThrowException("An impossible error has occured!", illegalAccessException4);
			return null;
		}
	}

	public static int getUniqueBlockModelID(BaseMod mod, boolean full3DItem) {
		int id = nextBlockModelID++;
		blockModels.put(id, mod);
		blockSpecialInv.put(id, full3DItem);
		return id;
	}

	public static int getUniqueEntityId() {
		return highestEntityId++;
	}

	private static int getUniqueItemSpriteIndex() {
		while(itemSpriteIndex < usedItemSprites.length) {
			if(!usedItemSprites[itemSpriteIndex]) {
				usedItemSprites[itemSpriteIndex] = true;
				--itemSpritesLeft;
				return itemSpriteIndex++;
			}

			++itemSpriteIndex;
		}

		Exception e = new Exception("No more empty item sprite indices left!");
		logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
		ThrowException(e);
		return 0;
	}

	public static int getUniqueSpriteIndex(String path) {
		if(path.equals("/gui/items.png")) {
			return getUniqueItemSpriteIndex();
		} else if(path.equals("/terrain.png")) {
			return getUniqueTerrainSpriteIndex();
		} else {
			Exception e = new Exception("No registry for this texture: " + path);
			logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
			ThrowException(e);
			return 0;
		}
	}

	private static int getUniqueTerrainSpriteIndex() {
		while(terrainSpriteIndex < usedTerrainSprites.length) {
			if(!usedTerrainSprites[terrainSpriteIndex]) {
				usedTerrainSprites[terrainSpriteIndex] = true;
				--terrainSpritesLeft;
				return terrainSpriteIndex++;
			}

			++terrainSpriteIndex;
		}

		Exception e = new Exception("No more empty terrain sprite indices left!");
		logger.throwing("ModLoader", "getUniqueItemSpriteIndex", e);
		ThrowException(e);
		return 0;
	}

	private static void init() {
		hasInit = true;
		String usedItemSpritesString = "1111111111111111111111111111111111111101111111011111111111111001111111111111111111111111111011111111100110000011111110000000001111111001100000110000000100000011000000010000001100000000000000110000000000000000000000000000000000000000000000001100000000000000";
		String usedTerrainSpritesString = "1111111111111111111111111111110111111111111111111111110111111111111111111111000111111011111111111111001111111110111111111111100011111111000010001111011110000000111111000000000011111100000000001111000000000111111000000000001101000000000001111111111111000011";

		for(int e = 0; e < 256; ++e) {
			usedItemSprites[e] = usedItemSpritesString.charAt(e) == 49;
			if(!usedItemSprites[e]) {
				++itemSpritesLeft;
			}

			usedTerrainSprites[e] = usedTerrainSpritesString.charAt(e) == 49;
			if(!usedTerrainSprites[e]) {
				++terrainSpritesLeft;
			}
		}

		try {
			instance = (Minecraft)getPrivateValue(Minecraft.class, (Object)null, 1);
			instance.entityRenderer = new EntityRendererProxy(instance);
			classMap = (Map)getPrivateValue(EntityList.class, (Object)null, 0);
			field_modifiers = Field.class.getDeclaredField("modifiers");
			field_modifiers.setAccessible(true);
			field_blockList = Session.class.getDeclaredFields()[0];
			field_blockList.setAccessible(true);
			field_TileEntityRenderers = TileEntityRenderer.class.getDeclaredFields()[0];
			field_TileEntityRenderers.setAccessible(true);
			field_armorList = RenderPlayer.class.getDeclaredFields()[3];
			field_modifiers.setInt(field_armorList, field_armorList.getModifiers() & -17);
			field_armorList.setAccessible(true);
			field_animList = RenderEngine.class.getDeclaredFields()[6];
			field_animList.setAccessible(true);
			Field[] field15 = BiomeGenBase.class.getDeclaredFields();
			LinkedList mod = new LinkedList();

			for(int e1 = 0; e1 < field15.length; ++e1) {
				Class fieldType = field15[e1].getType();
				if((field15[e1].getModifiers() & 8) != 0 && fieldType.isAssignableFrom(BiomeGenBase.class)) {
					BiomeGenBase biome = (BiomeGenBase)field15[e1].get((Object)null);
					if(!(biome instanceof BiomeGenHell) && !(biome instanceof BiomeGenSky)) {
						mod.add(biome);
					}
				}
			}

			standardBiomes = (BiomeGenBase[])mod.toArray(new BiomeGenBase[0]);

			try {
				method_RegisterTileEntity = TileEntity.class.getDeclaredMethod("a", new Class[]{Class.class, String.class});
			} catch (NoSuchMethodException noSuchMethodException8) {
				method_RegisterTileEntity = TileEntity.class.getDeclaredMethod("addMapping", new Class[]{Class.class, String.class});
			}

			method_RegisterTileEntity.setAccessible(true);

			try {
				method_RegisterEntityID = EntityList.class.getDeclaredMethod("a", new Class[]{Class.class, String.class, Integer.TYPE});
			} catch (NoSuchMethodException noSuchMethodException7) {
				method_RegisterEntityID = EntityList.class.getDeclaredMethod("addMapping", new Class[]{Class.class, String.class, Integer.TYPE});
			}

			method_RegisterEntityID.setAccessible(true);
		} catch (SecurityException securityException10) {
			logger.throwing("ModLoader", "init", securityException10);
			ThrowException(securityException10);
			throw new RuntimeException(securityException10);
		} catch (NoSuchFieldException noSuchFieldException11) {
			logger.throwing("ModLoader", "init", noSuchFieldException11);
			ThrowException(noSuchFieldException11);
			throw new RuntimeException(noSuchFieldException11);
		} catch (NoSuchMethodException noSuchMethodException12) {
			logger.throwing("ModLoader", "init", noSuchMethodException12);
			ThrowException(noSuchMethodException12);
			throw new RuntimeException(noSuchMethodException12);
		} catch (IllegalArgumentException illegalArgumentException13) {
			logger.throwing("ModLoader", "init", illegalArgumentException13);
			ThrowException(illegalArgumentException13);
			throw new RuntimeException(illegalArgumentException13);
		} catch (IllegalAccessException illegalAccessException14) {
			logger.throwing("ModLoader", "init", illegalAccessException14);
			ThrowException(illegalAccessException14);
			throw new RuntimeException(illegalAccessException14);
		}

		try {
			loadConfig();
			if(props.containsKey("loggingLevel")) {
				cfgLoggingLevel = Level.parse(props.getProperty("loggingLevel"));
			}

			if(props.containsKey("grassFix")) {
				RenderBlocks.cfgGrassFix = Boolean.parseBoolean(props.getProperty("grassFix"));
			}

			logger.setLevel(cfgLoggingLevel);
			if((logfile.exists() || logfile.createNewFile()) && logfile.canWrite() && logHandler == null) {
				logHandler = new FileHandler(logfile.getPath());
				logHandler.setFormatter(new SimpleFormatter());
				logger.addHandler(logHandler);
			}

			logger.fine("ModLoader Beta 1.7.3 Initializing...");
			System.out.println("ModLoader Beta 1.7.3 Initializing...");
			File file16 = new File(ModLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			modDir.mkdirs();
			readFromModFolder(modDir);
			readFromClassPath(file16);
			System.out.println("Done.");
			props.setProperty("loggingLevel", cfgLoggingLevel.getName());
			props.setProperty("grassFix", Boolean.toString(RenderBlocks.cfgGrassFix));
			Iterator iterator18 = modList.iterator();

			while(iterator18.hasNext()) {
				BaseMod baseMod17 = (BaseMod)iterator18.next();
				baseMod17.ModsLoaded();
				if(!props.containsKey(baseMod17.getClass().getName())) {
					props.setProperty(baseMod17.getClass().getName(), "on");
				}
			}

			instance.gameSettings.keyBindings = RegisterAllKeys(instance.gameSettings.keyBindings);
			instance.gameSettings.loadOptions();
			initStats();
			saveConfig();
		} catch (Throwable throwable9) {
			logger.throwing("ModLoader", "init", throwable9);
			ThrowException("ModLoader has failed to initialize.", throwable9);
			if(logHandler != null) {
				logHandler.close();
			}

			throw new RuntimeException(throwable9);
		}
	}

	private static void initStats() {
		int idHashSet;
		String id;
		for(idHashSet = 0; idHashSet < Block.blocksList.length; ++idHashSet) {
			if(!StatList.field_25169_C.containsKey(16777216 + idHashSet) && Block.blocksList[idHashSet] != null && Block.blocksList[idHashSet].getEnableStats()) {
				id = StringTranslate.getInstance().translateKeyFormat("stat.mineBlock", new Object[]{Block.blocksList[idHashSet].translateBlockName()});
				StatList.mineBlockStatArray[idHashSet] = (new StatCrafting(16777216 + idHashSet, id, idHashSet)).registerStat();
				StatList.field_25185_d.add(StatList.mineBlockStatArray[idHashSet]);
			}
		}

		for(idHashSet = 0; idHashSet < Item.itemsList.length; ++idHashSet) {
			if(!StatList.field_25169_C.containsKey(16908288 + idHashSet) && Item.itemsList[idHashSet] != null) {
				id = StringTranslate.getInstance().translateKeyFormat("stat.useItem", new Object[]{Item.itemsList[idHashSet].getStatName()});
				StatList.field_25172_A[idHashSet] = (new StatCrafting(16908288 + idHashSet, id, idHashSet)).registerStat();
				if(idHashSet >= Block.blocksList.length) {
					StatList.field_25186_c.add(StatList.field_25172_A[idHashSet]);
				}
			}

			if(!StatList.field_25169_C.containsKey(16973824 + idHashSet) && Item.itemsList[idHashSet] != null && Item.itemsList[idHashSet].isDamagable()) {
				id = StringTranslate.getInstance().translateKeyFormat("stat.breakItem", new Object[]{Item.itemsList[idHashSet].getStatName()});
				StatList.field_25170_B[idHashSet] = (new StatCrafting(16973824 + idHashSet, id, idHashSet)).registerStat();
			}
		}

		HashSet hashSet4 = new HashSet();
		Iterator iterator2 = CraftingManager.getInstance().getRecipeList().iterator();

		Object object5;
		while(iterator2.hasNext()) {
			object5 = iterator2.next();
			hashSet4.add(((IRecipe)object5).getRecipeOutput().itemID);
		}

		iterator2 = FurnaceRecipes.smelting().getSmeltingList().values().iterator();

		while(iterator2.hasNext()) {
			object5 = iterator2.next();
			hashSet4.add(((ItemStack)object5).itemID);
		}

		iterator2 = hashSet4.iterator();

		while(iterator2.hasNext()) {
			int i6 = ((Integer)iterator2.next()).intValue();
			if(!StatList.field_25169_C.containsKey(16842752 + i6) && Item.itemsList[i6] != null) {
				String str = StringTranslate.getInstance().translateKeyFormat("stat.craftItem", new Object[]{Item.itemsList[i6].getStatName()});
				StatList.field_25158_z[i6] = (new StatCrafting(16842752 + i6, str, i6)).registerStat();
			}
		}

	}

	public static boolean isGUIOpen(Class gui) {
		Minecraft game = getMinecraftInstance();
		return gui == null ? game.currentScreen == null : (game.currentScreen == null && gui != null ? false : gui.isInstance(game.currentScreen));
	}

	public static boolean isModLoaded(String modname) {
		Class chk = null;

		try {
			chk = Class.forName(modname);
		} catch (ClassNotFoundException classNotFoundException4) {
			return false;
		}

		if(chk != null) {
			Iterator iterator3 = modList.iterator();

			while(iterator3.hasNext()) {
				BaseMod mod = (BaseMod)iterator3.next();
				if(chk.isInstance(mod)) {
					return true;
				}
			}
		}

		return false;
	}

	public static void loadConfig() throws IOException {
		cfgdir.mkdir();
		if(cfgfile.exists() || cfgfile.createNewFile()) {
			if(cfgfile.canRead()) {
				FileInputStream in = new FileInputStream(cfgfile);
				props.load(in);
				in.close();
			}

		}
	}

	public static BufferedImage loadImage(RenderEngine texCache, String path) throws Exception {
		TexturePackList pack = (TexturePackList)getPrivateValue(RenderEngine.class, texCache, 11);
		InputStream input = pack.selectedTexturePack.getResourceAsStream(path);
		if(input == null) {
			throw new Exception("Image not found: " + path);
		} else {
			BufferedImage image = ImageIO.read(input);
			if(image == null) {
				throw new Exception("Image corrupted: " + path);
			} else {
				return image;
			}
		}
	}

	public static void OnItemPickup(EntityPlayer player, ItemStack item) {
		Iterator iterator3 = modList.iterator();

		while(iterator3.hasNext()) {
			BaseMod mod = (BaseMod)iterator3.next();
			mod.OnItemPickup(player, item);
		}

	}

	public static void OnTick(Minecraft game) {
		if(!hasInit) {
			init();
			logger.fine("Initialized");
		}

		if(texPack == null || game.gameSettings.skin != texPack) {
			texturesAdded = false;
			texPack = game.gameSettings.skin;
		}

		if(!texturesAdded && game.renderEngine != null) {
			RegisterAllTextureOverrides(game.renderEngine);
			texturesAdded = true;
		}

		long newclock = 0L;
		Iterator modSet;
		Entry modSet1;
		if(game.theWorld != null) {
			newclock = game.theWorld.getWorldTime();
			modSet = inGameHooks.entrySet().iterator();

			label93:
			while(true) {
				do {
					if(!modSet.hasNext()) {
						break label93;
					}

					modSet1 = (Entry)modSet.next();
				} while(clock == newclock && ((Boolean)modSet1.getValue()).booleanValue());

				if(!((BaseMod)modSet1.getKey()).OnTickInGame(game)) {
					modSet.remove();
				}
			}
		}

		if(game.currentScreen != null) {
			modSet = inGUIHooks.entrySet().iterator();

			label80:
			while(true) {
				do {
					if(!modSet.hasNext()) {
						break label80;
					}

					modSet1 = (Entry)modSet.next();
				} while(clock == newclock && ((Boolean)modSet1.getValue()).booleanValue() & game.theWorld != null);

				if(!((BaseMod)modSet1.getKey()).OnTickInGUI(game, game.currentScreen)) {
					modSet.remove();
				}
			}
		}

		if(clock != newclock) {
			Iterator modSet3 = keyList.entrySet().iterator();

			label66:
			while(modSet3.hasNext()) {
				Entry modSet2 = (Entry)modSet3.next();
				Iterator iterator6 = ((Map)modSet2.getValue()).entrySet().iterator();

				while(true) {
					Entry keySet;
					boolean state;
					boolean[] keyInfo;
					boolean oldState;
					do {
						do {
							if(!iterator6.hasNext()) {
								continue label66;
							}

							keySet = (Entry)iterator6.next();
							state = Keyboard.isKeyDown(((KeyBinding)keySet.getKey()).keyCode);
							keyInfo = (boolean[])keySet.getValue();
							oldState = keyInfo[1];
							keyInfo[1] = state;
						} while(!state);
					} while(oldState && !keyInfo[0]);

					((BaseMod)modSet2.getKey()).KeyboardEvent((KeyBinding)keySet.getKey());
				}
			}
		}

		clock = newclock;
	}

	public static void OpenGUI(EntityPlayer player, GuiScreen gui) {
		if(!hasInit) {
			init();
			logger.fine("Initialized");
		}

		Minecraft game = getMinecraftInstance();
		if(game.thePlayer == player) {
			if(gui != null) {
				game.displayGuiScreen(gui);
			}

		}
	}

	public static void PopulateChunk(IChunkProvider generator, int chunkX, int chunkZ, World world) {
		if(!hasInit) {
			init();
			logger.fine("Initialized");
		}

		Random rnd = new Random(world.getRandomSeed());
		long xSeed = rnd.nextLong() / 2L * 2L + 1L;
		long zSeed = rnd.nextLong() / 2L * 2L + 1L;
		rnd.setSeed((long)chunkX * xSeed + (long)chunkZ * zSeed ^ world.getRandomSeed());
		Iterator iterator10 = modList.iterator();

		while(iterator10.hasNext()) {
			BaseMod mod = (BaseMod)iterator10.next();
			if(generator.makeString().equals("RandomLevelSource")) {
				mod.GenerateSurface(world, rnd, chunkX << 4, chunkZ << 4);
			} else if(generator.makeString().equals("HellRandomLevelSource")) {
				mod.GenerateNether(world, rnd, chunkX << 4, chunkZ << 4);
			}
		}

	}

	private static void readFromClassPath(File source) throws FileNotFoundException, IOException {
		logger.finer("Adding mods from " + source.getCanonicalPath());
		ClassLoader loader = ModLoader.class.getClassLoader();
		String name;
		if(!source.isFile() || !source.getName().endsWith(".jar") && !source.getName().endsWith(".zip")) {
			if(source.isDirectory()) {
				Package package6 = ModLoader.class.getPackage();
				if(package6 != null) {
					String string7 = package6.getName().replace('.', File.separatorChar);
					source = new File(source, string7);
				}

				logger.finer("Directory found.");
				File[] file8 = source.listFiles();
				if(file8 != null) {
					for(int i9 = 0; i9 < file8.length; ++i9) {
						name = file8[i9].getName();
						if(file8[i9].isFile() && name.startsWith("mod_") && name.endsWith(".class")) {
							addMod(loader, name);
						}
					}
				}
			}
		} else {
			logger.finer("Zip found.");
			FileInputStream pkg = new FileInputStream(source);
			ZipInputStream files = new ZipInputStream(pkg);
			ZipEntry i = null;

			while(true) {
				i = files.getNextEntry();
				if(i == null) {
					pkg.close();
					break;
				}

				name = i.getName();
				if(!i.isDirectory() && name.startsWith("mod_") && name.endsWith(".class")) {
					addMod(loader, name);
				}
			}
		}

	}

	private static void readFromModFolder(File folder) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		ClassLoader loader = Minecraft.class.getClassLoader();
		Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
		addURL.setAccessible(true);
		if(!folder.isDirectory()) {
			throw new IllegalArgumentException("folder must be a Directory.");
		} else {
			File[] sourcefiles = folder.listFiles();
			int file;
			File source;
			if(loader instanceof URLClassLoader) {
				for(file = 0; file < sourcefiles.length; ++file) {
					source = sourcefiles[file];
					if(source.isDirectory() || source.isFile() && (source.getName().endsWith(".jar") || source.getName().endsWith(".zip"))) {
						addURL.invoke(loader, new Object[]{source.toURI().toURL()});
					}
				}
			}

			for(file = 0; file < sourcefiles.length; ++file) {
				source = sourcefiles[file];
				if(source.isDirectory() || source.isFile() && (source.getName().endsWith(".jar") || source.getName().endsWith(".zip"))) {
					logger.finer("Adding mods from " + source.getCanonicalPath());
					String name;
					if(!source.isFile()) {
						if(source.isDirectory()) {
							Package package10 = ModLoader.class.getPackage();
							if(package10 != null) {
								String string11 = package10.getName().replace('.', File.separatorChar);
								source = new File(source, string11);
							}

							logger.finer("Directory found.");
							File[] file12 = source.listFiles();
							if(file12 != null) {
								for(int i13 = 0; i13 < file12.length; ++i13) {
									name = file12[i13].getName();
									if(file12[i13].isFile() && name.startsWith("mod_") && name.endsWith(".class")) {
										addMod(loader, name);
									}
								}
							}
						}
					} else {
						logger.finer("Zip found.");
						FileInputStream pkg = new FileInputStream(source);
						ZipInputStream dirfiles = new ZipInputStream(pkg);
						ZipEntry j = null;

						while(true) {
							j = dirfiles.getNextEntry();
							if(j == null) {
								dirfiles.close();
								pkg.close();
								break;
							}

							name = j.getName();
							if(!j.isDirectory() && name.startsWith("mod_") && name.endsWith(".class")) {
								addMod(loader, name);
							}
						}
					}
				}
			}

		}
	}

	public static KeyBinding[] RegisterAllKeys(KeyBinding[] w) {
		LinkedList combinedList = new LinkedList();
		combinedList.addAll(Arrays.asList(w));
		Iterator iterator3 = keyList.values().iterator();

		while(iterator3.hasNext()) {
			Map keyMap = (Map)iterator3.next();
			combinedList.addAll(keyMap.keySet());
		}

		return (KeyBinding[])combinedList.toArray(new KeyBinding[0]);
	}

	public static void RegisterAllTextureOverrides(RenderEngine texCache) {
		animList.clear();
		Minecraft game = getMinecraftInstance();
		Iterator iterator3 = modList.iterator();

		while(iterator3.hasNext()) {
			BaseMod overlay = (BaseMod)iterator3.next();
			overlay.RegisterAnimation(game);
		}

		iterator3 = animList.iterator();

		while(iterator3.hasNext()) {
			TextureFX overlay1 = (TextureFX)iterator3.next();
			texCache.registerTextureFX(overlay1);
		}

		iterator3 = overrides.entrySet().iterator();

		while(iterator3.hasNext()) {
			Entry overlay2 = (Entry)iterator3.next();
			Iterator iterator5 = ((Map)overlay2.getValue()).entrySet().iterator();

			while(iterator5.hasNext()) {
				Entry overlayEntry = (Entry)iterator5.next();
				String overlayPath = (String)overlayEntry.getKey();
				int index = ((Integer)overlayEntry.getValue()).intValue();
				int dst = ((Integer)overlay2.getKey()).intValue();

				try {
					BufferedImage e = loadImage(texCache, overlayPath);
					ModTextureStatic anim = new ModTextureStatic(index, dst, e);
					texCache.registerTextureFX(anim);
				} catch (Exception exception11) {
					logger.throwing("ModLoader", "RegisterAllTextureOverrides", exception11);
					ThrowException(exception11);
					throw new RuntimeException(exception11);
				}
			}
		}

	}

	public static void RegisterBlock(Block block) {
		RegisterBlock(block, (Class)null);
	}

	public static void RegisterBlock(Block block, Class itemclass) {
		try {
			if(block == null) {
				throw new IllegalArgumentException("block parameter cannot be null.");
			}

			List e = (List)field_blockList.get((Object)null);
			e.add(block);
			int id = block.blockID;
			ItemBlock item = null;
			if(itemclass != null) {
				item = (ItemBlock)itemclass.getConstructor(new Class[]{Integer.TYPE}).newInstance(new Object[]{id - 256});
			} else {
				item = new ItemBlock(id - 256);
			}

			if(Block.blocksList[id] != null && Item.itemsList[id] == null) {
				Item.itemsList[id] = item;
			}
		} catch (IllegalArgumentException illegalArgumentException5) {
			logger.throwing("ModLoader", "RegisterBlock", illegalArgumentException5);
			ThrowException(illegalArgumentException5);
		} catch (IllegalAccessException illegalAccessException6) {
			logger.throwing("ModLoader", "RegisterBlock", illegalAccessException6);
			ThrowException(illegalAccessException6);
		} catch (SecurityException securityException7) {
			logger.throwing("ModLoader", "RegisterBlock", securityException7);
			ThrowException(securityException7);
		} catch (InstantiationException instantiationException8) {
			logger.throwing("ModLoader", "RegisterBlock", instantiationException8);
			ThrowException(instantiationException8);
		} catch (InvocationTargetException invocationTargetException9) {
			logger.throwing("ModLoader", "RegisterBlock", invocationTargetException9);
			ThrowException(invocationTargetException9);
		} catch (NoSuchMethodException noSuchMethodException10) {
			logger.throwing("ModLoader", "RegisterBlock", noSuchMethodException10);
			ThrowException(noSuchMethodException10);
		}

	}

	public static void RegisterEntityID(Class entityClass, String entityName, int id) {
		try {
			method_RegisterEntityID.invoke((Object)null, new Object[]{entityClass, entityName, id});
		} catch (IllegalArgumentException illegalArgumentException4) {
			logger.throwing("ModLoader", "RegisterEntityID", illegalArgumentException4);
			ThrowException(illegalArgumentException4);
		} catch (IllegalAccessException illegalAccessException5) {
			logger.throwing("ModLoader", "RegisterEntityID", illegalAccessException5);
			ThrowException(illegalAccessException5);
		} catch (InvocationTargetException invocationTargetException6) {
			logger.throwing("ModLoader", "RegisterEntityID", invocationTargetException6);
			ThrowException(invocationTargetException6);
		}

	}

	public static void RegisterKey(BaseMod mod, KeyBinding keyHandler, boolean allowRepeat) {
		Object keyMap = (Map)keyList.get(mod);
		if(keyMap == null) {
			keyMap = new HashMap();
		}

		((Map)keyMap).put(keyHandler, new boolean[]{allowRepeat, false});
		keyList.put(mod, keyMap);
	}

	public static void RegisterTileEntity(Class tileEntityClass, String id) {
		RegisterTileEntity(tileEntityClass, id, (TileEntitySpecialRenderer)null);
	}

	public static void RegisterTileEntity(Class tileEntityClass, String id, TileEntitySpecialRenderer renderer) {
		try {
			method_RegisterTileEntity.invoke((Object)null, new Object[]{tileEntityClass, id});
			if(renderer != null) {
				TileEntityRenderer e = TileEntityRenderer.instance;
				Map renderers = (Map)field_TileEntityRenderers.get(e);
				renderers.put(tileEntityClass, renderer);
				renderer.setTileEntityRenderer(e);
			}
		} catch (IllegalArgumentException illegalArgumentException5) {
			logger.throwing("ModLoader", "RegisterTileEntity", illegalArgumentException5);
			ThrowException(illegalArgumentException5);
		} catch (IllegalAccessException illegalAccessException6) {
			logger.throwing("ModLoader", "RegisterTileEntity", illegalAccessException6);
			ThrowException(illegalAccessException6);
		} catch (InvocationTargetException invocationTargetException7) {
			logger.throwing("ModLoader", "RegisterTileEntity", invocationTargetException7);
			ThrowException(invocationTargetException7);
		}

	}

	public static void RemoveSpawn(Class entityClass, EnumCreatureType spawnList) {
		RemoveSpawn((Class)entityClass, spawnList, (BiomeGenBase[])null);
	}

	public static void RemoveSpawn(Class entityClass, EnumCreatureType spawnList, BiomeGenBase... biomes) {
		if(entityClass == null) {
			throw new IllegalArgumentException("entityClass cannot be null");
		} else if(spawnList == null) {
			throw new IllegalArgumentException("spawnList cannot be null");
		} else {
			if(biomes == null) {
				biomes = standardBiomes;
			}

			for(int i = 0; i < biomes.length; ++i) {
				List list = biomes[i].getSpawnableList(spawnList);
				if(list != null) {
					Iterator iter = list.iterator();

					while(iter.hasNext()) {
						SpawnListEntry entry = (SpawnListEntry)iter.next();
						if(entry.entityClass == entityClass) {
							iter.remove();
						}
					}
				}
			}

		}
	}

	public static void RemoveSpawn(String entityName, EnumCreatureType spawnList) {
		RemoveSpawn((String)entityName, spawnList, (BiomeGenBase[])null);
	}

	public static void RemoveSpawn(String entityName, EnumCreatureType spawnList, BiomeGenBase... biomes) {
		Class entityClass = (Class)classMap.get(entityName);
		if(entityClass != null && EntityLiving.class.isAssignableFrom(entityClass)) {
			RemoveSpawn(entityClass, spawnList, biomes);
		}

	}

	public static boolean RenderBlockIsItemFull3D(int modelID) {
		return !blockSpecialInv.containsKey(modelID) ? modelID == 16 : ((Boolean)blockSpecialInv.get(modelID)).booleanValue();
	}

	public static void RenderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID) {
		BaseMod mod = (BaseMod)blockModels.get(modelID);
		if(mod != null) {
			mod.RenderInvBlock(renderer, block, metadata, modelID);
		}
	}

	public static boolean RenderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID) {
		BaseMod mod = (BaseMod)blockModels.get(modelID);
		return mod == null ? false : mod.RenderWorldBlock(renderer, world, x, y, z, block, modelID);
	}

	public static void saveConfig() throws IOException {
		cfgdir.mkdir();
		if(cfgfile.exists() || cfgfile.createNewFile()) {
			if(cfgfile.canWrite()) {
				FileOutputStream out = new FileOutputStream(cfgfile);
				props.store(out, "ModLoader Config");
				out.close();
			}

		}
	}

	public static void SetInGameHook(BaseMod mod, boolean enable, boolean useClock) {
		if(enable) {
			inGameHooks.put(mod, useClock);
		} else {
			inGameHooks.remove(mod);
		}

	}

	public static void SetInGUIHook(BaseMod mod, boolean enable, boolean useClock) {
		if(enable) {
			inGUIHooks.put(mod, useClock);
		} else {
			inGUIHooks.remove(mod);
		}

	}

	public static void setPrivateValue(Class instanceclass, Object instance, int fieldindex, Object value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
		try {
			Field e = instanceclass.getDeclaredFields()[fieldindex];
			e.setAccessible(true);
			int modifiers = field_modifiers.getInt(e);
			if((modifiers & 16) != 0) {
				field_modifiers.setInt(e, modifiers & -17);
			}

			e.set(instance, value);
		} catch (IllegalAccessException illegalAccessException6) {
			logger.throwing("ModLoader", "setPrivateValue", illegalAccessException6);
			ThrowException("An impossible error has occured!", illegalAccessException6);
		}

	}

	public static void setPrivateValue(Class instanceclass, Object instance, String field, Object value) throws IllegalArgumentException, SecurityException, NoSuchFieldException {
		try {
			Field e = instanceclass.getDeclaredField(field);
			int modifiers = field_modifiers.getInt(e);
			if((modifiers & 16) != 0) {
				field_modifiers.setInt(e, modifiers & -17);
			}

			e.setAccessible(true);
			e.set(instance, value);
		} catch (IllegalAccessException illegalAccessException6) {
			logger.throwing("ModLoader", "setPrivateValue", illegalAccessException6);
			ThrowException("An impossible error has occured!", illegalAccessException6);
		}

	}

	private static void setupProperties(Class mod) throws IllegalArgumentException, IllegalAccessException, IOException, SecurityException, NoSuchFieldException {
		Properties modprops = new Properties();
		File modcfgfile = new File(cfgdir, mod.getName() + ".cfg");
		if(modcfgfile.exists() && modcfgfile.canRead()) {
			modprops.load(new FileInputStream(modcfgfile));
		}

		StringBuilder helptext = new StringBuilder();
		Field[] field7;
		int i6 = (field7 = mod.getFields()).length;

		for(int i5 = 0; i5 < i6; ++i5) {
			Field field = field7[i5];
			if((field.getModifiers() & 8) != 0 && field.isAnnotationPresent(MLProp.class)) {
				Class type = field.getType();
				MLProp annotation = (MLProp)field.getAnnotation(MLProp.class);
				String key = annotation.name().length() == 0 ? field.getName() : annotation.name();
				Object currentvalue = field.get((Object)null);
				StringBuilder range = new StringBuilder();
				if(annotation.min() != Double.NEGATIVE_INFINITY) {
					range.append(String.format(",>=%.1f", new Object[]{annotation.min()}));
				}

				if(annotation.max() != Double.POSITIVE_INFINITY) {
					range.append(String.format(",<=%.1f", new Object[]{annotation.max()}));
				}

				StringBuilder info = new StringBuilder();
				if(annotation.info().length() > 0) {
					info.append(" -- ");
					info.append(annotation.info());
				}

				helptext.append(String.format("%s (%s:%s%s)%s\n", new Object[]{key, type.getName(), currentvalue, range, info}));
				if(modprops.containsKey(key)) {
					String strvalue = modprops.getProperty(key);
					Object value = null;
					if(type.isAssignableFrom(String.class)) {
						value = strvalue;
					} else if(type.isAssignableFrom(Integer.TYPE)) {
						value = Integer.parseInt(strvalue);
					} else if(type.isAssignableFrom(Short.TYPE)) {
						value = Short.parseShort(strvalue);
					} else if(type.isAssignableFrom(Byte.TYPE)) {
						value = Byte.parseByte(strvalue);
					} else if(type.isAssignableFrom(Boolean.TYPE)) {
						value = Boolean.parseBoolean(strvalue);
					} else if(type.isAssignableFrom(Float.TYPE)) {
						value = Float.parseFloat(strvalue);
					} else if(type.isAssignableFrom(Double.TYPE)) {
						value = Double.parseDouble(strvalue);
					}

					if(value != null) {
						if(value instanceof Number) {
							double num = ((Number)value).doubleValue();
							if(annotation.min() != Double.NEGATIVE_INFINITY && num < annotation.min() || annotation.max() != Double.POSITIVE_INFINITY && num > annotation.max()) {
								continue;
							}
						}

						logger.finer(key + " set to " + value);
						if(!value.equals(currentvalue)) {
							field.set((Object)null, value);
						}
					}
				} else {
					logger.finer(key + " not in config, using default: " + currentvalue);
					modprops.setProperty(key, currentvalue.toString());
				}
			}
		}

		if(!modprops.isEmpty() && (modcfgfile.exists() || modcfgfile.createNewFile()) && modcfgfile.canWrite()) {
			modprops.store(new FileOutputStream(modcfgfile), helptext.toString());
		}

	}

	public static void TakenFromCrafting(EntityPlayer player, ItemStack item) {
		Iterator iterator3 = modList.iterator();

		while(iterator3.hasNext()) {
			BaseMod mod = (BaseMod)iterator3.next();
			mod.TakenFromCrafting(player, item);
		}

	}

	public static void TakenFromFurnace(EntityPlayer player, ItemStack item) {
		Iterator iterator3 = modList.iterator();

		while(iterator3.hasNext()) {
			BaseMod mod = (BaseMod)iterator3.next();
			mod.TakenFromFurnace(player, item);
		}

	}

	public static void ThrowException(String message, Throwable e) {
		Minecraft game = getMinecraftInstance();
		if(game != null) {
			game.displayUnexpectedThrowable(new UnexpectedThrowable(message, e));
		} else {
			throw new RuntimeException(e);
		}
	}

	private static void ThrowException(Throwable e) {
		ThrowException("Exception occured in ModLoader", e);
	}
}
