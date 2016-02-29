package com.michaelelin.standmaster;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.standmaster.command.ClearCommand;
import com.michaelelin.standmaster.command.ListCommand;
import com.michaelelin.standmaster.command.ModifierCommand;
import com.michaelelin.standmaster.command.ParentCommand;
import com.michaelelin.standmaster.command.PersistCommand;
import com.michaelelin.standmaster.command.PresetAddCommand;
import com.michaelelin.standmaster.command.PresetCommand;
import com.michaelelin.standmaster.command.PresetRemoveCommand;
import com.michaelelin.standmaster.command.ReloadCommand;
import com.michaelelin.standmaster.command.RemoveInvisibileCommand;
import com.michaelelin.standmaster.command.RotationCommand;
import com.michaelelin.standmaster.command.StandMasterCommand;
import com.michaelelin.standmaster.config.Configuration;

/**
 * Handles commands and provides some command-related tools.
 */
public class CommandTree {

    private StandMasterCommand commandBase = new ParentCommand("stand", "Armor stand commands")
        .addSubcommand(new ReloadCommand("reload", "Reloads the plugin's configuration")
            .setPermission("standmaster.reload"))
        .addSubcommand(new RemoveInvisibileCommand("removeinvisible", "Remove all invisible armor stands in a specified radius")
        	.setPermission("standmaster.removeinvisible"))
        .addSubcommand(new PersistCommand("persist", "Prevents your modifier list from clearing")
            .setPermission("standmaster.persist"))
        .addSubcommand(new PresetCommand("preset", "Loads a modifier preset")
            .addSubcommand(new PresetAddCommand("add", "Adds a personal modifier preset") {
                @Override
                protected Configuration getConfig(Player sender) {
                    return StandMasterPlugin.getInstance().getPlayerSettings(sender);
                }
            }
                .setPermission("standmaster.preset.add"))
            .addSubcommand(new PresetRemoveCommand("remove",
                    "Removes a personal modifier preset") {
                @Override
                protected Configuration getConfig(Player sender) {
                    return StandMasterPlugin.getInstance().getPlayerSettings(sender);
                }
            }
                .setPermission("standmaster.preset.remove"))
            .addSubcommand(new PresetAddCommand("addglobal", "Adds a global modifier preset") {
                @Override
                protected Configuration getConfig(Player sender) {
                    return StandMasterPlugin.getInstance().getGlobalConfig();
                }
            }
                .setPermission("standmaster.preset.addglobal"))
            .addSubcommand(new PresetRemoveCommand("removeglobal",
                    "Removes a global modifier preset") {
                @Override
                protected Configuration getConfig(Player sender) {
                    return StandMasterPlugin.getInstance().getGlobalConfig();
                }
            }
                .setPermission("standmaster.preset.removeglobal"))
            .setPermission("standmaster.preset.load"))
        .addSubcommand(new ListCommand("list", "Shows your current stand modifier list")
            .setPermission("standmaster.stand.list"))
        .addSubcommand(new ClearCommand("clear", "Clears your current stand modifier list")
            .setPermission("standmaster.stand.clear"))
        .addSubcommand(new ModifierCommand("name", "Gives the stand a visible nametag")
            .setPermission("standmaster.stand.name"))
        .addSubcommand(new ModifierCommand("invisible", "Makes the stand invisible")
            .setPermission("standmaster.stand.invisible"))
        .addSubcommand(new ModifierCommand("nobaseplate", "Removes the stand's baseplate")
            .setPermission("standmaster.stand.nobaseplate"))
        .addSubcommand(new ModifierCommand("nogravity", "Prevents the stand from falling")
            .setPermission("standmaster.stand.nogravity"))
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
                    "Sets the stand's head rotation"))
            .setPermission("standmaster.stand.pose"))
        .addSubcommand(new ModifierCommand("showarms", "Shows arms on the stand")
            .setPermission("standmaster.stand.showarms"))
        .addSubcommand(new ModifierCommand("small", "Makes the stand smaller")
            .setPermission("standmaster.stand.small"))
        .addAlias("sm")
        .addAlias("standmaster")
        .addAlias("standmaster9000");

    /**
     * Run a command from the given sender with the given arguments.
     * @param sender the sender of the command
     * @param args the arguments to the command
     */
    public void issueCommand(CommandSender sender, String[] args) {
        try {
            commandBase.execute(sender, new ArrayDeque<String>(),
                    new ArrayDeque<String>(Arrays.asList(args)));
        } catch (StandMasterException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }
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
     * Constructs a command identifier from the given context and command by
     * separating tokens with a period.
     *
     * @param context a collection of strings representing the ancestors to
     * this command
     * @param command the command name
     * @return the command identifier
     */
    public static String getCommandIdentifier(Collection<String> context, String command) {
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
