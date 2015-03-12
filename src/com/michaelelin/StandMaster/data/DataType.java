package com.michaelelin.StandMaster.data;

import java.util.HashMap;
import java.util.Map;

public enum DataType {
    BOOLEAN(BooleanData.class),
    INT(IntData.class),
    FLOAT(FloatData.class),
    STRING(StringData.class),
    ROTATION(RotationData.class);

    private static Map<Class<? extends StandMasterData>, DataType> dataMap;
    static {
        dataMap = new HashMap<Class<? extends StandMasterData>, DataType>();
        for (DataType type : values()) {
            dataMap.put(type.clazz, type);
        }
    }

    private Class<? extends StandMasterData> clazz;

    private DataType(Class<? extends StandMasterData> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends StandMasterData> toClass() {
        return clazz;
    }

    public StandMasterData wrapValue(String value) {
        switch(this) {
        case BOOLEAN:
            return new BooleanData(Boolean.valueOf(value));
        case INT:
            return new IntData(Integer.valueOf(value));
        case FLOAT:
            return new FloatData(Float.valueOf(value));
        case STRING:
            return new StringData(value);
        case ROTATION:
            String[] coords = value.split(" ", 3);
            return new RotationData(Float.valueOf(coords[0]), Float.valueOf(coords[1]),
                    Float.valueOf(coords[2]));
        default:
            return null;
        }
    }

    public static DataType fromClass(Class<? extends StandMasterData> clazz) {
        return dataMap.get(clazz);
    }

}
