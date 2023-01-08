package net.minecraft.src;

public class BlockPumpkin extends Block {
	private boolean blockType;

	protected BlockPumpkin(int i1, int i2, boolean z3) {
		super(i1, Material.pumpkin);
		this.blockIndexInTexture = i2;
		this.setTickOnLoad(true);
		this.blockType = z3;
	}

	public int getBlockTextureFromSideAndMetadata(int i1, int i2) {
		if(i1 == 1) {
			return this.blockIndexInTexture;
		} else if(i1 == 0) {
			return this.blockIndexInTexture;
		} else {
			int i3 = this.blockIndexInTexture + 1 + 16;
			if(this.blockType) {
				++i3;
			}

			return i2 == 2 && i1 == 2 ? i3 : (i2 == 3 && i1 == 5 ? i3 : (i2 == 0 && i1 == 3 ? i3 : (i2 == 1 && i1 == 4 ? i3 : this.blockIndexInTexture + 16)));
		}
	}

	public int getBlockTextureFromSide(int i1) {
		return i1 == 1 ? this.blockIndexInTexture : (i1 == 0 ? this.blockIndexInTexture : (i1 == 3 ? this.blockIndexInTexture + 1 + 16 : this.blockIndexInTexture + 16));
	}

	public void onBlockAdded(World world1, int i2, int i3, int i4) {
		super.onBlockAdded(world1, i2, i3, i4);
	}

	public boolean canPlaceBlockAt(World world1, int i2, int i3, int i4) {
		int i5 = world1.getBlockId(i2, i3, i4);
		return (i5 == 0 || Block.blocksList[i5].blockMaterial.getIsGroundCover()) && world1.isBlockNormalCube(i2, i3 - 1, i4);
	}

	public void onBlockPlacedBy(World world1, int i2, int i3, int i4, EntityLiving entityLiving5) {
		int i6 = MathHelper.floor_double((double)(entityLiving5.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
		world1.setBlockMetadataWithNotify(i2, i3, i4, i6);
	}
}
