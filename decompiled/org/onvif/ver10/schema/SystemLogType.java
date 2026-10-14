/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SystemLogType")
@XmlEnum
public enum SystemLogType {
    SYSTEM("System"),
    ACCESS("Access");

    private final String value;

    private SystemLogType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static SystemLogType fromValue(String v) {
        for (SystemLogType c : SystemLogType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

