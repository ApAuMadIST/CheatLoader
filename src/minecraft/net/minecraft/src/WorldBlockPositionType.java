package net.minecraft.src;

class WorldBlockPositionType {
	int field_1202_a;
	int field_1201_b;
	int field_1207_c;
	int field_1206_d;
	int field_1205_e;
	int field_1204_f;
	final WorldClient field_1203_g;

	public WorldBlockPositionType(WorldClient worldClient1, int i2, int i3, int i4, int i5, int i6) {
		this.field_1203_g = worldClient1;
		this.field_1202_a = i2;
		this.field_1201_b = i3;
		this.field_1207_c = i4;
		this.field_1206_d = 80;
		this.field_1205_e = i5;
		this.field_1204_f = i6;
	}
}
