package me.thicccat.catx;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.thicccat.catx.commands.Back;
import me.thicccat.catx.commands.Broadcast;
import me.thicccat.catx.commands.Clear;
import me.thicccat.catx.commands.Day;
import me.thicccat.catx.commands.Enchant;
import me.thicccat.catx.commands.Exp;
import me.thicccat.catx.commands.Feed;
import me.thicccat.catx.commands.Heal;
import me.thicccat.catx.commands.Help;
import me.thicccat.catx.commands.Home;
import me.thicccat.catx.commands.Info;
import me.thicccat.catx.commands.Invsee;
import me.thicccat.catx.commands.Kill;
import me.thicccat.catx.commands.Kit;
import me.thicccat.catx.commands.Nickname;
import me.thicccat.catx.commands.Night;
import me.thicccat.catx.commands.Reply;
import me.thicccat.catx.commands.Spawn;
import me.thicccat.catx.commands.Starve;
import me.thicccat.catx.commands.Summon;
import me.thicccat.catx.commands.Tpa;
import me.thicccat.catx.commands.Tpahere;
import me.thicccat.catx.commands.Warps;
import me.thicccat.catx.commands.Weather;
import me.thicccat.catx.commands.Whisper;
import me.thicccat.catx.commands.cWorld;
import me.thicccat.catx.handlers.Config;
import me.thicccat.catx.handlers.ConfigInitializer;
import me.thicccat.catx.handlers.ConfigManager;
import me.thicccat.catx.listeners.PlayerEvents;
import me.thicccat.catx.commands.Gmc;
import me.thicccat.catx.commands.Gms;

public class Main extends JavaPlugin {
	
	public static Logger log = Bukkit.getLogger();
		
	public ConfigManager manager;
	 
    public Config config;
    public Config messages;
	
	@Override
	public void onEnable() {
			
		ConfigInitializer init = new ConfigInitializer(this);
	    init.createConfig();
		
		PluginManager pm = getServer().getPluginManager();
		PlayerEvents pl = new PlayerEvents(this);
		pm.registerEvents(pl, this);
		new Info(this);
		new Day(this);
		new Night(this);
		new Kill(this);
		new Heal(this);
		new Help(this);
		new Feed(this);
		new Starve(this);
		new Gmc(this);
		new Gms(this);
		new Tpa(this);
		new Tpahere(this);
		new Weather(this);
		new Summon(this);
		new Home(this);
		new Spawn(this);
		new Back(this);
		new Warps(this);
		new Exp(this);
		new Whisper(this);
		new Reply(this);
		new Nickname(this);
		new Broadcast(this);
		new Kit(this);
		new Clear(this);
		new Invsee(this);
		new cWorld(this);
		new Enchant(this);
	}
	
	@Override
	public void onDisable() {
	}
}

