package com.michaelelin.StandMaster.commands;

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
 * A command to set an entity's rotation data.
 */
public final class RotationCommand extends ParentCommand {

    /**
     * Constructs a {@code RotationCommand} from the given name and
     * description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public RotationCommand(String name, String description) {
        super(name, description);
        addSubcommand(new ModifierCommand("x", "Sets the x rotation"));
        addSubcommand(new ModifierCommand("y", "Sets the y rotation"));
        addSubcommand(new ModifierCommand("z", "Sets the z rotation"));
    }

    @Override
    public void printHelp(CommandSender sender, Collection<String> context) {
        String fullCommand = CommandTree.getFullCommand(context, getName());
        sender.sendMessage(ChatColor.GOLD
                + fullCommand + ChatColor.WHITE + " - " + getDescription());
        sender.sendMessage("Usage: " + fullCommand + " <x> <y> <z>");
        sender.sendMessage("========");
        printSubcommands(sender);
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }

        Player player = (Player) sender;

        if (args.size() == 3) {
            try {
                StandMasterData value = StandMasterPlugin.getInstance().getModifierTable()
                        .get(context, getName()).getType().wrapValue(CommandTree.join(args, " "));
                StandMasterPlugin.getInstance().getModifierList(player).add(
                        StandMasterPlugin.getInstance().getModifierTable().get(context, getName())
                        .apply(value));
                player.sendMessage(ChatColor.AQUA + "Modifier added.");
            } catch (Exception e) {
                printHelp(sender, context);
            }
        } else {
            StandMasterCommand command = getSubcommand(args.poll());

            if (command == null) {
                printHelp(sender, context);
            } else {
                context.add(getName());
                command.execute(sender, context, args);
            }
        }
    }

}
