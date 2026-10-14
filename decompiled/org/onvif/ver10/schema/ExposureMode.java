/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ExposureMode")
@XmlEnum
public enum ExposureMode {
    AUTO,
    MANUAL;


    public String value() {
        return this.name();
    }

    public static ExposureMode fromValue(String v) {
        return ExposureMode.valueOf(v);
    }
}

