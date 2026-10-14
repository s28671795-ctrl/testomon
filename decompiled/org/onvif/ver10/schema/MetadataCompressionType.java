/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="MetadataCompressionType")
@XmlEnum
public enum MetadataCompressionType {
    NONE("None"),
    GZIP("GZIP"),
    EXI("EXI");

    private final String value;

    private MetadataCompressionType(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static MetadataCompressionType fromValue(String v) {
        for (MetadataCompressionType c : MetadataCompressionType.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

