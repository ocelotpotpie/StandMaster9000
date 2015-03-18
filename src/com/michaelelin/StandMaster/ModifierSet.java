package com.michaelelin.StandMaster;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;

import com.michaelelin.StandMaster.data.DataModifier;

/**
 * A set of modifiers.
 */
public class ModifierSet extends AbstractSet<DataModifier<?, ?>.Executable> implements Cloneable {

    private final Collection<DataModifier<?, ?>.Executable> delegate;

    /**
     * Constructs an empty modifier set.
     */
    public ModifierSet() {
        delegate = new TreeSet<>();
    }

    /**
     * Constructs a modifier set with the same elements as the given
     * collection.
     *
     * @param values the collection of values
     */
    public ModifierSet(Collection<DataModifier<?, ?>.Executable> values) {
        delegate = new TreeSet<>(values);
    }

    /**
     * Constructs a modifier set from the given identifier-value map.
     *
     * @param values the identifier-value map
     */
    public ModifierSet(Map<String, Object> values) {
        this();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            DataModifier<?, ?> modifier = StandMasterPlugin.getInstance().getModifierTable()
                    .get(entry.getKey().replace('_', '.'));
            add(modifier.apply(modifier.getType().deserialize(entry.getValue())));
        }
    }

    /**
     * Serialize the modifier set into a map from identifiers to values.
     *
     * @return the map from identifiers to values
     */
    public Map<String, Object> serialize() {
        Map<String, Object> ser = new LinkedHashMap<>();
        for (DataModifier<?, ?>.Executable modifier : delegate) {
            // Bukkit's YAML parser doesn't like periods in key names
            ser.put(modifier.getIdentifier().replace('.', '_'), modifier.getValue().serialize());
        }
        return ser;
    }

    @Override
    public boolean add(DataModifier<?, ?>.Executable modifier) {
        for (Iterator<DataModifier<?, ?>.Executable> it = delegate.iterator(); it.hasNext();) {
            if (it.next().getIdentifier().equals(modifier.getIdentifier())) {
                it.remove();
            }
        }
        delegate.add(modifier);
        return true;
    }

    @Override
    public Iterator<DataModifier<?, ?>.Executable> iterator() {
        return delegate.iterator();
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public ModifierSet clone() {
        return new ModifierSet(this);
    }

}
