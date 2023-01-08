package net.minecraft.src;

import java.util.Random;

public class Teleporter {
	private Random field_4232_a = new Random();

	public void func_4107_a(World world1, Entity entity2) {
		if(!this.func_4106_b(world1, entity2)) {
			this.func_4108_c(world1, entity2);
			this.func_4106_b(world1, entity2);
		}
	}

	public boolean func_4106_b(World world1, Entity entity2) {
		short s3 = 128;
		double d4 = -1.0D;
		int i6 = 0;
		int i7 = 0;
		int i8 = 0;
		int i9 = MathHelper.floor_double(entity2.posX);
		int i10 = MathHelper.floor_double(entity2.posZ);

		double d18;
		for(int i11 = i9 - s3; i11 <= i9 + s3; ++i11) {
			double d12 = (double)i11 + 0.5D - entity2.posX;

			for(int i14 = i10 - s3; i14 <= i10 + s3; ++i14) {
				double d15 = (double)i14 + 0.5D - entity2.posZ;

				for(int i17 = 127; i17 >= 0; --i17) {
					if(world1.getBlockId(i11, i17, i14) == Block.portal.blockID) {
						while(world1.getBlockId(i11, i17 - 1, i14) == Block.portal.blockID) {
							--i17;
						}

						d18 = (double)i17 + 0.5D - entity2.posY;
						double d20 = d12 * d12 + d18 * d18 + d15 * d15;
						if(d4 < 0.0D || d20 < d4) {
							d4 = d20;
							i6 = i11;
							i7 = i17;
							i8 = i14;
						}
					}
				}
			}
		}

		if(d4 >= 0.0D) {
			double d22 = (double)i6 + 0.5D;
			double d16 = (double)i7 + 0.5D;
			d18 = (double)i8 + 0.5D;
			if(world1.getBlockId(i6 - 1, i7, i8) == Block.portal.blockID) {
				d22 -= 0.5D;
			}

			if(world1.getBlockId(i6 + 1, i7, i8) == Block.portal.blockID) {
				d22 += 0.5D;
			}

			if(world1.getBlockId(i6, i7, i8 - 1) == Block.portal.blockID) {
				d18 -= 0.5D;
			}

			if(world1.getBlockId(i6, i7, i8 + 1) == Block.portal.blockID) {
				d18 += 0.5D;
			}

			entity2.setLocationAndAngles(d22, d16, d18, entity2.rotationYaw, 0.0F);
			entity2.motionX = entity2.motionY = entity2.motionZ = 0.0D;
			return true;
		} else {
			return false;
		}
	}

	public boolean func_4108_c(World world1, Entity entity2) {
		byte b3 = 16;
		double d4 = -1.0D;
		int i6 = MathHelper.floor_double(entity2.posX);
		int i7 = MathHelper.floor_double(entity2.posY);
		int i8 = MathHelper.floor_double(entity2.posZ);
		int i9 = i6;
		int i10 = i7;
		int i11 = i8;
		int i12 = 0;
		int i13 = this.field_4232_a.nextInt(4);

		int i14;
		double d15;
		int i17;
		double d18;
		int i20;
		int i21;
		int i22;
		int i23;
		int i24;
		int i25;
		int i26;
		int i27;
		int i28;
		double d32;
		double d33;
		for(i14 = i6 - b3; i14 <= i6 + b3; ++i14) {
			d15 = (double)i14 + 0.5D - entity2.posX;

			for(i17 = i8 - b3; i17 <= i8 + b3; ++i17) {
				d18 = (double)i17 + 0.5D - entity2.posZ;

				label293:
				for(i20 = 127; i20 >= 0; --i20) {
					if(world1.isAirBlock(i14, i20, i17)) {
						while(i20 > 0 && world1.isAirBlock(i14, i20 - 1, i17)) {
							--i20;
						}

						for(i21 = i13; i21 < i13 + 4; ++i21) {
							i22 = i21 % 2;
							i23 = 1 - i22;
							if(i21 % 4 >= 2) {
								i22 = -i22;
								i23 = -i23;
							}

							for(i24 = 0; i24 < 3; ++i24) {
								for(i25 = 0; i25 < 4; ++i25) {
									for(i26 = -1; i26 < 4; ++i26) {
										i27 = i14 + (i25 - 1) * i22 + i24 * i23;
										i28 = i20 + i26;
										int i29 = i17 + (i25 - 1) * i23 - i24 * i22;
										if(i26 < 0 && !world1.getBlockMaterial(i27, i28, i29).isSolid() || i26 >= 0 && !world1.isAirBlock(i27, i28, i29)) {
											continue label293;
										}
									}
								}
							}

							d32 = (double)i20 + 0.5D - entity2.posY;
							d33 = d15 * d15 + d32 * d32 + d18 * d18;
							if(d4 < 0.0D || d33 < d4) {
								d4 = d33;
								i9 = i14;
								i10 = i20;
								i11 = i17;
								i12 = i21 % 4;
							}
						}
					}
				}
			}
		}

		if(d4 < 0.0D) {
			for(i14 = i6 - b3; i14 <= i6 + b3; ++i14) {
				d15 = (double)i14 + 0.5D - entity2.posX;

				for(i17 = i8 - b3; i17 <= i8 + b3; ++i17) {
					d18 = (double)i17 + 0.5D - entity2.posZ;

					label231:
					for(i20 = 127; i20 >= 0; --i20) {
						if(world1.isAirBlock(i14, i20, i17)) {
							while(world1.isAirBlock(i14, i20 - 1, i17)) {
								--i20;
							}

							for(i21 = i13; i21 < i13 + 2; ++i21) {
								i22 = i21 % 2;
								i23 = 1 - i22;

								for(i24 = 0; i24 < 4; ++i24) {
									for(i25 = -1; i25 < 4; ++i25) {
										i26 = i14 + (i24 - 1) * i22;
										i27 = i20 + i25;
										i28 = i17 + (i24 - 1) * i23;
										if(i25 < 0 && !world1.getBlockMaterial(i26, i27, i28).isSolid() || i25 >= 0 && !world1.isAirBlock(i26, i27, i28)) {
											continue label231;
										}
									}
								}

								d32 = (double)i20 + 0.5D - entity2.posY;
								d33 = d15 * d15 + d32 * d32 + d18 * d18;
								if(d4 < 0.0D || d33 < d4) {
									d4 = d33;
									i9 = i14;
									i10 = i20;
									i11 = i17;
									i12 = i21 % 2;
								}
							}
						}
					}
				}
			}
		}

		int i30 = i9;
		int i16 = i10;
		i17 = i11;
		int i31 = i12 % 2;
		int i19 = 1 - i31;
		if(i12 % 4 >= 2) {
			i31 = -i31;
			i19 = -i19;
		}

		boolean z34;
		if(d4 < 0.0D) {
			if(i10 < 70) {
				i10 = 70;
			}

			if(i10 > 118) {
				i10 = 118;
			}

			i16 = i10;

			for(i20 = -1; i20 <= 1; ++i20) {
				for(i21 = 1; i21 < 3; ++i21) {
					for(i22 = -1; i22 < 3; ++i22) {
						i23 = i30 + (i21 - 1) * i31 + i20 * i19;
						i24 = i16 + i22;
						i25 = i17 + (i21 - 1) * i19 - i20 * i31;
						z34 = i22 < 0;
						world1.setBlockWithNotify(i23, i24, i25, z34 ? Block.obsidian.blockID : 0);
					}
				}
			}
		}

		for(i20 = 0; i20 < 4; ++i20) {
			world1.editingBlocks = true;

			for(i21 = 0; i21 < 4; ++i21) {
				for(i22 = -1; i22 < 4; ++i22) {
					i23 = i30 + (i21 - 1) * i31;
					i24 = i16 + i22;
					i25 = i17 + (i21 - 1) * i19;
					z34 = i21 == 0 || i21 == 3 || i22 == -1 || i22 == 3;
					world1.setBlockWithNotify(i23, i24, i25, z34 ? Block.obsidian.blockID : Block.portal.blockID);
				}
			}

			world1.editingBlocks = false;

			for(i21 = 0; i21 < 4; ++i21) {
				for(i22 = -1; i22 < 4; ++i22) {
					i23 = i30 + (i21 - 1) * i31;
					i24 = i16 + i22;
					i25 = i17 + (i21 - 1) * i19;
					world1.notifyBlocksOfNeighborChange(i23, i24, i25, world1.getBlockId(i23, i24, i25));
				}
			}
		}

		return true;
	}
}
