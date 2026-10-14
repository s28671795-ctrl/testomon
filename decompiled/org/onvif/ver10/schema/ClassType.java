/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ClassType")
@XmlEnum
public enum ClassType {
    ANIMAL("Animal"),
    FACE("Face"),
    HUMAN("Human"),
    VEHICAL("Vehical"),
    OTHER("Other");

    private final String value;

    private ClassType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ClassType fromValue(String v) {
        for (ClassType c : ClassType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

