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

@XmlType(name="TransformUnneededArithmeticExpressions")
@XmlEnum
public enum TransformUnneededArithmeticExpressions {
    NEVER,
    INTERNAL,
    ALWAYS;


    public String value() {
        return this.name();
    }

    public static TransformUnneededArithmeticExpressions fromValue(String v) {
        return TransformUnneededArithmeticExpressions.valueOf(v);
    }
}

