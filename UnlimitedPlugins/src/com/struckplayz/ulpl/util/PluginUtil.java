package com.struckplayz.ulpl.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginUtil {
	
	public static boolean isPlugin(String name) {
		return Bukkit.getPluginManager().getPlugin(name) != null;
	}

	public static Plugin getPlugin(String name) {
		return Bukkit.getPluginManager().getPlugin(name);
	}

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
