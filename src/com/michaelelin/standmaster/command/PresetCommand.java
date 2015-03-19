package com.michaelelin.standmaster.command;

import java.util.Collection;
import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.standmaster.CommandTree;
import com.michaelelin.standmaster.ModifierSet;
import com.michaelelin.standmaster.StandMasterException;
import com.michaelelin.standmaster.StandMasterPlugin;

/**
 * A command to load, add, or remove a modifier preset.
 */
public final class PresetCommand extends ParentCommand {

    /**
     * Constructs a {@code PresetCommand} from the given name and
     * description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public PresetCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void printHelp(CommandSender sender, Collection<String> context) {
        String fullCommand = CommandTree.getFullCommand(context, getName());
        sender.sendMessage(ChatColor.GOLD
                + fullCommand + ChatColor.WHITE + " - " + getDescription());
        sender.sendMessage("Usage: " + CommandTree.getFullCommand(context, getName())
                + " <preset>");
        if (!this.getAllowedSubcommands(sender).isEmpty()) {
            sender.sendMessage("========");
            printSubcommands(sender);
        }
        sender.sendMessage("========");
        sender.sendMessage(ChatColor.AQUA + "Armor stand presets:");
        for (String preset : StandMasterPlugin.getInstance().getPresetManager().listPresets()) {
            sender.sendMessage(preset);
        }
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }

        Player player = (Player) sender;

        String arg = args.poll();

        StandMasterCommand command = getSubcommand(arg);

        if (command == null) {
            ModifierSet mods = StandMasterPlugin.getInstance().getPresetManager().get(arg);
            if (mods == null || !args.isEmpty()) {
                printHelp(player, context);
            } else {
                StandMasterPlugin.getInstance().getModifierList(player).addAll(mods);
                player.sendMessage(ChatColor.AQUA + "Preset loaded.");
            }
        } else if (command.canUse(sender)) {
            context.add(getName());
            command.execute(sender, context, args);
        } else {
            printHelp(player, context);
        }
    }

}
