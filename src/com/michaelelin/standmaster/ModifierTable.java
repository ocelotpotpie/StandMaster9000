package com.michaelelin.standmaster;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.util.EulerAngle;

import com.michaelelin.standmaster.data.BooleanData;
import com.michaelelin.standmaster.data.DataAccessor;
import com.michaelelin.standmaster.data.DataModifier;
import com.michaelelin.standmaster.data.DataMutator;
import com.michaelelin.standmaster.data.DataType;
import com.michaelelin.standmaster.data.FloatData;
import com.michaelelin.standmaster.data.StringData;

/**
 * A table that maps commands (as strings or context,name pairs) to
 * DataModifiers.
 */
public class ModifierTable {
    private Map<String, DataModifier<?, ?>> table;

    /**
     * Constructs a new modifier table.
     */
    public ModifierTable() {
        table = new HashMap<String, DataModifier<?, ?>>();
    }

    /**
     * Gets the modifier from the given command identifier.
     *
     * @param command the command identifier
     * @return the modifier for the command identifier
     */
    public DataModifier<?, ?> get(String command) {
        return table.get(command);
    }

    /**
     * Gets the modifier from the given command.
     *
     * @param context the context of the command
     * @param name the name of the command
     * @return the modifier for the command
     */
    public DataModifier<?, ?> get(Collection<String> context, String name) {
        return get(CommandTree.getCommandIdentifier(context, name));
    }

    /**
     * Adds the default armor stand modifiers.
     */
    public void addDefaults() {
        add("stand.name", new DataModifier<ArmorStand, StringData>("stand.name",
                DataType.STRING, EntityType.ARMOR_STAND) {
            @Override
            protected void execute(ArmorStand object, StringData value) {
                object.setCustomName(ChatColor.translateAlternateColorCodes('&', value.value));
                object.setCustomNameVisible(true);
            }
        });
        add("stand.invisible", new DataModifier<ArmorStand, BooleanData>("stand.invisible",
                DataType.BOOLEAN, EntityType.ARMOR_STAND) {
            @Override
            protected void execute(ArmorStand object, BooleanData value) {
                object.setVisible(!value.value);
            }
        });
        add("stand.nobaseplate", new DataModifier<ArmorStand, BooleanData>("stand.nobaseplate",
                DataType.BOOLEAN, EntityType.ARMOR_STAND) {
            @Override
            protected void execute(ArmorStand object, BooleanData value) {
                object.setBasePlate(!value.value);
            }
        });
        add("stand.nogravity", new DataModifier<ArmorStand, BooleanData>("stand.nogravity",
                DataType.BOOLEAN, EntityType.ARMOR_STAND) {
            @Override
            protected void execute(ArmorStand object, BooleanData value) {
                object.setGravity(!value.value);
            }
        });
        addRotation("stand.pose.body", new DataAccessor<ArmorStand, EulerAngle>() {
            @Override
            public EulerAngle access(ArmorStand object) {
                return object.getBodyPose();
            }
        }, new DataMutator<ArmorStand, EulerAngle>() {
            @Override
            public void mutate(ArmorStand object, EulerAngle value) {
                object.setBodyPose(value);
            }
        });
        addRotation("stand.pose.leftarm", new DataAccessor<ArmorStand, EulerAngle>() {
            @Override
            public EulerAngle access(ArmorStand object) {
                return object.getLeftArmPose();
            }
        }, new DataMutator<ArmorStand, EulerAngle>() {
            @Override
            public void mutate(ArmorStand object, EulerAngle value) {
                object.setLeftArmPose(value);
            }
        });
        addRotation("stand.pose.rightarm", new DataAccessor<ArmorStand, EulerAngle>() {
            @Override
            public EulerAngle access(ArmorStand object) {
                return object.getRightArmPose();
            }
        }, new DataMutator<ArmorStand, EulerAngle>() {
            @Override
            public void mutate(ArmorStand object, EulerAngle value) {
                object.setRightArmPose(value);
            }
        });
        addRotation("stand.pose.leftleg", new DataAccessor<ArmorStand, EulerAngle>() {
            @Override
            public EulerAngle access(ArmorStand object) {
                return object.getLeftLegPose();
            }
        }, new DataMutator<ArmorStand, EulerAngle>() {
            @Override
            public void mutate(ArmorStand object, EulerAngle value) {
                object.setLeftLegPose(value);
            }
        });
        addRotation("stand.pose.rightleg", new DataAccessor<ArmorStand, EulerAngle>() {
            @Override
            public EulerAngle access(ArmorStand object) {
                return object.getRightLegPose();
            }
        }, new DataMutator<ArmorStand, EulerAngle>() {
            @Override
            public void mutate(ArmorStand object, EulerAngle value) {
                object.setRightLegPose(value);
            }
        });
        addRotation("stand.pose.head", new DataAccessor<ArmorStand, EulerAngle>() {
            @Override
            public EulerAngle access(ArmorStand object) {
                return object.getHeadPose();
            }
        }, new DataMutator<ArmorStand, EulerAngle>() {
            @Override
            public void mutate(ArmorStand object, EulerAngle value) {
                object.setHeadPose(value);
            }
        });
        add("stand.showarms", new DataModifier<ArmorStand, BooleanData>("stand.showarms",
                DataType.BOOLEAN, EntityType.ARMOR_STAND) {
            @Override
            protected void execute(ArmorStand object, BooleanData value) {
                object.setArms(value.value);
            }
        });
        add("stand.small", new DataModifier<ArmorStand, BooleanData>("stand.small",
                DataType.BOOLEAN, EntityType.ARMOR_STAND) {
            @Override
            protected void execute(ArmorStand object, BooleanData value) {
                object.setSmall(value.value);
            }
        });
    }

    /**
     * Maps the given command identifier to the given modifier.
     *
     * @param identifier the command identifier
     * @param mod the modifier
     */
    public void add(String identifier, DataModifier<?, ?> mod) {
        table.put(identifier, mod);
    }

    /**
     * Maps the given command and relevant subcommands to the proper rotation
     * modifiers.
     *
     * @param command the command
     * @param accessor an accessor that determines the correct
     * {@code EulerAngle} from a given armor stand
     */
    public void addRotation(String command, final DataAccessor<ArmorStand, EulerAngle> accessor,
            final DataMutator<ArmorStand, EulerAngle> mutator) {
        add(command + ".x", new DataModifier<ArmorStand, FloatData>(command + ".x",
                DataType.FLOAT, EntityType.ARMOR_STAND) {
            @Override
            protected void execute(ArmorStand object, FloatData value) {
                mutator.mutate(object, accessor.access(object).setX(Math.toRadians(value.value)));
            }
        });
        add(command + ".y", new DataModifier<ArmorStand, FloatData>(command + ".y",
                DataType.FLOAT, EntityType.ARMOR_STAND) {
            @Override
            protected void execute(ArmorStand object, FloatData value) {
                mutator.mutate(object, accessor.access(object).setY(Math.toRadians(value.value)));
            }
        });
        add(command + ".z", new DataModifier<ArmorStand, FloatData>(command + ".z",
                DataType.FLOAT, EntityType.ARMOR_STAND) {
            @Override
            protected void execute(ArmorStand object, FloatData value) {
                mutator.mutate(object, accessor.access(object).setZ(Math.toRadians(value.value)));
            }
        });
    }

    /**
     * Maps the given command identifier to the given modifier.
     *
     * @param context the context of the command
     * @param name the name of the command
     * @param mod the modifier
     */
    public void add(Collection<String> context, String name, DataModifier<?, ?> mod) {
        add(CommandTree.getCommandIdentifier(context, name), mod);
    }

}
