/*
 * Decompiled with CFR 0.152.
 */
package org.xmlsoap.schemas.ws._2004._08.addressing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.xmlsoap.schemas.ws._2004._08.addressing.AttributedQName;
import org.xmlsoap.schemas.ws._2004._08.addressing.AttributedURI;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferenceParametersType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferencePropertiesType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ServiceNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EndpointReferenceType", propOrder={"address", "referenceProperties", "referenceParameters", "portType", "serviceName", "any"})
public class EndpointReferenceType {
    @XmlElement(name="Address", required=true)
    protected AttributedURI address;
    @XmlElement(name="ReferenceProperties")
    protected ReferencePropertiesType referenceProperties;
    @XmlElement(name="ReferenceParameters")
    protected ReferenceParametersType referenceParameters;
    @XmlElement(name="PortType")
    protected AttributedQName portType;
    @XmlElement(name="ServiceName")
    protected ServiceNameType serviceName;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AttributedURI getAddress() {
        return this.address;
    }

    public void setAddress(AttributedURI value) {
        this.address = value;
    }

    public ReferencePropertiesType getReferenceProperties() {
        return this.referenceProperties;
    }

    public void setReferenceProperties(ReferencePropertiesType value) {
        this.referenceProperties = value;
    }

    public ReferenceParametersType getReferenceParameters() {
        return this.referenceParameters;
    }

    public void setReferenceParameters(ReferenceParametersType value) {
        this.referenceParameters = value;
    }

    public AttributedQName getPortType() {
        return this.portType;
    }

    public void setPortType(AttributedQName value) {
        this.portType = value;
    }

    public ServiceNameType getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(ServiceNameType value) {
        this.serviceName = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

