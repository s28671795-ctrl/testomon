/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CapabilityCategory")
@XmlEnum
public enum CapabilityCategory {
    ALL("All"),
    ANALYTICS("Analytics"),
    DEVICE("Device"),
    EVENTS("Events"),
    IMAGING("Imaging"),
    MEDIA("Media"),
    PTZ("PTZ");

    private final String value;

    private CapabilityCategory(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static CapabilityCategory fromValue(String v) {
        for (CapabilityCategory c : CapabilityCategory.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

