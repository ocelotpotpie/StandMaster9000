package com.michaelelin.standmaster.command;

import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.standmaster.StandMasterException;
import com.michaelelin.standmaster.StandMasterPlugin;

/**
 * A command to clear the player's current modifier list.
 */
public class ClearCommand extends AbstractCommand {

    /**
     * Constructs a {@code ClearCommand} from the given name and description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public ClearCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }

        Player player = (Player) sender;

        if (args.isEmpty()) {
            StandMasterPlugin.getInstance().getModifierList(player).clear();
            player.sendMessage(ChatColor.AQUA + "Modifier list cleared.");
        } else {
            printHelp(player, context);
        }
    }

}
