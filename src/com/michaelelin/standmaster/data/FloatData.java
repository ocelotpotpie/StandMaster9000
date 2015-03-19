package com.michaelelin.standmaster.data;

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
     *
     * @param value the float value
     */
    public FloatData(float value) {
        this.value = value;
    }

    /**
     * Constructs a FloatData object from a given double value.
     *
     * @param value the double value
     */
    public FloatData(double value) {
        this.value = (float) value;
    }

    @Override
    public Float serialize() {
        return value;
    }

    @Override
    public String toString() {
        return Float.toString(value);
    }

}
