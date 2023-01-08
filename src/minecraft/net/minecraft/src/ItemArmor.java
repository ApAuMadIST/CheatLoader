package net.minecraft.src;

public class ItemArmor extends Item {
	private static final int[] damageReduceAmountArray = new int[]{3, 8, 6, 3};
	private static final int[] maxDamageArray = new int[]{11, 16, 15, 13};
	public final int armorLevel;
	public final int armorType;
	public final int damageReduceAmount;
	public final int renderIndex;

	public ItemArmor(int i1, int i2, int i3, int i4) {
		super(i1);
		this.armorLevel = i2;
		this.armorType = i4;
		this.renderIndex = i3;
		this.damageReduceAmount = damageReduceAmountArray[i4];
		this.setMaxDamage(maxDamageArray[i4] * 3 << i2);
		this.maxStackSize = 1;
	}
}
