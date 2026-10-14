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

@XmlType(name="ParseNameCase")
@XmlEnum
public enum ParseNameCase {
    AS_IS,
    LOWER,
    LOWER_IF_UNQUOTED,
    UPPER,
    UPPER_IF_UNQUOTED,
    DEFAULT;


    public String value() {
        return this.name();
    }

    public static ParseNameCase fromValue(String v) {
        return ParseNameCase.valueOf(v);
    }
}

