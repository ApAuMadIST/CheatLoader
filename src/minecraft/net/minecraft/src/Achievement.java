package net.minecraft.src;

public class Achievement extends StatBase {
	public final int displayColumn;
	public final int displayRow;
	public final Achievement parentAchievement;
	private final String achievementDescription;
	private IStatStringFormat statStringFormatter;
	public final ItemStack theItemStack;
	private boolean isSpecial;

	public Achievement(int i1, String string2, int i3, int i4, Item item5, Achievement achievement6) {
		this(i1, string2, i3, i4, new ItemStack(item5), achievement6);
	}

	public Achievement(int i1, String string2, int i3, int i4, Block block5, Achievement achievement6) {
		this(i1, string2, i3, i4, new ItemStack(block5), achievement6);
	}

	public Achievement(int i1, String string2, int i3, int i4, ItemStack itemStack5, Achievement achievement6) {
		super(5242880 + i1, StatCollector.translateToLocal("achievement." + string2));
		this.theItemStack = itemStack5;
		this.achievementDescription = StatCollector.translateToLocal("achievement." + string2 + ".desc");
		this.displayColumn = i3;
		this.displayRow = i4;
		if(i3 < AchievementList.minDisplayColumn) {
			AchievementList.minDisplayColumn = i3;
		}

		if(i4 < AchievementList.minDisplayRow) {
			AchievementList.minDisplayRow = i4;
		}

		if(i3 > AchievementList.maxDisplayColumn) {
			AchievementList.maxDisplayColumn = i3;
		}

		if(i4 > AchievementList.maxDisplayRow) {
			AchievementList.maxDisplayRow = i4;
		}

		this.parentAchievement = achievement6;
	}

	public Achievement func_27089_a() {
		this.field_27088_g = true;
		return this;
	}

	public Achievement setSpecial() {
		this.isSpecial = true;
		return this;
	}

	public Achievement registerAchievement() {
		super.registerStat();
		AchievementList.achievementList.add(this);
		return this;
	}

	public boolean func_25067_a() {
		return true;
	}

	public String getDescription() {
		return this.statStringFormatter != null ? this.statStringFormatter.formatString(this.achievementDescription) : this.achievementDescription;
	}

	public Achievement setStatStringFormatter(IStatStringFormat iStatStringFormat1) {
		this.statStringFormatter = iStatStringFormat1;
		return this;
	}

	public boolean getSpecial() {
		return this.isSpecial;
	}

	public StatBase registerStat() {
		return this.registerAchievement();
	}

	public StatBase func_27082_h() {
		return this.func_27089_a();
	}
}
