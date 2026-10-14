/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ScopeDefinition")
@XmlEnum
public enum ScopeDefinition {
    FIXED("Fixed"),
    CONFIGURABLE("Configurable");

    private final String value;

    private ScopeDefinition(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ScopeDefinition fromValue(String v) {
        for (ScopeDefinition c : ScopeDefinition.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

