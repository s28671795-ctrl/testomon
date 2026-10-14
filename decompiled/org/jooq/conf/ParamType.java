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

@XmlType(name="ParamType")
@XmlEnum
public enum ParamType {
    INDEXED,
    FORCE_INDEXED,
    NAMED,
    NAMED_OR_INLINED,
    INLINED;


    public String value() {
        return this.name();
    }

    public static ParamType fromValue(String v) {
        return ParamType.valueOf(v);
    }
}

