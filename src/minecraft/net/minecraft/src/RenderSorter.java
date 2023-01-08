package net.minecraft.src;

import java.util.Comparator;

public class RenderSorter implements Comparator {
	private EntityLiving baseEntity;

	public RenderSorter(EntityLiving entityLiving1) {
		this.baseEntity = entityLiving1;
	}

	public int doCompare(WorldRenderer worldRenderer1, WorldRenderer worldRenderer2) {
		boolean z3 = worldRenderer1.isInFrustum;
		boolean z4 = worldRenderer2.isInFrustum;
		if(z3 && !z4) {
			return 1;
		} else if(z4 && !z3) {
			return -1;
		} else {
			double d5 = (double)worldRenderer1.distanceToEntitySquared(this.baseEntity);
			double d7 = (double)worldRenderer2.distanceToEntitySquared(this.baseEntity);
			return d5 < d7 ? 1 : (d5 > d7 ? -1 : (worldRenderer1.chunkIndex < worldRenderer2.chunkIndex ? 1 : -1));
		}
	}

	public int compare(Object object1, Object object2) {
		return this.doCompare((WorldRenderer)object1, (WorldRenderer)object2);
	}
}
