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

@XmlType(name="ParseUnknownFunctions")
@XmlEnum
public enum ParseUnknownFunctions {
    FAIL,
    IGNORE;


    public String value() {
        return this.name();
    }

    public static ParseUnknownFunctions fromValue(String v) {
        return ParseUnknownFunctions.valueOf(v);
    }
}

