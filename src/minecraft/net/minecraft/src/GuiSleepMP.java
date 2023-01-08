package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class GuiSleepMP extends GuiChat {
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, stringTranslate1.translateKey("multiplayer.stopSleeping")));
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void keyTyped(char c1, int i2) {
		if(i2 == 1) {
			this.func_22115_j();
		} else if(i2 == 28) {
			String string3 = this.message.trim();
			if(string3.length() > 0) {
				this.mc.thePlayer.sendChatMessage(this.message.trim());
			}

			this.message = "";
		} else {
			super.keyTyped(c1, i2);
		}

	}

	public void drawScreen(int i1, int i2, float f3) {
		super.drawScreen(i1, i2, f3);
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.id == 1) {
			this.func_22115_j();
		} else {
			super.actionPerformed(guiButton1);
		}

	}

	private void func_22115_j() {
		if(this.mc.thePlayer instanceof EntityClientPlayerMP) {
			NetClientHandler netClientHandler1 = ((EntityClientPlayerMP)this.mc.thePlayer).sendQueue;
			netClientHandler1.addToSendQueue(new Packet19EntityAction(this.mc.thePlayer, 3));
		}

	}
}
