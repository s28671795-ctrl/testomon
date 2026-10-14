/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="IPType")
@XmlEnum
public enum IPType {
    I_PV_4("IPv4"),
    I_PV_6("IPv6");

    private final String value;

    private IPType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static IPType fromValue(String v) {
        for (IPType c : IPType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

