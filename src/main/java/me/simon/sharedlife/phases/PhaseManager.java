package me.simon.sharedlife.phases;

import me.simon.sharedlife.SharedLife;

import org.bukkit.Bukkit;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import java.util.ArrayList;
import java.util.List;

public class PhaseManager {

    // Référence plugin
    private final SharedLife plugin;

    // Liste objectifs
    private final List<Phase> phases;

    // BossBar
    private final BossBar bossBar;

    // Getter BossBar
    public BossBar getBossBar() {

        return bossBar;
    }

    public PhaseManager(
            SharedLife plugin
    ) {

        this.plugin = plugin;

        this.phases =
                new ArrayList<>();

        /*
         * BossBar.
         */
        this.bossBar =
                Bukkit.createBossBar(
                        "",
                        BarColor.GREEN,
                        BarStyle.SOLID
                );

        // On cache la BossBar avant la command startAdventure()
        bossBar.setVisible(false);

        /*
         * Ajout joueurs.
         */
        Bukkit.getOnlinePlayers()
                .forEach(bossBar::addPlayer);

        /*
         * Création objectifs.
         */
        loadPhases();

        /*
         * Update affichage.
         */
        updateBossBar();
    }

    /*
     * Charge objectifs.
     */
    private void loadPhases() {

        phases.add(
                new Phase(
                        "Pioche Fer",
                        "Crafter une pioche en fer"
                )
        );

        phases.add(
                new Phase(
                        "Diamant",
                        "Obtenir un diamant"
                )
        );

        phases.add(
                new Phase(
                        "Enchantement",
                        "Crafter une table d'enchantement"
                )
        );

        phases.add(
                new Phase(
                        "Nether",
                        "Entrer dans le Nether"
                )
        );

        phases.add(
                new Phase(
                        "Eye of Ender",
                        "Crafter un oeil de l'End"
                )
        );

        phases.add(
                new Phase(
                        "Stronghold",
                        "Trouver le Stronghold"
                )
        );

        phases.add(
                new Phase(
                        "Dragon",
                        "Tuer le dragon"
                )
        );
    }

    /*
     * Complète un objectif.
     */
    public void completePhase(
            String name
    ) {

        for (Phase phase : phases) {

            /*
             * Ignore déjà terminé.
             */
            if (phase.isCompleted()) {
                continue;
            }

            /*
             * Vérifie objectif.
             */
            if (phase.getName()
                    .equalsIgnoreCase(name)) {

                phase.setCompleted(true);

                Bukkit.broadcastMessage(
                        "§aObjectif terminé : §f"
                                + phase.getDescription()
                );

                /*
                 * Update affichage.
                 */
                updateBossBar();

                plugin.getScoreboardManager()
                        .updateScoreboards();

                break;
            }
        }
    }

    /*
     * Reset objectifs.
     */
    public void resetPhases() {

        for (Phase phase : phases) {

            phase.setCompleted(false);
        }
    }

    /*
     * Vérifie si tout est terminé.
     */
    public boolean allObjectivesCompleted() {

        for (Phase phase : phases) {

            if (!phase.isCompleted()) {
                // L'objectif dragon est le dernier
                return phase.getName().equals("Dragon");
            }
        }

        return true;
    }

    /*
     * Update bossbar.
     */
    public void updateBossBar() {

        /*
         * Cherche premier objectif
         * non terminé.
         */
        for (Phase phase : phases) {

            if (!phase.isCompleted()) {

                bossBar.setTitle(
                        "§eObjectif : §f"
                                + phase.getDescription()
                );

                /*
                 * Nombre terminés.
                 */
                long completed =
                        phases.stream()
                                .filter(Phase::isCompleted)
                                .count();

                /*
                 * Progression globale.
                 */
                bossBar.setProgress(
                        (double) completed
                                / phases.size()
                );

                return;
            }
        }

        /*
         * Tous terminés.
         */
        bossBar.setTitle(
                "§aTous les objectifs sont terminés !"
        );

        bossBar.setProgress(1.0);
    }

    /*
     * Getter objectifs.
     */
    public List<Phase> getPhases() {

        return phases;
    }
}