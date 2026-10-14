/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TransportProtocol")
@XmlEnum
public enum TransportProtocol {
    UDP,
    TCP,
    RTSP,
    HTTP;


    public String value() {
        return this.name();
    }

    public static TransportProtocol fromValue(String v) {
        return TransportProtocol.valueOf(v);
    }
}

