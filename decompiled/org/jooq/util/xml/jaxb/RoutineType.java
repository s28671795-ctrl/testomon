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

@XmlType(name="RoutineType")
@XmlEnum
public enum RoutineType {
    FUNCTION,
    PROCEDURE;


    public String value() {
        return this.name();
    }

    public static RoutineType fromValue(String v) {
        return RoutineType.valueOf(v);
    }
}

