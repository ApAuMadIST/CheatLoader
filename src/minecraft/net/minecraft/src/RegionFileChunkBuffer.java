package net.minecraft.src;

import java.io.ByteArrayOutputStream;

class RegionFileChunkBuffer extends ByteArrayOutputStream {
	private int field_22283_b;
	private int field_22285_c;
	final RegionFile field_22284_a;

	public RegionFileChunkBuffer(RegionFile regionFile1, int i2, int i3) {
		super(8096);
		this.field_22284_a = regionFile1;
		this.field_22283_b = i2;
		this.field_22285_c = i3;
	}

	public void close() {
		this.field_22284_a.write(this.field_22283_b, this.field_22285_c, this.buf, this.count);
	}
}
