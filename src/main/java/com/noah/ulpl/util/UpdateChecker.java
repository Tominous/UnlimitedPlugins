package com.noah.ulpl.util;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
  * UpdateChecker
  *
  * This util is used to compare Spigot versions
  * It is used in all of my plugins
  *
  * @author Struck713
  *
  */
public class UpdateChecker {

    private JavaPlugin plugin;
    private double newVersion = 0;

    public UpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean check() {
        final String apiURL = "https://api.spigotmc.org/legacy/update.php?resource=32431";
        try {
            URL api = new URL(apiURL);
            URLConnection yc = api.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc
                    .getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                newVersion = Double.parseDouble(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String descriptionVersion = plugin.getDescription().getVersion();
        double version = Double.parseDouble(descriptionVersion);
        if (version < newVersion) {
            return true;
        }
        return false;
    }

    public double getNewVersion() {
        return newVersion;
    }

    public String getCurrent() {
        return plugin.getDescription().getVersion();
    }

}
