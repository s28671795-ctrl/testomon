/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PropertyOperation")
@XmlEnum
public enum PropertyOperation {
    INITIALIZED("Initialized"),
    DELETED("Deleted"),
    CHANGED("Changed");

    private final String value;

    private PropertyOperation(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PropertyOperation fromValue(String v) {
        for (PropertyOperation c : PropertyOperation.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

