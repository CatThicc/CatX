package me.thicccat.catx.handlers;

import java.io.File;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CooldownHandler {
		
	private Plugin plugin = null;
    public File configFile = null;
    public CooldownHandler(Plugin plugin) {
        this.plugin = plugin;
    }
    
    public void addCooldown(Integer time, String plrInput, HashMap<String, Long> map) {
    	Runnable task = new Runnable() {
            @Override
            public void run() {
            	if(map.get(plrInput) != null) {
        			map.put(plrInput, null);
        		}
            }
        };
    	if(map.get(plrInput) == null) {
    		
			map.put(plrInput, System.currentTimeMillis());
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, task, Long.valueOf(String.valueOf(time * 20)));
		}
    }
    
    public boolean checkCooldown(Integer time, Player p, HashMap<String, Long> map, String plrNameKit) {
    	if(map.get(p.getName() + plrNameKit) != null) {
    		String remaining = String.valueOf(time - ((System.currentTimeMillis() - map.get(p.getName() + plrNameKit)) / 1000));
	    	CaptionHandler handler = new CaptionHandler(plugin);
			p.sendMessage(handler.getCaption("CooldownRemaining").replace("{0}", remaining));
			return true;
    	}
		return false; 
    }
}