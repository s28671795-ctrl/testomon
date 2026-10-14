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

@XmlType(name="ThrowExceptions")
@XmlEnum
public enum ThrowExceptions {
    THROW_ALL,
    THROW_FIRST,
    THROW_NONE;


    public String value() {
        return this.name();
    }

    public static ThrowExceptions fromValue(String v) {
        return ThrowExceptions.valueOf(v);
    }
}

