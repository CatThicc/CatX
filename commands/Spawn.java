package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;
import me.thicccat.catx.listeners.PlayerEvents;

public class Spawn implements CommandExecutor {
	
	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public Spawn(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("spawn").setExecutor(this);
		plugin.getCommand("setspawn").setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("spawn") != null && this.config.getConfigurationSection("commands").getConfigurationSection("spawn").getBoolean("enabled") == true) {
			Player p = (Player) sender;
	
			if (sender instanceof Player) {
				if (label.equalsIgnoreCase("setspawn")) {
					if (p.hasPermission("catx.setspawn")) {
						p.getWorld().setSpawnLocation(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
						p.sendMessage(handler.getCaption("SpawnSet").replace("{0}", p.getWorld().getName()));
						return true;
					} else {
						p.sendMessage(handler.getCaption("NoPermission"));
					}
				} else if(label.equalsIgnoreCase("spawn")) {
					if (p.hasPermission("catx.spawn")) {
						PlayerEvents.lastCoords.put(p, p.getLocation());
						p.teleport(p.getWorld().getSpawnLocation());
						p.sendMessage(handler.getCaption("Teleporting"));
						return true;
					} else {
						p.sendMessage(handler.getCaption("NoPermission"));
					}
				}
			} else {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
			}
	    } else {
	    	sender.sendMessage(handler.getCaption("CommandDisabled"));
	    }
		return false;
	}

}
