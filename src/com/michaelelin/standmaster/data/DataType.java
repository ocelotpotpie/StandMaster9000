package com.michaelelin.standmaster.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A type of data used to modify the properties of an entity.
 */
public enum DataType {
    BOOLEAN(BooleanData.class),
    INT(IntData.class),
    FLOAT(FloatData.class),
    STRING(StringData.class);

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

    /**
     * Returns the data wrapper class associated with this data type.
     *
     * @return the data wrapper class
     */
    public Class<? extends StandMasterData> toClass() {
        return clazz;
    }

    /**
     * Wraps a value of this data type in an instance of {@code
     * StandMasterData}.
     *
     * @param value the value to wrap
     * @return the wrapped value
     */
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
        default:
            return null;
        }
    }

    /**
     * Attempts to deserialize the given value into this data type.
     *
     * @param value the serialized object
     * @return a {@code StandMasterData} representation of the value this type
     */
    public StandMasterData deserialize(Object value) {
        switch(this) {
        case BOOLEAN:
            return new BooleanData((boolean) value);
        case INT:
            return new IntData((int) value);
        case FLOAT:
            return new FloatData((Double) value);
        case STRING:
            return new StringData((String) value);
        default:
            return null;
        }
    }

    /**
     * Returns the data type corresponding to the given data wrapper class.
     *
     * @param clazz the wrapper class
     * @return the data type
     */
    public static DataType fromClass(Class<? extends StandMasterData> clazz) {
        return dataMap.get(clazz);
    }

	/**
	 * Returns the user-friendly string describing the data type.
	 * 
	 * @return the user-friendly string
	 */
    @Override
	public String toString() {
        switch(this) {
        case BOOLEAN:
		    return new String("TRUE/FALSE");
		case INT:
		    return new String("NUMBER");
		case FLOAT:
			return new String("DECIMAL");
		case STRING:
			return new String("WORD");
		default:
			return null;
		}
	}    
}
