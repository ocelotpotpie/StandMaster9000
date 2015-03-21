package com.michaelelin.standmaster.command;

import java.util.Collection;
import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.standmaster.CommandTree;
import com.michaelelin.standmaster.StandMasterException;
import com.michaelelin.standmaster.StandMasterPlugin;

/**
 * A command to make the player's modifier set persist across play sessions and
 * after placing armor stands.
 */
public class PersistCommand extends AbstractCommand {

    /**
     * Constructs a {@code PersistCommand} from the given name and description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public PersistCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void printHelp(CommandSender sender, Collection<String> context) {
        super.printHelp(sender, context);
        sender.sendMessage("Usage: " + CommandTree.getFullCommand(context, getName())
                + " <BOOLEAN>");
        if (sender instanceof Player) {
            sender.sendMessage(ChatColor.AQUA + "Your current persistence setting: "
                    + StandMasterPlugin.getInstance().getPlayerSettings((Player) sender)
                    .persists());
        }
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }

        Player player = (Player) sender;

        if (args.size() == 1) {
            boolean value = Boolean.parseBoolean(args.poll());
            StandMasterPlugin.getInstance().getPlayerSettings(player).persist(value);
            sender.sendMessage(ChatColor.AQUA + "Modifier persistence set to " + value);
        } else {
            printHelp(sender, context);
        }
    }

}
