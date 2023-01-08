package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerController {
	protected final Minecraft mc;
	public boolean field_1064_b = false;

	public PlayerController(Minecraft minecraft1) {
		this.mc = minecraft1;
	}

	public void func_717_a(World world1) {
	}

	public void clickBlock(int i1, int i2, int i3, int i4) {
		this.mc.theWorld.onBlockHit(this.mc.thePlayer, i1, i2, i3, i4);
		this.sendBlockRemoved(i1, i2, i3, i4);
	}

	public boolean sendBlockRemoved(int i1, int i2, int i3, int i4) {
		World world5 = this.mc.theWorld;
		Block block6 = Block.blocksList[world5.getBlockId(i1, i2, i3)];
		world5.func_28106_e(2001, i1, i2, i3, block6.blockID + world5.getBlockMetadata(i1, i2, i3) * 256);
		int i7 = world5.getBlockMetadata(i1, i2, i3);
		boolean z8 = world5.setBlockWithNotify(i1, i2, i3, 0);
		if(block6 != null && z8) {
			block6.onBlockDestroyedByPlayer(world5, i1, i2, i3, i7);
		}

		return z8;
	}

	public void sendBlockRemoving(int i1, int i2, int i3, int i4) {
	}

	public void resetBlockRemoving() {
	}

	public void setPartialTime(float f1) {
	}

	public float getBlockReachDistance() {
		return 5.0F;
	}

	public boolean sendUseItem(EntityPlayer entityPlayer1, World world2, ItemStack itemStack3) {
		int i4 = itemStack3.stackSize;
		ItemStack itemStack5 = itemStack3.useItemRightClick(world2, entityPlayer1);
		if(itemStack5 != itemStack3 || itemStack5 != null && itemStack5.stackSize != i4) {
			entityPlayer1.inventory.mainInventory[entityPlayer1.inventory.currentItem] = itemStack5;
			if(itemStack5.stackSize == 0) {
				entityPlayer1.inventory.mainInventory[entityPlayer1.inventory.currentItem] = null;
			}

			return true;
		} else {
			return false;
		}
	}

	public void flipPlayer(EntityPlayer entityPlayer1) {
	}

	public void updateController() {
	}

	public boolean shouldDrawHUD() {
		return true;
	}

	public void func_6473_b(EntityPlayer entityPlayer1) {
	}

	public boolean sendPlaceBlock(EntityPlayer entityPlayer1, World world2, ItemStack itemStack3, int i4, int i5, int i6, int i7) {
		int i8 = world2.getBlockId(i4, i5, i6);
		return i8 > 0 && Block.blocksList[i8].blockActivated(world2, i4, i5, i6, entityPlayer1) ? true : (itemStack3 == null ? false : itemStack3.useItem(entityPlayer1, world2, i4, i5, i6, i7));
	}

	public EntityPlayer createPlayer(World world1) {
		return new EntityPlayerSP(this.mc, world1, this.mc.session, world1.worldProvider.worldType);
	}

	public void interactWithEntity(EntityPlayer entityPlayer1, Entity entity2) {
		entityPlayer1.useCurrentItemOnEntity(entity2);
	}

	public void attackEntity(EntityPlayer entityPlayer1, Entity entity2) {
		entityPlayer1.attackTargetEntityWithCurrentItem(entity2);
	}

	public ItemStack func_27174_a(int i1, int i2, int i3, boolean z4, EntityPlayer entityPlayer5) {
		return entityPlayer5.craftingInventory.func_27280_a(i2, i3, z4, entityPlayer5);
	}

	public void func_20086_a(int i1, EntityPlayer entityPlayer2) {
		entityPlayer2.craftingInventory.onCraftGuiClosed(entityPlayer2);
		entityPlayer2.craftingInventory = entityPlayer2.inventorySlots;
	}
}
