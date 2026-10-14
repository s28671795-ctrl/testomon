/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PTZPresetTourDirection")
@XmlEnum
public enum PTZPresetTourDirection {
    FORWARD("Forward"),
    BACKWARD("Backward"),
    EXTENDED("Extended");

    private final String value;

    private PTZPresetTourDirection(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PTZPresetTourDirection fromValue(String v) {
        for (PTZPresetTourDirection c : PTZPresetTourDirection.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

