package me.thicccat.catx.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Info implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
    
    public ConfigManager cManager;
    public Config cConfig;
	
	private Main plugin;

	public Info(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("cat").setExecutor(this);
	}

	public static String[] help = {
			"§6/back §e- Teleports to your previous location.",
			"§6/bc §e- Broadcasts a message to the server.",
			"§6/cat §e- The basic plugin command.",
			"§6/clear §e- Clears a player inventory.",
			"§6/day §e- Sets the time to day in the world you are in.",
			"§6/delhome §e- Deletes the specified home.",
			"§6/delkit §e- Deletes a kit.",
			"§6/delwarp §e- Deletes the specified warp.",
			"§6/exp §e- Adds exp to a player.",
			"§6/feed §e- Fills the foodbar of the specified player.",
			"§6/gmc §e- Sets your gamemode to creative.",
			"§6/gms §e- Sets your gamemode to survival.",
			"§6/heal §e- Heals the specified player.",
			"§6/help §e- Displays this plugin's commands.",
			"§6/home §e- Teleports to a home.",
			"§6/invsee §e- Lets you see the inventory of another player.",
			"§6/kill §e- Kills the specified player.",
			"§6/kit §e- Receives items from a kit.",
			"§6/msg §e- Sends a message to a player.",
			"§6/nick §e- Sets your nickname.",
			"§6/night §e- Sets the time to night in the world you are in.",
			"§6/r §e- Replies a message.",
			"§6/sethome §e- Sets a new home.",
			"§6/setkit §e- Sets a kit from your inventory items.",
			"§6/setspawn §e- Sets a new spawn.",
			"§6/setwarp §e- Sets a new warp.",
			"§6/spawn §e- Teleports to spawn.",
			"§6/starve §e- Starves the specified player.",
			"§6/storm §e- Sets the weather to storm in the current world.",
			"§6/summon §e- Summons the specified mob.",
			"§6/sun §e- Sets the weather to sun in the current world.",
			"§6/tpa §e- The basic plugin command.",
			"§6/tpaccept §e- Sets your gamemode to survival.",
			"§6/tpahere §e- Sets your gamemode to creative.",
			"§6/tpdeny §e- Denies the incoming tpa request.",
			"§6/warp §e- Teleports to the specified warp.",
			"§6/world §e- Teleports to the specified world."
		};
		
	
	public static void sendPageOne(CommandSender sender) {
		help = Stream.of(help).sorted().toArray(String[]::new);
		List<String> shortHelp = Arrays.asList(help);
		sender.sendMessage("§e=============== §6HELP MENU §e===============");
		for (String m:shortHelp.subList(0, 8)) {
			sender.sendMessage(m);
		}
		sender.sendMessage("§e====== §6PAGE 1 §e| §6/help 2 for more. §e=======");
	}

	public static void sendPageTwo(CommandSender sender) {
		help = Stream.of(help).sorted().toArray(String[]::new);
		List<String> shortHelp = Arrays.asList(help);
		sender.sendMessage("§e=============== §6HELP MENU §e===============");
		for (String m:shortHelp.subList(8, 16)) {
			sender.sendMessage(m);
		}
		sender.sendMessage("§e====== §6PAGE 2 §e| §6/help 3 for more. §e=======");
	}

	public static void sendPageThree(CommandSender sender) {
		help = Stream.of(help).sorted().toArray(String[]::new);
		List<String> shortHelp = Arrays.asList(help);
		sender.sendMessage("§e=============== §6HELP MENU §e===============");
		for (String m:shortHelp.subList(16, 24)) {
			sender.sendMessage(m);
		}
		sender.sendMessage("§e====== §6PAGE 3 §e| §6/help 4 for more. §e=======");
	}
	
	public static void sendPageFour(CommandSender sender) {
		help = Stream.of(help).sorted().toArray(String[]::new);
		List<String> shortHelp = Arrays.asList(help);
		sender.sendMessage("§e=============== §6HELP MENU §e===============");
		for (String m:shortHelp.subList(24, 32)) {
			sender.sendMessage(m);
		}
		sender.sendMessage("§e====== §6PAGE 4 §e| §6/help 5 for more. §e=======");
	}
	
	public static void sendPageFive(CommandSender sender) {
		help = Stream.of(help).sorted().toArray(String[]::new);
		List<String> shortHelp = Arrays.asList(help);
		sender.sendMessage("§e=============== §6HELP MENU §e===============");
		for (String m:shortHelp.subList(32, help.length)) {
			sender.sendMessage(m);
		}
		sender.sendMessage("§e========= §6PAGE 5 §e| §6END OF HELP §e=========");
	}

	public void sendVersion(CommandSender sender) {
		sender.sendMessage("§eYou are running §6CatX v1.0.0§e by §6ThiccCat§e.");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("cat") != null && this.config.getConfigurationSection("commands").getConfigurationSection("cat").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
					if (args.length < 2 || args.length >= 2 && args[1].equalsIgnoreCase("1")) {
						sendPageOne(sender);
						return true;
					} else if (args.length >= 2 && args[1].equalsIgnoreCase("2")) {
						sendPageTwo(sender);
						return true;
					} else if (args.length >= 2 && args[1].equalsIgnoreCase("3")) {
						sendPageThree(sender);
						return true;
					} else if (args.length >= 2 && args[1].equalsIgnoreCase("4")) {
						sendPageFour(sender);
						return true;
					} else if (args.length >= 2 && args[1].equalsIgnoreCase("5")) {
						sendPageFive(sender);
						return true;
					}
					return true;
				} else {
					sendVersion(sender);
					return true;
				}
			}
			Player p = (Player) sender;
	
				if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
					if (p.hasPermission("catx.reload")) {
						this.cManager = new ConfigManager(plugin);
						this.cConfig = cManager.getNewConfig("config.yml");
				        this.cConfig.reloadConfig();
				        this.cConfig = cManager.getNewConfig("locations.yml");
				        this.cConfig.reloadConfig();
				        this.cConfig = cManager.getNewConfig("lang.yml");
				        this.cConfig.reloadConfig();
						sender.sendMessage(handler.getCaption("Reloaded"));
						return true;
					} else {
						p.sendMessage(handler.getCaption("NoPermission"));
					}
				} else if (args.length > 0 && args[0].equalsIgnoreCase("help")) {
					if (p.hasPermission("catx.help")) {
						if (args.length < 2 || args.length >= 2 && args[1].equalsIgnoreCase("1")) {
							sendPageOne(p);
							return true;
						} else if (args.length >= 2 && args[1].equalsIgnoreCase("2")) {
							sendPageTwo(p);
							return true;
						} else if (args.length >= 2 && args[1].equalsIgnoreCase("3")) {
							sendPageThree(p);
							return true;
						} else if (args.length >= 2 && args[1].equalsIgnoreCase("5")) {
							sendPageFive(p);
							return true;
						}
					} else {
						p.sendMessage(handler.getCaption("NoPermission"));
					}
				} else {
					sendVersion(p);
					return true;
				}
				return true;
	    } else {
	    	sender.sendMessage(handler.getCaption("CommandDisabled"));
	    }
		return false;
	}
}
