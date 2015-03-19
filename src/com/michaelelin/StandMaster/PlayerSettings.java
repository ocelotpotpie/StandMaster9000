package com.michaelelin.standmaster;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Per-player settings.
 */
public class PlayerSettings {

    private final File configFile;

    private boolean persist;
    private ModifierSet modifiers;
    private Map<String, ModifierSet> presets;

    /**
     * Initializes settings for a player from the given configuration file.
     *
     * @param configFile the configuration file
     */
    public PlayerSettings(File configFile) {
        this.configFile = configFile;

        persist = false;
        modifiers = new ModifierSet();
        presets = new TreeMap<>();

        load();
    }

    /**
     * Loads settings from the player's configuration file.
     */
    public void load() {
        if (configFile != null && configFile.exists()) {
            try {
                FileConfiguration config = new YamlConfiguration();
                config.load(configFile);
                if (config.isBoolean("persist")) {
                    persist = config.getBoolean("persist");
                    if (persist && config.isConfigurationSection("modifiers")) {
                        ConfigurationSection mods = config.getConfigurationSection("modifiers");
                        modifiers = new ModifierSet(mods.getValues(false));
                    }
                }

                if (config.isConfigurationSection("presets")) {
                    ConfigurationSection presetSection = config.getConfigurationSection("presets");
                    for (Entry<String, Object> entry : presetSection.getValues(false).entrySet()) {
                        try {
                            presets.put(entry.getKey().toLowerCase(),
                                    new ModifierSet(((MemorySection) entry.getValue())
                                            .getValues(false)));
                        } catch (Exception e) {
                            StandMasterPlugin.getInstance().getLogger().warning(
                                    "There was a problem loading the \"" + entry.getKey()
                                    + "\" preset in " + configFile.getName());
                        }
                    }
                }
            } catch (Exception e) {
                StandMasterPlugin.getInstance().getLogger().warning("There was a problem loading "
                        + configFile.getName());
            }
        }
    }

    /**
     * Saves settings to the player's configuration file.
     */
    public void save() {
        FileConfiguration config = new YamlConfiguration();
        config.set("persist", persist);
        if (persist && !modifiers.isEmpty()) {
            config.createSection("modifiers", modifiers.serialize());
        }
        if (!presets.isEmpty()) {
            Map<String, Object> serPresets = new LinkedHashMap<String, Object>();
            for (Entry<String, ModifierSet> entry : presets.entrySet()) {
                serPresets.put(entry.getKey(), entry.getValue().serialize());
            }
            config.createSection("presets", serPresets);
        }
        try {
            config.save(configFile);
        } catch (Exception e) {
            StandMasterPlugin.getInstance().getLogger().warning("There was a problem saving "
                    + configFile.getName() + ":\n" + e.getMessage());
        }
    }

    /**
     * Returns whether the player's modifiers should persist across play
     * sessions and after placing armor stands.
     *
     * @return the player's modifier persistence
     */
    public boolean persists() {
        return persist;
    }

    /**
     * Sets whether the player's modifiers should persist across play sessions
     * and after placing armor stands.
     *
     * @param persist the player's modifier persistence
     */
    public void persist(boolean persist) {
        this.persist = persist;
    }

    /**
     * Returns the player's modifier set.
     *
     * @return the modifier set
     */
    public ModifierSet getModifiers() {
        return modifiers;
    }

}
