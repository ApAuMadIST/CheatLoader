package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public class GuiMainMenu extends GuiScreen {
	private static final Random rand = new Random();
	private float updateCounter = 0.0F;
	private String splashText = "missingno";
	private GuiButton multiplayerButton;

	public GuiMainMenu() {
		try {
			ArrayList arrayList1 = new ArrayList();
			BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/title/splashes.txt"), Charset.forName("UTF-8")));
			String string3 = "";

			while((string3 = bufferedReader2.readLine()) != null) {
				string3 = string3.trim();
				if(string3.length() > 0) {
					arrayList1.add(string3);
				}
			}

			this.splashText = (String)arrayList1.get(rand.nextInt(arrayList1.size()));
		} catch (Exception exception4) {
		}

	}

	public void updateScreen() {
		++this.updateCounter;
	}

	protected void keyTyped(char c1, int i2) {
	}

	public void initGui() {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(new Date());
		if(calendar1.get(2) + 1 == 11 && calendar1.get(5) == 9) {
			this.splashText = "Happy birthday, ez!";
		} else if(calendar1.get(2) + 1 == 6 && calendar1.get(5) == 1) {
			this.splashText = "Happy birthday, Notch!";
		} else if(calendar1.get(2) + 1 == 12 && calendar1.get(5) == 24) {
			this.splashText = "Merry X-mas!";
		} else if(calendar1.get(2) + 1 == 1 && calendar1.get(5) == 1) {
			this.splashText = "Happy new year!";
		}

		StringTranslate stringTranslate2 = StringTranslate.getInstance();
		int i4 = this.height / 4 + 48;
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, i4, stringTranslate2.translateKey("menu.singleplayer")));
		this.controlList.add(this.multiplayerButton = new GuiButton(2, this.width / 2 - 100, i4 + 24, stringTranslate2.translateKey("menu.multiplayer")));
		this.controlList.add(new GuiButton(3, this.width / 2 - 100, i4 + 48, stringTranslate2.translateKey("menu.mods")));
		if(this.mc.hideQuitButton) {
			this.controlList.add(new GuiButton(0, this.width / 2 - 100, i4 + 72, stringTranslate2.translateKey("menu.options")));
		} else {
			this.controlList.add(new GuiButton(0, this.width / 2 - 100, i4 + 72 + 12, 98, 20, stringTranslate2.translateKey("menu.options")));
			this.controlList.add(new GuiButton(4, this.width / 2 + 2, i4 + 72 + 12, 98, 20, stringTranslate2.translateKey("menu.quit")));
		}

		if(this.mc.session == null) {
			this.multiplayerButton.enabled = false;
		}

	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}

		if(guiButton1.id == 1) {
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}

		if(guiButton1.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

		if(guiButton1.id == 3) {
			this.mc.displayGuiScreen(new GuiTexturePacks(this));
		}

		if(guiButton1.id == 4) {
			this.mc.shutdown();
		}

	}

	public void drawScreen(int i1, int i2, float f3) {
		this.drawDefaultBackground();
		Tessellator tessellator4 = Tessellator.instance;
		short s5 = 274;
		int i6 = this.width / 2 - s5 / 2;
		byte b7 = 30;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/title/mclogo.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(i6 + 0, b7 + 0, 0, 0, 155, 44);
		this.drawTexturedModalRect(i6 + 155, b7 + 0, 0, 45, 155, 44);
		tessellator4.setColorOpaque_I(0xFFFFFF);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
		GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
		float f8 = 1.8F - MathHelper.abs(MathHelper.sin((float)(System.currentTimeMillis() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
		f8 = f8 * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
		GL11.glScalef(f8, f8, f8);
		this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, 16776960);
		GL11.glPopMatrix();
		this.drawString(this.fontRenderer, "Minecraft Beta 1.7.3", 2, 2, 5263440);
		String string9 = "Copyright Mojang AB. Do not distribute.";
		this.drawString(this.fontRenderer, string9, this.width - this.fontRenderer.getStringWidth(string9) - 2, this.height - 10, 0xFFFFFF);
		super.drawScreen(i1, i2, f3);
	}
}
