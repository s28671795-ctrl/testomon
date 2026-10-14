/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="MoveStatus")
@XmlEnum
public enum MoveStatus {
    IDLE,
    MOVING,
    UNKNOWN;


    public String value() {
        return this.name();
    }

    public static MoveStatus fromValue(String v) {
        return MoveStatus.valueOf(v);
    }
}

