package com.michaelelin.StandMaster.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * A dummy command that precedes another command.
 */
public class ParentCommand extends AbstractCommand {

    private final List<StandMasterCommand> subcommands;

    /**
     * Constructs a {@code ParentCommand} from the given name and description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public ParentCommand(String name, String description) {
        super(name, description);
        subcommands = new ArrayList<StandMasterCommand>();
    }

    /**
     * Adds a subcommand to this command.
     *
     * @param subCommand the subcommand
     * @return {@code this}
     */
    public ParentCommand addSubcommand(StandMasterCommand command) {
        subcommands.add(command);
        return this;
    }

    /**
     * Returns the subcommand with the given name, or {@code null} if it
     * doesn't exist.
     *
     * @param name the name of the subcommand
     * @return the subcommand with the given name
     */
    public StandMasterCommand getSubcommand(String name) {
        if (name == null) {
            return null;
        }
        for (StandMasterCommand command : subcommands) {
            if (command.getName().equalsIgnoreCase(name) || command.hasAlias(name)) {
                return command;
            }
        }
        return null;
    }

    @Override
    public void printHelp(CommandSender sender, Collection<String> context) {
        super.printHelp(sender, context);
        sender.sendMessage("========");
        printSubcommands(sender);
    }

    /**
     * Send the name and description of each subcommand to the specified
     * command sender.
     *
     * @param sender the sender to send the subcommands to
     */
    public void printSubcommands(CommandSender sender) {
        if (subcommands.isEmpty()) {
            sender.sendMessage("No subcommands");
        }
        for (StandMasterCommand subcommand : subcommands) {
            if (subcommand.canUse(sender)) {
                sender.sendMessage(ChatColor.GOLD + subcommand.getName() + ChatColor.WHITE + " - "
                        + subcommand.getDescription());
            }
        }
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        StandMasterCommand command = getSubcommand(args.poll());

        if (command == null || !command.canUse(sender)) {
            printHelp(sender, context);
        } else {
            context.add(getName());
            command.execute(sender, context, args);
        }
    }

}
