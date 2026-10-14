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

@XmlType(name="Transformation")
@XmlEnum
public enum Transformation {
    NEVER,
    WHEN_NEEDED,
    ALWAYS;


    public String value() {
        return this.name();
    }

    public static Transformation fromValue(String v) {
        return Transformation.valueOf(v);
    }
}

