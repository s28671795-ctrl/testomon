/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Dot11SignalStrength")
@XmlEnum
public enum Dot11SignalStrength {
    NONE("None"),
    VERY_BAD("Very Bad"),
    BAD("Bad"),
    GOOD("Good"),
    VERY_GOOD("Very Good"),
    EXTENDED("Extended");

    private final String value;

    private Dot11SignalStrength(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static Dot11SignalStrength fromValue(String v) {
        for (Dot11SignalStrength c : Dot11SignalStrength.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

