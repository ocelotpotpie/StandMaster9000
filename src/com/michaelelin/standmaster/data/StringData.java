package com.michaelelin.standmaster.data;

/**
 * A String wrapper.
 */
public final class StringData implements StandMasterData {
    /**
     * The String value.
     */
    public final String value;

    /**
     * Constructs a StringData object from a given String value.
     * @param value the String value
     */
    public StringData(String value) {
        this.value = value;
    }

    @Override
    public String serialize() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
