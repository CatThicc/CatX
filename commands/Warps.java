package me.thicccat.catx.commands;

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

public class Warps implements CommandExecutor {
	
	public ConfigManager manager;	 
    public Config config;
    
    public ConfigManager cManager;
    public Config cConfig;

	private Main plugin;

	public Warps(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("warp").setExecutor(this);
		plugin.getCommand("setwarp").setExecutor(this);
		plugin.getCommand("delwarp").setExecutor(this);
	}

	String name = null;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		this.cManager = new ConfigManager(plugin);
	    this.cConfig = cManager.getNewConfig("config.yml");
		this.manager = new ConfigManager((JavaPlugin) plugin);
		this.config = manager.getNewConfig("locations.yml");
		
		CaptionHandler handler = new CaptionHandler(plugin);
		Player p = (Player) sender;

			if (sender instanceof Player) {
					if (label.equalsIgnoreCase("setwarp")) {
						if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("setwarp") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("setwarp").getBoolean("enabled") == true) {
							if (p.hasPermission("catx.setwarp")) {
								if (args.length > 0) {
									this.name = args[0];
										config.set("warps" + "." + this.name + ".x",
												Integer.valueOf(p.getLocation().getBlockX()));
										config.set("warps" + "." + this.name + ".y",
												Integer.valueOf(p.getLocation().getBlockY()));
										config.set("warps" + "." + this.name + ".z",
												Integer.valueOf(p.getLocation().getBlockZ()));
										config.saveConfig();
										p.sendMessage(handler.getCaption("WarpSaved").replace("{0}", this.name));
								} else {
									p.sendMessage(handler.getCaption("Usage").replace("{0}", "/setwarp").replace("{1}", "<name>"));
								}
							} else {
								p.sendMessage(handler.getCaption("NoPermission"));
							}
						} else {
					    	sender.sendMessage(handler.getCaption("CommandDisabled"));
					    }
						return true;
					} else if (label.equalsIgnoreCase("warp")) {
						if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("warp") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("warp").getBoolean("enabled") == true) {
							if (p.hasPermission("catx.warp")) {
									if (config.getConfigurationSection("warps").getKeys(false).size() > 0 && args.length == 0) {
										if (p.hasPermission("catx.warp.list")) {
											String WarpList = handler.getCaption("WarpList");
											String commaColor = null;
											String nameColor = null;
											if (WarpList.substring(0, 1).equals("&")) {
												commaColor = "§" + WarpList.substring(1, 2);
											} else {
												commaColor = "§f";
											}
											if (WarpList.substring(WarpList.indexOf("{")-2, WarpList.indexOf("{")-1).equals("&")) {
												nameColor = "§" + WarpList.substring(WarpList.indexOf("{")-1, WarpList.indexOf("{"));
											} else {
												nameColor = "§f";
											}
											WarpList = handler.addColor(WarpList);
											p.sendMessage(WarpList.replace("{0}", String.join(commaColor + "," + nameColor + " ", config.getConfigurationSection("warps").getKeys(false))));
										}
									} else if (config.getConfigurationSection("warps").getKeys(false).size() == 0 && args.length == 0) {
										p.sendMessage(handler.getCaption("NoWarpSet"));
									} else if (args.length > 0){
										this.name = args[0];
										if (config.get("warps" + "." + this.name + ".x") != null) {
											int x = config.getInt("warps" + "." + this.name + ".x");
											int y = config.getInt("warps" + "." + this.name + ".y");
											int z = config.getInt("warps" + "." + this.name + ".z");
											String w = config.getString("warps" + "." + this.name + ".world");
											PlayerEvents.lastCoords.put(p, p.getLocation());
											p.teleport(new Location(p.getServer().getWorld(w), x, y, z));
										} else {
											p.sendMessage(handler.getCaption("WarpNotFound"));
										}
									}
							} else {
								p.sendMessage(handler.getCaption("NoPermission"));
							}
						} else {
					    	sender.sendMessage(handler.getCaption("CommandDisabled"));
					    }
						return true;
					} else if (label.equalsIgnoreCase("delwarp")) {
						if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("delwarp") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("delwarp").getBoolean("enabled") == true) {
							if (p.hasPermission("catx.delwarp")) {
								if (args.length > 0) {
									this.name = args[0];
									if (config.get("warps" + "." + this.name + ".x") != null) {
										config.set("warps" + "." + this.name, null);
										config.saveConfig();
										p.sendMessage(handler.getCaption("WarpDeleted").replace("{0}", this.name));
									} else {
										p.sendMessage(handler.getCaption("WarpNotFound"));
									}
									return true;
								} else {
									p.sendMessage(handler.getCaption("Usage").replace("{0}", "/delwarp").replace("{1}", "<name>"));
								}
							} else {
								p.sendMessage(handler.getCaption("NoPermission"));
							}
						} else {
					    	sender.sendMessage(handler.getCaption("CommandDisabled"));
					    }
					}
					return true;
			}
		return false;
	}

}
