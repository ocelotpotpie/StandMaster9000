package com.michaelelin.standmaster.command;

import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.standmaster.StandMasterException;
import com.michaelelin.standmaster.StandMasterPlugin;
import com.michaelelin.standmaster.data.DataModifier;

/**
 * A command to print the player's current modifier list.
 */
public class ListCommand extends AbstractCommand {

    /**
     * Constructs a {@code ListCommand} from the given name and description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public ListCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }

        Player player = (Player) sender;

        if (args.isEmpty()) {
            player.sendMessage(ChatColor.AQUA + "Your current modifiers:");
            for (DataModifier<?, ?>.Executable modifier
                    : StandMasterPlugin.getInstance().getPlayerSettings(player).getModifiers()) {
                sender.sendMessage(modifier.getIdentifier() + ": " + modifier.getValue());
            }
        } else {
            printHelp(sender, context);
        }
    }

}
