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

@XmlType(name="RenderQuotedNames")
@XmlEnum
public enum RenderQuotedNames {
    ALWAYS,
    EXPLICIT_DEFAULT_QUOTED,
    EXPLICIT_DEFAULT_UNQUOTED,
    NEVER;


    public String value() {
        return this.name();
    }

    public static RenderQuotedNames fromValue(String v) {
        return RenderQuotedNames.valueOf(v);
    }
}

