/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Dot11StationMode")
@XmlEnum
public enum Dot11StationMode {
    AD_HOC("Ad-hoc"),
    INFRASTRUCTURE("Infrastructure"),
    EXTENDED("Extended");

    private final String value;

    private Dot11StationMode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static Dot11StationMode fromValue(String v) {
        for (Dot11StationMode c : Dot11StationMode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

