package net.minecraft.src;

public final class ItemStack {
	public int stackSize;
	public int animationsToGo;
	public int itemID;
	private int itemDamage;

	public ItemStack(Block block1) {
		this((Block)block1, 1);
	}

	public ItemStack(Block block1, int i2) {
		this(block1.blockID, i2, 0);
	}

	public ItemStack(Block block1, int i2, int i3) {
		this(block1.blockID, i2, i3);
	}

	public ItemStack(Item item1) {
		this(item1.shiftedIndex, 1, 0);
	}

	public ItemStack(Item item1, int i2) {
		this(item1.shiftedIndex, i2, 0);
	}

	public ItemStack(Item item1, int i2, int i3) {
		this(item1.shiftedIndex, i2, i3);
	}

	public ItemStack(int i1, int i2, int i3) {
		this.stackSize = 0;
		this.itemID = i1;
		this.stackSize = i2;
		this.itemDamage = i3;
	}

	public ItemStack(NBTTagCompound nBTTagCompound1) {
		this.stackSize = 0;
		this.readFromNBT(nBTTagCompound1);
	}

	public ItemStack splitStack(int i1) {
		this.stackSize -= i1;
		return new ItemStack(this.itemID, i1, this.itemDamage);
	}

	public Item getItem() {
		return Item.itemsList[this.itemID];
	}

	public int getIconIndex() {
		return this.getItem().getIconIndex(this);
	}

	public boolean useItem(EntityPlayer entityPlayer1, World world2, int i3, int i4, int i5, int i6) {
		boolean z7 = this.getItem().onItemUse(this, entityPlayer1, world2, i3, i4, i5, i6);
		if(z7) {
			entityPlayer1.addStat(StatList.field_25172_A[this.itemID], 1);
		}

		return z7;
	}

	public float getStrVsBlock(Block block1) {
		return this.getItem().getStrVsBlock(this, block1);
	}

	public ItemStack useItemRightClick(World world1, EntityPlayer entityPlayer2) {
		return this.getItem().onItemRightClick(this, world1, entityPlayer2);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nBTTagCompound1) {
		nBTTagCompound1.setShort("id", (short)this.itemID);
		nBTTagCompound1.setByte("Count", (byte)this.stackSize);
		nBTTagCompound1.setShort("Damage", (short)this.itemDamage);
		return nBTTagCompound1;
	}

	public void readFromNBT(NBTTagCompound nBTTagCompound1) {
		this.itemID = nBTTagCompound1.getShort("id");
		this.stackSize = nBTTagCompound1.getByte("Count");
		this.itemDamage = nBTTagCompound1.getShort("Damage");
	}

	public int getMaxStackSize() {
		return this.getItem().getItemStackLimit();
	}

	public boolean isStackable() {
		return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
	}

	public boolean isItemStackDamageable() {
		return Item.itemsList[this.itemID].getMaxDamage() > 0;
	}

	public boolean getHasSubtypes() {
		return Item.itemsList[this.itemID].getHasSubtypes();
	}

	public boolean isItemDamaged() {
		return this.isItemStackDamageable() && this.itemDamage > 0;
	}

	public int getItemDamageForDisplay() {
		return this.itemDamage;
	}

	public int getItemDamage() {
		return this.itemDamage;
	}

	public void setItemDamage(int i1) {
		this.itemDamage = i1;
	}

	public int getMaxDamage() {
		return Item.itemsList[this.itemID].getMaxDamage();
	}

	public void damageItem(int i1, Entity entity2) {
		if(this.isItemStackDamageable()) {
			this.itemDamage += i1;
			if(this.itemDamage > this.getMaxDamage()) {
				if(entity2 instanceof EntityPlayer) {
					((EntityPlayer)entity2).addStat(StatList.field_25170_B[this.itemID], 1);
				}

				--this.stackSize;
				if(this.stackSize < 0) {
					this.stackSize = 0;
				}

				this.itemDamage = 0;
			}

		}
	}

	public void hitEntity(EntityLiving entityLiving1, EntityPlayer entityPlayer2) {
		boolean z3 = Item.itemsList[this.itemID].hitEntity(this, entityLiving1, entityPlayer2);
		if(z3) {
			entityPlayer2.addStat(StatList.field_25172_A[this.itemID], 1);
		}

	}

	public void onDestroyBlock(int i1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		boolean z6 = Item.itemsList[this.itemID].onBlockDestroyed(this, i1, i2, i3, i4, entityPlayer5);
		if(z6) {
			entityPlayer5.addStat(StatList.field_25172_A[this.itemID], 1);
		}

	}

	public int getDamageVsEntity(Entity entity1) {
		return Item.itemsList[this.itemID].getDamageVsEntity(entity1);
	}

	public boolean canHarvestBlock(Block block1) {
		return Item.itemsList[this.itemID].canHarvestBlock(block1);
	}

	public void func_1097_a(EntityPlayer entityPlayer1) {
	}

	public void useItemOnEntity(EntityLiving entityLiving1) {
		Item.itemsList[this.itemID].saddleEntity(this, entityLiving1);
	}

	public ItemStack copy() {
		return new ItemStack(this.itemID, this.stackSize, this.itemDamage);
	}

	public static boolean areItemStacksEqual(ItemStack itemStack0, ItemStack itemStack1) {
		return itemStack0 == null && itemStack1 == null ? true : (itemStack0 != null && itemStack1 != null ? itemStack0.isItemStackEqual(itemStack1) : false);
	}

	private boolean isItemStackEqual(ItemStack itemStack1) {
		return this.stackSize != itemStack1.stackSize ? false : (this.itemID != itemStack1.itemID ? false : this.itemDamage == itemStack1.itemDamage);
	}

	public boolean isItemEqual(ItemStack itemStack1) {
		return this.itemID == itemStack1.itemID && this.itemDamage == itemStack1.itemDamage;
	}

	public String getItemName() {
		return Item.itemsList[this.itemID].getItemNameIS(this);
	}

	public static ItemStack copyItemStack(ItemStack itemStack0) {
		return itemStack0 == null ? null : itemStack0.copy();
	}

	public String toString() {
		return this.stackSize + "x" + Item.itemsList[this.itemID].getItemName() + "@" + this.itemDamage;
	}

	public void updateAnimation(World world1, Entity entity2, int i3, boolean z4) {
		if(this.animationsToGo > 0) {
			--this.animationsToGo;
		}

		Item.itemsList[this.itemID].onUpdate(this, world1, entity2, i3, z4);
	}

	public void onCrafting(World world1, EntityPlayer entityPlayer2) {
		entityPlayer2.addStat(StatList.field_25158_z[this.itemID], this.stackSize);
		Item.itemsList[this.itemID].onCreated(this, world1, entityPlayer2);
	}

	public boolean isStackEqual(ItemStack itemStack1) {
		return this.itemID == itemStack1.itemID && this.stackSize == itemStack1.stackSize && this.itemDamage == itemStack1.itemDamage;
	}
}
