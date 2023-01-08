package net.minecraft.src;

import java.util.Comparator;

class RecipeSorter implements Comparator {
	final CraftingManager craftingManager;

	RecipeSorter(CraftingManager craftingManager1) {
		this.craftingManager = craftingManager1;
	}

	public int compareRecipes(IRecipe iRecipe1, IRecipe iRecipe2) {
		return iRecipe1 instanceof ShapelessRecipes && iRecipe2 instanceof ShapedRecipes ? 1 : (iRecipe2 instanceof ShapelessRecipes && iRecipe1 instanceof ShapedRecipes ? -1 : (iRecipe2.getRecipeSize() < iRecipe1.getRecipeSize() ? -1 : (iRecipe2.getRecipeSize() > iRecipe1.getRecipeSize() ? 1 : 0)));
	}

	public int compare(Object object1, Object object2) {
		return this.compareRecipes((IRecipe)object1, (IRecipe)object2);
	}
}
