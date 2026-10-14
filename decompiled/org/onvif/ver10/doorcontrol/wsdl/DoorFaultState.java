/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DoorFaultState")
@XmlEnum
public enum DoorFaultState {
    UNKNOWN("Unknown"),
    NOT_IN_FAULT("NotInFault"),
    FAULT_DETECTED("FaultDetected");

    private final String value;

    private DoorFaultState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DoorFaultState fromValue(String v) {
        for (DoorFaultState c : DoorFaultState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

