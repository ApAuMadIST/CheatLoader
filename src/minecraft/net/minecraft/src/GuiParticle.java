package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class GuiParticle extends Gui {
	private List field_25090_a = new ArrayList();
	private Minecraft field_25089_b;

	public GuiParticle(Minecraft minecraft1) {
		this.field_25089_b = minecraft1;
	}

	public void func_25088_a() {
		for(int i1 = 0; i1 < this.field_25090_a.size(); ++i1) {
			Particle particle2 = (Particle)this.field_25090_a.get(i1);
			particle2.func_25127_a();
			particle2.func_25125_a(this);
			if(particle2.field_25139_h) {
				this.field_25090_a.remove(i1--);
			}
		}

	}

	public void func_25087_a(float f1) {
		this.field_25089_b.renderEngine.bindTexture(this.field_25089_b.renderEngine.getTexture("/gui/particles.png"));

		for(int i2 = 0; i2 < this.field_25090_a.size(); ++i2) {
			Particle particle3 = (Particle)this.field_25090_a.get(i2);
			int i4 = (int)(particle3.field_25144_c + (particle3.field_25146_a - particle3.field_25144_c) * (double)f1 - 4.0D);
			int i5 = (int)(particle3.field_25143_d + (particle3.field_25145_b - particle3.field_25143_d) * (double)f1 - 4.0D);
			float f6 = (float)(particle3.field_25129_r + (particle3.field_25133_n - particle3.field_25129_r) * (double)f1);
			float f7 = (float)(particle3.field_25132_o + (particle3.field_25136_k - particle3.field_25132_o) * (double)f1);
			float f8 = (float)(particle3.field_25131_p + (particle3.field_25135_l - particle3.field_25131_p) * (double)f1);
			float f9 = (float)(particle3.field_25130_q + (particle3.field_25134_m - particle3.field_25130_q) * (double)f1);
			GL11.glColor4f(f7, f8, f9, f6);
			this.drawTexturedModalRect(i4, i5, 40, 0, 8, 8);
		}

	}
}
