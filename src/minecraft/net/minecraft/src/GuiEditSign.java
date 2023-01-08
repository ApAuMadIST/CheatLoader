package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiEditSign extends GuiScreen {
	protected String screenTitle = "Edit sign message:";
	private TileEntitySign entitySign;
	private int updateCounter;
	private int editLine = 0;
	private static final String allowedCharacters = ChatAllowedCharacters.allowedCharacters;

	public GuiEditSign(TileEntitySign tileEntitySign1) {
		this.entitySign = tileEntitySign1;
	}

	public void initGui() {
		this.controlList.clear();
		Keyboard.enableRepeatEvents(true);
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, "Done"));
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		if(this.mc.theWorld.multiplayerWorld) {
			this.mc.getSendQueue().addToSendQueue(new Packet130UpdateSign(this.entitySign.xCoord, this.entitySign.yCoord, this.entitySign.zCoord, this.entitySign.signText));
		}

	}

	public void updateScreen() {
		++this.updateCounter;
	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.enabled) {
			if(guiButton1.id == 0) {
				this.entitySign.onInventoryChanged();
				this.mc.displayGuiScreen((GuiScreen)null);
			}

		}
	}

	protected void keyTyped(char c1, int i2) {
		if(i2 == 200) {
			this.editLine = this.editLine - 1 & 3;
		}

		if(i2 == 208 || i2 == 28) {
			this.editLine = this.editLine + 1 & 3;
		}

		if(i2 == 14 && this.entitySign.signText[this.editLine].length() > 0) {
			this.entitySign.signText[this.editLine] = this.entitySign.signText[this.editLine].substring(0, this.entitySign.signText[this.editLine].length() - 1);
		}

		if(allowedCharacters.indexOf(c1) >= 0 && this.entitySign.signText[this.editLine].length() < 15) {
			this.entitySign.signText[this.editLine] = this.entitySign.signText[this.editLine] + c1;
		}

	}

	public void drawScreen(int i1, int i2, float f3) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 40, 0xFFFFFF);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(this.width / 2), 0.0F, 50.0F);
		float f4 = 93.75F;
		GL11.glScalef(-f4, -f4, -f4);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		Block block5 = this.entitySign.getBlockType();
		if(block5 == Block.signPost) {
			float f6 = (float)(this.entitySign.getBlockMetadata() * 360) / 16.0F;
			GL11.glRotatef(f6, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
		} else {
			int i8 = this.entitySign.getBlockMetadata();
			float f7 = 0.0F;
			if(i8 == 2) {
				f7 = 180.0F;
			}

			if(i8 == 4) {
				f7 = 90.0F;
			}

			if(i8 == 5) {
				f7 = -90.0F;
			}

			GL11.glRotatef(f7, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
		}

		if(this.updateCounter / 6 % 2 == 0) {
			this.entitySign.lineBeingEdited = this.editLine;
		}

		TileEntityRenderer.instance.renderTileEntityAt(this.entitySign, -0.5D, -0.75D, -0.5D, 0.0F);
		this.entitySign.lineBeingEdited = -1;
		GL11.glPopMatrix();
		super.drawScreen(i1, i2, f3);
	}
}
