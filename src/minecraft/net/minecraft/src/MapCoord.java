package net.minecraft.src;

public class MapCoord {
	public byte field_28217_a;
	public byte field_28216_b;
	public byte field_28220_c;
	public byte field_28219_d;
	final MapData field_28218_e;

	public MapCoord(MapData mapData1, byte b2, byte b3, byte b4, byte b5) {
		this.field_28218_e = mapData1;
		this.field_28217_a = b2;
		this.field_28216_b = b3;
		this.field_28220_c = b4;
		this.field_28219_d = b5;
	}
}
