/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RelayLogicalState")
@XmlEnum
public enum RelayLogicalState {
    ACTIVE("active"),
    INACTIVE("inactive");

    private final String value;

    private RelayLogicalState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static RelayLogicalState fromValue(String v) {
        for (RelayLogicalState c : RelayLogicalState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

