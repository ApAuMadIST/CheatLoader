package net.minecraft.src;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

final class J_PositionTrackingPushbackReader implements J_ThingWithPosition {
	private final PushbackReader field_27338_a;
	private int field_27337_b = 0;
	private int field_27340_c = 1;
	private boolean field_27339_d = false;

	public J_PositionTrackingPushbackReader(Reader reader1) {
		this.field_27338_a = new PushbackReader(reader1);
	}

	public void func_27334_a(char c1) throws IOException {
		--this.field_27337_b;
		if(this.field_27337_b < 0) {
			this.field_27337_b = 0;
		}

		this.field_27338_a.unread(c1);
	}

	public void func_27335_a(char[] c1) {
		this.field_27337_b -= c1.length;
		if(this.field_27337_b < 0) {
			this.field_27337_b = 0;
		}

	}

	public int func_27333_c() throws IOException {
		int i1 = this.field_27338_a.read();
		this.func_27332_a(i1);
		return i1;
	}

	public int func_27336_b(char[] c1) throws IOException {
		int i2 = this.field_27338_a.read(c1);
		char[] c3 = c1;
		int i4 = c1.length;

		for(int i5 = 0; i5 < i4; ++i5) {
			char c6 = c3[i5];
			this.func_27332_a(c6);
		}

		return i2;
	}

	private void func_27332_a(int i1) {
		if(13 == i1) {
			this.field_27337_b = 0;
			++this.field_27340_c;
			this.field_27339_d = true;
		} else {
			if(10 == i1 && !this.field_27339_d) {
				this.field_27337_b = 0;
				++this.field_27340_c;
			} else {
				++this.field_27337_b;
			}

			this.field_27339_d = false;
		}

	}

	public int func_27331_a() {
		return this.field_27337_b;
	}

	public int func_27330_b() {
		return this.field_27340_c;
	}
}
