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

@XmlType(name="RenderOptionalKeyword")
@XmlEnum
public enum RenderOptionalKeyword {
    OFF,
    ON,
    DEFAULT;


    public String value() {
        return this.name();
    }

    public static RenderOptionalKeyword fromValue(String v) {
        return RenderOptionalKeyword.valueOf(v);
    }
}

