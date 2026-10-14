/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="CombinatorType", namespace="http://cxf.apache.org/configuration/security")
@XmlEnum
public enum CombinatorType {
    ANY,
    ALL;


    public String value() {
        return this.name();
    }

    public static CombinatorType fromValue(String v) {
        return CombinatorType.valueOf(v);
    }
}

