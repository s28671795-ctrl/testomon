/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DoorAlarmState")
@XmlEnum
public enum DoorAlarmState {
    NORMAL("Normal"),
    DOOR_FORCED_OPEN("DoorForcedOpen"),
    DOOR_OPEN_TOO_LONG("DoorOpenTooLong");

    private final String value;

    private DoorAlarmState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DoorAlarmState fromValue(String v) {
        for (DoorAlarmState c : DoorAlarmState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

