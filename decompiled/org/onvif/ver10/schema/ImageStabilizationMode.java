/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ImageStabilizationMode")
@XmlEnum
public enum ImageStabilizationMode {
    OFF("OFF"),
    ON("ON"),
    AUTO("AUTO"),
    EXTENDED("Extended");

    private final String value;

    private ImageStabilizationMode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ImageStabilizationMode fromValue(String v) {
        for (ImageStabilizationMode c : ImageStabilizationMode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

