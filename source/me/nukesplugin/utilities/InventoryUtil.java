package me.nukesplugin.utilities;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.nukesplugin.NukesPlugin;

public class InventoryUtil {

	NukesPlugin plugin;
	
	public InventoryUtil(NukesPlugin plugin) {
		this.plugin = plugin;
	}
	
	// Remove a specific amount of items from inventory.
    public static int removeItems(Inventory inventory, Material type, int amount) {
    	 
        if(type == null || inventory == null)
            return -1;       
        if (amount <= 0)
            return -1;
 
        if (amount == Integer.MAX_VALUE) {
            inventory.remove(type);
            return 0;
        }
 
        HashMap<Integer,ItemStack> retVal = inventory.removeItem(new ItemStack(type,amount));
 
        int notRemoved = 0;
        for(ItemStack item: retVal.values()) {
            notRemoved+=item.getAmount();
        }
        return notRemoved;
    }
	
}
