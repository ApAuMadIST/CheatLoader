package net.minecraft.src;

public class ItemFishingRod extends Item {
	public ItemFishingRod(int i1) {
		super(i1);
		this.setMaxDamage(64);
		this.setMaxStackSize(1);
	}

	public boolean isFull3D() {
		return true;
	}

	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}

	public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
		if(entityPlayer3.fishEntity != null) {
			int i4 = entityPlayer3.fishEntity.catchFish();
			itemStack1.damageItem(i4, entityPlayer3);
			entityPlayer3.swingItem();
		} else {
			world2.playSoundAtEntity(entityPlayer3, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			if(!world2.multiplayerWorld) {
				world2.entityJoinedWorld(new EntityFish(world2, entityPlayer3));
			}

			entityPlayer3.swingItem();
		}

		return itemStack1;
	}
}
