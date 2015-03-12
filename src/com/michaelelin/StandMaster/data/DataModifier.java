package com.michaelelin.StandMaster.data;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * Abstract class designed to be extended in order to modify a property of an
 * entity of type {@code T}.
 *
 * @param <T> the type of entity to modify
 * @param <V> the type of data used to modify the entity
 */
public abstract class DataModifier<T extends Entity, V extends StandMasterData> {

    private String identifier;
    private DataType type;
    private EntityType eType;

    /**
     * Constructs a {@code DataModifier} for the given command identifier, data
     * type, and entity type.
     *
     * @param identifier the identifier of the command that modifies the entity
     * @param type the type of data used to modify the entity
     * @param eType the type of entity to modify
     */
    public DataModifier(String identifier, DataType type, EntityType eType) {
        this.identifier = identifier;
        this.type = type;
        this.eType = eType;
    }

    @SuppressWarnings("unchecked")
    public Executable apply(StandMasterData value) {
        return new Executable((V) value);
    }

    protected abstract void execute(T object, V value);

    /**
     * Returns the type of data used to modify the entity.
     *
     * @return the data type
     */
    public DataType getType() {
        return type;
    }

    /**
     * The class that executes the given modification on a given entity.
     */
    public final class Executable {
        private V value;

        /**
         * Constructs an {@code Executable} with the given modifier value.
         * @param value the value to modify the entity with
         */
        public Executable(V value) {
            this.value = value;
        }

        private void execute(T object) {
            DataModifier.this.execute(object, value);
        }

        /**
         * Modifies the given entity with this instance's modification value.
         *
         * @param object the entity to modify
         */
        @SuppressWarnings("unchecked")
        public void modify(Entity object) {
            if (object.getType() == eType) {
                execute((T) object);
            }
        }

        /**
         * Returns the identifier of the command that performs this
         * modification.
         *
         * @return the command's identifier
         */
        public String getIdentifier() {
            return identifier;
        }

        /**
         * Returns the value used to modify the entity.
         *
         * @return the modification value
         */
        public V getValue() {
            return value;
        }

    }

}
