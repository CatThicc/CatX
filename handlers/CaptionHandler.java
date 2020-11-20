package me.thicccat.catx.handlers;

import java.io.File;
import java.io.IOException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.thicccat.catx.Main;

public class CaptionHandler {
	public ConfigManager manager;
	 
    public Config config;
	
    private Plugin plugin = null;
    public File configFile = null;
    public CaptionHandler(Plugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(this.plugin.getDataFolder(), "lang.yml");

        this.saveDefault();
    }

    public void saveDefault() {
    	this.manager = new ConfigManager((JavaPlugin) plugin);
        if (!this.configFile.exists()) {
            try {
            	this.config = manager.getNewConfig("lang.yml");
            	this.configFile.createNewFile();
            	config.set("HomeNotFound", "&4ERROR: &cHome not found.");
            	config.set("WarpNotFound", "&4ERROR: &cWarp not found.");
            	config.set("PlayerNotFound", "&4ERROR: &cPlayer not found.");
            	config.set("KitNotFound", "&4ERROR: &cKit not found.");
            	config.set("WorldNotFound", "&4ERROR: &cValid worlds: {0}.");
            	config.set("HomeNotSaved", "&4ERROR: &cCould not save home.");
            	config.set("HomeSaved", "&eHome &6{0} &ehas been saved.");
            	config.set("HomeDeleted", "&eHome &6{0} &ehas been deleted.");
            	config.set("NoHomeSet", "&4ERROR: &cYou do not have any homes set.");
            	config.set("NoPermission", "&cYou don't have permission to use this command!");
            	config.set("TimeSet", "&eTime set to &6{0}&e in &6{1}&e.");
            	config.set("WasFed", "&eYou have been fed.");
            	config.set("WasHealed", "&eYou have been healed.");
            	config.set("WasStarved", "&eYou have been starved.");
            	config.set("GamemodeSet", "&eGamemode set to &6{0}&e.");
            	config.set("ConsoleForbidden", "&cYou cannot run this command in the console!");
            	config.set("InvalidSyntax", "&4ERROR: &cInvalid Syntax");
            	config.set("KilledSelf", "&6{0} &ehas committed suicide.");
            	config.set("KilledBy", "&6{0} &ewas killed by &6{1} &e.");
            	config.set("InvalidMobType", "&4ERROR: &cInvalid mob type.");
            	config.set("Summoned", "&eSuccessfully summoned &6{0}&e.");
            	config.set("TpaRequestSent", "&eRequest sent to &6{0}&e.");
            	config.set("TpaRequestReceived", "&6{0} &ehas requested to teleport to you.");
            	config.set("TpahereRequestReceived", "&6{0} &ehas requested you teleport to them.");
            	config.set("TpaOption", "&eTo {0}, type &6/tp{0}&e.");
            	config.set("TpaTime", "&eThis request will timeout after &6{0} seconds&e.");
            	config.set("TpaRequestAccepted", "&eTeleport request accepted.");
            	config.set("TpaRequestDenied", "&eTeleport request denied.");
            	config.set("NoTpaRequests", "&4ERROR: &cYou do not have any active tpa requests.");
            	config.set("WeatherSet", "&eWeather set to &6{0} &ein &6{1}&e.");
            	config.set("SpawnSet", "&eSpawn has been set in &6{0}&e.");
            	config.set("SpawnNotSet", "&4ERROR: &cCould not save spawn.");
            	config.set("NoSpawnSet", "&4ERROR: &cNo spawn has been set.");
            	config.set("SpawnDeleted", "&eSpawn has been deleted.");
            	config.set("Teleporting", "&eTeleporting...");
            	config.set("NotTeleportedYet", "&4ERROR: &cYou have not teleported yet.");
            	config.set("Usage", "&7Usage: {0} {1}");
            	config.set("WarpNotSaved", "&4ERROR: &cCould not save warp.");
            	config.set("WarpSaved", "&eWarp &6{0} &ehas been saved.");
            	config.set("WarpDeleted", "&eWarp &6{0} &ehas been deleted.");
            	config.set("NoWarpSet", "&4ERROR: &cNo warps have been set.");
            	config.set("WarpList", "&eWarps: &6{0}");
            	config.set("HomeList", "&eHomes: &6{0}");
            	config.set("KitList", "&eKits: &6{0}");
            	config.set("ExpSet", "&6{0}&e's exp has been set to &6{1}&e.");
            	config.set("Messaging", "&e[&6{0} &e-> &6{1}&e] &r{2}");
            	config.set("NoReply", "&4ERROR: &cThere is no one to reply to.");
            	config.set("SetNickname", "&eYour nickname has been set to &r{0}&e.");
            	config.set("NicknameTaken", "&4ERROR: &cThis nickname is taken.");
            	config.set("NicknameOff", "&eYou no longer have a nickname.");
            	config.set("Broadcast", "&e[&6Broadcast&e]&f {0}");
            	config.set("KitReceived", "&eYou have been given kit &6{0}&e.");
            	config.set("KitCreated", "&eKit &6{0}&e has been created.");
            	config.set("KitDeleted", "&eKit &6{0}&e has been deleted.");
            	config.set("InvalidItem", "&4ERROR: &cInvalid item, {0}");
            	config.set("ClearedSelf", "&eCleared own inventory.");
            	config.set("ClearedOther", "&eCleared &6{0}'s &einventory.");
            	config.set("ClearedOtherItem", "&eCleared &6{0} &ein &6{1}'s &einventory.");
            	config.set("CooldownRemaining", "&4COOLDOWN: &cThere is {0} seconds remaining.");
            	config.set("SeeOwnInv", "&4ERROR: &cYou cannot use /invsee on yourself.");
            	config.set("AlreadyInWorld", "&4ERROR: &cYou are already in this world.");
            	config.set("CommandDisabled", "&fThis command is disabled.");
            	config.set("Reloaded", "&6CatX&e has been reloaded.");
            	config.set("FlightEnabled", "&eFlight has been &6{0} &efor &6{1}&e.");
            	config.set("InvalidEnchantment", "&4ERROR: &cInvalid enchantment.");
            	config.set("ItemEnchanted", "&6{0} &ehas been added to &6{1}&e.");
            	config.set("ItemUnenchanted", "&eEnchantments removed from &6{0}&e.");
				config.saveConfig();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    public String getCaption(String name) {
        return this.getCaption(name, true);
    }

    public String addColor(String str) {
    	str = str.replace("&0", "§0")
    	.replace("&1", "§1")
    	.replace("&2", "§2")
    	.replace("&3", "§3")
    	.replace("&4", "§4")
    	.replace("&5", "§5")
    	.replace("&6", "§6")
    	.replace("&7", "§7")
    	.replace("&8", "§8")
    	.replace("&9", "§9")
    	.replace("&a", "§a")
    	.replace("&b", "§b")
    	.replace("&c", "§c")
    	.replace("&d", "§d")
    	.replace("&e", "§e")
    	.replace("&f", "§f")
    	.replace("&l", "§l")
    	.replace("&o", "§o")
    	.replace("&n", "§n")
    	.replace("&m", "§m")
    	.replace("&k", "§k")
    	.replace("&r", "§r");
    	return str;
    }
    
    public String removeColor(String str) {
    	str = str.replace("&0", "")
    	.replace("&1", "")
    	.replace("&2", "")
    	.replace("&6", "")
    	.replace("&7", "")
    	.replace("&8", "")
    	.replace("&9", "")
    	.replace("&a", "")
    	.replace("&b", "")
    	.replace("&c", "")
    	.replace("&d", "")
    	.replace("&e", "")
    	.replace("&f", "")
    	.replace("&l", "")
    	.replace("&o", "")
    	.replace("&n", "")
    	.replace("&m", "")
    	.replace("&k", "")
    	.replace("&r", "");
    	
    	return str;
    }
    
    public String getCaption(String name, boolean color) {
    	this.manager = new ConfigManager((JavaPlugin) plugin);
    	this.config = manager.getNewConfig("lang.yml");
        String caption = this.config.getString(name);
        if (caption == null) {
            Main.log.warning("Missing caption: " + name);
            caption = "&c[missing caption]";
        }

        if (color) {
        	if (name != "HomeList" && name != "WarpList" && name != "KitList") {
	        	caption = addColor(caption);
        	}
        }
        return caption;
    }
}