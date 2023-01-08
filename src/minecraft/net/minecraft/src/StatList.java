package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StatList {
	protected static Map field_25169_C = new HashMap();
	public static List field_25188_a = new ArrayList();
	public static List field_25187_b = new ArrayList();
	public static List field_25186_c = new ArrayList();
	public static List field_25185_d = new ArrayList();
	public static StatBase startGameStat = (new StatBasic(1000, StatCollector.translateToLocal("stat.startGame"))).func_27082_h().registerStat();
	public static StatBase createWorldStat = (new StatBasic(1001, StatCollector.translateToLocal("stat.createWorld"))).func_27082_h().registerStat();
	public static StatBase loadWorldStat = (new StatBasic(1002, StatCollector.translateToLocal("stat.loadWorld"))).func_27082_h().registerStat();
	public static StatBase joinMultiplayerStat = (new StatBasic(1003, StatCollector.translateToLocal("stat.joinMultiplayer"))).func_27082_h().registerStat();
	public static StatBase leaveGameStat = (new StatBasic(1004, StatCollector.translateToLocal("stat.leaveGame"))).func_27082_h().registerStat();
	public static StatBase minutesPlayedStat = (new StatBasic(1100, StatCollector.translateToLocal("stat.playOneMinute"), StatBase.field_27086_j)).func_27082_h().registerStat();
	public static StatBase distanceWalkedStat = (new StatBasic(2000, StatCollector.translateToLocal("stat.walkOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceSwumStat = (new StatBasic(2001, StatCollector.translateToLocal("stat.swimOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceFallenStat = (new StatBasic(2002, StatCollector.translateToLocal("stat.fallOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceClimbedStat = (new StatBasic(2003, StatCollector.translateToLocal("stat.climbOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceFlownStat = (new StatBasic(2004, StatCollector.translateToLocal("stat.flyOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceDoveStat = (new StatBasic(2005, StatCollector.translateToLocal("stat.diveOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceByMinecartStat = (new StatBasic(2006, StatCollector.translateToLocal("stat.minecartOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceByBoatStat = (new StatBasic(2007, StatCollector.translateToLocal("stat.boatOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase distanceByPigStat = (new StatBasic(2008, StatCollector.translateToLocal("stat.pigOneCm"), StatBase.field_27085_k)).func_27082_h().registerStat();
	public static StatBase jumpStat = (new StatBasic(2010, StatCollector.translateToLocal("stat.jump"))).func_27082_h().registerStat();
	public static StatBase dropStat = (new StatBasic(2011, StatCollector.translateToLocal("stat.drop"))).func_27082_h().registerStat();
	public static StatBase damageDealtStat = (new StatBasic(2020, StatCollector.translateToLocal("stat.damageDealt"))).registerStat();
	public static StatBase damageTakenStat = (new StatBasic(2021, StatCollector.translateToLocal("stat.damageTaken"))).registerStat();
	public static StatBase deathsStat = (new StatBasic(2022, StatCollector.translateToLocal("stat.deaths"))).registerStat();
	public static StatBase mobKillsStat = (new StatBasic(2023, StatCollector.translateToLocal("stat.mobKills"))).registerStat();
	public static StatBase playerKillsStat = (new StatBasic(2024, StatCollector.translateToLocal("stat.playerKills"))).registerStat();
	public static StatBase fishCaughtStat = (new StatBasic(2025, StatCollector.translateToLocal("stat.fishCaught"))).registerStat();
	public static StatBase[] mineBlockStatArray = func_25153_a("stat.mineBlock", 16777216);
	public static StatBase[] field_25158_z;
	public static StatBase[] field_25172_A;
	public static StatBase[] field_25170_B;
	private static boolean field_25166_D;
	private static boolean field_25164_E;

	public static void func_27360_a() {
	}

	public static void func_25154_a() {
		field_25172_A = func_25155_a(field_25172_A, "stat.useItem", 16908288, 0, Block.blocksList.length);
		field_25170_B = func_25149_b(field_25170_B, "stat.breakItem", 16973824, 0, Block.blocksList.length);
		field_25166_D = true;
		func_25157_c();
	}

	public static void func_25151_b() {
		field_25172_A = func_25155_a(field_25172_A, "stat.useItem", 16908288, Block.blocksList.length, 32000);
		field_25170_B = func_25149_b(field_25170_B, "stat.breakItem", 16973824, Block.blocksList.length, 32000);
		field_25164_E = true;
		func_25157_c();
	}

	public static void func_25157_c() {
		if(field_25166_D && field_25164_E) {
			HashSet hashSet0 = new HashSet();
			Iterator iterator1 = CraftingManager.getInstance().getRecipeList().iterator();

			while(iterator1.hasNext()) {
				IRecipe iRecipe2 = (IRecipe)iterator1.next();
				hashSet0.add(iRecipe2.getRecipeOutput().itemID);
			}

			iterator1 = FurnaceRecipes.smelting().getSmeltingList().values().iterator();

			while(iterator1.hasNext()) {
				ItemStack itemStack4 = (ItemStack)iterator1.next();
				hashSet0.add(itemStack4.itemID);
			}

			field_25158_z = new StatBase[32000];
			iterator1 = hashSet0.iterator();

			while(iterator1.hasNext()) {
				Integer integer5 = (Integer)iterator1.next();
				if(Item.itemsList[integer5.intValue()] != null) {
					String string3 = StatCollector.translateToLocalFormatted("stat.craftItem", new Object[]{Item.itemsList[integer5.intValue()].getStatName()});
					field_25158_z[integer5.intValue()] = (new StatCrafting(16842752 + integer5.intValue(), string3, integer5.intValue())).registerStat();
				}
			}

			replaceAllSimilarBlocks(field_25158_z);
		}
	}

	private static StatBase[] func_25153_a(String string0, int i1) {
		StatBase[] statBase2 = new StatBase[256];

		for(int i3 = 0; i3 < 256; ++i3) {
			if(Block.blocksList[i3] != null && Block.blocksList[i3].getEnableStats()) {
				String string4 = StatCollector.translateToLocalFormatted(string0, new Object[]{Block.blocksList[i3].translateBlockName()});
				statBase2[i3] = (new StatCrafting(i1 + i3, string4, i3)).registerStat();
				field_25185_d.add((StatCrafting)statBase2[i3]);
			}
		}

		replaceAllSimilarBlocks(statBase2);
		return statBase2;
	}

	private static StatBase[] func_25155_a(StatBase[] statBase0, String string1, int i2, int i3, int i4) {
		if(statBase0 == null) {
			statBase0 = new StatBase[32000];
		}

		for(int i5 = i3; i5 < i4; ++i5) {
			if(Item.itemsList[i5] != null) {
				String string6 = StatCollector.translateToLocalFormatted(string1, new Object[]{Item.itemsList[i5].getStatName()});
				statBase0[i5] = (new StatCrafting(i2 + i5, string6, i5)).registerStat();
				if(i5 >= Block.blocksList.length) {
					field_25186_c.add((StatCrafting)statBase0[i5]);
				}
			}
		}

		replaceAllSimilarBlocks(statBase0);
		return statBase0;
	}

	private static StatBase[] func_25149_b(StatBase[] statBase0, String string1, int i2, int i3, int i4) {
		if(statBase0 == null) {
			statBase0 = new StatBase[32000];
		}

		for(int i5 = i3; i5 < i4; ++i5) {
			if(Item.itemsList[i5] != null && Item.itemsList[i5].isDamagable()) {
				String string6 = StatCollector.translateToLocalFormatted(string1, new Object[]{Item.itemsList[i5].getStatName()});
				statBase0[i5] = (new StatCrafting(i2 + i5, string6, i5)).registerStat();
			}
		}

		replaceAllSimilarBlocks(statBase0);
		return statBase0;
	}

	private static void replaceAllSimilarBlocks(StatBase[] statBase0) {
		replaceSimilarBlocks(statBase0, Block.waterStill.blockID, Block.waterMoving.blockID);
		replaceSimilarBlocks(statBase0, Block.lavaStill.blockID, Block.lavaStill.blockID);
		replaceSimilarBlocks(statBase0, Block.pumpkinLantern.blockID, Block.pumpkin.blockID);
		replaceSimilarBlocks(statBase0, Block.stoneOvenActive.blockID, Block.stoneOvenIdle.blockID);
		replaceSimilarBlocks(statBase0, Block.oreRedstoneGlowing.blockID, Block.oreRedstone.blockID);
		replaceSimilarBlocks(statBase0, Block.redstoneRepeaterActive.blockID, Block.redstoneRepeaterIdle.blockID);
		replaceSimilarBlocks(statBase0, Block.torchRedstoneActive.blockID, Block.torchRedstoneIdle.blockID);
		replaceSimilarBlocks(statBase0, Block.mushroomRed.blockID, Block.mushroomBrown.blockID);
		replaceSimilarBlocks(statBase0, Block.stairDouble.blockID, Block.stairSingle.blockID);
		replaceSimilarBlocks(statBase0, Block.grass.blockID, Block.dirt.blockID);
		replaceSimilarBlocks(statBase0, Block.tilledField.blockID, Block.dirt.blockID);
	}

	private static void replaceSimilarBlocks(StatBase[] statBase0, int i1, int i2) {
		if(statBase0[i1] != null && statBase0[i2] == null) {
			statBase0[i2] = statBase0[i1];
		} else {
			field_25188_a.remove(statBase0[i1]);
			field_25185_d.remove(statBase0[i1]);
			field_25187_b.remove(statBase0[i1]);
			statBase0[i1] = statBase0[i2];
		}
	}

	public static StatBase func_27361_a(int i0) {
		return (StatBase)field_25169_C.get(i0);
	}

	static {
		AchievementList.func_27374_a();
		field_25166_D = false;
		field_25164_E = false;
	}
}
