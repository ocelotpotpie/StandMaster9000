package com.michaelelin.StandMaster.data;

/**
 * An int wrapper.
 */
public final class IntData implements StandMasterData {
    /**
     * The int value
     */
    public final int value;

    /**
     * Constructs an IntData object from a given int value
     *
     * @param value the int value
     */
    public IntData(int value) {
        this.value = value;
    }

    @Override
    public Integer serialize() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
