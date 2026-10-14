/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="IrCutFilterAutoBoundaryType")
@XmlEnum
public enum IrCutFilterAutoBoundaryType {
    COMMON("Common"),
    TO_ON("ToOn"),
    TO_OFF("ToOff"),
    EXTENDED("Extended");

    private final String value;

    private IrCutFilterAutoBoundaryType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static IrCutFilterAutoBoundaryType fromValue(String v) {
        for (IrCutFilterAutoBoundaryType c : IrCutFilterAutoBoundaryType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

