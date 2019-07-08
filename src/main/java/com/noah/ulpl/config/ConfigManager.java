package com.noah.ulpl.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ConfigManager {

    private JavaPlugin plugin;
    private FileConfiguration conf;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void defaults() {
        if (conf == null) {
            plugin.saveDefaultConfig();
            plugin.saveConfig();
        }
        this.conf = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        this.conf = plugin.getConfig();
    }

    public String getPrefix() {
        return getString(NodesUtil.PREFIX);
    }

    public String languageValue(String node) {
        return getPrefix() + color(getString(node));
    }

    public String languageList(String node) {
        List<String> lists = conf.getStringList(node);
        StringBuilder sb = new StringBuilder();
        for (String s : lists) {
            sb.append(color(s) + "\n");
        }
        return sb.substring(0, sb.lastIndexOf("\n"));
    }

    public String getString(String node) {
        String retrive = conf.getString(node);
        return color(retrive);
    }

    public boolean getBoolean(String node) {
        return conf.getBoolean(node);
    }

    public String color(String toColor) {
        return ChatColor.translateAlternateColorCodes('&', toColor);
    }

}