package net.minecraft.src;

public class ItemAxe extends ItemTool {
	private static Block[] blocksEffectiveAgainst = new Block[]{Block.planks, Block.bookShelf, Block.wood, Block.chest};

	protected ItemAxe(int i1, EnumToolMaterial enumToolMaterial2) {
		super(i1, 3, enumToolMaterial2, blocksEffectiveAgainst);
	}
}
