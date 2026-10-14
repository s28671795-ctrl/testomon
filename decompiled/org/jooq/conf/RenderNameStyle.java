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

@XmlType(name="RenderNameStyle")
@XmlEnum
@Deprecated
public enum RenderNameStyle {
    QUOTED,
    AS_IS,
    LOWER,
    UPPER;


    public String value() {
        return this.name();
    }

    public static RenderNameStyle fromValue(String v) {
        return RenderNameStyle.valueOf(v);
    }
}

