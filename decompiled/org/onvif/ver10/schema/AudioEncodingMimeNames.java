/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AudioEncodingMimeNames")
@XmlEnum
public enum AudioEncodingMimeNames {
    PCMU("PCMU"),
    G_726("G726"),
    MP_4_A_LATM("MP4A-LATM");

    private final String value;

    private AudioEncodingMimeNames(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static AudioEncodingMimeNames fromValue(String v) {
        for (AudioEncodingMimeNames c : AudioEncodingMimeNames.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

