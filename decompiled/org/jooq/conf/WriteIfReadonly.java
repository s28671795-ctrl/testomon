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

@XmlType(name="WriteIfReadonly")
@XmlEnum
public enum WriteIfReadonly {
    WRITE,
    IGNORE,
    THROW;


    public String value() {
        return this.name();
    }

    public static WriteIfReadonly fromValue(String v) {
        return WriteIfReadonly.valueOf(v);
    }
}

