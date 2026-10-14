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

@XmlType(name="ParameterMode")
@XmlEnum
public enum ParameterMode {
    IN,
    INOUT,
    OUT;


    public String value() {
        return this.name();
    }

    public static ParameterMode fromValue(String v) {
        return ParameterMode.valueOf(v);
    }
}

