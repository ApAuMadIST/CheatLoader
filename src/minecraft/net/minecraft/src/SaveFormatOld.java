package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SaveFormatOld implements ISaveFormat {
	protected final File field_22180_a;

	public SaveFormatOld(File file1) {
		if(!file1.exists()) {
			file1.mkdirs();
		}

		this.field_22180_a = file1;
	}

	public String func_22178_a() {
		return "Old Format";
	}

	public List func_22176_b() {
		ArrayList arrayList1 = new ArrayList();

		for(int i2 = 0; i2 < 5; ++i2) {
			String string3 = "World" + (i2 + 1);
			WorldInfo worldInfo4 = this.func_22173_b(string3);
			if(worldInfo4 != null) {
				arrayList1.add(new SaveFormatComparator(string3, "", worldInfo4.getLastTimePlayed(), worldInfo4.getSizeOnDisk(), false));
			}
		}

		return arrayList1;
	}

	public void flushCache() {
	}

	public WorldInfo func_22173_b(String string1) {
		File file2 = new File(this.field_22180_a, string1);
		if(!file2.exists()) {
			return null;
		} else {
			File file3 = new File(file2, "level.dat");
			NBTTagCompound nBTTagCompound4;
			NBTTagCompound nBTTagCompound5;
			if(file3.exists()) {
				try {
					nBTTagCompound4 = CompressedStreamTools.func_1138_a(new FileInputStream(file3));
					nBTTagCompound5 = nBTTagCompound4.getCompoundTag("Data");
					return new WorldInfo(nBTTagCompound5);
				} catch (Exception exception7) {
					exception7.printStackTrace();
				}
			}

			file3 = new File(file2, "level.dat_old");
			if(file3.exists()) {
				try {
					nBTTagCompound4 = CompressedStreamTools.func_1138_a(new FileInputStream(file3));
					nBTTagCompound5 = nBTTagCompound4.getCompoundTag("Data");
					return new WorldInfo(nBTTagCompound5);
				} catch (Exception exception6) {
					exception6.printStackTrace();
				}
			}

			return null;
		}
	}

	public void func_22170_a(String string1, String string2) {
		File file3 = new File(this.field_22180_a, string1);
		if(file3.exists()) {
			File file4 = new File(file3, "level.dat");
			if(file4.exists()) {
				try {
					NBTTagCompound nBTTagCompound5 = CompressedStreamTools.func_1138_a(new FileInputStream(file4));
					NBTTagCompound nBTTagCompound6 = nBTTagCompound5.getCompoundTag("Data");
					nBTTagCompound6.setString("LevelName", string2);
					CompressedStreamTools.writeGzippedCompoundToOutputStream(nBTTagCompound5, new FileOutputStream(file4));
				} catch (Exception exception7) {
					exception7.printStackTrace();
				}
			}

		}
	}

	public void func_22172_c(String string1) {
		File file2 = new File(this.field_22180_a, string1);
		if(file2.exists()) {
			func_22179_a(file2.listFiles());
			file2.delete();
		}
	}

	protected static void func_22179_a(File[] file0) {
		for(int i1 = 0; i1 < file0.length; ++i1) {
			if(file0[i1].isDirectory()) {
				func_22179_a(file0[i1].listFiles());
			}

			file0[i1].delete();
		}

	}

	public ISaveHandler getSaveLoader(String string1, boolean z2) {
		return new SaveHandler(this.field_22180_a, string1, z2);
	}

	public boolean isOldMapFormat(String string1) {
		return false;
	}

	public boolean convertMapFormat(String string1, IProgressUpdate iProgressUpdate2) {
		return false;
	}
}
