package com.michaelelin.standmaster.config;

import java.util.Collection;
import com.michaelelin.standmaster.ModifierSet;

/**
 * Object representation of a configuration file.
 */
public interface Configuration {

    /**
     * Loads properties from the configuration file.
     */
    public void load();

    /**
     * Saves properties to the configuration file.
     */
    public void save();

    /**
     * Adds the given preset with the given name to the configuration.
     *
     * @param name the name of the preset
     * @param preset the preset
     * @return whether the preset was added
     */
    public boolean addPreset(String name, ModifierSet preset);

    /**
     * Removes the preset with the given name from the configuration.
     *
     * @param name the name of the preset
     * @return whether a preset was removed
     */
    public boolean removePreset(String name);

    /**
     * Returns the preset with the given name.
     *
     * @param name the name of the preset
     * @return the preset
     */
    public ModifierSet getPreset(String name);

    /**
     * Returns a collection of names of presets.
     *
     * @return the preset names
     */
    public Collection<String> listPresets();

}
