/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Dot11Cipher")
@XmlEnum
public enum Dot11Cipher {
    CCMP("CCMP"),
    TKIP("TKIP"),
    ANY("Any"),
    EXTENDED("Extended");

    private final String value;

    private Dot11Cipher(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static Dot11Cipher fromValue(String v) {
        for (Dot11Cipher c : Dot11Cipher.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

