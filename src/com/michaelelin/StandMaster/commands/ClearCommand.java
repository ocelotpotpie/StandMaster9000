package com.michaelelin.StandMaster.commands;

import java.util.Deque;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.michaelelin.StandMaster.StandMasterException;
import com.michaelelin.StandMaster.StandMasterPlugin;

public class ClearCommand extends AbstractCommand {

    public ClearCommand(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute(CommandSender sender, Deque<String> context, Deque<String> args) {
        if (!(sender instanceof Player)) {
            throw new StandMasterException("That command can't be run from console.");
        }

        Player player = (Player) sender;

        if (args.isEmpty()) {
            StandMasterPlugin.getInstance().getModifierList(player).clear();
            player.sendMessage(ChatColor.AQUA + "Modifier list cleared.");
        } else {
            printHelp(player, context);
        }
    }

}
