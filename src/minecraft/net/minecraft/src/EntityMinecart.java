package net.minecraft.src;

import java.util.List;

public class EntityMinecart extends Entity implements IInventory {
	private ItemStack[] cargoItems;
	public int minecartCurrentDamage;
	public int minecartTimeSinceHit;
	public int minecartRockDirection;
	private boolean field_856_i;
	public int minecartType;
	public int fuel;
	public double pushX;
	public double pushZ;
	private static final int[][][] field_855_j = new int[][][]{{{0, 0, -1}, {0, 0, 1}}, {{-1, 0, 0}, {1, 0, 0}}, {{-1, -1, 0}, {1, 0, 0}}, {{-1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, {-1, 0, 0}}, {{0, 0, -1}, {-1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};
	private int field_9415_k;
	private double field_9414_l;
	private double field_9413_m;
	private double field_9412_n;
	private double field_9411_o;
	private double field_9410_p;
	private double field_9409_q;
	private double field_9408_r;
	private double field_9407_s;

	public EntityMinecart(World world1) {
		super(world1);
		this.cargoItems = new ItemStack[36];
		this.minecartCurrentDamage = 0;
		this.minecartTimeSinceHit = 0;
		this.minecartRockDirection = 1;
		this.field_856_i = false;
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.7F);
		this.yOffset = this.height / 2.0F;
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
	}

	public AxisAlignedBB getCollisionBox(Entity entity1) {
		return entity1.boundingBox;
	}

	public AxisAlignedBB getBoundingBox() {
		return null;
	}

	public boolean canBePushed() {
		return true;
	}

	public EntityMinecart(World world1, double d2, double d4, double d6, int i8) {
		this(world1);
		this.setPosition(d2, d4 + (double)this.yOffset, d6);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = d2;
		this.prevPosY = d4;
		this.prevPosZ = d6;
		this.minecartType = i8;
	}

	public double getMountedYOffset() {
		return (double)this.height * 0.0D - (double)0.3F;
	}

	public boolean attackEntityFrom(Entity entity1, int i2) {
		if(!this.worldObj.multiplayerWorld && !this.isDead) {
			this.minecartRockDirection = -this.minecartRockDirection;
			this.minecartTimeSinceHit = 10;
			this.setBeenAttacked();
			this.minecartCurrentDamage += i2 * 10;
			if(this.minecartCurrentDamage > 40) {
				if(this.riddenByEntity != null) {
					this.riddenByEntity.mountEntity(this);
				}

				this.setEntityDead();
				this.dropItemWithOffset(Item.minecartEmpty.shiftedIndex, 1, 0.0F);
				if(this.minecartType == 1) {
					EntityMinecart entityMinecart3 = this;

					for(int i4 = 0; i4 < entityMinecart3.getSizeInventory(); ++i4) {
						ItemStack itemStack5 = entityMinecart3.getStackInSlot(i4);
						if(itemStack5 != null) {
							float f6 = this.rand.nextFloat() * 0.8F + 0.1F;
							float f7 = this.rand.nextFloat() * 0.8F + 0.1F;
							float f8 = this.rand.nextFloat() * 0.8F + 0.1F;

							while(itemStack5.stackSize > 0) {
								int i9 = this.rand.nextInt(21) + 10;
								if(i9 > itemStack5.stackSize) {
									i9 = itemStack5.stackSize;
								}

								itemStack5.stackSize -= i9;
								EntityItem entityItem10 = new EntityItem(this.worldObj, this.posX + (double)f6, this.posY + (double)f7, this.posZ + (double)f8, new ItemStack(itemStack5.itemID, i9, itemStack5.getItemDamage()));
								float f11 = 0.05F;
								entityItem10.motionX = (double)((float)this.rand.nextGaussian() * f11);
								entityItem10.motionY = (double)((float)this.rand.nextGaussian() * f11 + 0.2F);
								entityItem10.motionZ = (double)((float)this.rand.nextGaussian() * f11);
								this.worldObj.entityJoinedWorld(entityItem10);
							}
						}
					}

					this.dropItemWithOffset(Block.chest.blockID, 1, 0.0F);
				} else if(this.minecartType == 2) {
					this.dropItemWithOffset(Block.stoneOvenIdle.blockID, 1, 0.0F);
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public void performHurtAnimation() {
		System.out.println("Animating hurt");
		this.minecartRockDirection = -this.minecartRockDirection;
		this.minecartTimeSinceHit = 10;
		this.minecartCurrentDamage += this.minecartCurrentDamage * 10;
	}

	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	public void setEntityDead() {
		for(int i1 = 0; i1 < this.getSizeInventory(); ++i1) {
			ItemStack itemStack2 = this.getStackInSlot(i1);
			if(itemStack2 != null) {
				float f3 = this.rand.nextFloat() * 0.8F + 0.1F;
				float f4 = this.rand.nextFloat() * 0.8F + 0.1F;
				float f5 = this.rand.nextFloat() * 0.8F + 0.1F;

				while(itemStack2.stackSize > 0) {
					int i6 = this.rand.nextInt(21) + 10;
					if(i6 > itemStack2.stackSize) {
						i6 = itemStack2.stackSize;
					}

					itemStack2.stackSize -= i6;
					EntityItem entityItem7 = new EntityItem(this.worldObj, this.posX + (double)f3, this.posY + (double)f4, this.posZ + (double)f5, new ItemStack(itemStack2.itemID, i6, itemStack2.getItemDamage()));
					float f8 = 0.05F;
					entityItem7.motionX = (double)((float)this.rand.nextGaussian() * f8);
					entityItem7.motionY = (double)((float)this.rand.nextGaussian() * f8 + 0.2F);
					entityItem7.motionZ = (double)((float)this.rand.nextGaussian() * f8);
					this.worldObj.entityJoinedWorld(entityItem7);
				}
			}
		}

		super.setEntityDead();
	}

	public void onUpdate() {
		if(this.minecartTimeSinceHit > 0) {
			--this.minecartTimeSinceHit;
		}

		if(this.minecartCurrentDamage > 0) {
			--this.minecartCurrentDamage;
		}

		double d7;
		if(this.worldObj.multiplayerWorld && this.field_9415_k > 0) {
			if(this.field_9415_k > 0) {
				double d46 = this.posX + (this.field_9414_l - this.posX) / (double)this.field_9415_k;
				double d47 = this.posY + (this.field_9413_m - this.posY) / (double)this.field_9415_k;
				double d5 = this.posZ + (this.field_9412_n - this.posZ) / (double)this.field_9415_k;

				for(d7 = this.field_9411_o - (double)this.rotationYaw; d7 < -180.0D; d7 += 360.0D) {
				}

				while(d7 >= 180.0D) {
					d7 -= 360.0D;
				}

				this.rotationYaw = (float)((double)this.rotationYaw + d7 / (double)this.field_9415_k);
				this.rotationPitch = (float)((double)this.rotationPitch + (this.field_9410_p - (double)this.rotationPitch) / (double)this.field_9415_k);
				--this.field_9415_k;
				this.setPosition(d46, d47, d5);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}

		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			this.motionY -= (double)0.04F;
			int i1 = MathHelper.floor_double(this.posX);
			int i2 = MathHelper.floor_double(this.posY);
			int i3 = MathHelper.floor_double(this.posZ);
			if(BlockRail.isRailBlockAt(this.worldObj, i1, i2 - 1, i3)) {
				--i2;
			}

			double d4 = 0.4D;
			boolean z6 = false;
			d7 = 2.0D / 256D;
			int i9 = this.worldObj.getBlockId(i1, i2, i3);
			if(BlockRail.isRailBlock(i9)) {
				Vec3D vec3D10 = this.func_514_g(this.posX, this.posY, this.posZ);
				int i11 = this.worldObj.getBlockMetadata(i1, i2, i3);
				this.posY = (double)i2;
				boolean z12 = false;
				boolean z13 = false;
				if(i9 == Block.railPowered.blockID) {
					z12 = (i11 & 8) != 0;
					z13 = !z12;
				}

				if(((BlockRail)Block.blocksList[i9]).getIsPowered()) {
					i11 &= 7;
				}

				if(i11 >= 2 && i11 <= 5) {
					this.posY = (double)(i2 + 1);
				}

				if(i11 == 2) {
					this.motionX -= d7;
				}

				if(i11 == 3) {
					this.motionX += d7;
				}

				if(i11 == 4) {
					this.motionZ += d7;
				}

				if(i11 == 5) {
					this.motionZ -= d7;
				}

				int[][] i14 = field_855_j[i11];
				double d15 = (double)(i14[1][0] - i14[0][0]);
				double d17 = (double)(i14[1][2] - i14[0][2]);
				double d19 = Math.sqrt(d15 * d15 + d17 * d17);
				double d21 = this.motionX * d15 + this.motionZ * d17;
				if(d21 < 0.0D) {
					d15 = -d15;
					d17 = -d17;
				}

				double d23 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
				this.motionX = d23 * d15 / d19;
				this.motionZ = d23 * d17 / d19;
				double d25;
				if(z13) {
					d25 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
					if(d25 < 0.03D) {
						this.motionX *= 0.0D;
						this.motionY *= 0.0D;
						this.motionZ *= 0.0D;
					} else {
						this.motionX *= 0.5D;
						this.motionY *= 0.0D;
						this.motionZ *= 0.5D;
					}
				}

				d25 = 0.0D;
				double d27 = (double)i1 + 0.5D + (double)i14[0][0] * 0.5D;
				double d29 = (double)i3 + 0.5D + (double)i14[0][2] * 0.5D;
				double d31 = (double)i1 + 0.5D + (double)i14[1][0] * 0.5D;
				double d33 = (double)i3 + 0.5D + (double)i14[1][2] * 0.5D;
				d15 = d31 - d27;
				d17 = d33 - d29;
				double d35;
				double d37;
				double d39;
				if(d15 == 0.0D) {
					this.posX = (double)i1 + 0.5D;
					d25 = this.posZ - (double)i3;
				} else if(d17 == 0.0D) {
					this.posZ = (double)i3 + 0.5D;
					d25 = this.posX - (double)i1;
				} else {
					d35 = this.posX - d27;
					d37 = this.posZ - d29;
					d39 = (d35 * d15 + d37 * d17) * 2.0D;
					d25 = d39;
				}

				this.posX = d27 + d15 * d25;
				this.posZ = d29 + d17 * d25;
				this.setPosition(this.posX, this.posY + (double)this.yOffset, this.posZ);
				d35 = this.motionX;
				d37 = this.motionZ;
				if(this.riddenByEntity != null) {
					d35 *= 0.75D;
					d37 *= 0.75D;
				}

				if(d35 < -d4) {
					d35 = -d4;
				}

				if(d35 > d4) {
					d35 = d4;
				}

				if(d37 < -d4) {
					d37 = -d4;
				}

				if(d37 > d4) {
					d37 = d4;
				}

				this.moveEntity(d35, 0.0D, d37);
				if(i14[0][1] != 0 && MathHelper.floor_double(this.posX) - i1 == i14[0][0] && MathHelper.floor_double(this.posZ) - i3 == i14[0][2]) {
					this.setPosition(this.posX, this.posY + (double)i14[0][1], this.posZ);
				} else if(i14[1][1] != 0 && MathHelper.floor_double(this.posX) - i1 == i14[1][0] && MathHelper.floor_double(this.posZ) - i3 == i14[1][2]) {
					this.setPosition(this.posX, this.posY + (double)i14[1][1], this.posZ);
				}

				if(this.riddenByEntity != null) {
					this.motionX *= (double)0.997F;
					this.motionY *= 0.0D;
					this.motionZ *= (double)0.997F;
				} else {
					if(this.minecartType == 2) {
						d39 = (double)MathHelper.sqrt_double(this.pushX * this.pushX + this.pushZ * this.pushZ);
						if(d39 > 0.01D) {
							z6 = true;
							this.pushX /= d39;
							this.pushZ /= d39;
							double d41 = 0.04D;
							this.motionX *= (double)0.8F;
							this.motionY *= 0.0D;
							this.motionZ *= (double)0.8F;
							this.motionX += this.pushX * d41;
							this.motionZ += this.pushZ * d41;
						} else {
							this.motionX *= (double)0.9F;
							this.motionY *= 0.0D;
							this.motionZ *= (double)0.9F;
						}
					}

					this.motionX *= (double)0.96F;
					this.motionY *= 0.0D;
					this.motionZ *= (double)0.96F;
				}

				Vec3D vec3D52 = this.func_514_g(this.posX, this.posY, this.posZ);
				if(vec3D52 != null && vec3D10 != null) {
					double d40 = (vec3D10.yCoord - vec3D52.yCoord) * 0.05D;
					d23 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
					if(d23 > 0.0D) {
						this.motionX = this.motionX / d23 * (d23 + d40);
						this.motionZ = this.motionZ / d23 * (d23 + d40);
					}

					this.setPosition(this.posX, vec3D52.yCoord, this.posZ);
				}

				int i53 = MathHelper.floor_double(this.posX);
				int i54 = MathHelper.floor_double(this.posZ);
				if(i53 != i1 || i54 != i3) {
					d23 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
					this.motionX = d23 * (double)(i53 - i1);
					this.motionZ = d23 * (double)(i54 - i3);
				}

				double d42;
				if(this.minecartType == 2) {
					d42 = (double)MathHelper.sqrt_double(this.pushX * this.pushX + this.pushZ * this.pushZ);
					if(d42 > 0.01D && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.001D) {
						this.pushX /= d42;
						this.pushZ /= d42;
						if(this.pushX * this.motionX + this.pushZ * this.motionZ < 0.0D) {
							this.pushX = 0.0D;
							this.pushZ = 0.0D;
						} else {
							this.pushX = this.motionX;
							this.pushZ = this.motionZ;
						}
					}
				}

				if(z12) {
					d42 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
					if(d42 > 0.01D) {
						double d44 = 0.06D;
						this.motionX += this.motionX / d42 * d44;
						this.motionZ += this.motionZ / d42 * d44;
					} else if(i11 == 1) {
						if(this.worldObj.isBlockNormalCube(i1 - 1, i2, i3)) {
							this.motionX = 0.02D;
						} else if(this.worldObj.isBlockNormalCube(i1 + 1, i2, i3)) {
							this.motionX = -0.02D;
						}
					} else if(i11 == 0) {
						if(this.worldObj.isBlockNormalCube(i1, i2, i3 - 1)) {
							this.motionZ = 0.02D;
						} else if(this.worldObj.isBlockNormalCube(i1, i2, i3 + 1)) {
							this.motionZ = -0.02D;
						}
					}
				}
			} else {
				if(this.motionX < -d4) {
					this.motionX = -d4;
				}

				if(this.motionX > d4) {
					this.motionX = d4;
				}

				if(this.motionZ < -d4) {
					this.motionZ = -d4;
				}

				if(this.motionZ > d4) {
					this.motionZ = d4;
				}

				if(this.onGround) {
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}

				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				if(!this.onGround) {
					this.motionX *= (double)0.95F;
					this.motionY *= (double)0.95F;
					this.motionZ *= (double)0.95F;
				}
			}

			this.rotationPitch = 0.0F;
			double d48 = this.prevPosX - this.posX;
			double d49 = this.prevPosZ - this.posZ;
			if(d48 * d48 + d49 * d49 > 0.001D) {
				this.rotationYaw = (float)(Math.atan2(d49, d48) * 180.0D / Math.PI);
				if(this.field_856_i) {
					this.rotationYaw += 180.0F;
				}
			}

			double d50;
			for(d50 = (double)(this.rotationYaw - this.prevRotationYaw); d50 >= 180.0D; d50 -= 360.0D) {
			}

			while(d50 < -180.0D) {
				d50 += 360.0D;
			}

			if(d50 < -170.0D || d50 >= 170.0D) {
				this.rotationYaw += 180.0F;
				this.field_856_i = !this.field_856_i;
			}

			this.setRotation(this.rotationYaw, this.rotationPitch);
			List list16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand((double)0.2F, 0.0D, (double)0.2F));
			if(list16 != null && list16.size() > 0) {
				for(int i51 = 0; i51 < list16.size(); ++i51) {
					Entity entity18 = (Entity)list16.get(i51);
					if(entity18 != this.riddenByEntity && entity18.canBePushed() && entity18 instanceof EntityMinecart) {
						entity18.applyEntityCollision(this);
					}
				}
			}

			if(this.riddenByEntity != null && this.riddenByEntity.isDead) {
				this.riddenByEntity = null;
			}

			if(z6 && this.rand.nextInt(4) == 0) {
				--this.fuel;
				if(this.fuel < 0) {
					this.pushX = this.pushZ = 0.0D;
				}

				this.worldObj.spawnParticle("largesmoke", this.posX, this.posY + 0.8D, this.posZ, 0.0D, 0.0D, 0.0D);
			}

		}
	}

	public Vec3D func_515_a(double d1, double d3, double d5, double d7) {
		int i9 = MathHelper.floor_double(d1);
		int i10 = MathHelper.floor_double(d3);
		int i11 = MathHelper.floor_double(d5);
		if(BlockRail.isRailBlockAt(this.worldObj, i9, i10 - 1, i11)) {
			--i10;
		}

		int i12 = this.worldObj.getBlockId(i9, i10, i11);
		if(!BlockRail.isRailBlock(i12)) {
			return null;
		} else {
			int i13 = this.worldObj.getBlockMetadata(i9, i10, i11);
			if(((BlockRail)Block.blocksList[i12]).getIsPowered()) {
				i13 &= 7;
			}

			d3 = (double)i10;
			if(i13 >= 2 && i13 <= 5) {
				d3 = (double)(i10 + 1);
			}

			int[][] i14 = field_855_j[i13];
			double d15 = (double)(i14[1][0] - i14[0][0]);
			double d17 = (double)(i14[1][2] - i14[0][2]);
			double d19 = Math.sqrt(d15 * d15 + d17 * d17);
			d15 /= d19;
			d17 /= d19;
			d1 += d15 * d7;
			d5 += d17 * d7;
			if(i14[0][1] != 0 && MathHelper.floor_double(d1) - i9 == i14[0][0] && MathHelper.floor_double(d5) - i11 == i14[0][2]) {
				d3 += (double)i14[0][1];
			} else if(i14[1][1] != 0 && MathHelper.floor_double(d1) - i9 == i14[1][0] && MathHelper.floor_double(d5) - i11 == i14[1][2]) {
				d3 += (double)i14[1][1];
			}

			return this.func_514_g(d1, d3, d5);
		}
	}

	public Vec3D func_514_g(double d1, double d3, double d5) {
		int i7 = MathHelper.floor_double(d1);
		int i8 = MathHelper.floor_double(d3);
		int i9 = MathHelper.floor_double(d5);
		if(BlockRail.isRailBlockAt(this.worldObj, i7, i8 - 1, i9)) {
			--i8;
		}

		int i10 = this.worldObj.getBlockId(i7, i8, i9);
		if(BlockRail.isRailBlock(i10)) {
			int i11 = this.worldObj.getBlockMetadata(i7, i8, i9);
			d3 = (double)i8;
			if(((BlockRail)Block.blocksList[i10]).getIsPowered()) {
				i11 &= 7;
			}

			if(i11 >= 2 && i11 <= 5) {
				d3 = (double)(i8 + 1);
			}

			int[][] i12 = field_855_j[i11];
			double d13 = 0.0D;
			double d15 = (double)i7 + 0.5D + (double)i12[0][0] * 0.5D;
			double d17 = (double)i8 + 0.5D + (double)i12[0][1] * 0.5D;
			double d19 = (double)i9 + 0.5D + (double)i12[0][2] * 0.5D;
			double d21 = (double)i7 + 0.5D + (double)i12[1][0] * 0.5D;
			double d23 = (double)i8 + 0.5D + (double)i12[1][1] * 0.5D;
			double d25 = (double)i9 + 0.5D + (double)i12[1][2] * 0.5D;
			double d27 = d21 - d15;
			double d29 = (d23 - d17) * 2.0D;
			double d31 = d25 - d19;
			if(d27 == 0.0D) {
				d1 = (double)i7 + 0.5D;
				d13 = d5 - (double)i9;
			} else if(d31 == 0.0D) {
				d5 = (double)i9 + 0.5D;
				d13 = d1 - (double)i7;
			} else {
				double d33 = d1 - d15;
				double d35 = d5 - d19;
				double d37 = (d33 * d27 + d35 * d31) * 2.0D;
				d13 = d37;
			}

			d1 = d15 + d27 * d13;
			d3 = d17 + d29 * d13;
			d5 = d19 + d31 * d13;
			if(d29 < 0.0D) {
				++d3;
			}

			if(d29 > 0.0D) {
				d3 += 0.5D;
			}

			return Vec3D.createVector(d1, d3, d5);
		} else {
			return null;
		}
	}

	protected void writeEntityToNBT(NBTTagCompound nBTTagCompound1) {
		nBTTagCompound1.setInteger("Type", this.minecartType);
		if(this.minecartType == 2) {
			nBTTagCompound1.setDouble("PushX", this.pushX);
			nBTTagCompound1.setDouble("PushZ", this.pushZ);
			nBTTagCompound1.setShort("Fuel", (short)this.fuel);
		} else if(this.minecartType == 1) {
			NBTTagList nBTTagList2 = new NBTTagList();

			for(int i3 = 0; i3 < this.cargoItems.length; ++i3) {
				if(this.cargoItems[i3] != null) {
					NBTTagCompound nBTTagCompound4 = new NBTTagCompound();
					nBTTagCompound4.setByte("Slot", (byte)i3);
					this.cargoItems[i3].writeToNBT(nBTTagCompound4);
					nBTTagList2.setTag(nBTTagCompound4);
				}
			}

			nBTTagCompound1.setTag("Items", nBTTagList2);
		}

	}

	protected void readEntityFromNBT(NBTTagCompound nBTTagCompound1) {
		this.minecartType = nBTTagCompound1.getInteger("Type");
		if(this.minecartType == 2) {
			this.pushX = nBTTagCompound1.getDouble("PushX");
			this.pushZ = nBTTagCompound1.getDouble("PushZ");
			this.fuel = nBTTagCompound1.getShort("Fuel");
		} else if(this.minecartType == 1) {
			NBTTagList nBTTagList2 = nBTTagCompound1.getTagList("Items");
			this.cargoItems = new ItemStack[this.getSizeInventory()];

			for(int i3 = 0; i3 < nBTTagList2.tagCount(); ++i3) {
				NBTTagCompound nBTTagCompound4 = (NBTTagCompound)nBTTagList2.tagAt(i3);
				int i5 = nBTTagCompound4.getByte("Slot") & 255;
				if(i5 >= 0 && i5 < this.cargoItems.length) {
					this.cargoItems[i5] = new ItemStack(nBTTagCompound4);
				}
			}
		}

	}

	public float getShadowSize() {
		return 0.0F;
	}

	public void applyEntityCollision(Entity entity1) {
		if(!this.worldObj.multiplayerWorld) {
			if(entity1 != this.riddenByEntity) {
				if(entity1 instanceof EntityLiving && !(entity1 instanceof EntityPlayer) && this.minecartType == 0 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D && this.riddenByEntity == null && entity1.ridingEntity == null) {
					entity1.mountEntity(this);
				}

				double d2 = entity1.posX - this.posX;
				double d4 = entity1.posZ - this.posZ;
				double d6 = d2 * d2 + d4 * d4;
				if(d6 >= 9.999999747378752E-5D) {
					d6 = (double)MathHelper.sqrt_double(d6);
					d2 /= d6;
					d4 /= d6;
					double d8 = 1.0D / d6;
					if(d8 > 1.0D) {
						d8 = 1.0D;
					}

					d2 *= d8;
					d4 *= d8;
					d2 *= (double)0.1F;
					d4 *= (double)0.1F;
					d2 *= (double)(1.0F - this.entityCollisionReduction);
					d4 *= (double)(1.0F - this.entityCollisionReduction);
					d2 *= 0.5D;
					d4 *= 0.5D;
					if(entity1 instanceof EntityMinecart) {
						double d10 = entity1.posX - this.posX;
						double d12 = entity1.posZ - this.posZ;
						double d14 = d10 * entity1.motionZ + d12 * entity1.prevPosX;
						d14 *= d14;
						if(d14 > 5.0D) {
							return;
						}

						double d16 = entity1.motionX + this.motionX;
						double d18 = entity1.motionZ + this.motionZ;
						if(((EntityMinecart)entity1).minecartType == 2 && this.minecartType != 2) {
							this.motionX *= (double)0.2F;
							this.motionZ *= (double)0.2F;
							this.addVelocity(entity1.motionX - d2, 0.0D, entity1.motionZ - d4);
							entity1.motionX *= (double)0.7F;
							entity1.motionZ *= (double)0.7F;
						} else if(((EntityMinecart)entity1).minecartType != 2 && this.minecartType == 2) {
							entity1.motionX *= (double)0.2F;
							entity1.motionZ *= (double)0.2F;
							entity1.addVelocity(this.motionX + d2, 0.0D, this.motionZ + d4);
							this.motionX *= (double)0.7F;
							this.motionZ *= (double)0.7F;
						} else {
							d16 /= 2.0D;
							d18 /= 2.0D;
							this.motionX *= (double)0.2F;
							this.motionZ *= (double)0.2F;
							this.addVelocity(d16 - d2, 0.0D, d18 - d4);
							entity1.motionX *= (double)0.2F;
							entity1.motionZ *= (double)0.2F;
							entity1.addVelocity(d16 + d2, 0.0D, d18 + d4);
						}
					} else {
						this.addVelocity(-d2, 0.0D, -d4);
						entity1.addVelocity(d2 / 4.0D, 0.0D, d4 / 4.0D);
					}
				}

			}
		}
	}

	public int getSizeInventory() {
		return 27;
	}

	public ItemStack getStackInSlot(int i1) {
		return this.cargoItems[i1];
	}

	public ItemStack decrStackSize(int i1, int i2) {
		if(this.cargoItems[i1] != null) {
			ItemStack itemStack3;
			if(this.cargoItems[i1].stackSize <= i2) {
				itemStack3 = this.cargoItems[i1];
				this.cargoItems[i1] = null;
				return itemStack3;
			} else {
				itemStack3 = this.cargoItems[i1].splitStack(i2);
				if(this.cargoItems[i1].stackSize == 0) {
					this.cargoItems[i1] = null;
				}

				return itemStack3;
			}
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i1, ItemStack itemStack2) {
		this.cargoItems[i1] = itemStack2;
		if(itemStack2 != null && itemStack2.stackSize > this.getInventoryStackLimit()) {
			itemStack2.stackSize = this.getInventoryStackLimit();
		}

	}

	public String getInvName() {
		return "Minecart";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void onInventoryChanged() {
	}

	public boolean interact(EntityPlayer entityPlayer1) {
		if(this.minecartType == 0) {
			if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != entityPlayer1) {
				return true;
			}

			if(!this.worldObj.multiplayerWorld) {
				entityPlayer1.mountEntity(this);
			}
		} else if(this.minecartType == 1) {
			if(!this.worldObj.multiplayerWorld) {
				entityPlayer1.displayGUIChest(this);
			}
		} else if(this.minecartType == 2) {
			ItemStack itemStack2 = entityPlayer1.inventory.getCurrentItem();
			if(itemStack2 != null && itemStack2.itemID == Item.coal.shiftedIndex) {
				if(--itemStack2.stackSize == 0) {
					entityPlayer1.inventory.setInventorySlotContents(entityPlayer1.inventory.currentItem, (ItemStack)null);
				}

				this.fuel += 1200;
			}

			this.pushX = this.posX - entityPlayer1.posX;
			this.pushZ = this.posZ - entityPlayer1.posZ;
		}

		return true;
	}

	public void setPositionAndRotation2(double d1, double d3, double d5, float f7, float f8, int i9) {
		this.field_9414_l = d1;
		this.field_9413_m = d3;
		this.field_9412_n = d5;
		this.field_9411_o = (double)f7;
		this.field_9410_p = (double)f8;
		this.field_9415_k = i9 + 2;
		this.motionX = this.field_9409_q;
		this.motionY = this.field_9408_r;
		this.motionZ = this.field_9407_s;
	}

	public void setVelocity(double d1, double d3, double d5) {
		this.field_9409_q = this.motionX = d1;
		this.field_9408_r = this.motionY = d3;
		this.field_9407_s = this.motionZ = d5;
	}

	public boolean canInteractWith(EntityPlayer entityPlayer1) {
		return this.isDead ? false : entityPlayer1.getDistanceSqToEntity(this) <= 64.0D;
	}
}
