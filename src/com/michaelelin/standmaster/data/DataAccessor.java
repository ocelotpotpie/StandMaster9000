package com.michaelelin.standmaster.data;

/**
 * Interface designed to be implemented in order to access data of type
 * {@code V} from an object of type {@code T}.
 *
 * @param <T> the type of object to access data from
 * @param <V> the type of data to access
 */
public interface DataAccessor<T, V> {

    /**
     * Accesses data from the given object.
     *
     * @param object the object to access data from
     * @return the accessed data
     */
    public V access(T object);

}
