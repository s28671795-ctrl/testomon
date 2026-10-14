/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RelayMode")
@XmlEnum
public enum RelayMode {
    MONOSTABLE("Monostable"),
    BISTABLE("Bistable");

    private final String value;

    private RelayMode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static RelayMode fromValue(String v) {
        for (RelayMode c : RelayMode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

