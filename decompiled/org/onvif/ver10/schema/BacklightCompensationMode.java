/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="BacklightCompensationMode")
@XmlEnum
public enum BacklightCompensationMode {
    OFF,
    ON;


    public String value() {
        return this.name();
    }

    public static BacklightCompensationMode fromValue(String v) {
        return BacklightCompensationMode.valueOf(v);
    }
}

