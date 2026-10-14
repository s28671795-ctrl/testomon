/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="WideDynamicMode")
@XmlEnum
public enum WideDynamicMode {
    OFF,
    ON;


    public String value() {
        return this.name();
    }

    public static WideDynamicMode fromValue(String v) {
        return WideDynamicMode.valueOf(v);
    }
}

