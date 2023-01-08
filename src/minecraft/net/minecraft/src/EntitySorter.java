package net.minecraft.src;

import java.util.Comparator;

public class EntitySorter implements Comparator {
	private double field_30008_a;
	private double field_30007_b;
	private double field_30009_c;

	public EntitySorter(Entity entity1) {
		this.field_30008_a = -entity1.posX;
		this.field_30007_b = -entity1.posY;
		this.field_30009_c = -entity1.posZ;
	}

	public int sortByDistanceToEntity(WorldRenderer worldRenderer1, WorldRenderer worldRenderer2) {
		double d3 = (double)worldRenderer1.posXPlus + this.field_30008_a;
		double d5 = (double)worldRenderer1.posYPlus + this.field_30007_b;
		double d7 = (double)worldRenderer1.posZPlus + this.field_30009_c;
		double d9 = (double)worldRenderer2.posXPlus + this.field_30008_a;
		double d11 = (double)worldRenderer2.posYPlus + this.field_30007_b;
		double d13 = (double)worldRenderer2.posZPlus + this.field_30009_c;
		return (int)((d3 * d3 + d5 * d5 + d7 * d7 - (d9 * d9 + d11 * d11 + d13 * d13)) * 1024.0D);
	}

	public int compare(Object object1, Object object2) {
		return this.sortByDistanceToEntity((WorldRenderer)object1, (WorldRenderer)object2);
	}
}
