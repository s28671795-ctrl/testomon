/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SerialPortType")
@XmlEnum
public enum SerialPortType {
    RS_232("RS232"),
    RS_422_HALF_DUPLEX("RS422HalfDuplex"),
    RS_422_FULL_DUPLEX("RS422FullDuplex"),
    RS_485_HALF_DUPLEX("RS485HalfDuplex"),
    RS_485_FULL_DUPLEX("RS485FullDuplex"),
    GENERIC("Generic");

    private final String value;

    private SerialPortType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static SerialPortType fromValue(String v) {
        for (SerialPortType c : SerialPortType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

