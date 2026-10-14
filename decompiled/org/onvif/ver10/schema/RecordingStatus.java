/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RecordingStatus")
@XmlEnum
public enum RecordingStatus {
    INITIATED("Initiated"),
    RECORDING("Recording"),
    STOPPED("Stopped"),
    REMOVING("Removing"),
    REMOVED("Removed"),
    UNKNOWN("Unknown");

    private final String value;

    private RecordingStatus(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static RecordingStatus fromValue(String v) {
        for (RecordingStatus c : RecordingStatus.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

