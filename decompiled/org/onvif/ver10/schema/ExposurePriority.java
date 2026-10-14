/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ExposurePriority")
@XmlEnum
public enum ExposurePriority {
    LOW_NOISE("LowNoise"),
    FRAME_RATE("FrameRate");

    private final String value;

    private ExposurePriority(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ExposurePriority fromValue(String v) {
        for (ExposurePriority c : ExposurePriority.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

