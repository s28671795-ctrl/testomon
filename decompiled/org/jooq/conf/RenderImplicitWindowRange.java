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

@XmlType(name="RenderImplicitWindowRange")
@XmlEnum
public enum RenderImplicitWindowRange {
    OFF,
    ROWS_UNBOUNDED_PRECEDING,
    ROWS_ALL,
    RANGE_UNBOUNDED_PRECEDING,
    RANGE_ALL;


    public String value() {
        return this.name();
    }

    public static RenderImplicitWindowRange fromValue(String v) {
        return RenderImplicitWindowRange.valueOf(v);
    }
}

