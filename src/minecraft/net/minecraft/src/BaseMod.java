package net.minecraft.src;

import java.util.Map;
import java.util.Random;

import net.minecraft.client.Minecraft;

public abstract class BaseMod {
	public int AddFuel(int id) {
		return 0;
	}

	public void AddRenderer(Map renderers) {
	}

	public boolean DispenseEntity(World world, double x, double y, double z, int xVel, int zVel, ItemStack item) {
		return false;
	}

	public void GenerateNether(World world, Random random, int chunkX, int chunkZ) {
	}

	public void GenerateSurface(World world, Random random, int chunkX, int chunkZ) {
	}

	public void KeyboardEvent(KeyBinding event) {
	}

	public void ModsLoaded() {
	}

	public boolean OnTickInGame(Minecraft game) {
		return false;
	}

	public boolean OnTickInGUI(Minecraft game, GuiScreen gui) {
		return false;
	}

	public void RegisterAnimation(Minecraft game) {
	}

	public void RenderInvBlock(RenderBlocks renderer, Block block, int metadata, int modelID) {
	}

	public boolean RenderWorldBlock(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, Block block, int modelID) {
		return false;
	}

	public void TakenFromCrafting(EntityPlayer player, ItemStack item) {
	}

	public void TakenFromFurnace(EntityPlayer player, ItemStack item) {
	}

	public void OnItemPickup(EntityPlayer player, ItemStack item) {
	}

	public String toString() {
		return this.getClass().getName() + " " + this.Version();
	}

	public abstract String Version();
}
