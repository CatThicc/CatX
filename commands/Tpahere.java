package me.thicccat.catx.commands;

import java.util.HashMap;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;
import me.thicccat.catx.listeners.PlayerEvents;

public class Tpahere implements CommandExecutor {
	
	public ConfigManager manager;
    public Config config;
	
	private Main plugin;
	
	public Tpahere(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("tpahere").setExecutor(this);
		plugin.getCommand("tpaccept").setExecutor(this);
		plugin.getCommand("tpdeny").setExecutor(this);
	}
	
	HashMap<Player, Player> tpa2 = new HashMap<Player, Player>();
	World othW = null;
	Double othX = null;
	Double othY = null;
	Double othZ = null;
	Player oth = null;
	Player sent = null;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		if (!(sender instanceof Player)) {
			sender.sendMessage(handler.getCaption("ConsoleForbidden"));
			return true;
		}
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("tpahere")) {
			this.manager = new ConfigManager(plugin);
		    this.config = manager.getNewConfig("config.yml");
		    if (this.config.getConfigurationSection("commands").getConfigurationSection("tpahere") != null && this.config.getConfigurationSection("commands").getConfigurationSection("tpahere").getBoolean("enabled") == true) {
				if (p.hasPermission("catx.tpahere")) {
					if (args.length == 0) {
						p.sendMessage(handler.getCaption("Usage").replace("{0}", "/tpahere").replace("{1}", "<player>"));
						return true;
					} else if (args.length >= 1) {
						if (p.getServer().getPlayer(args[0]) == null) {
							return true;
						} else {
							Player other = p.getServer().getPlayer(args[0]);
							p.sendMessage(handler.getCaption("TpaRequestSent").replace("{0}", other.getName()));
							other.sendMessage(handler.getCaption("TpahereRequestReceived").replace("{0}", p.getName()));
							other.sendMessage(handler.getCaption("TpaOption").replace("{0}", "accept"));
							other.sendMessage(handler.getCaption("TpaOption").replace("{0}", "deny"));
							other.sendMessage(handler.getCaption("TpaTime").replace("{0}", "90"));
							
							tpa2.put(other, p);
							this.othW = p.getLocation().getWorld();
							this.othX = p.getLocation().getX();
							this.othY = p.getLocation().getY();
							this.othZ = p.getLocation().getZ();
							this.oth = other;
							this.sent = p;
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
					        {
					            public void run()
					            {
					                tpa2.put(other, null);
					            }
					        }, 1800L);
							return true;
						}
					}
				} else {
					p.sendMessage(handler.getCaption("NoPermission"));
				}
		    } else {
		    	sender.sendMessage(handler.getCaption("CommandDisabled"));
		    }
		    return true;
	} else if (label.equalsIgnoreCase("tpaccept") || label.equalsIgnoreCase("tpyes")) {
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("tpaccept") != null && this.config.getConfigurationSection("commands").getConfigurationSection("tpaccept").getBoolean("enabled") == true) {
			if (p.hasPermission("catx.tpa") || p.hasPermission("catx.tpahere")) {
				if (tpa2.get(this.oth) != null) {
					PlayerEvents.lastCoords.put(oth, oth.getLocation());
					this.oth.teleport(this.sent);
					this.oth.sendMessage(handler.getCaption("TpaRequestAccepted"));
					this.sent.sendMessage(handler.getCaption("TpaRequestAccepted"));
					tpa2.put(this.oth, null);
					return true;
				} else if (Tpa.tpa1.get(p) != null) {
					PlayerEvents.lastCoords.put(oth, oth.getLocation());
					Tpa.tpa1.get(p).teleport(p);
					Tpa.oth1.sendMessage(handler.getCaption("TpaRequestAccepted"));
					Tpa.sent1.sendMessage(handler.getCaption("TpaRequestAccepted"));
					Tpa.tpa1.put(p, null);
				}else {
					p.sendMessage(handler.getCaption("NoTpaRequests"));
					return true;
				}
			} else {
				p.sendMessage(handler.getCaption("NoPermission"));
			}
	    } else {
	    	sender.sendMessage(handler.getCaption("CommandDisabled"));
	    }
	    return true;
	} else if (label.equalsIgnoreCase("tpdeny") || label.equalsIgnoreCase("tpno")) {
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("tpdeny") != null && this.config.getConfigurationSection("commands").getConfigurationSection("tpdeny").getBoolean("enabled") == true) {
			if (p.hasPermission("catx.tpa") || p.hasPermission("catx.tpahere")) {
				if (tpa2.get(p) != null) {
					tpa2.put(p, null);
					this.oth.sendMessage(handler.getCaption("TpaRequestDenied"));
					this.sent.sendMessage(handler.getCaption("TpaRequestDenied"));
					return true;
				} else if (Tpa.tpa1.get(p) != null) {
					Tpa.tpa1.put(p, null);
					Tpa.oth1.sendMessage(handler.getCaption("TpaRequestDenied"));
					Tpa.sent1.sendMessage(handler.getCaption("TpaRequestDenied"));
				} else {
					p.sendMessage(handler.getCaption("NoTpaRequests"));
					return true;
				}
			} else {
				p.sendMessage(handler.getCaption("NoPermission"));
			}
		} else {
	    	sender.sendMessage(handler.getCaption("CommandDisabled"));
	    }
	    return true;
	}
		return false;
	}
}
