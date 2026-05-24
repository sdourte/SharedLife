package me.simon.sharedlife.game;

import me.simon.sharedlife.SharedLife;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

public class GameManager {

    // Référence plugin
    private final SharedLife plugin;

    // Etat actuel
    private GameState gameState;

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
     * Lance aventure.
     */
    public void startAdventure() {

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
         * Etat terminé.
         */
        gameState =
                GameState.FINISHED;

        Bukkit.broadcastMessage(
                "§6L'aventure est terminée !"
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