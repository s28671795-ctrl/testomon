/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PTZPresetTourState")
@XmlEnum
public enum PTZPresetTourState {
    IDLE("Idle"),
    TOURING("Touring"),
    PAUSED("Paused"),
    EXTENDED("Extended");

    private final String value;

    private PTZPresetTourState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PTZPresetTourState fromValue(String v) {
        for (PTZPresetTourState c : PTZPresetTourState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

