package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Night implements CommandExecutor {
	
	public ConfigManager manager;
    public Config config;
	
	private Main plugin;
	
	public Night(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("night").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("night") != null && this.config.getConfigurationSection("commands").getConfigurationSection("night").getBoolean("enabled") == true) {
			ConsoleCommandSender console = sender.getServer().getConsoleSender();
			if (console != null) {
				sender.sendMessage(handler.getCaption("TimeSet").replace("{0}", "night").replace("{1}", sender.getServer().getWorlds().get(0).getName()));
				sender.getServer().getWorlds().get(0).setTime(15000);
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.night")) {
				p.sendMessage(handler.getCaption("TimeSet").replace("{0}", "night").replace("{1}", p.getWorld().getName()));
				p.getWorld().setTime(15000);
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
