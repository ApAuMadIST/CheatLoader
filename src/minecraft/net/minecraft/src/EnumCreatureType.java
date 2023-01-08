package net.minecraft.src;

public enum EnumCreatureType {
	monster(IMob.class, 70, Material.air, false),
	creature(EntityAnimal.class, 15, Material.air, true),
	waterCreature(EntityWaterMob.class, 5, Material.water, true);

	private final Class creatureClass;
	private final int maxNumberOfCreature;
	private final Material creatureMaterial;
	private final boolean isPeacefulCreature;

	private EnumCreatureType(Class class3, int i4, Material material5, boolean z6) {
		this.creatureClass = class3;
		this.maxNumberOfCreature = i4;
		this.creatureMaterial = material5;
		this.isPeacefulCreature = z6;
	}

	public Class getCreatureClass() {
		return this.creatureClass;
	}

	public int getMaxNumberOfCreature() {
		return this.maxNumberOfCreature;
	}

	public Material getCreatureMaterial() {
		return this.creatureMaterial;
	}

	public boolean getPeacefulCreature() {
		return this.isPeacefulCreature;
	}
}
