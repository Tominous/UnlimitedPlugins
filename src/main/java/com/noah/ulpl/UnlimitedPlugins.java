package com.noah.ulpl;

import com.noah.ulpl.config.ConfigManager;
import com.noah.ulpl.config.NodesUtil;
import com.noah.ulpl.plugins.UPluginManager;
import com.noah.ulpl.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class UnlimitedPlugins extends JavaPlugin {

	private UPluginManager uPluginManager;
	private ConfigManager configManager;

	public void onEnable() {
		metrics();

		this.configManager = new ConfigManager(this);
		configManager.defaults();
        this.uPluginManager = new UPluginManager(this);
        this.uPluginManager.loadAll(true);

        UpdateChecker updateChecker = new UpdateChecker(this);
        updateChecker.check();
        if (configManager.getBoolean(NodesUtil.UPDATER_ENABLED)) {
            log("Updater","You are running version &6" + updateChecker.getCurrent() + "&r of UnlimitedPlugins.", false);
            if (updateChecker.check()) {
                log("Updater","An update for &6UnlimitedPlugins (" + updateChecker.getNewVersion() + ") &rwas found. Please update at: https://www.spigotmc.org/resources/23989/", false);
            } else {
                log("Updater", "Your version of UnlimitedPlugins is &6up-to-date&r!", false);
            }
        }
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("unlimitedplugins") || command.getName().equalsIgnoreCase("ulpl")) {
			if (args.length <= 1) {
				sender.sendMessage(configManager.getString(NodesUtil.HELP_MENU));
			}
			if (args.length == 2) {
				String name = args[1];
				if (args[0].equalsIgnoreCase("disable")) {
					if (uPluginManager.disable(name)) {
                        sender.sendMessage(
                                String.format(
                                        configManager.getString(NodesUtil.MANAGEMENT_DISABLED),
                                        name));
						return true;
					}
					sender.sendMessage(configManager.getString(NodesUtil.EXISTS_NO));
					return true;
				}
				if (args[0].equalsIgnoreCase("enable")) {
                    if (uPluginManager.load(name)) {
                        sender.sendMessage(
                                String.format(
                                        configManager.getString(NodesUtil.MANAGEMENT_ENABLED),
                                        name));
                        return true;
                    }
                    sender.sendMessage(
                            configManager.getString(NodesUtil.EXISTS_NO));
                    return true;
				}
                sender.sendMessage(
                        configManager.getString(NodesUtil.HELP_MENU));
				return true;
			}
			if (args.length >= 3) {
                sender.sendMessage(
                        configManager.getString(NodesUtil.HELP_MENU));
                return true;
			}
		}
		return true;
	}

    private void metrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            log("Metrics", "Failed to send metrics to http://mcstats.org. :-(", true);
        }
    }

    final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    public void log(String module, String message, boolean error) {
        if (error) {
            sender.sendMessage(
                    "[UnlimitedPlugins] " + ChatColor.RED + "E:" + ChatColor.RESET + "(" + module + ") " + configManager.color(message));
            return;
        }
        sender.sendMessage("[UnlimitedPlugins] (" + module + ") " + configManager.color(message));
    }

    public UPluginManager getUPluginManager() {
        return uPluginManager;
    }


}
