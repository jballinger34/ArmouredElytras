package me.fakepumpkin7.armouredelytras.listeners;

import me.fakepumpkin7.armouredelytras.handlers.BaseArmouredElytra;
import me.fakepumpkin7.armouredelytras.util.ArmouredElytraType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.UUID;

public class ParticleListener implements Listener {

    BaseArmouredElytra base;
    public ParticleListener(BaseArmouredElytra base) {
        this.base = base;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        ArmouredElytraType type = base.getPlayersToGiveEffect().get(uuid);
        if(type == null){
            return;
        }
        Player player = event.getPlayer();
        if(player.isGliding()){
            Location l = player.getEyeLocation();

            player.spawnParticle(Particle.BLOCK_CRACK , l,2, type.getParticleBlockData());
            List<Entity> nearbyEntities = player.getNearbyEntities(10,10,10);
            for(Entity e : nearbyEntities){
                if(e instanceof Player){
                    Player near = (Player) e;
                    near.spawnParticle(Particle.BLOCK_CRACK , l,2, type.getParticleBlockData());
                }
            }
        }

    }

}
