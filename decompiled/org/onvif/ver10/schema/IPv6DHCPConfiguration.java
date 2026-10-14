/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="IPv6DHCPConfiguration")
@XmlEnum
public enum IPv6DHCPConfiguration {
    AUTO("Auto"),
    STATEFUL("Stateful"),
    STATELESS("Stateless"),
    OFF("Off");

    private final String value;

    private IPv6DHCPConfiguration(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static IPv6DHCPConfiguration fromValue(String v) {
        for (IPv6DHCPConfiguration c : IPv6DHCPConfiguration.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

