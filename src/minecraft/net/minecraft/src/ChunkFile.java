package net.minecraft.src;

import java.io.File;
import java.util.regex.Matcher;

class ChunkFile implements Comparable {
	private final File field_22326_a;
	private final int field_22325_b;
	private final int field_22327_c;

	public ChunkFile(File file1) {
		this.field_22326_a = file1;
		Matcher matcher2 = ChunkFilePattern.field_22189_a.matcher(file1.getName());
		if(matcher2.matches()) {
			this.field_22325_b = Integer.parseInt(matcher2.group(1), 36);
			this.field_22327_c = Integer.parseInt(matcher2.group(2), 36);
		} else {
			this.field_22325_b = 0;
			this.field_22327_c = 0;
		}

	}

	public int func_22322_a(ChunkFile chunkFile1) {
		int i2 = this.field_22325_b >> 5;
		int i3 = chunkFile1.field_22325_b >> 5;
		if(i2 == i3) {
			int i4 = this.field_22327_c >> 5;
			int i5 = chunkFile1.field_22327_c >> 5;
			return i4 - i5;
		} else {
			return i2 - i3;
		}
	}

	public File func_22324_a() {
		return this.field_22326_a;
	}

	public int func_22323_b() {
		return this.field_22325_b;
	}

	public int func_22321_c() {
		return this.field_22327_c;
	}

	public int compareTo(Object object1) {
		return this.func_22322_a((ChunkFile)object1);
	}
}
