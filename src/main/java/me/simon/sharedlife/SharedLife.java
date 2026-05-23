package me.simon.sharedlife;

import me.simon.sharedlife.listeners.DamageListener;
import me.simon.sharedlife.listeners.HungerListener;

import me.simon.sharedlife.managers.SharedStatsManager;

import org.bukkit.plugin.java.JavaPlugin;

public final class SharedLife
        extends JavaPlugin {

    // Gestion stats partagées
    private SharedStatsManager sharedStatsManager;

    /*
     * Getter manager.
     */
    public SharedStatsManager getSharedStatsManager() {

        return sharedStatsManager;
    }

    @Override
    public void onEnable() {

        /*
         * Création manager.
         */
        this.sharedStatsManager =
                new SharedStatsManager(this);

        /*
         * Listeners.
         */
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new HungerListener(this), this);

        getLogger().info(
                "SharedLife activé !"
        );
    }

    @Override
    public void onDisable() {

        getLogger().info(
                "SharedLife désactivé !"
        );
    }
}