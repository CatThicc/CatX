package me.thicccat.catx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Nickname implements CommandExecutor {

	public ConfigManager manager; 
    public Config config;
    
    public ConfigManager cManager;
    public Config cConfig;
	
	private Main plugin;

	public Nickname(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("nickname").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.cManager = new ConfigManager(plugin);
	    this.cConfig = cManager.getNewConfig("config.yml");
	    if (this.cConfig.getConfigurationSection("commands").getConfigurationSection("nick") != null && this.cConfig.getConfigurationSection("commands").getConfigurationSection("nick").getBoolean("enabled") == true) {
			this.manager = new ConfigManager(plugin);
	        this.config = manager.getNewConfig("config.yml");
			
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			Player p = (Player) sender;
			if (p.hasPermission("catx.nickname")) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("none")) {
						config.set("nicknames." + p.getName(), null);
						p.setDisplayName(p.getName());
						p.sendMessage(handler.getCaption("NicknameOff"));
						config.saveConfig();
						return true;
					} else {
						String name = args[0].toString() + "&f";
						String noColorName = handler.removeColor(name);
						for(String s:config.getConfigurationSection("nicknames").getKeys(false)){
							for(String e:config.getConfigurationSection("nicknames." + s).getKeys(false)){
								if (e.equalsIgnoreCase("rawnickname")) {
									if (config.getString("nicknames." + s + ".rawnickname").equalsIgnoreCase(noColorName)) {
										p.sendMessage(handler.getCaption("NicknameTaken"));
										return true;
									}
								}
							}
						}
						name = handler.addColor(name);
						p.setDisplayName(name);
						p.sendMessage(handler.getCaption("SetNickname").replace("{0}", name));
						config.set("nicknames." + p.getName() + ".nickname", name);
						config.set("nicknames." + p.getName() + ".rawnickname", noColorName);
						config.saveConfig();
					}
				} else {
					p.sendMessage(handler.getCaption("Usage").replace("{0}", "/nickname").replace("{1}", "<nickname>"));
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
