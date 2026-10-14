/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Direction")
@XmlEnum
public enum Direction {
    LEFT("Left"),
    RIGHT("Right"),
    ANY("Any");

    private final String value;

    private Direction(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static Direction fromValue(String v) {
        for (Direction c : Direction.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

