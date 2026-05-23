package me.simon.sharedlife.listeners;

import me.simon.sharedlife.SharedLife;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener
        implements Listener {

    // Référence plugin
    private final SharedLife plugin;

    public HungerListener(
            SharedLife plugin
    ) {

        this.plugin = plugin;
    }

    @EventHandler
    public void onFoodChange(
            FoodLevelChangeEvent event
    ) {

        /*
         * Vérifie joueur.
         */
        if (!(event.getEntity()
                instanceof Player player)) {

            return;
        }

        /*
         * Delay pour update.
         */
        plugin.getServer().getScheduler()
                .runTaskLater(
                        plugin,
                        () -> plugin
                                .getSharedStatsManager()
                                .syncFood(player),
                        1L
                );
    }
}