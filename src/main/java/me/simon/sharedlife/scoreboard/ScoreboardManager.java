package me.simon.sharedlife.scoreboard;

import me.simon.sharedlife.SharedLife;

import me.simon.sharedlife.phases.Phase;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    // Référence plugin
    private final SharedLife plugin;

    public ScoreboardManager(
            SharedLife plugin
    ) {

        this.plugin = plugin;
    }

    /*
     * Update scoreboards.
     */
    public void updateScoreboards() {

        for (Player player
                : Bukkit.getOnlinePlayers()) {

            Scoreboard scoreboard =
                    Bukkit.getScoreboardManager()
                            .getNewScoreboard();

            Objective objective =
                    scoreboard.registerNewObjective(
                            "sharedlife",
                            "dummy",
                            "§6SharedLife"
                    );

            objective.setDisplaySlot(
                    DisplaySlot.SIDEBAR
            );

            int score =
                    plugin.getPhaseManager()
                            .getPhases()
                            .size();

            for (Phase phase
                    : plugin.getPhaseManager()
                    .getPhases()) {

                String line;

                if (phase.isCompleted()) {

                    line =
                            "§a✔ "
                                    + phase.getName();

                } else {

                    line =
                            "§c✖ "
                                    + phase.getName();
                }

                objective.getScore(line)
                        .setScore(score);

                score--;
            }

            player.setScoreboard(
                    scoreboard
            );
        }
    }
}