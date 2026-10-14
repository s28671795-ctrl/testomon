/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="VideoEncodingProfiles")
@XmlEnum
public enum VideoEncodingProfiles {
    SIMPLE("Simple"),
    ADVANCED_SIMPLE("AdvancedSimple"),
    BASELINE("Baseline"),
    MAIN("Main"),
    MAIN_10("Main10"),
    EXTENDED("Extended"),
    HIGH("High");

    private final String value;

    private VideoEncodingProfiles(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static VideoEncodingProfiles fromValue(String v) {
        for (VideoEncodingProfiles c : VideoEncodingProfiles.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

