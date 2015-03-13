package com.michaelelin.StandMaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Entity;

import com.michaelelin.StandMaster.data.DataModifier;
import com.michaelelin.StandMaster.data.StandMasterData;

/**
 * Manages armor stand presets.
 */
public class PresetManager {
    private Map<String, List<DataModifier<? extends Entity,
            ? extends StandMasterData>.Executable>> presets;

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
    public List<DataModifier<? extends Entity, ? extends StandMasterData>.Executable> get(
            String name) {
        if (name == null) {
            return null;
        }
        return new ArrayList<>(presets.get(name));
    }

    /**
     * Adds the given preset with the given name.
     *
     * @param name the preset's name
     * @param preset the preset
     */
    public void add(String name,
            List<DataModifier<? extends Entity, ? extends StandMasterData>.Executable> preset) {
        if (presets.containsKey(name)) {
            throw new StandMasterException("That preset already exists - remove it first.");
        }
        presets.put(name, preset);
        savePresets();
    }

    public boolean remove(String name) {
        boolean removed = presets.remove(name) != null;
        if (removed) {
            savePresets();
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
                    presets.put(entry.getKey(),
                            deserialize(((MemorySection) entry.getValue()).getValues(true)));
                } catch (Exception e) {
                    StandMasterPlugin.getInstance().getLogger().warning(
                            "There was a problem loading the \"" + entry.getKey()
                            + "\" preset. Check config.yml.");
                }
            }
        }
    }

    /**
     * Saves the current preset list to the plugin configuration.
     */
    public void savePresets() {
        Map<String, Map<String, Object>> ser = new TreeMap<>();
        for (Entry<String, List<DataModifier<? extends Entity,
                ? extends StandMasterData>.Executable>> entry : presets.entrySet()) {
            ser.put(entry.getKey(), serialize(entry.getValue()));
        }
        StandMasterPlugin.getInstance().getConfig().set("presets", ser);
        StandMasterPlugin.getInstance().saveConfig();
    }

    private List<DataModifier<? extends Entity, ? extends StandMasterData>.Executable> deserialize(
            Map<String, Object> ser) {
        List<DataModifier<? extends Entity, ? extends StandMasterData>.Executable> mods =
                new ArrayList<>();
        for (Entry<String, Object> entry : ser.entrySet()) {
            // Figure out a better way of doing this
            if (!(entry.getValue() instanceof MemorySection)) {
                DataModifier<? extends Entity, ? extends StandMasterData> mod =
                        StandMasterPlugin.getInstance().getModifierTable().get(entry.getKey());
                if (mod != null) {
                    mods.add(mod.apply(mod.getType().deserialize(entry.getValue())));
                }
            }
        }
        return mods;
    }

    private Map<String, Object> serialize(List<DataModifier<? extends Entity,
            ? extends StandMasterData>.Executable> mods) {
        Map<String, Object> ser = new HashMap<String, Object>();
        for (DataModifier<? extends Entity, ? extends StandMasterData>.Executable mod : mods) {
            ser.put(mod.getIdentifier(), mod.getValue().serialize());
        }
        return ser;
    }

}
