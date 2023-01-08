package net.minecraft.src;

public class ItemRecord extends Item {
	public final String recordName;

	protected ItemRecord(int i1, String string2) {
		super(i1);
		this.recordName = string2;
		this.maxStackSize = 1;
	}

	public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
		if(world3.getBlockId(i4, i5, i6) == Block.jukebox.blockID && world3.getBlockMetadata(i4, i5, i6) == 0) {
			if(world3.multiplayerWorld) {
				return true;
			} else {
				((BlockJukeBox)Block.jukebox).ejectRecord(world3, i4, i5, i6, this.shiftedIndex);
				world3.func_28107_a((EntityPlayer)null, 1005, i4, i5, i6, this.shiftedIndex);
				--itemStack1.stackSize;
				return true;
			}
		} else {
			return false;
		}
	}
}
