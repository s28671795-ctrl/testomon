/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

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
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.MetadataType;
import org.apache.cxf.ws.addressing.ReferenceParametersType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EndpointReferenceType", namespace="http://www.w3.org/2005/08/addressing", propOrder={"address", "referenceParameters", "metadata", "any"})
public class EndpointReferenceType {
    @XmlElement(name="Address", namespace="http://www.w3.org/2005/08/addressing", required=true)
    protected AttributedURIType address = new AttributedURIType();
    @XmlElement(name="ReferenceParameters", namespace="http://www.w3.org/2005/08/addressing")
    protected ReferenceParametersType referenceParameters;
    @XmlElement(name="Metadata", namespace="http://www.w3.org/2005/08/addressing")
    protected MetadataType metadata;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AttributedURIType getAddress() {
        return this.address;
    }

    public void setAddress(AttributedURIType value) {
        this.address = value;
    }

    public ReferenceParametersType getReferenceParameters() {
        return this.referenceParameters;
    }

    public void setReferenceParameters(ReferenceParametersType value) {
        this.referenceParameters = value;
    }

    public MetadataType getMetadata() {
        return this.metadata;
    }

    public void setMetadata(MetadataType value) {
        this.metadata = value;
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

