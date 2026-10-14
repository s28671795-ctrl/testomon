/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="FactoryDefaultType")
@XmlEnum
public enum FactoryDefaultType {
    HARD("Hard"),
    SOFT("Soft");

    private final String value;

    private FactoryDefaultType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static FactoryDefaultType fromValue(String v) {
        for (FactoryDefaultType c : FactoryDefaultType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

