package net.minecraft.src;

import io.apaumadist.haxloader.Client;
import net.minecraft.client.Minecraft;

public class mod_HaxLoader extends BaseMod {
	Minecraft mc;
	
	public mod_HaxLoader() {
		mc = ModLoader.getMinecraftInstance();
	}

	public void ModsLoaded() {
		Client client = new Client();
		client.init();
	}
	
	@Override
	public String Version() {
		return Client.VERSION;
	}
}
