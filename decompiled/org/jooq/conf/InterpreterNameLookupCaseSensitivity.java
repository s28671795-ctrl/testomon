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

@XmlType(name="InterpreterNameLookupCaseSensitivity")
@XmlEnum
public enum InterpreterNameLookupCaseSensitivity {
    DEFAULT,
    ALWAYS,
    WHEN_QUOTED,
    NEVER;


    public String value() {
        return this.name();
    }

    public static InterpreterNameLookupCaseSensitivity fromValue(String v) {
        return InterpreterNameLookupCaseSensitivity.valueOf(v);
    }
}

