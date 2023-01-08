package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Explosion {
	public boolean isFlaming = false;
	private Random ExplosionRNG = new Random();
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;
	public Set destroyedBlockPositions = new HashSet();

	public Explosion(World world1, Entity entity2, double d3, double d5, double d7, float f9) {
		this.worldObj = world1;
		this.exploder = entity2;
		this.explosionSize = f9;
		this.explosionX = d3;
		this.explosionY = d5;
		this.explosionZ = d7;
	}

	public void doExplosionA() {
		float f1 = this.explosionSize;
		byte b2 = 16;

		int i3;
		int i4;
		int i5;
		double d15;
		double d17;
		double d19;
		for(i3 = 0; i3 < b2; ++i3) {
			for(i4 = 0; i4 < b2; ++i4) {
				for(i5 = 0; i5 < b2; ++i5) {
					if(i3 == 0 || i3 == b2 - 1 || i4 == 0 || i4 == b2 - 1 || i5 == 0 || i5 == b2 - 1) {
						double d6 = (double)((float)i3 / ((float)b2 - 1.0F) * 2.0F - 1.0F);
						double d8 = (double)((float)i4 / ((float)b2 - 1.0F) * 2.0F - 1.0F);
						double d10 = (double)((float)i5 / ((float)b2 - 1.0F) * 2.0F - 1.0F);
						double d12 = Math.sqrt(d6 * d6 + d8 * d8 + d10 * d10);
						d6 /= d12;
						d8 /= d12;
						d10 /= d12;
						float f14 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
						d15 = this.explosionX;
						d17 = this.explosionY;
						d19 = this.explosionZ;

						for(float f21 = 0.3F; f14 > 0.0F; f14 -= f21 * 0.75F) {
							int i22 = MathHelper.floor_double(d15);
							int i23 = MathHelper.floor_double(d17);
							int i24 = MathHelper.floor_double(d19);
							int i25 = this.worldObj.getBlockId(i22, i23, i24);
							if(i25 > 0) {
								f14 -= (Block.blocksList[i25].getExplosionResistance(this.exploder) + 0.3F) * f21;
							}

							if(f14 > 0.0F) {
								this.destroyedBlockPositions.add(new ChunkPosition(i22, i23, i24));
							}

							d15 += d6 * (double)f21;
							d17 += d8 * (double)f21;
							d19 += d10 * (double)f21;
						}
					}
				}
			}
		}

		this.explosionSize *= 2.0F;
		i3 = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
		i4 = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
		i5 = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
		int i29 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
		int i7 = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
		int i30 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
		List list9 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBoxFromPool((double)i3, (double)i5, (double)i7, (double)i4, (double)i29, (double)i30));
		Vec3D vec3D31 = Vec3D.createVector(this.explosionX, this.explosionY, this.explosionZ);

		for(int i11 = 0; i11 < list9.size(); ++i11) {
			Entity entity33 = (Entity)list9.get(i11);
			double d13 = entity33.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;
			if(d13 <= 1.0D) {
				d15 = entity33.posX - this.explosionX;
				d17 = entity33.posY - this.explosionY;
				d19 = entity33.posZ - this.explosionZ;
				double d39 = (double)MathHelper.sqrt_double(d15 * d15 + d17 * d17 + d19 * d19);
				d15 /= d39;
				d17 /= d39;
				d19 /= d39;
				double d40 = (double)this.worldObj.func_675_a(vec3D31, entity33.boundingBox);
				double d41 = (1.0D - d13) * d40;
				entity33.attackEntityFrom(this.exploder, (int)((d41 * d41 + d41) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D));
				entity33.motionX += d15 * d41;
				entity33.motionY += d17 * d41;
				entity33.motionZ += d19 * d41;
			}
		}

		this.explosionSize = f1;
		ArrayList arrayList32 = new ArrayList();
		arrayList32.addAll(this.destroyedBlockPositions);
		if(this.isFlaming) {
			for(int i34 = arrayList32.size() - 1; i34 >= 0; --i34) {
				ChunkPosition chunkPosition35 = (ChunkPosition)arrayList32.get(i34);
				int i36 = chunkPosition35.x;
				int i37 = chunkPosition35.y;
				int i16 = chunkPosition35.z;
				int i38 = this.worldObj.getBlockId(i36, i37, i16);
				int i18 = this.worldObj.getBlockId(i36, i37 - 1, i16);
				if(i38 == 0 && Block.opaqueCubeLookup[i18] && this.ExplosionRNG.nextInt(3) == 0) {
					this.worldObj.setBlockWithNotify(i36, i37, i16, Block.fire.blockID);
				}
			}
		}

	}

	public void doExplosionB(boolean z1) {
		this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		ArrayList arrayList2 = new ArrayList();
		arrayList2.addAll(this.destroyedBlockPositions);

		for(int i3 = arrayList2.size() - 1; i3 >= 0; --i3) {
			ChunkPosition chunkPosition4 = (ChunkPosition)arrayList2.get(i3);
			int i5 = chunkPosition4.x;
			int i6 = chunkPosition4.y;
			int i7 = chunkPosition4.z;
			int i8 = this.worldObj.getBlockId(i5, i6, i7);
			if(z1) {
				double d9 = (double)((float)i5 + this.worldObj.rand.nextFloat());
				double d11 = (double)((float)i6 + this.worldObj.rand.nextFloat());
				double d13 = (double)((float)i7 + this.worldObj.rand.nextFloat());
				double d15 = d9 - this.explosionX;
				double d17 = d11 - this.explosionY;
				double d19 = d13 - this.explosionZ;
				double d21 = (double)MathHelper.sqrt_double(d15 * d15 + d17 * d17 + d19 * d19);
				d15 /= d21;
				d17 /= d21;
				d19 /= d21;
				double d23 = 0.5D / (d21 / (double)this.explosionSize + 0.1D);
				d23 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
				d15 *= d23;
				d17 *= d23;
				d19 *= d23;
				this.worldObj.spawnParticle("explode", (d9 + this.explosionX * 1.0D) / 2.0D, (d11 + this.explosionY * 1.0D) / 2.0D, (d13 + this.explosionZ * 1.0D) / 2.0D, d15, d17, d19);
				this.worldObj.spawnParticle("smoke", d9, d11, d13, d15, d17, d19);
			}

			if(i8 > 0) {
				Block.blocksList[i8].dropBlockAsItemWithChance(this.worldObj, i5, i6, i7, this.worldObj.getBlockMetadata(i5, i6, i7), 0.3F);
				this.worldObj.setBlockWithNotify(i5, i6, i7, 0);
				Block.blocksList[i8].onBlockDestroyedByExplosion(this.worldObj, i5, i6, i7);
			}
		}

	}
}
