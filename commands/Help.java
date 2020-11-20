package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Help implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public Help(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("help").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("help") != null && this.config.getConfigurationSection("commands").getConfigurationSection("help").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				if (args.length == 0 || args.length > 0 && args[0].equalsIgnoreCase("1")) {
					Info.sendPageOne(sender);
					return true;
				} else if (args.length > 0 && args[0].equalsIgnoreCase("2")) {
					Info.sendPageTwo(sender);
					return true;
				} else if (args.length > 0 && args[0].equalsIgnoreCase("3")) {
					Info.sendPageThree(sender);
					return true;
				} else if (args.length > 0 && args[0].equalsIgnoreCase("4")) {
					Info.sendPageFour(sender);
					return true;
				} else if (args.length > 0 && args[0].equalsIgnoreCase("5")) {
					Info.sendPageFive(sender);
					return true;
				}
				return true;
			}
			Player p = (Player) sender;
	
			if (p.hasPermission("catx.help")) {
				if (args.length == 0 || args.length > 0 && args[0].equalsIgnoreCase("1")) {
					Info.sendPageOne(p);
					return true;
				} else if (args.length > 0 && args[0].equalsIgnoreCase("2")) {
					Info.sendPageTwo(p);
					return true;
				} else if (args.length > 0 && args[0].equalsIgnoreCase("3")) {
					Info.sendPageThree(p);
					return true;
				} else if (args.length > 0 && args[0].equalsIgnoreCase("4")) {
					Info.sendPageFour(p);
					return true;
				} else if (args.length > 0 && args[0].equalsIgnoreCase("5")) {
					Info.sendPageFive(p);
					return true;
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
