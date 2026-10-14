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

@XmlType(name="RenderNameCase")
@XmlEnum
public enum RenderNameCase {
    AS_IS,
    LOWER,
    LOWER_IF_UNQUOTED,
    UPPER,
    UPPER_IF_UNQUOTED;


    public String value() {
        return this.name();
    }

    public static RenderNameCase fromValue(String v) {
        return RenderNameCase.valueOf(v);
    }
}

