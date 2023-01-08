package net.minecraft.src;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class StatFileWriter {
	private Map field_25102_a = new HashMap();
	private Map field_25101_b = new HashMap();
	private boolean field_27189_c = false;
	private StatsSyncher statsSyncher;

	public StatFileWriter(Session session1, File file2) {
		File file3 = new File(file2, "stats");
		if(!file3.exists()) {
			file3.mkdir();
		}

		File[] file4 = file2.listFiles();
		int i5 = file4.length;

		for(int i6 = 0; i6 < i5; ++i6) {
			File file7 = file4[i6];
			if(file7.getName().startsWith("stats_") && file7.getName().endsWith(".dat")) {
				File file8 = new File(file3, file7.getName());
				if(!file8.exists()) {
					System.out.println("Relocating " + file7.getName());
					file7.renameTo(file8);
				}
			}
		}

		this.statsSyncher = new StatsSyncher(session1, this, file3);
	}

	public void readStat(StatBase statBase1, int i2) {
		this.writeStatToMap(this.field_25101_b, statBase1, i2);
		this.writeStatToMap(this.field_25102_a, statBase1, i2);
		this.field_27189_c = true;
	}

	private void writeStatToMap(Map map1, StatBase statBase2, int i3) {
		Integer integer4 = (Integer)map1.get(statBase2);
		int i5 = integer4 == null ? 0 : integer4.intValue();
		map1.put(statBase2, i5 + i3);
	}

	public Map func_27176_a() {
		return new HashMap(this.field_25101_b);
	}

	public void func_27179_a(Map map1) {
		if(map1 != null) {
			this.field_27189_c = true;
			Iterator iterator2 = map1.keySet().iterator();

			while(iterator2.hasNext()) {
				StatBase statBase3 = (StatBase)iterator2.next();
				this.writeStatToMap(this.field_25101_b, statBase3, ((Integer)map1.get(statBase3)).intValue());
				this.writeStatToMap(this.field_25102_a, statBase3, ((Integer)map1.get(statBase3)).intValue());
			}

		}
	}

	public void func_27180_b(Map map1) {
		if(map1 != null) {
			Iterator iterator2 = map1.keySet().iterator();

			while(iterator2.hasNext()) {
				StatBase statBase3 = (StatBase)iterator2.next();
				Integer integer4 = (Integer)this.field_25101_b.get(statBase3);
				int i5 = integer4 == null ? 0 : integer4.intValue();
				this.field_25102_a.put(statBase3, ((Integer)map1.get(statBase3)).intValue() + i5);
			}

		}
	}

	public void func_27187_c(Map map1) {
		if(map1 != null) {
			this.field_27189_c = true;
			Iterator iterator2 = map1.keySet().iterator();

			while(iterator2.hasNext()) {
				StatBase statBase3 = (StatBase)iterator2.next();
				this.writeStatToMap(this.field_25101_b, statBase3, ((Integer)map1.get(statBase3)).intValue());
			}

		}
	}

	public static Map func_27177_a(String string0) {
		HashMap hashMap1 = new HashMap();

		try {
			String string2 = "local";
			StringBuilder stringBuilder3 = new StringBuilder();
			J_JsonRootNode j_JsonRootNode4 = (new J_JdomParser()).func_27367_a(string0);
			List list5 = j_JsonRootNode4.func_27217_b(new Object[]{"stats-change"});
			Iterator iterator6 = list5.iterator();

			while(iterator6.hasNext()) {
				J_JsonNode j_JsonNode7 = (J_JsonNode)iterator6.next();
				Map map8 = j_JsonNode7.func_27214_c();
				Entry map$Entry9 = (Entry)map8.entrySet().iterator().next();
				int i10 = Integer.parseInt(((J_JsonStringNode)map$Entry9.getKey()).func_27216_b());
				int i11 = Integer.parseInt(((J_JsonNode)map$Entry9.getValue()).func_27216_b());
				StatBase statBase12 = StatList.func_27361_a(i10);
				if(statBase12 == null) {
					System.out.println(i10 + " is not a valid stat");
				} else {
					stringBuilder3.append(StatList.func_27361_a(i10).statGuid).append(",");
					stringBuilder3.append(i11).append(",");
					hashMap1.put(statBase12, i11);
				}
			}

			MD5String mD5String14 = new MD5String(string2);
			String string15 = mD5String14.func_27369_a(stringBuilder3.toString());
			if(!string15.equals(j_JsonRootNode4.func_27213_a(new Object[]{"checksum"}))) {
				System.out.println("CHECKSUM MISMATCH");
				return null;
			}
		} catch (J_InvalidSyntaxException j_InvalidSyntaxException13) {
			j_InvalidSyntaxException13.printStackTrace();
		}

		return hashMap1;
	}

	public static String func_27185_a(String string0, String string1, Map map2) {
		StringBuilder stringBuilder3 = new StringBuilder();
		StringBuilder stringBuilder4 = new StringBuilder();
		boolean z5 = true;
		stringBuilder3.append("{\r\n");
		if(string0 != null && string1 != null) {
			stringBuilder3.append("  \"user\":{\r\n");
			stringBuilder3.append("    \"name\":\"").append(string0).append("\",\r\n");
			stringBuilder3.append("    \"sessionid\":\"").append(string1).append("\"\r\n");
			stringBuilder3.append("  },\r\n");
		}

		stringBuilder3.append("  \"stats-change\":[");
		Iterator iterator6 = map2.keySet().iterator();

		while(iterator6.hasNext()) {
			StatBase statBase7 = (StatBase)iterator6.next();
			if(!z5) {
				stringBuilder3.append("},");
			} else {
				z5 = false;
			}

			stringBuilder3.append("\r\n    {\"").append(statBase7.statId).append("\":").append(map2.get(statBase7));
			stringBuilder4.append(statBase7.statGuid).append(",");
			stringBuilder4.append(map2.get(statBase7)).append(",");
		}

		if(!z5) {
			stringBuilder3.append("}");
		}

		MD5String mD5String8 = new MD5String(string1);
		stringBuilder3.append("\r\n  ],\r\n");
		stringBuilder3.append("  \"checksum\":\"").append(mD5String8.func_27369_a(stringBuilder4.toString())).append("\"\r\n");
		stringBuilder3.append("}");
		return stringBuilder3.toString();
	}

	public boolean hasAchievementUnlocked(Achievement achievement1) {
		return this.field_25102_a.containsKey(achievement1);
	}

	public boolean func_27181_b(Achievement achievement1) {
		return achievement1.parentAchievement == null || this.hasAchievementUnlocked(achievement1.parentAchievement);
	}

	public int writeStat(StatBase statBase1) {
		Integer integer2 = (Integer)this.field_25102_a.get(statBase1);
		return integer2 == null ? 0 : integer2.intValue();
	}

	public void func_27175_b() {
	}

	public void syncStats() {
		this.statsSyncher.syncStatsFileWithMap(this.func_27176_a());
	}

	public void func_27178_d() {
		if(this.field_27189_c && this.statsSyncher.func_27420_b()) {
			this.statsSyncher.func_27424_a(this.func_27176_a());
		}

		this.statsSyncher.func_27425_c();
	}
}
