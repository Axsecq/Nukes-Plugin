package me.nukesplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nukesplugin.NukesPlugin;
import me.nukesplugin.utilities.InventoryUtil;

public class SendNukes implements CommandExecutor {
	
	// Used for instance access.
	NukesPlugin plugin;
	
	public SendNukes(NukesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
		
		// Can't send as console or without permission.
		if ( !(sender instanceof Player) || !(sender.hasPermission("nukesplugin.sendnukes")) ) {
			sender.sendMessage(ChatColor.RED + "You cannot own any nukes.");
			return true;
		}
		
		// Can't use invalid arguments.
		if (!(arguments.length > 0) || !(arguments.length < 5)) {
			sender.sendMessage(ChatColor.RED + "Invalid arguments specified.");
			return false;
		}
		
		// Can't use tilde coordinates.
		if (arguments[0].contains("~") || arguments[1].contains("~") || arguments[2].contains("~")) {
			sender.sendMessage(ChatColor.RED + "Invalid coordinates specified.");
			return false;
		}
		
		// Get the player and the world.
		Player player = (Player) sender;
		World world = player.getWorld();
		
		// Distance between explosions.
		int distance = 20;
		
		// Get the X Y Z arguments.
		int x = Integer.parseInt(arguments[0]);
		int y = Integer.parseInt(arguments[1]);
		int z = Integer.parseInt(arguments[2]);
		
		// Get the power arguments.
		int power = Integer.parseInt(arguments[3]);
		
		// Maximum power.
		if (power > plugin.config.getConfig().getInt("maximum-tiers")) {
			power = plugin.config.getConfig().getInt("maximum-tiers");
		}
		
		// Minimum power.
		if (power < 1) {
			power = 1;
		}
		
		// Get the amount of diamond blocks required.
		int amount = power * 16;
		
		// Check if the player has the required amount of diamond blocks.
		if (player.getInventory().contains(Material.DIAMOND_BLOCK, amount)) {
			InventoryUtil.removeItems(player.getInventory(), Material.DIAMOND_BLOCK, amount);
			player.sendMessage(ChatColor.GREEN + "Nuke successfuly sent!");
		} else {
			player.sendMessage(ChatColor.RED + "You don't have the required resources");
			return true;
		}
		
		// Try to center the coordinates.
		x -= (distance * power) / 2;
		y -= (distance * power) / 2;
		z -= (distance * power) / 2;
		
		// Make a cube of explosions.
		for (int loopsX = 0; loopsX < power; loopsX++) {
			for (int loopsY = 0; loopsY < power; loopsY++) {
				for (int loopsZ = 0; loopsZ < power; loopsZ++) {
					spawnExplosion(world, x, y, z);
					z += distance;
					System.out.println("Spawn");
				}
				z -= distance * power;
				y += distance;
			}
			y -= distance * power;
			x += distance;
		}
		
		// Reset the coordinates to use in chat.
		x = Integer.parseInt(arguments[0]);
		y = Integer.parseInt(arguments[1]);
		z = Integer.parseInt(arguments[2]);
		
		// Alert the players using title.
		Bukkit.getOnlinePlayers().forEach(a -> a.sendTitle(ChatColor.DARK_RED + "!!! WARNING !!!", ChatColor.RED + "A Nuclear Bomb has been sent!"));
		
		// Alert the players in chat.
		Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "Warning!!! " + ChatColor.RED + "A Nuclear Bomb is Going to Land at: " + x + " " + y + " " + z + ".");
		Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "Warning!!! " + ChatColor.RED + "If you are nearby, hide in a basement.");
		
		// Start radiation.
		plugin.radiationUtil.setRadioactive(true);
		
		// Finish executing.
		return true;
	}
	
	private void spawnExplosion(World world, int x, int y, int z) {
		world.spawn(new Location(world, x, y, z), Creeper.class, entity -> {
			
			// Set the name to show up.
			entity.setCustomName("Nuclear Bomb");
			
			// Set the explosion properties.
			entity.setExplosionRadius(50);
			entity.setMaxFuseTicks(0);
			
			// Set the entity properties.
			entity.setGravity(false);
			entity.setSilent(true);
			entity.setAI(false);
			
			// Make sure it won't affect others.
			entity.setInvulnerable(true);
			
			// Make sure it's completely invisible.
			entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 2, false, false));
			
		});
	}
}