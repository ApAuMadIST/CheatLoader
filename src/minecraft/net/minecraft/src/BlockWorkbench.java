package net.minecraft.src;

public class BlockWorkbench extends Block {
	protected BlockWorkbench(int i1) {
		super(i1, Material.wood);
		this.blockIndexInTexture = 59;
	}

	public int getBlockTextureFromSide(int i1) {
		return i1 == 1 ? this.blockIndexInTexture - 16 : (i1 == 0 ? Block.planks.getBlockTextureFromSide(0) : (i1 != 2 && i1 != 4 ? this.blockIndexInTexture : this.blockIndexInTexture + 1));
	}

	public boolean blockActivated(World world1, int i2, int i3, int i4, EntityPlayer entityPlayer5) {
		if(world1.multiplayerWorld) {
			return true;
		} else {
			entityPlayer5.displayWorkbenchGUI(i2, i3, i4);
			return true;
		}
	}
}
