package me.simon.sharedlife.listeners;

import me.simon.sharedlife.SharedLife;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener
        implements Listener {

    // Référence plugin
    private final SharedLife plugin;

    public DamageListener(
            SharedLife plugin
    ) {

        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(
            EntityDamageEvent event
    ) {

        /*
         * Vérifie joueur.
         */
        if (!(event.getEntity()
                instanceof Player player)) {

            return;
        }

        /*
         * Delay pour laisser
         * les dégâts s'appliquer.
         */
        plugin.getServer().getScheduler()
                .runTaskLater(
                        plugin,
                        () -> plugin
                                .getSharedStatsManager()
                                .syncHealth(player),
                        1L
                );
    }
}