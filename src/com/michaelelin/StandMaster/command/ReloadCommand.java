package com.michaelelin.StandMaster.command;

import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.michaelelin.StandMaster.StandMasterPlugin;

/**
 * A command to reload the plugin's configuration.
 */
public class ReloadCommand extends AbstractCommand {

    /**
     * Constructs a {@code ReloadCommand} from the given name and description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public ReloadCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (args.isEmpty()) {
            StandMasterPlugin.getInstance().reload();
            sender.sendMessage(ChatColor.AQUA + "Configuration reloaded.");
        } else {
            printHelp(sender, context);
        }
    }

}
