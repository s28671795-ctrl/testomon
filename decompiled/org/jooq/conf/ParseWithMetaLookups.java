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

@XmlType(name="ParseWithMetaLookups")
@XmlEnum
public enum ParseWithMetaLookups {
    OFF,
    IGNORE_ON_FAILURE,
    THROW_ON_FAILURE;


    public String value() {
        return this.name();
    }

    public static ParseWithMetaLookups fromValue(String v) {
        return ParseWithMetaLookups.valueOf(v);
    }
}

