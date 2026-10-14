/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transports.http_jetty.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.configuration.security.TLSServerParametersType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TLSServerParametersIdentifiedType", propOrder={"tlsServerParameters"})
public class TLSServerParametersIdentifiedType {
    @XmlElement(required=true)
    protected TLSServerParametersType tlsServerParameters = new TLSServerParametersType();
    @XmlAttribute(name="id")
    protected String id;

    public TLSServerParametersType getTlsServerParameters() {
        return this.tlsServerParameters;
    }

    public void setTlsServerParameters(TLSServerParametersType value) {
        this.tlsServerParameters = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }
}

