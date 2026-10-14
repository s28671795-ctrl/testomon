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

@XmlType(name="QueryPoolable")
@XmlEnum
public enum QueryPoolable {
    TRUE,
    FALSE,
    DEFAULT;


    public String value() {
        return this.name();
    }

    public static QueryPoolable fromValue(String v) {
        return QueryPoolable.valueOf(v);
    }
}

