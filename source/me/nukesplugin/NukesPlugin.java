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
	
	// Initialize configurations.
	public Config config;
	
	// Initialize utilities.
	public RadiationUtil radiationUtil;
	public InventoryUtil inventoryUtil;
	
	@Override
	public void onEnable() {
		
		// Send the loading message.
		Bukkit.getLogger().info("Loading...");
		
		// Create the configurations, if weren't yet.
		config = new Config(this, "config.yml");
		
		// Run the first installation.
		install();
		
		// Initialize utilities.
		radiationUtil = new RadiationUtil(this);
		inventoryUtil = new InventoryUtil(this);
		
		// Save the radiation state.
		radiationUtil.setRadioactive(config.getConfig().getBoolean("radioactive"));
		
		// Register all the commands.
		getCommand("sendnukes").setExecutor(new SendNukes(this));
		getCommand("radiation").setExecutor(new Radiation(this));
		
		// Run radiation every couple seconds.
		new BukkitRunnable() {
		    @Override
		    public void run() {
		    	if (radiationUtil.isRadioactive()) {
			    	Bukkit.getOnlinePlayers().forEach(a -> a.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2, false, false)));
			    	Bukkit.getOnlinePlayers().forEach(a -> a.addPotionEffect(new PotionEffect(PotionEffectType.POISON,    100, 0, false, false)));
			    	Bukkit.getOnlinePlayers().forEach(a -> a.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,    100, 0, false, false)));
			    	
			    	Bukkit.getOnlinePlayers().forEach(a -> a.sendTitle(ChatColor.RED + "Radiation", ChatColor.DARK_RED + "You are poisoned by radiation."));
		    	}
		    }
		}.runTaskTimer(this, 0L, 60L);
		
		// Register all the events.
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		
		// Send the unloading message.
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
