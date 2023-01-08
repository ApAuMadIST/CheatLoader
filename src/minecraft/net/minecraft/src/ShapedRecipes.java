package net.minecraft.src;

public class ShapedRecipes implements IRecipe {
	private int recipeWidth;
	private int recipeHeight;
	private ItemStack[] recipeItems;
	private ItemStack recipeOutput;
	public final int recipeOutputItemID;

	public ShapedRecipes(int i1, int i2, ItemStack[] itemStack3, ItemStack itemStack4) {
		this.recipeOutputItemID = itemStack4.itemID;
		this.recipeWidth = i1;
		this.recipeHeight = i2;
		this.recipeItems = itemStack3;
		this.recipeOutput = itemStack4;
	}

	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}

	public boolean matches(InventoryCrafting inventoryCrafting1) {
		for(int i2 = 0; i2 <= 3 - this.recipeWidth; ++i2) {
			for(int i3 = 0; i3 <= 3 - this.recipeHeight; ++i3) {
				if(this.func_21137_a(inventoryCrafting1, i2, i3, true)) {
					return true;
				}

				if(this.func_21137_a(inventoryCrafting1, i2, i3, false)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean func_21137_a(InventoryCrafting inventoryCrafting1, int i2, int i3, boolean z4) {
		for(int i5 = 0; i5 < 3; ++i5) {
			for(int i6 = 0; i6 < 3; ++i6) {
				int i7 = i5 - i2;
				int i8 = i6 - i3;
				ItemStack itemStack9 = null;
				if(i7 >= 0 && i8 >= 0 && i7 < this.recipeWidth && i8 < this.recipeHeight) {
					if(z4) {
						itemStack9 = this.recipeItems[this.recipeWidth - i7 - 1 + i8 * this.recipeWidth];
					} else {
						itemStack9 = this.recipeItems[i7 + i8 * this.recipeWidth];
					}
				}

				ItemStack itemStack10 = inventoryCrafting1.func_21103_b(i5, i6);
				if(itemStack10 != null || itemStack9 != null) {
					if(itemStack10 == null && itemStack9 != null || itemStack10 != null && itemStack9 == null) {
						return false;
					}

					if(itemStack9.itemID != itemStack10.itemID) {
						return false;
					}

					if(itemStack9.getItemDamage() != -1 && itemStack9.getItemDamage() != itemStack10.getItemDamage()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting1) {
		return new ItemStack(this.recipeOutput.itemID, this.recipeOutput.stackSize, this.recipeOutput.getItemDamage());
	}

	public int getRecipeSize() {
		return this.recipeWidth * this.recipeHeight;
	}
}
