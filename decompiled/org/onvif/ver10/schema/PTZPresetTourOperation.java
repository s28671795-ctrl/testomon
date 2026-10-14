/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="PTZPresetTourOperation")
@XmlEnum
public enum PTZPresetTourOperation {
    START("Start"),
    STOP("Stop"),
    PAUSE("Pause"),
    EXTENDED("Extended");

    private final String value;

    private PTZPresetTourOperation(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static PTZPresetTourOperation fromValue(String v) {
        for (PTZPresetTourOperation c : PTZPresetTourOperation.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

