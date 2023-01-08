package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

public class TileEntityRendererPiston extends TileEntitySpecialRenderer {
	private RenderBlocks field_31071_b;

	public void func_31070_a(TileEntityPiston tileEntityPiston1, double d2, double d4, double d6, float f8) {
		Block block9 = Block.blocksList[tileEntityPiston1.getStoredBlockID()];
		if(block9 != null && tileEntityPiston1.func_31008_a(f8) < 1.0F) {
			Tessellator tessellator10 = Tessellator.instance;
			this.bindTextureByName("/terrain.png");
			RenderHelper.disableStandardItemLighting();
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			if(Minecraft.isAmbientOcclusionEnabled()) {
				GL11.glShadeModel(GL11.GL_SMOOTH);
			} else {
				GL11.glShadeModel(GL11.GL_FLAT);
			}

			tessellator10.startDrawingQuads();
			tessellator10.setTranslationD((double)((float)d2 - (float)tileEntityPiston1.xCoord + tileEntityPiston1.func_31017_b(f8)), (double)((float)d4 - (float)tileEntityPiston1.yCoord + tileEntityPiston1.func_31014_c(f8)), (double)((float)d6 - (float)tileEntityPiston1.zCoord + tileEntityPiston1.func_31013_d(f8)));
			tessellator10.setColorOpaque(1, 1, 1);
			if(block9 == Block.pistonExtension && tileEntityPiston1.func_31008_a(f8) < 0.5F) {
				this.field_31071_b.func_31079_a(block9, tileEntityPiston1.xCoord, tileEntityPiston1.yCoord, tileEntityPiston1.zCoord, false);
			} else if(tileEntityPiston1.func_31012_k() && !tileEntityPiston1.func_31015_b()) {
				Block.pistonExtension.func_31052_a_(((BlockPistonBase)block9).func_31040_i());
				this.field_31071_b.func_31079_a(Block.pistonExtension, tileEntityPiston1.xCoord, tileEntityPiston1.yCoord, tileEntityPiston1.zCoord, tileEntityPiston1.func_31008_a(f8) < 0.5F);
				Block.pistonExtension.func_31051_a();
				tessellator10.setTranslationD((double)((float)d2 - (float)tileEntityPiston1.xCoord), (double)((float)d4 - (float)tileEntityPiston1.yCoord), (double)((float)d6 - (float)tileEntityPiston1.zCoord));
				this.field_31071_b.func_31078_d(block9, tileEntityPiston1.xCoord, tileEntityPiston1.yCoord, tileEntityPiston1.zCoord);
			} else {
				this.field_31071_b.func_31075_a(block9, tileEntityPiston1.xCoord, tileEntityPiston1.yCoord, tileEntityPiston1.zCoord);
			}

			tessellator10.setTranslationD(0.0D, 0.0D, 0.0D);
			tessellator10.draw();
			RenderHelper.enableStandardItemLighting();
		}

	}

	public void func_31069_a(World world1) {
		this.field_31071_b = new RenderBlocks(world1);
	}

	public void renderTileEntityAt(TileEntity tileEntity1, double d2, double d4, double d6, float f8) {
		this.func_31070_a((TileEntityPiston)tileEntity1, d2, d4, d6, f8);
	}
}
