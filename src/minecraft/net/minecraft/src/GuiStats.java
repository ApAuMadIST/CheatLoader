package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiStats extends GuiScreen {
	private static RenderItem field_27153_j = new RenderItem();
	protected GuiScreen field_27152_a;
	protected String field_27154_i = "Select world";
	private GuiSlotStatsGeneral field_27151_l;
	private GuiSlotStatsItem field_27150_m;
	private GuiSlotStatsBlock field_27157_n;
	private StatFileWriter field_27156_o;
	private GuiSlot field_27155_p = null;

	public GuiStats(GuiScreen guiScreen1, StatFileWriter statFileWriter2) {
		this.field_27152_a = guiScreen1;
		this.field_27156_o = statFileWriter2;
	}

	public void initGui() {
		this.field_27154_i = StatCollector.translateToLocal("gui.stats");
		this.field_27151_l = new GuiSlotStatsGeneral(this);
		this.field_27151_l.registerScrollButtons(this.controlList, 1, 1);
		this.field_27150_m = new GuiSlotStatsItem(this);
		this.field_27150_m.registerScrollButtons(this.controlList, 1, 1);
		this.field_27157_n = new GuiSlotStatsBlock(this);
		this.field_27157_n.registerScrollButtons(this.controlList, 1, 1);
		this.field_27155_p = this.field_27151_l;
		this.func_27130_k();
	}

	public void func_27130_k() {
		StringTranslate stringTranslate1 = StringTranslate.getInstance();
		this.controlList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, stringTranslate1.translateKey("gui.done")));
		this.controlList.add(new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, stringTranslate1.translateKey("stat.generalButton")));
		GuiButton guiButton2;
		this.controlList.add(guiButton2 = new GuiButton(2, this.width / 2 - 46, this.height - 52, 100, 20, stringTranslate1.translateKey("stat.blocksButton")));
		GuiButton guiButton3;
		this.controlList.add(guiButton3 = new GuiButton(3, this.width / 2 + 62, this.height - 52, 100, 20, stringTranslate1.translateKey("stat.itemsButton")));
		if(this.field_27157_n.getSize() == 0) {
			guiButton2.enabled = false;
		}

		if(this.field_27150_m.getSize() == 0) {
			guiButton3.enabled = false;
		}

	}

	protected void actionPerformed(GuiButton guiButton1) {
		if(guiButton1.enabled) {
			if(guiButton1.id == 0) {
				this.mc.displayGuiScreen(this.field_27152_a);
			} else if(guiButton1.id == 1) {
				this.field_27155_p = this.field_27151_l;
			} else if(guiButton1.id == 3) {
				this.field_27155_p = this.field_27150_m;
			} else if(guiButton1.id == 2) {
				this.field_27155_p = this.field_27157_n;
			} else {
				this.field_27155_p.actionPerformed(guiButton1);
			}

		}
	}

	public void drawScreen(int i1, int i2, float f3) {
		this.field_27155_p.drawScreen(i1, i2, f3);
		this.drawCenteredString(this.fontRenderer, this.field_27154_i, this.width / 2, 20, 0xFFFFFF);
		super.drawScreen(i1, i2, f3);
	}

	private void func_27138_c(int i1, int i2, int i3) {
		this.func_27147_a(i1 + 1, i2 + 1);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		field_27153_j.drawItemIntoGui(this.fontRenderer, this.mc.renderEngine, i3, 0, Item.itemsList[i3].getIconFromDamage(0), i1 + 2, i2 + 2);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	}

	private void func_27147_a(int i1, int i2) {
		this.func_27136_c(i1, i2, 0, 0);
	}

	private void func_27136_c(int i1, int i2, int i3, int i4) {
		int i5 = this.mc.renderEngine.getTexture("/gui/slot.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(i5);
		Tessellator tessellator10 = Tessellator.instance;
		tessellator10.startDrawingQuads();
		tessellator10.addVertexWithUV((double)(i1 + 0), (double)(i2 + 18), (double)this.zLevel, (double)((float)(i3 + 0) * 0.0078125F), (double)((float)(i4 + 18) * 0.0078125F));
		tessellator10.addVertexWithUV((double)(i1 + 18), (double)(i2 + 18), (double)this.zLevel, (double)((float)(i3 + 18) * 0.0078125F), (double)((float)(i4 + 18) * 0.0078125F));
		tessellator10.addVertexWithUV((double)(i1 + 18), (double)(i2 + 0), (double)this.zLevel, (double)((float)(i3 + 18) * 0.0078125F), (double)((float)(i4 + 0) * 0.0078125F));
		tessellator10.addVertexWithUV((double)(i1 + 0), (double)(i2 + 0), (double)this.zLevel, (double)((float)(i3 + 0) * 0.0078125F), (double)((float)(i4 + 0) * 0.0078125F));
		tessellator10.draw();
	}

	static Minecraft func_27141_a(GuiStats guiStats0) {
		return guiStats0.mc;
	}

	static FontRenderer func_27145_b(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static StatFileWriter func_27142_c(GuiStats guiStats0) {
		return guiStats0.field_27156_o;
	}

	static FontRenderer func_27140_d(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static FontRenderer func_27146_e(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static Minecraft func_27143_f(GuiStats guiStats0) {
		return guiStats0.mc;
	}

	static void func_27128_a(GuiStats guiStats0, int i1, int i2, int i3, int i4) {
		guiStats0.func_27136_c(i1, i2, i3, i4);
	}

	static Minecraft func_27149_g(GuiStats guiStats0) {
		return guiStats0.mc;
	}

	static FontRenderer func_27133_h(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static FontRenderer func_27137_i(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static FontRenderer func_27132_j(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static FontRenderer func_27134_k(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static FontRenderer func_27139_l(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static void func_27129_a(GuiStats guiStats0, int i1, int i2, int i3, int i4, int i5, int i6) {
		guiStats0.drawGradientRect(i1, i2, i3, i4, i5, i6);
	}

	static FontRenderer func_27144_m(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static FontRenderer func_27127_n(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static void func_27135_b(GuiStats guiStats0, int i1, int i2, int i3, int i4, int i5, int i6) {
		guiStats0.drawGradientRect(i1, i2, i3, i4, i5, i6);
	}

	static FontRenderer func_27131_o(GuiStats guiStats0) {
		return guiStats0.fontRenderer;
	}

	static void func_27148_a(GuiStats guiStats0, int i1, int i2, int i3) {
		guiStats0.func_27138_c(i1, i2, i3);
	}
}
