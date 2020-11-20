package me.thicccat.catx.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.thicccat.catx.Main;
import me.thicccat.catx.handlers.CaptionHandler;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;

public class Enchant implements CommandExecutor {

	public ConfigManager manager;
    public Config config;
	
	private Main plugin;

	public Enchant(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("enchant").setExecutor(this);
	}
	
	HashMap<Enchantment, Integer> maxLevel = new HashMap<Enchantment, Integer>();
	
	public Enchantment getEnchantment(String enchString) {
        enchString = enchString.toLowerCase().replaceAll("[ _-]", "");
	    switch (enchString.trim().toLowerCase()) {
		    case "protection":
		    	maxLevel.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
	            return Enchantment.PROTECTION_ENVIRONMENTAL;
		    case "prot":
		    	maxLevel.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
	            return Enchantment.PROTECTION_ENVIRONMENTAL;
		    case "fireprotection":
		    	maxLevel.put(Enchantment.PROTECTION_FIRE, 4);
	            return Enchantment.PROTECTION_FIRE;
		    case "fireprot":
		    	maxLevel.put(Enchantment.PROTECTION_FIRE, 4);
	            return Enchantment.PROTECTION_FIRE;
		    case "protectionfire":
		    	maxLevel.put(Enchantment.PROTECTION_FIRE, 4);
	            return Enchantment.PROTECTION_FIRE;
		    case "protfire":
		    	maxLevel.put(Enchantment.PROTECTION_FIRE, 4);
	            return Enchantment.PROTECTION_FIRE;
		    case "featherfalling":
		    	maxLevel.put(Enchantment.PROTECTION_FALL, 5);
	            return Enchantment.PROTECTION_FALL;
		    case "feather":
		    	maxLevel.put(Enchantment.PROTECTION_FALL, 5);
	            return Enchantment.PROTECTION_FALL;
	        case "blastprotection":
		    	maxLevel.put(Enchantment.PROTECTION_EXPLOSIONS, 4);
	            return Enchantment.PROTECTION_EXPLOSIONS;
	        case "blastprot":
		    	maxLevel.put(Enchantment.PROTECTION_EXPLOSIONS, 4);
	            return Enchantment.PROTECTION_EXPLOSIONS;
	        case "protectionblast":
		    	maxLevel.put(Enchantment.PROTECTION_EXPLOSIONS, 4);
	            return Enchantment.PROTECTION_EXPLOSIONS;
	        case "protblast":
		    	maxLevel.put(Enchantment.PROTECTION_EXPLOSIONS, 4);
	            return Enchantment.PROTECTION_EXPLOSIONS;
	        case "projectileprotection":
		    	maxLevel.put(Enchantment.PROTECTION_PROJECTILE, 4);
	            return Enchantment.PROTECTION_PROJECTILE;
	        case "protproj":
		    	maxLevel.put(Enchantment.PROTECTION_PROJECTILE, 4);
	            return Enchantment.PROTECTION_PROJECTILE;
	        case "projprot":
		    	maxLevel.put(Enchantment.PROTECTION_PROJECTILE, 4);
	            return Enchantment.PROTECTION_PROJECTILE;
	        case "projectileprot":
		    	maxLevel.put(Enchantment.PROTECTION_PROJECTILE, 4);
	            return Enchantment.PROTECTION_PROJECTILE;
	        case "projprotection":
		    	maxLevel.put(Enchantment.PROTECTION_PROJECTILE, 4);
	            return Enchantment.PROTECTION_PROJECTILE;
	        case "protectionprojectile":
		    	maxLevel.put(Enchantment.PROTECTION_PROJECTILE, 4);
	            return Enchantment.PROTECTION_PROJECTILE;
	        case "respiration":
		    	maxLevel.put(Enchantment.OXYGEN, 3);
	            return Enchantment.OXYGEN;
	        case "aquaaffinity":
		    	maxLevel.put(Enchantment.WATER_WORKER, 1);
	            return Enchantment.WATER_WORKER;
	        case "aqua":
		    	maxLevel.put(Enchantment.WATER_WORKER, 1);
	            return Enchantment.WATER_WORKER;
	        case "unbreaking":
		    	maxLevel.put(Enchantment.DURABILITY, 3);
	            return Enchantment.DURABILITY;
	        case "unb":
		    	maxLevel.put(Enchantment.DURABILITY, 3);
	            return Enchantment.DURABILITY;
	        case "sharpness":
		    	maxLevel.put(Enchantment.DAMAGE_ALL, 5);
	            return Enchantment.DAMAGE_ALL;
	        case "sharp":
		    	maxLevel.put(Enchantment.DAMAGE_ALL, 5);
	            return Enchantment.DAMAGE_ALL;
	        case "smite":
		    	maxLevel.put(Enchantment.DAMAGE_UNDEAD, 5);
	            return Enchantment.DAMAGE_UNDEAD;
	        case "baneofarthropods":
		    	maxLevel.put(Enchantment.DAMAGE_ARTHROPODS, 5);
	            return Enchantment.DAMAGE_ARTHROPODS;
	        case "bane":
		    	maxLevel.put(Enchantment.DAMAGE_ARTHROPODS, 5);
	            return Enchantment.DAMAGE_ARTHROPODS;
	        case "knockback":
		    	maxLevel.put(Enchantment.KNOCKBACK, 2);
	            return Enchantment.KNOCKBACK;
	        case "fireaspect":
		    	maxLevel.put(Enchantment.FIRE_ASPECT, 2);
	            return Enchantment.FIRE_ASPECT;
	        case "looting":
		    	maxLevel.put(Enchantment.LOOT_BONUS_MOBS, 3);
	            return Enchantment.LOOT_BONUS_MOBS;
	        case "efficiency":
		    	maxLevel.put(Enchantment.DIG_SPEED, 5);
	            return Enchantment.DIG_SPEED;
	        case "eff":
		    	maxLevel.put(Enchantment.DIG_SPEED, 5);
	            return Enchantment.DIG_SPEED;
	        case "silktouch":
		    	maxLevel.put(Enchantment.SILK_TOUCH, 1);
	            return Enchantment.SILK_TOUCH;
	        case "silk":
		    	maxLevel.put(Enchantment.SILK_TOUCH, 1);
	            return Enchantment.SILK_TOUCH;
	        case "fortune":
		    	maxLevel.put(Enchantment.LOOT_BONUS_BLOCKS, 3);
	            return Enchantment.LOOT_BONUS_BLOCKS;
	        case "flame":
		    	maxLevel.put(Enchantment.ARROW_FIRE, 1);
	            return Enchantment.ARROW_FIRE;
	        case "power":
		    	maxLevel.put(Enchantment.ARROW_DAMAGE, 5);
	            return Enchantment.ARROW_DAMAGE;
	        case "punch":
		    	maxLevel.put(Enchantment.ARROW_KNOCKBACK, 2);
	            return Enchantment.ARROW_KNOCKBACK;
	        case "infinity":
		    	maxLevel.put(Enchantment.ARROW_INFINITE, 1);
	            return Enchantment.ARROW_INFINITE;
	        case "infinite":
		    	maxLevel.put(Enchantment.ARROW_INFINITE, 1);
	            return Enchantment.ARROW_INFINITE;
	        default:
	            return null;
	    }       
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		CaptionHandler handler = new CaptionHandler(plugin);
		this.manager = new ConfigManager(plugin);
	    this.config = manager.getNewConfig("config.yml");
	    if (this.config.getConfigurationSection("commands").getConfigurationSection("enchant") != null && this.config.getConfigurationSection("commands").getConfigurationSection("enchant").getBoolean("enabled") == true) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(handler.getCaption("ConsoleForbidden"));
				return true;
			}
			
			Player p = (Player) sender;
			if (p.hasPermission("catx.enchant")) {
				if (args.length == 0) {
					p.sendMessage(handler.getCaption("Usage").replace("{0}", "/enchant").replace("{1}", "<enchantment> <level>"));
				} else {
					if (args[0].equalsIgnoreCase("none") || args[0].equalsIgnoreCase("clear")) {
						ItemStack item = p.getItemInHand();
						for (Enchantment e:item.getEnchantments().keySet()) {
							item.removeEnchantment(e);
						}
						p.setItemInHand(item);
						p.sendMessage(handler.getCaption("ItemUnenchanted").replace("{0}", item.getType().name()));
						return true;
					}
					Enchantment enchant = getEnchantment(args[0]);
					if (enchant != null) {
						Integer level = 1;
						if (args.length >= 2) {
							if (Integer.valueOf(args[1]) != null) {
								level = Integer.valueOf(args[1]);
								if (level < 1) {
									level = 1;
								}
								if (level > maxLevel.get(enchant)) {
									if (this.config.getConfigurationSection("UnsafeEnchantments") != null && this.config.getBoolean("UnsafeEnchantments") != true) { 
										level = maxLevel.get(enchant);
									}
								}
							}
						}
						ItemStack item = p.getItemInHand();
						item.addUnsafeEnchantment(enchant, level);
						p.setItemInHand(item);
						p.sendMessage(handler.getCaption("ItemEnchanted").replace("{0}", enchant.getName()).replace("{1}", item.getType().name()));
					} else {
						p.sendMessage(handler.getCaption("InvalidEnchantment"));
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
