package me.fakepumpkin7.armouredelytras;

import me.fakepumpkin7.armouredelytras.handlers.BaseArmouredElytra;
import me.fakepumpkin7.armouredelytras.listeners.ArmouredElytraListener;
import me.fakepumpkin7.armouredelytras.listeners.CraftListener;
import me.fakepumpkin7.armouredelytras.listeners.ParticleListener;
import me.fakepumpkin7.armouredelytras.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmouredElytras extends JavaPlugin {

    // TODO custom model

    BaseArmouredElytra base;
    String resourcePackURL = "https://www.dropbox.com/scl/fo/nsvncnjzmpif4er08ow6p/h?rlkey=x8agscmf3vudgevazhue6ktr2&dl=1";

    @Override
    public void onEnable() {
        // Plugin startup logic
        base = new BaseArmouredElytra(this);
        Bukkit.getPluginManager().registerEvents(new CraftListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArmouredElytraListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ParticleListener(this.getBase()), this);
        loadCustomTexturePack();
    }

    private void loadCustomTexturePack() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public BaseArmouredElytra getBase() {
        return base;
    }

    public String getResourcePackURL() {
        return resourcePackURL;
    }
}
