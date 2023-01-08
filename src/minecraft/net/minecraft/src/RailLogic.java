package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

class RailLogic {
	private World worldObj;
	private int trackX;
	private int trackY;
	private int trackZ;
	private final boolean isPoweredRail;
	private List connectedTracks;
	final BlockRail rail;

	public RailLogic(BlockRail blockRail1, World world2, int i3, int i4, int i5) {
		this.rail = blockRail1;
		this.connectedTracks = new ArrayList();
		this.worldObj = world2;
		this.trackX = i3;
		this.trackY = i4;
		this.trackZ = i5;
		int i6 = world2.getBlockId(i3, i4, i5);
		int i7 = world2.getBlockMetadata(i3, i4, i5);
		if(BlockRail.isPoweredBlockRail((BlockRail)Block.blocksList[i6])) {
			this.isPoweredRail = true;
			i7 &= -9;
		} else {
			this.isPoweredRail = false;
		}

		this.setConnections(i7);
	}

	private void setConnections(int i1) {
		this.connectedTracks.clear();
		if(i1 == 0) {
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
		} else if(i1 == 1) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
		} else if(i1 == 2) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY + 1, this.trackZ));
		} else if(i1 == 3) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY + 1, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
		} else if(i1 == 4) {
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ - 1));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
		} else if(i1 == 5) {
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY + 1, this.trackZ + 1));
		} else if(i1 == 6) {
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
		} else if(i1 == 7) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ + 1));
		} else if(i1 == 8) {
			this.connectedTracks.add(new ChunkPosition(this.trackX - 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
		} else if(i1 == 9) {
			this.connectedTracks.add(new ChunkPosition(this.trackX + 1, this.trackY, this.trackZ));
			this.connectedTracks.add(new ChunkPosition(this.trackX, this.trackY, this.trackZ - 1));
		}

	}

	private void func_785_b() {
		for(int i1 = 0; i1 < this.connectedTracks.size(); ++i1) {
			RailLogic railLogic2 = this.getMinecartTrackLogic((ChunkPosition)this.connectedTracks.get(i1));
			if(railLogic2 != null && railLogic2.isConnectedTo(this)) {
				this.connectedTracks.set(i1, new ChunkPosition(railLogic2.trackX, railLogic2.trackY, railLogic2.trackZ));
			} else {
				this.connectedTracks.remove(i1--);
			}
		}

	}

	private boolean isMinecartTrack(int i1, int i2, int i3) {
		return BlockRail.isRailBlockAt(this.worldObj, i1, i2, i3) ? true : (BlockRail.isRailBlockAt(this.worldObj, i1, i2 + 1, i3) ? true : BlockRail.isRailBlockAt(this.worldObj, i1, i2 - 1, i3));
	}

	private RailLogic getMinecartTrackLogic(ChunkPosition chunkPosition1) {
		return BlockRail.isRailBlockAt(this.worldObj, chunkPosition1.x, chunkPosition1.y, chunkPosition1.z) ? new RailLogic(this.rail, this.worldObj, chunkPosition1.x, chunkPosition1.y, chunkPosition1.z) : (BlockRail.isRailBlockAt(this.worldObj, chunkPosition1.x, chunkPosition1.y + 1, chunkPosition1.z) ? new RailLogic(this.rail, this.worldObj, chunkPosition1.x, chunkPosition1.y + 1, chunkPosition1.z) : (BlockRail.isRailBlockAt(this.worldObj, chunkPosition1.x, chunkPosition1.y - 1, chunkPosition1.z) ? new RailLogic(this.rail, this.worldObj, chunkPosition1.x, chunkPosition1.y - 1, chunkPosition1.z) : null));
	}

	private boolean isConnectedTo(RailLogic railLogic1) {
		for(int i2 = 0; i2 < this.connectedTracks.size(); ++i2) {
			ChunkPosition chunkPosition3 = (ChunkPosition)this.connectedTracks.get(i2);
			if(chunkPosition3.x == railLogic1.trackX && chunkPosition3.z == railLogic1.trackZ) {
				return true;
			}
		}

		return false;
	}

	private boolean isInTrack(int i1, int i2, int i3) {
		for(int i4 = 0; i4 < this.connectedTracks.size(); ++i4) {
			ChunkPosition chunkPosition5 = (ChunkPosition)this.connectedTracks.get(i4);
			if(chunkPosition5.x == i1 && chunkPosition5.z == i3) {
				return true;
			}
		}

		return false;
	}

	private int getAdjacentTracks() {
		int i1 = 0;
		if(this.isMinecartTrack(this.trackX, this.trackY, this.trackZ - 1)) {
			++i1;
		}

		if(this.isMinecartTrack(this.trackX, this.trackY, this.trackZ + 1)) {
			++i1;
		}

		if(this.isMinecartTrack(this.trackX - 1, this.trackY, this.trackZ)) {
			++i1;
		}

		if(this.isMinecartTrack(this.trackX + 1, this.trackY, this.trackZ)) {
			++i1;
		}

		return i1;
	}

	private boolean handleKeyPress(RailLogic railLogic1) {
		if(this.isConnectedTo(railLogic1)) {
			return true;
		} else if(this.connectedTracks.size() == 2) {
			return false;
		} else if(this.connectedTracks.size() == 0) {
			return true;
		} else {
			ChunkPosition chunkPosition2 = (ChunkPosition)this.connectedTracks.get(0);
			return railLogic1.trackY == this.trackY && chunkPosition2.y == this.trackY ? true : true;
		}
	}

	private void func_788_d(RailLogic railLogic1) {
		this.connectedTracks.add(new ChunkPosition(railLogic1.trackX, railLogic1.trackY, railLogic1.trackZ));
		boolean z2 = this.isInTrack(this.trackX, this.trackY, this.trackZ - 1);
		boolean z3 = this.isInTrack(this.trackX, this.trackY, this.trackZ + 1);
		boolean z4 = this.isInTrack(this.trackX - 1, this.trackY, this.trackZ);
		boolean z5 = this.isInTrack(this.trackX + 1, this.trackY, this.trackZ);
		byte b6 = -1;
		if(z2 || z3) {
			b6 = 0;
		}

		if(z4 || z5) {
			b6 = 1;
		}

		if(!this.isPoweredRail) {
			if(z3 && z5 && !z2 && !z4) {
				b6 = 6;
			}

			if(z3 && z4 && !z2 && !z5) {
				b6 = 7;
			}

			if(z2 && z4 && !z3 && !z5) {
				b6 = 8;
			}

			if(z2 && z5 && !z3 && !z4) {
				b6 = 9;
			}
		}

		if(b6 == 0) {
			if(BlockRail.isRailBlockAt(this.worldObj, this.trackX, this.trackY + 1, this.trackZ - 1)) {
				b6 = 4;
			}

			if(BlockRail.isRailBlockAt(this.worldObj, this.trackX, this.trackY + 1, this.trackZ + 1)) {
				b6 = 5;
			}
		}

		if(b6 == 1) {
			if(BlockRail.isRailBlockAt(this.worldObj, this.trackX + 1, this.trackY + 1, this.trackZ)) {
				b6 = 2;
			}

			if(BlockRail.isRailBlockAt(this.worldObj, this.trackX - 1, this.trackY + 1, this.trackZ)) {
				b6 = 3;
			}
		}

		if(b6 < 0) {
			b6 = 0;
		}

		int i7 = b6;
		if(this.isPoweredRail) {
			i7 = this.worldObj.getBlockMetadata(this.trackX, this.trackY, this.trackZ) & 8 | b6;
		}

		this.worldObj.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, i7);
	}

	private boolean func_786_c(int i1, int i2, int i3) {
		RailLogic railLogic4 = this.getMinecartTrackLogic(new ChunkPosition(i1, i2, i3));
		if(railLogic4 == null) {
			return false;
		} else {
			railLogic4.func_785_b();
			return railLogic4.handleKeyPress(this);
		}
	}

	public void func_792_a(boolean z1, boolean z2) {
		boolean z3 = this.func_786_c(this.trackX, this.trackY, this.trackZ - 1);
		boolean z4 = this.func_786_c(this.trackX, this.trackY, this.trackZ + 1);
		boolean z5 = this.func_786_c(this.trackX - 1, this.trackY, this.trackZ);
		boolean z6 = this.func_786_c(this.trackX + 1, this.trackY, this.trackZ);
		byte b7 = -1;
		if((z3 || z4) && !z5 && !z6) {
			b7 = 0;
		}

		if((z5 || z6) && !z3 && !z4) {
			b7 = 1;
		}

		if(!this.isPoweredRail) {
			if(z4 && z6 && !z3 && !z5) {
				b7 = 6;
			}

			if(z4 && z5 && !z3 && !z6) {
				b7 = 7;
			}

			if(z3 && z5 && !z4 && !z6) {
				b7 = 8;
			}

			if(z3 && z6 && !z4 && !z5) {
				b7 = 9;
			}
		}

		if(b7 == -1) {
			if(z3 || z4) {
				b7 = 0;
			}

			if(z5 || z6) {
				b7 = 1;
			}

			if(!this.isPoweredRail) {
				if(z1) {
					if(z4 && z6) {
						b7 = 6;
					}

					if(z5 && z4) {
						b7 = 7;
					}

					if(z6 && z3) {
						b7 = 9;
					}

					if(z3 && z5) {
						b7 = 8;
					}
				} else {
					if(z3 && z5) {
						b7 = 8;
					}

					if(z6 && z3) {
						b7 = 9;
					}

					if(z5 && z4) {
						b7 = 7;
					}

					if(z4 && z6) {
						b7 = 6;
					}
				}
			}
		}

		if(b7 == 0) {
			if(BlockRail.isRailBlockAt(this.worldObj, this.trackX, this.trackY + 1, this.trackZ - 1)) {
				b7 = 4;
			}

			if(BlockRail.isRailBlockAt(this.worldObj, this.trackX, this.trackY + 1, this.trackZ + 1)) {
				b7 = 5;
			}
		}

		if(b7 == 1) {
			if(BlockRail.isRailBlockAt(this.worldObj, this.trackX + 1, this.trackY + 1, this.trackZ)) {
				b7 = 2;
			}

			if(BlockRail.isRailBlockAt(this.worldObj, this.trackX - 1, this.trackY + 1, this.trackZ)) {
				b7 = 3;
			}
		}

		if(b7 < 0) {
			b7 = 0;
		}

		this.setConnections(b7);
		int i8 = b7;
		if(this.isPoweredRail) {
			i8 = this.worldObj.getBlockMetadata(this.trackX, this.trackY, this.trackZ) & 8 | b7;
		}

		if(z2 || this.worldObj.getBlockMetadata(this.trackX, this.trackY, this.trackZ) != i8) {
			this.worldObj.setBlockMetadataWithNotify(this.trackX, this.trackY, this.trackZ, i8);

			for(int i9 = 0; i9 < this.connectedTracks.size(); ++i9) {
				RailLogic railLogic10 = this.getMinecartTrackLogic((ChunkPosition)this.connectedTracks.get(i9));
				if(railLogic10 != null) {
					railLogic10.func_785_b();
					if(railLogic10.handleKeyPress(this)) {
						railLogic10.func_788_d(this);
					}
				}
			}
		}

	}

	static int getNAdjacentTracks(RailLogic railLogic0) {
		return railLogic0.getAdjacentTracks();
	}
}
