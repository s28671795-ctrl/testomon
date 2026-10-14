/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="VideoEncodingMimeNames")
@XmlEnum
public enum VideoEncodingMimeNames {
    JPEG("JPEG"),
    MPV_4_ES("MPV4-ES"),
    H_264("H264"),
    H_265("H265");

    private final String value;

    private VideoEncodingMimeNames(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static VideoEncodingMimeNames fromValue(String v) {
        for (VideoEncodingMimeNames c : VideoEncodingMimeNames.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

