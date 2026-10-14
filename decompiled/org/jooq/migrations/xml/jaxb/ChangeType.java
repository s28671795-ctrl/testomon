/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlEnum
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.migrations.xml.jaxb;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name="ChangeType")
@XmlEnum
public enum ChangeType {
    ADD,
    MODIFY,
    DELETE,
    RENAME,
    COPY;


    public String value() {
        return this.name();
    }

    public static ChangeType fromValue(String v) {
        return ChangeType.valueOf(v);
    }
}

