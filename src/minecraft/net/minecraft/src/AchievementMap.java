package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AchievementMap {
	public static AchievementMap instance = new AchievementMap();
	private Map guidMap = new HashMap();

	private AchievementMap() {
		try {
			BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(AchievementMap.class.getResourceAsStream("/achievement/map.txt")));

			String string2;
			while((string2 = bufferedReader1.readLine()) != null) {
				String[] string3 = string2.split(",");
				int i4 = Integer.parseInt(string3[0]);
				this.guidMap.put(i4, string3[1]);
			}

			bufferedReader1.close();
		} catch (Exception exception5) {
			exception5.printStackTrace();
		}

	}

	public static String getGuid(int i0) {
		return (String)instance.guidMap.get(i0);
	}
}
