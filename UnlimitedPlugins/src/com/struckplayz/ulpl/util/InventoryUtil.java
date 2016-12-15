package com.struckplayz.ulpl.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class InventoryUtil {

	public static void statusInventory(Player player) {
		Plugin[] list = Bukkit.getPluginManager().getPlugins();
		Inventory i = Bukkit.createInventory(null, 54, "Loaded Plugins - " + list.length);
		for (Plugin p : list) {
			ItemStack is = new ItemStack(Material.NAME_TAG);
			ItemMeta im = is.getItemMeta();
			if (p.isEnabled()) {
				im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
				im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			}
			PluginDescriptionFile file = p.getDescription();
			im.setDisplayName("§7" + p.getName());
			im.setLore(new ArrayList<String>() {
				private static final long serialVersionUID = 1L;
			{
				
				add("§7Version: §av" + file.getVersion());
				add("§7Description: §a" + file.getDescription());
				add("§7Authors:");
				for (String s : file.getAuthors()) {
					add(" §7- §a" + s);
				}
				
			}});
			is.setItemMeta(im);
			i.addItem(is);
		}
		ItemStack is = new ItemStack(Material.REDSTONE_LAMP_OFF, 1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("§7Information");
		im.setLore(new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
		{
			
			add("§7All §aenchanted§7 nametags are enabled plugins.");
			add("§7Click nametags to enable/disable plugins.");
			
		}});
		is.setItemMeta(im);
		i.setItem(49, is);
		player.openInventory(i);
	}
	
}
