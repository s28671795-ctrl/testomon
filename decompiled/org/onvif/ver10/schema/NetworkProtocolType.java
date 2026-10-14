/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="NetworkProtocolType")
@XmlEnum
public enum NetworkProtocolType {
    HTTP,
    HTTPS,
    RTSP;


    public String value() {
        return this.name();
    }

    public static NetworkProtocolType fromValue(String v) {
        return NetworkProtocolType.valueOf(v);
    }
}

