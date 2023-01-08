package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityRendererProxy extends EntityRenderer {
	private Minecraft game;

	public EntityRendererProxy(Minecraft minecraft) {
		super(minecraft);
		this.game = minecraft;
	}

	public void updateCameraAndRender(float f1) {
		super.updateCameraAndRender(f1);
		ModLoader.OnTick(this.game);
	}
}
