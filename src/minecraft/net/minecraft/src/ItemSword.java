package net.minecraft.src;

public class ItemSword extends Item {
	private int weaponDamage;

	public ItemSword(int i1, EnumToolMaterial enumToolMaterial2) {
		super(i1);
		this.maxStackSize = 1;
		this.setMaxDamage(enumToolMaterial2.getMaxUses());
		this.weaponDamage = 4 + enumToolMaterial2.getDamageVsEntity() * 2;
	}

	public float getStrVsBlock(ItemStack itemStack1, Block block2) {
		return block2.blockID == Block.web.blockID ? 15.0F : 1.5F;
	}

	public boolean hitEntity(ItemStack itemStack1, EntityLiving entityLiving2, EntityLiving entityLiving3) {
		itemStack1.damageItem(1, entityLiving3);
		return true;
	}

	public boolean onBlockDestroyed(ItemStack itemStack1, int i2, int i3, int i4, int i5, EntityLiving entityLiving6) {
		itemStack1.damageItem(2, entityLiving6);
		return true;
	}

	public int getDamageVsEntity(Entity entity1) {
		return this.weaponDamage;
	}

	public boolean isFull3D() {
		return true;
	}

	public boolean canHarvestBlock(Block block1) {
		return block1.blockID == Block.web.blockID;
	}
}
