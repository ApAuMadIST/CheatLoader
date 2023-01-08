package net.minecraft.src;

public class WatchableObject {
	private final int objectType;
	private final int dataValueId;
	private Object watchedObject;
	private boolean isWatching;

	public WatchableObject(int i1, int i2, Object object3) {
		this.dataValueId = i2;
		this.watchedObject = object3;
		this.objectType = i1;
		this.isWatching = true;
	}

	public int getDataValueId() {
		return this.dataValueId;
	}

	public void setObject(Object object1) {
		this.watchedObject = object1;
	}

	public Object getObject() {
		return this.watchedObject;
	}

	public int getObjectType() {
		return this.objectType;
	}

	public void setWatching(boolean z1) {
		this.isWatching = z1;
	}
}
