/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.v200408;

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
import org.apache.cxf.ws.addressing.v200408.AttributedQName;
import org.apache.cxf.ws.addressing.v200408.AttributedURI;
import org.apache.cxf.ws.addressing.v200408.ReferenceParametersType;
import org.apache.cxf.ws.addressing.v200408.ReferencePropertiesType;
import org.apache.cxf.ws.addressing.v200408.ServiceNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EndpointReferenceType", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", propOrder={"address", "referenceProperties", "referenceParameters", "portType", "serviceName", "any"})
public class EndpointReferenceType {
    @XmlElement(name="Address", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", required=true)
    protected AttributedURI address = new AttributedURI();
    @XmlElement(name="ReferenceProperties", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing")
    protected ReferencePropertiesType referenceProperties;
    @XmlElement(name="ReferenceParameters", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing")
    protected ReferenceParametersType referenceParameters;
    @XmlElement(name="PortType", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing")
    protected AttributedQName portType;
    @XmlElement(name="ServiceName", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing")
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
}

