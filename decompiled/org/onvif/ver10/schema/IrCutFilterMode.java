/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="IrCutFilterMode")
@XmlEnum
public enum IrCutFilterMode {
    ON,
    OFF,
    AUTO;


    public String value() {
        return this.name();
    }

    public static IrCutFilterMode fromValue(String v) {
        return IrCutFilterMode.valueOf(v);
    }
}

