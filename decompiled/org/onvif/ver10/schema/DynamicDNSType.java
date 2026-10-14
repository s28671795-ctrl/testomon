/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DynamicDNSType")
@XmlEnum
public enum DynamicDNSType {
    NO_UPDATE("NoUpdate"),
    CLIENT_UPDATES("ClientUpdates"),
    SERVER_UPDATES("ServerUpdates");

    private final String value;

    private DynamicDNSType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DynamicDNSType fromValue(String v) {
        for (DynamicDNSType c : DynamicDNSType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

