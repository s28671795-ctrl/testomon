/*
 * Decompiled with CFR 0.152.
 */
package org.xmlsoap.schemas.ws._2005._04.discovery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2005._04.discovery.ScopesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ProbeMatchType", propOrder={"endpointReference", "types", "scopes", "xAddrs", "metadataVersion", "any"})
public class ProbeMatchType {
    @XmlElement(name="EndpointReference", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", required=true)
    protected EndpointReferenceType endpointReference;
    @XmlList
    @XmlElement(name="Types")
    protected List<QName> types;
    @XmlElement(name="Scopes")
    protected ScopesType scopes;
    @XmlList
    @XmlElement(name="XAddrs")
    protected List<String> xAddrs;
    @XmlElement(name="MetadataVersion")
    @XmlSchemaType(name="unsignedInt")
    protected long metadataVersion;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public EndpointReferenceType getEndpointReference() {
        return this.endpointReference;
    }

    public void setEndpointReference(EndpointReferenceType value) {
        this.endpointReference = value;
    }

    public List<QName> getTypes() {
        if (this.types == null) {
            this.types = new ArrayList<QName>();
        }
        return this.types;
    }

    public ScopesType getScopes() {
        return this.scopes;
    }

    public void setScopes(ScopesType value) {
        this.scopes = value;
    }

    public List<String> getXAddrs() {
        if (this.xAddrs == null) {
            this.xAddrs = new ArrayList<String>();
        }
        return this.xAddrs;
    }

    public long getMetadataVersion() {
        return this.metadataVersion;
    }

    public void setMetadataVersion(long value) {
        this.metadataVersion = value;
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

