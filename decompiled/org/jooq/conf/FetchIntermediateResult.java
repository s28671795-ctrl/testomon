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

@XmlType(name="FetchIntermediateResult")
@XmlEnum
public enum FetchIntermediateResult {
    ALWAYS,
    WHEN_EXECUTE_LISTENERS_PRESENT,
    WHEN_RESULT_REQUESTED;


    public String value() {
        return this.name();
    }

    public static FetchIntermediateResult fromValue(String v) {
        return FetchIntermediateResult.valueOf(v);
    }
}

