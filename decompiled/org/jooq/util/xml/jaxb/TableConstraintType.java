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

@XmlType(name="TableConstraintType")
@XmlEnum
public enum TableConstraintType {
    PRIMARY_KEY("PRIMARY KEY"),
    UNIQUE("UNIQUE"),
    CHECK("CHECK"),
    FOREIGN_KEY("FOREIGN KEY");

    private final String value;

    private TableConstraintType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static TableConstraintType fromValue(String v) {
        for (TableConstraintType c : TableConstraintType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }

    public String toString() {
        switch (this) {
            case PRIMARY_KEY: {
                return "PRIMARY KEY";
            }
            case FOREIGN_KEY: {
                return "FOREIGN KEY";
            }
        }
        return this.name();
    }
}

