package net.minecraft.src;

import java.util.Random;

public class WorldGenClay extends WorldGenerator {
	private int clayBlockId = Block.blockClay.blockID;
	private int numberOfBlocks;

	public WorldGenClay(int i1) {
		this.numberOfBlocks = i1;
	}

	public boolean generate(World world1, Random random2, int i3, int i4, int i5) {
		if(world1.getBlockMaterial(i3, i4, i5) != Material.water) {
			return false;
		} else {
			float f6 = random2.nextFloat() * (float)Math.PI;
			double d7 = (double)((float)(i3 + 8) + MathHelper.sin(f6) * (float)this.numberOfBlocks / 8.0F);
			double d9 = (double)((float)(i3 + 8) - MathHelper.sin(f6) * (float)this.numberOfBlocks / 8.0F);
			double d11 = (double)((float)(i5 + 8) + MathHelper.cos(f6) * (float)this.numberOfBlocks / 8.0F);
			double d13 = (double)((float)(i5 + 8) - MathHelper.cos(f6) * (float)this.numberOfBlocks / 8.0F);
			double d15 = (double)(i4 + random2.nextInt(3) + 2);
			double d17 = (double)(i4 + random2.nextInt(3) + 2);

			for(int i19 = 0; i19 <= this.numberOfBlocks; ++i19) {
				double d20 = d7 + (d9 - d7) * (double)i19 / (double)this.numberOfBlocks;
				double d22 = d15 + (d17 - d15) * (double)i19 / (double)this.numberOfBlocks;
				double d24 = d11 + (d13 - d11) * (double)i19 / (double)this.numberOfBlocks;
				double d26 = random2.nextDouble() * (double)this.numberOfBlocks / 16.0D;
				double d28 = (double)(MathHelper.sin((float)i19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d26 + 1.0D;
				double d30 = (double)(MathHelper.sin((float)i19 * (float)Math.PI / (float)this.numberOfBlocks) + 1.0F) * d26 + 1.0D;
				int i32 = MathHelper.floor_double(d20 - d28 / 2.0D);
				int i33 = MathHelper.floor_double(d20 + d28 / 2.0D);
				int i34 = MathHelper.floor_double(d22 - d30 / 2.0D);
				int i35 = MathHelper.floor_double(d22 + d30 / 2.0D);
				int i36 = MathHelper.floor_double(d24 - d28 / 2.0D);
				int i37 = MathHelper.floor_double(d24 + d28 / 2.0D);

				for(int i38 = i32; i38 <= i33; ++i38) {
					for(int i39 = i34; i39 <= i35; ++i39) {
						for(int i40 = i36; i40 <= i37; ++i40) {
							double d41 = ((double)i38 + 0.5D - d20) / (d28 / 2.0D);
							double d43 = ((double)i39 + 0.5D - d22) / (d30 / 2.0D);
							double d45 = ((double)i40 + 0.5D - d24) / (d28 / 2.0D);
							if(d41 * d41 + d43 * d43 + d45 * d45 < 1.0D) {
								int i47 = world1.getBlockId(i38, i39, i40);
								if(i47 == Block.sand.blockID) {
									world1.setBlock(i38, i39, i40, this.clayBlockId);
								}
							}
						}
					}
				}
			}

			return true;
		}
	}
}
