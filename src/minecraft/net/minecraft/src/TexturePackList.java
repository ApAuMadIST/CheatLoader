package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;

public class TexturePackList {
	private List availableTexturePacks = new ArrayList();
	private TexturePackBase defaultTexturePack = new TexturePackDefault();
	public TexturePackBase selectedTexturePack;
	private Map field_6538_d = new HashMap();
	private Minecraft mc;
	private File texturePackDir;
	private String currentTexturePack;

	public TexturePackList(Minecraft minecraft1, File file2) {
		this.mc = minecraft1;
		this.texturePackDir = new File(file2, "texturepacks");
		if(!this.texturePackDir.exists()) {
			this.texturePackDir.mkdirs();
		}

		this.currentTexturePack = minecraft1.gameSettings.skin;
		this.updateAvaliableTexturePacks();
		this.selectedTexturePack.func_6482_a();
	}

	public boolean setTexturePack(TexturePackBase texturePackBase1) {
		if(texturePackBase1 == this.selectedTexturePack) {
			return false;
		} else {
			this.selectedTexturePack.closeTexturePackFile();
			this.currentTexturePack = texturePackBase1.texturePackFileName;
			this.selectedTexturePack = texturePackBase1;
			this.mc.gameSettings.skin = this.currentTexturePack;
			this.mc.gameSettings.saveOptions();
			this.selectedTexturePack.func_6482_a();
			return true;
		}
	}

	public void updateAvaliableTexturePacks() {
		ArrayList arrayList1 = new ArrayList();
		this.selectedTexturePack = null;
		arrayList1.add(this.defaultTexturePack);
		if(this.texturePackDir.exists() && this.texturePackDir.isDirectory()) {
			File[] file2 = this.texturePackDir.listFiles();
			File[] file3 = file2;
			int i4 = file2.length;

			for(int i5 = 0; i5 < i4; ++i5) {
				File file6 = file3[i5];
				if(file6.isFile() && file6.getName().toLowerCase().endsWith(".zip")) {
					String string7 = file6.getName() + ":" + file6.length() + ":" + file6.lastModified();

					try {
						if(!this.field_6538_d.containsKey(string7)) {
							TexturePackCustom texturePackCustom8 = new TexturePackCustom(file6);
							texturePackCustom8.field_6488_d = string7;
							this.field_6538_d.put(string7, texturePackCustom8);
							texturePackCustom8.func_6485_a(this.mc);
						}

						TexturePackBase texturePackBase12 = (TexturePackBase)this.field_6538_d.get(string7);
						if(texturePackBase12.texturePackFileName.equals(this.currentTexturePack)) {
							this.selectedTexturePack = texturePackBase12;
						}

						arrayList1.add(texturePackBase12);
					} catch (IOException iOException9) {
						iOException9.printStackTrace();
					}
				}
			}
		}

		if(this.selectedTexturePack == null) {
			this.selectedTexturePack = this.defaultTexturePack;
		}

		this.availableTexturePacks.removeAll(arrayList1);
		Iterator iterator10 = this.availableTexturePacks.iterator();

		while(iterator10.hasNext()) {
			TexturePackBase texturePackBase11 = (TexturePackBase)iterator10.next();
			texturePackBase11.func_6484_b(this.mc);
			this.field_6538_d.remove(texturePackBase11.field_6488_d);
		}

		this.availableTexturePacks = arrayList1;
	}

	public List availableTexturePacks() {
		return new ArrayList(this.availableTexturePacks);
	}
}
