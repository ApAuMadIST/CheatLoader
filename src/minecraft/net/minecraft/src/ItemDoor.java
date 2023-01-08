package net.minecraft.src;

public class ItemDoor extends Item {
	private Material doorMaterial;

	public ItemDoor(int i1, Material material2) {
		super(i1);
		this.doorMaterial = material2;
		this.maxStackSize = 1;
	}

	public boolean onItemUse(ItemStack itemStack1, EntityPlayer entityPlayer2, World world3, int i4, int i5, int i6, int i7) {
		if(i7 != 1) {
			return false;
		} else {
			++i5;
			Block block8;
			if(this.doorMaterial == Material.wood) {
				block8 = Block.doorWood;
			} else {
				block8 = Block.doorSteel;
			}

			if(!block8.canPlaceBlockAt(world3, i4, i5, i6)) {
				return false;
			} else {
				int i9 = MathHelper.floor_double((double)((entityPlayer2.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
				byte b10 = 0;
				byte b11 = 0;
				if(i9 == 0) {
					b11 = 1;
				}

				if(i9 == 1) {
					b10 = -1;
				}

				if(i9 == 2) {
					b11 = -1;
				}

				if(i9 == 3) {
					b10 = 1;
				}

				int i12 = (world3.isBlockNormalCube(i4 - b10, i5, i6 - b11) ? 1 : 0) + (world3.isBlockNormalCube(i4 - b10, i5 + 1, i6 - b11) ? 1 : 0);
				int i13 = (world3.isBlockNormalCube(i4 + b10, i5, i6 + b11) ? 1 : 0) + (world3.isBlockNormalCube(i4 + b10, i5 + 1, i6 + b11) ? 1 : 0);
				boolean z14 = world3.getBlockId(i4 - b10, i5, i6 - b11) == block8.blockID || world3.getBlockId(i4 - b10, i5 + 1, i6 - b11) == block8.blockID;
				boolean z15 = world3.getBlockId(i4 + b10, i5, i6 + b11) == block8.blockID || world3.getBlockId(i4 + b10, i5 + 1, i6 + b11) == block8.blockID;
				boolean z16 = false;
				if(z14 && !z15) {
					z16 = true;
				} else if(i13 > i12) {
					z16 = true;
				}

				if(z16) {
					i9 = i9 - 1 & 3;
					i9 += 4;
				}

				world3.editingBlocks = true;
				world3.setBlockAndMetadataWithNotify(i4, i5, i6, block8.blockID, i9);
				world3.setBlockAndMetadataWithNotify(i4, i5 + 1, i6, block8.blockID, i9 + 8);
				world3.editingBlocks = false;
				world3.notifyBlocksOfNeighborChange(i4, i5, i6, block8.blockID);
				world3.notifyBlocksOfNeighborChange(i4, i5 + 1, i6, block8.blockID);
				--itemStack1.stackSize;
				return true;
			}
		}
	}
}
