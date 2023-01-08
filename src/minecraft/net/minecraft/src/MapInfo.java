package net.minecraft.src;

public class MapInfo {
	public final EntityPlayer entityplayerObj;
	public int[] field_28119_b;
	public int[] field_28124_c;
	private int field_28122_e;
	private int field_28121_f;
	final MapData mapDataObj;

	public MapInfo(MapData mapData1, EntityPlayer entityPlayer2) {
		this.mapDataObj = mapData1;
		this.field_28119_b = new int[128];
		this.field_28124_c = new int[128];
		this.field_28122_e = 0;
		this.field_28121_f = 0;
		this.entityplayerObj = entityPlayer2;

		for(int i3 = 0; i3 < this.field_28119_b.length; ++i3) {
			this.field_28119_b[i3] = 0;
			this.field_28124_c[i3] = 127;
		}

	}
}
