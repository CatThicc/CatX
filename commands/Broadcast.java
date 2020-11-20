package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Broadcast implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public Broadcast(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("broadcast").setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("broadcast") != null && this.config.getConfigurationSection("commands").getConfigurationSection("broadcast").getBoolean("enabled") == true) {
			Player p = (Player) sender;
			if (sender instanceof Player) {
				if (p.hasPermission("catx.broadcast")) {
					if (args.length == 0) {
						p.sendMessage(handler.getCaption("Usage").replace("{0}", "/broadcast").replace("{1}", "<message>"));
					} else {
						String message = String.join(" ", args);
						message = handler.addColor(message);
						p.getServer().broadcastMessage(handler.getCaption("Broadcast").replace("{0}", message));
					}
					return true;
				} else {
					p.sendMessage(handler.getCaption("NoPermission"));
				}
			} else {
				if (args.length == 0) {
					sender.sendMessage(handler.getCaption("Usage").replace("{0}", "/broadcast").replace("{1}", "<message>"));
				} else {
					String message = String.join(" ", args);
					message = handler.addColor(message);
					sender.getServer().broadcastMessage(handler.getCaption("Broadcast").replace("{0}", message));
				}
				return true;
			}
	    } else {
	    	sender.sendMessage(handler.getCaption("CommandDisabled"));
	    }
		return false;
	}

}
