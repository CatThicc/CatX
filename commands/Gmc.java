package me.thicccat.catx.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Gmc implements CommandExecutor {
	
	public ConfigManager manager;
    public Config config;
	
	private Main plugin;
	
	public Gmc(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("gmc").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("gmc") != null && this.config.getConfigurationSection("commands").getConfigurationSection("gmc").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
			if (args.length == 0) {
				if (p.hasPermission("catx.gmc")) {
					Player me = p.getServer().getPlayer(p.getName());
					me.sendMessage(handler.getCaption("GamemodeSet").replace("{0}", "creative"));
					me.setGameMode(GameMode.CREATIVE);
					return true;
				} else {
					p.sendMessage(handler.getCaption("NoPermission"));
				}
			} else {
				if (p.hasPermission("catx.gmc.others")) {
					Player other = p.getServer().getPlayer(args[0]);
					other.sendMessage(handler.getCaption("GamemodeSet").replace("{0}", "creative"));
					p.setGameMode(GameMode.CREATIVE);
					return true;
				} else {
					p.sendMessage(handler.getCaption("NoPermission"));
				}
			}
	    } else {
	    	sender.sendMessage(handler.getCaption("CommandDisabled"));
	    }
		return false;
	}
}
