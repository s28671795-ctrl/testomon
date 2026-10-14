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

@XmlType(name="ExecuteWithoutWhere")
@XmlEnum
public enum ExecuteWithoutWhere {
    IGNORE,
    LOG_DEBUG,
    LOG_INFO,
    LOG_WARN,
    THROW;


    public String value() {
        return this.name();
    }

    public static ExecuteWithoutWhere fromValue(String v) {
        return ExecuteWithoutWhere.valueOf(v);
    }
}

