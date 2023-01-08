package net.minecraft.src;

public class NextTickListEntry implements Comparable {
	private static long nextTickEntryID = 0L;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int blockID;
	public long scheduledTime;
	private long tickEntryID = nextTickEntryID++;

	public NextTickListEntry(int i1, int i2, int i3, int i4) {
		this.xCoord = i1;
		this.yCoord = i2;
		this.zCoord = i3;
		this.blockID = i4;
	}

	public boolean equals(Object object1) {
		if(!(object1 instanceof NextTickListEntry)) {
			return false;
		} else {
			NextTickListEntry nextTickListEntry2 = (NextTickListEntry)object1;
			return this.xCoord == nextTickListEntry2.xCoord && this.yCoord == nextTickListEntry2.yCoord && this.zCoord == nextTickListEntry2.zCoord && this.blockID == nextTickListEntry2.blockID;
		}
	}

	public int hashCode() {
		return (this.xCoord * 128 * 1024 + this.zCoord * 128 + this.yCoord) * 256 + this.blockID;
	}

	public NextTickListEntry setScheduledTime(long j1) {
		this.scheduledTime = j1;
		return this;
	}

	public int comparer(NextTickListEntry nextTickListEntry1) {
		return this.scheduledTime < nextTickListEntry1.scheduledTime ? -1 : (this.scheduledTime > nextTickListEntry1.scheduledTime ? 1 : (this.tickEntryID < nextTickListEntry1.tickEntryID ? -1 : (this.tickEntryID > nextTickListEntry1.tickEntryID ? 1 : 0)));
	}

	public int compareTo(Object object1) {
		return this.comparer((NextTickListEntry)object1);
	}
}
