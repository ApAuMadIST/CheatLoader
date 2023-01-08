package net.minecraft.src;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ChunkFolderPattern implements FileFilter {
	public static final Pattern field_22392_a = Pattern.compile("[0-9a-z]|([0-9a-z][0-9a-z])");

	private ChunkFolderPattern() {
	}

	public boolean accept(File file1) {
		if(file1.isDirectory()) {
			Matcher matcher2 = field_22392_a.matcher(file1.getName());
			return matcher2.matches();
		} else {
			return false;
		}
	}

	ChunkFolderPattern(Empty2 empty21) {
		this();
	}
}
