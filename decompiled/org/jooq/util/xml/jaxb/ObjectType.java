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

@XmlType(name="ObjectType")
@XmlEnum
public enum ObjectType {
    DOMAIN("DOMAIN"),
    ROUTINE("ROUTINE"),
    TABLE("TABLE"),
    USER_DEFINED_TYPE("USER-DEFINED TYPE");

    private final String value;

    private ObjectType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ObjectType fromValue(String v) {
        for (ObjectType c : ObjectType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }

    public String toString() {
        switch (this) {
            case USER_DEFINED_TYPE: {
                return "USER-DEFINED TYPE";
            }
        }
        return this.name();
    }
}

