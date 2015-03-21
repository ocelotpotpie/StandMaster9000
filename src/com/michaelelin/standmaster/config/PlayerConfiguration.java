package com.michaelelin.standmaster.config;

import java.io.File;

import com.michaelelin.standmaster.ModifierSet;

/**
 * Per-player configuration.
 */
public class PlayerConfiguration extends AbstractConfiguration {

    private boolean persist;
    private ModifierSet modifiers;

    /**
     * Initializes settings for a player from the given configuration file.
     *
     * @param configFile the configuration file
     */
    public PlayerConfiguration(File configFile) {
        super(configFile);
    }

    @Override
    public void load() {
        super.load();
        persist = config.getBoolean("persist", false);
        if (persist && config.isConfigurationSection("modifiers")) {
            modifiers = new ModifierSet(config.getConfigurationSection("modifiers")
                    .getValues(false));
        } else {
            modifiers = new ModifierSet();
        }
    }

    @Override
    public void save() {
        config.set("persist", persist);
        config.set("modifiers", persist ? modifiers.serialize() : null);
        super.save();
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
