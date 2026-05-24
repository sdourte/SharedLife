package me.simon.sharedlife.listeners;

import me.simon.sharedlife.SharedLife;

import me.simon.sharedlife.game.GameState;
import me.simon.sharedlife.phases.Phase;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import org.bukkit.advancement.Advancement;

import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.event.enchantment.EnchantItemEvent;

import org.bukkit.event.entity.EntityDeathEvent;

import org.bukkit.event.inventory.CraftItemEvent;

import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerPortalEvent;

import org.bukkit.inventory.ItemStack;

public class PhaseListener
        implements Listener {

    // Référence plugin
    private final SharedLife plugin;

    public PhaseListener(
            SharedLife plugin
    ) {

        this.plugin = plugin;
    }

    /*
     * Vérifie si phase déjà terminée.
     */
    private boolean isCompleted(
            String name
    ) {

        for (Phase phase
                : plugin.getPhaseManager()
                .getPhases()) {

            if (phase.getName()
                    .equalsIgnoreCase(name)) {

                return phase.isCompleted();
            }
        }

        return false;
    }

    /*
     * Pioche fer
     * + eye of ender.
     */
    @EventHandler
    public void onCraft(
            CraftItemEvent event
    ) {

        /*
         * Vérifie partie active.
         */
        if (plugin.getGameManager()
                .getGameState()
                != GameState.RUNNING) {

            return;
        }

        ItemStack item =
                event.getCurrentItem();

        if (item == null) {
            return;
        }

        /*
         * Pioche fer.
         */
        if (item.getType()
                == Material.IRON_PICKAXE) {

            plugin.getPhaseManager()
                    .completePhase(
                            "Pioche Fer"
                    );
        }

        /*
         * Eye of ender.
         */
        if (item.getType()
                == Material.ENDER_EYE) {

            plugin.getPhaseManager()
                    .completePhase(
                            "Eye of Ender"
                    );
        }
    }

    /*
     * Diamant.
     */
    @EventHandler
    public void onMine(
            BlockBreakEvent event
    ) {

        /*
         * Vérifie partie active.
         */
        if (plugin.getGameManager()
                .getGameState()
                != GameState.RUNNING) {

            return;
        }

        if (event.getBlock().getType()
                == Material.DIAMOND_ORE

                ||

                event.getBlock().getType()
                        == Material.DEEPSLATE_DIAMOND_ORE) {

            plugin.getPhaseManager()
                    .completePhase(
                            "Diamant"
                    );
        }
    }

    /*
     * Enchantement.
     */
    @EventHandler
    public void onEnchant(
            EnchantItemEvent event
    ) {

        /*
         * Vérifie partie active.
         */
        if (plugin.getGameManager()
                .getGameState()
                != GameState.RUNNING) {

            return;
        }

        plugin.getPhaseManager()
                .completePhase(
                        "Enchantement"
                );
    }

    /*
     * Nether.
     */
    @EventHandler
    public void onWorldChange(
            PlayerChangedWorldEvent event
    ) {

        /*
         * Vérifie partie active.
         */
        if (plugin.getGameManager()
                .getGameState()
                != GameState.RUNNING) {

            return;
        }

        Player player =
                event.getPlayer();

        /*
         * Nether.
         */
        if (player.getWorld()
                .getEnvironment()
                == org.bukkit.World.Environment.NETHER) {

            plugin.getPhaseManager()
                    .completePhase(
                            "Nether"
                    );
        }
    }

    /*
     * Stronghold.
     */
    @EventHandler
    public void onAdvancement(
            PlayerAdvancementDoneEvent event
    ) {

        /*
         * Vérifie partie active.
         */
        if (plugin.getGameManager()
                .getGameState()
                != GameState.RUNNING) {

            return;
        }

        Advancement advancement =
                event.getAdvancement();

        /*
         * Vérifie advancement Stronghold.
         */
        if (advancement.getKey().equals(
                NamespacedKey.minecraft(
                        "story/follow_ender_eye"
                )
        )) {

            plugin.getPhaseManager()
                    .completePhase(
                            "Stronghold"
                    );
        }
    }

    /*
     * Bloque End si objectifs
     * incomplets.
     */
    @EventHandler
    public void onPortal(
            PlayerPortalEvent event
    ) {

        /*
         * Vérifie partie active.
         */
        if (plugin.getGameManager()
                .getGameState()
                != GameState.RUNNING) {

            return;
        }

        /*
         * Vérifie portail End.
         */
        if (event.getCause()
                != PlayerPortalEvent.TeleportCause.END_PORTAL) {

            return;
        }

        /*
         * Vérifie objectifs.
         */
        if (!plugin.getPhaseManager()
                .allObjectivesCompleted()) {

            event.setCancelled(true);

            event.getPlayer().sendMessage(
                    "§cVous devez terminer tous les objectifs avant l'End."
            );
        }
    }

    /*
     * Dragon.
     */
    @EventHandler
    public void onDragonDeath(
            EntityDeathEvent event
    ) {

        /*
         * Vérifie partie active.
         */
        if (plugin.getGameManager()
                .getGameState()
                != GameState.RUNNING) {

            return;
        }

        if (!(event.getEntity()
                instanceof EnderDragon)) {

            return;
        }

        /*
         * Vérifie autres objectifs.
         */
        if (!plugin.getPhaseManager()
                .allObjectivesCompleted()) {

            Bukkit.broadcastMessage(
                    "§cLe dragon a été tué mais tous les objectifs ne sont pas terminés."
            );

            return;
        }

        plugin.getPhaseManager()
                .completePhase(
                        "Dragon"
                );

        /*
         * Fin aventure.
         */
        plugin.getGameManager()
                .endAdventure();

        Bukkit.broadcastMessage(
                "§aTous les objectifs sont terminés !"
        );
    }
}