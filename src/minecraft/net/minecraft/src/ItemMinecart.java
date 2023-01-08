package net.minecraft.src;

public class ItemMinecart extends Item {
	public int minecartType;

	public ItemMinecart(int i1, int i2) {
		super(i1);
		this.maxStackSize = 1;
		this.minecartType = i2;
	}

	public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
		int i8 = world3.getBlockId(i4, i5, i6);
		if(BlockRail.isRailBlock(i8)) {
			if(!world3.multiplayerWorld) {
				world3.entityJoinedWorld(new EntityMinecart(world3, (double)((float)i4 + 0.5F), (double)((float)i5 + 0.5F), (double)((float)i6 + 0.5F), this.minecartType));
			}

			--itemStack1.stackSize;
			return true;
		} else {
			return false;
		}
	}
}
