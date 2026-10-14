/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlEnum
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name="BackslashEscaping")
@XmlEnum
public enum BackslashEscaping {
    DEFAULT,
    ON,
    OFF;


    public String value() {
        return this.name();
    }

    public static BackslashEscaping fromValue(String v) {
        return BackslashEscaping.valueOf(v);
    }
}

