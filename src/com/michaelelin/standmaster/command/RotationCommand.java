package com.michaelelin.standmaster.command;

import java.util.Collection;
import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.standmaster.CommandTree;
import com.michaelelin.standmaster.StandMasterException;
import com.michaelelin.standmaster.StandMasterPlugin;
import com.michaelelin.standmaster.data.DataModifier;

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
                String[] axes = {"x", "y", "z"};
                for (int i = 0; i < 3; i++) {
                    DataModifier<?, ?> modifier = StandMasterPlugin.getInstance()
                            .getModifierTable().get(context, getName() + "." + axes[i]);
                    StandMasterPlugin.getInstance().getPlayerSettings(player).getModifiers().add(
                            modifier.apply(modifier.getType().wrapValue(args.poll())));
                }
                player.sendMessage(ChatColor.AQUA + "Modifier added.");
            } catch (Exception e) {
                printHelp(sender, context);
            }
        } else {
            StandMasterCommand command = getSubcommand(args.poll());

            if (command == null || !command.canUse(sender)) {
                printHelp(sender, context);
            } else {
                context.add(getName());
                command.execute(sender, context, args);
            }
        }
    }

}
