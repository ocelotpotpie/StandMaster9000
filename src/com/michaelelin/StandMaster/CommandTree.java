package com.michaelelin.StandMaster;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.command.CommandSender;

import com.michaelelin.StandMaster.command.ClearCommand;
import com.michaelelin.StandMaster.command.ListCommand;
import com.michaelelin.StandMaster.command.ModifierCommand;
import com.michaelelin.StandMaster.command.ParentCommand;
import com.michaelelin.StandMaster.command.ReloadCommand;
import com.michaelelin.StandMaster.command.RotationCommand;
import com.michaelelin.StandMaster.command.StandMasterCommand;

/**
 * Handles commands and provides some command-related tools.
 */
public class CommandTree {

    private StandMasterCommand commandBase = new ParentCommand("stand", "Armor stand commands")
        .addSubcommand(new ReloadCommand("reload", "Reloads the plugin's configuration"))
        .addSubcommand(new ListCommand("list", "Shows your current stand modifier list"))
        .addSubcommand(new ClearCommand("clear", "Clears your current stand modifier list"))
        .addSubcommand(new ModifierCommand("invisible", "Makes the stand invisible"))
        .addSubcommand(new ModifierCommand("nobaseplate", "Removes the stand's baseplate"))
        .addSubcommand(new ModifierCommand("nogravity", "Prevents the stand from falling"))
        .addSubcommand(new ParentCommand("pose", "Sets the stand's pose")
            .addSubcommand(new RotationCommand("body",
                    "Sets the stand's body rotation"))
            .addSubcommand(new RotationCommand("leftarm",
                    "Sets the stand's left arm rotation"))
            .addSubcommand(new RotationCommand("rightarm",
                    "Sets the stand's right arm rotation"))
            .addSubcommand(new RotationCommand("leftleg",
                    "Sets the stand's left leg rotation"))
            .addSubcommand(new RotationCommand("rightleg",
                    "Sets the stand's right leg rotation"))
            .addSubcommand(new RotationCommand("head",
                    "Sets the stand's head rotation")))
        .addSubcommand(new ModifierCommand("showarms", "Shows arms on the stand"))
        .addSubcommand(new ModifierCommand("small", "Makes the stand smaller"))
        .addAlias("sm")
        .addAlias("standmaster");

    /**
     * Run a command from the given sender with the given arguments.
     * @param sender the sender of the command
     * @param args the arguments to the command
     */
    public void issueCommand(CommandSender sender, String[] args) {
        commandBase.execute(sender, new ArrayDeque<String>(),
                new ArrayDeque<String>(Arrays.asList(args)));
    }

    /**
     * Constructs a full command string from the given context and command.
     *
     * @param context a collection of strings representing the ancestors to
     * this command
     * @param command the command name
     * @return the full command string
     */
    public static String getFullCommand(Collection<String> context, String command) {
        return "/" + join(context, " ", command);
    }

    /**
     * Constructs a command "footprint" from the given context and command by
     * separating tokens with a period.
     *
     * @param context a collection of strings representing the ancestors to
     * this command
     * @param command the command name
     * @return the command footprint
     */
    public static String getCommandFootprint(Collection<String> context, String command) {
        return join(context, ".", command);
    }

    /**
     * Joins the given strings with the given delimiter.
     *
     * @param parts the strings to join
     * @param delim the delimiter
     * @param suffixes additional strings to append
     * @return the string created by joining the given strings
     */
    public static String join(Collection<String> parts, String delim, String... suffixes) {
        if (parts.isEmpty()) {
            if (suffixes.length == 0) {
                return "";
            } else {
                return join(Arrays.asList(suffixes), delim);
            }
        }

        StringBuilder fullCommand = new StringBuilder();
        Iterator<String> it = parts.iterator();
        fullCommand.append(it.next());
        while (it.hasNext()) {
            fullCommand.append(delim).append(it.next());
        }

        for (String s : suffixes) {
            fullCommand.append(delim).append(s);
        }

        return fullCommand.toString();
    }

}
