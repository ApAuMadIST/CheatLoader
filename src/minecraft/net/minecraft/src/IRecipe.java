package net.minecraft.src;

public interface IRecipe {
	boolean matches(InventoryCrafting inventoryCrafting1);

	ItemStack getCraftingResult(InventoryCrafting inventoryCrafting1);

	int getRecipeSize();

	ItemStack getRecipeOutput();
}
