package me.fakepumpkin7.armouredelytras.listeners;

import me.fakepumpkin7.armouredelytras.ArmouredElytras;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    ArmouredElytras armouredElytras;
    public PlayerJoinListener(ArmouredElytras ae){
        this.armouredElytras = ae;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        player.setResourcePack(armouredElytras.getResourcePackURL());
    }


}
