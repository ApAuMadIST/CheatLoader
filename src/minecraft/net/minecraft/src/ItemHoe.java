package net.minecraft.src;

public class ItemHoe extends Item {
	public ItemHoe(int i1, EnumToolMaterial enumToolMaterial2) {
		super(i1);
		this.maxStackSize = 1;
		this.setMaxDamage(enumToolMaterial2.getMaxUses());
	}

	public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
		int i8 = world3.getBlockId(i4, i5, i6);
		int i9 = world3.getBlockId(i4, i5 + 1, i6);
		if((i7 == 0 || i9 != 0 || i8 != Block.grass.blockID) && i8 != Block.dirt.blockID) {
			return false;
		} else {
			Block block10 = Block.tilledField;
			world3.playSoundEffect((double)((float)i4 + 0.5F), (double)((float)i5 + 0.5F), (double)((float)i6 + 0.5F), block10.stepSound.func_1145_d(), (block10.stepSound.getVolume() + 1.0F) / 2.0F, block10.stepSound.getPitch() * 0.8F);
			if(world3.multiplayerWorld) {
				return true;
			} else {
				world3.setBlockWithNotify(i4, i5, i6, block10.blockID);
				itemStack1.damageItem(1, entityPlayer2);
				return true;
			}
		}
	}

	public boolean isFull3D() {
		return true;
	}
}
