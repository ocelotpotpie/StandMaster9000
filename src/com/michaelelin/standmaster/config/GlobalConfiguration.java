package com.michaelelin.standmaster.config;

import java.io.File;

import com.michaelelin.standmaster.ModifierSet;
import com.michaelelin.standmaster.StandMasterException;

/**
 * Global configuration.
 */
public class GlobalConfiguration extends AbstractConfiguration {

    private int globalPresetLimit;
    private int playerPresetLimit;

    /**
     * Initializes configuration from the given configuration file.
     * @param configFile the configuration file
     */
    public GlobalConfiguration(File configFile) {
        super(configFile);
    }

    @Override
    public void load() {
        super.load();
        globalPresetLimit = config.getInt("limits.preset.global", -1);
        playerPresetLimit = config.getInt("limits.preset.player", 3);
    }

    @Override
    public boolean addPreset(String name, ModifierSet preset) {
        int limit = getGlobalPresetLimit();
        if (limit >= 0 && presets.size() >= limit) {
            throw new StandMasterException("You have reached the global preset limit of " + limit
                    + ". Remove a preset first.");
        }
        return super.addPreset(name, preset);
    }

    /**
     * Returns the global preset limit.
     *
     * @return the global preset limit
     */
    public int getGlobalPresetLimit() {
        return globalPresetLimit;
    }

    /**
     * Returns the player preset limit.
     *
     * @return the player preset limit
     */
    public int getPlayerPresetLimit() {
        return playerPresetLimit;
    }

}
