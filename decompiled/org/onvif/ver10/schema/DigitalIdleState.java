/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DigitalIdleState")
@XmlEnum
public enum DigitalIdleState {
    CLOSED("closed"),
    OPEN("open");

    private final String value;

    private DigitalIdleState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DigitalIdleState fromValue(String v) {
        for (DigitalIdleState c : DigitalIdleState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

