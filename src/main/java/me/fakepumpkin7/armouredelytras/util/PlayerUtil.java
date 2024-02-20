package me.fakepumpkin7.armouredelytras.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtil {

    public static void giveItem(Player player, ItemStack item){
        if(player.getInventory().addItem(item) == null){
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    }


}
