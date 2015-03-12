package com.michaelelin.StandMaster.data;

/**
 * Interface designed to be implemented in order to mutate data of type
 * {@code V} for an object of type {@code T}.
 *
 * @param <T> the type of object to mutate data for
 * @param <V> the type of data to mutate
 */
public interface DataMutator<T, V> {

    /**
     * Mutates data for the given object with the given value.
     *
     * @param object the object to mutate data for
     * @param value the value to mutate the object with
     */
    public void mutate(T object, V value);

}
