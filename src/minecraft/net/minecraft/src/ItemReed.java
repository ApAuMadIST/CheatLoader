package net.minecraft.src;

public class ItemReed extends Item {
	private int field_320_a;

	public ItemReed(int i1, Block block2) {
		super(i1);
		this.field_320_a = block2.blockID;
	}

	public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
		if(world3.getBlockId(i4, i5, i6) == Block.snow.blockID) {
			i7 = 0;
		} else {
			if(i7 == 0) {
				--i5;
			}

			if(i7 == 1) {
				++i5;
			}

			if(i7 == 2) {
				--i6;
			}

			if(i7 == 3) {
				++i6;
			}

			if(i7 == 4) {
				--i4;
			}

			if(i7 == 5) {
				++i4;
			}
		}

		if(itemStack1.stackSize == 0) {
			return false;
		} else {
			if(world3.canBlockBePlacedAt(this.field_320_a, i4, i5, i6, false, i7)) {
				Block block8 = Block.blocksList[this.field_320_a];
				if(world3.setBlockWithNotify(i4, i5, i6, this.field_320_a)) {
					Block.blocksList[this.field_320_a].onBlockPlaced(world3, i4, i5, i6, i7);
					Block.blocksList[this.field_320_a].onBlockPlacedBy(world3, i4, i5, i6, entityPlayer2);
					world3.playSoundEffect((double)((float)i4 + 0.5F), (double)((float)i5 + 0.5F), (double)((float)i6 + 0.5F), block8.stepSound.func_1145_d(), (block8.stepSound.getVolume() + 1.0F) / 2.0F, block8.stepSound.getPitch() * 0.8F);
					--itemStack1.stackSize;
				}
			}

			return true;
		}
	}
}
