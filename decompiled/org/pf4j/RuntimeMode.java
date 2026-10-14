/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public enum RuntimeMode {
    DEVELOPMENT("development", "dev"),
    DEPLOYMENT("deployment", "prod");

    private final String name;
    private final String[] aliases;
    private static final Map<String, RuntimeMode> map;

    private RuntimeMode(String name, String ... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public String toString() {
        return this.name;
    }

    public static RuntimeMode byName(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        }
        throw new NoSuchElementException("Cannot found PF4J runtime mode with name '" + name + "'.Must be one value from '" + map.keySet() + ".");
    }

    static {
        map = new HashMap<String, RuntimeMode>();
        for (RuntimeMode mode : RuntimeMode.values()) {
            map.put(mode.name, mode);
            for (String alias : mode.aliases) {
                map.put(alias, mode);
            }
        }
    }
}

