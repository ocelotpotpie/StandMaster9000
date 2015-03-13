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

/**
 * The StandMaster9000 plugin.
 */
public class StandMasterPlugin extends JavaPlugin {

    private static StandMasterPlugin instance;
    private Map<Player, List<DataModifier<? extends Entity,
            ? extends StandMasterData>.Executable>> modifiers;
    private ModifierTable modifierTable;
    private CommandTree commands;
    private PresetManager presetManager;

    /**
     * A list of armor stand placement data to be processed.
     */
    public List<Placement> placements;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();

        instance = this;
        modifiers = new HashMap<>();
        modifierTable = new ModifierTable();
        modifierTable.addDefaults();
        commands = new CommandTree();
        presetManager = new PresetManager();
        placements = new ArrayList<Placement>();
        getServer().getPluginManager().registerEvents(new StandMasterListener(), this);
    }

    /**
     * Reloads the plugin's configuration.
     */
    public void reload() {
        presetManager.loadPresets();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        commands.issueCommand(sender, args);
        return true;
    }

    /**
     * Returns the given player's armor stand modifier list.
     *
     * @param player the player
     * @return the player's modifier list
     */
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

    /**
     * Returns the modifier table.
     *
     * @return the modifier table
     */
    public ModifierTable getModifierTable() {
        return modifierTable;
    }

    /**
     * Returns the preset manager.
     *
     * @return the preset manager
     */
    public PresetManager getPresetManager() {
        return presetManager;
    }

    /**
     * Returns the active instance of the plugin.
     *
     * @return the active instance of the plugin
     */
    public static StandMasterPlugin getInstance() {
        return instance;
    }

    /**
     * A wrapper for the location, player, and time of an armor stand
     * placement.
     */
    public static class Placement {
        /**
         * The armor stand's location.
         */
        public final Location location;
        /**
         * The player who placed the armor stand.
         */
        public final Player player;
        /**
         * The time at which the armor stand was placed.
         */
        public final long time;

        /**
         * Constructs a {@code Placement} from the given location, player, and
         * time.
         *
         * @param location the location
         * @param player the player
         * @param time the time
         */
        public Placement(Location location, Player player, long time) {
            this.location = location;
            this.player = player;
            this.time = time;
        }

    }

}
