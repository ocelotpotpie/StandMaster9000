package com.michaelelin.standmaster.config;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.michaelelin.standmaster.ModifierSet;
import com.michaelelin.standmaster.StandMasterPlugin;

public abstract class AbstractConfiguration implements Configuration {

    protected final File configFile;
    protected final FileConfiguration config;

    protected Map<String, ModifierSet> presets;

    protected AbstractConfiguration(File configFile) {
        this.configFile = configFile;
        config = new YamlConfiguration();
        presets = new TreeMap<>();

        load();
    }

    @Override
    public void load() {
        if (configFile.exists()) {
            try {
                config.load(configFile);
            } catch (Exception e) {
                StandMasterPlugin.getInstance().getLogger().warning("There was a problem loading "
                        + configFile.getName());
            }
            loadPresets();
        }
    }

    @Override
    public void save() {
        try {
            config.save(configFile);
        } catch (Exception e) {
            StandMasterPlugin.getInstance().getLogger().warning("There was a problem saving to "
                    + configFile.getName() + ":\n" + e.getMessage());
        }
    }

    @Override
    public boolean addPreset(String name, ModifierSet preset) {
        if (!presets.containsKey(name.toLowerCase())) {
            presets.put(name, preset.clone());
            getPresetSection().createSection(name.toLowerCase(), preset.serialize());
            save();
            return true;
        }
        return false;
    }

    @Override
    public boolean removePreset(String name) {
        if (presets.remove(name) != null) {
            getPresetSection().set(name, null);
            save();
            return true;
        }
        return false;
    }

    @Override
    public ModifierSet getPreset(String name) {
        if (name != null && presets.containsKey(name)) {
            return presets.get(name).clone();
        }
        return null;
    }

    @Override
    public Collection<String> listPresets() {
        return presets.keySet();
    }

    protected void loadPresets() {
        if (config.isConfigurationSection("presets")) {
            ConfigurationSection presetSection = config.getConfigurationSection("presets");
            for (Entry<String, Object> entry : presetSection.getValues(false).entrySet()) {
                try {
                    presets.put(entry.getKey().toLowerCase(),
                            new ModifierSet(((MemorySection) entry.getValue()).getValues(false)));
                } catch (Exception e) {
                    StandMasterPlugin.getInstance().getLogger().warning(
                            "There was a problem loading the \"" + entry.getKey()
                            + "\" preset in " + configFile.getName());
                }
            }
        }
    }

    protected ConfigurationSection getPresetSection() {
        if (config.isConfigurationSection("presets")) {
            return config.getConfigurationSection("presets");
        } else {
            return config.createSection("presets");
        }
    }

}
