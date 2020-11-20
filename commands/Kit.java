package me.thicccat.catx.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import me.thicccat.catx.handlers.CooldownHandler;

public class Kit implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
    
    public ConfigManager cManager;
    public Config cConfig;
	
	private Main plugin;

	public Kit(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("kit").setExecutor(this);
		plugin.getCommand("setkit").setExecutor(this);
		plugin.getCommand("delkit").setExecutor(this);
	}

	 HashMap<String, Long> kitCooldowns = new HashMap<String, Long>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		this.cManager = new ConfigManager(plugin);
	    this.cConfig = cManager.getNewConfig("config.yml");
		this.manager = new ConfigManager(plugin);
        this.config = manager.getNewConfig("config.yml");
		
		CaptionHandler handler = new CaptionHandler(plugin);
		CooldownHandler cooldowns = new CooldownHandler(plugin);
		if (!(sender instanceof Player)) {
			sender.sendMessage(handler.getCaption("ConsoleForbidden"));
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("kit")) {
			if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("kit") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("kit").getBoolean("enabled") == true) {
				if (p.hasPermission("catx.kit")) {
					if (args.length == 0) {
						if (p.hasPermission("catx.kit.list")) {
							String KitList = handler.getCaption("KitList");
							String commaColor = null;
							String nameColor = null;
							if (KitList.substring(0, 1).equals("&")) {
								commaColor = "§" + KitList.substring(1, 2);
							} else {
								commaColor = "§f";
							}
							if (KitList.substring(KitList.indexOf("{")-2, KitList.indexOf("{")-1).equals("&")) {
								nameColor = "§" + KitList.substring(KitList.indexOf("{")-1, KitList.indexOf("{"));
							} else {
								nameColor = "§f";
							}
							KitList = handler.addColor(KitList);
							p.sendMessage(KitList.replace("{0}", String.join(commaColor + "," + nameColor + " ", config.getConfigurationSection("kits").getKeys(false))));
						} else {
							p.sendMessage(handler.getCaption("Usage").replace("{0}", "/kit").replace("{1}", "<name>"));
						}
					} else {
						int cooldownTime = 0;
						String kitName = args[0];
						if (config.getConfigurationSection("kits").getConfigurationSection(kitName) != null) {
							if (p.hasPermission("catx.kit." + kitName)) {
								if (config.getConfigurationSection("kits").getConfigurationSection(kitName).isSet("cooldown")) {
									cooldownTime = config.getConfigurationSection("kits").getConfigurationSection(kitName).getInt("cooldown");
								}
								if (config.getConfigurationSection("kits").getConfigurationSection(kitName).getList("items") != null) {
									@SuppressWarnings("unchecked")
									List<String> items = config.getConfigurationSection("kits").getConfigurationSection(kitName).getList("items");
									if (Boolean.valueOf(cooldowns.checkCooldown(cooldownTime, p, kitCooldowns, kitName)) == true) {
										return true;
									};
									for (String val:items) {
										Integer amount = Integer.valueOf(val.replaceAll("[^0-9]", ""));
										String itemName = String.valueOf(val.replaceAll("[^_A-Za-z]", ""));
										ItemStack item = new ItemStack(Material.getMaterial(itemName), amount);
										if (Material.getMaterial(itemName) != null) {
											p.getInventory().addItem(item);
										} else {
											p.sendMessage(handler.getCaption("InvalidItem").replace("{0}", itemName));
										}
									}
									if (cooldownTime > 0 || p.hasPermission("catx.kit." + kitName + ".cooldown") == false) {
										cooldowns.addCooldown(cooldownTime, p.getName()+kitName, kitCooldowns);
									}
									p.sendMessage(handler.getCaption("KitReceived").replace("{0}", kitName));
								}
							} else {
								p.sendMessage(handler.getCaption("NoPermission"));
							}
						} else {
							p.sendMessage(handler.getCaption("KitNotFound"));
						}
					}
					
					return true;
				} else {
					p.sendMessage(handler.getCaption("NoPermission"));
				}
			} else {
		    	sender.sendMessage(handler.getCaption("CommandDisabled"));
		    }
			return true;
		} else if (label.equalsIgnoreCase("setkit")) {
			if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("setkit") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("setkit").getBoolean("enabled") == true) {
				if (p.hasPermission("catx.setkit")) {
					if (args.length == 0) {
						p.sendMessage(handler.getCaption("Usage").replace("{0}", "/setkit").replace("{1}", "<name>"));
					} else {
						Integer kitDelay = null;
						if (args.length >= 2) {
							kitDelay = Integer.valueOf(args[1].replaceAll("[^0-9]", ""));
						}
						List<String> kitItems = new ArrayList<>();
						for (ItemStack item:p.getInventory().getContents()) {
							if (item != null) {
								kitItems.add(item.getData().getItemType().name() + " " + item.getAmount());
							}
						}
						String kitName = args[0];
						config.set("kits." + kitName + ".cooldown", kitDelay);
						config.set("kits." + kitName + ".items", kitItems);
						p.sendMessage(handler.getCaption("KitCreated").replace("{0}", kitName));
						config.saveConfig();
					}
				}
			} else {
		    	sender.sendMessage(handler.getCaption("CommandDisabled"));
		    }
			return true;
		} else if (label.equalsIgnoreCase("delkit")) {
			if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("delkit") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("delkit").getBoolean("enabled") == true) {
				if (p.hasPermission("catx.delkit")) {
					if (args.length == 0) {
						p.sendMessage(handler.getCaption("Usage").replace("{0}", "/delkit").replace("{1}", "<name>"));
					} else {
						String kitName = args[0];
						if (config.getConfigurationSection("kits." + kitName) != null) {
							p.sendMessage(handler.getCaption("KitDeleted").replace("{0}", kitName));
							config.set("kits." + kitName, null);
							config.saveConfig();
						} else {
							p.sendMessage(handler.getCaption("KitNotFound"));
						}
					}
				}
			} else {
		    	sender.sendMessage(handler.getCaption("CommandDisabled"));
		    }
			return true;
		}
		return false;
	}
}
