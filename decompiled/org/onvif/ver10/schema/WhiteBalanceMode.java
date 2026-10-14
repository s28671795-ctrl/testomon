/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="WhiteBalanceMode")
@XmlEnum
public enum WhiteBalanceMode {
    AUTO,
    MANUAL;


    public String value() {
        return this.name();
    }

    public static WhiteBalanceMode fromValue(String v) {
        return WhiteBalanceMode.valueOf(v);
    }
}

