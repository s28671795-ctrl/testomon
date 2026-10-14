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

@XmlType(name="DiagnosticsConnection")
@XmlEnum
public enum DiagnosticsConnection {
    DEFAULT,
    ON,
    OFF;


    public String value() {
        return this.name();
    }

    public static DiagnosticsConnection fromValue(String v) {
        return DiagnosticsConnection.valueOf(v);
    }
}

