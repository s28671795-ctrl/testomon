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

@XmlType(name="NestedCollectionEmulation")
@XmlEnum
public enum NestedCollectionEmulation {
    NATIVE,
    DEFAULT,
    XML,
    JSON,
    JSONB;


    public String value() {
        return this.name();
    }

    public static NestedCollectionEmulation fromValue(String v) {
        return NestedCollectionEmulation.valueOf(v);
    }
}

