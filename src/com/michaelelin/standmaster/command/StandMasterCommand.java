package com.michaelelin.standmaster.command;

import java.util.Collection;
import java.util.Deque;

import org.bukkit.command.CommandSender;

/**
 * A command for StandMaster9000.
 */
public interface StandMasterCommand {

    /**
     * Gets the command's name.
     *
     * @return the command's name
     */
    public String getName();

    /**
     * Gets a description of the command.
     *
     * @return the command's description
     */
    public String getDescription();

    /**
     * Adds an alias for this command.
     *
     * @param alias the name of the alias
     * @return {@code this}
     */
    public StandMasterCommand addAlias(String alias);

    /**
     * Returns whether the given name is an alias for this command.
     *
     * @param name the command name to check
     * @return whether the given name is an alias
     */
    public boolean hasAlias(String name);

    /**
     * Sets the permission required to execute this command.
     *
     * @param permission the permission node to use
     * @return {@code this}
     */
    public StandMasterCommand setPermission(String permission);

    /**
     * Returns whether the given sender has permission to use this command.
     *
     * @param sender the sender to check for permission
     * @return whether the sender can use this command
     */
    public boolean canUse(CommandSender sender);

    /**
     * Send the help for this command to the specified command sender.
     *
     * @param sender the sender to send the help to
     * @param context the parent commands preceding this command
     */
    public void printHelp(CommandSender sender, Collection<String> context);

    /**
     * Executes the command from the sender with the given arguments.
     *
     * @param sender the user who executed the command
     * @param context the parent commands preceding this command
     * @param args arguments to the command
     */
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args);

}
