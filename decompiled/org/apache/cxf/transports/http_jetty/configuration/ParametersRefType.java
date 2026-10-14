/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http_jetty.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ParametersRefType")
public class ParametersRefType {
    @XmlAttribute(name="id", required=true)
    protected String id;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }
}

