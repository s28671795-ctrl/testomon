/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Dot11SecurityMode")
@XmlEnum
public enum Dot11SecurityMode {
    NONE("None"),
    WEP("WEP"),
    PSK("PSK"),
    DOT_1_X("Dot1X"),
    EXTENDED("Extended");

    private final String value;

    private Dot11SecurityMode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static Dot11SecurityMode fromValue(String v) {
        for (Dot11SecurityMode c : Dot11SecurityMode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

