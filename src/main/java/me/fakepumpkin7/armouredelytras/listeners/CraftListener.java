package me.fakepumpkin7.armouredelytras.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CraftListener implements Listener {

    @EventHandler
    public void onCraftAE(PrepareItemCraftEvent event){
        ItemStack[] matrix = event.getInventory().getMatrix();
        if(!isArmouredElytraRecipie(matrix)){
            return;
        }
        ItemStack res = event.getInventory().getResult();

        for(ItemStack item : matrix){
            if(item!=null){
                Map<Enchantment,Integer> enchMap = item.getEnchantments();
                for(Enchantment enchantment: enchMap.keySet()){
                    int level = enchMap.get(enchantment);
                    if(res.getEnchantments().containsKey(enchantment)){

                        int currentLevel = res.getEnchantmentLevel(enchantment);
                        if(currentLevel < level){
                            res.addUnsafeEnchantment(enchantment,level);
                        }
                    } else {
                        res.addUnsafeEnchantment(enchantment,level);
                    }

                }
            }


        }
        event.getInventory().setResult(res);

    }

    public boolean isArmouredElytraRecipie(ItemStack[] matrix) {
        if(matrix.length != 9){
            return false;
        }


        if (matrix[4] != null && matrix[4].getType() == Material.ELYTRA) {
            if (matrix[0] != null || matrix[2] != null
                    || matrix[3] != null || matrix[5] != null
                    || matrix[6] != null || matrix[8] != null
                    || matrix[1] == null || matrix[7] == null) {
                return false;
            }


            if (matrix[1].getType() == Material.GOLD_INGOT && matrix[7].getType() == Material.GOLDEN_CHESTPLATE
                    || matrix[1].getType() == Material.IRON_INGOT && matrix[7].getType() == Material.IRON_CHESTPLATE
                    || matrix[1].getType() == Material.DIAMOND && matrix[7].getType() == Material.DIAMOND_CHESTPLATE
                    || matrix[1].getType() == Material.NETHERITE_INGOT && matrix[7].getType() == Material.NETHERITE_CHESTPLATE) {
                return true;
            }

        }
        return false;
    }
}
