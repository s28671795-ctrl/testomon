/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http.configuration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="connectionType")
@XmlEnum
public enum ConnectionType {
    CLOSE("close"),
    KEEP_ALIVE("Keep-Alive");

    private final String value;

    private ConnectionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ConnectionType fromValue(String v) {
        for (ConnectionType c : ConnectionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

