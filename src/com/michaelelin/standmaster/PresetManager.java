package com.michaelelin.standmaster;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Manages armor stand presets.
 */
public class PresetManager {
    private Map<String, ModifierSet> presets;

    /**
     * Constructs a new preset manager.
     */
    public PresetManager() {
        presets = new TreeMap<>();
        loadPresets();
    }

    /**
     * Gets the preset with the given name, or null if not found.
     *
     * @param name the preset's name
     * @return the preset with the given name
     */
    public ModifierSet get(String name) {
        if (name == null) {
            return null;
        }
        return presets.get(name).clone();
    }

    /**
     * Adds the given preset with the given name.
     *
     * @param name the preset's name
     * @param preset the preset
     */
    public void add(String name, ModifierSet preset) {
        if (presets.containsKey(name)) {
            throw new StandMasterException("That preset already exists - remove it first.");
        }
        presets.put(name.toLowerCase(), preset.clone());
        savePreset(name.toLowerCase());
    }

    /**
     * Removes the preset with the given name.
     *
     * @param name the preset's name
     * @return whether a preset was removed
     */
    public boolean remove(String name) {
        boolean removed = presets.remove(name.toLowerCase()) != null;
        if (removed) {
            savePreset(name.toLowerCase());
        }
        return removed;
    }

    /**
     * Returns a collection of all preset names.
     *
     * @return all preset names
     */
    public Collection<String> listPresets() {
        return presets.keySet();
    }

    /**
     * Loads the preset list from the plugin configuration.
     */
    public void loadPresets() {
        StandMasterPlugin.getInstance().reloadConfig();
        presets.clear();
        ConfigurationSection presetConfig = StandMasterPlugin.getInstance().getConfig()
                .getConfigurationSection("presets");
        if (presetConfig != null) {
            for (Entry<String, Object> entry : presetConfig.getValues(false).entrySet()) {
                try {
                    presets.put(entry.getKey().toLowerCase(),
                            new ModifierSet(((MemorySection) entry.getValue()).getValues(false)));
                } catch (Exception e) {
                    StandMasterPlugin.getInstance().getLogger().warning(
                            "There was a problem loading the \"" + entry.getKey()
                            + "\" preset. Check config.yml.");
                }
            }
        }
    }

    /**
     * Saves the given preset to the plugin configuration.
     *
     * @param name the name of the preset
     */
    public void savePreset(String name) {
        FileConfiguration config = StandMasterPlugin.getInstance().getConfig();
        ConfigurationSection presetSection = config.getConfigurationSection("presets");
        presetSection.set(name, presets.get(name).serialize());
        StandMasterPlugin.getInstance().saveConfig();
    }

}
