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

@XmlType(name="RenderTable")
@XmlEnum
public enum RenderTable {
    ALWAYS,
    WHEN_MULTIPLE_TABLES,
    WHEN_AMBIGUOUS_COLUMNS,
    NEVER;


    public String value() {
        return this.name();
    }

    public static RenderTable fromValue(String v) {
        return RenderTable.valueOf(v);
    }
}

