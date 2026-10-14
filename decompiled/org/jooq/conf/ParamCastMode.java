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

@XmlType(name="ParamCastMode")
@XmlEnum
public enum ParamCastMode {
    ALWAYS,
    DEFAULT,
    NEVER;


    public String value() {
        return this.name();
    }

    public static ParamCastMode fromValue(String v) {
        return ParamCastMode.valueOf(v);
    }
}

