package net.minecraft.src;

public class ColorizerGrass {
	private static int[] grassBuffer = new int[65536];

	public static void func_28181_a(int[] i0) {
		grassBuffer = i0;
	}

	public static int getGrassColor(double d0, double d2) {
		d2 *= d0;
		int i4 = (int)((1.0D - d0) * 255.0D);
		int i5 = (int)((1.0D - d2) * 255.0D);
		return grassBuffer[i5 << 8 | i4];
	}
}
