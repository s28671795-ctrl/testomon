/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ReverseMode")
@XmlEnum
public enum ReverseMode {
    OFF("OFF"),
    ON("ON"),
    AUTO("AUTO"),
    EXTENDED("Extended");

    private final String value;

    private ReverseMode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ReverseMode fromValue(String v) {
        for (ReverseMode c : ReverseMode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

