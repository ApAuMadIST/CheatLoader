package net.minecraft.src;

public class ChunkBlockMap {
	private static byte[] field_26003_a = new byte[256];

	public static void func_26002_a(byte[] b0) {
		for(int i1 = 0; i1 < b0.length; ++i1) {
			b0[i1] = field_26003_a[b0[i1] & 255];
		}

	}

	static {
		try {
			for(int i0 = 0; i0 < 256; ++i0) {
				byte b1 = (byte)i0;
				if(b1 != 0 && Block.blocksList[b1 & 255] == null) {
					b1 = 0;
				}

				field_26003_a[i0] = b1;
			}
		} catch (Exception exception2) {
			exception2.printStackTrace();
		}

	}
}
