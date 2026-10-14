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

@XmlType(name="RenderKeywordStyle")
@XmlEnum
@Deprecated
public enum RenderKeywordStyle {
    AS_IS,
    LOWER,
    UPPER,
    PASCAL;


    public String value() {
        return this.name();
    }

    public static RenderKeywordStyle fromValue(String v) {
        return RenderKeywordStyle.valueOf(v);
    }
}

