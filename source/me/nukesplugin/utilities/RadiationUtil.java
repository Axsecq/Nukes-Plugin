package me.nukesplugin.utilities;

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
}
