/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Enabled")
@XmlEnum
public enum Enabled {
    ENABLED,
    DISABLED;


    public String value() {
        return this.name();
    }

    public static Enabled fromValue(String v) {
        return Enabled.valueOf(v);
    }
}

