package net.minecraft.src;

public class MaterialPortal extends Material {
	public MaterialPortal(MapColor mapColor1) {
		super(mapColor1);
	}

	public boolean isSolid() {
		return false;
	}

	public boolean getCanBlockGrass() {
		return false;
	}

	public boolean getIsSolid() {
		return false;
	}
}
