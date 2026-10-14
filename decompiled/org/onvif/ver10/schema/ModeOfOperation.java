/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ModeOfOperation")
@XmlEnum
public enum ModeOfOperation {
    IDLE("Idle"),
    ACTIVE("Active"),
    UNKNOWN("Unknown");

    private final String value;

    private ModeOfOperation(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ModeOfOperation fromValue(String v) {
        for (ModeOfOperation c : ModeOfOperation.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

