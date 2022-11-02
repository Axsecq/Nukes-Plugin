package me.nukesplugin.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nukesplugin.NukesPlugin;

public class RadiationUtil {

	NukesPlugin plugin;
	
	public RadiationUtil(NukesPlugin plugin) {
		this.plugin = plugin;
	}
	
	public boolean radioactive = false;
	
	public boolean isRadioactive() {
		return this.radioactive;
	}

	public void setRadioactive(boolean state) {
		this.plugin.config.getConfig().set("radioactive", state);
		this.plugin.config.save();
		this.radioactive = state;
	}
	
	@SuppressWarnings("deprecation")
	public void sendWarning(Player player, int x, int y, int z) {
		player.sendMessage(ChatColor.DARK_RED + "Warning!!! " + ChatColor.RED + "A Nuclear Bomb is Going to Land at: " + x + " " + y + " " + z + ".");
		player.sendMessage(ChatColor.DARK_RED + "Warning!!! " + ChatColor.RED + "If you are nearby, hide in a basement.");
		
		player.sendTitle(ChatColor.RED + "⚠", ChatColor.DARK_RED + "A Nuclear Bomb has been sent!");
	}
	
	@SuppressWarnings("deprecation")
	public void giveRadiation(Player player) {
		if (isRadioactive() && !player.hasPotionEffect(PotionEffectType.REGENERATION)) {
			
	    	player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2, false, false));
	    	player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,    100, 0, false, false));
	    	player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,    100, 0, false, false));
	    	
	    	player.sendTitle(ChatColor.RED + "⚠", ChatColor.DARK_RED + "You are poisoned with radiation.");
	    	
		};
	}
}
