/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RelayIdleState")
@XmlEnum
public enum RelayIdleState {
    CLOSED("closed"),
    OPEN("open");

    private final String value;

    private RelayIdleState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static RelayIdleState fromValue(String v) {
        for (RelayIdleState c : RelayIdleState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

