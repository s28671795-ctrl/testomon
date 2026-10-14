/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="OSDType")
@XmlEnum
public enum OSDType {
    TEXT("Text"),
    IMAGE("Image"),
    EXTENDED("Extended");

    private final String value;

    private OSDType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static OSDType fromValue(String v) {
        for (OSDType c : OSDType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

