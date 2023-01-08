package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

public class RegionFile {
	private static final byte[] emptySector = new byte[4096];
	private final File fileName;
	private RandomAccessFile dataFile;
	private final int[] offsets = new int[1024];
	private final int[] field_22217_e = new int[1024];
	private ArrayList sectorFree;
	private int sizeDelta;
	private long field_22214_h = 0L;

	public RegionFile(File file1) {
		this.fileName = file1;
		this.debugln("REGION LOAD " + this.fileName);
		this.sizeDelta = 0;

		try {
			if(file1.exists()) {
				this.field_22214_h = file1.lastModified();
			}

			this.dataFile = new RandomAccessFile(file1, "rw");
			int i2;
			if(this.dataFile.length() < 4096L) {
				for(i2 = 0; i2 < 1024; ++i2) {
					this.dataFile.writeInt(0);
				}

				for(i2 = 0; i2 < 1024; ++i2) {
					this.dataFile.writeInt(0);
				}

				this.sizeDelta += 8192;
			}

			if((this.dataFile.length() & 4095L) != 0L) {
				for(i2 = 0; (long)i2 < (this.dataFile.length() & 4095L); ++i2) {
					this.dataFile.write(0);
				}
			}

			i2 = (int)this.dataFile.length() / 4096;
			this.sectorFree = new ArrayList(i2);

			int i3;
			for(i3 = 0; i3 < i2; ++i3) {
				this.sectorFree.add(true);
			}

			this.sectorFree.set(0, false);
			this.sectorFree.set(1, false);
			this.dataFile.seek(0L);

			int i4;
			for(i3 = 0; i3 < 1024; ++i3) {
				i4 = this.dataFile.readInt();
				this.offsets[i3] = i4;
				if(i4 != 0 && (i4 >> 8) + (i4 & 255) <= this.sectorFree.size()) {
					for(int i5 = 0; i5 < (i4 & 255); ++i5) {
						this.sectorFree.set((i4 >> 8) + i5, false);
					}
				}
			}

			for(i3 = 0; i3 < 1024; ++i3) {
				i4 = this.dataFile.readInt();
				this.field_22217_e[i3] = i4;
			}
		} catch (IOException iOException6) {
			iOException6.printStackTrace();
		}

	}

	public synchronized int func_22209_a() {
		int i1 = this.sizeDelta;
		this.sizeDelta = 0;
		return i1;
	}

	private void func_22211_a(String string1) {
	}

	private void debugln(String string1) {
		this.func_22211_a(string1 + "\n");
	}

	private void func_22199_a(String string1, int i2, int i3, String string4) {
		this.func_22211_a("REGION " + string1 + " " + this.fileName.getName() + "[" + i2 + "," + i3 + "] = " + string4);
	}

	private void func_22197_a(String string1, int i2, int i3, int i4, String string5) {
		this.func_22211_a("REGION " + string1 + " " + this.fileName.getName() + "[" + i2 + "," + i3 + "] " + i4 + "B = " + string5);
	}

	private void debugln(String string1, int i2, int i3, String string4) {
		this.func_22199_a(string1, i2, i3, string4 + "\n");
	}

	public synchronized DataInputStream getChunkDataInputStream(int i1, int i2) {
		if(this.outOfBounds(i1, i2)) {
			this.debugln("READ", i1, i2, "out of bounds");
			return null;
		} else {
			try {
				int i3 = this.getOffset(i1, i2);
				if(i3 == 0) {
					return null;
				} else {
					int i4 = i3 >> 8;
					int i5 = i3 & 255;
					if(i4 + i5 > this.sectorFree.size()) {
						this.debugln("READ", i1, i2, "invalid sector");
						return null;
					} else {
						this.dataFile.seek((long)(i4 * 4096));
						int i6 = this.dataFile.readInt();
						if(i6 > 4096 * i5) {
							this.debugln("READ", i1, i2, "invalid length: " + i6 + " > 4096 * " + i5);
							return null;
						} else {
							byte b7 = this.dataFile.readByte();
							byte[] b8;
							DataInputStream dataInputStream9;
							if(b7 == 1) {
								b8 = new byte[i6 - 1];
								this.dataFile.read(b8);
								dataInputStream9 = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(b8)));
								return dataInputStream9;
							} else if(b7 == 2) {
								b8 = new byte[i6 - 1];
								this.dataFile.read(b8);
								dataInputStream9 = new DataInputStream(new InflaterInputStream(new ByteArrayInputStream(b8)));
								return dataInputStream9;
							} else {
								this.debugln("READ", i1, i2, "unknown version " + b7);
								return null;
							}
						}
					}
				}
			} catch (IOException iOException10) {
				this.debugln("READ", i1, i2, "exception");
				return null;
			}
		}
	}

	public DataOutputStream getChunkDataOutputStream(int i1, int i2) {
		return this.outOfBounds(i1, i2) ? null : new DataOutputStream(new DeflaterOutputStream(new RegionFileChunkBuffer(this, i1, i2)));
	}

	protected synchronized void write(int i1, int i2, byte[] b3, int i4) {
		try {
			int i5 = this.getOffset(i1, i2);
			int i6 = i5 >> 8;
			int i7 = i5 & 255;
			int i8 = (i4 + 5) / 4096 + 1;
			if(i8 >= 256) {
				return;
			}

			if(i6 != 0 && i7 == i8) {
				this.func_22197_a("SAVE", i1, i2, i4, "rewrite");
				this.write(i6, b3, i4);
			} else {
				int i9;
				for(i9 = 0; i9 < i7; ++i9) {
					this.sectorFree.set(i6 + i9, true);
				}

				i9 = this.sectorFree.indexOf(true);
				int i10 = 0;
				int i11;
				if(i9 != -1) {
					for(i11 = i9; i11 < this.sectorFree.size(); ++i11) {
						if(i10 != 0) {
							if(((Boolean)this.sectorFree.get(i11)).booleanValue()) {
								++i10;
							} else {
								i10 = 0;
							}
						} else if(((Boolean)this.sectorFree.get(i11)).booleanValue()) {
							i9 = i11;
							i10 = 1;
						}

						if(i10 >= i8) {
							break;
						}
					}
				}

				if(i10 >= i8) {
					this.func_22197_a("SAVE", i1, i2, i4, "reuse");
					i6 = i9;
					this.setOffset(i1, i2, i9 << 8 | i8);

					for(i11 = 0; i11 < i8; ++i11) {
						this.sectorFree.set(i6 + i11, false);
					}

					this.write(i6, b3, i4);
				} else {
					this.func_22197_a("SAVE", i1, i2, i4, "grow");
					this.dataFile.seek(this.dataFile.length());
					i6 = this.sectorFree.size();

					for(i11 = 0; i11 < i8; ++i11) {
						this.dataFile.write(emptySector);
						this.sectorFree.add(false);
					}

					this.sizeDelta += 4096 * i8;
					this.write(i6, b3, i4);
					this.setOffset(i1, i2, i6 << 8 | i8);
				}
			}

			this.func_22208_b(i1, i2, (int)(System.currentTimeMillis() / 1000L));
		} catch (IOException iOException12) {
			iOException12.printStackTrace();
		}

	}

	private void write(int i1, byte[] b2, int i3) throws IOException {
		this.debugln(" " + i1);
		this.dataFile.seek((long)(i1 * 4096));
		this.dataFile.writeInt(i3 + 1);
		this.dataFile.writeByte(2);
		this.dataFile.write(b2, 0, i3);
	}

	private boolean outOfBounds(int i1, int i2) {
		return i1 < 0 || i1 >= 32 || i2 < 0 || i2 >= 32;
	}

	private int getOffset(int i1, int i2) {
		return this.offsets[i1 + i2 * 32];
	}

	public boolean func_22202_c(int i1, int i2) {
		return this.getOffset(i1, i2) != 0;
	}

	private void setOffset(int i1, int i2, int i3) throws IOException {
		this.offsets[i1 + i2 * 32] = i3;
		this.dataFile.seek((long)((i1 + i2 * 32) * 4));
		this.dataFile.writeInt(i3);
	}

	private void func_22208_b(int i1, int i2, int i3) throws IOException {
		this.field_22217_e[i1 + i2 * 32] = i3;
		this.dataFile.seek((long)(4096 + (i1 + i2 * 32) * 4));
		this.dataFile.writeInt(i3);
	}

	public void func_22196_b() throws IOException {
		this.dataFile.close();
	}
}
