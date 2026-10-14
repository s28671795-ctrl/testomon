/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="KeyStatus")
@XmlEnum
public enum KeyStatus {
    OK("ok"),
    GENERATING("generating"),
    CORRUPT("corrupt");

    private final String value;

    private KeyStatus(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static KeyStatus fromValue(String v) {
        for (KeyStatus c : KeyStatus.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

