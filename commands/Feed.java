package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Feed implements CommandExecutor {
	
	public ConfigManager manager;
    public Config config;
	
	private Main plugin;
	
	public Feed(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("feed").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("feed") != null && this.config.getConfigurationSection("commands").getConfigurationSection("feed").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					if (sender.getServer().getPlayer(args[0]) != null) {
						Player other = (Player) sender.getServer().getPlayer(args[0]);
						other.sendMessage(handler.getCaption("PlayerNotFound"));
						other.setFoodLevel(20);
						return true;
					} else {
						sender.sendMessage(handler.getCaption("PlayerNotFound"));
						return true;
					}
				}
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.feed")) {
				if (args.length > 0 && p.hasPermission("catx.feed.others")) {
					if (sender.getServer().getPlayer(args[0]) != null) {
						Player other = (Player) sender.getServer().getPlayer(args[0]);
						if (other.getName() == p.getName()) {
							p.sendMessage(handler.getCaption("WasFed"));
							p.setFoodLevel(20);
							return true;
						}
						other.sendMessage(handler.getCaption("WasFed"));
						other.setFoodLevel(20);
						return true;
					} else {
						p.sendMessage(handler.getCaption("PlayerNotFound"));
						return true;
					}
				} else {
					p.sendMessage(handler.getCaption("WasFed"));
					p.setFoodLevel(20);
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
	