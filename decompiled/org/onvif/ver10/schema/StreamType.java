/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="StreamType")
@XmlEnum
public enum StreamType {
    RTP_UNICAST("RTP-Unicast"),
    RTP_MULTICAST("RTP-Multicast");

    private final String value;

    private StreamType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static StreamType fromValue(String v) {
        for (StreamType c : StreamType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

