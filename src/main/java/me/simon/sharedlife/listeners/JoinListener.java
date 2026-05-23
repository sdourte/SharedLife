package me.simon.sharedlife.listeners;

import me.simon.sharedlife.SharedLife;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener
        implements Listener {

    // Référence plugin
    private final SharedLife plugin;

    public JoinListener(
            SharedLife plugin
    ) {

        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(
            PlayerJoinEvent event
    ) {

        Player joiningPlayer =
                event.getPlayer();

        /*
         * Cherche un joueur déjà connecté.
         */
        Player referencePlayer =
                Bukkit.getOnlinePlayers()
                        .stream()
                        .filter(player ->
                                !player.equals(joiningPlayer)
                        )
                        .findFirst()
                        .orElse(null);

        /*
         * Aucun joueur trouvé.
         */
        if (referencePlayer == null) {
            return;
        }

        /*
         * Delay pour laisser
         * le joueur charger.
         */
        Bukkit.getScheduler().runTaskLater(
                plugin,
                () -> {

                    /*
                     * Synchronise vie.
                     */
                    joiningPlayer.setHealth(
                            referencePlayer.getHealth()
                    );

                    /*
                     * Synchronise nourriture.
                     */
                    joiningPlayer.setFoodLevel(
                            referencePlayer.getFoodLevel()
                    );

                    /*
                     * Synchronise saturation.
                     */
                    joiningPlayer.setSaturation(
                            referencePlayer.getSaturation()
                    );

                },
                20L
        );
    }
}