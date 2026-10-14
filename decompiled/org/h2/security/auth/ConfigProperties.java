/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import org.h2.security.auth.AuthConfigException;
import org.h2.security.auth.PropertyConfig;
import org.h2.util.Utils;

public class ConfigProperties {
    private HashMap<String, String> properties = new HashMap();

    public ConfigProperties() {
    }

    public ConfigProperties(PropertyConfig ... propertyConfigArray) {
        this(propertyConfigArray == null ? null : Arrays.asList(propertyConfigArray));
    }

    public ConfigProperties(Collection<PropertyConfig> collection) {
        if (collection != null) {
            for (PropertyConfig propertyConfig : collection) {
                if (this.properties.putIfAbsent(propertyConfig.getName(), propertyConfig.getValue()) == null) continue;
                throw new AuthConfigException("duplicate property " + propertyConfig.getName());
            }
        }
    }

    public String getStringValue(String string, String string2) {
        String string3 = this.properties.get(string);
        if (string3 == null) {
            return string2;
        }
        return string3;
    }

    public String getStringValue(String string) {
        String string2 = this.properties.get(string);
        if (string2 == null) {
            throw new AuthConfigException("missing config property " + string);
        }
        return string2;
    }

    public int getIntValue(String string, int n) {
        String string2 = this.properties.get(string);
        if (string2 == null) {
            return n;
        }
        return Integer.parseInt(string2);
    }

    public int getIntValue(String string) {
        String string2 = this.properties.get(string);
        if (string2 == null) {
            throw new AuthConfigException("missing config property " + string);
        }
        return Integer.parseInt(string2);
    }

    public boolean getBooleanValue(String string, boolean bl) {
        String string2 = this.properties.get(string);
        if (string2 == null) {
            return bl;
        }
        return Utils.parseBoolean(string2, bl, true);
    }
}

