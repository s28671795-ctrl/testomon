/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DoorTamperState")
@XmlEnum
public enum DoorTamperState {
    UNKNOWN("Unknown"),
    NOT_IN_TAMPER("NotInTamper"),
    TAMPER_DETECTED("TamperDetected");

    private final String value;

    private DoorTamperState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DoorTamperState fromValue(String v) {
        for (DoorTamperState c : DoorTamperState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

