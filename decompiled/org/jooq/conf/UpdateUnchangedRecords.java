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

@XmlType(name="UpdateUnchangedRecords")
@XmlEnum
public enum UpdateUnchangedRecords {
    NEVER,
    SET_PRIMARY_KEY_TO_ITSELF,
    SET_NON_PRIMARY_KEY_TO_THEMSELVES,
    SET_NON_PRIMARY_KEY_TO_RECORD_VALUES;


    public String value() {
        return this.name();
    }

    public static UpdateUnchangedRecords fromValue(String v) {
        return UpdateUnchangedRecords.valueOf(v);
    }
}

