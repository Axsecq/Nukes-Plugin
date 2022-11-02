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
		
		if ( !(sender instanceof Player) || !(sender.hasPermission("nukesplugin.sendnukes")) ) {
			sender.sendMessage(ChatColor.RED + "You cannot own any nukes.");
			return true;
		}
		
		if (!(arguments.length > 0) || !(arguments.length < 5)) {
			sender.sendMessage(ChatColor.RED + "Invalid arguments specified.");
			return false;
		}
		
		if (arguments[0].contains("~") || arguments[1].contains("~") || arguments[2].contains("~")) {
			sender.sendMessage(ChatColor.RED + "Invalid coordinates specified.");
			return false;
		}
		
		Player player = (Player) sender;
		World world = player.getWorld();
		
		int distance = 15;
		
		final int x = Integer.parseInt(arguments[0]);
		final int y = Integer.parseInt(arguments[1]);
		final int z = Integer.parseInt(arguments[2]);
		
		int power = Integer.parseInt(arguments[3]);
		
		if (power > plugin.config.getConfig().getInt("maximum-tiers")) {
			power = plugin.config.getConfig().getInt("maximum-tiers");
		}
		
		if (power < 1) {
			power = 1;
		}
		
		int amount = power * 16;
		
		if (player.getInventory().contains(Material.DIAMOND_BLOCK, amount)) {
			InventoryUtil.removeItems(player.getInventory(), Material.DIAMOND_BLOCK, amount);
			player.sendMessage(ChatColor.GREEN + "Nuke successfuly sent!");
		} else {
			player.sendMessage(ChatColor.RED + "You don't have the required resources");
			return true;
		}
		
		int newX = x - (distance * power) / 2;
		int newY = y - (distance * power) / 2;
		int newZ = z - (distance * power) / 2;
		
		for (int loopsX = 0; loopsX < power; loopsX++) {
			for (int loopsY = 0; loopsY < power; loopsY++) {
				for (int loopsZ = 0; loopsZ < power; loopsZ++) {
					spawnExplosion(world, x, y, z);
					newZ += distance;
					System.out.println("Spawn");
				}
				newZ -= distance * power;
				newY += distance;
			}
			newY -= distance * power;
			newX += distance;
		}
		
		Bukkit.getOnlinePlayers().forEach(a -> plugin.radiationUtil.sendWarning(a, x, y, z));
		
		plugin.radiationUtil.setRadioactive(true);
		
		return true;
	}
	
	private void spawnExplosion(World world, int x, int y, int z) {
		world.spawn(new Location(world, x, y, z), Creeper.class, entity -> {
			
			entity.setCustomName("Nuclear Bomb");
			
			entity.setExplosionRadius(75);
			entity.setMaxFuseTicks(0);
			
			entity.setGravity(false);
			entity.setSilent(true);
			entity.setAI(false);
			
			entity.setInvulnerable(true);
			
			entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10000, 2, false, false));
			
		});
	}
}