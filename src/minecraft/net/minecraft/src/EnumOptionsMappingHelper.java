package net.minecraft.src;

class EnumOptionsMappingHelper {
	static final int[] enumOptionsMappingHelperArray = new int[EnumOptions.values().length];

	static {
		try {
			enumOptionsMappingHelperArray[EnumOptions.INVERT_MOUSE.ordinal()] = 1;
		} catch (NoSuchFieldError noSuchFieldError5) {
		}

		try {
			enumOptionsMappingHelperArray[EnumOptions.VIEW_BOBBING.ordinal()] = 2;
		} catch (NoSuchFieldError noSuchFieldError4) {
		}

		try {
			enumOptionsMappingHelperArray[EnumOptions.ANAGLYPH.ordinal()] = 3;
		} catch (NoSuchFieldError noSuchFieldError3) {
		}

		try {
			enumOptionsMappingHelperArray[EnumOptions.ADVANCED_OPENGL.ordinal()] = 4;
		} catch (NoSuchFieldError noSuchFieldError2) {
		}

		try {
			enumOptionsMappingHelperArray[EnumOptions.AMBIENT_OCCLUSION.ordinal()] = 5;
		} catch (NoSuchFieldError noSuchFieldError1) {
		}

	}
}
