package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class StatsSyncher {
	private volatile boolean field_27438_a = false;
	private volatile Map field_27437_b = null;
	private volatile Map field_27436_c = null;
	private StatFileWriter field_27435_d;
	private File field_27434_e;
	private File field_27433_f;
	private File field_27432_g;
	private File field_27431_h;
	private File field_27430_i;
	private File field_27429_j;
	private Session field_27428_k;
	private int field_27427_l = 0;
	private int field_27426_m = 0;

	public StatsSyncher(Session session1, StatFileWriter statFileWriter2, File file3) {
		this.field_27434_e = new File(file3, "stats_" + session1.username.toLowerCase() + "_unsent.dat");
		this.field_27433_f = new File(file3, "stats_" + session1.username.toLowerCase() + ".dat");
		this.field_27430_i = new File(file3, "stats_" + session1.username.toLowerCase() + "_unsent.old");
		this.field_27429_j = new File(file3, "stats_" + session1.username.toLowerCase() + ".old");
		this.field_27432_g = new File(file3, "stats_" + session1.username.toLowerCase() + "_unsent.tmp");
		this.field_27431_h = new File(file3, "stats_" + session1.username.toLowerCase() + ".tmp");
		if(!session1.username.toLowerCase().equals(session1.username)) {
			this.func_28214_a(file3, "stats_" + session1.username + "_unsent.dat", this.field_27434_e);
			this.func_28214_a(file3, "stats_" + session1.username + ".dat", this.field_27433_f);
			this.func_28214_a(file3, "stats_" + session1.username + "_unsent.old", this.field_27430_i);
			this.func_28214_a(file3, "stats_" + session1.username + ".old", this.field_27429_j);
			this.func_28214_a(file3, "stats_" + session1.username + "_unsent.tmp", this.field_27432_g);
			this.func_28214_a(file3, "stats_" + session1.username + ".tmp", this.field_27431_h);
		}

		this.field_27435_d = statFileWriter2;
		this.field_27428_k = session1;
		if(this.field_27434_e.exists()) {
			statFileWriter2.func_27179_a(this.func_27415_a(this.field_27434_e, this.field_27432_g, this.field_27430_i));
		}

		this.func_27418_a();
	}

	private void func_28214_a(File file1, String string2, File file3) {
		File file4 = new File(file1, string2);
		if(file4.exists() && !file4.isDirectory() && !file3.exists()) {
			file4.renameTo(file3);
		}

	}

	private Map func_27415_a(File file1, File file2, File file3) {
		return file1.exists() ? this.func_27408_a(file1) : (file3.exists() ? this.func_27408_a(file3) : (file2.exists() ? this.func_27408_a(file2) : null));
	}

	private Map func_27408_a(File file1) {
		BufferedReader bufferedReader2 = null;

		try {
			bufferedReader2 = new BufferedReader(new FileReader(file1));
			String string3 = "";
			StringBuilder stringBuilder4 = new StringBuilder();

			while((string3 = bufferedReader2.readLine()) != null) {
				stringBuilder4.append(string3);
			}

			Map map5 = StatFileWriter.func_27177_a(stringBuilder4.toString());
			return map5;
		} catch (Exception exception15) {
			exception15.printStackTrace();
		} finally {
			if(bufferedReader2 != null) {
				try {
					bufferedReader2.close();
				} catch (Exception exception14) {
					exception14.printStackTrace();
				}
			}

		}

		return null;
	}

	private void func_27410_a(Map map1, File file2, File file3, File file4) throws IOException {
		PrintWriter printWriter5 = new PrintWriter(new FileWriter(file3, false));

		try {
			printWriter5.print(StatFileWriter.func_27185_a(this.field_27428_k.username, "local", map1));
		} finally {
			printWriter5.close();
		}

		if(file4.exists()) {
			file4.delete();
		}

		if(file2.exists()) {
			file2.renameTo(file4);
		}

		file3.renameTo(file2);
	}

	public void func_27418_a() {
		if(this.field_27438_a) {
			throw new IllegalStateException("Can\'t get stats from server while StatsSyncher is busy!");
		} else {
			this.field_27427_l = 100;
			this.field_27438_a = true;
			(new ThreadStatSyncherReceive(this)).start();
		}
	}

	public void func_27424_a(Map map1) {
		if(this.field_27438_a) {
			throw new IllegalStateException("Can\'t save stats while StatsSyncher is busy!");
		} else {
			this.field_27427_l = 100;
			this.field_27438_a = true;
			(new ThreadStatSyncherSend(this, map1)).start();
		}
	}

	public void syncStatsFileWithMap(Map map1) {
		int i2 = 30;

		while(this.field_27438_a) {
			--i2;
			if(i2 <= 0) {
				break;
			}

			try {
				Thread.sleep(100L);
			} catch (InterruptedException interruptedException10) {
				interruptedException10.printStackTrace();
			}
		}

		this.field_27438_a = true;

		try {
			this.func_27410_a(map1, this.field_27434_e, this.field_27432_g, this.field_27430_i);
		} catch (Exception exception8) {
			exception8.printStackTrace();
		} finally {
			this.field_27438_a = false;
		}

	}

	public boolean func_27420_b() {
		return this.field_27427_l <= 0 && !this.field_27438_a && this.field_27436_c == null;
	}

	public void func_27425_c() {
		if(this.field_27427_l > 0) {
			--this.field_27427_l;
		}

		if(this.field_27426_m > 0) {
			--this.field_27426_m;
		}

		if(this.field_27436_c != null) {
			this.field_27435_d.func_27187_c(this.field_27436_c);
			this.field_27436_c = null;
		}

		if(this.field_27437_b != null) {
			this.field_27435_d.func_27180_b(this.field_27437_b);
			this.field_27437_b = null;
		}

	}

	static Map func_27422_a(StatsSyncher statsSyncher0) {
		return statsSyncher0.field_27437_b;
	}

	static File func_27423_b(StatsSyncher statsSyncher0) {
		return statsSyncher0.field_27433_f;
	}

	static File func_27411_c(StatsSyncher statsSyncher0) {
		return statsSyncher0.field_27431_h;
	}

	static File func_27413_d(StatsSyncher statsSyncher0) {
		return statsSyncher0.field_27429_j;
	}

	static void func_27412_a(StatsSyncher statsSyncher0, Map map1, File file2, File file3, File file4) throws IOException {
		statsSyncher0.func_27410_a(map1, file2, file3, file4);
	}

	static Map func_27421_a(StatsSyncher statsSyncher0, Map map1) {
		return statsSyncher0.field_27437_b = map1;
	}

	static Map func_27409_a(StatsSyncher statsSyncher0, File file1, File file2, File file3) {
		return statsSyncher0.func_27415_a(file1, file2, file3);
	}

	static boolean func_27416_a(StatsSyncher statsSyncher0, boolean z1) {
		return statsSyncher0.field_27438_a = z1;
	}

	static File func_27414_e(StatsSyncher statsSyncher0) {
		return statsSyncher0.field_27434_e;
	}

	static File func_27417_f(StatsSyncher statsSyncher0) {
		return statsSyncher0.field_27432_g;
	}

	static File func_27419_g(StatsSyncher statsSyncher0) {
		return statsSyncher0.field_27430_i;
	}
}
