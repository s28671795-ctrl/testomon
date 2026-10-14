/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="VideoEncoding")
@XmlEnum
public enum VideoEncoding {
    JPEG("JPEG"),
    MPEG_4("MPEG4"),
    H_264("H264");

    private final String value;

    private VideoEncoding(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static VideoEncoding fromValue(String v) {
        for (VideoEncoding c : VideoEncoding.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

