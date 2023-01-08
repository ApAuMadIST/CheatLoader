package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.minecraft.client.Minecraft;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ThreadDownloadResources extends Thread {
	public File resourcesFolder;
	private Minecraft mc;
	private boolean closing = false;

	public ThreadDownloadResources(File file1, Minecraft minecraft2) {
		this.mc = minecraft2;
		this.setName("Resource download thread");
		this.setDaemon(true);
		this.resourcesFolder = new File(file1, "resources/");
		if(!this.resourcesFolder.exists() && !this.resourcesFolder.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + this.resourcesFolder);
		}
	}

	public void run() {
		try {
			URL uRL1 = new URL("http://s3.amazonaws.com/MinecraftResources/");
			DocumentBuilderFactory documentBuilderFactory2 = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder3 = documentBuilderFactory2.newDocumentBuilder();
			Document document4 = documentBuilder3.parse(uRL1.openStream());
			NodeList nodeList5 = document4.getElementsByTagName("Contents");

			for(int i6 = 0; i6 < 2; ++i6) {
				for(int i7 = 0; i7 < nodeList5.getLength(); ++i7) {
					Node node8 = nodeList5.item(i7);
					if(node8.getNodeType() == 1) {
						Element element9 = (Element)node8;
						String string10 = ((Element)element9.getElementsByTagName("Key").item(0)).getChildNodes().item(0).getNodeValue();
						long j11 = Long.parseLong(((Element)element9.getElementsByTagName("Size").item(0)).getChildNodes().item(0).getNodeValue());
						if(j11 > 0L) {
							this.downloadAndInstallResource(uRL1, string10, j11, i6);
							if(this.closing) {
								return;
							}
						}
					}
				}
			}
		} catch (Exception exception13) {
			this.loadResource(this.resourcesFolder, "");
			exception13.printStackTrace();
		}

	}

	public void reloadResources() {
		this.loadResource(this.resourcesFolder, "");
	}

	private void loadResource(File file1, String string2) {
		File[] file3 = file1.listFiles();

		for(int i4 = 0; i4 < file3.length; ++i4) {
			if(file3[i4].isDirectory()) {
				this.loadResource(file3[i4], string2 + file3[i4].getName() + "/");
			} else {
				try {
					this.mc.installResource(string2 + file3[i4].getName(), file3[i4]);
				} catch (Exception exception6) {
					System.out.println("Failed to add " + string2 + file3[i4].getName());
				}
			}
		}

	}

	private void downloadAndInstallResource(URL uRL1, String string2, long j3, int i5) {
		try {
			int i6 = string2.indexOf("/");
			String string7 = string2.substring(0, i6);
			if(!string7.equals("sound") && !string7.equals("newsound")) {
				if(i5 != 1) {
					return;
				}
			} else if(i5 != 0) {
				return;
			}

			File file8 = new File(this.resourcesFolder, string2);
			if(!file8.exists() || file8.length() != j3) {
				file8.getParentFile().mkdirs();
				String string9 = string2.replaceAll(" ", "%20");
				this.downloadResource(new URL(uRL1, string9), file8, j3);
				if(this.closing) {
					return;
				}
			}

			this.mc.installResource(string2, file8);
		} catch (Exception exception10) {
			exception10.printStackTrace();
		}

	}

	private void downloadResource(URL uRL1, File file2, long j3) throws IOException {
		byte[] b5 = new byte[4096];
		DataInputStream dataInputStream6 = new DataInputStream(uRL1.openStream());
		DataOutputStream dataOutputStream7 = new DataOutputStream(new FileOutputStream(file2));
		boolean z8 = false;

		do {
			int i9;
			if((i9 = dataInputStream6.read(b5)) < 0) {
				dataInputStream6.close();
				dataOutputStream7.close();
				return;
			}

			dataOutputStream7.write(b5, 0, i9);
		} while(!this.closing);

	}

	public void closeMinecraft() {
		this.closing = true;
	}
}
