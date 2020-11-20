package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Exp implements CommandExecutor {
	
	public ConfigManager manager;
    public Config config;
	
	private Main plugin;
	
	public Exp(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("exp").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("exp") != null && this.config.getConfigurationSection("commands").getConfigurationSection("exp").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
			
			if (p.hasPermission("catx.exp")) {
				if (args.length == 0) {
					sender.sendMessage(handler.getCaption("Usage").replace("{0}", "/exp").replace("{1}", "<player>") + " <amount>");
				} else {
					if (args.length >= 1) {
						Player toEffect = plugin.getServer().getPlayer(args[0]);
						if (toEffect != null) {
							if (args.length >= 2) {
								Integer amount = Integer.valueOf(args[1]);
								toEffect.setTotalExperience(toEffect.getTotalExperience() + amount);
								p.sendMessage(handler.getCaption("ExpSet").replace("{0}", toEffect.getName()).replace("{1}", amount.toString()));
							} else {
								p.sendMessage(handler.getCaption("InvalidSyntax"));
							}
						} else {
							p.sendMessage(handler.getCaption("PlayerNotFound"));
						}
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
