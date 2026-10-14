/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="NetworkHostType")
@XmlEnum
public enum NetworkHostType {
    I_PV_4("IPv4"),
    I_PV_6("IPv6"),
    DNS("DNS");

    private final String value;

    private NetworkHostType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static NetworkHostType fromValue(String v) {
        for (NetworkHostType c : NetworkHostType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

