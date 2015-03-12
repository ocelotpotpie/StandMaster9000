package com.michaelelin.StandMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.michaelelin.StandMaster.data.DataModifier;
import com.michaelelin.StandMaster.data.StandMasterData;

public class StandMasterPlugin extends JavaPlugin {

    private static StandMasterPlugin instance;
    private Map<Player, List<DataModifier<? extends Entity,
            ? extends StandMasterData>.Executable>> modifiers;
    private ModifierTable modifierTable;
    private CommandTree commands;

    public List<Placement> placements;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();

        instance = this;
        modifiers = new HashMap<Player, List<DataModifier<? extends Entity,
                ? extends StandMasterData>.Executable>>();
        modifierTable = new ModifierTable();
        modifierTable.addDefaults();
        commands = new CommandTree();
        placements = new ArrayList<Placement>();
        getServer().getPluginManager().registerEvents(new StandMasterListener(), this);
    }

    @Override
    public void onDisable() {
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        commands.issueCommand(sender, args);
        return true;
    }

    public List<DataModifier<? extends Entity, ? extends StandMasterData>.Executable>
            getModifierList(Player player) {
        if (modifiers.containsKey(player)) {
            return modifiers.get(player);
        } else {
            List<DataModifier<? extends Entity, ? extends StandMasterData>.Executable> l =
                    new ArrayList<DataModifier<? extends Entity, ? extends StandMasterData>
                        .Executable>();
            modifiers.put(player, l);
            return l;
        }
    }

    public ModifierTable getModifierTable() {
        return modifierTable;
    }

    public static StandMasterPlugin getInstance() {
        return instance;
    }

    public static class Placement {
        public final Location location;
        public final Player player;
        public final long time;

        public Placement(Location location, Player player, long time) {
            this.location = location;
            this.player = player;
            this.time = time;
        }

    }

}
