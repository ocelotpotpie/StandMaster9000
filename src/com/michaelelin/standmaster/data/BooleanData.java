package com.michaelelin.standmaster.data;

/**
 * A boolean wrapper.
 */
public final class BooleanData implements StandMasterData {
    /**
     * The boolean value.
     */
    public final boolean value;

    /**
     * Constructs a BooleanData object from a given boolean value.
     *
     * @param value the boolean value
     */
    public BooleanData(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean serialize() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

}
