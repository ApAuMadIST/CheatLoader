package net.minecraft.src;

public abstract class MapDataBase {
	public final String field_28168_a;
	private boolean dirty;

	public MapDataBase(String string1) {
		this.field_28168_a = string1;
	}

	public abstract void readFromNBT(NBTTagCompound nBTTagCompound1);

	public abstract void writeToNBT(NBTTagCompound nBTTagCompound1);

	public void markDirty() {
		this.setDirty(true);
	}

	public void setDirty(boolean z1) {
		this.dirty = z1;
	}

	public boolean isDirty() {
		return this.dirty;
	}
}
