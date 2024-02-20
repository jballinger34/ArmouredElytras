package me.fakepumpkin7.armouredelytras.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public enum ArmouredElytraType {

    IRON,
    GOLD,
    DIAMOND,
    NETHERITE;


    public String getName(){
        switch(this){
            case IRON:
                return "Iron";
            case GOLD:
                return "Gold";
            case DIAMOND:
                return "Diamond";
            case NETHERITE:
                return "Netherite";

        }
        return "";
    }
    public String getColour(){
        switch(this){
            case IRON:
                return ""+ChatColor.WHITE;
            case GOLD:
                return  "" +ChatColor.YELLOW;
            case DIAMOND:
                return "" +ChatColor.AQUA;
            case NETHERITE:
                return "" +ChatColor.DARK_PURPLE;

        }
        return "";
    }
    public Material getMaterial(){
        switch(this){
            case IRON:
                return Material.IRON_INGOT;
            case GOLD:
                return  Material.GOLD_INGOT;
            case DIAMOND:
                return Material.DIAMOND;
            case NETHERITE:
                return Material.NETHERITE_INGOT;
        }
        return Material.AIR;
    }
    public Material getChestplate(){
        switch(this){
            case IRON:
                return Material.IRON_CHESTPLATE;
            case GOLD:
                return  Material.GOLDEN_CHESTPLATE;
            case DIAMOND:
                return Material.DIAMOND_CHESTPLATE;
            case NETHERITE:
                return Material.NETHERITE_CHESTPLATE;
        }
        return Material.AIR;
    }
    public int getCustomModelData(){
        switch(this){
            case IRON:
                return 1;
            case GOLD:
                return  2;
            case DIAMOND:
                return 3;
            case NETHERITE:
                return 4;
        }
        return 0;
    }

    public BlockData getParticleBlockData(){
        switch(this){
            case IRON:
                return Material.IRON_BLOCK.createBlockData();
            case GOLD:
                return  Material.GOLD_BLOCK.createBlockData();
            case DIAMOND:
                return Material.DIAMOND_BLOCK.createBlockData();
            case NETHERITE:
                return Material.NETHERITE_BLOCK.createBlockData();
        }
        return Material.AIR.createBlockData();
    }

}
