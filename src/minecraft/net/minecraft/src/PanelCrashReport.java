package net.minecraft.src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextArea;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public class PanelCrashReport extends Panel {
	public PanelCrashReport(UnexpectedThrowable parammh) {
		this.setBackground(new Color(3028036));
		this.setLayout(new BorderLayout());
		StringWriter localStringWriter = new StringWriter();
		parammh.exception.printStackTrace(new PrintWriter(localStringWriter));
		String str1 = localStringWriter.toString();
		String str2 = "";
		String str3 = "";

		try {
			str3 = str3 + "Generated " + (new SimpleDateFormat()).format(new Date()) + "\n";
			str3 = str3 + "\n";
			str3 = str3 + "Minecraft: Minecraft Beta 1.7.3\n";
			str3 = str3 + "OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version") + "\n";
			str3 = str3 + "Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor") + "\n";
			str3 = str3 + "VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor") + "\n";
			str3 = str3 + "LWJGL: " + Sys.getVersion() + "\n";
			str2 = GL11.glGetString(GL11.GL_VENDOR);
			str3 = str3 + "OpenGL: " + GL11.glGetString(GL11.GL_RENDERER) + " version " + GL11.glGetString(GL11.GL_VERSION) + ", " + GL11.glGetString(GL11.GL_VENDOR) + "\n";
		} catch (Throwable throwable9) {
			str3 = str3 + "[failed to get system properties (" + throwable9 + ")]\n";
		}

		str3 = str3 + "\n";
		str3 = str3 + str1;
		String str4 = "";
		str4 = str4 + "Mods loaded: " + (ModLoader.getLoadedMods().size() + 1) + "\n";
		str4 = str4 + "ModLoader Beta 1.7.3" + "\n";

		BaseMod localTextArea;
		for(Iterator iterator8 = ModLoader.getLoadedMods().iterator(); iterator8.hasNext(); str4 = str4 + localTextArea.getClass().getName() + " " + localTextArea.Version() + "\n") {
			localTextArea = (BaseMod)iterator8.next();
		}

		str4 = str4 + "\n";
		if(str1.contains("Pixel format not accelerated")) {
			str4 = str4 + "      Bad video card drivers!      \n";
			str4 = str4 + "      -----------------------      \n";
			str4 = str4 + "\n";
			str4 = str4 + "Minecraft was unable to start because it failed to find an accelerated OpenGL mode.\n";
			str4 = str4 + "This can usually be fixed by updating the video card drivers.\n";
			if(str2.toLowerCase().contains("nvidia")) {
				str4 = str4 + "\n";
				str4 = str4 + "You might be able to find drivers for your video card here:\n";
				str4 = str4 + "  http://www.nvidia.com/\n";
			} else if(str2.toLowerCase().contains("ati")) {
				str4 = str4 + "\n";
				str4 = str4 + "You might be able to find drivers for your video card here:\n";
				str4 = str4 + "  http://www.amd.com/\n";
			}
		} else {
			str4 = str4 + "      Minecraft has crashed!      \n";
			str4 = str4 + "      ----------------------      \n";
			str4 = str4 + "\n";
			str4 = str4 + "Minecraft has stopped running because it encountered a problem.\n";
			str4 = str4 + "\n";
			str4 = str4 + "If you wish to report this, please copy this entire text and email it to support@mojang.com.\n";
			str4 = str4 + "Please include a description of what you did when the error occured.\n";
		}

		str4 = str4 + "\n";
		str4 = str4 + "\n";
		str4 = str4 + "\n";
		str4 = str4 + "--- BEGIN ERROR REPORT " + Integer.toHexString(str4.hashCode()) + " --------\n";
		str4 = str4 + str3;
		str4 = str4 + "--- END ERROR REPORT " + Integer.toHexString(str4.hashCode()) + " ----------\n";
		str4 = str4 + "\n";
		str4 = str4 + "\n";
		TextArea localTextArea1 = new TextArea(str4, 0, 0, 1);
		localTextArea1.setFont(new Font("Monospaced", 0, 12));
		this.add(new CanvasMojangLogo(), "North");
		this.add(new CanvasCrashReport(80), "East");
		this.add(new CanvasCrashReport(80), "West");
		this.add(new CanvasCrashReport(100), "South");
		this.add(localTextArea1, "Center");
	}
}
