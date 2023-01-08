package net.minecraft.src;

class EnumJsonNodeTypeMappingHelper {
	static final int[] field_27341_a = new int[EnumJsonNodeType.values().length];

	static {
		try {
			field_27341_a[EnumJsonNodeType.ARRAY.ordinal()] = 1;
		} catch (NoSuchFieldError noSuchFieldError7) {
		}

		try {
			field_27341_a[EnumJsonNodeType.OBJECT.ordinal()] = 2;
		} catch (NoSuchFieldError noSuchFieldError6) {
		}

		try {
			field_27341_a[EnumJsonNodeType.STRING.ordinal()] = 3;
		} catch (NoSuchFieldError noSuchFieldError5) {
		}

		try {
			field_27341_a[EnumJsonNodeType.NUMBER.ordinal()] = 4;
		} catch (NoSuchFieldError noSuchFieldError4) {
		}

		try {
			field_27341_a[EnumJsonNodeType.FALSE.ordinal()] = 5;
		} catch (NoSuchFieldError noSuchFieldError3) {
		}

		try {
			field_27341_a[EnumJsonNodeType.TRUE.ordinal()] = 6;
		} catch (NoSuchFieldError noSuchFieldError2) {
		}

		try {
			field_27341_a[EnumJsonNodeType.NULL.ordinal()] = 7;
		} catch (NoSuchFieldError noSuchFieldError1) {
		}

	}
}
