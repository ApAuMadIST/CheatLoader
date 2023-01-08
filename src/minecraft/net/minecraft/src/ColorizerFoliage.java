package net.minecraft.src;

public class ColorizerFoliage {
	private static int[] foliageBuffer = new int[65536];

	public static void func_28152_a(int[] i0) {
		foliageBuffer = i0;
	}

	public static int getFoliageColor(double d0, double d2) {
		d2 *= d0;
		int i4 = (int)((1.0D - d0) * 255.0D);
		int i5 = (int)((1.0D - d2) * 255.0D);
		return foliageBuffer[i5 << 8 | i4];
	}

	public static int getFoliageColorPine() {
		return 6396257;
	}

	public static int getFoliageColorBirch() {
		return 8431445;
	}

	public static int func_31073_c() {
		return 4764952;
	}
}
