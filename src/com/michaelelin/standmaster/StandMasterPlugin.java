package com.michaelelin.standmaster;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.michaelelin.standmaster.config.GlobalConfiguration;
import com.michaelelin.standmaster.config.PlayerConfiguration;

/**
 * The StandMaster9000 plugin.
 */
public class StandMasterPlugin extends JavaPlugin {

    private static StandMasterPlugin instance;

    private Map<String, PlayerConfiguration> players;
    private ModifierTable modifierTable;
    private CommandTree commands;
    private GlobalConfiguration configuration;

    /**
     * A list of armor stand placement data to be processed.
     */
    public List<Placement> placements;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.reloadConfig();

        instance = this;

        players = new HashMap<>();
        modifierTable = new ModifierTable();
        modifierTable.addDefaults();
        commands = new CommandTree();
        configuration = new GlobalConfiguration(new File(this.getDataFolder(), "config.yml"));
        placements = new ArrayList<Placement>();
        getServer().getPluginManager().registerEvents(new StandMasterListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * Reloads the plugin's configuration.
     */
    public void reload() {
        configuration.load();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        commands.issueCommand(sender, args);
        return true;
    }

    private File getConfiguration(Player player) {
        return new File(this.getDataFolder(), "players" + File.separator
                + player.getUniqueId().toString() + ".yml");
    }

    /**
     * Returns the given player's settings.
     *
     * @param player the player
     * @return the player's settings
     */
    public PlayerConfiguration getPlayerSettings(Player player) {
        if (players.containsKey(player.getName())) {
            return players.get(player.getName());
        } else {
            PlayerConfiguration settings = new PlayerConfiguration(getConfiguration(player));
            players.put(player.getName(), settings);
            return settings;
        }
    }

    /**
     * Removes the given player's settings from memory.
     *
     * @param player the player
     */
    public void removePlayerSettings(Player player) {
        if (players.containsKey(player.getName())) {
            players.get(player.getName()).save();
            players.remove(player.getName());
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
     * Returns the global configuration.
     *
     * @return the global configuration
     */
    public GlobalConfiguration getGlobalConfig() {
        return configuration;
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
