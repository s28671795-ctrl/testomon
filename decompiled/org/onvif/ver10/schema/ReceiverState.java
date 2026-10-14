/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ReceiverState")
@XmlEnum
public enum ReceiverState {
    NOT_CONNECTED("NotConnected"),
    CONNECTING("Connecting"),
    CONNECTED("Connected"),
    UNKNOWN("Unknown");

    private final String value;

    private ReceiverState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ReceiverState fromValue(String v) {
        for (ReceiverState c : ReceiverState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

