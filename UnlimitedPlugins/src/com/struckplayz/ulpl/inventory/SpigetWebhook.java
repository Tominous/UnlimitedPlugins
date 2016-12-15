package com.struckplayz.ulpl.inventory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.struckplayz.ulpl.UnlimitedPlugins;

public class SpigetWebhook {

	public static ArrayList<JsonObject> search(String name, int size) throws IOException {
		ArrayList<JsonObject> objects = new ArrayList<JsonObject>();

		URL url = new URL("https://api.spiget.org/v2/search/resources/" + name + "?field=name&size=" + size);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		JsonElement parser = new JsonParser().parse(new JsonReader(in));
		JsonArray array = parser.getAsJsonArray();
		for (int i = 0; i < array.size(); i++) {
			JsonObject e = array.get(i).getAsJsonObject();
			objects.add(e);
		}
		in.close();

		return objects;
	}
	
	public static ArrayList<JsonObject> getRecents() throws IOException {
		ArrayList<JsonObject> objects = new ArrayList<JsonObject>();

		URL url = new URL("https://api.spiget.org/v2/resources/new?size=20");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		JsonElement parser = new JsonParser().parse(new JsonReader(in));
		JsonArray array = parser.getAsJsonArray();
		for (int i = 0; i < array.size(); i++) {
			JsonObject e = array.get(i).getAsJsonObject();
			objects.add(e);
		}
		in.close();

		return objects;
	}
	
	public static void downloadResource(int id) throws IOException {
		File to = new File(UnlimitedPlugins.getInstance().getDataFolder(), id + ".jar");
		saveUrl(to.getPath(), "https://api.spiget.org/v2/resources/" + id + "/download");
		UnlimitedPlugins.getInstance().loadPlugin(Bukkit.getConsoleSender(), to, new ArrayList<Plugin>());
	}
	
	private static void saveUrl(final String filename, final String urlString)
	        throws MalformedURLException, IOException {
	    BufferedInputStream in = null;
	    FileOutputStream fout = null;
	    try {
	        in = new BufferedInputStream(new URL(urlString).openStream());
	        fout = new FileOutputStream(filename);

	        final byte data[] = new byte[1024];
	        int count;
	        while ((count = in.read(data, 0, 1024)) != -1) {
	            fout.write(data, 0, count);
	        }
	    } finally {
	        if (in != null) {
	            in.close();
	        }
	        if (fout != null) {
	            fout.close();
	        }
	    }
	}
	
	public static Inventory getInventory(String nam, ArrayList<JsonObject> objs) throws IOException {
		String fixedName = nam.substring(0, 30);
		Inventory i = Bukkit.createInventory(null, 27, fixedName);

		for (JsonObject obj : objs) {
			String name = obj.get("name").getAsString();
			String tag = obj.get("tag").getAsString();
			int id = obj.get("id").getAsInt();
			String downloads = (obj.get("downloads") == null) ? "Couldn't grab amount of downloads.":obj.get("downloads").getAsInt() + "";
			ItemStack is = new ItemStack(Material.NAME_TAG);
			ItemMeta im = is.getItemMeta();
			im.setDisplayName("§7" + name);
			im.setLore(new ArrayList<String>() {
				private static final long serialVersionUID = 1L;
				{

					add("§7Tag: §a" + tag);
					add("§7ID: §a" + id);
					add(" ");
					add("§7Downloads: §a" + downloads);
					add(" ");
					add("§aClick to download!");

				}
			});
			is.setItemMeta(im);
			i.addItem(is);
		}
		
		return i;
	}

}
