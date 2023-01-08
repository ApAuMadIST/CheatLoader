package net.minecraft.src;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiScreen extends Gui {
	protected Minecraft mc;
	public int width;
	public int height;
	protected List controlList = new ArrayList();
	public boolean field_948_f = false;
	protected FontRenderer fontRenderer;
	public GuiParticle field_25091_h;
	private GuiButton selectedButton = null;

	public void drawScreen(int i1, int i2, float f3) {
		for(int i4 = 0; i4 < this.controlList.size(); ++i4) {
			GuiButton guiButton5 = (GuiButton)this.controlList.get(i4);
			guiButton5.drawButton(this.mc, i1, i2);
		}

	}

	protected void keyTyped(char c1, int i2) {
		if(i2 == 1) {
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
		}

	}

	public static String getClipboardString() {
		try {
			Transferable transferable0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);
			if(transferable0 != null && transferable0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				String string1 = (String)transferable0.getTransferData(DataFlavor.stringFlavor);
				return string1;
			}
		} catch (Exception exception2) {
		}

		return null;
	}

	protected void mouseClicked(int i1, int i2, int i3) {
		if(i3 == 0) {
			for(int i4 = 0; i4 < this.controlList.size(); ++i4) {
				GuiButton guiButton5 = (GuiButton)this.controlList.get(i4);
				if(guiButton5.mousePressed(this.mc, i1, i2)) {
					this.selectedButton = guiButton5;
					this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					this.actionPerformed(guiButton5);
				}
			}
		}

	}

	protected void mouseMovedOrUp(int i1, int i2, int i3) {
		if(this.selectedButton != null && i3 == 0) {
			this.selectedButton.mouseReleased(i1, i2);
			this.selectedButton = null;
		}

	}

	protected void actionPerformed(GuiButton guiButton1) {
	}

	public void setWorldAndResolution(Minecraft minecraft1, int i2, int i3) {
		this.field_25091_h = new GuiParticle(minecraft1);
		this.mc = minecraft1;
		this.fontRenderer = minecraft1.fontRenderer;
		this.width = i2;
		this.height = i3;
		this.controlList.clear();
		this.initGui();
	}

	public void initGui() {
	}

	public void handleInput() {
		while(Mouse.next()) {
			this.handleMouseInput();
		}

		while(Keyboard.next()) {
			this.handleKeyboardInput();
		}

	}

	public void handleMouseInput() {
		int i1;
		int i2;
		if(Mouse.getEventButtonState()) {
			i1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
			i2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
			this.mouseClicked(i1, i2, Mouse.getEventButton());
		} else {
			i1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
			i2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
			this.mouseMovedOrUp(i1, i2, Mouse.getEventButton());
		}

	}

	public void handleKeyboardInput() {
		if(Keyboard.getEventKeyState()) {
			if(Keyboard.getEventKey() == Keyboard.KEY_F11) {
				this.mc.toggleFullscreen();
				return;
			}

			this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
		}

	}

	public void updateScreen() {
	}

	public void onGuiClosed() {
	}

	public void drawDefaultBackground() {
		this.drawWorldBackground(0);
	}

	public void drawWorldBackground(int i1) {
		if(this.mc.theWorld != null) {
			this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
		} else {
			this.drawBackground(i1);
		}

	}

	public void drawBackground(int i1) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator tessellator2 = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/background.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f3 = 32.0F;
		tessellator2.startDrawingQuads();
		tessellator2.setColorOpaque_I(4210752);
		tessellator2.addVertexWithUV(0.0D, (double)this.height, 0.0D, 0.0D, (double)((float)this.height / f3 + (float)i1));
		tessellator2.addVertexWithUV((double)this.width, (double)this.height, 0.0D, (double)((float)this.width / f3), (double)((float)this.height / f3 + (float)i1));
		tessellator2.addVertexWithUV((double)this.width, 0.0D, 0.0D, (double)((float)this.width / f3), (double)(0 + i1));
		tessellator2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, (double)(0 + i1));
		tessellator2.draw();
	}

	public boolean doesGuiPauseGame() {
		return true;
	}

	public void deleteWorld(boolean z1, int i2) {
	}

	public void selectNextField() {
	}
}
