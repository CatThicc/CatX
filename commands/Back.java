package me.thicccat.catx.commands;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;
import me.thicccat.catx.listeners.PlayerEvents;

public class Back implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public Back(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("back").setExecutor(this);
	}
	
	HashMap<Player, Location> lastSpot = PlayerEvents.lastCoords;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("back") != null && this.config.getConfigurationSection("commands").getConfigurationSection("back").getBoolean("enabled") == true) {
	    	if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.back")) {
				if (lastSpot.get(p) != null) {
					Location before = p.getLocation();
					p.teleport(lastSpot.get(p));
					PlayerEvents.lastCoords.put(p, before);
					p.sendMessage(handler.getCaption("Teleporting"));
				} else {
					p.sendMessage(handler.getCaption("NotTeleportedYet"));
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
