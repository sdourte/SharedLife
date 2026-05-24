package me.simon.sharedlife;

import me.simon.sharedlife.commands.StartAdventureCommand;
import me.simon.sharedlife.game.GameManager;
import me.simon.sharedlife.listeners.*;

import me.simon.sharedlife.managers.SharedStatsManager;

import me.simon.sharedlife.phases.PhaseManager;
import me.simon.sharedlife.scoreboard.ScoreboardManager;

import org.bukkit.plugin.java.JavaPlugin;

public final class SharedLife
        extends JavaPlugin {

    // GameManager
    private GameManager gameManager;

    // Getter GameManager
    public GameManager getGameManager() {

        return gameManager;
    }

    // Gestion stats partagées
    private SharedStatsManager sharedStatsManager;

    /*
     * Getter manager.
     */
    public SharedStatsManager getSharedStatsManager() {

        return sharedStatsManager;
    }

    // Gestion des phases
    private PhaseManager phaseManager;

    public PhaseManager getPhaseManager() {

        return phaseManager;
    }

    // Gestion du scoreboard
    private ScoreboardManager scoreboardManager;

    public ScoreboardManager getScoreboardManager() {

        return scoreboardManager;
    }

    @Override
    public void onEnable() {

        // GameManager
        this.gameManager = new GameManager(this);

        /*
         * Création manager.
         */
        this.sharedStatsManager =
                new SharedStatsManager(this);

        getLogger().info(
                "SharedLife activé !"
        );

        /*
         * Création managers.
         */
        this.phaseManager =
                new PhaseManager(this);

        this.scoreboardManager =
                new ScoreboardManager(this);

        /*
         * Update scoreboards.
         */
        scoreboardManager.updateScoreboards();

        /*
         * Listeners.
         */
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        getServer().getPluginManager().registerEvents(new HungerListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PhaseListener(this), this);

        // Commandes
        getCommand("startadventure").setExecutor(new StartAdventureCommand(this));
    }

    @Override
    public void onDisable() {

        getLogger().info(
                "SharedLife désactivé !"
        );
    }
}