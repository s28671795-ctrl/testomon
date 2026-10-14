/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="DiscoveryMode")
@XmlEnum
public enum DiscoveryMode {
    DISCOVERABLE("Discoverable"),
    NON_DISCOVERABLE("NonDiscoverable");

    private final String value;

    private DiscoveryMode(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static DiscoveryMode fromValue(String v) {
        for (DiscoveryMode c : DiscoveryMode.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

