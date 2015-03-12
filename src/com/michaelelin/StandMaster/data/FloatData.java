package com.michaelelin.StandMaster.data;

/**
 * A float wrapper.
 */
public final class FloatData implements StandMasterData {
    /**
     * The float value.
     */
    public final float value;

    /**
     * Constructs a FloatData object from a given float value.
     * @param value the float value
     */
    public FloatData(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }

}
