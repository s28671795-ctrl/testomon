/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http.configuration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="proxyServerType")
@XmlEnum
public enum ProxyServerType {
    HTTP,
    SOCKS;


    public String value() {
        return this.name();
    }

    public static ProxyServerType fromValue(String v) {
        return ProxyServerType.valueOf(v);
    }
}

