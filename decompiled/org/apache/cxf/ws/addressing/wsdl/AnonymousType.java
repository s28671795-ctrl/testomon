/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AnonymousType", namespace="http://www.w3.org/2006/05/addressing/wsdl")
@XmlEnum
public enum AnonymousType {
    OPTIONAL("optional"),
    REQUIRED("required"),
    PROHIBITED("prohibited");

    private final String value;

    private AnonymousType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static AnonymousType fromValue(String v) {
        for (AnonymousType c : AnonymousType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

