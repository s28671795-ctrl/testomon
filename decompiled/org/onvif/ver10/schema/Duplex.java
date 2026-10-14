/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Duplex")
@XmlEnum
public enum Duplex {
    FULL("Full"),
    HALF("Half");

    private final String value;

    private Duplex(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static Duplex fromValue(String v) {
        for (Duplex c : Duplex.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

