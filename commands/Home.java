package me.thicccat.catx.commands;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;
import me.thicccat.catx.listeners.PlayerEvents;

public class Home implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
    
    public ConfigManager cManager;
    public Config cConfig;
    
	private Main plugin;

	public Home(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("home").setExecutor(this);
		plugin.getCommand("sethome").setExecutor(this);
		plugin.getCommand("delhome").setExecutor(this);
	}

	public String name = "home";

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		this.cManager = new ConfigManager(plugin);
	    this.cConfig = cManager.getNewConfig("config.yml");
		this.manager = new ConfigManager((JavaPlugin) plugin);
		this.config = manager.getNewConfig("locations.yml");
		
			CaptionHandler handler = new CaptionHandler(plugin);
			Player p = (Player) sender;
	
			if (p.hasPermission("catx.home")) {
				if (sender instanceof Player) {
					if (args.length > 0) {
						this.name = args[0];
					} else {
						this.name = "home";
					}
					if (label.equalsIgnoreCase("sethome")) {
						if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("sethome") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("sethome").getBoolean("enabled") == true) {
							config.set(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".x",
									Integer.valueOf(p.getLocation().getBlockX()));
							config.set(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".y",
									Integer.valueOf(p.getLocation().getBlockY()));
							config.set(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".z",
									Integer.valueOf(p.getLocation().getBlockZ()));
							config.set(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".world",
									String.valueOf(p.getWorld().getName()));
							config.saveConfig();
							p.sendMessage(handler.getCaption("HomeSaved").replace("{0}", this.name));
						} else {
					    	sender.sendMessage(handler.getCaption("CommandDisabled"));
					    }
						return true;
					} else if (label.equalsIgnoreCase("home")) {
						if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("home") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("home").getBoolean("enabled") == true) {
							if (config.getConfigurationSection("homes" + "." + p.getName()) != null) {
								if (config.getConfigurationSection("homes" + "." + p.getName()).getKeys(false).size() == 0) {
									p.sendMessage(handler.getCaption("NoHomeSet"));
								} else if (config.getConfigurationSection("homes" + "." + p.getName()).getKeys(false).size() > 1 && args.length == 0) {
										String HomeList = handler.getCaption("HomeList");
										String commaColor = null;
										String nameColor = null;
										if (HomeList.substring(0, 1).equals("&")) {
											commaColor = "§" + HomeList.substring(1, 2);
										} else {
											commaColor = "§f";
										}
										if (HomeList.substring(HomeList.indexOf("{")-2, HomeList.indexOf("{")-1).equals("&")) {
											nameColor = "§" + HomeList.substring(HomeList.indexOf("{")-1, HomeList.indexOf("{"));
										} else {
											nameColor = "§f";
										}
										HomeList = handler.addColor(HomeList);
										p.sendMessage(HomeList.replace("{0}", String.join(commaColor + "," + nameColor + " ", config.getConfigurationSection("homes" + "." + p.getName()).getKeys(false))));
								} else if (config.getConfigurationSection("homes" + "." + p.getName()).getKeys(false).size() == 1){
									Set<String> se = config.getConfigurationSection("homes").getConfigurationSection(p.getName()).getKeys(false);
									String getHome = Arrays.asList(se).get(0).toString().replace("[", "").replace("]", "");
									if (config.get(String.valueOf("homes" + "." + p.getName()) + "." + getHome + ".x") != null) {
										int x = config.getInt(String.valueOf("homes" + "." + p.getName()) + "." + getHome + ".x");
										int y = config.getInt(String.valueOf("homes" + "." + p.getName()) + "." + getHome + ".y");
										int z = config.getInt(String.valueOf("homes" + "." + p.getName()) + "." + getHome + ".z");
										String w = config.getString(String.valueOf("homes" + "." + p.getName()) + "." + getHome + ".world");
										PlayerEvents.lastCoords.put(p, p.getLocation());
										p.teleport(new Location(p.getServer().getWorld(w), x, y, z));
									} else {
										p.sendMessage(handler.getCaption("HomeNotFound"));
									}
									return true;
								} else {
									if (config.get(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".x") != null) {
										int x = config.getInt(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".x");
										int y = config.getInt(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".y");
										int z = config.getInt(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".z");
										String w = config.getString(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".world");
										PlayerEvents.lastCoords.put(p, p.getLocation());
										p.teleport(new Location(p.getServer().getWorld(w), x, y, z));
									} else {
										p.sendMessage(handler.getCaption("HomeNotFound"));
									}
								}
							} else {
								p.sendMessage(handler.getCaption("NoHomeSet"));
							}
						} else {
					    	sender.sendMessage(handler.getCaption("CommandDisabled"));
					    }
						return true;
					} else if (label.equalsIgnoreCase("delhome")) {
						if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("delhome") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("delhome").getBoolean("enabled") == true) {
							if (config.get(String.valueOf("homes" + "." + p.getName()) + "." + this.name + ".x") != null) {
								config.set(String.valueOf("homes" + "." + p.getName()) + "." + this.name, null);
								config.saveConfig();
								p.sendMessage(handler.getCaption("HomeDeleted").replace("{0}", this.name));
							} else {
								p.sendMessage(handler.getCaption("HomeNotFound"));
							}
							return true;
						} else {
					    	sender.sendMessage(handler.getCaption("CommandDisabled"));
					    }
						return true;
					}
				}
			} else {
				p.sendMessage(handler.getCaption("NoPermission"));
			}
		return false;
	}

}
