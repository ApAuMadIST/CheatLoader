package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapStorage {
	private ISaveHandler field_28191_a;
	private Map loadedDataMap = new HashMap();
	private List loadedDataList = new ArrayList();
	private Map idCounts = new HashMap();

	public MapStorage(ISaveHandler iSaveHandler1) {
		this.field_28191_a = iSaveHandler1;
		this.loadIdCounts();
	}

	public MapDataBase loadData(Class class1, String string2) {
		MapDataBase mapDataBase3 = (MapDataBase)this.loadedDataMap.get(string2);
		if(mapDataBase3 != null) {
			return mapDataBase3;
		} else {
			if(this.field_28191_a != null) {
				try {
					File file4 = this.field_28191_a.func_28113_a(string2);
					if(file4 != null && file4.exists()) {
						try {
							mapDataBase3 = (MapDataBase)class1.getConstructor(new Class[]{String.class}).newInstance(new Object[]{string2});
						} catch (Exception exception7) {
							throw new RuntimeException("Failed to instantiate " + class1.toString(), exception7);
						}

						FileInputStream fileInputStream5 = new FileInputStream(file4);
						NBTTagCompound nBTTagCompound6 = CompressedStreamTools.func_1138_a(fileInputStream5);
						fileInputStream5.close();
						mapDataBase3.readFromNBT(nBTTagCompound6.getCompoundTag("data"));
					}
				} catch (Exception exception8) {
					exception8.printStackTrace();
				}
			}

			if(mapDataBase3 != null) {
				this.loadedDataMap.put(string2, mapDataBase3);
				this.loadedDataList.add(mapDataBase3);
			}

			return mapDataBase3;
		}
	}

	public void setData(String string1, MapDataBase mapDataBase2) {
		if(mapDataBase2 == null) {
			throw new RuntimeException("Can\'t set null data");
		} else {
			if(this.loadedDataMap.containsKey(string1)) {
				this.loadedDataList.remove(this.loadedDataMap.remove(string1));
			}

			this.loadedDataMap.put(string1, mapDataBase2);
			this.loadedDataList.add(mapDataBase2);
		}
	}

	public void saveAllData() {
		for(int i1 = 0; i1 < this.loadedDataList.size(); ++i1) {
			MapDataBase mapDataBase2 = (MapDataBase)this.loadedDataList.get(i1);
			if(mapDataBase2.isDirty()) {
				this.saveData(mapDataBase2);
				mapDataBase2.setDirty(false);
			}
		}

	}

	private void saveData(MapDataBase mapDataBase1) {
		if(this.field_28191_a != null) {
			try {
				File file2 = this.field_28191_a.func_28113_a(mapDataBase1.field_28168_a);
				if(file2 != null) {
					NBTTagCompound nBTTagCompound3 = new NBTTagCompound();
					mapDataBase1.writeToNBT(nBTTagCompound3);
					NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
					nBTTagCompound4.setCompoundTag("data", nBTTagCompound3);
					FileOutputStream fileOutputStream5 = new FileOutputStream(file2);
					CompressedStreamTools.writeGzippedCompoundToOutputStream(nBTTagCompound4, fileOutputStream5);
					fileOutputStream5.close();
				}
			} catch (Exception exception6) {
				exception6.printStackTrace();
			}

		}
	}

	private void loadIdCounts() {
		try {
			this.idCounts.clear();
			if(this.field_28191_a == null) {
				return;
			}

			File file1 = this.field_28191_a.func_28113_a("idcounts");
			if(file1 != null && file1.exists()) {
				DataInputStream dataInputStream2 = new DataInputStream(new FileInputStream(file1));
				NBTTagCompound nBTTagCompound3 = CompressedStreamTools.func_1141_a(dataInputStream2);
				dataInputStream2.close();
				Iterator iterator4 = nBTTagCompound3.func_28110_c().iterator();

				while(iterator4.hasNext()) {
					NBTBase nBTBase5 = (NBTBase)iterator4.next();
					if(nBTBase5 instanceof NBTTagShort) {
						NBTTagShort nBTTagShort6 = (NBTTagShort)nBTBase5;
						String string7 = nBTTagShort6.getKey();
						short s8 = nBTTagShort6.shortValue;
						this.idCounts.put(string7, s8);
					}
				}
			}
		} catch (Exception exception9) {
			exception9.printStackTrace();
		}

	}

	public int getUniqueDataId(String string1) {
		Short short2 = (Short)this.idCounts.get(string1);
		if(short2 == null) {
			short2 = (short)0;
		} else {
			short2 = (short)(short2.shortValue() + 1);
		}

		this.idCounts.put(string1, short2);
		if(this.field_28191_a == null) {
			return short2.shortValue();
		} else {
			try {
				File file3 = this.field_28191_a.func_28113_a("idcounts");
				if(file3 != null) {
					NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
					Iterator iterator5 = this.idCounts.keySet().iterator();

					while(iterator5.hasNext()) {
						String string6 = (String)iterator5.next();
						short s7 = ((Short)this.idCounts.get(string6)).shortValue();
						nBTTagCompound4.setShort(string6, s7);
					}

					DataOutputStream dataOutputStream9 = new DataOutputStream(new FileOutputStream(file3));
					CompressedStreamTools.func_1139_a(nBTTagCompound4, dataOutputStream9);
					dataOutputStream9.close();
				}
			} catch (Exception exception8) {
				exception8.printStackTrace();
			}

			return short2.shortValue();
		}
	}
}
