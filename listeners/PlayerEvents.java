package me.thicccat.catx.listeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigManager;


public class PlayerEvents implements Listener{
	
	Plugin pl = null;
	
	public PlayerEvents(Plugin plugin){
		this.pl = plugin;
	}
	
	public ConfigManager manager;
	 
    public Config config;
	
	public static HashMap<Player, Location> lastCoords = new HashMap<Player, Location>();
	Boolean died = false;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player p = event.getPlayer();
		this.manager = new ConfigManager((JavaPlugin) this.pl);
		this.config = manager.getNewConfig("config.yml");
		p.setPlayerListName("§e" + p.getName());
		if (config.getString("nicknames." + p.getName() + ".nickname") != null) {
			p.setDisplayName(config.getString("nicknames." + p.getName() + ".nickname"));
		}
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
		this.manager = new ConfigManager((JavaPlugin) this.pl);
		this.config = manager.getNewConfig("config.yml");
		Player p = event.getPlayer();
		if (event.getMessage().substring(0, 1).equals("&")) {
			if (p.hasPermission("catx.chatcolor") && config.getBoolean("chatcolors") == true) {
				event.setMessage(event.getMessage().replace("&", "§"));
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		Player p = (Player) event.getEntity();
		if (event.getDeathMessage().equalsIgnoreCase(p.getName() + " died")) {
			event.setDeathMessage(null);
		}
		if (p != null && p.hasPermission("catx.back.ondeath")) {
			lastCoords.put(p, p.getLocation());
			this.died = true;
		}
	}
}
