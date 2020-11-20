package me.thicccat.catx.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Whisper implements CommandExecutor {
	
	public ConfigManager manager;
    public Config config;
	
	private Main plugin;
	
	public Whisper(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("msg").setExecutor(this);
	}
	
	public static HashMap<Player, Player> msg = new HashMap<Player, Player>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("msg") != null && this.config.getConfigurationSection("commands").getConfigurationSection("msg").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.msg")) {
				if (args.length == 0) {
					p.sendMessage(handler.getCaption("Usage").replace("{0}", "/msg").replace("{1}", "<player>") + " <message>");
				} else {
					Player other = plugin.getServer().getPlayer(args[0]);
					if (args.length > 1) {
						String message = String.join(" ", args).substring(args[0].length()+1, String.join(" ", args).length());
						message = handler.addColor(message);
						p.sendMessage(handler.getCaption("Messaging").replace("{0}", "me").replace("{1}", other.getDisplayName()).replace("{2}", message));
						other.sendMessage(handler.getCaption("Messaging").replace("{0}", p.getDisplayName()).replace("{1}", "me").replace("{2}", message));
						msg.put(other, p);
					} else {
						p.sendMessage(handler.getCaption("InvalidSyntax"));
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
