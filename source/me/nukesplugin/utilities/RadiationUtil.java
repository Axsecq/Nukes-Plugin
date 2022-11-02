package me.nukesplugin.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nukesplugin.NukesPlugin;

public class RadiationUtil {

	NukesPlugin plugin;
	
	public RadiationUtil(NukesPlugin plugin) {
		this.plugin = plugin;
	}
	
	// Radiation state.
	public boolean radioactive = false;
	
	// Get the radiation state.
	public boolean isRadioactive() {
		return this.radioactive;
	}

	// Set the radiation state.
	public void setRadioactive(boolean state) {
		this.plugin.config.getConfig().set("radioactive", state);
		this.plugin.config.save();
		this.radioactive = state;
	}
	
	@SuppressWarnings("deprecation")
	public void giveRadiation(Player player) {
		if (isRadioactive() && !player.hasPotionEffect(PotionEffectType.REGENERATION)) {
			
	    	player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 2, false, false));
	    	player.addPotionEffect(new PotionEffect(PotionEffectType.POISON,    100, 0, false, false));
	    	player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,    100, 0, false, false));
	    	
	    	player.sendTitle("⚠︎", "You are poisoned with radiation.");
	    	
		};
	}
}
