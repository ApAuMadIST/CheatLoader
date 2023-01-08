package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class FurnaceRecipes {
	private static final FurnaceRecipes smeltingBase = new FurnaceRecipes();
	private Map smeltingList = new HashMap();

	public static final FurnaceRecipes smelting() {
		return smeltingBase;
	}

	private FurnaceRecipes() {
		this.addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron));
		this.addSmelting(Block.oreGold.blockID, new ItemStack(Item.ingotGold));
		this.addSmelting(Block.oreDiamond.blockID, new ItemStack(Item.diamond));
		this.addSmelting(Block.sand.blockID, new ItemStack(Block.glass));
		this.addSmelting(Item.porkRaw.shiftedIndex, new ItemStack(Item.porkCooked));
		this.addSmelting(Item.fishRaw.shiftedIndex, new ItemStack(Item.fishCooked));
		this.addSmelting(Block.cobblestone.blockID, new ItemStack(Block.stone));
		this.addSmelting(Item.clay.shiftedIndex, new ItemStack(Item.brick));
		this.addSmelting(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2));
		this.addSmelting(Block.wood.blockID, new ItemStack(Item.coal, 1, 1));
	}

	public void addSmelting(int i1, ItemStack itemStack2) {
		this.smeltingList.put(i1, itemStack2);
	}

	public ItemStack getSmeltingResult(int i1) {
		return (ItemStack)this.smeltingList.get(i1);
	}

	public Map getSmeltingList() {
		return this.smeltingList;
	}
}
