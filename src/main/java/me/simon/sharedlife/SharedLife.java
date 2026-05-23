package me.simon.sharedlife;

import me.simon.sharedlife.listeners.DamageListener;
import me.simon.sharedlife.listeners.HungerListener;

import me.simon.sharedlife.listeners.JoinListener;
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

        getLogger().info(
                "SharedLife activé !"
        );

        /*
         * Listeners.
         */
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new HungerListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    }

    @Override
    public void onDisable() {

        getLogger().info(
                "SharedLife désactivé !"
        );
    }
}