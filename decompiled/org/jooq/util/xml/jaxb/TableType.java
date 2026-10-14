/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlEnum
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.util.xml.jaxb;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name="TableType")
@XmlEnum
public enum TableType {
    BASE_TABLE("BASE TABLE"),
    VIEW("VIEW"),
    GLOBAL_TEMPORARY("GLOBAL TEMPORARY");

    private final String value;

    private TableType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TableType fromValue(String v) {
        for (TableType c : TableType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }

    public String toString() {
        switch (this) {
            case BASE_TABLE: {
                return "BASE TABLE";
            }
            case GLOBAL_TEMPORARY: {
                return "GLOBAL TEMPORARY";
            }
        }
        return this.name();
    }
}

