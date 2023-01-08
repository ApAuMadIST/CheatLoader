package net.minecraft.src;

import java.util.Random;

public class BlockOre extends Block {
	public BlockOre(int i1, int i2) {
		super(i1, i2, Material.rock);
	}

	public int idDropped(int i1, Random random2) {
		return this.blockID == Block.oreCoal.blockID ? Item.coal.shiftedIndex : (this.blockID == Block.oreDiamond.blockID ? Item.diamond.shiftedIndex : (this.blockID == Block.oreLapis.blockID ? Item.dyePowder.shiftedIndex : this.blockID));
	}

	public int quantityDropped(Random random1) {
		return this.blockID == Block.oreLapis.blockID ? 4 + random1.nextInt(5) : 1;
	}

	protected int damageDropped(int i1) {
		return this.blockID == Block.oreLapis.blockID ? 4 : 0;
	}
}
