package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapelessRecipes implements IRecipe {
	private final ItemStack recipeOutput;
	private final List recipeItems;

	public ShapelessRecipes(ItemStack itemStack1, List list2) {
		this.recipeOutput = itemStack1;
		this.recipeItems = list2;
	}

	public ItemStack getRecipeOutput() {
		return this.recipeOutput;
	}

	public boolean matches(InventoryCrafting inventoryCrafting1) {
		ArrayList arrayList2 = new ArrayList(this.recipeItems);

		for(int i3 = 0; i3 < 3; ++i3) {
			for(int i4 = 0; i4 < 3; ++i4) {
				ItemStack itemStack5 = inventoryCrafting1.func_21103_b(i4, i3);
				if(itemStack5 != null) {
					boolean z6 = false;
					Iterator iterator7 = arrayList2.iterator();

					while(iterator7.hasNext()) {
						ItemStack itemStack8 = (ItemStack)iterator7.next();
						if(itemStack5.itemID == itemStack8.itemID && (itemStack8.getItemDamage() == -1 || itemStack5.getItemDamage() == itemStack8.getItemDamage())) {
							z6 = true;
							arrayList2.remove(itemStack8);
							break;
						}
					}

					if(!z6) {
						return false;
					}
				}
			}
		}

		return arrayList2.isEmpty();
	}

	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting1) {
		return this.recipeOutput.copy();
	}

	public int getRecipeSize() {
		return this.recipeItems.size();
	}
}
