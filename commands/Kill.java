package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Kill implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public Kill(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("kill").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("kill") != null && this.config.getConfigurationSection("commands").getConfigurationSection("kill").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				if (args.length > 0) {
					if (sender.getServer().getPlayer(args[0]) != null) {
						Player other = (Player) sender.getServer().getPlayer(args[0]);
						sender.getServer().broadcastMessage(
								handler.getCaption("KilledBy").replace("{0}", other.getName()).replace("{1}", "console"));
						other.setHealth(0);
						return true;
					} else {
						sender.sendMessage(handler.getCaption("PlayerNotFound"));
						return true;
					}
				}
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.kill")) {
				if (args.length > 0 && p.hasPermission("catx.kill.others")) {
					if (sender.getServer().getPlayer(args[0]) != null) {
						Player other = (Player) sender.getServer().getPlayer(args[0]);
						if (other.getName() == p.getName()) {
							p.getServer().broadcastMessage(handler.getCaption("KilledSelf").replace("{0}", p.getName()));
							p.setHealth(0);
							return true;
						}
						p.getServer().broadcastMessage(
								handler.getCaption("KilledBy").replace("{0}", other.getName()).replace("{1}", p.getName()));
						other.setHealth(0);
						return true;
					} else {
						p.sendMessage(handler.getCaption("PlayerNotFound"));
						return true;
					}
				} else {
					p.getServer().broadcastMessage(handler.getCaption("KilledSelf").replace("{0}", p.getName()));
					p.setHealth(0);
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
