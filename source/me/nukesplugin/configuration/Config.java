package me.nukesplugin.configuration;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	
	private File file;
	private FileConfiguration config;
	
	public Config(JavaPlugin plugin, String path) {
		this(plugin.getDataFolder().getAbsolutePath() + "/" + path);
	}
	
	public Config(String path) {
		this.file = new File(path);
		this.config = YamlConfiguration.loadConfiguration(file);
	}
	
	public boolean save() {
		try {
			this.config.save(file);
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}
	
	public File getFile() {
		return this.file;
	}
	
	public FileConfiguration getConfig() {
		return this.config;
	}
}
