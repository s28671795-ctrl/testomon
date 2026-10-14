/*
 * Decompiled with CFR 0.152.
 */
package liquibase.configuration;

import java.util.logging.Level;
import liquibase.Scope;

public interface ConfigurationValueConverter<DataType> {
    public static final ConfigurationValueConverter<Level> LOG_LEVEL = value -> {
        String stringLevel;
        if (value == null) {
            return null;
        }
        if (value instanceof Level) {
            return (Level)value;
        }
        switch (stringLevel = String.valueOf(value).toUpperCase()) {
            case "DEBUG": {
                return Level.FINE;
            }
            case "WARN": {
                return Level.WARNING;
            }
            case "ERROR": {
                return Level.SEVERE;
            }
        }
        try {
            return Level.parse(stringLevel);
        }
        catch (IllegalArgumentException e) {
            Scope.getCurrentScope().getUI().sendErrorMessage("WARNING:  Unknown log level " + stringLevel + ". Defaulting to 'INFO'");
            return Level.INFO;
        }
    };
    public static final ConfigurationValueConverter<String> STRING = value -> {
        if (value == null) {
            return null;
        }
        return String.valueOf(value);
    };
    public static final ConfigurationValueConverter<Class> CLASS = value -> {
        if (value == null) {
            return null;
        }
        try {
            return Class.forName(String.valueOf(value));
        }
        catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot instantiate " + value + ": " + e.getMessage(), e);
        }
    };

    public DataType convert(Object var1) throws IllegalArgumentException;
}

