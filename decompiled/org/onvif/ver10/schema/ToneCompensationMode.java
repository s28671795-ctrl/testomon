/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ToneCompensationMode")
@XmlEnum
public enum ToneCompensationMode {
    OFF,
    ON,
    AUTO;


    public String value() {
        return this.name();
    }

    public static ToneCompensationMode fromValue(String v) {
        return ToneCompensationMode.valueOf(v);
    }
}

