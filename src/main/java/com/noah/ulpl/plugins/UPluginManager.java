package com.noah.ulpl.plugins;

import com.noah.ulpl.UnlimitedPlugins;
import com.noah.ulpl.util.PluginUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class UPluginManager {

    private File directory;
    private UnlimitedPlugins unlimitedPlugins;
    private ArrayList<UPlugin> loadedPlugins;

    public UPluginManager(UnlimitedPlugins unlimitedPlugins) {
        this.unlimitedPlugins = unlimitedPlugins;
        this.directory = new File(unlimitedPlugins.getDataFolder(), "/plugins/");
        this.loadedPlugins = new ArrayList<>();

        if (!directory.exists()) {
            directory.mkdirs();
        }

    }

    public void loadAll(boolean message)  {
        final PluginManager pm = Bukkit.getPluginManager();
        Plugin[] plugins = pm.loadPlugins(directory);

        for (Plugin plugin : plugins) {
            pm.enablePlugin(plugin);

            File location = null;
            try {
                location = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            UPlugin uPlugin = new UPlugin(plugin.getName(), location);
            uPlugin.setLoaded(true);
            uPlugin.setEnabled(true);

            loadedPlugins.add(uPlugin);
        }

        if (message) {
            final ConsoleCommandSender sender = Bukkit.getConsoleSender();
            unlimitedPlugins.log("PluginLoader", "Starting &aload process&r..", false);
            unlimitedPlugins.log("PluginLoader", "Scanning files to see if anything is left to load...", false);
            if (loadedPlugins.size() != 0) {
                unlimitedPlugins.log("PluginLoader", "Listing loaded plugins (&aSuccess&r/&cFail&r):", false);
                unlimitedPlugins.log("PluginLoader", "&m----------------------------------", false);
                for (UPlugin p : loadedPlugins) {
                    unlimitedPlugins.log("PluginLoader", "&a" + p.getName(), false);
                }
                unlimitedPlugins.log("PluginLoader", "&m----------------------------------", false);
            } else {
                unlimitedPlugins.log("PluginLoader", "Found no plugins to load. Place all plugins you want to load in the \""
                                + directory.toString() + "\" directory.", true);
            }
        }

    }

    public boolean load(String name) {
        UPlugin uPlugin = searchByName(name);
        if (uPlugin == null) {
            return false;
        }

        final PluginManager pm = Bukkit.getPluginManager();
        if (!uPlugin.isLoaded()) {
            try {
                Plugin loaded = pm.loadPlugin(uPlugin.getFile());
                uPlugin.setLoaded(true);

                //name could need updated
                String loadedName = loaded.getName();
                if (!uPlugin.getName().equals(loadedName)) {
                    uPlugin.setName(loadedName);
                }
                loadedPlugins.add(uPlugin);
            } catch (UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e) {
                File file = uPlugin.getFile();
                if (e instanceof UnknownDependencyException) {
                    unlimitedPlugins.log("PluginLoader","Tried to load the jar, " + file.getName()
                            + " but failed because the required dependencies wheren't found.", true);
                } else if (e instanceof InvalidPluginException) {
                    unlimitedPlugins.log("PluginLoader", "Tried to load the jar, " + file.getName()
                            + " but failed because the jar was invalid.", true);
                } else if (e instanceof InvalidDescriptionException) {
                    unlimitedPlugins.log("PluginLoader", "Tried to load the jar, " + file.getName()
                            + " but failed because the plugin.yml was invalid.", true);
                } else {
                   unlimitedPlugins.log("PluginLoader","Tried to load the jar, " + file.getName()
                            + " but failed because an unknown error occurred.", true);
                }
            }
        }
        if (!uPlugin.isEnabled()) {
            Plugin plugin = pm.getPlugin(uPlugin.getName());
            pm.enablePlugin(plugin);
            uPlugin.setEnabled(true);
        }
        return true;
    }

    public boolean disable(String name) {
        UPlugin plugin = searchByName(name);
        if (!plugin.isLoaded() || !plugin.isEnabled()) {
            return false;
        }

        final PluginManager pm = Bukkit.getPluginManager();
        Plugin bukkitPlugin = PluginUtil.getPlugin(name);
        pm.disablePlugin(bukkitPlugin);

        plugin.setEnabled(false);

        return true;
    }


    public UPlugin searchByName(String name) {
        for (UPlugin uPlugin : loadedPlugins) {
            if (uPlugin.getName().equalsIgnoreCase(name)) {
                return uPlugin;
            }
        }

        for (File file : directory.listFiles()) {
            String fileName = file.getName()
                    .replaceAll("\\.jar", "");

            if (fileName.equalsIgnoreCase(name)) {
                return new UPlugin(fileName, file);
            }

        }

        return null;
    }

    public File getDirectory() {
        return directory;
    }

    public ArrayList<UPlugin> getLoadedPlugins() {
        return loadedPlugins;
    }
}
