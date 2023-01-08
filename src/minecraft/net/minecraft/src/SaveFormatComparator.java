package net.minecraft.src;

public class SaveFormatComparator implements Comparable {
	private final String fileName;
	private final String displayName;
	private final long field_22169_c;
	private final long field_22168_d;
	private final boolean field_22167_e;

	public SaveFormatComparator(String string1, String string2, long j3, long j5, boolean z7) {
		this.fileName = string1;
		this.displayName = string2;
		this.field_22169_c = j3;
		this.field_22168_d = j5;
		this.field_22167_e = z7;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public long func_22159_c() {
		return this.field_22168_d;
	}

	public boolean func_22161_d() {
		return this.field_22167_e;
	}

	public long func_22163_e() {
		return this.field_22169_c;
	}

	public int func_22160_a(SaveFormatComparator saveFormatComparator1) {
		return this.field_22169_c < saveFormatComparator1.field_22169_c ? 1 : (this.field_22169_c > saveFormatComparator1.field_22169_c ? -1 : this.fileName.compareTo(saveFormatComparator1.fileName));
	}

	public int compareTo(Object object1) {
		return this.func_22160_a((SaveFormatComparator)object1);
	}
}
