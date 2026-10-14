/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="EFlipMode")
@XmlEnum
public enum EFlipMode {
    OFF("OFF"),
    ON("ON"),
    EXTENDED("Extended");

    private final String value;

    private EFlipMode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static EFlipMode fromValue(String v) {
        for (EFlipMode c : EFlipMode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

