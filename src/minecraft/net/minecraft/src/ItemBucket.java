package net.minecraft.src;

public class ItemBucket extends Item {
	private int isFull;

	public ItemBucket(int i1, int i2) {
		super(i1);
		this.maxStackSize = 1;
		this.isFull = i2;
	}

	public ItemStack onItemRightClick(ItemStack itemStack1, World world2, EntityPlayer entityPlayer3) {
		float f4 = 1.0F;
		float f5 = entityPlayer3.prevRotationPitch + (entityPlayer3.rotationPitch - entityPlayer3.prevRotationPitch) * f4;
		float f6 = entityPlayer3.prevRotationYaw + (entityPlayer3.rotationYaw - entityPlayer3.prevRotationYaw) * f4;
		double d7 = entityPlayer3.prevPosX + (entityPlayer3.posX - entityPlayer3.prevPosX) * (double)f4;
		double d9 = entityPlayer3.prevPosY + (entityPlayer3.posY - entityPlayer3.prevPosY) * (double)f4 + 1.62D - (double)entityPlayer3.yOffset;
		double d11 = entityPlayer3.prevPosZ + (entityPlayer3.posZ - entityPlayer3.prevPosZ) * (double)f4;
		Vec3D vec3D13 = Vec3D.createVector(d7, d9, d11);
		float f14 = MathHelper.cos(-f6 * 0.017453292F - (float)Math.PI);
		float f15 = MathHelper.sin(-f6 * 0.017453292F - (float)Math.PI);
		float f16 = -MathHelper.cos(-f5 * 0.017453292F);
		float f17 = MathHelper.sin(-f5 * 0.017453292F);
		float f18 = f15 * f16;
		float f20 = f14 * f16;
		double d21 = 5.0D;
		Vec3D vec3D23 = vec3D13.addVector((double)f18 * d21, (double)f17 * d21, (double)f20 * d21);
		MovingObjectPosition movingObjectPosition24 = world2.rayTraceBlocks_do(vec3D13, vec3D23, this.isFull == 0);
		if(movingObjectPosition24 == null) {
			return itemStack1;
		} else {
			if(movingObjectPosition24.typeOfHit == EnumMovingObjectType.TILE) {
				int i25 = movingObjectPosition24.blockX;
				int i26 = movingObjectPosition24.blockY;
				int i27 = movingObjectPosition24.blockZ;
				if(!world2.func_6466_a(entityPlayer3, i25, i26, i27)) {
					return itemStack1;
				}

				if(this.isFull == 0) {
					if(world2.getBlockMaterial(i25, i26, i27) == Material.water && world2.getBlockMetadata(i25, i26, i27) == 0) {
						world2.setBlockWithNotify(i25, i26, i27, 0);
						return new ItemStack(Item.bucketWater);
					}

					if(world2.getBlockMaterial(i25, i26, i27) == Material.lava && world2.getBlockMetadata(i25, i26, i27) == 0) {
						world2.setBlockWithNotify(i25, i26, i27, 0);
						return new ItemStack(Item.bucketLava);
					}
				} else {
					if(this.isFull < 0) {
						return new ItemStack(Item.bucketEmpty);
					}

					if(movingObjectPosition24.sideHit == 0) {
						--i26;
					}

					if(movingObjectPosition24.sideHit == 1) {
						++i26;
					}

					if(movingObjectPosition24.sideHit == 2) {
						--i27;
					}

					if(movingObjectPosition24.sideHit == 3) {
						++i27;
					}

					if(movingObjectPosition24.sideHit == 4) {
						--i25;
					}

					if(movingObjectPosition24.sideHit == 5) {
						++i25;
					}

					if(world2.isAirBlock(i25, i26, i27) || !world2.getBlockMaterial(i25, i26, i27).isSolid()) {
						if(world2.worldProvider.isHellWorld && this.isFull == Block.waterMoving.blockID) {
							world2.playSoundEffect(d7 + 0.5D, d9 + 0.5D, d11 + 0.5D, "random.fizz", 0.5F, 2.6F + (world2.rand.nextFloat() - world2.rand.nextFloat()) * 0.8F);

							for(int i28 = 0; i28 < 8; ++i28) {
								world2.spawnParticle("largesmoke", (double)i25 + Math.random(), (double)i26 + Math.random(), (double)i27 + Math.random(), 0.0D, 0.0D, 0.0D);
							}
						} else {
							world2.setBlockAndMetadataWithNotify(i25, i26, i27, this.isFull, 0);
						}

						return new ItemStack(Item.bucketEmpty);
					}
				}
			} else if(this.isFull == 0 && movingObjectPosition24.entityHit instanceof EntityCow) {
				return new ItemStack(Item.bucketMilk);
			}

			return itemStack1;
		}
	}
}
