/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="UserLevel")
@XmlEnum
public enum UserLevel {
    ADMINISTRATOR("Administrator"),
    OPERATOR("Operator"),
    USER("User"),
    ANONYMOUS("Anonymous"),
    EXTENDED("Extended");

    private final String value;

    private UserLevel(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static UserLevel fromValue(String v) {
        for (UserLevel c : UserLevel.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

