package me.simon.sharedlife.game;

import me.simon.sharedlife.SharedLife;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

public class GameManager {

    // Référence plugin
    private final SharedLife plugin;

    // Etat actuel
    private GameState gameState;

    /*
     * Temps début aventure.
     */
    private long startTime;

    /*
     * Temps final.
     */
    private String finalTime;

    // Getter final time
    public String getFinalTime() {

        return finalTime;
    }

    public GameManager(
            SharedLife plugin
    ) {

        this.plugin = plugin;

        /*
         * Etat initial.
         */
        this.gameState =
                GameState.WAITING;
    }

    /*
     * Getter état.
     */
    public GameState getGameState() {

        return gameState;
    }

    /*
     * Retourne temps formaté.
     */
    public String getFormattedTime() {

        /*
         * Vérifie partie active.
         */
        if (gameState
                == GameState.WAITING) {

            return "00:00:00";
        }

        /*
         * Temps écoulé.
         */
        long elapsed =
                System.currentTimeMillis()
                        - startTime;

        /*
         * Conversion secondes.
         */
        long totalSeconds =
                elapsed / 1000;

        long hours =
                totalSeconds / 3600;

        long minutes =
                (totalSeconds % 3600) / 60;

        long seconds =
                totalSeconds % 60;

        return String.format(
                "%02d:%02d:%02d",
                hours,
                minutes,
                seconds
        );
    }

    /*
     * Lance aventure.
     */
    public void startAdventure() {

        /*
         * Sauvegarde temps début.
         */
        startTime =
                System.currentTimeMillis();

        /*
         * Vérifie état.
         */
        if (gameState
                == GameState.RUNNING) {

            Bukkit.broadcastMessage(
                    "§cUne aventure est déjà en cours."
            );

            return;
        }

        // On affiche la BossBar
        plugin.getPhaseManager()
                .getBossBar()
                .setVisible(true);

        /*
         * Reset objectifs.
         */
        plugin.getPhaseManager()
                .resetPhases();

        /*
         * Update UI.
         */
        plugin.getPhaseManager()
                .updateBossBar();

        plugin.getScoreboardManager()
                .updateScoreboards();

        /*
         * Change état.
         */
        gameState =
                GameState.RUNNING;

        /*
         * Affichage.
         */
        Bukkit.broadcastMessage(
                "§aL'aventure commence !"
        );

        /*
         * Ajoute bossbar joueurs.
         */
        for (Player player
                : Bukkit.getOnlinePlayers()) {

            plugin.getPhaseManager()
                    .getBossBar()
                    .addPlayer(player);
        }
    }

    /*
     * Fin aventure.
     */
    public void endAdventure() {

        /*
         * Vérifie état.
         */
        if (gameState
                != GameState.RUNNING) {

            return;
        }

        /*
         * Sauvegarde temps final.
         */
        finalTime =
                getFormattedTime();

        /*
         * Etat terminé.
         */
        gameState =
                GameState.FINISHED;

        Bukkit.broadcastMessage(
                "§6L'aventure est terminée !"
        );

        Bukkit.broadcastMessage(
                "§aTemps final : §f"
                        + finalTime
        );
    }

    /*
     * Reset complet.
     */
    public void resetGame() {

        // On cache la BossBar
        plugin.getPhaseManager()
                .getBossBar()
                .setVisible(false);

        /*
         * Reset objectifs.
         */
        plugin.getPhaseManager()
                .resetPhases();

        /*
         * Reset UI.
         */
        plugin.getPhaseManager()
                .updateBossBar();

        plugin.getScoreboardManager()
                .updateScoreboards();

        /*
         * Etat attente.
         */
        gameState =
                GameState.WAITING;

        Bukkit.broadcastMessage(
                "§eLa partie a été réinitialisée."
        );
    }
}