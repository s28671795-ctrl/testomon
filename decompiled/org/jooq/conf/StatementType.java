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

@XmlType(name="StatementType")
@XmlEnum
public enum StatementType {
    STATIC_STATEMENT,
    PREPARED_STATEMENT;


    public String value() {
        return this.name();
    }

    public static StatementType fromValue(String v) {
        return StatementType.valueOf(v);
    }
}

