package com.michaelelin.StandMaster.command;

import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.michaelelin.StandMaster.StandMasterException;
import com.michaelelin.StandMaster.StandMasterPlugin;
import com.michaelelin.StandMaster.data.DataModifier;
import com.michaelelin.StandMaster.data.StandMasterData;

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
            for (DataModifier<? extends Entity, ? extends StandMasterData>.Executable modifier
                    : StandMasterPlugin.getInstance().getModifierList(player)) {
                sender.sendMessage(modifier.getFootprint() + ": " + modifier.getValue());
            }
        } else {
            printHelp(sender, context);
        }
    }

}
