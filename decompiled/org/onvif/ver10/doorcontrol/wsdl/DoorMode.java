/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DoorMode")
@XmlEnum
public enum DoorMode {
    UNKNOWN("Unknown"),
    LOCKED("Locked"),
    UNLOCKED("Unlocked"),
    ACCESSED("Accessed"),
    BLOCKED("Blocked"),
    LOCKED_DOWN("LockedDown"),
    LOCKED_OPEN("LockedOpen"),
    DOUBLE_LOCKED("DoubleLocked");

    private final String value;

    private DoorMode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DoorMode fromValue(String v) {
        for (DoorMode c : DoorMode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

