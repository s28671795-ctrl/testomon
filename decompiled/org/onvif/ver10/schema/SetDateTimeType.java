/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SetDateTimeType")
@XmlEnum
public enum SetDateTimeType {
    MANUAL("Manual"),
    NTP("NTP");

    private final String value;

    private SetDateTimeType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static SetDateTimeType fromValue(String v) {
        for (SetDateTimeType c : SetDateTimeType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

