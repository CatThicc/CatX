package me.thicccat.catx.handlers;

import java.util.ArrayList;
import java.util.List;

import me.thicccat.catx.Main;

public class ConfigInitializer {
	
	public Main plugin;

	public ConfigInitializer(Main plugin) {
		this.plugin = plugin;
	}
	
	public ConfigManager manager;	 
    public Config config;
    public Config messages;
    
	public void createConfig() {
		this.manager = new ConfigManager(plugin);
		 
        this.config = manager.getNewConfig("config.yml");
        
        if (!this.config.getBoolean("chatcolors")) {
        	String[] chatcolor = {"Players will still need the 'catx.chatcolor' permission.", "n/a | catx.chatcolor"};
        	this.config.set("chatcolors", true, chatcolor);
        }
        if (this.config.get("nicknames") == null) {
        	this.config.createSection("nicknames", "/nick | catx.nickname");
        }
        if (this.config.get("unsafeenchants") == null) {
        	String[] unsafe = {"Enables enchantments higher than the max level."};
        	this.config.set("unsafeenchants", true, unsafe);
        }
        if (this.config.get("kits") == null) {
        	String[] kitInfo = {"Cooldown is in seconds.", "format = <item> <amount>", "Cooldown bypass = catx.kit.[kitname].cooldown", "n/a | catx.kits.[kitname]"};
        	this.config.createSection("kits", kitInfo);
        	this.config.set("kits.example.cooldown", 60);
        	List<String> exampleItems = new ArrayList<String>();
        	exampleItems.add("STONE_PICKAXE 1");
        	exampleItems.add("STONE_AXE 1");
        	exampleItems.add("STONE_SPADE 1");
        	exampleItems.add("STONE_SWORD 1");
        	exampleItems.add("APPLE 10");
        	this.config.set("kits.example.items", exampleItems);
        }
        if (this.config.get("commands") == null) {
        	this.config.createSection("commands", "Enabled = true, Disabled = false");
        	this.config.set("commands.back.enabled", true);
        	this.config.set("commands.broadcast.enabled", true);
        	this.config.set("commands.cat.enabled", true);
        	this.config.set("commands.clear.enabled", true);
        	this.config.set("commands.day.enabled", true);
        	this.config.set("commands.delhome.enabled", true);
        	this.config.set("commands.delkit.enabled", true);
        	this.config.set("commands.delwarp.enabled", true);
        	this.config.set("commands.enchant.enabled", true);
        	this.config.set("commands.exp.enabled", true);
        	this.config.set("commands.feed.enabled", true);
        	this.config.set("commands.fly.enabled", true);
        	this.config.set("commands.gmc.enabled", true);
        	this.config.set("commands.gms.enabled", true);
        	this.config.set("commands.heal.enabled", true);
        	this.config.set("commands.help.enabled", true);
        	this.config.set("commands.home.enabled", true);
        	this.config.set("commands.invsee.enabled", true);
        	this.config.set("commands.kill.enabled", true);
        	this.config.set("commands.kit.enabled", true);
        	this.config.set("commands.msg.enabled", true);
        	this.config.set("commands.nick.enabled", true);
        	this.config.set("commands.night.enabled", true);
        	this.config.set("commands.reply.enabled", true);
        	this.config.set("commands.sethome.enabled", true);
        	this.config.set("commands.setkit.enabled", true);
        	this.config.set("commands.setspawn.enabled", true);
        	this.config.set("commands.setwarp.enabled", true);
        	this.config.set("commands.spawn.enabled", true);
        	this.config.set("commands.starve.enabled", true);
        	this.config.set("commands.storm.enabled", true);
        	this.config.set("commands.summon.enabled", true);
        	this.config.set("commands.sun.enabled", true);
        	this.config.set("commands.tpa.enabled", true);
        	this.config.set("commands.tpahere.enabled", true);
        	this.config.set("commands.tpaccept.enabled", true);
        	this.config.set("commands.tpdeny.enabled", true);
        	this.config.set("commands.warp.enabled", true);
        	this.config.set("commands.weather.enabled", true);
        	this.config.set("commands.world.enabled", true);
        }
        this.config.saveConfig();
	}
	
}
