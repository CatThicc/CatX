package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Weather implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public Weather(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("weather").setExecutor(this);
		plugin.getCommand("sun").setExecutor(this);
		plugin.getCommand("storm").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("weather") != null && this.config.getConfigurationSection("commands").getConfigurationSection("weather").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
	
			if (p.hasPermission("catx.weather")) {
				if (args.length == 0) {
					if (label.equalsIgnoreCase("sun")) {
						p.getWorld().setStorm(false);
						p.sendMessage(handler.getCaption("WeatherSet").replace("{0}", "sun").replace("{1}",
								p.getWorld().getName()));
					} else if (label.equalsIgnoreCase("storm") || label.equalsIgnoreCase("thunder")) {
						p.getWorld().setStorm(true);
						p.sendMessage(handler.getCaption("WeatherSet").replace("{0}", "storm").replace("{1}",
								p.getWorld().getName()));
					} else if (label.equalsIgnoreCase("weather")) {
						p.sendMessage(handler.getCaption("Usage").replace("{0}", "/weather").replace("{1}", "<storm | sun>"));
					}
				} else if (args.length > 0 && label.equalsIgnoreCase("weather")) {
					if (args[0].equalsIgnoreCase("sun")) {
						p.getWorld().setStorm(false);
						p.sendMessage(handler.getCaption("WeatherSet").replace("{0}", "sun").replace("{1}",
								p.getWorld().getName()));
					} else if (args[0].equalsIgnoreCase("storm") || args[0].equalsIgnoreCase("thunder")) {
						p.getWorld().setStorm(true);
						p.sendMessage(handler.getCaption("WeatherSet").replace("{0}", "storm").replace("{1}",
								p.getWorld().getName()));
					}
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
