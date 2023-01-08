package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataWatcher {
	private static final HashMap dataTypes = new HashMap();
	private final Map watchedObjects = new HashMap();
	private boolean objectChanged;

	public void addObject(int i1, Object object2) {
		Integer integer3 = (Integer)dataTypes.get(object2.getClass());
		if(integer3 == null) {
			throw new IllegalArgumentException("Unknown data type: " + object2.getClass());
		} else if(i1 > 31) {
			throw new IllegalArgumentException("Data value id is too big with " + i1 + "! (Max is " + 31 + ")");
		} else if(this.watchedObjects.containsKey(i1)) {
			throw new IllegalArgumentException("Duplicate id value for " + i1 + "!");
		} else {
			WatchableObject watchableObject4 = new WatchableObject(integer3.intValue(), i1, object2);
			this.watchedObjects.put(i1, watchableObject4);
		}
	}

	public byte getWatchableObjectByte(int i1) {
		return ((Byte)((WatchableObject)this.watchedObjects.get(i1)).getObject()).byteValue();
	}

	public int getWatchableObjectInt(int i1) {
		return ((Integer)((WatchableObject)this.watchedObjects.get(i1)).getObject()).intValue();
	}

	public String getWatchableObjectString(int i1) {
		return (String)((WatchableObject)this.watchedObjects.get(i1)).getObject();
	}

	public void updateObject(int i1, Object object2) {
		WatchableObject watchableObject3 = (WatchableObject)this.watchedObjects.get(i1);
		if(!object2.equals(watchableObject3.getObject())) {
			watchableObject3.setObject(object2);
			watchableObject3.setWatching(true);
			this.objectChanged = true;
		}

	}

	public static void writeObjectsInListToStream(List list0, DataOutputStream dataOutputStream1) throws IOException {
		if(list0 != null) {
			Iterator iterator2 = list0.iterator();

			while(iterator2.hasNext()) {
				WatchableObject watchableObject3 = (WatchableObject)iterator2.next();
				writeWatchableObject(dataOutputStream1, watchableObject3);
			}
		}

		dataOutputStream1.writeByte(127);
	}

	public void writeWatchableObjects(DataOutputStream dataOutputStream1) throws IOException {
		Iterator iterator2 = this.watchedObjects.values().iterator();

		while(iterator2.hasNext()) {
			WatchableObject watchableObject3 = (WatchableObject)iterator2.next();
			writeWatchableObject(dataOutputStream1, watchableObject3);
		}

		dataOutputStream1.writeByte(127);
	}

	private static void writeWatchableObject(DataOutputStream dataOutputStream0, WatchableObject watchableObject1) throws IOException {
		int i2 = (watchableObject1.getObjectType() << 5 | watchableObject1.getDataValueId() & 31) & 255;
		dataOutputStream0.writeByte(i2);
		switch(watchableObject1.getObjectType()) {
		case 0:
			dataOutputStream0.writeByte(((Byte)watchableObject1.getObject()).byteValue());
			break;
		case 1:
			dataOutputStream0.writeShort(((Short)watchableObject1.getObject()).shortValue());
			break;
		case 2:
			dataOutputStream0.writeInt(((Integer)watchableObject1.getObject()).intValue());
			break;
		case 3:
			dataOutputStream0.writeFloat(((Float)watchableObject1.getObject()).floatValue());
			break;
		case 4:
			Packet.writeString((String)watchableObject1.getObject(), dataOutputStream0);
			break;
		case 5:
			ItemStack itemStack4 = (ItemStack)watchableObject1.getObject();
			dataOutputStream0.writeShort(itemStack4.getItem().shiftedIndex);
			dataOutputStream0.writeByte(itemStack4.stackSize);
			dataOutputStream0.writeShort(itemStack4.getItemDamage());
			break;
		case 6:
			ChunkCoordinates chunkCoordinates3 = (ChunkCoordinates)watchableObject1.getObject();
			dataOutputStream0.writeInt(chunkCoordinates3.x);
			dataOutputStream0.writeInt(chunkCoordinates3.y);
			dataOutputStream0.writeInt(chunkCoordinates3.z);
		}

	}

	public static List readWatchableObjects(DataInputStream dataInputStream0) throws IOException {
		ArrayList arrayList1 = null;

		for(byte b2 = dataInputStream0.readByte(); b2 != 127; b2 = dataInputStream0.readByte()) {
			if(arrayList1 == null) {
				arrayList1 = new ArrayList();
			}

			int i3 = (b2 & 224) >> 5;
			int i4 = b2 & 31;
			WatchableObject watchableObject5 = null;
			switch(i3) {
			case 0:
				watchableObject5 = new WatchableObject(i3, i4, dataInputStream0.readByte());
				break;
			case 1:
				watchableObject5 = new WatchableObject(i3, i4, dataInputStream0.readShort());
				break;
			case 2:
				watchableObject5 = new WatchableObject(i3, i4, dataInputStream0.readInt());
				break;
			case 3:
				watchableObject5 = new WatchableObject(i3, i4, dataInputStream0.readFloat());
				break;
			case 4:
				watchableObject5 = new WatchableObject(i3, i4, Packet.readString(dataInputStream0, 64));
				break;
			case 5:
				short s9 = dataInputStream0.readShort();
				byte b10 = dataInputStream0.readByte();
				short s11 = dataInputStream0.readShort();
				watchableObject5 = new WatchableObject(i3, i4, new ItemStack(s9, b10, s11));
				break;
			case 6:
				int i6 = dataInputStream0.readInt();
				int i7 = dataInputStream0.readInt();
				int i8 = dataInputStream0.readInt();
				watchableObject5 = new WatchableObject(i3, i4, new ChunkCoordinates(i6, i7, i8));
			}

			arrayList1.add(watchableObject5);
		}

		return arrayList1;
	}

	public void updateWatchedObjectsFromList(List list1) {
		Iterator iterator2 = list1.iterator();

		while(iterator2.hasNext()) {
			WatchableObject watchableObject3 = (WatchableObject)iterator2.next();
			WatchableObject watchableObject4 = (WatchableObject)this.watchedObjects.get(watchableObject3.getDataValueId());
			if(watchableObject4 != null) {
				watchableObject4.setObject(watchableObject3.getObject());
			}
		}

	}

	static {
		dataTypes.put(Byte.class, 0);
		dataTypes.put(Short.class, 1);
		dataTypes.put(Integer.class, 2);
		dataTypes.put(Float.class, 3);
		dataTypes.put(String.class, 4);
		dataTypes.put(ItemStack.class, 5);
		dataTypes.put(ChunkCoordinates.class, 6);
	}
}
