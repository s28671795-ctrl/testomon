/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="RotateMode")
@XmlEnum
public enum RotateMode {
    OFF,
    ON,
    AUTO;


    public String value() {
        return this.name();
    }

    public static RotateMode fromValue(String v) {
        return RotateMode.valueOf(v);
    }
}

