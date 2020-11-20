package me.thicccat.catx.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Clear implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public Clear(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("clear").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("clear") != null && this.config.getConfigurationSection("commands").getConfigurationSection("clear").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					if (plugin.getServer().getPlayer(args[0]) != null) {
						Player toClear = plugin.getServer().getPlayer(args[0]);
						if (args.length >= 2) { // if an item is mentioned
							if (Material.getMaterial(args[1].toUpperCase()) != null) {
							for (ItemStack item:toClear.getInventory().getContents()) {
								if (item != null && item.getType() == Material.getMaterial(args[1].toUpperCase())) {
									toClear.getInventory().remove(item);
								}
							}
							if (args[1].toLowerCase().contains("boots")) {
								toClear.getInventory().setBoots(null);
							}
							if (args[1].toLowerCase().contains("leggings")) {
								toClear.getInventory().setLeggings(null);					
							}
							if (args[1].toLowerCase().contains("helmet")) {
								toClear.getInventory().setHelmet(null);
							}
							if (args[1].toLowerCase().contains("chestplate")) {
								toClear.getInventory().setChestplate(null);
							}
							String itemName = args[1].replace("_", " ");
							sender.sendMessage(handler.getCaption("ClearedOtherItem").replace("{0}", itemName).replace("{1}", toClear.getName()));
							} else {
								sender.sendMessage(handler.getCaption("InvalidItem").replace("{0}", args[1]));
							}
							return true;
						} else { // clear the entire inventory
							toClear.getInventory().clear();
							toClear.getInventory().setArmorContents(null);
							sender.sendMessage(handler.getCaption("ClearedOther").replace("{0}", toClear.getName()));
							return true;
						}
					} else {
						sender.sendMessage(handler.getCaption("PlayerNotFound"));
					}
				} else {
					sender.sendMessage(handler.getCaption("Usage").replace("{0}", "/clear").replace("{1}", "<player> [item]"));
				}
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.clear")) {
				if (args.length > 0) {
					if (p.hasPermission("catx.clear.others")) {
						if (plugin.getServer().getPlayer(args[0]) != null) {
							Player toClear = plugin.getServer().getPlayer(args[0]);
							if (args.length >= 2) { // if an item is mentioned
								if (Material.getMaterial(args[1].toUpperCase()) != null) {
								for (ItemStack item:toClear.getInventory().getContents()) {
									if (item != null && item.getType() == Material.getMaterial(args[1].toUpperCase())) {
										toClear.getInventory().remove(item);
									}
								}
								if (args[1].toLowerCase().contains("boots")) {
									toClear.getInventory().setBoots(null);
								}
								if (args[1].toLowerCase().contains("leggings")) {
									toClear.getInventory().setLeggings(null);					
								}
								if (args[1].toLowerCase().contains("helmet")) {
									toClear.getInventory().setHelmet(null);
								}
								if (args[1].toLowerCase().contains("chestplate")) {
									toClear.getInventory().setChestplate(null);
								}
								String itemName = args[1].replace("_", " ");
								p.sendMessage(handler.getCaption("ClearedOtherItem").replace("{0}", itemName).replace("{1}", toClear.getName()));
								} else {
									p.sendMessage(handler.getCaption("InvalidItem").replace("{0}", args[1]));
								}
								return true;
							} else { // clear the entire inventory
								toClear.getInventory().clear();
								toClear.getInventory().setArmorContents(null);
								p.sendMessage(handler.getCaption("ClearedOther").replace("{0}", toClear.getName()));
								return true;
							}
						} else {
							sender.sendMessage(handler.getCaption("PlayerNotFound"));
						}
					} else {
						p.sendMessage(handler.getCaption("NoPermission"));
					}
				} else {
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
					p.sendMessage(handler.getCaption("ClearedSelf"));
				}
				return true;
			} else {
				p.sendMessage(handler.getCaption("NoPermission"));
			}
	    } else {
	    	sender.sendMessage(handler.getCaption("CommandDisabled"));
	    }
		return false;
	}
}
