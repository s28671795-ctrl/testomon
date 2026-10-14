/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DoorPhysicalState")
@XmlEnum
public enum DoorPhysicalState {
    UNKNOWN("Unknown"),
    OPEN("Open"),
    CLOSED("Closed"),
    FAULT("Fault");

    private final String value;

    private DoorPhysicalState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DoorPhysicalState fromValue(String v) {
        for (DoorPhysicalState c : DoorPhysicalState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

