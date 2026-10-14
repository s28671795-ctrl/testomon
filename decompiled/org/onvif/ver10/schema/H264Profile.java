/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="H264Profile")
@XmlEnum
public enum H264Profile {
    BASELINE("Baseline"),
    MAIN("Main"),
    EXTENDED("Extended"),
    HIGH("High");

    private final String value;

    private H264Profile(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static H264Profile fromValue(String v) {
        for (H264Profile c : H264Profile.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

