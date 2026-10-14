/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ReceiverMode")
@XmlEnum
public enum ReceiverMode {
    AUTO_CONNECT("AutoConnect"),
    ALWAYS_CONNECT("AlwaysConnect"),
    NEVER_CONNECT("NeverConnect"),
    UNKNOWN("Unknown");

    private final String value;

    private ReceiverMode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ReceiverMode fromValue(String v) {
        for (ReceiverMode c : ReceiverMode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

