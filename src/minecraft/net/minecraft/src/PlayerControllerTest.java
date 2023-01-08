package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerTest extends PlayerController {
	public PlayerControllerTest(Minecraft minecraft1) {
		super(minecraft1);
		this.field_1064_b = true;
	}

	public void func_6473_b(EntityPlayer entityPlayer1) {
		for(int i2 = 0; i2 < 9; ++i2) {
			if(entityPlayer1.inventory.mainInventory[i2] == null) {
				this.mc.thePlayer.inventory.mainInventory[i2] = new ItemStack((Block)Session.registeredBlocksList.get(i2));
			} else {
				this.mc.thePlayer.inventory.mainInventory[i2].stackSize = 1;
			}
		}

	}

	public boolean shouldDrawHUD() {
		return false;
	}

	public void func_717_a(World world1) {
		super.func_717_a(world1);
	}

	public void updateController() {
	}
}
