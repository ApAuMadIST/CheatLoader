package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerSP extends PlayerController {
	private int field_1074_c = -1;
	private int field_1073_d = -1;
	private int field_1072_e = -1;
	private float curBlockDamage = 0.0F;
	private float prevBlockDamage = 0.0F;
	private float field_1069_h = 0.0F;
	private int blockHitWait = 0;

	public PlayerControllerSP(Minecraft minecraft1) {
		super(minecraft1);
	}

	public void flipPlayer(EntityPlayer entityPlayer1) {
		entityPlayer1.rotationYaw = -180.0F;
	}

	public boolean sendBlockRemoved(int i1, int i2, int i3, int i4) {
		int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
		int i6 = this.mc.theWorld.getBlockMetadata(i1, i2, i3);
		boolean z7 = super.sendBlockRemoved(i1, i2, i3, i4);
		ItemStack itemStack8 = this.mc.thePlayer.getCurrentEquippedItem();
		boolean z9 = this.mc.thePlayer.canHarvestBlock(Block.blocksList[i5]);
		if(itemStack8 != null) {
			itemStack8.onDestroyBlock(i5, i1, i2, i3, this.mc.thePlayer);
			if(itemStack8.stackSize == 0) {
				itemStack8.func_1097_a(this.mc.thePlayer);
				this.mc.thePlayer.destroyCurrentEquippedItem();
			}
		}

		if(z7 && z9) {
			Block.blocksList[i5].harvestBlock(this.mc.theWorld, this.mc.thePlayer, i1, i2, i3, i6);
		}

		return z7;
	}

	public void clickBlock(int i1, int i2, int i3, int i4) {
		this.mc.theWorld.onBlockHit(this.mc.thePlayer, i1, i2, i3, i4);
		int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
		if(i5 > 0 && this.curBlockDamage == 0.0F) {
			Block.blocksList[i5].onBlockClicked(this.mc.theWorld, i1, i2, i3, this.mc.thePlayer);
		}

		if(i5 > 0 && Block.blocksList[i5].blockStrength(this.mc.thePlayer) >= 1.0F) {
			this.sendBlockRemoved(i1, i2, i3, i4);
		}

	}

	public void resetBlockRemoving() {
		this.curBlockDamage = 0.0F;
		this.blockHitWait = 0;
	}

	public void sendBlockRemoving(int i1, int i2, int i3, int i4) {
		if(this.blockHitWait > 0) {
			--this.blockHitWait;
		} else {
			if(i1 == this.field_1074_c && i2 == this.field_1073_d && i3 == this.field_1072_e) {
				int i5 = this.mc.theWorld.getBlockId(i1, i2, i3);
				if(i5 == 0) {
					return;
				}

				Block block6 = Block.blocksList[i5];
				this.curBlockDamage += block6.blockStrength(this.mc.thePlayer);
				if(this.field_1069_h % 4.0F == 0.0F && block6 != null) {
					this.mc.sndManager.playSound(block6.stepSound.func_1145_d(), (float)i1 + 0.5F, (float)i2 + 0.5F, (float)i3 + 0.5F, (block6.stepSound.getVolume() + 1.0F) / 8.0F, block6.stepSound.getPitch() * 0.5F);
				}

				++this.field_1069_h;
				if(this.curBlockDamage >= 1.0F) {
					this.sendBlockRemoved(i1, i2, i3, i4);
					this.curBlockDamage = 0.0F;
					this.prevBlockDamage = 0.0F;
					this.field_1069_h = 0.0F;
					this.blockHitWait = 5;
				}
			} else {
				this.curBlockDamage = 0.0F;
				this.prevBlockDamage = 0.0F;
				this.field_1069_h = 0.0F;
				this.field_1074_c = i1;
				this.field_1073_d = i2;
				this.field_1072_e = i3;
			}

		}
	}

	public void setPartialTime(float f1) {
		if(this.curBlockDamage <= 0.0F) {
			this.mc.ingameGUI.damageGuiPartialTime = 0.0F;
			this.mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float f2 = this.prevBlockDamage + (this.curBlockDamage - this.prevBlockDamage) * f1;
			this.mc.ingameGUI.damageGuiPartialTime = f2;
			this.mc.renderGlobal.damagePartialTime = f2;
		}

	}

	public float getBlockReachDistance() {
		return 4.0F;
	}

	public void func_717_a(World world1) {
		super.func_717_a(world1);
	}

	public void updateController() {
		this.prevBlockDamage = this.curBlockDamage;
		this.mc.sndManager.playRandomMusicIfReady();
	}
}
