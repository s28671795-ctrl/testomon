/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Dot11AuthAndMangementSuite")
@XmlEnum
public enum Dot11AuthAndMangementSuite {
    NONE("None"),
    DOT_1_X("Dot1X"),
    PSK("PSK"),
    EXTENDED("Extended");

    private final String value;

    private Dot11AuthAndMangementSuite(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static Dot11AuthAndMangementSuite fromValue(String v) {
        for (Dot11AuthAndMangementSuite c : Dot11AuthAndMangementSuite.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

