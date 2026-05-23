package me.simon.sharedlife.listeners;

import me.simon.sharedlife.SharedLife;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;

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
         * Dégâts réels.
         */
        double damage =
                event.getFinalDamage();

        /*
         * Ignore dégâts nuls.
         */
        if (damage <= 0) {
            return;
        }

        /*
         * Conversion en coeurs.
         */
        double hearts =
                damage / 2.0;

        /*
         * Arrondi 1 décimale.
         */
        hearts =
                Math.round(
                        hearts * 10.0
                ) / 10.0;

        /*
         * Message action bar.
         */
        String message =
                "§c"
                        + player.getName()
                        + " a perdu §4"
                        + hearts
                        + " ❤";

        /*
         * Envoie à tous.
         */
        for (Player onlinePlayer
                : Bukkit.getOnlinePlayers()) {

            onlinePlayer.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    new TextComponent(message)
            );
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