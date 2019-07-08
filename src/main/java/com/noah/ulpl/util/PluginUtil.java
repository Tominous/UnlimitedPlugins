package com.noah.ulpl.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginUtil {

    @Deprecated
	public static boolean isPlugin(String name) {
		return Bukkit.getPluginManager().getPlugin(name) != null;
	}

	public static Plugin getPlugin(String name) {
		return Bukkit.getPluginManager().getPlugin(name);
	}

	@Deprecated
	public static boolean isExisting(String name, File file) {
		for (File f : file.listFiles()) {
			if (f.getName().replaceAll(".jar", "").equals(name)) {
				return true;
			}
		}
		for (File f : new File("plugins/").listFiles()) {
			if (f.getName().replaceAll(".jar", "").equals(name)) {
				return true;
			}
		}
		return false;
	}

    @Deprecated
	public static File getPluginFile(String name, File file) {
		for (File f : file.listFiles()) {
			if (f.getName().replaceAll(".jar", "").equals(name)) {
				return f;
			}
		}
		for (File f : new File("plugins/").listFiles()) {
			if (f.getName().replaceAll(".jar", "").equals(name)) {
				return f;
			}
		}
		return null;
	}

}
