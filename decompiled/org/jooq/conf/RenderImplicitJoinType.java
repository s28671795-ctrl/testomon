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

@XmlType(name="RenderImplicitJoinType")
@XmlEnum
public enum RenderImplicitJoinType {
    DEFAULT,
    INNER_JOIN,
    LEFT_JOIN;


    public String value() {
        return this.name();
    }

    public static RenderImplicitJoinType fromValue(String v) {
        return RenderImplicitJoinType.valueOf(v);
    }
}

