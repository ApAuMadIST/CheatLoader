package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileEntityPiston extends TileEntity {
	private int storedBlockID;
	private int storedMetadata;
	private int field_31025_c;
	private boolean field_31024_i;
	private boolean field_31023_j;
	private float field_31022_k;
	private float field_31020_l;
	private static List field_31018_m = new ArrayList();

	public TileEntityPiston() {
	}

	public TileEntityPiston(int i1, int i2, int i3, boolean z4, boolean z5) {
		this.storedBlockID = i1;
		this.storedMetadata = i2;
		this.field_31025_c = i3;
		this.field_31024_i = z4;
		this.field_31023_j = z5;
	}

	public int getStoredBlockID() {
		return this.storedBlockID;
	}

	public int getBlockMetadata() {
		return this.storedMetadata;
	}

	public boolean func_31015_b() {
		return this.field_31024_i;
	}

	public int func_31009_d() {
		return this.field_31025_c;
	}

	public boolean func_31012_k() {
		return this.field_31023_j;
	}

	public float func_31008_a(float f1) {
		if(f1 > 1.0F) {
			f1 = 1.0F;
		}

		return this.field_31020_l + (this.field_31022_k - this.field_31020_l) * f1;
	}

	public float func_31017_b(float f1) {
		return this.field_31024_i ? (this.func_31008_a(f1) - 1.0F) * (float)PistonBlockTextures.field_31056_b[this.field_31025_c] : (1.0F - this.func_31008_a(f1)) * (float)PistonBlockTextures.field_31056_b[this.field_31025_c];
	}

	public float func_31014_c(float f1) {
		return this.field_31024_i ? (this.func_31008_a(f1) - 1.0F) * (float)PistonBlockTextures.field_31059_c[this.field_31025_c] : (1.0F - this.func_31008_a(f1)) * (float)PistonBlockTextures.field_31059_c[this.field_31025_c];
	}

	public float func_31013_d(float f1) {
		return this.field_31024_i ? (this.func_31008_a(f1) - 1.0F) * (float)PistonBlockTextures.field_31058_d[this.field_31025_c] : (1.0F - this.func_31008_a(f1)) * (float)PistonBlockTextures.field_31058_d[this.field_31025_c];
	}

	private void func_31010_a(float f1, float f2) {
		if(!this.field_31024_i) {
			--f1;
		} else {
			f1 = 1.0F - f1;
		}

		AxisAlignedBB axisAlignedBB3 = Block.pistonMoving.func_31035_a(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.storedBlockID, f1, this.field_31025_c);
		if(axisAlignedBB3 != null) {
			List list4 = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)null, axisAlignedBB3);
			if(!list4.isEmpty()) {
				field_31018_m.addAll(list4);
				Iterator iterator5 = field_31018_m.iterator();

				while(iterator5.hasNext()) {
					Entity entity6 = (Entity)iterator5.next();
					entity6.moveEntity((double)(f2 * (float)PistonBlockTextures.field_31056_b[this.field_31025_c]), (double)(f2 * (float)PistonBlockTextures.field_31059_c[this.field_31025_c]), (double)(f2 * (float)PistonBlockTextures.field_31058_d[this.field_31025_c]));
				}

				field_31018_m.clear();
			}
		}

	}

	public void func_31011_l() {
		if(this.field_31020_l < 1.0F) {
			this.field_31020_l = this.field_31022_k = 1.0F;
			this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
			this.func_31005_i();
			if(this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID) {
				this.worldObj.setBlockAndMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID, this.storedMetadata);
			}
		}

	}

	public void updateEntity() {
		this.field_31020_l = this.field_31022_k;
		if(this.field_31020_l >= 1.0F) {
			this.func_31010_a(1.0F, 0.25F);
			this.worldObj.removeBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
			this.func_31005_i();
			if(this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) == Block.pistonMoving.blockID) {
				this.worldObj.setBlockAndMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.storedBlockID, this.storedMetadata);
			}

		} else {
			this.field_31022_k += 0.5F;
			if(this.field_31022_k >= 1.0F) {
				this.field_31022_k = 1.0F;
			}

			if(this.field_31024_i) {
				this.func_31010_a(this.field_31022_k, this.field_31022_k - this.field_31020_l + 0.0625F);
			}

		}
	}

	public void readFromNBT(NBTTagCompound nBTTagCompound1) {
		super.readFromNBT(nBTTagCompound1);
		this.storedBlockID = nBTTagCompound1.getInteger("blockId");
		this.storedMetadata = nBTTagCompound1.getInteger("blockData");
		this.field_31025_c = nBTTagCompound1.getInteger("facing");
		this.field_31020_l = this.field_31022_k = nBTTagCompound1.getFloat("progress");
		this.field_31024_i = nBTTagCompound1.getBoolean("extending");
	}

	public void writeToNBT(NBTTagCompound nBTTagCompound1) {
		super.writeToNBT(nBTTagCompound1);
		nBTTagCompound1.setInteger("blockId", this.storedBlockID);
		nBTTagCompound1.setInteger("blockData", this.storedMetadata);
		nBTTagCompound1.setInteger("facing", this.field_31025_c);
		nBTTagCompound1.setFloat("progress", this.field_31020_l);
		nBTTagCompound1.setBoolean("extending", this.field_31024_i);
	}
}
