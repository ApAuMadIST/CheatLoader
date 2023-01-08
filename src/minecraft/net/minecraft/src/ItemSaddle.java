package net.minecraft.src;

public class ItemSaddle extends Item {
	public ItemSaddle(int i1) {
		super(i1);
		this.maxStackSize = 1;
	}

	public void saddleEntity(ItemStack itemStack1, EntityLiving entityLiving2) {
		if(entityLiving2 instanceof EntityPig) {
			EntityPig entityPig3 = (EntityPig)entityLiving2;
			if(!entityPig3.getSaddled()) {
				entityPig3.setSaddled(true);
				--itemStack1.stackSize;
			}
		}

	}

	public boolean hitEntity(ItemStack itemStack1, EntityLiving entityLiving2, EntityLiving entityLiving3) {
		this.saddleEntity(itemStack1, entityLiving2);
		return true;
	}
}
