/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="IPAddressFilterType")
@XmlEnum
public enum IPAddressFilterType {
    ALLOW("Allow"),
    DENY("Deny");

    private final String value;

    private IPAddressFilterType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static IPAddressFilterType fromValue(String v) {
        for (IPAddressFilterType c : IPAddressFilterType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

