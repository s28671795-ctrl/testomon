/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TrackType")
@XmlEnum
public enum TrackType {
    VIDEO("Video"),
    AUDIO("Audio"),
    METADATA("Metadata"),
    EXTENDED("Extended");

    private final String value;

    private TrackType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TrackType fromValue(String v) {
        for (TrackType c : TrackType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

