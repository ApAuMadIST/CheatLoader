package net.minecraft.src;

public class StepSound {
	public final String field_1678_a;
	public final float field_1677_b;
	public final float field_1679_c;

	public StepSound(String string1, float f2, float f3) {
		this.field_1678_a = string1;
		this.field_1677_b = f2;
		this.field_1679_c = f3;
	}

	public float getVolume() {
		return this.field_1677_b;
	}

	public float getPitch() {
		return this.field_1679_c;
	}

	public String stepSoundDir() {
		return "step." + this.field_1678_a;
	}

	public String func_1145_d() {
		return "step." + this.field_1678_a;
	}
}
