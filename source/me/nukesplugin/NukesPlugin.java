package me.nukesplugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.nukesplugin.commands.Radiation;
import me.nukesplugin.commands.SendNukes;
import me.nukesplugin.configuration.Config;
import me.nukesplugin.utilities.InventoryUtil;
import me.nukesplugin.utilities.RadiationUtil;
import net.md_5.bungee.api.ChatColor;

public class NukesPlugin extends JavaPlugin implements Listener {
	
	public Config config;
	
	public RadiationUtil radiationUtil;
	public InventoryUtil inventoryUtil;
	
	@Override
	public void onEnable() {
		Bukkit.getLogger().info("Loading...");
		
		config = new Config(this, "config.yml");
		
		install();
		
		radiationUtil = new RadiationUtil(this);
		inventoryUtil = new InventoryUtil(this);
		
		radiationUtil.setRadioactive(config.getConfig().getBoolean("radioactive"));
		
		getCommand("sendnukes").setExecutor(new SendNukes(this));
		getCommand("radiation").setExecutor(new Radiation(this));
		
		new BukkitRunnable() {
		    @Override
		    public void run() {
		    	Bukkit.getOnlinePlayers().forEach(a -> radiationUtil.giveRadiation(a));
		    }
		}.runTaskTimer(this, 0L, 60L);
		
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info("Unloading...");
	}
	
	private void install() {
		if (!config.getFile().exists()) {
			config.getConfig().set("radioactive", false);
			config.getConfig().set("maximum-tiers", 3);
			config.save();
		}
	}
}
