/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="StorageType")
@XmlEnum
public enum StorageType {
    NFS,
    CIFS,
    CDMI;


    public String value() {
        return this.name();
    }

    public static StorageType fromValue(String v) {
        return StorageType.valueOf(v);
    }
}

