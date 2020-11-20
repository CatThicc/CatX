package me.thicccat.catx.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class cWorld implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public cWorld(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("world").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("world") != null && this.config.getConfigurationSection("commands").getConfigurationSection("world").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.world")) {
				if (args.length == 0) {
					p.sendMessage(handler.getCaption("Usage").replace("{0}", "/world").replace("{1}", "<worldname>"));
				} else {
					if (p.getServer().getWorld(args[0]) != null) {
						World getByName = p.getServer().getWorld(args[0]);
						if (p.getWorld() == getByName) {
							p.sendMessage(handler.getCaption("AlreadyInWorld"));
							return true;
						}
						Double x = p.getLocation().getX();
						Double y = p.getLocation().getY();
						Double z = p.getLocation().getX();
						Location given = new Location(getByName, x, y, z);
						Location goTo = new Location(getByName, x, getByName.getHighestBlockYAt(given), z);
						p.teleport(goTo);
					} else {
						List<String> validWorlds = new ArrayList<String>();
						for (World w : p.getServer().getWorlds()) {
							validWorlds.add(w.getName());
						}
						p.sendMessage(handler.getCaption("WorldNotFound").replace("{0}", String.join(", ", validWorlds)));
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
