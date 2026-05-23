package me.simon.sharedlife.managers;

import me.simon.sharedlife.SharedLife;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

public class SharedStatsManager {

    // Référence plugin
    private final SharedLife plugin;

    /*
     * Évite boucle infinie.
     */
    private boolean updating = false;

    public SharedStatsManager(
            SharedLife plugin
    ) {

        this.plugin = plugin;
    }

    /*
     * Synchronise la vie.
     */
    public void syncHealth(
            Player source
    ) {

        /*
         * Évite récursion.
         */
        if (updating) {
            return;
        }

        updating = true;

        /*
         * Vie source.
         */
        double health =
                source.getHealth();

        for (Player player
                : Bukkit.getOnlinePlayers()) {

            /*
             * Ignore source.
             */
            if (player.equals(source)) {
                continue;
            }

            /*
             * Clamp sécurité.
             */
            double finalHealth = Math.min(
                    health,
                    player.getMaxHealth()
            );

            /*
             * Synchronise vie
             * seulement si nécessaire.
             */
            if (Math.abs(
                    player.getHealth()
                            - finalHealth
            ) > 0.01) {

                player.setHealth(
                        finalHealth
                );
            }
        }

        updating = false;
    }

    /*
     * Synchronise nourriture.
     */
    public void syncFood(
            Player source
    ) {

        /*
         * Évite récursion.
         */
        if (updating) {
            return;
        }

        updating = true;

        /*
         * Nourriture source.
         */
        int food =
                source.getFoodLevel();

        /*
         * Gestion aturation
         */
        float saturation =
                source.getSaturation();

        for (Player player
                : Bukkit.getOnlinePlayers()) {

            /*
             * Ignore source.
             */
            if (player.equals(source)) {
                continue;
            }

            player.setFoodLevel(
                    food
            );

            player.setSaturation(
                    saturation
            );
        }

        updating = false;
    }
}