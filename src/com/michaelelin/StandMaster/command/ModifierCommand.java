package com.michaelelin.StandMaster.command;

import java.util.Collection;
import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.StandMaster.CommandTree;
import com.michaelelin.StandMaster.StandMasterException;
import com.michaelelin.StandMaster.StandMasterPlugin;
import com.michaelelin.StandMaster.data.StandMasterData;

/**
 * Command to modify a property of an entity.
 */
public class ModifierCommand extends AbstractCommand {

    /**
     * Constructs a {@code ModifierCommand} from the given name and
     * description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public ModifierCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void printHelp(CommandSender sender, Collection<String> context) {
        super.printHelp(sender, context);
        sender.sendMessage("Usage: " + CommandTree.getFullCommand(context, getName()) + " <"
                + StandMasterPlugin.getInstance().getModifierTable().get(context, getName())
                        .getType().toString() + ">");
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }

        if (args.isEmpty()) {
            printHelp(sender, context);
            return;
        }

        Player player = (Player) sender;

        try {
            StandMasterData value = StandMasterPlugin.getInstance().getModifierTable()
                    .get(context, getName()).getType().wrapValue(CommandTree.join(args, " "));
            StandMasterPlugin.getInstance().getModifierList(player).add(
                    StandMasterPlugin.getInstance().getModifierTable().get(context, getName())
                    .apply(value));
            player.sendMessage(ChatColor.AQUA + "Modifier added.");
        } catch (RuntimeException e) {
            printHelp(sender, context);
        }
    }

}
