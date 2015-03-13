package com.michaelelin.StandMaster.command;

import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.StandMaster.StandMasterException;
import com.michaelelin.StandMaster.StandMasterPlugin;

/**
 * A command to add an armor stand preset.
 */
public class PresetAddCommand extends AbstractCommand {

    /**
     * Constructs a {@code PresetAddCommand} from the given name and description.
     *
     * @param name the command's name
     * @param description the command's description
     */
    public PresetAddCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }

        Player player = (Player) sender;

        String name = args.poll();

        if (name == null || !args.isEmpty()) {
            printHelp(sender, context);
        } else {
            StandMasterPlugin.getInstance().getPresetManager().add(name,
                    StandMasterPlugin.getInstance().getModifierList(player));
            player.sendMessage(ChatColor.AQUA + "Preset saved.");
        }
    }

}
