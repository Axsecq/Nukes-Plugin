package me.nukesplugin.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	
	// Config itself and it's file.
	private File file;
	private FileConfiguration config;
	
	// Plugin + path.
	public Config(JavaPlugin plugin, String path) {
		this(plugin.getDataFolder().getAbsolutePath() + "/" + path);
	}
	
	// Only path.
	public Config(String path) {
		this.file = new File(path);
		this.config = YamlConfiguration.loadConfiguration(file);
	}
	
	// Saves the config.
	public boolean save() {
		try {
			this.config.save(file);
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	// Get the config file.
	public File getFile() {
		return this.file;
	}
	
	// Get the config itself.
	public FileConfiguration getConfig() {
		return this.config;
	}
}
