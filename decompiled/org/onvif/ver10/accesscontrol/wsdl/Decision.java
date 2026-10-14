/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Decision")
@XmlEnum
public enum Decision {
    GRANTED("Granted"),
    DENIED("Denied");

    private final String value;

    private Decision(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static Decision fromValue(String v) {
        for (Decision c : Decision.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

