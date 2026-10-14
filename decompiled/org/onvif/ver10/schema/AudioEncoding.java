/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AudioEncoding")
@XmlEnum
public enum AudioEncoding {
    G_711("G711"),
    G_726("G726"),
    AAC("AAC");

    private final String value;

    private AudioEncoding(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static AudioEncoding fromValue(String v) {
        for (AudioEncoding c : AudioEncoding.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

