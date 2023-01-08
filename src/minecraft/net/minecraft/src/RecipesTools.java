package net.minecraft.src;

public class RecipesTools {
	private String[][] recipePatterns = new String[][]{{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
	private Object[][] recipeItems = new Object[][]{{Block.planks, Block.cobblestone, Item.ingotIron, Item.diamond, Item.ingotGold}, {Item.pickaxeWood, Item.pickaxeStone, Item.pickaxeSteel, Item.pickaxeDiamond, Item.pickaxeGold}, {Item.shovelWood, Item.shovelStone, Item.shovelSteel, Item.shovelDiamond, Item.shovelGold}, {Item.axeWood, Item.axeStone, Item.axeSteel, Item.axeDiamond, Item.axeGold}, {Item.hoeWood, Item.hoeStone, Item.hoeSteel, Item.hoeDiamond, Item.hoeGold}};

	public void addRecipes(CraftingManager craftingManager1) {
		for(int i2 = 0; i2 < this.recipeItems[0].length; ++i2) {
			Object object3 = this.recipeItems[0][i2];

			for(int i4 = 0; i4 < this.recipeItems.length - 1; ++i4) {
				Item item5 = (Item)this.recipeItems[i4 + 1][i2];
				craftingManager1.addRecipe(new ItemStack(item5), new Object[]{this.recipePatterns[i4], '#', Item.stick, 'X', object3});
			}
		}

		craftingManager1.addRecipe(new ItemStack(Item.shears), new Object[]{" #", "# ", '#', Item.ingotIron});
	}
}
