package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Summon implements CommandExecutor {
	
	public ConfigManager manager;
    public Config config;
	
	private Main plugin;
	
	public Summon(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("summon").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("summon") != null && this.config.getConfigurationSection("commands").getConfigurationSection("summon").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.summon")) {
				if (args.length == 0) {
					p.sendMessage(handler.getCaption("Usage").replace("{0}", "/summon").replace("{1}", "<mob>"));
					return true;
				} else if (args.length == 1) {
					args[0] = args[0].toLowerCase().substring(0, 1).toUpperCase() + args[0].toLowerCase().substring(1);
					if (EntityType.fromName(args[0]) != null) {
						p.getWorld().spawnCreature(p.getLocation(), EntityType.fromName(String.join(" ", args)));
						p.sendMessage(handler.getCaption("Summoned").replace("{0}", String.join(" ", args)));
					} else {
						p.sendMessage(handler.getCaption("InvalidMobType"));
					}
					return true;
				} else if (args.length > 1) {
					args[0] = args[0].toLowerCase().substring(0, 1).toUpperCase() + args[0].toLowerCase().substring(1);
					args[1] = args[1].toLowerCase().substring(0, 1).toUpperCase() + args[1].toLowerCase().substring(1);
					if (EntityType.fromName(String.join(" ", args)) != null) {
						p.getWorld().spawnCreature(p.getLocation(), EntityType.fromName(String.join(" ", args)));
						p.sendMessage(handler.getCaption("Summoned").replace("{0}", String.join(" ", args)));
					} else {
						p.sendMessage(handler.getCaption("InvalidMobType"));
					}
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
