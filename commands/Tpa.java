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

public class Tpa implements CommandExecutor {
	
	public ConfigManager manager;
    public Config config;
	
	private Main plugin;
	
	public Tpa(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("tpa").setExecutor(this);
	}
	
	static HashMap<Player, Player> tpa1 = new HashMap<Player, Player>();
	static Player oth1 = null;
	static Player sent1 = null;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("tpa") != null && this.config.getConfigurationSection("commands").getConfigurationSection("tpa").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
			if (label.equalsIgnoreCase("tpa")) {
				if (p.hasPermission("catx.tpa")) {
					if (args.length == 0) {
						p.sendMessage(handler.getCaption("Usage").replace("{0}", "/tpa").replace("{1}", "<player>"));
						return true;
					} else if (args.length >= 1) {
						if (p.getServer().getPlayer(args[0]) == null) {
							p.sendMessage(handler.getCaption("PlayerNotFound"));
							return true;
						} else {
							Player other = p.getServer().getPlayer(args[0]);
							p.sendMessage(handler.getCaption("TpaRequestSent").replace("{0}", other.getName()));
							other.sendMessage(handler.getCaption("TpaRequestReceived").replace("{0}", p.getName()));
							other.sendMessage(handler.getCaption("TpaOption").replace("{0}", "accept"));
							other.sendMessage(handler.getCaption("TpaOption").replace("{0}", "deny"));
							other.sendMessage(handler.getCaption("TpaTime").replace("{0}", "90"));
							Tpa.oth1 = other;
							Tpa.sent1 = p;
							tpa1.put(other, p);
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
					        {
					            public void run()
					            {
					                tpa1.put(other, null);
					            }
					        }, 1800L);
							return true;
						}
					}
				} else {
					p.sendMessage("§cYou don't have permission to use this command!");
				}
			}
	    } else {
	    	sender.sendMessage(handler.getCaption("CommandDisabled"));
	    }
		return false;
	}
}
