package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.IInventory;

public class Invsee implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public Invsee(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("invsee").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("invsee") != null && this.config.getConfigurationSection("commands").getConfigurationSection("invsee").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.invsee")) {
				if (args.length > 0) {
					Player other = p.getServer().getPlayer(args[0]);
					if (other != null) {
						if (other == p) {
							p.sendMessage(handler.getCaption("SeeOwnInv"));
							return true;
						}
						EntityPlayer toGetE;
						CraftPlayer toGetC;
						EntityPlayer ePlayer;
						CraftPlayer cPlayer;
						cPlayer = (CraftPlayer) p;
						ePlayer = cPlayer.getHandle();
						toGetC = (CraftPlayer) other;
						toGetE = toGetC.getHandle();
						IInventory inv = toGetE.inventory;
						ePlayer.a(inv);
					} else {
						p.sendMessage(handler.getCaption("PlayerNotFound"));
					}
				} else {
					p.sendMessage(handler.getCaption("Usage").replace("{0}", "/invsee").replace("{1}", "<player>"));
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
