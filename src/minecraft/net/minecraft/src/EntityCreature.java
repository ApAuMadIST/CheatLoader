package net.minecraft.src;

public class EntityCreature extends EntityLiving {
	private PathEntity pathToEntity;
	protected Entity playerToAttack;
	protected boolean hasAttacked = false;

	public EntityCreature(World world1) {
		super(world1);
	}

	protected boolean isMovementCeased() {
		return false;
	}

	protected void updatePlayerActionState() {
		this.hasAttacked = this.isMovementCeased();
		float f1 = 16.0F;
		if(this.playerToAttack == null) {
			this.playerToAttack = this.findPlayerToAttack();
			if(this.playerToAttack != null) {
				this.pathToEntity = this.worldObj.getPathToEntity(this, this.playerToAttack, f1);
			}
		} else if(!this.playerToAttack.isEntityAlive()) {
			this.playerToAttack = null;
		} else {
			float f2 = this.playerToAttack.getDistanceToEntity(this);
			if(this.canEntityBeSeen(this.playerToAttack)) {
				this.attackEntity(this.playerToAttack, f2);
			} else {
				this.attackBlockedEntity(this.playerToAttack, f2);
			}
		}

		if(this.hasAttacked || this.playerToAttack == null || this.pathToEntity != null && this.rand.nextInt(20) != 0) {
			if(!this.hasAttacked && (this.pathToEntity == null && this.rand.nextInt(80) == 0 || this.rand.nextInt(80) == 0)) {
				this.func_31026_E();
			}
		} else {
			this.pathToEntity = this.worldObj.getPathToEntity(this, this.playerToAttack, f1);
		}

		int i21 = MathHelper.floor_double(this.boundingBox.minY + 0.5D);
		boolean z3 = this.isInWater();
		boolean z4 = this.handleLavaMovement();
		this.rotationPitch = 0.0F;
		if(this.pathToEntity != null && this.rand.nextInt(100) != 0) {
			Vec3D vec3D5 = this.pathToEntity.getPosition(this);
			double d6 = (double)(this.width * 2.0F);

			while(vec3D5 != null && vec3D5.squareDistanceTo(this.posX, vec3D5.yCoord, this.posZ) < d6 * d6) {
				this.pathToEntity.incrementPathIndex();
				if(this.pathToEntity.isFinished()) {
					vec3D5 = null;
					this.pathToEntity = null;
				} else {
					vec3D5 = this.pathToEntity.getPosition(this);
				}
			}

			this.isJumping = false;
			if(vec3D5 != null) {
				double d8 = vec3D5.xCoord - this.posX;
				double d10 = vec3D5.zCoord - this.posZ;
				double d12 = vec3D5.yCoord - (double)i21;
				float f14 = (float)(Math.atan2(d10, d8) * 180.0D / (double)(float)Math.PI) - 90.0F;
				float f15 = f14 - this.rotationYaw;

				for(this.moveForward = this.moveSpeed; f15 < -180.0F; f15 += 360.0F) {
				}

				while(f15 >= 180.0F) {
					f15 -= 360.0F;
				}

				if(f15 > 30.0F) {
					f15 = 30.0F;
				}

				if(f15 < -30.0F) {
					f15 = -30.0F;
				}

				this.rotationYaw += f15;
				if(this.hasAttacked && this.playerToAttack != null) {
					double d16 = this.playerToAttack.posX - this.posX;
					double d18 = this.playerToAttack.posZ - this.posZ;
					float f20 = this.rotationYaw;
					this.rotationYaw = (float)(Math.atan2(d18, d16) * 180.0D / (double)(float)Math.PI) - 90.0F;
					f15 = (f20 - this.rotationYaw + 90.0F) * (float)Math.PI / 180.0F;
					this.moveStrafing = -MathHelper.sin(f15) * this.moveForward * 1.0F;
					this.moveForward = MathHelper.cos(f15) * this.moveForward * 1.0F;
				}

				if(d12 > 0.0D) {
					this.isJumping = true;
				}
			}

			if(this.playerToAttack != null) {
				this.faceEntity(this.playerToAttack, 30.0F, 30.0F);
			}

			if(this.isCollidedHorizontally && !this.hasPath()) {
				this.isJumping = true;
			}

			if(this.rand.nextFloat() < 0.8F && (z3 || z4)) {
				this.isJumping = true;
			}

		} else {
			super.updatePlayerActionState();
			this.pathToEntity = null;
		}
	}

	protected void func_31026_E() {
		boolean z1 = false;
		int i2 = -1;
		int i3 = -1;
		int i4 = -1;
		float f5 = -99999.0F;

		for(int i6 = 0; i6 < 10; ++i6) {
			int i7 = MathHelper.floor_double(this.posX + (double)this.rand.nextInt(13) - 6.0D);
			int i8 = MathHelper.floor_double(this.posY + (double)this.rand.nextInt(7) - 3.0D);
			int i9 = MathHelper.floor_double(this.posZ + (double)this.rand.nextInt(13) - 6.0D);
			float f10 = this.getBlockPathWeight(i7, i8, i9);
			if(f10 > f5) {
				f5 = f10;
				i2 = i7;
				i3 = i8;
				i4 = i9;
				z1 = true;
			}
		}

		if(z1) {
			this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, i2, i3, i4, 10.0F);
		}

	}

	protected void attackEntity(Entity entity1, float f2) {
	}

	protected void attackBlockedEntity(Entity entity1, float f2) {
	}

	protected float getBlockPathWeight(int i1, int i2, int i3) {
		return 0.0F;
	}

	protected Entity findPlayerToAttack() {
		return null;
	}

	public boolean getCanSpawnHere() {
		int i1 = MathHelper.floor_double(this.posX);
		int i2 = MathHelper.floor_double(this.boundingBox.minY);
		int i3 = MathHelper.floor_double(this.posZ);
		return super.getCanSpawnHere() && this.getBlockPathWeight(i1, i2, i3) >= 0.0F;
	}

	public boolean hasPath() {
		return this.pathToEntity != null;
	}

	public void setPathToEntity(PathEntity pathEntity1) {
		this.pathToEntity = pathEntity1;
	}

	public Entity getTarget() {
		return this.playerToAttack;
	}

	public void setTarget(Entity entity1) {
		this.playerToAttack = entity1;
	}
}
