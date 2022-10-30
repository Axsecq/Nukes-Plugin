package me.nukesplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.nukesplugin.NukesPlugin;

public class Radiation implements CommandExecutor {

	NukesPlugin plugin;
	
	public Radiation(NukesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
		
		// Can't send without permission.
		if ( !(sender.hasPermission("nukesplugin.radiation")) ) {
			sender.sendMessage(ChatColor.RED + "You cannot toggle radiation.");
			return true;
		}
		
		// Toggle radiation.
		plugin.radiationUtil.setRadioactive(!plugin.radiationUtil.isRadioactive());
		
		// Finish executing.
		
		return true;
	}

}
