package net.minecraft.src;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

public class RenderList {
	private int field_1242_a;
	private int field_1241_b;
	private int field_1240_c;
	private float field_1239_d;
	private float field_1238_e;
	private float field_1237_f;
	private IntBuffer field_1236_g = GLAllocation.createDirectIntBuffer(65536);
	private boolean field_1235_h = false;
	private boolean field_1234_i = false;

	public void func_861_a(int i1, int i2, int i3, double d4, double d6, double d8) {
		this.field_1235_h = true;
		this.field_1236_g.clear();
		this.field_1242_a = i1;
		this.field_1241_b = i2;
		this.field_1240_c = i3;
		this.field_1239_d = (float)d4;
		this.field_1238_e = (float)d6;
		this.field_1237_f = (float)d8;
	}

	public boolean func_862_a(int i1, int i2, int i3) {
		return !this.field_1235_h ? false : i1 == this.field_1242_a && i2 == this.field_1241_b && i3 == this.field_1240_c;
	}

	public void func_858_a(int i1) {
		this.field_1236_g.put(i1);
		if(this.field_1236_g.remaining() == 0) {
			this.func_860_a();
		}

	}

	public void func_860_a() {
		if(this.field_1235_h) {
			if(!this.field_1234_i) {
				this.field_1236_g.flip();
				this.field_1234_i = true;
			}

			if(this.field_1236_g.remaining() > 0) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float)this.field_1242_a - this.field_1239_d, (float)this.field_1241_b - this.field_1238_e, (float)this.field_1240_c - this.field_1237_f);
				GL11.glCallLists(this.field_1236_g);
				GL11.glPopMatrix();
			}

		}
	}

	public void func_859_b() {
		this.field_1235_h = false;
		this.field_1234_i = false;
	}
}
