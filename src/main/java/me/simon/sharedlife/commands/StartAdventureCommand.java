package me.simon.sharedlife.commands;

import me.simon.sharedlife.SharedLife;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartAdventureCommand
        implements CommandExecutor {

    // Référence plugin
    private final SharedLife plugin;

    public StartAdventureCommand(
            SharedLife plugin
    ) {

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        /*
         * Lance aventure.
         */
        plugin.getGameManager()
                .startAdventure();

        return true;
    }
}