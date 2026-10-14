/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.TransportProtocol;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Transport", propOrder={"protocol", "tunnel"})
public class Transport {
    @XmlElement(name="Protocol", required=true)
    @XmlSchemaType(name="string")
    protected TransportProtocol protocol;
    @XmlElement(name="Tunnel")
    protected Transport tunnel;

    public TransportProtocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(TransportProtocol value) {
        this.protocol = value;
    }

    public Transport getTunnel() {
        return this.tunnel;
    }

    public void setTunnel(Transport value) {
        this.tunnel = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

