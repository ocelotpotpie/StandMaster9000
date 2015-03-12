package com.michaelelin.StandMaster.data;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public abstract class DataModifier<T extends Entity, V extends StandMasterData> {

    private String footprint;
    private DataType type;
    private EntityType eType;

    public DataModifier(String footprint, DataType type, EntityType eType) {
        this.footprint = footprint;
        this.type = type;
        this.eType = eType;
    }

    @SuppressWarnings("unchecked")
    public Executable apply(StandMasterData value) {
        return new Executable((V) value);
    }

    protected abstract void execute(T object, V value);

    public DataType getType() {
        return type;
    }

    public class Executable {
        private V value;

        public Executable(V value) {
            this.value = value;
        }

        private void execute(T object) {
            DataModifier.this.execute(object, value);
        }

        @SuppressWarnings("unchecked")
        public void modify(Entity object) {
            if (object.getType() == eType) {
                execute((T) object);
            }
        }

        public String getFootprint() {
            return footprint;
        }

        public V getValue() {
            return value;
        }

    }

}
