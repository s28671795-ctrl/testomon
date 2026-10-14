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

@XmlType(name="RenderDefaultNullability")
@XmlEnum
public enum RenderDefaultNullability {
    IMPLICIT_DEFAULT,
    IMPLICIT_NULL,
    EXPLICIT_NULL;


    public String value() {
        return this.name();
    }

    public static RenderDefaultNullability fromValue(String v) {
        return RenderDefaultNullability.valueOf(v);
    }
}

