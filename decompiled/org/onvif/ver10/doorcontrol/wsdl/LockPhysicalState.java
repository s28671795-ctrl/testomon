/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="LockPhysicalState")
@XmlEnum
public enum LockPhysicalState {
    UNKNOWN("Unknown"),
    LOCKED("Locked"),
    UNLOCKED("Unlocked"),
    FAULT("Fault");

    private final String value;

    private LockPhysicalState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static LockPhysicalState fromValue(String v) {
        for (LockPhysicalState c : LockPhysicalState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

