/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="ParityBit")
@XmlEnum
public enum ParityBit {
    NONE("None"),
    EVEN("Even"),
    ODD("Odd"),
    MARK("Mark"),
    SPACE("Space"),
    EXTENDED("Extended");

    private final String value;

    private ParityBit(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static ParityBit fromValue(String v) {
        for (ParityBit c : ParityBit.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

