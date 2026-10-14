/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="Mpeg4Profile")
@XmlEnum
public enum Mpeg4Profile {
    SP,
    ASP;


    public String value() {
        return this.name();
    }

    public static Mpeg4Profile fromValue(String v) {
        return Mpeg4Profile.valueOf(v);
    }
}

