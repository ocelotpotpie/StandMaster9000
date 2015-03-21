package com.michaelelin.standmaster.command;

import java.util.Collection;
import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.standmaster.CommandTree;
import com.michaelelin.standmaster.StandMasterException;
import com.michaelelin.standmaster.config.Configuration;

/**
 * A command to remove an armor stand preset.
 */
public abstract class PresetRemoveCommand extends AbstractCommand {

    /**
     * Constructs a {@code PresetRemoveCommand} from the given name and description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public PresetRemoveCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void printHelp(CommandSender sender, Collection<String> context) {
        super.printHelp(sender, context);
        sender.sendMessage("Usage: " + CommandTree.getFullCommand(context, getName())
                + " <preset>");
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }

        Player player = (Player) sender;

        String name = args.poll();

        if (name != null && args.isEmpty()) {
            if (getConfig(player).removePreset(name)) {
                player.sendMessage(ChatColor.AQUA + "Preset removed.");
            } else {
                throw new StandMasterException("That preset does not exist.");
            }
        } else {
            printHelp(sender, context);
        }
    }

    protected abstract Configuration getConfig(Player sender);

}
