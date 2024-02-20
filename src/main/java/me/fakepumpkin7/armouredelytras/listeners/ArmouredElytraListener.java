package me.fakepumpkin7.armouredelytras.listeners;

import me.fakepumpkin7.armouredelytras.ArmouredElytras;
import me.fakepumpkin7.armouredelytras.handlers.BaseArmouredElytra;
import me.fakepumpkin7.armouredelytras.util.ArmouredElytraType;
import me.fakepumpkin7.armouredelytras.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ArmouredElytraListener implements Listener {

    ArmouredElytras plugin;
    BaseArmouredElytra base;

    public ArmouredElytraListener(ArmouredElytras plugin){
        this.plugin = plugin;
        this.base = plugin.getBase();
    }

    //give a player an armoured elytra on join
/*    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        ItemStack item = base.buildArmouredElytra(3);
        PlayerUtil.giveItem(player,item);
    }*/

    @EventHandler
    public void onPlayerRightClickHotbar(PlayerInteractEvent e){
        if(e.useItemInHand().equals(Event.Result.DENY))return;
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getItem() == null) return;
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        if(canBeOnChest(item)){
            base.onUnequip(player);
        }
        if(base.isArmouredElytra(item)){
            ArmouredElytraType type = base.getArmouredElytraType(item);
            base.onEquip(player,type);
        }

    }

    @EventHandler
    public void onPlayerChangeArmourInventory(InventoryClickEvent e){
        if(e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
        PlayerInventory inventory = (PlayerInventory) e.getClickedInventory();
        if(e.getCursor() == null && e.getCurrentItem() == null) return;
        boolean shift = false;
        boolean numberKey = false;
        if(e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)){
            shift = true;
        }
        if(shift){
            if(e.getCurrentItem() == null) return;
            ItemStack item = e.getCurrentItem();
            if(base.isArmouredElytra(item)){
                if(e.getSlotType() == InventoryType.SlotType.ARMOR){
                    base.onUnequip(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()));
                }
                if(e.getSlotType() != InventoryType.SlotType.ARMOR){
                    if(inventory.getChestplate() == null){
                        ArmouredElytraType type = base.getArmouredElytraType(item);
                        base.onEquip(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()),type);
                    }
                }
            }
        }

        if(e.getClick().equals(ClickType.NUMBER_KEY)){
            numberKey = true;
        }
        if(numberKey){
            if(e.getSlotType() == InventoryType.SlotType.ARMOR){
                if(base.isArmouredElytra(e.getCurrentItem())){
                    base.onUnequip(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()));
                }
                ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                if(hotbarItem != null && base.isArmouredElytra(hotbarItem)){
                    ArmouredElytraType type = base.getArmouredElytraType(hotbarItem);
                    base.onEquip(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()),type);
                }
            }
        }
        ItemStack currentItem = e.getCurrentItem();
        ItemStack cursorItem = e.getCursor();
        if(e.getSlotType() == InventoryType.SlotType.ARMOR){
            if(currentItem != null){
                if(base.isArmouredElytra(currentItem)){
                    base.onUnequip(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()));
                }
            }
            if(cursorItem != null){
                if(base.isArmouredElytra(cursorItem)){
                    ArmouredElytraType type = base.getArmouredElytraType(cursorItem);
                    base.onEquip(Bukkit.getPlayer(e.getWhoClicked().getUniqueId()),type);
                }
            }
        }
    }

    @EventHandler
    public void onArmourBreak(PlayerItemBreakEvent e){
        ItemStack item = e.getBrokenItem();
        if(base.isArmouredElytra(item)){
            if(e.getPlayer().getInventory().getChestplate().equals(item)){
                base.onUnequip(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        base.onUnequip(e.getEntity());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        base.onUnequip(e.getPlayer());
    }

    private boolean canBeOnChest(ItemStack item) {
        Material type = item.getType();
        if(type == Material.ELYTRA || type == Material.LEATHER_CHESTPLATE
                || type == Material.GOLDEN_CHESTPLATE || type == Material.IRON_CHESTPLATE
                || type == Material.CHAINMAIL_CHESTPLATE || type == Material.CHAINMAIL_CHESTPLATE
                || type == Material.DIAMOND_CHESTPLATE || type == Material.NETHERITE_CHESTPLATE){
            return true;
        }
        return false;
    }

}
